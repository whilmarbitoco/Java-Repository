package org.whilmarbitoco;


import org.whilmarbitoco.app.database.model.User;
import org.whilmarbitoco.app.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        UserRepository userRepository = new UserRepository();

        User user = new User("John Doe", "johndoe@gmail.com");
        userRepository.create(user);
        System.out.println("User created");

        userRepository.findAll().forEach(u -> {
            System.out.println("ID:: " + u.getId());
            System.out.println("Email:: " + u.getEmail());
            System.out.println("Name:: " + u.getName());
            System.out.println();
        });

        Optional<User> foundUser = userRepository.findByID(208);
        foundUser.ifPresent(value -> System.out.println("User found: " + value.getName()));

        List<User> filteredUsers = userRepository.findWhere("email", "=", "johnDoe@gmail.com");
        filteredUsers.forEach(u -> {
            System.out.println("ID:: " + u.getId());
            System.out.println("Email:: " + u.getEmail());
            System.out.println("Name:: " + u.getName());
            System.out.println();
        });

        if (foundUser.isPresent()) {
            User updatedUser = foundUser.get();
            updatedUser.setEmail("newemail@example.com");
            userRepository.update(updatedUser);
            System.out.println("User updated: " + updatedUser.getEmail());
        }

        userRepository.delete(foundUser.get().getId());
        System.out.println("User with ID 1 deleted.");

    }
}
