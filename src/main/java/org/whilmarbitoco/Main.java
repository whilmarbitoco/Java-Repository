package org.whilmarbitoco;

import org.whilmarbitoco.Models.Product;
import org.whilmarbitoco.Models.User;
import org.whilmarbitoco.Repository.ProductRepository;
import org.whilmarbitoco.Repository.UserRepository;

public class Main {

    public static void main(String[] args) {

        ProductRepository prod = new ProductRepository();

//        prod.getAll().forEach(product -> System.out.println(product.getProduct() + " " + product.getPrice()));

//        prod.save(new Product("Guava", 90));


//        prod.getAll().forEach(product -> System.out.println(product.getProduct() + " " + product.getPrice()));

        prod.findByField("product", "Orange").forEach(product -> {
            System.out.println(product.getPrice());
        });
//
//        prod.getAll().forEach(product -> System.out.println(product.getProduct() + " " + product.getPrice()));

    }


}
