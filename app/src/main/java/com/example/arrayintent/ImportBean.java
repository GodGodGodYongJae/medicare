package com.example.arrayintent;

import java.sql.Date;

public class ImportBean {

    private int NO;	//번호
    private int pdt_cd;//상품fk
    private String pdt_code;//상품 코드
    private String pdt_name;	//상품명
    private String pdt_standard;	//상품 규격
    private int unit_price;	//단가
    private int real_good_qty;// 입고 된거 출고 수량
    private int bad_qty;//불량 수량
    private String unit;	//단위
    private String from_type;//from(c거래처s창고l라인)
    private String to_type;//to(c거래처s창고l라인)
    private String bigo;	//비고

    //inoutmaster
    private int io_seq;//pk
    private String io_type;//입출고 구분
    private int io_code;//입출고 코드
    private int cust_seq;//거래처
    private String io_date;//입고일
    private String io_status;//입출고 상태
    private int manager_seq;//담당자
    private String reg_date;//작성일
    private String mod_date;//수정일
    private String delete_date;//삭제일

    //orderdetail
    private String order_code;	//발주코드
    //private int pdt_cd;//상품fk
    //private String pdt_code;//상품 코드
    //private String pdt_name;	//상품명
    //private String pdt_standard;	//상품 규격
    private int qty;//수량?
    //private String unit;	//단위
    //private int unit_price;//단가
    private int supply_price;	//공급가
    private int vat;	//세액

    //ordermaster
    private int order_seq;//오더 스퀸스
    //private String order_code;	//발주코드
    private String supply_manager_name;	//공급처 담당자명
    private String order_addr;	//주소
    private String order_date;	//발주 날짜
    private String close_date; //납기마감 일자
    private String manager_name;//
    private int supply_amount;	//공급가액
    private int tax_amount;	//세액
    private int total_amount;	//총액
    private String order_status;//발주상태?

    //scm_deliverydetail
    private int pdt_qty; //상품발주 수량?
    private int pdt_real_qty;	//실수량??

    //scm_deliverymaster
    private int del_seq;//
    private String del_status;
    private String trading_qr_filecode;

    public int getNO() {
        return NO;
    }

    public void setNO(int NO) {
        this.NO = NO;
    }

    public int getPdt_cd() {
        return pdt_cd;
    }

    public void setPdt_cd(int pdt_cd) {
        this.pdt_cd = pdt_cd;
    }

    public String getPdt_code() {
        return pdt_code;
    }

    public void setPdt_code(String pdt_code) {
        this.pdt_code = pdt_code;
    }

    public String getPdt_name() {
        return pdt_name;
    }

    public void setPdt_name(String pdt_name) {
        this.pdt_name = pdt_name;
    }

    public String getPdt_standard() {
        return pdt_standard;
    }

    public void setPdt_standard(String pdt_standard) {
        this.pdt_standard = pdt_standard;
    }

    public int getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }

    public int getReal_good_qty() {
        return real_good_qty;
    }

    public void setReal_good_qty(int real_good_qty) {
        this.real_good_qty = real_good_qty;
    }

    public int getBad_qty() {
        return bad_qty;
    }

    public void setBad_qty(int bad_qty) {
        this.bad_qty = bad_qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFrom_type() {
        return from_type;
    }

    public void setFrom_type(String from_type) {
        this.from_type = from_type;
    }

    public String getTo_type() {
        return to_type;
    }

    public void setTo_type(String to_type) {
        this.to_type = to_type;
    }

    public String getBigo() {
        return bigo;
    }

    public void setBigo(String bigo) {
        this.bigo = bigo;
    }

    public int getIo_seq() {
        return io_seq;
    }

    public void setIo_seq(int io_seq) {
        this.io_seq = io_seq;
    }

    public String getIo_type() {
        return io_type;
    }

    public void setIo_type(String io_type) {
        this.io_type = io_type;
    }

    public int getIo_code() {
        return io_code;
    }

    public void setIo_code(int io_code) {
        this.io_code = io_code;
    }

    public int getCust_seq() {
        return cust_seq;
    }

    public void setCust_seq(int cust_seq) {
        this.cust_seq = cust_seq;
    }

    public String getIo_date() {
        return io_date;
    }

    public void setIo_date(String io_date) {
        this.io_date = io_date;
    }

    public String getIo_status() {
        return io_status;
    }

    public void setIo_status(String io_status) {
        this.io_status = io_status;
    }

    public int getManager_seq() {
        return manager_seq;
    }

    public void setManager_seq(int manager_seq) {
        this.manager_seq = manager_seq;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getMod_date() {
        return mod_date;
    }

    public void setMod_date(String mod_date) {
        this.mod_date = mod_date;
    }

    public String getDelete_date() {
        return delete_date;
    }

    public void setDelete_date(String delete_date) {
        this.delete_date = delete_date;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getSupply_price() {
        return supply_price;
    }

    public void setSupply_price(int supply_price) {
        this.supply_price = supply_price;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public int getOrder_seq() {
        return order_seq;
    }

    public void setOrder_seq(int order_seq) {
        this.order_seq = order_seq;
    }

    public String getSupply_manager_name() {
        return supply_manager_name;
    }

    public void setSupply_manager_name(String supply_manager_name) {
        this.supply_manager_name = supply_manager_name;
    }

    public String getOrder_addr() {
        return order_addr;
    }

    public void setOrder_addr(String order_addr) {
        this.order_addr = order_addr;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getClose_date() {
        return close_date;
    }

    public void setClose_date(String close_date) {
        this.close_date = close_date;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public int getSupply_amount() {
        return supply_amount;
    }

    public void setSupply_amount(int supply_amount) {
        this.supply_amount = supply_amount;
    }

    public int getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(int tax_amount) {
        this.tax_amount = tax_amount;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public int getPdt_qty() {
        return pdt_qty;
    }

    public void setPdt_qty(int pdt_qty) {
        this.pdt_qty = pdt_qty;
    }

    public int getPdt_real_qty() {
        return pdt_real_qty;
    }

    public void setPdt_real_qty(int pdt_real_qty) {
        this.pdt_real_qty = pdt_real_qty;
    }

    public int getDel_seq() {
        return del_seq;
    }

    public void setDel_seq(int del_seq) {
        this.del_seq = del_seq;
    }

    public String getDel_status() {
        return del_status;
    }

    public void setDel_status(String del_status) {
        this.del_status = del_status;
    }

    public String getTrading_qr_filecode() {
        return trading_qr_filecode;
    }

    public void setTrading_qr_filecode(String trading_qr_filecode) {
        this.trading_qr_filecode = trading_qr_filecode;
    }
}
