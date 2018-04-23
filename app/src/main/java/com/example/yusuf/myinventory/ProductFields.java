package com.example.yusuf.myinventory;

/**
 * Created by Yusuf on 19/02/2018.
 */

public class ProductFields {

    String productId;
    String productName;
    String productCategory;
    String productPrice;
    String productAmount;

    public ProductFields(){

    }

    public ProductFields(String productId, String productName, String productCategory, String productPrice, String productAmount) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productAmount = productAmount;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductAmount() {
        return productAmount;
    }
}
