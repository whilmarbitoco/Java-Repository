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
    protected final EntityHelper<T> entityHelper;

    public Repository(String table, Class<T> entity, Connection conn) {
        this.builder = new Builder(table);
        this.mapper = new EntityMapper<>(entity);
        this.streamer = new QueryStreamer<>(entity);
        this.validator = new Validator<>(entity);
        this.entity = entity;
        this.conn = conn;
        this.mapper2 = new Mapper<>(entity);
        this.entityHelper = new EntityHelper<>(entity);
    }

    public final void save(T entity) {
        if (!validator.entitiy(entity)) {
            throw new RuntimeException("All attributes must not be null.");
        }

        String fields = entityHelper.getFields();
        String query = builder.insert(fields).toString();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            mapper2.from(entity, stmt).execute();
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
                T obj = mapper2.to(rs);
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
                T obj = mapper2.to(rs);
                tmp.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tmp.stream().findFirst().orElse(null);
    }

    public final Stream<T> findByField(String field, Object value) {

        String val = validator.field(field);
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
        if (!validator.entitiy(entity)) {
            throw new RuntimeException("All attributes must not be null.");
        }

        String fields = Arrays.stream(entity.getClass().getDeclaredFields())
                .map(Field::getName)
                .filter(name -> !name.equals("id"))
                .collect(Collectors.joining(", "));

    }

}
