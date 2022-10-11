package com.example.arrayintent;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.WindowManager;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.jaredrummler.materialspinner.MaterialSpinner;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportListViewAdapter extends BaseAdapter {

    private ImportDialog mImportDialog;

    Context context;
    int layoutId;
    //static Context context = WarehouseActivity.context;

    AlertDialog dialog;
    int i = 0;
    String storageName1;
    String storageName2;
    String storageName3;

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
    private Button QtyBtn;

    private ArrayList<ImportBean> importBeans = new ArrayList<ImportBean>();

    /*public ImportListViewAdapter(Context context, ArrayList<ImportBean> list, Point size) {
        this.context = context;
        this.listViewArr = list;


        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //인플레이터는 시스템에서 가져온다.
    }*/
    /*public ImportListViewAdapter(Context context){
        this.context = context;
        this.listViewArr = listViewArr;
        this.inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }*/
    public ImportListViewAdapter(){

    }


    ImportListViewAdapter(Context context, int layoutId, ArrayList<ImportBean> listViewArr, ImportBean importBean){
        this.context =context;
        this.layoutId =layoutId;
        this.listViewArr = listViewArr;
        this.importBean = importBean;
        this.inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //리스트(list)에 항목을 추가해 줄 메서드 작성
    public void addImportBean(ImportBean ImportBean) {
        listViewArr.add(ImportBean);
    }

    //리스트의 항목을 삭제할 메서드 작성
    public void delImportBean(int position) {
        listViewArr.remove(position);
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImportListViewHolder importListViewHolder;
        final int pos = position;
        final Context context = parent.getContext();

//        final int pos = position;
        //아래로 스크롤 했을때 위에있는것들 재사용
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layoutId, parent, false);
            importListViewHolder = new ImportListViewHolder();

            ListView listView = (ListView)convertView.findViewById(R.id.pdtListview1);

            TextView importTextPdt_Name = (TextView) convertView.findViewById(R.id.importTextPdt_Name);
            importTextPdt_Name.setText(listViewArr.get(position).getPdt_name());

            TextView importTextOrder_Qty = (TextView) convertView.findViewById(R.id.importTextOrder_Qty);
            importTextOrder_Qty.setText(Integer.toString(listViewArr.get(position).getQty()));

//            TextView importTextUnit = (TextView) convertView.findViewById(R.id.importTextUnit);
////            importTextUnit.setText((listViewArr.get(position).getUnit()));

            EditText importTextReal_Qty = (EditText) convertView.findViewById(R.id.importTextReal_Qty);
            //importTextReal_Qty.setText(Integer.toString(listViewArr.get(position).getDel_qty()));
            importTextReal_Qty.setHint(Integer.toString(listViewArr.get(position).getPdt_qty()));

        /*TextView importstore = (TextView) convertView.findViewById(R.id.importstore);
        importstore.setText((listViewArr.get(position).getStorage()));*/

            //importTextReal_Qty.setSelection(importTextReal_Qty.getHint().length());
            TextView importTextReal_Qty2 = (TextView) convertView.findViewById(R.id.importTextReal_Qty2);
            //importListViewHolder.importTextReal_Qty = convertView.findViewById(R.id.importTextReal_Qty);
            //TextView importTextReal_Qty = (TextView) convertView.findViewById(R.id.importTextReal_Qty);

            /*LinearLayout importstoredialog =(LinearLayout)View.inflate(context,R.layout.importstoredialog,null);

            TextView boxDialogTextView4 = (TextView)importstoredialog.findViewById(R.id.boxDialogTextView4);
            AlertDialog.Builder ImportDialog = new AlertDialog.Builder(context);*/

            importListViewHolder.QtyBtn = convertView.findViewById(R.id.QtyBtn);
            //QtyBtn.setOnClickListener((View.OnClickListener)context);

            convertView.setTag(importListViewHolder);
        }else {
            importListViewHolder = (ImportListViewHolder) convertView.getTag();
        }


        importListViewHolder.importTextPdt_Name.setText(listViewArr.get(position).getPdt_name());
        importListViewHolder.importTextOrder_Qty.setText(Integer.toString(listViewArr.get(position).getPdt_qty()));
        importListViewHolder.importTextDel_Qty.setText(Integer.toString(listViewArr.get(position).getPdt_qty()));

        /*Button QtyBtn = (Button)convertView.findViewById(R.id.QtyBtn);
        QtyBtn.setOnClickListener((View.OnClickListener)context);*/

        /*LinearLayout cmdArea = (LinearLayout)convertView.findViewById(R.id.cmdArea);
        cmdArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        /*importListViewHolder.QtyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               *//* Toast.makeText(context, "선택 : " + position
                        + ", 이름 : " + listViewArr.get(position).getPdt_name(), Toast.LENGTH_SHORT).show();*//*
                //importstoredialogXml();

            }
        });*/

        return convertView;
    }
    public class ImportListViewHolder {
        public TextView importTextPdt_Name, importTextOrder_Qty, importTextDel_Qty, importTextReal_Qty, boxDialogTextView4;
        public Button QtyBtn;
    }

   /* public void importstoredialogXml(){



        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.importstoredialog, null);
        TextView boxDialogTextView1 = view.findViewById(R.id.boxDialogTextView1);
        TextView boxDialogTextView4 = view.findViewById(R.id.boxDialogTextView4);
        EditText importStore_Qty = view.findViewById(R.id.importStore_Qty);
        TextView boxDialogTextView2 = view.findViewById(R.id.boxDialogTextView2);
        TextView importEditReal_Qty = view.findViewById(R.id.importEditReal_Qty);
        MaterialSpinner storeNameSpinner = view.findViewById(R.id.storeNameSpinner);
        MaterialSpinner storeDetail1Spinner = view.findViewById(R.id.storeDetail1Spinner);
        MaterialSpinner storeDedatil2Spinner = view.findViewById(R.id.storeDedatil2Spinner);
        ImageButton importListResetBtn1 = view.findViewById(R.id.importListResetBtn1);
        ImageButton importListSendBtn1 = view.findViewById(R.id.importListSendBtn1);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("수량,창고 입력")
                .setView(view);

        importListResetBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "초기화", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                ((WarehouseActivity) context).finish();
            }
        });

        importListSendBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "등록 완료", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(ImportDialog.getContext(), newMainActivity.class);
                dialog.dismiss();
                //((WarehouseActivity) context).finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
*/
}
