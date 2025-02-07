package org.whilmarbitoco.Repository;


import org.whilmarbitoco.Models.User;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Stream;

public class UserRepository extends Repository<User> {


    public UserRepository() {
        super("users", User.class);
    }

}
