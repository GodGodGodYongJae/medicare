/*
package com.example.arrayintent;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseViewAdapter2 extends BaseAdapter {

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
    public interface ListBtnClickListener {
        void OnClickListener(int position);
    }

    // 생성자로부터 전달된 ListBtnClickListener  저장.
    private WarehouseViewAdapter.ListBtnClickListener listBtnClickListener;

    WarehouseViewAdapter(Context context, int layoutId, ArrayList<ImportBean> listViewArr, ImportBean importBean, WarehouseViewAdapter.ListBtnClickListener clickListener) {
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

TextView storename1 = (TextView) convertView.findViewById(R.id.importTextDel_Qty);
        storename1.setText(listViewArr.get(position).getSupply_company_name());


        TextView importTextOrder_Qty = (TextView) convertView.findViewById(R.id.importTextOrder_Qty);
        importTextOrder_Qty.setText(Integer.toString(listViewArr.get(position).getOrder_qty()));

        TextView importTextDel_Qty = (TextView) convertView.findViewById(R.id.importTextDel_Qty);
        importTextDel_Qty.setText(listViewArr.get(position).getStorageName1());

TextView importTextReal_Qty = (TextView) convertView.findViewById(R.id.importTextReal_Qty);


LinearLayout importstoredialog = (LinearLayout) View.inflate(context, R.layout.importstoredialog, null);

        TextView boxDialogTextView4 = (TextView) importstoredialog.findViewById(R.id.boxDialogTextView4);


        // button의 TAG에 position값 지정. Adapter를 click listener로 지정.
        Button QtyBtn = (Button) convertView.findViewById(R.id.QtyBtn);
        QtyBtn.setTag(position);
        QtyBtn.setOnClickListener(this);


        //importstoredialog = new LinearLayout(context);       // Dialog 초기화
        //importdialog.requestWindowFeature(importdialog.FEATURE_NO_TITLE); // 타이틀 제거
        //importstoredialog.setContentView(R.layout.importstoredialog);// xml 레이아웃 파일과 연결

        //QtyBtn.setOnClickListener(mOnClickListener);

        //QtyBtn.setOnClickListener();
QtyBtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                ImportDialog importDialog = new ImportDialog(context);
                // 커스텀 다이얼로그를 호출한다.
                importDialog.showDefaultDialog();

            }
        });



        return convertView;


    }
}
*/
