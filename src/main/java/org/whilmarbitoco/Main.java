package org.whilmarbitoco;

import org.whilmarbitoco.Models.User;
import org.whilmarbitoco.Repository.UserRepository;

public class Main {

    public static void main(String[] args) {

        UserRepository repo = new UserRepository();

//        repo.save(new User("John", "johnny@gmail.com"));

        repo.getAll().forEach(user -> System.out.println(user.getEmail() + " " + user.getId()));
        System.out.println();
        System.out.println(repo.find(1).getEmail());

        repo.delete(2);
        System.out.println();

        repo.getAll().forEach(user -> System.out.println(user.getEmail() + " " + user.getId()));

    }


}
