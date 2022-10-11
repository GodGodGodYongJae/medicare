package com.example.arrayintent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class listActivity extends AppCompatActivity implements View.OnClickListener {

    Button sendBtn;
    ArrayAdapter<String> Adapter;
    ArrayList<String> DataList;
    ArrayList<String> DayList;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int Data_Num;
    int n;
    int Day_Num;
    String BaData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listactivity_main);
        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(this);


        SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        DataList = new ArrayList<String>();
        DayList =new ArrayList<String>();


        Data_Num = pref.getInt("Data_Num", 0);
        Day_Num = pref.getInt("Day_Num",0);


        for (n = 0; n < Data_Num; n++) {
            BaData = pref.getString("Ba_Data" + n, "");
            DataList.add(BaData);
        }



        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DataList);

        ListView list = (ListView) findViewById(R.id.listview);
        list.setAdapter(Adapter);


    }
    //메뉴바
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); //메뉴 XML파일 인플레이션
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //화면에 설정한 메뉴를 사용자가 선택하면 onOptionsItemSelected 메소드가 호출됨
        int curId = item.getItemId();
        //어떤 메뉴를 선택했는지를 id로 구분하여 처리
        switch(curId) {
            case R.id.action_login:
                Intent intent = new Intent(listActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, "로그인 메뉴가 선택되었습니다.", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onClick(View v) {
        pref = getSharedPreferences("test", MODE_PRIVATE);
        editor = pref.edit();
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();
        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        switch (v.getId()) {
            case R.id.sendBtn:
                if (!loginpref.getString("test_id", "").equals("")) {
                if (wifi.isConnected()) {
                    send();
                    Toast myToast = Toast.makeText(this.getApplicationContext(), "정상적으로 전송되었습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                    editor.clear();
                    editor.commit();

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    break;
                } else {
                    Toast myToast = Toast.makeText(this.getApplicationContext(), "WIFI를 연결 후 전송하세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
        }else {
                    Toast myToast = Toast.makeText(this.getApplicationContext(), "로그인을 해주세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            default:
                break;
        }
    }


    void send() {
        Log.w("send","데이터 전달중");
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();

        try {
            for (n = 0; n < Data_Num; n++) {
                String data_1 = pref.getString("Ba_Data" + n, "");
                String scan_day = pref.getString("Ba_Day"+n,"");
                String scan_id =  loginpref.getString("test_id","");

                Log.w("앱에서 보낸 값", data_1+scan_day);
                CustomTask task = new CustomTask();
                String result2 = task.execute(data_1, scan_day, scan_id).get();

            }
        }catch (Exception e){

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
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);



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

            } catch (IOException e) {
                e.printStackTrace();
            }

            return receiveMsg;
        }

    }

}
