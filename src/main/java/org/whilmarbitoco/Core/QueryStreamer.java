package org.whilmarbitoco.Core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

public class QueryStreamer<T> {
    private final EntityMapper<T> mapper;


    public QueryStreamer(Class<T> entity) {
        this.mapper = new EntityMapper<>(entity);

    }

    public Stream<T> stream(String sql, Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            return mapper.map(rs).stream();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Stream<T> streamWithId(String sql, Connection conn, int id) {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return mapper.map(rs).stream();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
