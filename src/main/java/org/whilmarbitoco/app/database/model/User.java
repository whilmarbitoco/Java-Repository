package org.whilmarbitoco.app.database.model;


import org.whilmarbitoco.app.anotation.Column;
import org.whilmarbitoco.app.anotation.Primary;
import org.whilmarbitoco.app.anotation.Table;

@Table(name = "User")
public class User {

    @Primary
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String username;

    @Column(name = "email")
    private String email;



    public User() {}

    public User(String name, String email) {
        this.username = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
