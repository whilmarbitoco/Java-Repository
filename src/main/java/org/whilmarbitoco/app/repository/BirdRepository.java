package org.whilmarbitoco.app.repository;

import org.whilmarbitoco.app.database.model.Bird;

public class BirdRepository extends Repository<Bird> {
    public BirdRepository() {
        super(Bird.class);
    }
}
