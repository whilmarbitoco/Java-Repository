package org.whilmarbitoco.Repository;

import org.whilmarbitoco.Core.Builder;
import org.whilmarbitoco.Core.EntityMapper;
import org.whilmarbitoco.Core.QueryStreamer;
import org.whilmarbitoco.Database.DatabaseConnection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract public class Repository<T> {

    protected final Builder builder;
    protected final EntityMapper<T> mapper;
    protected final Connection conn;
    protected final QueryStreamer<T> streamer;

    public Repository(String table, Class<T> entity) {
        this.builder = new Builder(table);
        this.mapper = new EntityMapper<>(entity);
        this.conn = DatabaseConnection.getConnection();
        this.streamer = new QueryStreamer<>(entity);

    }

    public final void save(T entity) {

        String fields = Arrays.stream(entity.getClass().getDeclaredFields())
                .map(Field::getName)
                .filter(name -> !name.equals("id"))
                .collect(Collectors.joining(", "));

        try (PreparedStatement stmt = conn.prepareStatement(builder.insert(fields).toString())) {
            mapper.map(entity, stmt).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public final Stream<T> getAll() {
        return streamer.stream(builder.select().toString(), this.conn);
    }

    public final T find(int id) {
        return streamer.streamWithId(builder.select().where("id = ?").toString(), this.conn, id).findFirst().orElse(null);
    }

    public final void delete(int id) {
        if (find(id) == null) {
            throw new RuntimeException("ID not found");
        }
        try (PreparedStatement stmt = conn.prepareStatement(builder.delete().where("id = ?").toString())) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
