package org.whilmarbitoco;

import org.whilmarbitoco.Models.Product;
import org.whilmarbitoco.Models.User;
import org.whilmarbitoco.Repository.ProductRepository;
import org.whilmarbitoco.Repository.UserRepository;

public class Main {

    public static void main(String[] args) {

        UserRepository urp = new UserRepository();

       User u = new User("Bobbyyyyy", "boby@gmail.com");
//
//       urp.save(u);

       urp.getAll().forEach(user -> System.out.println(user.getName() + " " + user.getId()));



    }


}
