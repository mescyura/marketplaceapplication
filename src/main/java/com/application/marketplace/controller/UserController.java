package com.application.marketplace.controller;

import com.application.marketplace.exception.ProductException;
import com.application.marketplace.exception.UserException;
import com.application.marketplace.model.Product;
import com.application.marketplace.model.User;
import com.application.marketplace.service.UserService;
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
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("getting all users controller");
        List<User> users = service.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/user", consumes = "application/json")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        log.info("create a user controller");
        try {
            service.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
        log.info("get user by id {} controller", id);
        try {
            return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        log.info("delete user by id {} controller", id);
        try {
            service.deleteUser(id);
            return new ResponseEntity<>("Successfully deleted user by id - " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{id}/product-list")
    public ResponseEntity<?> getUserProductList(@PathVariable("id") long id) {
        log.info("getting user with id {} product list controller", id);
        try {
            List<Product> userProductList = service.getUserProductList(id);
            return new ResponseEntity<>(userProductList, HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/user/{taskId}/product/{productId}")
    public ResponseEntity<?> addProductToUser(@PathVariable("taskId") long taskId, @PathVariable("productId") long productId) {
        log.info("create a user controller");
        try {
            service.buyProduct(taskId, productId);
            return new ResponseEntity<>(taskId, HttpStatus.OK);
        } catch (UserException | ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    //TODO
//    @PutMapping(value = "/user/{taskId}/add-products")
//    public ResponseEntity<?> addListOfProducts(@PathVariable("taskId") long taskId, @RequestBody ProductWrapper productList) {
//        log.info("create a user controller");
//        try {
//            service.addListOfProductsToUser(taskId, productList);
//            return new ResponseEntity<>(taskId, HttpStatus.OK);
//        } catch (UserException | ProductException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
//        }
//    }
}

