package com.example.arrayintent.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.arrayintent.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExportListAdapter extends BaseAdapter {
    Context context;
    int layoutId;
    int i = 0;
    ArrayList<Map> exportList;
    LayoutInflater inflater;

  public  ExportListAdapter(Context context,int layoutId,ArrayList<Map> exportList)
    {
        this.context = context;
        this.layoutId = layoutId;
        this.exportList = exportList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder{
        TextView txt;
        View layout;
    }

    @Override
    public void notifyDataSetChanged(){super.notifyDataSetChanged();}

    @Override
    public int getCount() {
        try {
            return exportList.size();
        }catch (Exception e)
        {
            return 0;
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
        ViewHolder holder =null;
        if(convertView == null)
        {
            convertView = inflater.inflate(layoutId,parent,false);
        }
        TextView order_code = (TextView) convertView.findViewById(R.id.order_code);
        order_code.setText(exportList.get(position).get("contract_code").toString());
        TextView pdt_name = (TextView) convertView.findViewById(R.id.pdt_name);
        pdt_name.setText(exportList.get(position).get("pdt_name").toString());
        TextView buyer_name = (TextView) convertView.findViewById(R.id.buyer_name);
        buyer_name.setText(exportList.get(position).get("buyer_name").toString());
        return convertView;
    }
}
