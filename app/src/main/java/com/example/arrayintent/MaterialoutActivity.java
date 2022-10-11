/*
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

public class MaterialoutActivity extends AppCompatActivity implements View.OnClickListener {
    //ImportListViewAdapter importListViewAdapter;
    //WarehouseAdapter warehouseAdapter;
    WarehouseViewAdapter warehouseViewAdapter;
    ImportTradingListViewAdapter importTradingListViewAdapter;
    ArrayList<ImportBean> selectBoxList;
    ImportBean importBean;
    WarehouseViewAdapter.ListBtnClickListener clickListener;

    TextView trasactionBarcodeText;
    TextView orderCodeText;
    TextView delCompanyNameText;
    EditText orderCodeEditText;

    private RadioButton rg_btn1, rg_btn2;
    private RadioGroup radioGroup;

    Button QtyBtn;

    ImageView orderCodeBtn;
    ImageView importListSendBtn;
    ImageView importListResetBtn;
    ImageView back;

    ListView pdtListview1;
    SwipeRefreshLayout swipe;
    String[] action_value_buf = new String[]{ScanManager.ACTION_DECODE, ScanManager.BARCODE_STRING_TAG};
    private ScanManager mScanManager;
    int[] idbuf = new int[]{PropertyID.WEDGE_INTENT_ACTION_NAME, PropertyID.WEDGE_INTENT_DATA_STRING_TAG};
    int[] idmodebuf = new int[]{PropertyID.WEDGE_KEYBOARD_ENABLE, PropertyID.TRIGGERING_MODES};
    int[] idmode;
    String result;

    //추가 부분
    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            byte[] barcode = intent.getByteArrayExtra(ScanManager.DECODE_DATA_TAG);
            int barcodelen = intent.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0);
            byte temp = intent.getByteExtra(ScanManager.BARCODE_TYPE_TAG, (byte) 0);
            result = intent.getStringExtra(action_value_buf[1]);

           */
/* if(barcodelen != 0)
                barcodeStr = new String(barcode, 0, barcodelen);
            else
                barcodeStr = intent.getStringExtra("barcode_string");*//*

            //Log.w("result 값은 : ",result);
            if(result != null) {
                try {
                    ImportListConnection importListConnection = new ImportListConnection();
                    String Json_result=importListConnection.execute(result).get();
                    Log.w("Json", Json_result);

                    trasactionBarcodeText.setText(selectBoxList.get(0).getFilename());
                    orderCodeText.setText(selectBoxList.get(0).getOrder_code());
                    delCompanyNameText.setText(selectBoxList.get(0).getSupply_company_name());

                    //수정전
//                    importListViewAdapter = new ImportListViewAdapter(context,R.layout.import_listview_list, selectBoxList, importBean);
//                    pdtListview = (ListView) findViewById(R.id.pdtListview);
//                    pdtListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//                    pdtListview.setAdapter(importListViewAdapter);

                    // 수정후
                    warehouseViewAdapter = new WarehouseViewAdapter(context,R.layout.inwarehouse_listview_list, selectBoxList, importBean, clickListener);
                    pdtListview1 = (ListView) findViewById(R.id.pdtListview);
                    pdtListview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    pdtListview1.setAdapter(warehouseViewAdapter);

                } catch (Exception e) {
                    Log.e("importconfirmerror",e.getMessage());
                    Toast.makeText(context, "QR코드가 맞지 않거나 서버 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //super.onCreate:상위 클래스의 onCreate 메소드를 먼저 호출하여 먼저 실행 되게 하고 오버라이드된 메소드를 처리 한다는 의미
        setContentView(R.layout.activity_warehouse_in);


        mScanManager = new ScanManager();
        mScanManager.openScanner();
        action_value_buf = mScanManager.getParameterString(idbuf);
        idmode = mScanManager.getParameterInts(idmodebuf);
        idmode[0] = 0;
        mScanManager.setParameterInts(idmodebuf, idmode);

        ListView pdtListview1 ;
        WarehouseViewAdapter warehouseViewAdapter;
        //ArrayList<ListViewBtnItem> items = new ArrayList<ListViewBtnItem>() ;

        // items 로드.
        //loadItemsFromDB(items) ;

        // Adapter 생성
        warehouseViewAdapter = new WarehouseViewAdapter(this,R.layout.inwarehouse_listview_list, selectBoxList, importBean, clickListener);
        pdtListview1 = (ListView) findViewById(R.id.pdtListview1);
        pdtListview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        pdtListview1.setAdapter(warehouseViewAdapter);

        // 리스트뷰 참조 및 Adapter달기
        pdtListview1 = (ListView) findViewById(R.id.pdtListview1);
        pdtListview1.setAdapter(warehouseViewAdapter);


       */
/* //라디오 버튼 설정
        rg_btn1 = (RadioButton) findViewById(R.id.rg_btn1);
        rg_btn2 = (RadioButton) findViewById(R.id.rg_btn2);
        rg_btn1.setOnClickListener(radioButtonClickListener);
        rg_btn2.setOnClickListener(radioButtonClickListener);

        //라디오 그룹 설정
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);*//*


        trasactionBarcodeText = findViewById(R.id.trasactionBarcodeText);
        orderCodeText = findViewById(R.id.orderCodeText);
        delCompanyNameText = findViewById(R.id.delCompanyNameText);
        importListSendBtn = findViewById(R.id.importListSendBtn);
        importListResetBtn = findViewById(R.id.importListResetBtn);
        back = findViewById(R.id.back);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        orderCodeEditText =findViewById(R.id.orderCodeEditText);
        orderCodeBtn = findViewById(R.id.orderCodeBtn);


        importListSendBtn.setOnClickListener(this);
        importListResetBtn.setOnClickListener(this);
        back.setOnClickListener(this);
        orderCodeBtn.setOnClickListener(this);
        //QtyBtn.setOnClickListener(this);
        //Button QtyBtn = (Button) findViewById(R.id.QtyBtn);

       */
/* QtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                ImportDialog customDialog = new ImportDialog(WarehouseActivity.this);
                // 커스텀 다이얼로그를 호출한다.
                customDialog.onCreate();
            }
        });*//*

        // 당겨서 새로고침
     */
/*   swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);

                swipe.setRefreshing(false);
            }
        });*//*

       */
/* findViewById(R.id.QtyBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImportDialog dialog = new ImportDialog(WarehouseActivity.this);
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.copyFrom(dialog.getWindow().getAttributes());
                params.width = (int) (getApplicationContext().getResources().getDisplayMetrics().widthPixels * 0.7f);
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                dialog.show();

            }
        });*//*



    }



    //라디오 버튼 클릭 리스너
*/
/*
    RadioButton.OnClickListener radioButtonClickListener = new RadioButton.OnClickListener(){
        @Override public void onClick(View view)
        { Toast.makeText(WarehouseActivity.this, "rg_btn1 : "+rg_btn1.isChecked() + "rg_btn2 : " +rg_btn2.isChecked() , Toast.LENGTH_SHORT).show();
        }
    };
    //라디오 그룹 클릭 리스너
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.rg_btn1){ Toast.makeText(WarehouseActivity.this, "라디오 그룹 버튼1 눌렸습니다.", Toast.LENGTH_SHORT).show(); }
            else if(i == R.id.rg_btn2){ Toast.makeText(WarehouseActivity.this, "라디오 그룹 버튼2 눌렸습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };
*//*


   */
/* @Override
    public void OnC(int position){
        Toast.makeText(this, Integer.toString(position+1), Toast.LENGTH_SHORT).show();
    }*//*


    @Override
    protected void onResume() {
        super.onResume();

        //추가
        IntentFilter filter = new IntentFilter();
        action_value_buf = mScanManager.getParameterString(idbuf);
        filter.addAction(action_value_buf[0]);
        //registerReceiver(mScanReceiver, filter);
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
                intent = new Intent(MaterialoutActivity.this, newMainActivity.class);
                finish();
                startActivity(intent);
                break;

            case R.id.orderCodeBtn :
                if(!orderCodeEditText.getText().toString().equals("")){
                    try{

                        ImportListConnection2 importListConnection2 = new ImportListConnection2();
                        String Json_result=importListConnection2.execute(orderCodeEditText.getText().toString()).get();
                        Log.w("Json", Json_result);

                        trasactionBarcodeText.setText(selectBoxList.get(0).getFilename());
                        orderCodeText.setText(selectBoxList.get(0).getOrder_code());
                        delCompanyNameText.setText(selectBoxList.get(0).getSupply_company_name());

                        //수정전
//                    importListViewAdapter = new ImportListViewAdapter(context,R.layout.import_listview_list, selectBoxList, importBean);
//                    pdtListview1 = (ListView) findViewById(R.id.pdtListview1);
//                    pdtListview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//                    pdtListview1.setAdapter(importListViewAdapter);

                        // 수정후
                        warehouseViewAdapter = new WarehouseViewAdapter(getApplicationContext(),R.layout.inwarehouse_listview_list, selectBoxList, importBean, clickListener);
                        pdtListview1 = (ListView) findViewById(R.id.pdtListview1);
                        pdtListview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        pdtListview1.setAdapter(warehouseViewAdapter);




                    }catch (Exception e){

                    }

                }else{
                    Toast.makeText(this, "발주번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.importListSendBtn:
              */
/*  if (v.getId() == R.id.rg_btn1){
                    //jsonSend();
                    intent = new Intent(WarehouseActivity.this, NotImportConfirmActivity.class);
                    finish();
                    startActivity(intent);
                }else if(v.getId() == R.id.rg_btn2){
                    //jsonSend();
                    intent = new Intent(WarehouseActivity.this, ImportConfirmActivity.class);
                    finish();
                    startActivity(intent);

                }*//*

                jsonSend();
                intent = new Intent(MaterialoutActivity.this, NotImportConfirmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

                break;


                */
/*intent = getIntent();
                finish();
                startActivity(intent);

                Toast.makeText(this, "입고가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                break;*//*

            */
/*case R.id.QtyBtn:
               *//*
*/
/* ImportDialog palletDialog = new ImportDialog(WarehouseActivity.this);
                WindowManager.LayoutParams params = palletDialog.getWindow().getAttributes();
                params.copyFrom(palletDialog.getWindow().getAttributes());
                params.width = (int) (getApplicationContext().getResources().getDisplayMetrics().widthPixels * 0.7f);
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                palletDialog.show();*//*
*/
/*

                break;*//*

            */
/*case R.id.QtyBtn:
                // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                ImportDialog customDialog = new ImportDialog(WarehouseActivity.this);
                // 커스텀 다이얼로그를 호출한다.
                customDialog.onCreate();

                break;*//*


        }



    }


    //------------------------------스캔---------------------------------------------------
    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false); //자동 화면 가로,세로 변횐
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);  //모든타입 바코드
        integrator.setPrompt("Scanning");
        integrator.setRequestCode(1);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult trading_result = IntentIntegrator.parseActivityResult(resultCode, data);
        String trading_data = trading_result.getContents();  //바코드데이터를 BaData에 담음
        SharedPreferences importpref = getSharedPreferences("pallet", MODE_PRIVATE);
        SharedPreferences.Editor importeditor = importpref.edit();
//        List<ImportBean> importBean = new ArrayList<ImportBean>();


        if (trading_result != null) {
            if (trading_data != null) {
                //파렛트 스캔 시 data, num 둘 다 불러오기
                if (requestCode == 1) {
                    importeditor.putString("import_barcode", trading_data);
                    importeditor.commit();

                    String import_barcode_scan = importpref.getString("import_barcode", null);

                    try {
                        ImportListConnection importListConnection = new ImportListConnection();
                        String Json_result=importListConnection.execute(import_barcode_scan).get();
                        Log.w("Json", Json_result);

                        trasactionBarcodeText.setText(selectBoxList.get(0).getFilename());
                        orderCodeText.setText(selectBoxList.get(0).getOrder_code());
                        delCompanyNameText.setText(selectBoxList.get(0).getSupply_company_name());

                        warehouseViewAdapter = new WarehouseViewAdapter(getApplicationContext(),R.layout.inwarehouse_listview_list, selectBoxList, importBean, clickListener);
                        pdtListview1 = (ListView) findViewById(R.id.pdtListview1);
                        pdtListview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        pdtListview1.setAdapter(warehouseViewAdapter);

                    } catch (Exception e) {
                        Log.e("importconfirmerror",e.getMessage());
                        Toast.makeText(this, "QR코드가 맞지 않거나 서버 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void jsonSend() {
        JSONObject JsonSend = new JSONObject();

        try{
            JSONArray jsonSendArray = new JSONArray();
            for(int i=0; i<selectBoxList.size(); i++){
                ImportBean sendImportBean = selectBoxList.get(i);
                JSONObject row = new JSONObject();
                row.put("filecode", sendImportBean.getFilename());
                row.put("order_code", sendImportBean.getOrder_code());
                row.put("supply_company_code", sendImportBean.getSupply_company_name());
                row.put("no", sendImportBean.getNO());
                row.put("pdt_name", sendImportBean.getPdt_name());
                row.put("del_qty", sendImportBean.getDel_qty());
                row.put("order_qty", sendImportBean.getOrder_qty());
                row.put("real_qty",sendImportBean.getReal_qty());
                row.put("pdt_code", sendImportBean.getPdt_code());
                row.put("pdt_standard", sendImportBean.getPdt_standard());
                row.put("unit", sendImportBean.getUnit());
                row.put("unit_price", sendImportBean.getUnit_price());
                row.put("supply_price", sendImportBean.getSupply_price());
                row.put("vat", sendImportBean.getVat());
                row.put("order_date", sendImportBean.getOrder_date());
                row.put("close_date", sendImportBean.getClose_date());
                row.put("business_no", sendImportBean.getBusiness_no());
                row.put("supply_manager_name", sendImportBean.getSupply_manager_name());
                row.put("storageName1",sendImportBean.getStorageName1());
                row.put("storageName2",sendImportBean.getStorageName2());
                row.put("storageName3",sendImportBean.getStorageName3());
                row.put("bigo",sendImportBean.getBigo());
                row.put("order_addr",sendImportBean.getOrder_addr());
                row.put("supply_amount",sendImportBean.getSupply_amount());
                row.put("tax_amount",sendImportBean.getTax_amount());
                row.put("total_amount",sendImportBean.getTotal_amount());
                row.put("order_seq",sendImportBean.getOrder_seq());



                jsonSendArray.put(row);
            }
            JsonSend.put("importpdtsendlist",jsonSendArray);
            String JsonSendString = JsonSend.toString();

            try {

                ImportListSendConnection importListSendConnection = new ImportListSendConnection();
                String send_result = importListSendConnection.execute(JsonSendString).get();
                Log.w("Json전송 결과", send_result);

            }catch (Exception e){
                Toast.makeText(this, "서버 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e){

        }
    }

    public class ImportListConnection extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                selectBoxList = new ArrayList<>();
                String str;
                URL url = new URL("http://192.168.0.26:8080/MES_PDA/import/importpdtlist.do?" + "import_barcode_scan="+strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "import_barcode_scan="+strings[0];
//                osw.write(sendMsg.replace(": ", ""));
                osw.flush();
                Log.i("import_barcode_scan=",strings[0]);
                String tmp2 = "";
                for(String strss : strings){
                    tmp2 += strss + " ";

                }
                Log.i("strings : ", tmp2);
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("receiveMsg : ", receiveMsg);
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
                            importBean.setFilename(row.getString("filecode"));
                            importBean.setOrder_code(row.getString("order_code"));
                            importBean.setSupply_company_name(row.getString("supply_company_code"));
                            importBean.setNO(row.getInt("no"));
                            importBean.setPdt_name(row.getString("pdt_name"));
                            importBean.setDel_qty(row.getInt("del_qty"));
                            importBean.setOrder_qty(row.getInt("order_qty"));
                            importBean.setStorageName1("");
                            importBean.setStorageName2("");
                            importBean.setStorageName3("");
                            importBean.setPdt_code(row.getString("pdt_code"));
                            importBean.setPdt_standard(row.getString("pdt_standard"));
                            importBean.setUnit(row.getString("unit"));
                            importBean.setUnit_price(row.getInt("unit_price"));
                            importBean.setSupply_price(row.getInt("supply_price"));
                            importBean.setVat(row.getInt("vat"));
                            importBean.setOrder_date(row.getString("order_date"));
                            importBean.setClose_date(row.getString("close_date"));
                            importBean.setBusiness_no(row.getString("business_no"));
                            importBean.setSupply_manager_name(row.getString("supply_manager_name"));
                            importBean.setBigo(row.getString("bigo"));
                            importBean.setOrder_addr(row.getString("order_addr"));
                            importBean.setSupply_amount(row.getInt("supply_amount"));
                            importBean.setTax_amount(row.getInt("tax_amount"));
                            importBean.setTotal_amount(row.getInt("total_amount"));
                            importBean.setOrder_seq(row.getString("order_seq"));



                            selectBoxList.add(importBean);

                        }
                        Log.i("selectBox리스트요", selectBoxList.get(0).getPdt_name());
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
            } catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }

    public class ImportListConnection2 extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                selectBoxList = new ArrayList<>();
                String str;
                URL url = new URL("http://192.168.0.26:8080/MES_PDA/import/importpdtlist2.do");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "import_order_code="+strings[0];
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
                            importBean.setFilename(row.getString("filecode"));
                            importBean.setOrder_code(row.getString("order_code"));
                            importBean.setSupply_company_name(row.getString("supply_company_code"));
                            importBean.setNO(row.getInt("no"));
                            importBean.setPdt_name(row.getString("pdt_name"));
                            importBean.setDel_qty(row.getInt("del_qty"));
                            importBean.setOrder_qty(row.getInt("order_qty"));
                            importBean.setStorageName1("");
                            importBean.setStorageName2("");
                            importBean.setStorageName3("");
                            importBean.setPdt_code(row.getString("pdt_code"));
                            importBean.setPdt_standard(row.getString("pdt_standard"));
                            importBean.setUnit(row.getString("unit"));
                            importBean.setUnit_price(row.getInt("unit_price"));
                            importBean.setSupply_price(row.getInt("supply_price"));
                            importBean.setVat(row.getInt("vat"));
                            importBean.setOrder_date(row.getString("order_date"));
                            importBean.setClose_date(row.getString("close_date"));
                            importBean.setBusiness_no(row.getString("business_no"));
                            importBean.setSupply_manager_name(row.getString("supply_manager_name"));
                            importBean.setBigo(row.getString("bigo"));
                            importBean.setOrder_addr(row.getString("order_addr"));
                            //importBean.setSupply_amount(row.getInt("supply_amount"));
                            importBean.setSupply_amount(row.getInt("supply_amount"));
                            importBean.setTax_amount(row.getInt("tax_amount"));
                            importBean.setTotal_amount(row.getInt("total_amount"));
                            importBean.setOrder_seq(row.getString("order_seq"));



                            selectBoxList.add(importBean);

                        }
                        Log.i("selectBox리스트요", selectBoxList.get(0).getPdt_name());
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


    public class ImportListSendConnection extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                selectBoxList = new ArrayList<>();
                String str;
                URL url = new URL("http://192.168.0.26:8080/MES_PDA/import/insertmaterialin");
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
*/
