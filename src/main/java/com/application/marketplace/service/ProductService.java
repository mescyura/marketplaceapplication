package com.application.marketplace.service;

import com.application.marketplace.exception.ProductException;
import com.application.marketplace.model.Product;

import java.util.List;

public interface ProductService {

    void createProduct(Product product) throws ProductException;

    List<Product> getAllProducts();

    Product getProductById(long id) throws ProductException;

    void deleteProduct(long id) throws ProductException;
}