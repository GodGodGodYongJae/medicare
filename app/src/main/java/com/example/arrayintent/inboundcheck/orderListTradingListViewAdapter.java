package com.example.arrayintent.inboundcheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.arrayintent.R;

import java.util.ArrayList;

public class orderListTradingListViewAdapter extends BaseAdapter {
    Context context;
    int layoutId;
    ArrayList<orderBean> listViewArr;
    LayoutInflater inflater;

    public orderListTradingListViewAdapter(Context context, int layoutId, ArrayList<orderBean> listViewArr ) {
        this.context = context;
        this.layoutId = layoutId;
        this.listViewArr = listViewArr;
        this.inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        try {
                return listViewArr.size();
            }
        catch (Exception e)
        {
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return listViewArr.get(position).getPdt_name();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = null;
        if(convertView == null)
        {
            convertView = inflater.inflate(layoutId,null);
        }
        TextView tv_pdtname = (TextView) convertView.findViewById(R.id.importTextPdt_Name);
        TextView tv_qty = (TextView) convertView.findViewById(R.id.importTextOrder_Qty) ;
        TextView tv_realqty = (TextView) convertView.findViewById(R.id.importTextReal_Qty);
        tv_pdtname.setText(listViewArr.get(position).getPdt_name());
        tv_qty.setText(String.valueOf(listViewArr.get(position).getQty()));
        tv_realqty.setText(String.valueOf(listViewArr.get(position).getRealqty()));
        return convertView;
    }
}
