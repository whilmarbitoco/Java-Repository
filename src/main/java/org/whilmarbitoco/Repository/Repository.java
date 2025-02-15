package org.whilmarbitoco.Repository;


import org.whilmarbitoco.Core.Builder;
import org.whilmarbitoco.Core.EntityHelper;
import org.whilmarbitoco.Core.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

abstract public class Repository<T> {

    protected final Builder builder;
    protected final Connection conn;
    protected final Class<T> entity;
    protected final Mapper<T> mapper;
    protected final EntityHelper<T> entityHelper;

    public Repository(String table, Class<T> entity, Connection conn) {
        this.entity = entity;
        this.conn = conn;
        this.builder = new Builder(table);
        this.mapper = new Mapper<>(entity);
        this.entityHelper = new EntityHelper<>(entity);
    }

    public final boolean save(T entity) {
        if (!entityHelper.validateAllFields(entity)) throw new RuntimeException("All attributes must not be null.");

        String fields = entityHelper.getFields();
        String query = builder.insert(fields).toString();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            return mapper.from(entity, stmt).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public final Stream<T> getAll() {
        List<T> entities = new ArrayList<>();
        String query = builder.select().toString();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                T obj = mapper.to(rs);
                entities.add(obj);
            }

            return entities.stream();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public final T findById(int id)  {
        List<T> tmp = new ArrayList<>();
        String query = builder.select().where("id = ?").toString();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                T obj = mapper.to(rs);
                tmp.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tmp.stream().findFirst().orElse(null);
    }

    public final Stream<T> findWhere(String field, String condition, Object value) {
        if (!entityHelper.validateField(field)) throw new RuntimeException();

        List<T> entities = new ArrayList<>();

        String query = builder.select().where(field + " " + condition + " ?").toString();
        try(PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, value);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                T obj = mapper.to(rs);
                entities.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entities.stream();
    }

    public final boolean delete(int id) {
        if (findById(id) == null) throw new RuntimeException("ID Not Found.");

        String query = builder.delete().where("id = ?").toString();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            return stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public final boolean update(int id, T entity) {
        if (!entityHelper.validateAllFields(entity)) throw new RuntimeException("All attributes must not be null.");

        String fields = entityHelper.getFieldForUpdate();

        String query = builder.update(fields).where("id = " + id).toString();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            return mapper.from(entity, stmt).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
