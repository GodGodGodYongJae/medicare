package com.example.arrayintent;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class ImportDialog extends Dialog {
    private static final Dialog V = null ;

    private Context context;
    private ImageButton importListResetBtn1;
    private ImageButton importListSendBtn1;
    private TextView boxDialogTextView4;
    private MaterialSpinner storeNameSpinner;
    private MaterialSpinner storeDetail1Spinner;
    private MaterialSpinner storeDedatil2Spinner;
    private EditText importEditReal_Qty;
    private TextView boxDialogTextView1;
    private EditText importStore_Qty;
    private TextView boxDialogTextView2;

    public ImportDialog(@NonNull Context mcontext) {
        super(mcontext);
        this.context = mcontext;
    }


    @Override
    protected void onCreate(Bundle  savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.importstoredialog);

        boxDialogTextView1 = (TextView) findViewById(R.id.boxDialogTextView1);
        boxDialogTextView4 = (TextView) findViewById(R.id.boxDialogTextView4);
        importStore_Qty = (EditText) findViewById(R.id.importStore_Qty);
        boxDialogTextView2 = (TextView) findViewById(R.id.boxDialogTextView2);
        importEditReal_Qty = (EditText) findViewById(R.id.importEditReal_Qty);
        storeNameSpinner = (MaterialSpinner) findViewById(R.id.storeNameSpinner);
        storeDetail1Spinner = (MaterialSpinner) findViewById(R.id.storeDetail1Spinner);
        storeDedatil2Spinner = (MaterialSpinner) findViewById(R.id.storeDedatil2Spinner);
        importListResetBtn1 = (ImageButton) findViewById(R.id.importListResetBtn1);
        importListSendBtn1 = (ImageButton) findViewById(R.id.importListSendBtn1);


        importListResetBtn1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "초기화", Toast.LENGTH_SHORT).show();
                dismiss();
                ((WarehouseActivity) context).finish();
            }
        });

        importListSendBtn1.setOnClickListener((new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "등록 완료", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ImportDialog.V.getContext(), newMainActivity.class);
                dismiss();
                ((WarehouseActivity) context).finish();

            }
        }));




      /*  importListResetBtn1.setOnClickListener(this);
        importListSendBtn1.setOnClickListener(this);
        boxDialogTextView4.setOnClickListener(this);
        storeNameSpinner.setOnClickListener(this);
        storeDetail1Spinner.setOnClickListener(this);
        storeDedatil2Spinner.setOnClickListener(this);
        importEditReal_Qty.setOnClickListener(this);*/

    }


 /*   @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.importListResetBtn1:
                Toast.makeText(context, "초기화", Toast.LENGTH_SHORT).show();
                dismiss();
                ((WarehouseActivity) context).finish();
                break;

            case R.id.importListSendBtn1:
                Toast.makeText(context, "등록 완료", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ImportDialog.V.getContext(), newMainActivity.class);
                dismiss();
                ((WarehouseActivity) context).finish();

                break;

            //case R.id.boxDialogTextView4
        }

    }*/

     /* public  void callDialog()
    {
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.importstoredialog);
        dialog.show();

        final ImageButton importListResetBtn = (ImageButton) dialog.findViewById(R.id.importListResetBtn);
        final ImageButton importListSendBtn = (ImageButton) dialog.findViewById(R.id.importListSendBtn);
        final TextView boxDialogTextView4 = (TextView) dialog.findViewById(R.id.boxDialogTextView4);
        final MaterialSpinner storeNameSpinner = (MaterialSpinner) dialog.findViewById(R.id.storeNameSpinner);
        final MaterialSpinner storeDetail1Spinner = (MaterialSpinner) dialog.findViewById(R.id.storeDetail1Spinner);
        final MaterialSpinner storeDetail2Spinner = (MaterialSpinner) dialog.findViewById(R.id.storeDedatil2Spinner);
        final EditText importEditReal_Qty = (EditText) dialog.findViewById(R.id.importEditReal_Qty);

        *//*importListResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "초기화", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                ((WarehouseActivity) context).finish();
            }
        });

        importListSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "등록 완료", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ImportDialog.V.getContext(), newMainActivity.class);
                dialog.dismiss();
                ((WarehouseActivity) context).finish();
            }
        });*//*
    }*/

}
