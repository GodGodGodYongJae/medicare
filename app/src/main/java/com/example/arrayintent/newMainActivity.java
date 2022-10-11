package com.example.arrayintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.arrayintent.inboundcheck.ImportConfirmActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class newMainActivity extends AppCompatActivity implements View.OnClickListener{
    private UserDatabaseHelper userDatabaseHelper;
    public  static final String TABLE_NAME = "test2";
    SQLiteDatabase database;

    private static final String TAG = "Main_Activity";

    private DrawerLayout drawerLayout;

    boolean bLog = false;

    int Data_Num;
    int Day_Num;
    String data_1;
    String scan_day;
    String scan_id;
    ImageView onwifi;
    ImageView nowifi;
    ImageView menuleftbtn;
    RelativeLayout menuBar;
    CardView import_pagebtn;
    CardView export_pagebtn;
    CardView production_pagebtn;
    CardView notimport_pagebtn;
    CardView in_warehouse_pagebtn;
    CardView out_warehouse_pagebtn;
    ClipData.Item importb;
    ClipData.Item export;
    ClipData.Item production;
    ClipData.Item not_trans;

    private String days(){
        SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String today = day.format(cal.getTime());

        return today;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmain);

        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();

        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        drawerLayout=findViewById(R.id.maindrawer_layout);
        onwifi = findViewById(R.id.onwifi);
        nowifi = findViewById(R.id.nowifi);
        menuleftbtn = findViewById(R.id.menuleftbtn);
        menuBar = findViewById(R.id.menuBar);
        import_pagebtn = findViewById(R.id.import_pagebtn);
        export_pagebtn = findViewById(R.id.export_pagebtn);
        production_pagebtn = findViewById(R.id.production_pagebtn);
        in_warehouse_pagebtn = findViewById(R.id.in_warehouse_pagebtn);
        out_warehouse_pagebtn = findViewById(R.id.out_warehouse_pagebtn);
        notimport_pagebtn = findViewById(R.id.notimport_pagebtn);

        menuleftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick:클릭됨");
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        import_pagebtn.setOnClickListener(this);
        notimport_pagebtn.setOnClickListener(this);
        export_pagebtn.setOnClickListener(this);
        production_pagebtn.setOnClickListener(this);
        in_warehouse_pagebtn.setOnClickListener(this);
        out_warehouse_pagebtn.setOnClickListener(this);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 메뉴버튼이 처음 눌러졌을 때 실행되는 콜백메서드
        getMenuInflater().inflate(R.menu.main_navigationmenu, menu);
        Log.d("test", "onCreateOptionsMenu - 최초 메뉴키를 눌렀을 때 호출됨");
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("test", "onPrepareOptionsMenu - 옵션메뉴가 " +
                "화면에 보여질때 마다 호출됨");
        if(bLog){ // 로그인 한 상태: 로그인은 안보이게, 로그아웃은 보이게
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setEnabled(false);
        }else{ // 로그 아웃 한 상태 : 로그인 보이게, 로그아웃은 안보이게
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(true);
        }

        bLog = !bLog;   // 값을 반대로 바꿈
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 메뉴의 항목을 선택(클릭)했을 때 호출되는 콜백메서드
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("test", "onOptionsItemSelected - 메뉴항목을 클릭했을 때 호출됨");
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();
        int id = item.getItemId();

        switch(id) {

            case R.id.import_pagebtn:
                Intent intent = new Intent(newMainActivity.this, ImportConfirmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.in_warehouse_pagebtn:
                intent = new Intent(newMainActivity.this, WarehouseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.export_pagebtn:
                intent = new Intent(newMainActivity.this, WarehouseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.production_pagebtn:
                intent = new Intent(newMainActivity.this, ImportConfirmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

            case R.id.loginBtn:
                Toast.makeText(getApplicationContext(), "로그인 메뉴 클릭",
                        Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();

        switch (v.getId()){
            case R.id.import_pagebtn:

                Intent intent = new Intent(newMainActivity.this, ImportConfirmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

            case R.id.in_warehouse_pagebtn:

                intent = new Intent(newMainActivity.this, WarehouseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

            case R.id.export_pagebtn:

                intent = new Intent(newMainActivity.this, newProductionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

            case R.id.production_pagebtn:

                intent = new Intent(newMainActivity.this, WarehouseProductActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

            case R.id.notimport_pagebtn:

                intent = new Intent(newMainActivity.this, ProductlineActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

            case R.id.out_warehouse_pagebtn:

                intent = new Intent(newMainActivity.this, NotImportConfirmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

            /*case R.id.importListSendBtn:
                intent = new Intent(newMainActivity.this, ImportDialog.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;*/
//                logineditor.clear();
//                logineditor.commit();
//                intent = new Intent(newMainActivity.this, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
            default:
                break;
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
                            Toast myToast = Toast.makeText(newMainActivity.this, "와이파이가 비활성화 중 입니다.", Toast.LENGTH_SHORT);
                            myToast.show();
                            break;

                        case WifiManager.WIFI_STATE_DISABLED:  //와이파이 비활성화
                            Log.w("와이파이상태", "비활성화");
                            myToast = Toast.makeText(newMainActivity.this, "와이파이가 비활성화 되었습니다.", Toast.LENGTH_SHORT);
                            myToast.show();

                            onwifi.setVisibility(View.GONE);
                            nowifi.setVisibility(View.VISIBLE);


                            break;

                        case WifiManager.WIFI_STATE_ENABLING:  //와이파이 활성화중
                            Log.w("와이파이상태", "활성화중");
                            myToast = Toast.makeText(newMainActivity.this, "와이파이가 활성화 중 입니다.", Toast.LENGTH_SHORT);
                            myToast.show();
                            break;

                        case WifiManager.WIFI_STATE_ENABLED:   //와이파이 활성화
                            Log.w("와이파이상태", "활성화");
                            myToast = Toast.makeText(newMainActivity.this, "와이파이가 활성화 되었습니다.", Toast.LENGTH_SHORT);
                            myToast.show();
                            onwifi.setVisibility(View.VISIBLE);
                            nowifi.setVisibility(View.GONE);

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
