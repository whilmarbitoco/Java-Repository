package org.whilmarbitoco.Repository;

import org.whilmarbitoco.Core.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract public class Repository<T> {

    protected final Builder builder;
    protected final EntityMapper<T> mapper;
    protected final Connection conn;
    protected final QueryStreamer<T> streamer;
    protected final Validator<T> validator;
    protected final Class<T> entity;
    protected final Mapper<T> mapper2;

    public Repository(String table, Class<T> entity, Connection conn) {
        this.builder = new Builder(table);
        this.mapper = new EntityMapper<>(entity);
        this.streamer = new QueryStreamer<>(entity);
        this.validator = new Validator<T>();
        this.entity = entity;
        this.conn = conn;
        this.mapper2 = new Mapper<>(entity);
    }

    public final void save(T entity) {

        if (!validator.validateEntity(entity)) {
            throw new RuntimeException("All attributes must not be null");
        }

        String fields = Arrays.stream(entity.getClass().getDeclaredFields())
                .map(Field::getName)
                .filter(name -> !name.equals("id"))
                .collect(Collectors.joining(", "));

        String query = builder.insert(fields).toString();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            mapper.map(entity, stmt).execute();
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
                T obj = mapper2.map(rs);
                entities.add(obj);
            }

            return entities.stream();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public final T findById(int id) {
        String query = builder.select().where("id = ?").toString();
        return streamer.streamWithId(query, this.conn, id).findFirst().orElse(null);
    }

    public final Stream<T> findByField(String field, Object value) {

        String val = validator.validateField(this.entity, field);
        if (val == null) {
            throw new RuntimeException();
        }

        String query = builder.select().where(val + " = '" + value + "'").toString();
        
        return streamer.stream(query, this.conn);
    }

    public final void delete(int id) {
        if (findById(id) == null) {
            throw new RuntimeException("ID Not Found.");
        }

        String query = builder.delete().where("id = ?").toString();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(T entity) {
        if (!validator.validateEntity(entity)) {
            throw new RuntimeException("All attributes must not be null.");
        }

        String fields = Arrays.stream(entity.getClass().getDeclaredFields())
                .map(Field::getName)
                .filter(name -> !name.equals("id"))
                .collect(Collectors.joining(", "));

    }

}
