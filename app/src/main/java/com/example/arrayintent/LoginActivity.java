
package com.example.arrayintent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView loginBtn;
    EditText et_id;
    EditText et_pw;



    /*public static MemberDTO loginDTO = null;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity_main);
       // 로그인 아이디를 세션으로 유지 시 SharedPreferences 를 통해 유지시킴
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();



        loginBtn = (ImageView) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        et_id = (EditText) findViewById(R.id.et_id);
        et_pw = (EditText) findViewById(R.id.et_pw);
// 자동로그인
        if(loginpref.getString("test_id",null)!=null){
            Intent intent = new Intent(LoginActivity.this, newMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public void onClick(View v) {
        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        SharedPreferences loginpref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();

        switch (v.getId()) {
            case R.id.loginBtn:
                if(wifi.isConnected()) {
                    String manager_id = et_id.getText().toString();
                    String password = et_pw.getText().toString();

                    try {
                        LoginSelect loginSelect = new LoginSelect();
                        String result = loginSelect.execute(manager_id, password).get();
                        Log.w("받은 값", result);
                        JSONObject jsonObject = new JSONObject(result);

                        String message = jsonObject.getString("success").toString();
                        if (message.equals("true")) {
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();


                            //TOdo

                            // End

                            //logineditor.putString("test_id", manager_id);
                            logineditor.commit();

                            Intent intent = new Intent(LoginActivity.this, newMainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else if (message.equals("false")) {
                            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 틀렸음", Toast.LENGTH_SHORT).show();
                            et_id.setText("");
                            et_pw.setText("");
                        } else if (message.equals("noId")) {
                            Toast.makeText(LoginActivity.this, "존재하지 않는 아이디", Toast.LENGTH_SHORT).show();
                            et_id.setText("");
                            et_pw.setText("");
                        }
                    } catch (Exception e) {
                        Toast.makeText(this,"서버와 연결을 확인해주세요",Toast.LENGTH_SHORT).show();
                        //Log.w("로그인",e);
                    }


                    break;
                }else {
                    Toast myToast = Toast.makeText(this.getApplicationContext(), "WIFI를 연결 후 로그인하세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }

            default:
                break;

        }
    }






}