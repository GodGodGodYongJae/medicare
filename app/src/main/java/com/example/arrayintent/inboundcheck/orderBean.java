package com.example.arrayintent.inboundcheck;

import java.util.Date;

public class orderBean {
    private int no;
    private String order_code;
    private String cust_name; // 회사 이름

    private String pdt_name;
    private float qty;
    private float realqty;

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public float getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(float tax_amount) {
        this.tax_amount = tax_amount;
    }

    public float getSupply_amount() {
        return supply_amount;
    }

    public void setSupply_amount(float supply_amount) {
        this.supply_amount = supply_amount;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    private String order_date;
    private float tax_amount;
    private float supply_amount;
    private float total_amount;


    public String getPdt_name() {
        return pdt_name;
    }
    public void setPdt_name(String pdt_name) {
        this.pdt_name = pdt_name;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public float getRealqty() {
        return realqty;
    }

    public void setRealqty(float realqty) {
        this.realqty = realqty;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }
}
