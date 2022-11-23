package com.example.arrayintent.inboundcheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.arrayintent.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class popupListViewAdapter extends BaseAdapter {
    Context context;
    int layoutId;
    ArrayList<inbounBean> listViewArr;
    LayoutInflater inflater;

    public popupListViewAdapter(Context context, int layoutId, ArrayList<inbounBean> listViewArr) {
        this.context = context;
        this.layoutId = layoutId;
        this.listViewArr = listViewArr;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        try {
            return listViewArr.size();
        }catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return listViewArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         convertView = null;
         if(convertView == null)
         {
             convertView = inflater.inflate(layoutId,null);
         }
        TextView tv_ordercode = (TextView) convertView.findViewById(R.id.order_code);
        tv_ordercode.setText(listViewArr.get(position).getOrder_code());

        TextView tv_supplyName = (TextView) convertView.findViewById(R.id.supply_name);
        tv_supplyName.setText(listViewArr.get(position).getSupply_name());

        TextView tv_pdtName = (TextView) convertView.findViewById(R.id.pdt_name);
        tv_pdtName.setText(listViewArr.get(position).getPdt_name());
        return convertView;
    }
}
