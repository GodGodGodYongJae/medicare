package com.example.arrayintent.inboundcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.arrayintent.R;

public class inboundCheckActivity extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbound_check);
        Log.i("ordertest","eee");
        ImageView btn_back = findViewById(R.id.back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}