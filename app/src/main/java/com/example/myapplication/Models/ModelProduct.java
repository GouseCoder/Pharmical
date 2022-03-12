package com.example.myapplication.Models;

public class ModelProduct {
    int productPrice;
    int productQuantity;
    String productCategory,productName,productSize,productBrand;
    String productLocation,productManufacture,productExpire;

    public ModelProduct() {

    }

    public ModelProduct(int productPrice, int productQuantity, String productCategory, String productName, String productSize, String productBrand, String productLocation, String productManufacture, String productExpire) {
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productCategory = productCategory;
        this.productName = productName;
        this.productSize = productSize;
        this.productBrand = productBrand;
        this.productLocation = productLocation;
        this.productManufacture = productManufacture;
        this.productExpire = productExpire;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }

    public String getProductManufacture() {
        return productManufacture;
    }

    public void setProductManufacture(String productManufacture) {
        this.productManufacture = productManufacture;
    }

    public String getProductExpire() {
        return productExpire;
    }

    public void setProductExpire(String productExpire) {
        this.productExpire = productExpire;
    }
}
