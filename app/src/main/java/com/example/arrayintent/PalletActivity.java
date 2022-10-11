package com.example.arrayintent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PalletActivity extends AppCompatActivity implements View.OnClickListener{

    private UserDatabaseHelper userDatabaseHelper;
    public  static final String TABLE_NAME = "test2";
    SQLiteDatabase database;

    TextView palletBarcode;
    Button palletScanBtn;
    Button boxScanBtn;
    Button listSendBtn;
    Button palletNumBtn;
    SwipeRefreshLayout swipe;
    ListView list;
    EditText palletNumEditText;
    LinearLayout palletLinear;


    int Data_Num;
    int Day_Num;
    int BoxData_Num;
    int BoxDay_Num;
    int n;
    String data_1;
    String scan_day;
    String scan_id;
    ArrayList<String> BoxDataList;
    ArrayAdapter<String> Adapter;

    private String days(){
        SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String today = day.format(cal.getTime());

        return today;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.palletactivity_main);
        SharedPreferences palletpref = getSharedPreferences("pallet",MODE_PRIVATE);
        SharedPreferences.Editor palleteditor = palletpref.edit();

        palletBarcode = findViewById(R.id.palletBarcode);
        palletScanBtn = findViewById(R.id.palletScanBtn);
        boxScanBtn = findViewById(R.id.boxScanBtn);
        listSendBtn = findViewById(R.id.listSendBtn);
        palletNumBtn = findViewById(R.id.palletNumBtn);
        palletLinear = (LinearLayout) View.inflate(PalletActivity.this, R.layout.palletnumdialog, null);
        palletNumEditText =(EditText) palletLinear.findViewById(R.id.palletNumEditText);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);


        palletScanBtn.setOnClickListener(this);
        boxScanBtn.setOnClickListener(this);
        listSendBtn.setOnClickListener(this);
        palletNumBtn.setOnClickListener(this);

        userDatabaseHelper  = UserDatabaseHelper.getInstance(this);
        database = userDatabaseHelper.getWritableDatabase();



        // 당겨서 새로고침
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);

                Intent intent = getIntent();
                finish();
                startActivity(intent);

                swipe.setRefreshing(false);
            }
        });



    }

    // 동작하면서 새로고침이 됨
    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences palletpref = getSharedPreferences("pallet",MODE_PRIVATE);

        //--------------------------
        BoxDataList = new ArrayList<>();
        BoxData_Num = palletpref.getInt("BoxData_Num", 0);


        for (n = 0; n < BoxData_Num; n++) {
            String BoxBaData = palletpref.getString("BoxData" + n, "");
            BoxDataList.add(BoxBaData);
        }

        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, BoxDataList);
        list = (ListView) findViewById(R.id.boxListview );
        list.setAdapter(Adapter);
        //------------------------------

    }



    @Override
    public void onClick(View v) {
        SharedPreferences palletpref = getSharedPreferences("pallet",MODE_PRIVATE);
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor palleteditor = palletpref.edit();
        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        switch (v.getId()) {
            case R.id.palletScanBtn:
                scanCode();
                break;

            case R.id.boxScanBtn:
                scanCode2();
                break;

            case R.id.listSendBtn:
                if(wifi.isConnected()) {
                    if (!loginpref.getString("test_id","").equals("")) {

                        for (n = 0; n < BoxData_Num; n++) {
                            String data_1 = palletpref.getString("BoxData"+n,null);
                            String scan_day = palletpref.getString("BoxDay"+n,null);
                            String scan_id =  loginpref.getString("test_id",null);

                            Log.w("앱에서 보낸 값", data_1+scan_day);

                            insertData(data_1, scan_day, scan_id);

                        }

                    send();
                    palleteditor.clear();
                    palleteditor.commit();
                }else {
                        Toast myToast = Toast.makeText(this.getApplicationContext(), "로그인 후 전송하세요.", Toast.LENGTH_SHORT);
                        myToast.show();

                    }
                }else {
                    Toast myToast = Toast.makeText(this.getApplicationContext(), "WIFI를 연결 후 전송하세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                break;


         case R.id.palletNumBtn:
             palleteditor.putString("pallet_barcode",null);
             palleteditor.commit();

             AlertDialog.Builder palletDialog = new AlertDialog.Builder(PalletActivity.this);
             if(palletLinear.getParent() != null) {
                 ((ViewGroup) palletLinear.getParent()).removeView(palletLinear);
                 palletDialog.setView(palletLinear);
             }else {
                 palletDialog.setView(palletLinear);
             }

             palletDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             String pallet_data = palletpref.getString("pallet_barcode",null);
                             String pallet_idx = palletNumEditText.getText().toString();

//                             ViewGroup dialogParentView = (ViewGroup) palletLinear.getParent();

                             try {
                                 CheckPallet checkPallet = new CheckPallet();
                                 String Pallet_result = checkPallet.execute(pallet_data ,pallet_idx).get();
                                 Log.w("받은 값", Pallet_result);
                                 if (Pallet_result.equals("false")){
                                     palleteditor.putString("pallet_idx",pallet_idx);
                                     palleteditor.commit();

                                     Toast.makeText(PalletActivity.this, palletpref.getString("pallet_idx",null)+"번은 사용가능한 Pallet 입니다.", Toast.LENGTH_SHORT).show();
                                     dialog.dismiss();
//                                     dialogParentView.removeView(palletLinear);
                                 }else if(Pallet_result.equals("true")) {
                                     Toast.makeText(PalletActivity.this, "사용중인 Pallet 입니다.", Toast.LENGTH_SHORT).show();
                                 }


                             }catch (Exception e){
                                 Toast.makeText(PalletActivity.this, "서버 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                             }

                         }
                     });
                     palletDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             dialog.dismiss();

                         }
                     });
                     palletDialog.show();


        }

    }

    //------------------------------스캔---------------------------------------------------
    private void scanCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false); //자동 화면 가로,세로 변횐
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);  //모든타입 바코드
        integrator.setPrompt("Scanning");
        integrator.setRequestCode(1);
        integrator.initiateScan();
    }

    private void scanCode2(){
        IntentIntegrator integrator2 = new IntentIntegrator(this);
        integrator2.setCaptureActivity(CaptureAct2.class);
        integrator2.setOrientationLocked(false); //자동 화면 가로,세로 변횐
        integrator2.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);  //모든타입 바코드
        integrator2.setPrompt("Scanning");
        integrator2.setRequestCode(2);
        integrator2.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult pallet_result = IntentIntegrator.parseActivityResult(resultCode, data);
        String pallet_data = pallet_result.getContents();  //바코드데이터를 BaData에 담음
        SharedPreferences palletpref = getSharedPreferences("pallet",MODE_PRIVATE);
        SharedPreferences.Editor palleteditor = palletpref.edit();
        List<String> boxArrayList = new ArrayList<String>();

        BoxData_Num = palletpref.getInt("BoxData_Num", 0);
        BoxDay_Num = palletpref.getInt("BoxDay_Num",0);
        palleteditor.commit();

        if(pallet_result != null){
            if(pallet_data != null){
                if(requestCode == 1){
                palleteditor.putString("pallet_barcode",pallet_data);
                palleteditor.commit();
                palletBarcode.setText(palletpref.getString("pallet_barcode",null));
                Toast myToast = Toast.makeText(this.getApplicationContext(), pallet_data+"가 확인되었습니다.", Toast.LENGTH_SHORT);
                myToast.show();


            } else if(requestCode == 2){



                    palleteditor.putString("BoxData"+BoxData_Num, pallet_data); // 바코드 데이터를 "Ba_Data" key에 넣기
                    palleteditor.putString("BoxDay"+BoxDay_Num, days());

                    BoxData_Num++; //xml에 "Ba_Data"가 하나 추가될 때 마다 숫자 1씩 증가
                    BoxDay_Num++;

                    palleteditor.putInt("BoxData_Num",BoxData_Num); //증가된 Data_Num를 xml에 저장
                    palleteditor.putInt("BoxDay_Num",BoxDay_Num);
                    palleteditor.commit(); //커밋


                    Toast myToast = Toast.makeText(this.getApplicationContext(), BoxData_Num+"번째"+pallet_data+"PalletList에 저장되었습니다.", Toast.LENGTH_SHORT);
                    myToast.show();

                    scanCode2();
                }

        }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }



    //-------------------------중계서버로 전달------------------------------------------------------
    //---------------box들 보내기----------------//
    void send() {
        Log.w("send","데이터 전달중");
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();
        SharedPreferences palletpref = getSharedPreferences("pallet",MODE_PRIVATE);
        SharedPreferences.Editor palleteditor = palletpref.edit();
        Map<String, String> BoxList = new HashMap<String, String>();


        try {
            String pallet_data = palletpref.getString("pallet_barcode", "");
            String pallet_day = days();
            String pallet_admin_id =loginpref.getString("test_id",null);

            for (n = 0; n < 160; n++) {

                BoxList.put("box_data"+n, palletpref.getString("BoxData"+n,null));

                Log.w("앱에서 보낸 값", pallet_data+pallet_day);

            }
            PalletActivity.CustomTask task = new PalletActivity.CustomTask();

            String result2 = task.execute(pallet_data, pallet_day, pallet_admin_id, BoxList.get("box_data1"), BoxList.get("box_data2"), BoxList.get("box_data3"), BoxList.get("box_data4"), BoxList.get("box_data5"), BoxList.get("box_data6"), BoxList.get("box_data7"), BoxList.get("box_data8"), BoxList.get("box_data9"), BoxList.get("box_data10"),
                    BoxList.get("box_data11"), BoxList.get("box_data12"), BoxList.get("box_data13"), BoxList.get("box_data14"), BoxList.get("box_data15"), BoxList.get("box_data16"), BoxList.get("box_data17"), BoxList.get("box_data18"), BoxList.get("box_data19"), BoxList.get("box_data20"),
                    BoxList.get("box_data21"), BoxList.get("box_data22"), BoxList.get("box_data23"), BoxList.get("box_data24"), BoxList.get("box_data25"), BoxList.get("box_data26"), BoxList.get("box_data27"), BoxList.get("box_data28"), BoxList.get("box_data29"), BoxList.get("box_data30"),
                    BoxList.get("box_data31"), BoxList.get("box_data32"), BoxList.get("box_data33"), BoxList.get("box_data34"), BoxList.get("box_data35"), BoxList.get("box_data36"), BoxList.get("box_data37"), BoxList.get("box_data38"), BoxList.get("box_data39"), BoxList.get("box_data40"),
                    BoxList.get("box_data41"), BoxList.get("box_data42"), BoxList.get("box_data43"), BoxList.get("box_data44"), BoxList.get("box_data45"), BoxList.get("box_data46"), BoxList.get("box_data47"), BoxList.get("box_data48"), BoxList.get("box_data49"), BoxList.get("box_data50"),
                    BoxList.get("box_data51"), BoxList.get("box_data52"), BoxList.get("box_data53"), BoxList.get("box_data54"), BoxList.get("box_data55"), BoxList.get("box_data56"), BoxList.get("box_data57"), BoxList.get("box_data58"), BoxList.get("box_data59"), BoxList.get("box_data60"),
                    BoxList.get("box_data61"), BoxList.get("box_data62"), BoxList.get("box_data63"), BoxList.get("box_data64"), BoxList.get("box_data65"), BoxList.get("box_data66"), BoxList.get("box_data67"), BoxList.get("box_data68"), BoxList.get("box_data69"), BoxList.get("box_data70"),
                    BoxList.get("box_data71"), BoxList.get("box_data72"), BoxList.get("box_data73"), BoxList.get("box_data74"), BoxList.get("box_data75"), BoxList.get("box_data76"), BoxList.get("box_data77"), BoxList.get("box_data78"), BoxList.get("box_data79"), BoxList.get("box_data80"),
                    BoxList.get("box_data81"), BoxList.get("box_data82"), BoxList.get("box_data83"), BoxList.get("box_data84"), BoxList.get("box_data85"), BoxList.get("box_data86"), BoxList.get("box_data87"), BoxList.get("box_data88"), BoxList.get("box_data89"), BoxList.get("box_data90"),
                    BoxList.get("box_data91"), BoxList.get("box_data92"), BoxList.get("box_data93"), BoxList.get("box_data94"), BoxList.get("box_data95"), BoxList.get("box_data96"), BoxList.get("box_data97"), BoxList.get("box_data98"), BoxList.get("box_data99"), BoxList.get("box_data100"),
                    BoxList.get("box_data101"), BoxList.get("box_data102"), BoxList.get("box_data103"), BoxList.get("box_data104"), BoxList.get("box_data105"), BoxList.get("box_data106"), BoxList.get("box_data107"), BoxList.get("box_data108"), BoxList.get("box_data109"), BoxList.get("box_data110"),
                    BoxList.get("box_data111"), BoxList.get("box_data112"), BoxList.get("box_data113"), BoxList.get("box_data114"), BoxList.get("box_data115"), BoxList.get("box_data116"), BoxList.get("box_data117"), BoxList.get("box_data118"), BoxList.get("box_data119"), BoxList.get("box_data120"),
                    BoxList.get("box_data121"), BoxList.get("box_data122"), BoxList.get("box_data123"), BoxList.get("box_data124"), BoxList.get("box_data125"), BoxList.get("box_data126"), BoxList.get("box_data127"), BoxList.get("box_data128"), BoxList.get("box_data129"), BoxList.get("box_data130"),
                    BoxList.get("box_data131"), BoxList.get("box_data132"), BoxList.get("box_data133"), BoxList.get("box_data134"), BoxList.get("box_data135"), BoxList.get("box_data136"), BoxList.get("box_data137"), BoxList.get("box_data138"), BoxList.get("box_data139"), BoxList.get("box_data140"),
                    BoxList.get("box_data141"), BoxList.get("box_data142"), BoxList.get("box_data143"), BoxList.get("box_data144"), BoxList.get("box_data145"), BoxList.get("box_data146"), BoxList.get("box_data147"), BoxList.get("box_data148"), BoxList.get("box_data149"), BoxList.get("box_data150"),
                    BoxList.get("box_data151"), BoxList.get("box_data152"), BoxList.get("box_data153"), BoxList.get("box_data154"), BoxList.get("box_data155"), BoxList.get("box_data156"), BoxList.get("box_data157"), BoxList.get("box_data158"), BoxList.get("box_data159"), BoxList.get("box_data160")).get();
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
                URL url = new URL("http://mespda.thethe.co.kr/pallet");
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);



                // 서버에 보낼 값 포함해 요청함.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "pallet_data="+strings[0]+"&pallet_day="+strings[1]+"&pallet_admin_id="+strings[2]+"&box_data1="+strings[3]+"&box_data2="+strings[4]+"&box_data3="+strings[5]+"&box_data4="+strings[6]+"&box_data5="+strings[7]+"&box_data6="+strings[8]+"&box_data7="+strings[9]+"&box_data8="+strings[10]+"&box_data9="+strings[11]+"&box_data10="+strings[12]+
                        "&box_data11="+strings[13]+"&box_data12="+strings[14]+"&box_data13="+strings[15]+"&box_data14="+strings[16]+"&box_data15="+strings[17]+"&box_data16="+strings[18]+"&box_data17="+strings[19]+"&box_data18="+strings[20]+"&box_data19="+strings[21]+"&box_data20="+strings[22]+
                        "&box_data21="+strings[23]+"&box_data22="+strings[24]+"&box_data23="+strings[25]+"&box_data24="+strings[26]+"&box_data25="+strings[27]+"&box_data26="+strings[28]+"&box_data27="+strings[29]+"&box_data28="+strings[30]+"&box_data29="+strings[31]+"&box_data30="+strings[32]+
                        "&box_data31="+strings[33]+"&box_data32="+strings[34]+"&box_data33="+strings[35]+"&box_data34="+strings[36]+"&box_data35="+strings[37]+"&box_data36="+strings[38]+"&box_data37="+strings[39]+"&box_data38="+strings[40]+"&box_data39="+strings[41]+"&box_data40="+strings[42]+
                        "&box_data41="+strings[43]+"&box_data42="+strings[44]+"&box_data43="+strings[45]+"&box_data44="+strings[46]+"&box_data45="+strings[47]+"&box_data46="+strings[48]+"&box_data47="+strings[49]+"&box_data48="+strings[50]+"&box_data49="+strings[51]+"&box_data50="+strings[52]+
                        "&box_data51="+strings[53]+"&box_data52="+strings[54]+"&box_data53="+strings[55]+"&box_data54="+strings[56]+"&box_data55="+strings[57]+"&box_data56="+strings[58]+"&box_data57="+strings[59]+"&box_data58="+strings[60]+"&box_data59="+strings[61]+"&box_data60="+strings[62]+
                        "&box_data61="+strings[63]+"&box_data62="+strings[64]+"&box_data63="+strings[65]+"&box_data64="+strings[66]+"&box_data65="+strings[67]+"&box_data66="+strings[68]+"&box_data67="+strings[69]+"&box_data68="+strings[70]+"&box_data69="+strings[71]+"&box_data70="+strings[72]+
                        "&box_data71="+strings[73]+"&box_data72="+strings[74]+"&box_data73="+strings[75]+"&box_data74="+strings[76]+"&box_data75="+strings[77]+"&box_data76="+strings[78]+"&box_data77="+strings[79]+"&box_data78="+strings[80]+"&box_data79="+strings[81]+"&box_data80="+strings[82]+
                        "&box_data81="+strings[83]+"&box_data82="+strings[84]+"&box_data83="+strings[85]+"&box_data84="+strings[86]+"&box_data85="+strings[87]+"&box_data86="+strings[88]+"&box_data87="+strings[89]+"&box_data88="+strings[90]+"&box_data89="+strings[91]+"&box_data90="+strings[92]+
                        "&box_data91="+strings[93]+"&box_data92="+strings[94]+"&box_data93="+strings[95]+"&box_data94="+strings[96]+"&box_data95="+strings[97]+"&box_data96="+strings[98]+"&box_data97="+strings[99]+"&box_data98="+strings[100]+"&box_data99="+strings[101]+"&box_data100="+strings[102]+
                        "&box_data101="+strings[103]+"&box_data102="+strings[104]+"&box_data103="+strings[105]+"&box_data104="+strings[106]+"&box_data105="+strings[107]+"&box_data106="+strings[108]+"&box_data107="+strings[109]+"&box_data108="+strings[110]+"&box_data109="+strings[111]+"&box_data110="+strings[112]+
                        "&box_data111="+strings[113]+"&box_data112="+strings[114]+"&box_data113="+strings[115]+"&box_data114="+strings[116]+"&box_data115="+strings[117]+"&box_data116="+strings[118]+"&box_data117="+strings[119]+"&box_data118="+strings[120]+"&box_data119="+strings[121]+"&box_data120="+strings[122]+
                        "&box_data121="+strings[123]+"&box_data122="+strings[124]+"&box_data123="+strings[125]+"&box_data124="+strings[126]+"&box_data125="+strings[127]+"&box_data126="+strings[128]+"&box_data127="+strings[129]+"&box_data128="+strings[130]+"&box_data129="+strings[131]+"&box_data130="+strings[132]+
                        "&box_data131="+strings[133]+"&box_data132="+strings[134]+"&box_data133="+strings[135]+"&box_data134="+strings[136]+"&box_data135="+strings[137]+"&box_data136="+strings[138]+"&box_data137="+strings[139]+"&box_data138="+strings[140]+"&box_data139="+strings[141]+"&box_data140="+strings[142]+
                        "&box_data141="+strings[143]+"&box_data142="+strings[144]+"&box_data143="+strings[145]+"&box_data144="+strings[146]+"&box_data145="+strings[147]+"&box_data146="+strings[148]+"&box_data147="+strings[149]+"&box_data148="+strings[150]+"&box_data149="+strings[151]+"&box_data150="+strings[152]+
                        "&box_data151="+strings[153]+"&box_data152="+strings[154]+"&box_data153="+strings[155]+"&box_data154="+strings[156]+"&box_data155="+strings[157]+"&box_data156="+strings[158]+"&box_data157="+strings[159]+"&box_data158="+strings[160]+"&box_data159="+strings[161]+"&box_data160="+strings[162];

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

            } catch (IOException e) {
                e.printStackTrace();

            }
            return receiveMsg;
        }
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
