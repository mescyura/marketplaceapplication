package com.application.marketplace.service;

import com.application.marketplace.exception.ProductException;
import com.application.marketplace.exception.UserException;
import com.application.marketplace.model.Product;
import com.application.marketplace.wrapper.ProductWrapper;
import com.application.marketplace.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user) throws ProductException;

    List<User> getAllUsers();

    User getUserById(long id) throws UserException;

    void deleteUser(long id) throws UserException;

    List<Product> getUserProductList(long id) throws UserException;

    void buyProduct(long userId, long productId) throws ProductException, UserException;

    void addListOfProductsToUser(long id, ProductWrapper productList) throws UserException, ProductException;
}