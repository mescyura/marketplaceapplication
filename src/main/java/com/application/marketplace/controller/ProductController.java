package com.application.marketplace.controller;

import com.application.marketplace.exception.ProductException;
import com.application.marketplace.model.Product;
import com.application.marketplace.model.User;
import com.application.marketplace.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/marketplace")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("getting all products controller");
        List<Product> products = service.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping(value = "/product", consumes = "application/json")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
        log.info("create a product controller");
        try {
            service.createProduct(product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") long id) {
        log.info("get product by id {} controller", id);
        try {
            return new ResponseEntity<>(service.getProductById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        log.info("delete product by id {} controller", id);
        try {
            service.deleteProduct(id);
            return new ResponseEntity<>("Successfully deleted product by id - " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/who-bought-the-product/{id}")
    public ResponseEntity<?> getListOfUsersTWhoBuyThisProduct(@PathVariable("id") long id) {
        log.info("getting users who bought product with id - {}", id);
        try {
            return new ResponseEntity<>(service.getListOfUsersTWhoBuyThisProduct(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}