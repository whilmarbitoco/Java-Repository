package org.whilmarbitoco.app.repository;

import org.whilmarbitoco.app.core.Repository;
import org.whilmarbitoco.app.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository extends Repository<User> {

    public UserRepository() {
        super(User.class);
    }
}
