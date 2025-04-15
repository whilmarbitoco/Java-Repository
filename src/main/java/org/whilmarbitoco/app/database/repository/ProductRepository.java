package org.whilmarbitoco.app.database.repository;

import org.whilmarbitoco.app.model.Product;

public class ProductRepository extends Repository<Product> {
    public ProductRepository() {
        super(Product.class);
    }
}
