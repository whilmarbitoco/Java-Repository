package org.whilmarbitoco.Repository;

import org.whilmarbitoco.Database.DatabaseConnection;
import org.whilmarbitoco.Models.Product;

import java.sql.Connection;

public class ProductRepository extends Repository<Product> {


    public ProductRepository() {
        super("products", Product.class, DatabaseConnection.getConnection());
    }
}
