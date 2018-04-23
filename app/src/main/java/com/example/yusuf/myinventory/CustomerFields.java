package com.example.yusuf.myinventory;

/**
 * Created by Yusuf on 18/03/2018.
 */

public class CustomerFields {



    String customerId;
    String customerName;
    String customerAddress;
    String customerEmail;
    String customerTel;
    String customerDate;

    public CustomerFields(){

    }

    public CustomerFields(String customerId, String customerName, String customerAddress, String customerEmail, String customerTel, String customerDate) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.customerTel = customerTel;
        this.customerDate = customerDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public String getCustomerDate() {
        return customerDate;
    }
}






