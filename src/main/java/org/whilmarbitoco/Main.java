package org.whilmarbitoco;


import org.whilmarbitoco.app.repository.UserRepository;

public class Main {

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();

        userRepository.findAll().forEach(user -> {
            System.out.println(user.getEmail());
        });

    }
}
