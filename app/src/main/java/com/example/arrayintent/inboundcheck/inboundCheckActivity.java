package com.example.arrayintent.inboundcheck;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arrayintent.ImportBean;
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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class inboundCheckActivity extends AppCompatActivity  implements View.OnClickListener{
ArrayList<orderBean> orderBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbound_check);
        ImageView btn_back = findViewById(R.id.back);
        btn_back.setOnClickListener(this);

        onInitData();


    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private String FormatFloatToString(float data){

        DecimalFormat formatter = new DecimalFormat("#,##0.#");
        float parseflaot = data;
        String tmp = formatter.format(parseflaot) + " 원";
        return tmp;
    }

    private void onInitData(){
        Intent intent = getIntent();
        String orderCode = intent.getStringExtra("OrderCode");
        APIConnection apiConnection = new APIConnection();
        try {
            String Json_Result = apiConnection.execute(orderCode).get();
            Log.w("Json",Json_Result);


            TextView tv_custname = findViewById(R.id.tv_custnameText);
            tv_custname.setText(orderBeans.get(0).getCust_name());
            TextView tv_orderDate = findViewById(R.id.orderDateText);
            tv_orderDate.setText(orderBeans.get(0).getOrder_date());
            TextView tv_supplyAmount = findViewById(R.id.supplyAmountText);
            tv_supplyAmount.setText(FormatFloatToString(orderBeans.get(0).getSupply_amount()));

            TextView tv_taxAmount = findViewById(R.id.taxAmountText);
            tv_taxAmount.setText(FormatFloatToString(orderBeans.get(0).getTax_amount()));
            TextView tv_totalAmount = findViewById(R.id.totalAmountText);
            tv_totalAmount.setText(FormatFloatToString(orderBeans.get(0).getTotal_amount()));
            DrawListAdatper();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // data binding
        TextView tv_ordercode = findViewById(R.id.orderCodeText);

        // data input
        tv_ordercode.setText(orderCode);

    }

    private void DrawListAdatper(){
        orderListTradingListViewAdapter _orderListTradingListViewAdapter = new orderListTradingListViewAdapter(getApplicationContext(),R.layout.orderlist_listview_list,orderBeans);
        ListView orderlistview = (ListView) findViewById(R.id.importCheckList);
        orderlistview.setAdapter(_orderListTradingListViewAdapter);
    }

public class APIConnection extends AsyncTask<String,Void,String>
{
    String sendMsg, receiveMsg;
    @Override
    protected String doInBackground(String... strings) {

        String str;
        Log.w("test",strings[0]);
        try {
            orderBeans = new ArrayList<>();
            URL url = new URL("http://192.168.0.227:8089/api/orderCheckList");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(6000);
            conn.setRequestProperty("access_token", "LY6LAhDvtaV9q2kmNlkK");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            sendMsg = "{\"ORDER_CODE\":\"" + strings[0] + "\"}";
            Log.i("sendMsg",sendMsg);
            osw.write(sendMsg);
            osw.flush();
            String tmp2 = "";
            for (String strss : strings) {
                tmp2 += strss + " ";
            }
            Log.i("strings : ", tmp2);
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
                    JSONArray jArray = (JSONArray) jsonObj.get("orderlist");
                    for (int i = 0; i < jArray.length(); i++) {
                        //json배열.getJSONObject(인덱스)
                        JSONObject row = jArray.getJSONObject(i);
                        orderBean Bean = new orderBean();
//                        importBean = new ImportBean();
//                        //json객체.get자료형("변수명")
                        Bean.setNo(i);
                        Bean.setCust_name(row.getString("CUST_NAME"));
                        Bean.setPdt_name(row.getString("PDT_NAME"));
                        Bean.setQty(Float.parseFloat(row.getString("QTY")));
                        Bean.setRealqty(Float.parseFloat(row.getString("REALQTY")));
                        Bean.setSupply_amount(Float.parseFloat(row.getString("SUPPLY_AMOUNT")));
                        Bean.setTax_amount(Float.parseFloat(row.getString("TAX_AMOUNT")));
                        Bean.setTotal_amount(Float.parseFloat(row.getString("TOTAL_AMOUNT")));
                        JSONObject daterow = row.getJSONObject("date_format(mo");
                        String t = daterow.getString("ORDER_DATE,'%Y-%m-%d')");
                        Bean.setOrder_date(t);
//                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                        String t = row.getString("\"date_format(mo\":{\"ORDER_DATE,'%Y-%m-%d')\"");
//                        Log.i("date2",t);
//                        Date date = format.parse(row.getString("\"date_format(mo\":{\"ORDER_DATE,'%Y-%m-%d')\""));
//                        Log.i("date",date.toString());
//                        Bean.setOrder_date(date);

//                        importBean.setTrading_qr_filecode(row.getString("TRADING_QR_FILECODE"));
//                        importBean.setOrder_code(row.getString("ORDER_CODE"));
//                        importBean.setSupply_manager_name(row.getString("SUPPLY_MANAGER_NAME"));
//
//
//                        importBean.setNO(row.getInt("NO"));
//                        importBean.setPdt_name(row.getString("PDT_NAME"));
//                        importBean.setQty(row.getInt("QTY"));
//                        importBean.setPdt_real_qty(row.getInt("QTY"));
//                        importBean.setUnit(row.getString("UNIT"));
//                        selectBoxList.add(importBean);
                        orderBeans.add(Bean);
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
