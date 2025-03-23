package org.whilmarbitoco.app.repository;


import org.whilmarbitoco.app.anotation.Primary;
import org.whilmarbitoco.app.anotation.Table;
import org.whilmarbitoco.app.database.connection.DBConnection;
import org.whilmarbitoco.app.util.Builder;
import org.whilmarbitoco.app.util.Entity;
import org.whilmarbitoco.app.util.Mapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Repository<T> {

    private final Connection connection = DBConnection.getConnection();

    protected Entity<T> entityManager;
    protected Builder builder;
    protected Mapper<T> mapper;

    private Class<T> type;

    public Repository(Class<T> type) {
        this.entityManager = new Entity<>(type);
        this.builder = new Builder();
        this.mapper = new Mapper<>(type);
        this.type = type;
    }

    public final void create(T entity) {
        entityManager.validate(entity);

        String query = builder.insert(entityManager.getTable(), entityManager.getColumns()).build();
        System.out.println(query);
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            mapper.from(entity, stmt).execute();
        } catch (SQLException err) {
           throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }

    public Optional<T> findByID(int id) {
        String query = builder.select(entityManager.getTable()).where("id = ?").build();
        System.out.println(query);
        List<T> result = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                result.add(mapper.to(res));
            }

            return Optional.of(result.getFirst());
        } catch (SQLException err) {
            throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }

    public List<T> all() {
        String query = builder.select(entityManager.getTable()).build();
        List<T> result = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                result.add(mapper.to(res));
            }

            return result;
        } catch (SQLException err) {
            throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }

}
