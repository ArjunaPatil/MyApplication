package com.example.vin.myapplication;

import java.util.ArrayList;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Primary_Secondary_On_App {
//extends RealmObject

    //http://androidprogramblog.blogspot.com/2017/06/retrofit-with-nested-json-data-get.html
    //https://www.javacodegeeks.com/2013/10/android-json-tutorial-create-and-parse-json-data.html
    //https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects
    //https://www.journaldev.com/2321/gson-example-tutorial-parse-json

    //fragment links
    //http://www.vogella.com/tutorials/AndroidFragments/article.html

    private Integer tot_sale_value;

    //private RealmList<POJO_Primary_Secondary_On_App_Product_List> product_data = null;
    public ArrayList<POJO_Primary_Secondary_On_App_Product_List> product_data = null;


    private Integer tot_ret_value;
    //@SerializedName("tot_ret_value")
    private String tot_emp;
    //@SerializedName("tot_ret_value")
    private String stockist;
    //@SerializedName("tot_ret_value")
    private Integer tot_sale_qty;
    //@SerializedName("tot_ret_value")
    private String to_date;
    //@SerializedName("tot_ret_value")
    private Integer tot_ret_qty;
    //@SerializedName("fromDate")
    private String from_date;
    //@SerializedName("emp")
    private String emp;
    //@SerializedName("employee")
    private String employee;
    private String full_name;

    public Integer getTotSaleValue() {
        return tot_sale_value;
    }

    public void setTotSaleValue(Integer tot_sale_value) {
        this.tot_sale_value = tot_sale_value;
    }

    public ArrayList<POJO_Primary_Secondary_On_App_Product_List> getProductData() {
        return product_data;
    }

    public void setProductData(ArrayList<POJO_Primary_Secondary_On_App_Product_List> product_data) {
        this.product_data = product_data;
    }

    /*public RealmList<Primary_secondary_Product_List> getProductData() {
        return productData;
    }

    public void setProductData(RealmList<Primary_secondary_Product_List> productData) {
        this.productData = productData;
    }*/

    public Integer getTotRetValue() {
        return tot_ret_value;
    }

    public void setTotRetValue(Integer tot_ret_value) {
        this.tot_ret_value = tot_ret_value;
    }

    public String getTotEmp() {
        return tot_emp;
    }

    public void setTotEmp(String tot_emp) {
        this.tot_emp = tot_emp;
    }

    public String getStockist() {
        return stockist;
    }

    public void setStockist(String stockist) {
        this.stockist = stockist;
    }

    public Integer getTotSaleQty() {
        return tot_sale_qty;
    }

    public void setTotSaleQty(Integer tot_sale_qty) {
        this.tot_sale_qty = tot_sale_qty;
    }

    public String getToDate() {
        return to_date;
    }

    public void setToDate(String to_date) {
        this.to_date = to_date;
    }

    public Integer getTotRetQty() {
        return tot_ret_qty;
    }

    public void setTotRetQty(Integer tot_ret_qty) {
        this.tot_ret_qty = tot_ret_qty;
    }

    public String getFromDate() {
        return from_date;
    }

    public void setFromDate(String from_date) {
        this.from_date = from_date;
    }

    public String getEmp() {
        return emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }


    /*public class Primary_secondary_Product_List extends RealmObject {

        private String saleQty;
        private String retValue;
        private String product;
        private String retQty;
        private String saleValue;

        public String getSaleQty() {
            return saleQty;
        }

        public void setSaleQty(String saleQty) {
            this.saleQty = saleQty;
        }

        public String getRetValue() {
            return retValue;
        }

        public void setRetValue(String retValue) {
            this.retValue = retValue;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getRetQty() {
            return retQty;
        }

        public void setRetQty(String retQty) {
            this.retQty = retQty;
        }

        public String getSaleValue() {
            return saleValue;
        }

        public void setSaleValue(String saleValue) {
            this.saleValue = saleValue;
        }

    }*/

}
