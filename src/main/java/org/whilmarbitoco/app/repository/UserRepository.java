package org.whilmarbitoco.app.repository;

import org.whilmarbitoco.app.core.Repository;
import org.whilmarbitoco.app.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository extends Repository<User> {
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public UserRepository() {
        super(User.class);
        roleRepository = new RoleRepository();
        cartRepository = new CartRepository();
        productRepository = new ProductRepository();
    }

    public Optional<Role> getRole(int id) {
        Optional<User> user = findByID(id);
        if (user.isEmpty()) return Optional.empty();

        return roleRepository.findByID(user.get().getId());
    }

    public List<Product> cart(int userID) {
        List<Product> products = new ArrayList<>();
        Optional<User> user = findByID(userID);
        if (user.isEmpty()) throw new RuntimeException("User Not Found");

        List<Cart> cart = cartRepository.findWhere("user_id", "=", userID);
        for (Cart c : cart) {
            Optional<Product> product = productRepository.findByID(c.getBookID());
            product.ifPresent(products::add);
        }

        return products;
    }
}
