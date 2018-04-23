package com.example.yusuf.myinventory;

/**
 * Created by Yusuf on 02/03/2018.
 */

public class ProductInfo {

    private String infoId;
    private  String infoSupplier;
    private  String infoDelivery;
    private  String infoProduct;

    public ProductInfo(){

    }

    public  ProductInfo(String infoId, String infoSupplier, String infoDelivery, String infoProduct) {

        this.infoId = infoId;
        this.infoSupplier = infoSupplier;
        this.infoDelivery =  infoDelivery;
        this.infoProduct = infoProduct;

    }

    public String getInfoId() {
        return infoId;
    }

    public String getInfoSupplier() {
        return infoSupplier;
    }

    public String getInfoDelivery() {
        return infoDelivery;
    }

    public String getInfoProduct() {
        return infoProduct;
    }

}
