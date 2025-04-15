package org.whilmarbitoco.app.database.repository;


import org.whilmarbitoco.app.database.connection.DBConnection;
import org.whilmarbitoco.app.util.Builder;
import org.whilmarbitoco.app.util.EntityManager;
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

    protected EntityManager<T> entityManager;
    protected Builder builder;
    protected Mapper<T> mapper;
    protected String tableName;
    protected List<String> columns;

    public Repository(Class<T> type) {
        this.entityManager = new EntityManager<>(type);
        this.builder = new Builder();
        this.mapper = new Mapper<>(type);
        this.tableName = entityManager.getTable();
        this.columns = entityManager.getColumns();
    }


    public final void create(T entity) {
        entityManager.validate(entity);
        String query = builder.insert(tableName, columns).build();
        execute(entity, query);
    }

    public List<T> findAll() {
        String query = builder.select(tableName).build();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            return executeQuery(stmt).list();
        } catch (SQLException err) {
            throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }

    public Optional<T> findByID(int id) {
        Optional<String> primaryKey = entityManager.getPrimaryKey();

        if (primaryKey.isEmpty())
            throw new RuntimeException("[Repository] Empty primary key for " + entityManager.getTable());

        String query = builder.select(tableName).where(primaryKey.get() + " = ?").build();

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

        String query = builder.select(tableName).where(column + " " + condition + " ?").build();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, value);
           return executeQuery(stmt).list();
        } catch (SQLException err) {
            throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }

    public List<T> findLike(String column, Object value) {
        entityManager.isValidColumn(column);

        String query = builder.select(tableName).where(column).like("?").build();

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

        Object pkValue = entityManager.getPrimaryKeyValue(entity);

        String query = builder.update(tableName, columns)
                .where(primaryKey.get() + " = " + pkValue).build();

        execute(entity, query);
    }

    public void delete(int id) {
        Optional<String> primaryKey = entityManager.getPrimaryKey();

        if (primaryKey.isEmpty())
            throw new RuntimeException("[Repository] Empty primary key for " + entityManager.getTable());

        String query = builder.delete(tableName).where(primaryKey.get() + " = ?").build();

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
                result.add(mapper.toEntity(res));
            }

            return new QueryResult<T>(result);
        } catch (SQLException err) {
            throw new RuntimeException("[Repository] Failed to execute due to -> " + err.getMessage());
        }
    }

    protected void execute(T entity, String query) {
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            PreparedStatement pstmt = mapper.fromEntity(entity, stmt);
            System.out.println("QUERY:: " + pstmt.toString());
            pstmt.execute();
        } catch (SQLException err) {
            throw new RuntimeException("[Repository] SQL Error:: " + err.getMessage());
        }
    }

}
