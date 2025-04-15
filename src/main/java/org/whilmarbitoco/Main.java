package org.whilmarbitoco;


import org.whilmarbitoco.app.database.repository.CartRepository;
import org.whilmarbitoco.app.database.repository.ProductRepository;
import org.whilmarbitoco.app.database.repository.UserRepository;
import org.whilmarbitoco.app.model.User;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        UserRepository userRepository = new UserRepository();
        ProductRepository productRepository = new ProductRepository();
        CartRepository cartRepository = new CartRepository();


        userRepository.cart(1).forEach(e -> {
            System.out.println(e.getId() + " - " + e.getTitle());
        });




    }
}
