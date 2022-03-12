package com.example.myapplication.Models;

public class ModelCount {
    private int productsCount,productsNoticeCount,brandsCount;

    public ModelCount(int productsCount, int productsNoticeCount, int brandsCount) {
        this.productsCount = productsCount;
        this.productsNoticeCount = productsNoticeCount;
        this.brandsCount = brandsCount;
    }

    public int getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(int productsCount) {
        this.productsCount = productsCount;
    }

    public int getProductsNoticeCount() {
        return productsNoticeCount;
    }

    public void setProductsNoticeCount(int productsNoticeCount) {
        this.productsNoticeCount = productsNoticeCount;
    }

    public int getBrandsCount() {
        return brandsCount;
    }

    public void setBrandsCount(int brandsCount) {
        this.brandsCount = brandsCount;
    }
}
