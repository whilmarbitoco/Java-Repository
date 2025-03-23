package org.whilmarbitoco;


import org.whilmarbitoco.app.database.model.User;
import org.whilmarbitoco.app.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        var userRepository = new UserRepository();

        List<User> test = userRepository.findWhere("name", "=", "test");

        for (User u : test) {
            System.out.println(u.getName());
        }

    }


}
