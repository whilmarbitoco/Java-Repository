package org.whilmarbitoco.app.model;

import org.whilmarbitoco.app.database.anotation.Column;
import org.whilmarbitoco.app.database.anotation.Primary;
import org.whilmarbitoco.app.database.anotation.Table;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;


@Table(name = "Cart")
public class Cart {

    @Primary
    @Column(name = "user_id")
    private int userID;

    @Primary
    @Column(name = "product_id")
    private int bookID;

    @Column(name = "created_at")
    private Timestamp color;

    public Cart() {
    }

    public int getUserID() {
        return userID;
    }

    public int getBookID() {
        return bookID;
    }

    public Timestamp getColor() {
        return color;
    }

    public void setColor(Timestamp color) {
        this.color = color;
    }
}
