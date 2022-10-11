package com.example.arrayintent;
import android.content.BroadcastReceiver;
import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
import android.device.ScanManager;
import android.device.scanner.configuration.PropertyID;
import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.FragmentTransaction;
        import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

        import com.google.zxing.integration.android.IntentIntegrator;
        import com.google.zxing.integration.android.IntentResult;
        import com.jaredrummler.materialspinner.MaterialSpinner;

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
        import java.util.Collections;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class newProductionPalletActivity extends AppCompatActivity implements View.OnClickListener {

    private UserDatabaseHelper userDatabaseHelper;
    public static final String TABLE_NAME = "test2";
    SQLiteDatabase database;

    TextView palletBarcode;
    TextView boxDialogTextView;
    TextView palletSelectText;
    ImageView palletSelectBtn;
    ImageView palletCancelBtn;
    ImageView previousBtn;
    ImageView nextBtn;
    MaterialSpinner palletNumSpinner;
    LinearLayout palletLinear;
    String pallet_num_input;
    String pallet_data_scan;

    String pallet_datanum_result;
    String palletSpinnerNum;
    ArrayAdapter<String> palletNumAdapter;
    String[] action_value_buf = new String[]{ScanManager.ACTION_DECODE, ScanManager.BARCODE_STRING_TAG};
    private ScanManager mScanManager;
    int[] idbuf = new int[]{PropertyID.WEDGE_INTENT_ACTION_NAME, PropertyID.WEDGE_INTENT_DATA_STRING_TAG};
    int[] idmodebuf = new int[]{PropertyID.WEDGE_KEYBOARD_ENABLE, PropertyID.TRIGGERING_MODES};
    int[] idmode;
    String result;
    String[] palletNum;
    String splitPalletNum;

    //추가 부분
    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            byte[] barcode = intent.getByteArrayExtra(ScanManager.DECODE_DATA_TAG);
            int barcodelen = intent.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0);
            byte temp = intent.getByteExtra(ScanManager.BARCODE_TYPE_TAG, (byte) 0);
            result = intent.getStringExtra(action_value_buf[1]);
            SharedPreferences palletpref = getSharedPreferences("pallet", MODE_PRIVATE);
            SharedPreferences.Editor palleteditor = palletpref.edit();


            Log.w("result 값은 : ",result);
            if(result != null) {
                palleteditor.putString("pallet_barcode", result);
                palleteditor.putString("pallet_num", null);
                palleteditor.commit();

                String pallet_data_scan = palletpref.getString("pallet_barcode", null);
                String pallet_num_input = palletpref.getString("pallet_num", null);

                try {
                    GetPalletNumCustomTask getPalletNumCustomTask = new GetPalletNumCustomTask();
                    pallet_datanum_result = getPalletNumCustomTask.execute(pallet_data_scan, pallet_num_input).get();
                    palleteditor.putString("pallet_datanum_result", pallet_datanum_result);
                    palleteditor.commit();
                    Log.w("data, num받은 값", pallet_datanum_result);
                    // "x번 / 12345648aa9 식으로 나오는 거에서 x번만 떼어줌"
                    palletBarcode.setText(palletpref.getString("pallet_datanum_result", null));
                    if(!palletpref.getString("pallet_datanum_result",null).equals("번 / ")) {
                        palletNum = palletpref.getString("pallet_datanum_result", null).split("번 / ");
                        splitPalletNum = palletNum[0];
                        palletSelectText.setText("Pallet 번호 : " + splitPalletNum);
                    }else{
                        palletSelectText.setText("Pallet 번호 : " + "없음");
                    }
                    Log.w("splitnum",splitPalletNum);


                    //spinner 값 조정
                    palletNumSpinner.setSelectedIndex(getIndex(palletNumSpinner,palletNumAdapter, splitPalletNum));

                    Log.w("index 값", Integer.toString(getIndex(palletNumSpinner,palletNumAdapter ,splitPalletNum)));


                } catch (Exception e) {
                    Toast.makeText(context, "서버와 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                }


            }
        }

    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_pallet);
        SharedPreferences palletpref = getSharedPreferences("pallet", MODE_PRIVATE);
        SharedPreferences.Editor palleteditor = palletpref.edit();

        mScanManager = new ScanManager();
        mScanManager.openScanner();
        action_value_buf = mScanManager.getParameterString(idbuf);
        idmode = mScanManager.getParameterInts(idmodebuf);
        idmode[0] = 0;
        mScanManager.setParameterInts(idmodebuf, idmode);

        palletBarcode = findViewById(R.id.palletBarcode);
        palletCancelBtn = findViewById(R.id.palletCancelBtn);
        //palletSelectText = findViewById(R.id.palletSelectText);
        previousBtn = findViewById(R.id.previousBtn);
        nextBtn =findViewById(R.id.nextBtn);
        palletSelectBtn = findViewById(R.id.palletSelectBtn);
        //palletNumSpinner = (MaterialSpinner) findViewById(R.id.palletNumSpinner);
        palletLinear = (LinearLayout) View.inflate(newProductionPalletActivity.this, R.layout.palletnumdialog, null);
        boxDialogTextView = (TextView) palletLinear.findViewById(R.id.boxDialogTextView);

        palletSelectBtn.setOnClickListener(this);
        palletCancelBtn.setOnClickListener(this);
        previousBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        if(palletpref.getString("pallet_datanum_result",null)!=null) {
            if (!palletpref.getString("pallet_datanum_result", null).equals("번 / ")) {

                palletBarcode.setText(palletpref.getString("pallet_datanum_result", null));
                palletNum = palletpref.getString("pallet_datanum_result", null).split("번 / ");

                splitPalletNum = palletNum[0];
                palletSelectText.setText("Pallet 번호 : " + splitPalletNum);
            } else {
                palletSelectText.setText("Pallet 번호 : " + "없음");
            }
        }
        }






    // 동작하면서 새로고침이 됨
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences palletpref = getSharedPreferences("pallet", MODE_PRIVATE);
        String pallet_data_scan = palletpref.getString("pallet_barcode", null);
        String pallet_num_input = palletpref.getString("pallet_num", null);
        List<String> palletNumList = new ArrayList<String>();

        IntentFilter filter = new IntentFilter();
        action_value_buf = mScanManager.getParameterString(idbuf);
        filter.addAction(action_value_buf[0]);
        registerReceiver(mScanReceiver, filter);

        try {
            GetPalletList getPalletList = new GetPalletList();
            String pallet_numlist_result = getPalletList.execute(pallet_data_scan, pallet_num_input).get();
            Log.w("data, num받은 값", pallet_numlist_result);

            String[] splitNum = pallet_numlist_result.split(",  ");
            palletNumList.add("선택하기");
            for (int i = 0; i < splitNum.length; i++) {
                palletNumList.add(splitNum[i]);
            }

            palletNumAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, palletNumList);
            palletNumSpinner.setAdapter(palletNumAdapter);



        } catch (Exception e) {
            Toast.makeText(this, "서버와 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
        }

        palletNumSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                palletSpinnerNum = item.toString();

            }
        });


    }


    @Override
    public void onClick(View v) {
        SharedPreferences palletpref = getSharedPreferences("pallet", MODE_PRIVATE);
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor palleteditor = palletpref.edit();
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        switch (v.getId()) {

            case R.id.previousBtn:

                    Intent intent = new Intent(newProductionPalletActivity.this, newMainActivity.class);
                    finish();
                    startActivity(intent);
                break;

            //spinner 입력 버튼
            case R.id.nextBtn:
                mScanManager.closeScanner();
                intent = new Intent(newProductionPalletActivity.this, newMainActivity.class);
                //newProductionBoxActivity
                finish();
                startActivity(intent);

                break;

            case R.id.palletSelectBtn:
                if (palletSpinnerNum.equals("선택하기")) {
                    Toast.makeText(this, "번호를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    pallet_num_input = palletSpinnerNum;
                    palleteditor.putString("pallet_barcode", null);
                    palleteditor.putString("pallet_num", pallet_num_input);
                    palleteditor.commit();

                    pallet_data_scan = palletpref.getString("pallet_barcode", null);
                    pallet_num_input = palletpref.getString("pallet_num", null);
                    try {
                        GetPalletNumCustomTask getPalletNumCustomTask = new GetPalletNumCustomTask();
                        pallet_datanum_result = getPalletNumCustomTask.execute(pallet_data_scan, pallet_num_input).get();
                        palleteditor.putString("pallet_datanum_result", pallet_datanum_result);
                        palleteditor.commit();
                        Log.w("data, num받은 값", pallet_datanum_result);
                        palletBarcode.setText(palletpref.getString("pallet_datanum_result", null));
                        palletNum = palletpref.getString("pallet_datanum_result", null).split("번 / ");
                        splitPalletNum = palletNum[0];
                        palletSelectText.setText("Pallet 번호 : " + splitPalletNum);
                        palletNumSpinner.setSelectedIndex(0);


                    } catch (Exception e) {
                        Toast.makeText(this, "서버와 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                }


                break;
            case R.id.palletCancelBtn:
                palleteditor.putString("pallet_barcode", null);
                palleteditor.putString("pallet_num", null);
                palleteditor.putString("pallet_datanum_result", null);
                palleteditor.commit();
                intent = getIntent();
                finish();
                startActivity(intent);
        }

    }

    // 스캔 시 자동 스피너값 조정
    private int getIndex(MaterialSpinner spinner, ArrayAdapter<String> palletNumAdapter, String item){
        for (int i=0; i<palletNumAdapter.getCount(); i++){
            if(palletNumAdapter.getItem(i).toString().equalsIgnoreCase(item)){
                return i;
            }
        }
        return 0;
    }


}