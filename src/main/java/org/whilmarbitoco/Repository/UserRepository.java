package org.whilmarbitoco.Repository;


import org.whilmarbitoco.Database.DatabaseConnection;
import org.whilmarbitoco.Models.User;

public class UserRepository extends Repository<User> {

    public UserRepository() {
        super("users", User.class, DatabaseConnection.getConnection());
    }

}
