package com.example.arrayintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.arrayintent.Product.ExportCheckActivity;
import com.example.arrayintent.Product.ExportListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class product_new extends AppCompatActivity implements View.OnClickListener{
ExportListAdapter exportListAdapter;
EditText orderCodeEditText;

ImageView orderCodeBtn;
ImageView exportListSendBtn;
ImageView exportListResetBtn;
ImageView back;

ListView exportListView;
ArrayList<Map> mesList;
ArrayList<Map> searchList;
SwipeRefreshLayout swipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_new);

        exportListSendBtn = findViewById(R.id.exportListSendBtn);
        exportListResetBtn = findViewById(R.id.exportListResetBtn);
        back = findViewById(R.id.back);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        orderCodeEditText = findViewById(R.id.orderCodeEditText);
        orderCodeBtn = findViewById(R.id.orderCodeBtn);

        exportListSendBtn.setOnClickListener(this);
        exportListResetBtn.setOnClickListener(this);
        back.setOnClickListener(this);
        orderCodeBtn.setOnClickListener(this);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
               exportListConnection exportListConnection = new exportListConnection();
               exportListConnection.execute();

               swipe.setRefreshing(false);
            }
        });

        exportListConnection exportListConnection = new exportListConnection();
        exportListConnection.execute();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("test","restart");
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.orderCodeBtn:
                if(orderCodeEditText.getText().length() > 0)
                {
                    try {
                        product_new.searchListConnection searchListConnection = new searchListConnection();
                        searchListConnection.execute(orderCodeEditText.getText().toString());
                    }catch (Exception e)
                    {

                    }
                }
                else{
                    Toast.makeText(this, "번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back:
                intent = new Intent(product_new.this,newMainActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.exportListResetBtn:
                 intent = getIntent();
                finish();
                startActivity(intent);
                break;
            case R.id.exportListSendBtn:
                exportListView = (ListView) findViewById(R.id.exportList);

                Log.d("Count",String.valueOf(exportListView.getCheckedItemCount()));
                if(exportListView.getCheckedItemCount() != 0)
                {
                    HashMap getItem = (HashMap) exportListView.getAdapter().getItem(exportListView.getCheckedItemPosition());
                    Bundle extras = new Bundle();
                    extras.putSerializable("getItem",getItem);

                    intent = new Intent(product_new.this, ExportCheckActivity.class);
                    intent.putExtras(extras);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);

                }else{
                    Toast.makeText(this, "리스트를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public class exportListConnection extends AsyncTask<String,Void,String>
    {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                mesList = new ArrayList<>();
                String str;
                URL url = new URL("http://192.168.0.227:8089/api/contractList?type=MES");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);
                conn.setRequestProperty("access_token","LY6LAhDvtaV9q2kmNlkK");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");

                if(conn.getResponseCode() == conn.HTTP_OK)
                {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(),"UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null)
                    {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("API 리턴",receiveMsg);
                    try{
                        JSONObject jsonObj = new JSONObject(receiveMsg);
                        JSONObject storeData = jsonObj.getJSONObject("storeData");

                        JSONArray mesjArray = (JSONArray) storeData.get("mes");
                        Log.d("mesjArray",String.valueOf(mesjArray));
                        for (int i = 0; i < mesjArray.length();i++)
                        {
                            JSONObject row = mesjArray.getJSONObject(i);
                            Map list = new HashMap();
                            list.put("no",row.getInt("no"));
                            list.put("total",row.getInt("total"));
                            list.put("contract_seq",row.getString("contract_seq"));
                            list.put("contract_code",row.getString("contract_code"));
                            list.put("contract_phoneNumber",row.getString("contract_phoneNumber"));
                            list.put("contract_status",row.getString("contract_status"));
                            list.put("buyer_name",row.getString("buyer_name"));
                            list.put("supply_amount",row.getString("supply_amount"));
                            list.put("reg_date",row.getString("reg_date"));
                            list.put("contract_date",row.getString("contract_date"));
                            list.put("close_date",row.getString("close_date"));
                            list.put("pdt_name",row.getString("pdt_name"));
                            Log.d("LIST ::::",String.valueOf(list));
                            mesList.add(list);
                        }
                        exportListAdapter = new ExportListAdapter(product_new.this,R.layout.export_listview_list,mesList);
                        exportListView = (ListView) findViewById(R.id.exportList);
                        exportListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                exportListView.setAdapter(exportListAdapter);
                            }
                        });

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Log.e("error",e.getMessage());
                    }
                }   else{
                    Log.i("통신 결과",conn.getResponseCode()+"에러");
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
    public class searchListConnection extends AsyncTask<String,Void,String>
    {
        String sendMsg,receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                searchList = new ArrayList<>();
                String str;
                URL url = new URL("http://192.168.0.227:8089/api/contractList?type=MES&search_string="+strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);
                conn.setRequestProperty("access_token", "LY6LAhDvtaV9q2kmNlkK");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("API 리턴", receiveMsg);
                    JSONObject jsonObj = new JSONObject(receiveMsg);
                    JSONObject storeData = jsonObj.getJSONObject("storeData");

                    JSONArray mesjArray = (JSONArray) storeData.get("mes");
                    for(int i = 0 ; i < mesjArray.length(); i++)
                    {
                       JSONObject row = mesjArray.getJSONObject(i);
                       Map list = new HashMap();
                        list.put("no",row.getInt("no"));
                        list.put("total",row.getInt("total"));
                        list.put("contract_seq",row.getString("contract_seq"));
                        list.put("contract_code",row.getString("contract_code"));
                        list.put("contract_phoneNumber",row.getString("contract_phoneNumber"));
                        list.put("contract_status",row.getString("contract_status"));
                        list.put("buyer_name",row.getString("buyer_name"));
                        list.put("supply_amount",row.getString("supply_amount"));
                        list.put("reg_date",row.getString("reg_date"));
                        list.put("contract_date",row.getString("contract_date"));
                        list.put("close_date",row.getString("close_date"));
//                            list.put("cnt",row.getInt("cnt"));
                        list.put("pdt_name",row.getString("pdt_name"));
                        Log.d("리스트 내용 ::::: ", String.valueOf(list));
                        searchList.add(list);
                    }
                    exportListAdapter = new ExportListAdapter(product_new.this,R.layout.export_listview_list,searchList);
                    exportListView = (ListView) findViewById(R.id.exportList);
                    exportListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            exportListView.setAdapter(exportListAdapter);
                            if(searchList.size() == 0){
                                Toast.makeText(product_new.this, "없는 번호이거나 이미 만료되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

}

