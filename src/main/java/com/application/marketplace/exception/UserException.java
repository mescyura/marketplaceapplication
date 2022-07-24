package com.application.marketplace.exception;

import com.application.marketplace.model.Product;

public class UserException extends Exception {

    public UserException(String message) {
        super(message);
    }

    public static String NotFoundException(long id) {
        return "User with id " + id + " not found!";
    }

    public static String UserAlreadyExists() {
        return "User with given name already exists!";
    }

    public static String UserEmptyList(long id) {
        return "User with given id " + id + " has an empty product list!";
    }

    public static String notEnoughMoney(long id, Product product) {
        return "User with given id " + id + " has nas not enough money for " + product;
    }
}