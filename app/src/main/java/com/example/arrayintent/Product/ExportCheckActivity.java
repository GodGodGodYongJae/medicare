package com.example.arrayintent.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExportCheckActivity extends AppCompatActivity implements View.OnClickListener {

    ExportCheckListAdapter exportCheckListAdapter;

    TextView orderCodeText;
    TextView supplyNameText;
    TextView supplyManagerNameText;
    TextView orderDataText;
    TextView supplyPhoneNumberText;

    ImageView exportListSendBtn;
    ImageView exportListResetBtn;
    ImageView back;

    ListView exportCheckListView;
    SwipeRefreshLayout swipe;
    ArrayList<Map> exportCheckList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_check);

        orderCodeText = findViewById(R.id.orderCodeText);
        supplyNameText = findViewById(R.id.supplyNameText);
        supplyManagerNameText = findViewById(R.id.supplyManagerNameText);
        exportListSendBtn = findViewById(R.id.exportListSendBtn);
        exportListResetBtn = findViewById(R.id.exportListResetBtn);
        back = findViewById(R.id.back);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        exportListSendBtn.setOnClickListener(this);
        exportListResetBtn.setOnClickListener(this);
        back.setOnClickListener(this);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                Bundle bundle = getIntent().getExtras();

                HashMap itemMap = new HashMap();
                if(bundle != null){
                    itemMap = (HashMap) bundle.getSerializable("getItem");
                }
                exportListConnection _exportListconnection = new exportListConnection();
                _exportListconnection.execute(String.valueOf(itemMap.get("contract_code")));
                swipe.setRefreshing(false);
            }
        });

        Bundle bundle = this.getIntent().getExtras();

        HashMap itemMap = new HashMap();
        if(bundle != null)
        {
            itemMap = (HashMap) bundle.getSerializable("getItem");
        }
        exportListConnection exportListConnection = new exportListConnection();
        exportListConnection.execute(String.valueOf(itemMap.get("contract_code")));

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }else {
                    v.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.exportListResetBtn:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
            case R.id.back:
                super.onBackPressed();
                break;
            case R.id.exportListSendBtn:
                boolean isZero = false;
                exportCheckListView  = (ListView) findViewById(R.id.exportCheckList);
                Log.i("sz",String.valueOf(exportCheckListView.getChildCount()));
                for (int i = 0 ; i < exportCheckList.size(); i++) {



                    //EditText exportTextReal_Qty = (EditText)exportCheckListView.getAdapter().getView(i,null,exportCheckListView).findViewById(R.id.exportTextReal_Qty);
                   // TextView exportTextReal_Qty = (TextView) exportCheckListView.getAdapter().getView(i,null,null).findViewById(R.id.exportTextReal_Qty);
//                    EditText exportTextReal_Qty = (EditText) exportCheckListView.getChildAt(i).findViewById(R.id.exportTextReal_Qty);


//                    Log.i("EX", String.valueOf(exportTextReal_Qty.getText()));
                    try{
                        Log.i("EX",exportCheckListAdapter.textMap.get(i));
                    }catch (Exception e)
                    {
                        Log.i("err",e.toString());
                    }

                    if (exportCheckListAdapter.textMap.get(i).length() <= 0 || exportCheckListAdapter.textMap.get(i) == "0") {
                        isZero = true;
                    }
                }
                    if(isZero == false)
                    {
                        jsonSend();
                        super.onBackPressed();
                        Toast.makeText(this, "출고가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }else{
                        Toast.makeText(this, "출고 수량을 확인해주세요. ", Toast.LENGTH_SHORT).show();
                        break;
                    }


        }
    }

    private void jsonSend() {
        JSONObject JsonSend = new JSONObject();
        Log.i("js","Send1");
        try{
            JSONArray jsonSendArray = new JSONArray();
            exportCheckListView = (ListView) findViewById(R.id.exportCheckList);
//            EditText exportTextReal_Qty = exportCheckListView.getChildAt(0).findViewById(R.id.exportTextReal_Qty);
//            EditText exportTextReal_Qty2 = exportCheckListView.getChildAt(1).findViewById(R.id.exportTextReal_Qty);
//            EditText exportTextReal_Qty3 = exportCheckListView.getChildAt(2).findViewById(R.id.exportTextReal_Qty);
//            Log.d("1", String.valueOf(exportTextReal_Qty.getText()));
//            Log.d("2", String.valueOf(exportTextReal_Qty2.getText()));
//            Log.d("3", String.valueOf(exportTextReal_Qty3.getText()));



            SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor logineditor = loginpref.edit();
            for(int i=0; i<exportCheckList.size(); i++){
                exportCheckList.get(i);
                JSONObject row = new JSONObject();
                long now = System.currentTimeMillis(); //현재시간 가져오기
                Date date = new Date(now); //Date형식으로 Convert
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //SimpleDateFormat 로 원하는 포맷형식 만들기
                String getTime = dateFormat.format(date); //지정한 포맷형식으로 일자받기
                EditText exportTextReal_Qty = exportCheckListView.getChildAt(i).findViewById(R.id.exportTextReal_Qty);
//                EditText exportTextBad_Qty = exportCheckListView.getChildAt(i).findViewById(R.id.exportTextBad_Qty);

                row.put("io_date", getTime);
                row.put("io_type", "OUT");
                row.put("cust_seq", exportCheckList.get(i).get("cust_seq"));
                row.put("orderno", exportCheckList.get(i).get("contract_code"));
                row.put("manager_seq", loginpref.getString("manager_seq",""));
                row.put("pdt_cd", exportCheckList.get(i).get("pdt_cd"));
                row.put("good_qty", exportCheckListAdapter.textMap.get(i));
//                row.put("bad_qty",exportTextBad_Qty.getText());
                row.put("bad_qty","0");
                row.put("bad_reason", "");
                row.put("bigo", "");
//                row.put("unit", sendexportBean.getUnit());
//                row.put("unit_price", sendexportBean.getUnit_price());
//                row.put("supply_price", sendexportBean.getSupply_price());
//                row.put("vat", sendexportBean.getVat());
//                row.put("order_date", sendexportBean.getOrder_date());
//                row.put("close_date", sendexportBean.getClose_date());
//                //row.put("business_no", sendexportBean.getBusiness_no());
//                //row.put("supply_manager_name", sendexportBean.getSupply_manager_name());
//                row.put("from_type", sendexportBean.getFrom_type());
//                row.put("to_type", sendexportBean.getTo_type());
//                row.put("bigo",sendexportBean.getBigo());
//                row.put("order_addr",sendexportBean.getOrder_addr());
//                row.put("supply_amount",sendexportBean.getSupply_amount());
//                row.put("tax_amount",sendexportBean.getTax_amount());
//                row.put("total_amount",sendexportBean.getTotal_amount());
//                row.put("order_seq",sendexportBean.getOrder_seq());
//                row.put("qty",sendexportBean.getQty());

                jsonSendArray.put(row);
            }
            JsonSend.put("body",jsonSendArray);
            String JsonSendString = JsonSend.toString();
            Log.d("JsonSendString ::: ",JsonSendString);
            try {
                exportListSendConnection exportListSendConnection = new exportListSendConnection();
                String send_result = exportListSendConnection.execute(String.valueOf(jsonSendArray)).get();
                Log.w("Json전송 결과", send_result);
            }catch (Exception e){
                Toast.makeText(this, "서버 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e){
            Log.e("error",e.toString());
        }
    }


    public class exportListSendConnection extends AsyncTask<String,Void,String>
    {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {Log.i("js","Send2");
            try {
                String str;
                URL url = new URL("http://192.168.0.227:8089/api/insertIO");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);
                conn.setRequestProperty("access_token","LY6LAhDvtaV9q2kmNlkK");
                conn.setRequestProperty("type","MES");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = strings[0];
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK)
                {
                   InputStreamReader tmp = new InputStreamReader(conn.getInputStream(),"UTF-8");
                   BufferedReader reader = new BufferedReader(tmp);
                   StringBuffer buffer = new StringBuffer();

                   while((str = reader.readLine()) != null)
                   {
                        buffer.append(str);
                   }
                   receiveMsg = buffer.toString();
                }
                else{
                    Log.i("통신결과", conn.getResponseCode() + "에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return receiveMsg;
        }
    }
    public class exportListConnection extends AsyncTask<String,Void,String>
    {
        String sendMsg,reciveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                exportCheckList = new ArrayList<>();
                String str;
                URL url = new URL("http://192.168.0.227:8089/api/contractInfo?"+"code="+strings[0]+"&type=MES");
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
                    while ((str = reader.readLine())!= null)
                    {
                        buffer.append(str);
                    }
                    reciveMsg = buffer.toString();

                    JSONObject jsonObj = new JSONObject(reciveMsg);
                    JSONArray storeData = jsonObj.getJSONArray("storeData");
                    for(int i=0; i<storeData.length(); i++){
                        JSONObject row = storeData.getJSONObject(i);
                        Map list = new HashMap();
                        list.put("contract_seq",row.getString("contract_seq"));
                        list.put("contract_code",row.getString("contract_code"));
                        list.put("cust_seq",row.getString("cust_seq"));
                        list.put("contract_phonenumber",row.getString("contract_phonenumber"));
                        list.put("contract_date",row.getString("contract_date"));
                        list.put("close_date",row.getString("close_date"));
                        list.put("total_amount",row.getString("total_amount"));
                        list.put("supply_amount",row.getString("supply_amount"));
                        list.put("tax_amount",row.getString("tax_amount"));
                        list.put("buyer_name",row.getString("buyer_name"));
                        list.put("bigo",row.getString("bigo"));
                        list.put("contract_status",row.getString("contract_status"));
                        list.put("no",row.getString("no"));
                        list.put("pdt_cd",row.getString("pdt_cd"));
                        list.put("pdt_code",row.getString("pdt_code"));
                        list.put("pdt_name",row.getString("pdt_name"));
                        list.put("pdt_standard",row.getString("pdt_standard"));
                        list.put("qty",row.getString("qty"));
                        list.put("unit",row.getString("unit"));
                        list.put("unit_price",row.getString("unit_price"));
                        list.put("supply_price",row.getString("supply_price"));
                        list.put("vat",row.getString("vat"));
                        list.put("contract_manager_name",row.getString("contract_manager_name"));
                        list.put("buyer_manager_name",row.getString("buyer_manager_name"));
                        list.put("post_no",row.getString("post_no"));
                        list.put("buyer_addr",row.getString("buyer_addr"));
                        list.put("buyer_addr_info",row.getString("buyer_addr_info"));
                        list.put("buyer_phonenumber",row.getString("buyer_phonenumber"));
                        list.put("location1",row.getString("location1"));
                        list.put("location2",row.getString("location2"));
                        list.put("location3",row.getString("location3"));
                        list.put("location_name",row.getString("location_name"));
                        Log.d("리스트 내용 ::::: ", String.valueOf(list));
                        exportCheckList.add(list);
                    }
                    exportCheckListAdapter = new ExportCheckListAdapter(ExportCheckActivity.this,R.layout.export_listview,exportCheckList);
                    exportCheckListView = (ListView) findViewById(R.id.exportCheckList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            orderCodeText = findViewById(R.id.orderCodeText);
                            supplyNameText = findViewById(R.id.supplyNameText);
                            supplyManagerNameText = findViewById(R.id.supplyManagerNameText);
                            orderDataText = findViewById(R.id.orderDateText);
                            supplyPhoneNumberText = findViewById(R.id.supplyPhoneNumberText);

                            orderCodeText.setText((CharSequence) exportCheckList.get(0).get("contract_code"));
                            supplyNameText.setText((CharSequence) exportCheckList.get(0).get("buyer_name"));
                            supplyManagerNameText.setText((CharSequence) exportCheckList.get(0).get("buyer_manager_name"));
                            orderDataText.setText((CharSequence) exportCheckList.get(0).get("contract_date"));
                            supplyPhoneNumberText.setText((CharSequence) exportCheckList.get(0).get("buyer_phonenumber"));

                            exportCheckListView.setAdapter(exportCheckListAdapter);
                            exportCheckListView.setItemsCanFocus(true);

                        }
                    });

                }
                else{
                    Log.i("통신 결과",conn.getResponseCode() + "에러");
                }


            }catch (Exception e){

            }
            return reciveMsg;
        }
    }
}