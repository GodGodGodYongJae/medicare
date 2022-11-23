package com.example.arrayintent.Product;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.arrayintent.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExportCheckListAdapter extends BaseAdapter {
    Context context;
    int layoutId;
    int i = 0;
    ArrayList<Map> exportList;
    LayoutInflater inflater;
    Spinner spinner;
    public HashMap<Integer,String> textMap = new HashMap<Integer,String>();
    public ExportCheckListAdapter(Context context, int layoutId, ArrayList<Map> exportList) {
        this.context = context;
        this.layoutId = layoutId;
        this.exportList = exportList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public class ViewHolder{
        EditText caption;
        TextView txt;
        View layout;
    }
    @Override
    public void notifyDataSetChanged(){super.notifyDataSetChanged();}

    @Override
    public int getCount() {
        try {
    return exportList.size();
        }catch (Exception e ){
            return 0 ;
        }
    }

    @Override
    public Object getItem(int position) {
        return exportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    final ViewHolder holder;
//     double[] curqty = new double[100]; // How To Fix
     double[] curqty = new double[exportList.size()]; // How To Fix

    convertView = null;
    if(convertView == null)
    {
        holder = new ViewHolder();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        curqty[position] = Double.parseDouble(String.valueOf(exportList.get(position).get("qty")));
        Log.d("curQty",String.valueOf(curqty[position]));

        convertView = inflater.inflate(layoutId,null);
        holder.caption = (EditText) convertView
                .findViewById(R.id.exportTextReal_Qty);
        holder.caption.setTag(position);
        int curparse = (int) curqty[position];
        holder.caption.setText(String.valueOf(curparse));
        textMap.put(position,String.valueOf(curparse));
        convertView.setTag(holder);
    } else{
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
            if(Caption.getText().toString().length() > 0)
            {
                curqty[position2] = Double.parseDouble(Caption.getText().toString());
            }
            else if(Caption.getText().toString().length() == 0)
            {
                curqty[position2] = 0;
                if(String.valueOf(curqty[position2])!=Caption.getText().toString())
                {
                    Caption.setText("0");
                    Caption.requestFocus();
                    Caption.setSelection(Caption.getText().toString().length());
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
          final int position2 = holder.caption.getId();
       final EditText Caption = (EditText) holder.caption;
       double d_qty = Double.parseDouble(String.valueOf(exportList.get(position).get("qty")));
      int qty = (int) d_qty;
      int goodqty = (int)curqty[position2];
      if(qty < goodqty)
      {
          curqty[position2] = qty;
          if(String.valueOf(curqty[position2])!=Caption.getText().toString())
          {
              Caption.setText(Integer.toString(qty));
              Caption.requestFocus();
              Caption.setSelection(Caption.getText().toString().length());
              textMap.put(position2,Caption.getText().toString());
          }
      }

            if (Caption.getText().toString().length() > 1 && s.toString().startsWith("0")) {
                String zerobuff = Caption.getText().toString();
                Log.i("zero",zerobuff.substring(1));
                Caption.setText(zerobuff.substring(1));
                Caption.requestFocus();
                Caption.setSelection(Caption.getText().toString().length());
                textMap.put(position2,Caption.getText().toString());
            }
            textMap.put(position2,Caption.getText().toString());
        }

    });
        TextView pdt_name = (TextView) convertView.findViewById(R.id.pdt_name);
        pdt_name.setText(Html.fromHtml(exportList.get(position).get("pdt_name").toString()+"<br><small>("+exportList.get(position).get("pdt_code").toString()+")</small>"));

        TextView location_name = (TextView) convertView.findViewById(R.id.location_name);
        location_name.setText(exportList.get(position).get("location_name").toString());

        TextView qty = (TextView) convertView.findViewById(R.id.qty);
        int qtyparse = (int) Double.parseDouble(String.valueOf(exportList.get(position).get("qty")));
        qty.setText(String.valueOf(qtyparse));
        //        ViewHolder holder = null;
//        if(convertView == null)
//        {
//            convertView = inflater.inflate(layoutId,parent,false);
//        }
//        TextView pdt_name = (TextView) convertView.findViewById(R.id.pdt_name);
//        pdt_name.setText(Html.fromHtml(exportList.get(position).get("pdt_name").toString()+"<br><small>("+exportList.get(position).get("pdt_code").toString()+")</small>"));
//
//        TextView qty = (TextView) convertView.findViewById(R.id.qty);
//        Double doubleQty = Double.valueOf(String.valueOf(exportList.get(position).get("qty")));
//        int intQty = doubleQty.intValue();
//        qty.setText(Html.fromHtml(String.valueOf(intQty)));
//
//        EditText exportTextReal_Qty = (EditText) convertView.findViewById(R.id.exportTextReal_Qty);
//        // location_name
//        if(exportTextReal_Qty.getText().toString().equals("")){
//            exportTextReal_Qty.setText(String.valueOf(intQtys));
//        }


        return convertView;
    }
}
