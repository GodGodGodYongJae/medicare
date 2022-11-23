package com.example.arrayintent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportTradingListViewAdapter extends BaseAdapter {
    Context context;
    int layoutId;
    int i = 0;
    String storageName1;
    String storageName2;
    String storageName3;
    String realEditText;
    String realEdithint;
    ArrayAdapter<String> name1Adapter;
    ArrayAdapter<String> name2Adapter;
    ArrayAdapter<String> name3Adapter;
    ImportBean importBean;
    List<String> name1List = new ArrayList<String>();
    List<String> name2List = new ArrayList<String>();
    List<String> name3List = new ArrayList<String>();
    String[] splitNum1, splitNum2, splitNum3;
    Map<String, String> nameMap = new HashMap<>();
    ArrayList<ImportBean> listViewArr;
    LayoutInflater inflater;

    public ImportTradingListViewAdapter(Context context, int layoutId, ArrayList<ImportBean> listViewArr, ImportBean importBean){
        this.context =context;
        this.layoutId =layoutId;
        this.listViewArr = listViewArr;
        this.importBean = importBean;
      //  this.inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }



    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        try {
            return listViewArr.size();
        }catch (Exception e){
            return 0;
        }
    }


    @Override
    public Object getItem(int position)
    {
        return listViewArr.get(position).getPdt_name();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        final int pos = position;
        final ViewHolder holder;
        //아래로 스크롤 했을때 위에있는것들 재사용
        convertView = null;
        if(convertView == null){
            holder = new ViewHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutId, null);
            holder.caption = (EditText) convertView
                    .findViewById(R.id.importTextReal_Qty);
            holder.caption.setTag(position);
            holder.caption.setText(String.valueOf(listViewArr.get(position).getPdt_real_qty()));
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        int tag_position = (Integer) holder.caption.getTag();
        holder.caption.setId(tag_position);
        holder.caption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final int position2 = holder.caption.getId();
                final EditText Caption = (EditText) holder.caption;

                if(Caption.getText().toString().length() >0)
                {
                    listViewArr.get(position2).setPdt_real_qty(Integer.parseInt(Caption.getText().toString()));
//                    listViewArr.set(position2,Caption.getText().toString());
                }
//                else if(Integer.parseInt(Caption.getText().toString()) > listViewArr.get(position2).getQty())
//                {
//
//                    listViewArr.get(position2).setPdt_real_qty(listViewArr.get(position2).getQty());
//                }
//                else{
//                    listViewArr.get(position2).setPdt_real_qty(listViewArr.get(position2).getQty());
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                final int position2 = holder.caption.getId();
                final EditText Caption = (EditText) holder.caption;
                int qty = Integer.valueOf(String.valueOf(listViewArr.get(position2).getQty()));
                int goodpty = Integer.valueOf(String.valueOf(listViewArr.get(position2).getPdt_real_qty()));
                if(qty < goodpty)
                {
//                    Log.i("inti","들어옴");
                    listViewArr.get(position2).setPdt_real_qty(listViewArr.get(position2).getQty());
                    if(  String.valueOf(listViewArr.get(position2).getPdt_real_qty()) != Caption.getText().toString())
                    {
                        Caption.setText(Integer.toString(listViewArr.get(position2).getPdt_real_qty()));
//                        setSelection(position)
//                        Log.i("inti",Caption.getText().toString());
//                        Log.i("inti2",String.valueOf(listViewArr.get(position2).getQty()));
                        Caption.requestFocus();
                        Caption.setSelection(Caption.getText().toString().length());
                        Toast.makeText(context, "납품수량을 초과할 수 없습니다.", Toast.LENGTH_SHORT).show();

                    }
                }
                Log.i("init",String.valueOf(Caption.getText().toString().length()));
                if (Caption.getText().toString().length() > 1 && s.toString().startsWith("0")) {
                    String zerobuff = Caption.getText().toString();
                    Log.i("zero",zerobuff.substring(1));
                    Caption.setText(zerobuff.substring(1));
                    Caption.requestFocus();
                    Caption.setSelection(Caption.getText().toString().length());
                }



//                if(  holder.caption.hasFocus()){
//                    int qty = Integer.valueOf(String.valueOf(listViewArr.get(position).getQty()));
//                    int goodQty = 0;
//                    try {
//                        goodQty = Integer.valueOf(String.valueOf(s).replaceFirst("^0+(?!$)",""));
//                    }catch (NumberFormatException e){
//                        goodQty = 0;
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    holder.caption.removeTextChangedListener(this);
//                    if(goodQty > qty){
//                        goodQty = qty;
//                        holder.caption.setText(String.valueOf(qty));
//                    }
//                    holder.caption.setText(String.valueOf(goodQty));
//                    holder.caption.addTextChangedListener(this);
//                }

            }

        });

        TextView importTextPdt_Name = (TextView) convertView.findViewById(R.id.importTextPdt_Name);
        importTextPdt_Name.setText(listViewArr.get(position).getPdt_name());

        TextView importTextOrder_Qty = (TextView) convertView.findViewById(R.id.importTextOrder_Qty);

        importTextOrder_Qty.setText(Integer.toString(listViewArr.get(position).getQty()));

//        TextView importTextUnit = (TextView) convertView.findViewById(R.id.importTextUnit);
//        importTextUnit.setText((listViewArr.get(position).getUnit()));

//        EditText importTextReal_Qty = (EditText) convertView.findViewById(R.id.importTextReal_Qty);
        //importTextReal_Qty.setText(Integer.toString(listViewArr.get(position).getDel_qty()));
            //importTextReal_Qty.setText(Integer.toString(listViewArr.get(position).getPdt_real_qty()));
//            importTextReal_Qty.setHint(Integer.toString(listViewArr.get(position).getPdt_qty()));

        /*TextView importstore = (TextView) convertView.findViewById(R.id.importstore);
        importstore.setText((listViewArr.get(position).getStorage()));*/

        //importTextReal_Qty.setSelection(importTextReal_Qty.getHint().length());
//        TextView importTextReal_Qty2 = (TextView) convertView.findViewById(R.id.importTextReal_Qty2);
//        importTextReal_Qty2.setText((listViewArr.get(position).getPdt_qty()));
        //처음 초기값 미리 real값에 넣어두기

//        if(importTextReal_Qty.getText().toString().equals("")){
//            realEditText = importTextReal_Qty.getHint().toString();
//            listViewArr.get(position).setPdt_real_qty(Integer.parseInt(realEditText));
//        }
//
//      if(importTextReal_Qty.getText().toString().equals("")){
//          importTextReal_Qty.setText("0");
//        }
//
//
//        importTextReal_Qty.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                listViewArr.get(position).setPdt_real_qty(Integer.parseInt(realEdithint));
//            }
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                try {
////                    if (importTextReal_Qty.getText().toString().equals("")) {
////                        realEdithint = importTextReal_Qty.getHint().toString();
////                        listViewArr.get(position).setPdt_real_qty(Integer.parseInt(realEdithint));
////
////                    }else {
////
////                        realEditText = importTextReal_Qty.getText().toString();
////                        listViewArr.get(position).setPdt_real_qty(Integer.parseInt(realEditText));
////
////                    }
////
////                }catch (Exception e){
////
////                }
//
//                if(importTextReal_Qty.hasFocus()){
//                    int qty = Integer.valueOf(String.valueOf(listViewArr.get(position).getQty()));
//                    int goodQty = 0;
//                    try {
//                        goodQty = Integer.valueOf(String.valueOf(s).replaceFirst("^0+(?!$)",""));
//                    }catch (NumberFormatException e){
//                        goodQty = 0;
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    importTextReal_Qty.removeTextChangedListener(this);
//                    if(goodQty > qty){
//                        goodQty = qty;
//                        importTextReal_Qty.setText(String.valueOf(qty));
//                    }
//                    importTextReal_Qty.setText(String.valueOf(goodQty));
//                    importTextReal_Qty.addTextChangedListener(this);
//                }
//
//            }
//        });

// 단일 버튼이었을때
//        Button QtyBtn = (Button)convertView.findViewById(R.id.QtyBtn);
//        QtyBtn.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    if (QtyBtn.getText().toString().equals("확인")) {
//                        if (importTextReal_Qty.getText().toString().equals("")) {
//                            String realEditText = importTextReal_Qty.getHint().toString();
//                            importTextReal_Qty.setVisibility(View.GONE);
//                            importTextReal_Qty2.setVisibility(View.VISIBLE);
//                            importTextReal_Qty2.setText(realEditText);
//                            QtyBtn.setText("수정");
//                            listViewArr.get(position).setReal_qty(Integer.parseInt(importTextReal_Qty2.getText().toString()));
//                            Log.w("힌트저장",Integer.toString(listViewArr.get(position).getReal_qty()));
//                        } else {
//                            String realEditText = importTextReal_Qty.getText().toString();
//                            importTextReal_Qty.setVisibility(View.GONE);
//                            importTextReal_Qty2.setVisibility(View.VISIBLE);
//                            importTextReal_Qty2.setText(realEditText);
//                            QtyBtn.setText("수정");
//                            listViewArr.get(position).setReal_qty(Integer.parseInt(importTextReal_Qty2.getText().toString()));
//                            Log.w("EDIT저장",Integer.toString(listViewArr.get(position).getReal_qty()));
//
//                        }
//
//
//                    } else {
//                        String realTextView = importTextReal_Qty2.getText().toString();
//                        importTextReal_Qty.setVisibility(View.VISIBLE);
//                        importTextReal_Qty2.setVisibility(View.GONE);
//                        //importTextReal_Qty.setText(realTextView);
//                        QtyBtn.setText("확인");
//
//
//                    }
//                }catch (Exception e){
//
//                }
//
//
//
//
//
//
//
//
//                                      }
//                                  }
//
//        );


        return convertView;
    }
}
class ViewHolder {
    EditText caption;
}