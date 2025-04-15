package org.whilmarbitoco.app.model;

import org.whilmarbitoco.app.database.anotation.Column;
import org.whilmarbitoco.app.database.anotation.Primary;
import org.whilmarbitoco.app.database.anotation.Table;

@Table(name = "product")
public class Product {
    @Primary
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    public Product() {}

    public Product(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
