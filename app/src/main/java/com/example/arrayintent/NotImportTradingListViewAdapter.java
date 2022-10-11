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

public class NotImportTradingListViewAdapter extends BaseAdapter {
    Context context;
    int layoutId;
    int i = 0;
    String storageName1;
    String storageName2;
    String storageName3;
    String realEditText;
    String realEdithint;
    String delText;
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
    NotImportTradingListViewAdapter(Context context, int layoutId, ArrayList<ImportBean> listViewArr, ImportBean importBean){
        this.context =context;
        this.layoutId =layoutId;
        this.listViewArr = listViewArr;
        this.importBean = importBean;
        this.inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
//        final int pos = position;
        //아래로 스크롤 했을때 위에있는것들 재사용
        if(convertView == null){
            convertView = inflater.inflate(layoutId, parent, false);
        }

        TextView importTextPdt_Name = (TextView) convertView.findViewById(R.id.importTextPdt_Name);
        importTextPdt_Name.setText(listViewArr.get(position).getPdt_name());

        TextView importTextOrder_Qty = (TextView) convertView.findViewById(R.id.importTextOrder_Qty);
        //importTextOrder_Qty.setText(Integer.toString(listViewArr.get(position).getOrder_qty()));
        importTextOrder_Qty.setText(Integer.toString(listViewArr.get(position).getPdt_cd()));

//        TextView importTextDel_Qty = (TextView) convertView.findViewById(R.id.importTextUnit);
//        importTextDel_Qty.setText(Integer.toString(listViewArr.get(position).getPdt_cd()-listViewArr.get(position).getPdt_real_qty()));

        EditText importTextReal_Qty = (EditText) convertView.findViewById(R.id.importTextReal_Qty);

        importTextReal_Qty.setHint(Integer.toString(listViewArr.get(position).getPdt_real_qty()));
        //importTextReal_Qty.setSelection(importTextReal_Qty.getHint().length());
        //TextView importTextReal_Qty2 = (TextView) convertView.findViewById(R.id.importTextReal_Qty2);

        //처음 초기값 미리 real값에 넣어두기

        if(importTextReal_Qty.getText().toString().equals("")){
            realEditText = importTextReal_Qty.getHint().toString();
//            delText = importTextDel_Qty.getText().toString();
            listViewArr.get(position).setPdt_real_qty(Integer.parseInt(realEditText)+Integer.parseInt(delText));
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
//                        delText = importTextDel_Qty.getText().toString();
                        listViewArr.get(position).setPdt_real_qty(Integer.parseInt(realEdithint)+Integer.parseInt(delText));

                    }else {

                        realEditText = importTextReal_Qty.getText().toString();
//                        delText = importTextDel_Qty.getText().toString();
                        listViewArr.get(position).setPdt_real_qty(Integer.parseInt(realEditText)+Integer.parseInt(delText));

                    }

                }catch (Exception e){

                }


            }
        });

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
