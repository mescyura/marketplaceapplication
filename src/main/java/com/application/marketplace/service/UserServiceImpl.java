package com.application.marketplace.service;

import com.application.marketplace.exception.ProductException;
import com.application.marketplace.exception.UserException;
import com.application.marketplace.model.Product;
import com.application.marketplace.repository.ProductRepository;
import com.application.marketplace.wrapper.ProductWrapper;
import com.application.marketplace.model.User;
import com.application.marketplace.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public void addUser(User user) {
        userRepository.save(user);
        log.info("user -  {} successfully created", user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.size() > 0) {
            log.info("successfully loaded users {} ", users);
            return users;
        } else {
            log.warn("no users in the database");
            return new ArrayList<>();
        }
    }

    @Override
    public User getUserById(long id) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            log.info("found user - {}", optionalUser.get());
            return optionalUser.get();
        } else {
            log.warn("no user with id - {}", id);
            throw new UserException(UserException.NotFoundException(id));
        }
    }

    @Override
    public void deleteUser(long id) throws UserException {
        log.info("searching for user with id - {}, to delete", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            log.warn("product with id - {} not found", id);
            throw new UserException(UserException.NotFoundException(id));
        } else {
            log.info("product with id - {} successfully deleted", id);
            userRepository.deleteById(id);
        }
    }

    @Override
    public List<Product> getUserProductList(long id) throws UserException {
        log.info("searching for user with id - {}, to get his product list", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            log.info("found user - {}", optionalUser.get());
            if (!optionalUser.get().getProductList().isEmpty()) {
                return optionalUser.get().getProductList();
            } else {
                log.warn("user product list with id - {} is empty ", id);
                throw new UserException(UserException.UserEmptyList(id));
            }
        } else {
            log.warn("no user with id - {}", id);
            throw new UserException(UserException.NotFoundException(id));
        }
    }

    @Override
    public void buyProduct(long userId, long productId) throws UserException, ProductException {
        log.info("searching for user with id - {}, to get his product list", userId);
        User user;
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalUser.isPresent() && optionalProduct.isPresent()) {
            log.info("found user - {} , and product - {}", optionalUser.get(), optionalProduct.get());
            if (optionalUser.get().getMoney() - optionalProduct.get().getPrice() >= 0) {
                user = optionalUser.get();
                user.setMoney(user.getMoney() - optionalProduct.get().getPrice());
                user.getProductList().add(optionalProduct.get());
                userRepository.save(user);
            } else {
                log.warn("user with id - {} has not enough money for product - {} ", userId, optionalProduct.get());
                throw new UserException(UserException.notEnoughMoney(userId,optionalProduct.get()));
            }
        } else if (optionalUser.isPresent()) {
            log.warn("no user with id - {}", userId);
            throw new UserException(UserException.NotFoundException(userId));
        } else {
            log.warn("no product with id - {}", userId);
            throw new ProductException(ProductException.NotFoundException(productId));
        }
    }

    @Override // TODO
    public void addListOfProductsToUser(long id, ProductWrapper productList) throws UserException {
//        User userById = getUserById(id);
//        userById.getProductList().addAll(productList.getProductList());
//        userRepository.save(userById);
    }
}