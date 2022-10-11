package com.example.arrayintent;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ExportActivity  extends AppCompatActivity implements View.OnClickListener {

    ImageView exportPalletScanBtn;
    ImageView exportPalletNumBtn;
    ImageView exportPalletSelectBtn;
    ImageView exportSendBtn;
    ImageView exportResetBtn;

    TextView inputpalletBarcode;
    TextView exportDialogTextView;
    EditText palletNumEditText;
    MaterialSpinner ExportpalletNumSpinner;
    LinearLayout exportpalletdialog;

    private String days() {
        SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String today = day.format(cal.getTime());

        return today;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        exportPalletScanBtn = findViewById(R.id.exportPalletScanBtn);
        exportPalletNumBtn = findViewById(R.id.exportPalletNumBtn);
        exportPalletSelectBtn = findViewById(R.id.exportPalletSelectBtn);
        exportSendBtn = findViewById(R.id.exportSendBtn);
        exportResetBtn = findViewById(R.id.exportResetBtn);
        inputpalletBarcode = findViewById(R.id.inputpalletBarcode);
        palletNumEditText = findViewById(R.id.palletNumEditText);
        ExportpalletNumSpinner = findViewById(R.id.ExportpalletNumSpinner);
        exportpalletdialog = (LinearLayout) View.inflate(ExportActivity.this, R.layout.exportpalletdialog,null);
        exportDialogTextView = (TextView) exportpalletdialog.findViewById(R.id.exportDialogTextView);

        exportPalletScanBtn.setOnClickListener(this);
        exportPalletNumBtn.setOnClickListener(this);
        exportPalletSelectBtn.setOnClickListener(this);
        exportSendBtn.setOnClickListener(this);
        exportResetBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.exportPalletScanBtn:
                exportScanCode();
                break;
        }

        switch (v.getId()) {
            case R.id.exportPalletNumBtn:

                break;
        }

        switch (v.getId()) {
            case R.id.exportPalletSelectBtn:

                break;
        }

        switch (v.getId()) {
            case R.id.exportSendBtn:

                break;
        }

        switch (v.getId()) {
            case R.id.exportResetBtn:

                break;
        }
    }


    private void exportScanCode() {
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
        IntentResult pallet_result = IntentIntegrator.parseActivityResult(resultCode, data);
        String pallet_data = pallet_result.getContents();  //바코드데이터를 BaData에 담음
        String pallet_datanum_result;

        if (pallet_result != null) {
            if (pallet_data != null) {
                //파렛트 스캔 시 data, num 둘 다 불러오기
                if (requestCode == 1) {
                    try {
                        GetPalletNumCustomTask getPalletNumCustomTask = new GetPalletNumCustomTask();
                        pallet_datanum_result = getPalletNumCustomTask.execute(pallet_data, null).get();

                        //--------------다이얼로그
                        AlertDialog.Builder palletDialog = new AlertDialog.Builder(ExportActivity.this);
                        exportDialogTextView.setText(pallet_datanum_result+" 바코드의 파렛트를 출고 하시겠습니까?");
                        if(exportpalletdialog.getParent() != null) {
                            ((ViewGroup) exportpalletdialog.getParent()).removeView(exportpalletdialog);
                            palletDialog.setView(exportpalletdialog);
                        }else {
                            palletDialog.setView(exportpalletdialog);
                        }

                        palletDialog.setPositiveButton("출고 확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast myToast = Toast.makeText(ExportActivity.this, pallet_datanum_result+" 바코드의 파렛트를 출고 완료 되었습니다.", Toast.LENGTH_SHORT);
                                myToast.show();
                                dialog.dismiss();
                                exportScanCode();


                            }
                        });
                        palletDialog.setNegativeButton("다시 스캔", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                        palletDialog.show();

                        //-----------다이얼로그

                    }catch (Exception e){
                        Toast.makeText(this,"서버와 연결을 확인해주세요",Toast.LENGTH_SHORT).show();
                    }



                }
                }


            } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    }
