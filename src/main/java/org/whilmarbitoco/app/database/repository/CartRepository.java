package org.whilmarbitoco.app.database.repository;

import org.whilmarbitoco.app.model.Cart;

public class CartRepository extends Repository<Cart>{
    public CartRepository() {
        super(Cart.class);
    }
}
