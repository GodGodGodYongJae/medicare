/*
package com.example.arrayintent;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.device.ScanManager;
import android.device.scanner.configuration.PropertyID;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.IdRes;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseImport extends AppCompatActivity implements View.OnClickListener {

    public static Context context;

    ImportListViewAdapter importListViewAdapter;
    //WarehouseAdapter warehouseAdapter;
    WarehouseViewAdapter warehouseViewAdapter;
    ImportTradingListViewAdapter importTradingListViewAdapter;
    ArrayList<ImportBean> selectBoxList;
    ImportBean importBean;
    //WarehouseViewAdapter.ListBtnClickListener clickListener;

    private ImportDialog mImportDialog;
    //private Context context;
    private ImageView importListResetBtn;
    private ImageView importListSendBtn;

    TextView trasactionBarcodeText;
    TextView orderCodeText;
    TextView delCompanyNameText;
    EditText orderCodeEditText;

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;

    private RadioButton rg_btn1, rg_btn2;
    private RadioGroup radioGroup;

    Button QtyBtn;

    ImageView orderCodeBtn;
    //ImageView importListSendBtn;
    // importListResetBtn;
    ImageView back;

    ListView pdtListview;
    SwipeRefreshLayout swipe;
    String[] action_value_buf = new String[]{ScanManager.ACTION_DECODE, ScanManager.BARCODE_STRING_TAG};
    private ScanManager mScanManager;
    //private Context context;
    //Dialog importDialog;
    int[] idbuf = new int[]{PropertyID.WEDGE_INTENT_ACTION_NAME, PropertyID.WEDGE_INTENT_DATA_STRING_TAG};
    int[] idmodebuf = new int[]{PropertyID.WEDGE_KEYBOARD_ENABLE, PropertyID.TRIGGERING_MODES};
    int[] idmode;
    String result;

    ArrayAdapter<String> name1Adapter;
    ArrayAdapter<String> name2Adapter;
    ArrayAdapter<String> name3Adapter;
    List<String> name1List = new ArrayList<String>();
    List<String> name2List = new ArrayList<String>();
    List<String> name3List = new ArrayList<String>();
    String[] splitNum1, splitNum2, splitNum3;
    Map<String, String> nameMap = new HashMap<>();
    ArrayList<ImportBean> listViewArr;
    LayoutInflater inflater;
    private int position;

    String storageName1;
    String storageName2;
    String storageName3;

    //추가 부분
    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            byte[] barcode = intent.getByteArrayExtra(ScanManager.DECODE_DATA_TAG);
            int barcodelen = intent.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0);
            byte temp = intent.getByteExtra(ScanManager.BARCODE_TYPE_TAG, (byte) 0);
            result = intent.getStringExtra(action_value_buf[1]);

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
                    importListViewAdapter = new ImportListViewAdapter(context,R.layout.inwarehouse_listview_list, selectBoxList, importBean);
                    pdtListview = (ListView) findViewById(R.id.pdtListview);
                    pdtListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    pdtListview.setAdapter(importListViewAdapter);

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
        setContentView(R.layout.activity_warehouseimport);

        setLayout();

        mScanManager = new ScanManager();
        mScanManager.openScanner();
        action_value_buf = mScanManager.getParameterString(idbuf);
        idmode = mScanManager.getParameterInts(idmodebuf);
        idmode[0] = 0;
        mScanManager.setParameterInts(idmodebuf, idmode);

        ListView pdtListview ;
        ImportListViewAdapter importListViewAdapter;
        //ArrayList<ListViewBtnItem> items = new ArrayList<ListViewBtnItem>() ;

        // Adapter 생성
        importListViewAdapter = new ImportListViewAdapter(this,R.layout.inwarehouse_listview_list, selectBoxList, importBean);
        pdtListview = (ListView) findViewById(R.id.pdtListview);
        pdtListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        pdtListview.setAdapter(importListViewAdapter);

        // 리스트뷰 참조 및 Adapter달기
        pdtListview = (ListView) findViewById(R.id.pdtListview);
        pdtListview.setAdapter(importListViewAdapter);

        pdtListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText( getApplicationContext(), "ITEM CLICK = " + position, Toast.LENGTH_SHORT ).show();
            }
        });


        trasactionBarcodeText = findViewById(R.id.trasactionBarcodeText);
        orderCodeText = findViewById(R.id.orderCodeText);
        delCompanyNameText = findViewById(R.id.delCompanyNameText);
        importListSendBtn = findViewById(R.id.importListSendBtn);
        importListResetBtn = findViewById(R.id.importListResetBtn);
        back = findViewById(R.id.back);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        orderCodeEditText =findViewById(R.id.orderCodeEditText);
        orderCodeBtn = findViewById(R.id.orderCodeBtn);
        QtyBtn = findViewById(R.id.QtyBtn);

        //QtyBtn.setOnClickListener(this);
        importListSendBtn.setOnClickListener(this);
        importListResetBtn.setOnClickListener(this);
        back.setOnClickListener(this);
        orderCodeBtn.setOnClickListener(this);

        //QtyBtn.setOnClickListener(this);

        importListViewAdapter.notifyDataSetChanged();

        spinner1 = findViewById(R.id.storeNameSpinner);
        spinner2 = findViewById(R.id.storeDetail1Spinner);
        spinner3 = findViewById(R.id.storeDedatil2Spinner);

        ArrayAdapter spAdapter1 = ArrayAdapter.createFromResource(this, R.array.spinnerArray1, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter spAdapter2 = ArrayAdapter.createFromResource(this, R.array.spinnerArray2, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter spAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinnerArray3, android.R.layout.simple_spinner_dropdown_item);

        spAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(spAdapter1);
        spinner2.setAdapter(spAdapter2);
        spinner3.setAdapter(spAdapter3);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void setLayout(){
        pdtListview = (ListView) findViewById(R.id.pdtListview); }

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
                intent = new Intent(WarehouseImport.this, newMainActivity.class);
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
                        importListViewAdapter = new ImportListViewAdapter(getApplicationContext(),R.layout.inwarehouse_listview_list, selectBoxList, importBean);
                        pdtListview = (ListView) findViewById(R.id.pdtListview);
                        pdtListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        pdtListview.setAdapter(importListViewAdapter);
                    }catch (Exception e){

                    }

                }else{
                    Toast.makeText(this, "발주번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.importListSendBtn:
                intent = new Intent(WarehouseImport.this, WarehouseImport.class);
                finish();
                startActivity(intent);

                jsonSend();
                intent = new Intent(WarehouseImport.this, newMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

                break;

            */
/*case R.id.QtyBtn:
                intent = new Intent(WarehouseImport.this, ImportDialog.class );
                finish();
                startActivity(intent);


                intent = new Intent(WarehouseImport.this, TesListViewActivity.class);
                finish();
                startActivity(intent);
                break;


                intent = getIntent();
                finish();
                startActivity(intent);

                Toast.makeText(this, "입고가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.QtyBtn:
                // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                mImportDialog = new ImportDialog(WarehouseImport.this);
                mImportDialog.show();

                importListViewAdapter = new ImportListViewAdapter(getApplicationContext(),R.layout.inwarehouse_listview_list, selectBoxList, importBean);
                pdtListview1 = (ListView) findViewById(R.id.pdtListview1);
                pdtListview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                pdtListview1.setAdapter(importListViewAdapter);

                break;

            // 버튼: 커스텀 다이얼로그 띄우기
case R.id.QtyBtn:
                // 버튼: 커스텀 다이얼로그 띄우기
                showDefaultDialog(); // 아래 showDialog01() 함수 호출

            break;*//*



        }
    }

*/
/*public void showDefaultDialog() {
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        //Dialog importDialog = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        //ImportDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        importDialog.setContentView(R.layout.importstoredialog);

        // 커스텀 다이얼로그를 노출한다.

        LinearLayout importstoredialog = (LinearLayout) View.inflate(context, R.layout.importstoredialog, null);
        TextView boxDialogTextView4 = (TextView) importstoredialog.findViewById(R.id.boxDialogTextView4);
        MaterialSpinner storeNameSpinner = (MaterialSpinner) importstoredialog.findViewById(R.id.storeNameSpinner);
        MaterialSpinner storeDetail1Spinner = (MaterialSpinner) importstoredialog.findViewById(R.id.storeDetail1Spinner);
        MaterialSpinner storeDetail2Spinner = (MaterialSpinner) importstoredialog.findViewById(R.id.storeDedatil2Spinner);
        EditText importEditReal_Qty = (EditText) importstoredialog.findViewById(R.id.importEditReal_Qty);

 for(int i = 0; i < listViewArr.size(); i++){
            System.err.println(i+" : " + listViewArr.get(i).getOrder_qty());
        }
        String order_qty = Integer.toString(listViewArr.get(0).getOrder_qty());

        if (!listViewArr.get(position).getStorageName1().equals("")) {
            boxDialogTextView4.setText("(창고 : " + listViewArr.get(position).getStorageName1() + "-" + listViewArr.get(position).getStorageName2() +
                    "-" + listViewArr.get(position).getStorageName3() + ")");
        }
        name1List.clear();
        name2List.clear();

        name3List.clear();

        name1Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name1List);
        storeNameSpinner.setAdapter(name1Adapter);
        name2Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name2List);
        storeDetail1Spinner.setAdapter(name2Adapter);
        name3Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name3List);
        storeDetail2Spinner.setAdapter(name3Adapter);
        //실수량

        //창고이름
        try {
            StorageName1CustomTask storageName1CustomTask = new StorageName1CustomTask();
            String name1_result = storageName1CustomTask.execute().get();
            Log.w("name1 리스트", name1_result);

            name1List.clear();
            name2List.clear();
            name3List.clear();
            splitNum1 = name1_result.split(",  ");
            if (!name1List.contains("창고명")) {
                name1List.add("창고명");
            }
            for (int i = 0; i < splitNum1.length; i++) {
                if (!name1List.contains(splitNum1[i])) {
                    name1List.add(splitNum1[i]);
                }
            }

            name1Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name1List);
            storeNameSpinner.setAdapter(name1Adapter);

        } catch (Exception e) {

        }

        storeNameSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                storageName1 = item.toString();
                if (storageName1 != null) {

                    //창고 디테일1
                    try {
                        StorageName2CustomTask storageName2CustomTask = new StorageName2CustomTask();
                        String name2_result = storageName2CustomTask.execute(storageName1).get();
                        Log.w("name2 리스트", name2_result);
                        name2List.clear();
                        name3List.clear();
                        splitNum2 = name2_result.split(",  ");
                        if (!name2List.contains("구분1")) {
                            name2List.add("구분1");
                        }


                        for (int i = 0; i < splitNum2.length; i++) {
                            if (!name2List.contains(splitNum2[i])) {
                                name2List.add(splitNum2[i]);
                            }
                        }

                        name2Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name2List);
                        storeDetail1Spinner.setAdapter(name2Adapter);


                    } catch (Exception e) {

                    }
                    storeDetail2Spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                            nameMap.put("storage_detail2"+position,item.toString());
                            storageName3 = item.toString();
                        }
                    });

                }
            }

        });
        //ImportDialog.show();
        importDialog.show();
    }*//*




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

                        importListViewAdapter = new ImportListViewAdapter(getApplicationContext(),R.layout.inwarehouse_listview_list, selectBoxList, importBean);
                        pdtListview = (ListView) findViewById(R.id.pdtListview);
                        pdtListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        pdtListview.setAdapter(importListViewAdapter);

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
                row.put("pdt_qty", sendImportBean.getPdt_qty());
                row.put("pdt_real_qty",sendImportBean.getPdt_real_qty());
                row.put("pdt_code", sendImportBean.getPdt_code());
                row.put("pdt_standard", sendImportBean.getPdt_standard());
                row.put("unit", sendImportBean.getUnit());
                row.put("unit_price", sendImportBean.getUnit_price());
                row.put("supply_price", sendImportBean.getSupply_price());
                row.put("vat", sendImportBean.getVat());
                row.put("order_date", sendImportBean.getOrder_date());
                row.put("close_date", sendImportBean.getClose_date());
                //row.put("business_no", sendImportBean.getBusiness_no());
                row.put("supply_manager_name", sendImportBean.getSupply_manager_name());
                row.put("storage",sendImportBean.getStorage());
                row.put("storage_zone",sendImportBean.getStorage_zone());
                row.put("storage_detail",sendImportBean.getStorage_detail());
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
                URL url = new URL("http://mespda.thethe.co.kr/import/importpdtlist.do?" + "import_barcode_scan="+strings[0]);
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
                            importBean.setPdt_qty(row.getInt("pdt_qty"));
                            importBean.setStorage("");
                            importBean.setStorage_zone("");
                            importBean.setStorage_detail("");
                            importBean.setPdt_code(row.getString("pdt_code"));
                            importBean.setPdt_standard(row.getString("pdt_standard"));
                            importBean.setUnit(row.getString("unit"));
                            importBean.setUnit_price(row.getInt("unit_price"));
                            importBean.setSupply_price(row.getInt("supply_price"));
                            importBean.setVat(row.getInt("vat"));
                            importBean.setOrder_date(row.getString("order_date"));
                            importBean.setClose_date(row.getString("close_date"));
                            //importBean.setBusiness_no(row.getString("business_no"));
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
                URL url = new URL("http://mespda.thethe.co.kr/import/importpdtlist2.do");
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
                            importBean.setPdt_qty(row.getInt("pdt_qty"));
                            importBean.setStorage("");
                            importBean.setStorage_zone("");
                            importBean.setStorage_detail("");
                            importBean.setPdt_code(row.getString("pdt_code"));
                            importBean.setPdt_standard(row.getString("pdt_standard"));
                            importBean.setUnit(row.getString("unit"));
                            importBean.setUnit_price(row.getInt("unit_price"));
                            importBean.setSupply_price(row.getInt("supply_price"));
                            importBean.setVat(row.getInt("vat"));
                            importBean.setOrder_date(row.getString("order_date"));
                            importBean.setClose_date(row.getString("close_date"));
                           // importBean.setBusiness_no(row.getString("business_no"));
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
                URL url = new URL("http://mespda.thethe.co.kr/import/insertmaterialin");
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
