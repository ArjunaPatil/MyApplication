package com.example.vin.myapplication;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Primary_Secondary_On_App_Product_List {
//extends RealmObject
//ArrayList
    //http://androidprogramblog.blogspot.com/2017/06/retrofit-with-nested-json-data-get.html
    //https://www.javacodegeeks.com/2013/10/android-json-tutorial-create-and-parse-json-data.html
    //https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects
    //https://www.journaldev.com/2321/gson-example-tutorial-parse-json

    //fragment links
    //http://www.vogella.com/tutorials/AndroidFragments/article.html

    private String sale_qty;
    private String ret_value;
    private String product;
    private String ret_qty;
    private String sale_value;

    public String getSaleQty() {
        return sale_qty;
    }

    public void setSaleQty(String sale_qty) {
        this.sale_qty = sale_qty;
    }

    public String getRetValue() {
        return ret_value;
    }

    public void setRetValue(String ret_value) {
        this.ret_value = ret_value;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getRetQty() {
        return ret_qty;
    }

    public void setRetQty(String ret_qty) {
        this.ret_qty = ret_qty;
    }

    public String getSaleValue() {
        return sale_value;
    }

    public void setSaleValue(String sale_value) {
        this.sale_value = sale_value;
    }

}
