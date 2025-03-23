package org.whilmarbitoco;


import org.whilmarbitoco.app.database.model.User;
import org.whilmarbitoco.app.repository.UserRepository;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        var userRepository = new UserRepository();


//        List<User> students = userRepository.all();
//
//        for (User s : students) {
//            System.out.println(s.getEmail());
//            System.out.println(s.getName());
//            System.out.println(s.getId());
//            System.out.println("-------------------------");
//        }
//
        System.out.println("============");
        User user = userRepository.findByID(203).get();

        System.out.println(user.getName());
    }


}
