package org.whilmarbitoco.app.repository;


import org.whilmarbitoco.app.database.connection.DBConnection;
import org.whilmarbitoco.app.util.Builder;
import org.whilmarbitoco.app.util.Entity;
import org.whilmarbitoco.app.util.Mapper;
import org.whilmarbitoco.app.util.QueryResult;

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
            PreparedStatement test = mapper.from(entity, stmt);
            System.out.println("QUERY:: " + test.toString());
            test.execute();
        } catch (SQLException err) {
           throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }

    public List<T> findAll() {
        String query = builder.select(entityManager.getTable()).build();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            return executeQuery(stmt).list();
        } catch (SQLException err) {
            throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }

    public Optional<T> findByID(int id) {
        Optional<String> primaryKey = entityManager.getPrimaryKey();

        if (primaryKey.isEmpty()) throw new RuntimeException("[Repository] Empty primary key for " + entityManager.getTable());
        String query = builder.select(entityManager.getTable()).where(primaryKey.get() + " = ?").build();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            return Optional.ofNullable(executeQuery(stmt).firstResult());
        } catch (SQLException err) {
            throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }


    public List<T> findWhere(String column, String condition, Object value) {
        entityManager.isValidColumn(column);
        entityManager.isValidCondition(condition);

        String query = builder.select(entityManager.getTable()).where(column + " " + condition + " ?").build();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, value);
           return executeQuery(stmt).list();
        } catch (SQLException err) {
            throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }

    public List<T> findLike(String column, Object value) {
        entityManager.isValidColumn(column);

        String query = builder.select(entityManager.getTable()).where(column).like("?").build();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, "%" + value + "%");
            return executeQuery(stmt).list();
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
            PreparedStatement test = mapper.from(entity, stmt);
            System.out.println("QUERY:: " + test.toString());
            test.execute();
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
            System.out.println("QUERY:: " + stmt.toString());
            stmt.execute();

        } catch (SQLException err) {
            throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }

    protected QueryResult<T> executeQuery(PreparedStatement stmt) {
        try {
            List<T> result = new ArrayList<>();

            System.out.println("QUERY:: " + stmt.toString());
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                result.add(mapper.to(res));
            }

            return new QueryResult<T>(result);
        } catch (SQLException err) {
            throw new RuntimeException("[Repository] Failed to execute due to -> " + err.getMessage());
        }
    }

}
