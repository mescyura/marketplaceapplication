package com.application.marketplace.exception;

public class ProductException extends Exception {

    public ProductException(String message) {
        super(message);
    }

    public static String NotFoundException(long id) {
        return "Product with id " + id + " not found!";
    }

    public static String ProductAlreadyExists() {
        return "Product with given name already exists!";
    }

    public static String ProductHasNoUsersList(long id) {
        return "Product with id " + id + " no one bought";
    }
}