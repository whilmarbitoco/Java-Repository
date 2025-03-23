package org.whilmarbitoco.app.repository;

import org.whilmarbitoco.app.database.model.User;

public class UserRepository extends Repository<User> {
    public UserRepository() {
        super(User.class);
    }
}
