package org.whilmarbitoco.app.repository;


import org.whilmarbitoco.app.database.connection.DBConnection;
import org.whilmarbitoco.app.util.Builder;
import org.whilmarbitoco.app.util.Entity;
import org.whilmarbitoco.app.util.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            mapper.from(entity, stmt).execute();
        } catch (SQLException err) {
           throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }

    public List<T> findAll() {
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

    public Optional<T> findByID(int id) {
        Optional<String> primaryKey = entityManager.getPrimaryKey();

        if (primaryKey.isEmpty()) throw new RuntimeException("[Repository] Empty primary key for " + entityManager.getTable());
        String query = builder.select(entityManager.getTable()).where(primaryKey.get() + " = ?").build();

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


    public List<T> findWhere(String column, String condition, Object value) {
        entityManager.isValidColumn(column);
        entityManager.isValidCondition(condition);

        String query = builder.select(entityManager.getTable()).where(column + " " + condition + " ?").build();
        List<T> result = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, value);
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                result.add(mapper.to(res));
            }

            return result;
        } catch (SQLException err) {
            throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }

    public void update(T entity) {
        Optional<String> primaryKey = entityManager.getPrimaryKey();
        if (primaryKey.isEmpty())
            throw new RuntimeException("[Repository] Empty primary key for " + entityManager.getTable());

        String query = builder.update(entityManager.getTable(), entityManager.getColumns())
                .where(primaryKey.get() + " = " + entityManager.getPrimaryKeyValue(entity)).build();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            mapper.from(entity, stmt).execute();
        } catch (SQLException e) {
            throw new RuntimeException("[Repository] " + e.getMessage());


        }

    }

    public void delete(int id) {
        Optional<String> primaryKey = entityManager.getPrimaryKey();

        if (primaryKey.isEmpty()) throw new RuntimeException("[Repository] Empty primary key for " + entityManager.getTable());
        String query = builder.delete(entityManager.getTable()).where(primaryKey.get() + " = ?").build();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.execute();

        } catch (SQLException err) {
            throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }
}
