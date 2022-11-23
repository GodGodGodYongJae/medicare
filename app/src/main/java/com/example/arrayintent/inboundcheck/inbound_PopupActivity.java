package com.example.arrayintent.inboundcheck;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.arrayintent.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class inbound_PopupActivity extends Activity {

    TextView txtText;
    ListView pdtListview;
    SwipeRefreshLayout swipe;
    ArrayList<inbounBean> inbounBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.product_popuplist);

        //UI 객체생성
       // txtText = (TextView)findViewById(R.id.txtText);

        //데이터 가져오기
        Intent intent = getIntent();
//        String data = intent.getStringExtra("data");
//        txtText.setText(data);
        onInitData();
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);

            }
        });
    }

    private void onInitData(){

        APIConnection apiConnection = new APIConnection();
        try{
            String Json_Result = apiConnection.execute().get();
            Log.w("Json",Json_Result);
            DrawListAdpater();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void DrawListAdpater(){
        popupListViewAdapter ListViewAdapter = new popupListViewAdapter(getApplicationContext(),R.layout.inbound_popup_listview,inbounBeans);
        ListView listview = (ListView) findViewById(R.id.pdtListview);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listview.setAdapter(ListViewAdapter);
            }
        });

    }


    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기

        //액티비티(팝업) 닫기
        finish();
    }

    public void mOnCheck(View v)
    {
 // todo - 클릭 시 해당 정보를 putExtra에 넣어줘야 함 .


        ListView listview = (ListView) findViewById(R.id.pdtListview);
        if(listview.getCheckedItemCount() != 0)
        {

            inbounBean getItem = (inbounBean) listview.getAdapter().getItem(listview.getCheckedItemPosition());
            Log.d("Count",String.valueOf(listview.getCheckedItemCount()));

            Log.d("2222",getItem.getPdt_name());
            Intent intent = new Intent();
            intent.putExtra("result", getItem.getOrder_code());
            setResult(RESULT_OK, intent);
            finish();
        }else{
            Toast.makeText(this, "리스트를 선택해주세요.", Toast.LENGTH_SHORT).show();
        }

//
//        Log.i("Test","Fire Mung");

      //
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    public class APIConnection extends AsyncTask<String,Void,String>
    {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {

            String str;

            try {
                inbounBeans = new ArrayList<>();
                URL url = new URL("http://192.168.0.227:8089/api/orderList");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);
                conn.setRequestProperty("access_token", "LY6LAhDvtaV9q2kmNlkK");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");
//                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
//                sendMsg = "{}";
//                Log.i("sendMsg",sendMsg);
//                osw.write(sendMsg);
//                osw.flush();
//                String tmp2 = "";
//                for (String strss : strings) {
//                    tmp2 += strss + " ";
//                }
//                Log.i("strings : ", tmp2);
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("receiveMsg : ", receiveMsg);
                    try {
                        //string > JsonObject로 변환
                        JSONObject jsonObj = new JSONObject(receiveMsg);
                        //json객체.get("변수명")
                        JSONObject storeDate = jsonObj.getJSONObject("storeData");
                        JSONArray jArray = (JSONArray) storeDate.get("list");
                        for (int i = 0; i < jArray.length(); i++) {
                            //json배열.getJSONObject(인덱스)
                            JSONObject row = jArray.getJSONObject(i);
                            inbounBean Bean = new inbounBean();
//                        importBean = new ImportBean();
//                        //json객체.get자료형("변수명")
                            Bean.setOrder_code(row.getString("order_code"));
                            Bean.setSupply_name(row.getString("supply_name"));
                            Bean.setPdt_name(row.getString("pdt_name"));
                            Bean.setOrder_date("order_date");



                            inbounBeans.add(Bean);
                        }
//                    Log.i("selectBox리스트요", selectBoxList.get(0).getPdt_name());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("error", e.getMessage());
                    }

                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
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
