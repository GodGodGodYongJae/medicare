package com.example.arrayintent;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.content.SharedPreferences;
        import android.device.ScanManager;
        import android.device.scanner.configuration.PropertyID;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

        import com.google.zxing.integration.android.IntentIntegrator;
        import com.google.zxing.integration.android.IntentResult;
        import com.jaredrummler.materialspinner.MaterialSpinner;

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
        import java.util.List;

public class NotImportConfirmActivity extends AppCompatActivity implements View.OnClickListener {
    ImportListViewAdapter importListViewAdapter;
    NotImportTradingListViewAdapter notImportTradingListViewAdapter;
    ArrayList<ImportBean> selectBoxList;
    String pdtcodeResult;
    String ordercodeResult;
    String pdtCodeItem;
    String orderCodeItem;
    String connect = "success";
    String notimport_pdt_code;
    String notimport_order_code;
    ArrayAdapter<String> pdtCodeAdapter;
    ArrayAdapter<String> orderCodeAdapter;
    ImportBean importBean;

    TextView orderCodeText;

    MaterialSpinner pdtCodeSpinner;
    MaterialSpinner orderCodeSpinner;

    ImageView pdtCodeBtn;
    ImageView orderCodeBtn;
    ImageView importListSendBtn;
    ImageView importListResetBtn;
    ImageView back;

    ListView pdtListview;
    SwipeRefreshLayout swipe;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notimportconfirm);

        orderCodeText = findViewById(R.id.orderCodeText);
        pdtCodeSpinner = findViewById(R.id.pdtCodeSpinner);
        orderCodeSpinner =findViewById(R.id.orderCodeSpinner);
        pdtCodeBtn = findViewById(R.id.pdtCodeBtn);
        importListSendBtn = findViewById(R.id.importListSendBtn);
        importListResetBtn = findViewById(R.id.importListResetBtn);
        back = findViewById(R.id.back);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        orderCodeBtn = findViewById(R.id.orderCodeBtn);

        pdtCodeBtn.setOnClickListener(this);
        orderCodeBtn.setOnClickListener(this);
        importListSendBtn.setOnClickListener(this);
        importListResetBtn.setOnClickListener(this);
        back.setOnClickListener(this);


        // 당겨서 새로고침
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);

                swipe.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<String> pdtCodeList = new ArrayList<String>();
        List<String> orderCodeList = new ArrayList<String>();
        try {
        GetNotInPdtCodeList getNotInPdtCodeList = new GetNotInPdtCodeList();
        pdtcodeResult = getNotInPdtCodeList.execute(connect).get();

        GetNotInOrderCodeList getNotInOrderCodeList = new GetNotInOrderCodeList();
        ordercodeResult = getNotInOrderCodeList.execute(connect).get();

        String[] splitPdtCode = pdtcodeResult.split(", ");
        pdtCodeList.add("선택하기");
        for(int i=0; i < splitPdtCode.length; i++){
            pdtCodeList.add(splitPdtCode[i]);
        }

            String[] splitOrderCode = ordercodeResult.split(", ");
            orderCodeList.add("선택하기");
            for(int i=0; i < splitOrderCode.length; i++){
                orderCodeList.add(splitOrderCode[i]);
            }

        pdtCodeAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, pdtCodeList);
        pdtCodeSpinner.setAdapter(pdtCodeAdapter);

        orderCodeAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, orderCodeList);
        orderCodeSpinner.setAdapter(orderCodeAdapter);

        }catch (Exception e){

        }
        pdtCodeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
               pdtCodeItem = item.toString();
            }
        });

        orderCodeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                orderCodeItem = item.toString();
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {




            case R.id.importListResetBtn:
                Intent intent = getIntent();
                finish();
                startActivity(intent);

                break;

            case R.id.back :
                intent = new Intent(NotImportConfirmActivity.this, newMainActivity.class);
                finish();
                startActivity(intent);
                break;

            case R.id.pdtCodeBtn :
                if (pdtCodeItem.equals("선택하기")) {
                    Toast.makeText(this, "물품코드를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    notimport_pdt_code = pdtCodeItem;
                    try{
                        NotImportPdtCodeListConnection notImportPdtCodeListConnection = new NotImportPdtCodeListConnection();
                        String jsonPdtListResult = notImportPdtCodeListConnection.execute(notimport_pdt_code).get();

                        notImportTradingListViewAdapter = new NotImportTradingListViewAdapter(getApplicationContext(),R.layout.import_listview_list, selectBoxList, importBean);
                        pdtListview = (ListView) findViewById(R.id.pdtListview);
                        pdtListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        pdtListview.setAdapter(notImportTradingListViewAdapter);

                    }
                    catch (Exception e){

                    }
                }

                break;

            case R.id.orderCodeBtn :
                if (orderCodeItem.equals("선택하기")) {
                    Toast.makeText(this, "발주번호를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    notimport_order_code = orderCodeItem;

                    try {
                        NotImportOrderCodeListConnection notImportOrderCodeListConnection = new NotImportOrderCodeListConnection();
                        String jsonOrderListResult = notImportOrderCodeListConnection.execute(notimport_order_code).get();

                        notImportTradingListViewAdapter = new NotImportTradingListViewAdapter(getApplicationContext(),R.layout.import_listview_list, selectBoxList, importBean);
                        pdtListview = (ListView) findViewById(R.id.pdtListview);
                        pdtListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        pdtListview.setAdapter(notImportTradingListViewAdapter);

                    }catch (Exception e){

                    }
                }


                break;

            case R.id.importListSendBtn:
                jsonSend();

                intent = getIntent();
                finish();
                startActivity(intent);

                Toast.makeText(this, "입고가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    // 컨트롤러와

    private void jsonSend() {
        JSONObject JsonSend = new JSONObject();

        try{
            JSONArray jsonSendArray = new JSONArray();
            for(int i=0; i<selectBoxList.size(); i++){
                ImportBean sendImportBean = selectBoxList.get(i);
                JSONObject row = new JSONObject();
                row.put("no", sendImportBean.getNO());
                row.put("real_qty",sendImportBean.getPdt_real_qty());
                row.put("order_seq",sendImportBean.getOrder_seq());


                jsonSendArray.put(row);
            }
            JsonSend.put("importpdtsendlist",jsonSendArray);
            String JsonSendString = JsonSend.toString();

            try {

                NotImportListSendConnection importListSendConnection = new NotImportListSendConnection();
                String send_result = importListSendConnection.execute(JsonSendString).get();
                Log.w("Json전송 결과", send_result);

            }catch (Exception e){
                Toast.makeText(this, "서버 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e){

        }
    }

    public class NotImportPdtCodeListConnection extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                selectBoxList = new ArrayList<>();
                String str;
                URL url = new URL("http://mespda.thethe.co.kr/import/notimportpdtcodelist.do");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "notimport_pdt_code="+strings[0];
                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    try{
                        //string > JsonObject로 변환
                        JSONObject jsonObj = new JSONObject(receiveMsg);
                        //json객체.get("변수명")
                        JSONArray jArray =(JSONArray)jsonObj.get("importpdtlist");
                        for(int i=0; i<jArray.length(); i++){
                            //json배열.getJSONObject(인덱스)
                            JSONObject row = jArray.getJSONObject(i);
                            importBean = new ImportBean();
                            //json객체.get자료형("변수명")
                            importBean.setOrder_code(row.getString("order_code"));
                            importBean.setNO(row.getInt("no"));
                            importBean.setPdt_name(row.getString("pdt_name"));
                            //importBean.setDel_qty(row.getInt("del_qty"));
                            importBean.setPdt_qty(row.getInt("pdt_qty"));
                            importBean.setPdt_real_qty(row.getInt("pdt_real_qty"));
                            //importBean.setStorage("");
                            //importBean.setStorage_zone("");
                            //importBean.setStorage_detail("");

                            importBean.setPdt_code(row.getString("pdt_code"));
                            importBean.setPdt_standard(row.getString("pdt_standard"));
                            importBean.setUnit(row.getString("unit"));
                            importBean.setUnit_price(row.getInt("unit_price"));
                            importBean.setSupply_price(row.getInt("supply_price"));
                            importBean.setVat(row.getInt("vat"));
                            //importBean.setOrder_seq(row.getString("order_seq"));



                            selectBoxList.add(importBean);

                        }
                       // Log.i("selectBox리스트요", selectBoxList.get(0).getPdt_name());
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.e("error",e.getMessage());
                    }

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            return receiveMsg;
        }
    }


    public class NotImportOrderCodeListConnection extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                selectBoxList = new ArrayList<>();
                String str;
                URL url = new URL("http://mespda.thethe.co.kr/import/notimportordercodelist.do");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "notimport_order_code="+strings[0];
                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    try{
                        //string > JsonObject로 변환
                        JSONObject jsonObj = new JSONObject(receiveMsg);
                        //json객체.get("변수명")
                        JSONArray jArray =(JSONArray)jsonObj.get("importpdtlist");
                        for(int i=0; i<jArray.length(); i++){
                            //json배열.getJSONObject(인덱스)
                            JSONObject row = jArray.getJSONObject(i);
                            importBean = new ImportBean();
                            //json객체.get자료형("변수명")
                            importBean.setOrder_code(row.getString("order_code"));
                            importBean.setNO(row.getInt("no"));
                            importBean.setPdt_name(row.getString("pdt_name"));
                            //importBean.setDel_qty(row.getInt("del_qty"));
                            importBean.setPdt_qty(row.getInt("pdt_qty"));
                            importBean.setPdt_real_qty(row.getInt("pdt_real_qty"));
                            //importBean.setStorage("");
                            //importBean.setStorage_zone("");
                           // importBean.setStorage_detail("");
                            importBean.setPdt_code(row.getString("pdt_code"));
                            importBean.setPdt_standard(row.getString("pdt_standard"));
                            importBean.setUnit(row.getString("unit"));
                            importBean.setUnit_price(row.getInt("unit_price"));
                            importBean.setSupply_price(row.getInt("supply_price"));
                            importBean.setVat(row.getInt("vat"));
                            //importBean.setOrder_seq(row.getString("order_seq"));



                            selectBoxList.add(importBean);

                        }
                        // Log.i("selectBox리스트요", selectBoxList.get(0).getPdt_name());
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.e("error",e.getMessage());
                    }

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            return receiveMsg;
        }
    }



    public class NotImportListSendConnection extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                selectBoxList = new ArrayList<>();
                String str;
                URL url = new URL("http://mespda.thethe.co.kr/import/updatematerialin");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "json_send="+strings[0];
                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();


                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
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

