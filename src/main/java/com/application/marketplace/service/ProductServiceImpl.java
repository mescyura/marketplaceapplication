package com.application.marketplace.service;


import com.application.marketplace.exception.ProductException;
import com.application.marketplace.model.Product;
import com.application.marketplace.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;

    @Override
    public void createProduct(Product product) throws ProductException {
        Optional<Product> optionalTodo = repository.findProductByName(product.getName());
        if ((optionalTodo.isPresent())) {
            log.warn("there is an existing product with {} name already", product.getName());
            throw new ProductException(ProductException.ProductAlreadyExists());
        } else {
            repository.save(product);
            log.info("product -  {} successfully created", product);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = repository.findAll();
        if (products.size() > 0) {
            log.info("successfully loaded products {} ", products);
            return products;
        } else {
            log.warn("no products in the database");
            return new ArrayList<>();
        }
    }

    @Override
    public Product getProductById(long id) throws ProductException {
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isPresent()) {
            log.info("found product - {}", optionalProduct.get());
            return optionalProduct.get();
        } else {
            log.warn("no product with id - {}", id);
            throw new ProductException(ProductException.NotFoundException(id));
        }
    }

    @Override
    public void deleteProduct(long id) throws ProductException {
        log.info("searching for product with id - {}, to delete", id);
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isEmpty()) {
            log.warn("product with id - {} not found", id);
            throw new ProductException(ProductException.NotFoundException(id));
        } else {
            log.info("product with id - {} successfully deleted", id);
            repository.deleteById(id);
        }
    }
}