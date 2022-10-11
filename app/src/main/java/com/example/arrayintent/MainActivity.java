package com.example.arrayintent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private UserDatabaseHelper userDatabaseHelper;
    public  static final String TABLE_NAME = "test2";
    SQLiteDatabase database;

    Button scanBtn;
    Button listBtn;
    Button palletBtn;
    int Data_Num;
    int Day_Num;
    String data_1;
    Button loginpageBtn;
    Button logoutBtn;
    String getId;
    TextView getIdText;
    String scan_day;
    String scan_id;
    ImageView onwifi;
    ImageView nowifi;

    private String days(){
        SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String today = day.format(cal.getTime());

        return today;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();

        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        scanBtn = findViewById(R.id.scanBtn);
        listBtn = findViewById(R.id.listBtn);
        palletBtn = findViewById(R.id.palletBtn);
        loginpageBtn = findViewById(R.id.loginpageBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        getIdText = findViewById(R.id.getIdText);
        onwifi = findViewById(R.id.onwifi);
        nowifi = findViewById(R.id.nowifi);

        scanBtn.setOnClickListener(this);
        listBtn.setOnClickListener(this);
        palletBtn.setOnClickListener(this);
        loginpageBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver, filter);

        userDatabaseHelper  = UserDatabaseHelper.getInstance(this);
        database = userDatabaseHelper.getWritableDatabase();

        if (wifi.isConnected()){
            onwifi.setVisibility(View.VISIBLE);
            nowifi.setVisibility(View.GONE);

        }else {
            onwifi.setVisibility(View.GONE);
            nowifi.setVisibility(View.VISIBLE);

        }

        if(loginpref.getString("test_id","").equals("")){
            loginpageBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
            getIdText.setText("");
        }else{
            loginpageBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.VISIBLE);
            getIdText.setText(loginpref.getString("test_id","")+"님");
        }

    }

    @Override
    public void onClick(View v) {
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();

        switch (v.getId()){
          case R.id.scanBtn:
              scanCode();
              break;


          case R.id.listBtn:
              //list 버튼 누를시 listActivity 액티비티로 이동
              Intent intent = new Intent(getApplicationContext(), listActivity.class);
              startActivity(intent);
              break;

            case R.id.palletBtn:
                //list 버튼 누를시 listActivity 액티비티로 이동
                intent = new Intent(getApplicationContext(), PalletActivity.class);
                startActivity(intent);
                break;


            case R.id.loginpageBtn:
              intent = new Intent(MainActivity.this, LoginActivity.class);
              startActivity(intent);

              Toast.makeText(this, "로그인 메뉴가 선택되었습니다.", Toast.LENGTH_SHORT).show();
              break;

            case R.id.logoutBtn:
              Toast myToast = Toast.makeText(this.getApplicationContext(), loginpref.getString("test_id","")+"로그아웃 되었습니다.", Toast.LENGTH_SHORT);
              logineditor.clear();
              logineditor.commit();
              myToast.show();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
          default:
              break;
      }

    }


    //scanBtn을 눌렀을 때 사용 함수
    private void scanCode(){

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false); //자동 화면 가로,세로 변횐
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);  //모든타입 바코드
        integrator.setPrompt("Scanning");
        integrator.initiateScan();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String data_1 = result.getContents();  //바코드데이터를 BaData에 담음
        SharedPreferences pref = getSharedPreferences("test",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();
        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(wifi.isConnected() && !loginpref.getString("test_id","").equals("")) {
            String data_2 = data_1;  //data_1의 값을 sqlite data_2에 집어 넣음
            String scan_day2 = days();
            String scan_id2 = loginpref.getString("test_id","");
            insertData(data_2, scan_day2,scan_id2);
            send(result);  //와이파이가 연결확인 시 바로 send() 함수 작동


        }else {
            //와이파이가 연결되어 있지 않다면
            //Data_Num에 xml에서 가져온 "Data_Num"을 담음(int형), 몇번째 임시데이터인지 확인
            Data_Num = pref.getInt("Data_Num", 0);
            Day_Num = pref.getInt("Day_Num",0);

            editor.commit();

            if(result != null){
                if(data_1 != null){

                    editor.putString("Ba_Data"+Data_Num, data_1); // 바코드 데이터를 "Ba_Data" key에 넣기
                    editor.putString("Ba_Day"+Day_Num, days());

                    Data_Num++; //xml에 "Ba_Data"가 하나 추가될 때 마다 숫자 1씩 증가
                    Day_Num++;

                    editor.putInt("Data_Num",Data_Num); //증가된 Data_Num를 xml에 저장
                    editor.putInt("Day_Num",Day_Num);
                    editor.commit(); //커밋

                    Toast myToast = Toast.makeText(this.getApplicationContext(), Data_Num+"번째"+data_1+"임시 List에 저장되었습니다.", Toast.LENGTH_SHORT);
                    myToast.show();

                    scanCode();


                }

            }else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }


    }

    void send(IntentResult result) {
        SharedPreferences pref = getSharedPreferences("test",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();

        Log.w("send","데이터 전달중");
        try {
            data_1 =  result.getContents();
            scan_id = loginpref.getString("test_id","");
            scan_day = days();

            Log.w("앱에서 보낸 값", data_1+scan_day+scan_id);
            if(data_1 != null) {
                CustomTask task = new CustomTask();

                String result2 = task.execute(data_1, scan_day, scan_id).get();

                if (result2.equals("Usable")) {
                    Log.w("받은 값", result2);
                    Toast myToast = Toast.makeText(this.getApplicationContext(), "정상적으로 전송되었습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                    scanCode();
                } else if (result2.equals(data_1)) {
                    Log.w("받은 값", result2);
                    Toast myToast = Toast.makeText(this.getApplicationContext(), "이미 저장되어 있는 바코드입니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                    scanCode();

                } else {
                Log.w("받은 값",result2);
                Data_Num = pref.getInt("Data_Num", 0);
                Day_Num = pref.getInt("Day_Num", 0);

                editor.commit();

                editor.putString("Ba_Data"+Data_Num, data_1); // 바코드 데이터를 "Ba_Data" key에 넣기
                editor.putString("Ba_Day"+Day_Num, days());
                Data_Num++;
                Day_Num++;
                editor.putInt("Data_Num",Data_Num);
                editor.putInt("Day_Num",Day_Num);
                editor.commit();


                Toast myToast = Toast.makeText(this.getApplicationContext(), Data_Num+"번째"+data_1+"임시 List에 저장되었습니다.", Toast.LENGTH_SHORT);
                myToast.show();

                scanCode();

                }
            }


        }catch (Exception e){
            Toast.makeText(this,"서버와 연결을 확인해주세요",Toast.LENGTH_SHORT).show();
        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection conn = null;
            try {
                String str;
                URL url = new URL("http://mespda.thethe.co.kr/test2");
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");        //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);
                conn.setDoInput(true);

                //서버에서 데이터 받기





                // 서버에 보낼 값 포함해 요청함.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "data_1="+strings[0]+"&scan_day="+strings[1]+"&scan_id="+strings[2];
                osw.write(sendMsg);
                osw.flush();


                //jsp와 통신 잘되고, 서버에서 보낸 값 받음
                if(conn.getResponseCode() == conn.HTTP_OK){

                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }

                    receiveMsg = buffer.toString();
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
finally {
                if(conn != null)
                    conn.disconnect();

            }



            // 서버에서 보낸 값을 리턴합니다.
            return receiveMsg;
        }

    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch(intent.getAction()){
                //와이파이 상태변화
                case WifiManager.WIFI_STATE_CHANGED_ACTION:
                    //와이파이 상태값 가져오기
                    int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
                    switch(wifistate){
                        case WifiManager.WIFI_STATE_DISABLING: //와이파이 비활성화중
                            Log.w("와이파이상태", "비활성화중");
                            Toast myToast = Toast.makeText(MainActivity.this, "와이파이가 비활성화 중 입니다.", Toast.LENGTH_SHORT);
                            myToast.show();
                            break;

                        case WifiManager.WIFI_STATE_DISABLED:  //와이파이 비활성화
                            Log.w("와이파이상태", "비활성화");
                            myToast = Toast.makeText(MainActivity.this, "와이파이가 비활성화 되었습니다.", Toast.LENGTH_SHORT);
                            myToast.show();

                            onwifi.setVisibility(View.GONE);
                            nowifi.setVisibility(View.VISIBLE);
                            scanBtn.setText("비로그인 상태로 스캔");

                            break;

                        case WifiManager.WIFI_STATE_ENABLING:  //와이파이 활성화중
                            Log.w("와이파이상태", "활성화중");
                            myToast = Toast.makeText(MainActivity.this, "와이파이가 활성화 중 입니다.", Toast.LENGTH_SHORT);
                            myToast.show();
                            break;

                        case WifiManager.WIFI_STATE_ENABLED:   //와이파이 활성화
                            Log.w("와이파이상태", "활성화");
                            myToast = Toast.makeText(MainActivity.this, "와이파이가 활성화 되었습니다.", Toast.LENGTH_SHORT);
                            myToast.show();
                            onwifi.setVisibility(View.VISIBLE);
                            nowifi.setVisibility(View.GONE);
                            scanBtn.setText("Barcode Scan");

                            break;

                        default:

                            break;
                    }
                    break;

                //네트워크 상태변화
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                    NetworkInfo info = (NetworkInfo)intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    //네트워크 상태값 가져오기
                    NetworkInfo.DetailedState state = info.getDetailedState();

                    String typename = info.getTypeName();
                    if(state==NetworkInfo.DetailedState.CONNECTED){ //네트워크 연결
                        Log.w("네트워크", "연결됨");
                    }
                    else if(state==NetworkInfo.DetailedState.DISCONNECTED){ //네트워크 끊음
                        Log.w("네트워크", "끊김");
                    }
                    break;
            }
        }
    };


    //리시버 해제

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    ///------------SQLite 쿼리 ---------------------
    private void selectData(String tableName){
        if(database != null){
            String sql = "SELECT data_idx2, data_2, scan_day2, scan_id2 FROM " + tableName;
            Cursor cursor = database.rawQuery(sql, null);

            Log.w("데이터 갯수 : ", Integer.toString(cursor.getCount()));

            for (int i = 0; i <cursor.getCount(); i++){
                cursor.moveToNext();
                String data_idx2  = cursor.getString(0);
                String data_2 = cursor.getString(1);
                String scan_day2 = cursor.getString(2);
                String scan_id2 = cursor.getString(3);

            }
        }
    }

    private void insertData(String data_2, String scan_day2, String scan_id2){
        if (database != null){
            String sql = "INSERT INTO test2(data_2, scan_day2, scan_id2) VALUES(?, ?, ?)";
            Object[] params = {data_2, scan_day2, scan_id2};
            database.execSQL(sql, params);
            Log.w("insert 상태","성공적!");
        }
    }


}



