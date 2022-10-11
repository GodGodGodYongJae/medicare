package com.example.arrayintent;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.annotation.NonNull;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseViewAdapter extends BaseAdapter {
    Context context;
    int layoutId;
    int i = 0;
    String storageName1;
    String storageName2;
    String storageName3;

    Button QtyBtn;

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
    Dialog importstoredialog;

    private int position;
    private Object ImportDialog;
    private ImportDialog mImportDialog;


    // 버튼 클릭 이벤트를 위한 Listener 인터페이스 정의.
    /*public interface ListBtnClickListener {
        void OnClickListener(int position);
    }*/

    /*public void show_default_dialog(View v){
        //클릭시 defaultDialog 를 띄워준다
        new ImportDialog(this).showDefaultDialog();
    }*/

    // 생성자로부터 전달된 ListBtnClickListener  저장.
    //private ListBtnClickListener listBtnClickListener;

    WarehouseViewAdapter(Context context, int layoutId, ArrayList<ImportBean> listViewArr, ImportBean importBean/*, ListBtnClickListener clickListener*/) {
        this.context = context;
        this.layoutId = layoutId;
        this.listViewArr = listViewArr;
        this.importBean = importBean;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.listBtnClickListener = clickListener;
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutId, parent, false);
        }

        TextView importTextPdt_Name = (TextView) convertView.findViewById(R.id.importTextPdt_Name);
        importTextPdt_Name.setText(listViewArr.get(position).getPdt_name());

        /*TextView storename1 = (TextView) convertView.findViewById(R.id.importTextDel_Qty);
        storename1.setText(listViewArr.get(position).getSupply_company_name());*/

        TextView importTextOrder_Qty = (TextView) convertView.findViewById(R.id.importTextOrder_Qty);
        importTextOrder_Qty.setText(Integer.toString(listViewArr.get(position).getPdt_qty()));

//        TextView importTextDel_Qty = (TextView) convertView.findViewById(R.id.importTextUnit);
//        importTextDel_Qty.setText(listViewArr.get(position).getUnit());

        /*TextView importTextReal_Qty = (TextView) convertView.findViewById(R.id.importTextReal_Qty);*/

        /*LinearLayout importstoredialog = (LinearLayout) View.inflate(context, R.layout.importstoredialog, null);

        TextView boxDialogTextView4 = (TextView) importstoredialog.findViewById(R.id.boxDialogTextView4);*/

        // button의 TAG에 position값 지정. Adapter를 click listener로 지정.
        Button QtyBtn = (Button) convertView.findViewById(R.id.QtyBtn);
        QtyBtn.setTag(position);
        //QtyBtn.setOnClickListener(buttonClickListener);
        //QtyBtn.setOnClickListener(this);


        //importstoredialog = new LinearLayout(context);       // Dialog 초기화
        //importdialog.requestWindowFeature(importdialog.FEATURE_NO_TITLE); // 타이틀 제거
        //importstoredialog.setContentView(R.layout.importstoredialog);// xml 레이아웃 파일과 연결

        //QtyBtn.setOnClickListener(mOnClickListener);

        //QtyBtn.setOnClickListener();
        /*QtyBtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                ImportDialog importDialog = new ImportDialog(context);
                // 커스텀 다이얼로그를 호출한다.
                importDialog.showDefaultDialog();

            }
        });*/


        return convertView;
    }

    /*public void setListViewArr (ArrayList<ImportBean> listViewArr){
        this.listViewArr = listViewArr;
    }
    public ArrayList getListViewArr(){
        return listViewArr;
    }
    private View.OnClickListener buttonClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.QtyBtn:
                    mImportDialog = new ImportDialog(this);
                    mImportDialog.setCancelable(false);
                    mImportDialog.show();
                    break;

            }

        }
    };*/







        /*@Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.QtyBtn:

                    // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                    *//*mImportDialog = new ImportDialog();
                    mImportDialog.setCancelable(false);
                    mImportDialog.show();
                    break;*//*
            }

        }*/

    /*public void setListViewArr(ArrayList arrays){
        this.listViewArr = arrays;
    }

    public ArrayList ArrayList(){
        return listViewArr;
    }

        private View.OnClickListener buttonClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.QtyBtn:

                     //ImportDialog dialog= new ImportDialog(this);
                    //dialog.show();
                    break;
                    *//*LinearLayout importstoredialog = (LinearLayout) View.inflate(context, R.layout.importstoredialog, null);
                    TextView boxDialogTextView4 = (TextView) importstoredialog.findViewById(R.id.boxDialogTextView4);
                    MaterialSpinner storeNameSpinner = (MaterialSpinner) importstoredialog.findViewById(R.id.storeNameSpinner);
                    MaterialSpinner storeDetail1Spinner = (MaterialSpinner) importstoredialog.findViewById(R.id.storeDetail1Spinner);
                    MaterialSpinner storeDetail2Spinner = (MaterialSpinner) importstoredialog.findViewById(R.id.storeDedatil2Spinner);
                    EditText importEditReal_Qty = (EditText) importstoredialog.findViewById(R.id.importEditReal_Qty);
                    for(int i = 0; i < listViewArr.size(); i++){
                        System.err.println(i+" : " + listViewArr.get(i).getOrder_qty());
                    }
                    String order_qty = Integer.toString(listViewArr.get(0).getOrder_qty());

                    if (!listViewArr.get(position).getStorageName1().equals("")) {
                        boxDialogTextView4.setText("(창고 : " + listViewArr.get(position).getStorageName1() + "-" + listViewArr.get(position).getStorageName2() +
                                "-" + listViewArr.get(position).getStorageName3() + ")");
                    }

                    name1List.clear();
                    name2List.clear();

                    name3List.clear();

                    name1Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name1List);
                    storeNameSpinner.setAdapter(name1Adapter);
                    name2Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name2List);
                    storeDetail1Spinner.setAdapter(name2Adapter);
                    name3Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name3List);
                    storeDetail2Spinner.setAdapter(name3Adapter);
                    //실수량

                    //창고이름
                    try {
                        StorageName1CustomTask storageName1CustomTask = new StorageName1CustomTask();
                        String name1_result = storageName1CustomTask.execute().get();
                        Log.w("name1 리스트", name1_result);

                        name1List.clear();
                        name2List.clear();
                        name3List.clear();
                        splitNum1 = name1_result.split(",  ");
                        if (!name1List.contains("창고명")) {
                            name1List.add("창고명");
                        }
                        for (int i = 0; i < splitNum1.length; i++) {
                            if (!name1List.contains(splitNum1[i])) {
                                name1List.add(splitNum1[i]);
                            }
                        }

                        name1Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name1List);
                        storeNameSpinner.setAdapter(name1Adapter);

                    } catch (Exception e) {

                    }

                    storeNameSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                            storageName1 = item.toString();
                            if (storageName1 != null) {

                                //창고 디테일1
                                try {
                                    StorageName2CustomTask storageName2CustomTask = new StorageName2CustomTask();
                                    String name2_result = storageName2CustomTask.execute(storageName1).get();
                                    Log.w("name2 리스트", name2_result);
                                    name2List.clear();
                                    name3List.clear();
                                    splitNum2 = name2_result.split(",  ");
                                    if (!name2List.contains("구분1")) {
                                        name2List.add("구분1");
                                    }


                                    for (int i = 0; i < splitNum2.length; i++) {
                                        if (!name2List.contains(splitNum2[i])) {
                                            name2List.add(splitNum2[i]);
                                        }
                                    }

                                    name2Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name2List);
                                    storeDetail1Spinner.setAdapter(name2Adapter);


                                } catch (Exception e) {

                                }
                                storeDetail2Spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                                        nameMap.put("storage_detail2"+position,item.toString());
                                        storageName3 = item.toString();
                                    }
                                });

                            }
                        }

                    });*//*

            }
            //importstoredialog.show();
        }
    };*/

   /* public void ImportDialog(Context context) {
        this.context = context;

       *//* // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        Dialog importDialog = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        //ImportDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        importDialog.setContentView(R.layout.importstoredialog);*//*

        // 다이얼로그 띄우기
        //importstoredialog.show();


        *//*LinearLayout importstoredialog = (LinearLayout) View.inflate(context, R.layout.importstoredialog, null);
        TextView boxDialogTextView4 = (TextView) importstoredialog.findViewById(R.id.boxDialogTextView4);
        MaterialSpinner storeNameSpinner = (MaterialSpinner) importstoredialog.findViewById(R.id.storeNameSpinner);
        MaterialSpinner storeDetail1Spinner = (MaterialSpinner) importstoredialog.findViewById(R.id.storeDetail1Spinner);
        MaterialSpinner storeDetail2Spinner = (MaterialSpinner) importstoredialog.findViewById(R.id.storeDedatil2Spinner);
        EditText importEditReal_Qty = (EditText) importstoredialog.findViewById(R.id.importEditReal_Qty);
        for(int i = 0; i < listViewArr.size(); i++){
            System.err.println(i+" : " + listViewArr.get(i).getOrder_qty());
        }
        String order_qty = Integer.toString(listViewArr.get(0).getOrder_qty());

        if (!listViewArr.get(position).getStorageName1().equals("")) {
            boxDialogTextView4.setText("(창고 : " + listViewArr.get(position).getStorageName1() + "-" + listViewArr.get(position).getStorageName2() +
                    "-" + listViewArr.get(position).getStorageName3() + ")");
        }

        name1List.clear();
        name2List.clear();

        name3List.clear();

        name1Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name1List);
        storeNameSpinner.setAdapter(name1Adapter);
        name2Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name2List);
        storeDetail1Spinner.setAdapter(name2Adapter);
        name3Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name3List);
        storeDetail2Spinner.setAdapter(name3Adapter);
        //실수량

        //창고이름
        try {
            StorageName1CustomTask storageName1CustomTask = new StorageName1CustomTask();
            String name1_result = storageName1CustomTask.execute().get();
            Log.w("name1 리스트", name1_result);

            name1List.clear();
            name2List.clear();
            name3List.clear();
            splitNum1 = name1_result.split(",  ");
            if (!name1List.contains("창고명")) {
                name1List.add("창고명");
            }
            for (int i = 0; i < splitNum1.length; i++) {
                if (!name1List.contains(splitNum1[i])) {
                    name1List.add(splitNum1[i]);
                }
            }

            name1Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name1List);
            storeNameSpinner.setAdapter(name1Adapter);

        } catch (Exception e) {

        }

        storeNameSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                storageName1 = item.toString();
                if (storageName1 != null) {

                    //창고 디테일1
                    try {
                        StorageName2CustomTask storageName2CustomTask = new StorageName2CustomTask();
                        String name2_result = storageName2CustomTask.execute(storageName1).get();
                        Log.w("name2 리스트", name2_result);
                        name2List.clear();
                        name3List.clear();
                        splitNum2 = name2_result.split(",  ");
                        if (!name2List.contains("구분1")) {
                            name2List.add("구분1");
                        }


                        for (int i = 0; i < splitNum2.length; i++) {
                            if (!name2List.contains(splitNum2[i])) {
                                name2List.add(splitNum2[i]);
                            }
                        }

                        name2Adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, name2List);
                        storeDetail1Spinner.setAdapter(name2Adapter);


                    } catch (Exception e) {

                    }
                    storeDetail2Spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                            nameMap.put("storage_detail2"+position,item.toString());
                            storageName3 = item.toString();
                        }
                    });

                }
            }

        });*//*
        //importDialog.show();
    }
*/


}
