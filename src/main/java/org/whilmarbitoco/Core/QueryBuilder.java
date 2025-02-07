package org.whilmarbitoco.Core;

import org.whilmarbitoco.Database.DatabaseConnection;

import java.util.stream.Stream;

public class QueryBuilder<T> extends QueryStreamer<T> {

    private final StringBuilder query = new StringBuilder();
    private final String table;

    public QueryBuilder(String table, Class<T> entity) {
        super(entity);
        this.table = table;
    }

    public QueryBuilder<T> select() {
        query.setLength(0);
        query.append("SELECT * FROM ").append(this.table).append(" ");
        return this;
    }

    public QueryBuilder<T> select(String rows) {
        query.setLength(0);
        query.append("SELECT ").append(rows).append(" FROM ").append(this.table).append(" ");
        return this;
    }

    public QueryBuilder<T> where(String condition) {
        query.append("WHERE ").append(condition);
        return this;
    }

    public QueryBuilder<T> and() {
        query.append(" AND ");
        return this;
    }

    public void insert(String... columns) {
        query.setLength(0);
        query.append("INSERT INTO ").append(this.table).append(" (");
        query.append(String.join(", ", columns));
        query.append(") VALUES (");
        query.append("?, ".repeat(columns.length - 1)).append("?");
        query.append(")");

        System.out.println(query);
    }

    public Stream<T> build() {
//        return stream(toString());
        return null;
    }

    @Override
    public String toString() {
        return query.append(";").toString();
    }

}
