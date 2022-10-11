package com.example.arrayintent;

public class ImportListViewData {
    String pdt_name;
    int pdt_qty;
    int del_qty;
    int pdt_real_qty;
    String storege;
    String storege_zone;
    String store_detail;

    ImportListViewData(String pdt_name, int order_qty, int del_qty){
        this.pdt_name = pdt_name;
        this.pdt_qty = order_qty;
        this.del_qty = del_qty;
    }

    public String getPdt_name() {
        return pdt_name;
    }

    public void setPdt_name(String pdt_name) {
        this.pdt_name = pdt_name;
    }

    public int getPdt_qty() {
        return pdt_qty;
    }

    public void setPdt_qty(int pdt_qty) {
        this.pdt_qty = pdt_qty;
    }

    public int getDel_qty() {
        return del_qty;
    }

    public void setDel_qty(int del_qty) {
        this.del_qty = del_qty;
    }

    public int getPdt_real_qty(){
        return pdt_real_qty;
    }
    public void setPdt_real_qty(int getPdt_real_qty){
        this.pdt_real_qty = pdt_real_qty;
    }

    public String getStorege() {
        return storege;
    }

    public void setStorege(String storege) {
        this.storege = storege;
    }

    public String getStorege_zone() {
        return storege_zone;
    }

    public void setStorege_zone(String storege_zone) {
        this.storege_zone = storege_zone;
    }

    public String getStore_detail() {
        return store_detail;
    }

    public void setStore_detail(String store_detail) {
        this.store_detail = store_detail;
    }
}
