package org.whilmarbitoco.app.model;

import org.whilmarbitoco.app.database.anotation.Column;
import org.whilmarbitoco.app.database.anotation.Primary;
import org.whilmarbitoco.app.database.anotation.Table;

@Table(name = "Role")
public class Role {
    @Primary
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    private String role;

    public Role() {}

    public Role(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
