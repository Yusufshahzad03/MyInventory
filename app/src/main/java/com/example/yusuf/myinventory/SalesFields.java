package com.example.yusuf.myinventory;

/**
 * Created by Yusuf on 18/03/2018.
 */

public class SalesFields {

    private String orderId;
    private  String orderSku;
    private  String orderQuantity;
    private  String orderDate;

    public SalesFields(){

    }

    public  SalesFields(String orderId, String orderSku, String orderQuantity, String orderDate) {

        this.orderId = orderId;
        this.orderSku = orderSku;
        this.orderQuantity = orderQuantity;
        this.orderDate = orderDate;

    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderSku() {
        return orderSku;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public String getOrderDate() {
        return orderDate;
    }


}
