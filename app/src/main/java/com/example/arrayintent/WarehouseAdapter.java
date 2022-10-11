package com.example.arrayintent;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseAdapter extends BaseAdapter {
    Context context;
    int layoutId;
    int i = 0;
    String storageName1;
    String storageName2;
    String storageName3;
    String realEditText;
    String realEditText1;
    String realEditText2;
    String realEditText3;
    String realEdithint;
    String realEdithint1;
    String realEdithint2;
    String realEdithint3;
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

    WarehouseAdapter(Context context, int layoutId, ArrayList<ImportBean> listViewArr, ImportBean importBean) {
        this.context = context;
        this.layoutId = layoutId;
        this.listViewArr = listViewArr;
        this.importBean = importBean;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        try {
            return listViewArr.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return listViewArr.get(position).getPdt_name();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        final int pos = position;
        //아래로 스크롤 했을때 위에있는것들 재사용
        if (convertView == null) {
            convertView = inflater.inflate(layoutId, parent, false);
        }

        TextView importTextPdt_Name = (TextView) convertView.findViewById(R.id.importTextPdt_Name);
        importTextPdt_Name.setText(listViewArr.get(position).getPdt_name());

        /*TextView importTextOrder_Qty = (TextView) convertView.findViewById(R.id.importTextOrder_Qty);
        importTextOrder_Qty.setText(Integer.toString(listViewArr.get(position).getPdt_qty()));*/

        /*TextView importTextDel_Qty = (TextView) convertView.findViewById(R.id.importTextDel_Qty);
        importTextDel_Qty.setText(Integer.toString(listViewArr.get(position).getDel_qty()));*/

        EditText importTextReal_Qty = (EditText) convertView.findViewById(R.id.importTextReal_Qty);
        //importTextReal_Qty.setText(Integer.toString(listViewArr.get(position).getDel_qty()));
        importTextReal_Qty.setHint(Integer.toString(listViewArr.get(position).getPdt_qty()));

        //importTextReal_Qty.setSelection(importTextReal_Qty.getHint().length());
        TextView importTextReal_Qty2 = (TextView) convertView.findViewById(R.id.importTextReal_Qty2);
        //importstorge.setSelection(importstorge.getHint().length());
        TextView importstorge_real = (TextView) convertView.findViewById(R.id.importstorge_real);
        //importstorge_zone.setSelection(importstorge_zone.getHint().length());
        TextView importstorge_zone_real = (TextView) convertView.findViewById(R.id.importstorge_zone_real);
        //importstorge_detail.setSelection(importstorge_detail.getHint().length());
        TextView importstorge_detail_real = (TextView) convertView.findViewById(R.id.importstorge_detail_real);
        //처음 초기값 미리 real값에 넣어두기

        if(importTextReal_Qty.getText().toString().equals("")){
            realEditText = importTextReal_Qty.getHint().toString();

            listViewArr.get(position).setPdt_real_qty(Integer.parseInt(realEditText));

        }


        importTextReal_Qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (importTextReal_Qty.getText().toString().equals("")) {
                        realEdithint = importTextReal_Qty.getHint().toString();
                        listViewArr.get(position).setPdt_real_qty(Integer.parseInt(realEdithint));
                    }else {
                        realEditText = importTextReal_Qty.getText().toString();
                        listViewArr.get(position).setPdt_real_qty(Integer.parseInt(realEditText));
                    }

                }catch (Exception e){

                }
            }
        });
        return convertView;
    }
}

