package org.whilmarbitoco;

import org.whilmarbitoco.Models.Product;
import org.whilmarbitoco.Models.User;
import org.whilmarbitoco.Repository.ProductRepository;
import org.whilmarbitoco.Repository.UserRepository;

public class Main {

    public static void main(String[] args) {

        UserRepository urp = new UserRepository();

       urp.getAll().forEach(user -> System.out.println(user.getEmail()));
    }


}
