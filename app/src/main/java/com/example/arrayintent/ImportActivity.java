package com.example.arrayintent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.arrayintent.inboundcheck.ImportConfirmActivity;

public class ImportActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView back;
    CardView ImportConfirmActivity_pagebtn;
    CardView NotImportConfirmActivity_pagebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearing);

        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        ImportConfirmActivity_pagebtn = (CardView)findViewById(R.id.ImportConfirmActivity_pagebtn);
        NotImportConfirmActivity_pagebtn = (CardView) findViewById(R.id.NotImportConfirmActivity_pagebtn);
        back = findViewById(R.id.back);

        ImportConfirmActivity_pagebtn.setOnClickListener(this);
        NotImportConfirmActivity_pagebtn.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();

        switch (v.getId()){
            case R.id.ImportConfirmActivity_pagebtn:
                Intent intent = new Intent(ImportActivity.this, ImportConfirmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

            case R.id.NotImportConfirmActivity_pagebtn:
                intent = new Intent(ImportActivity.this, NotImportConfirmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

            case R.id.back:
                intent = new Intent(ImportActivity.this, newMainActivity.class);
                finish();
                startActivity(intent);
                break;

            default:
                break;
        }

    }
}



