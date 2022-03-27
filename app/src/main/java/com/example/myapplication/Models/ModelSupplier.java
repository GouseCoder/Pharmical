package com.example.myapplication.Models;

public class ModelSupplier {
    private String sellerName,sellerEmail,sellerContactNumber,sellerAddress, distributingProduct;

    public ModelSupplier() {
    }

    public ModelSupplier(String sellerName, String sellerEmail, String sellerContactNumber, String sellerAddress, String distributingProduct) {
        this.sellerName = sellerName;
        this.sellerEmail = sellerEmail;
        this.sellerContactNumber = sellerContactNumber;
        this.sellerAddress = sellerAddress;
        this.distributingProduct = distributingProduct;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getSellerContactNumber() {
        return sellerContactNumber;
    }

    public void setSellerContactNumber(String sellerContactNumber) {
        this.sellerContactNumber = sellerContactNumber;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getDistributingProduct() {
        return distributingProduct;
    }

    public void setDistributingProduct(String distributingProduct) {
        this.distributingProduct = distributingProduct;
    }
}
