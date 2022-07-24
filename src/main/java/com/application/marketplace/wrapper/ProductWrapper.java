package com.application.marketplace.wrapper;

import com.application.marketplace.model.Product;

import java.util.List;

public class ProductWrapper {
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}