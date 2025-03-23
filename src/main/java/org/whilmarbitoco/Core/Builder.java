package org.whilmarbitoco.Core;

public class Builder {

    private final String tableName;
    private final StringBuilder query = new StringBuilder();

    public Builder(String tableName) {
        this.tableName = tableName;

    }

    public Builder select() {
        query.setLength(0);
        query.append("SELECT * FROM ").append(tableName);
        return this;
    }

    public Builder select(String... columns) {
        query.setLength(0);
        query.append("SELECT ");
        query.append(String.join(", ", columns));
        query.append(" FROM ").append(tableName);
        return this;
    }

    public Builder where(String condition) {
        query.append(" WHERE ").append(condition);
        return this;
    }

    public Builder or() {
        query.append(" OR ");
        return this;
    }

    public Builder delete() {
        query.setLength(0);
        query.append("DELETE FROM ").append(tableName);
        return this;
    }

    public Builder insert(String... columns) {
        query.setLength(0);
        query.append("INSERT INTO ").append(tableName).append(" (");
        query.append(String.join(", ", columns));
        query.append(") VALUES (");
        query.append("?, ".repeat(columns.length));
        query.append(")");
        return this;
    }

    public Builder update(String... columns) {
        query.setLength(0);
        query.append("UPDATE ").append(tableName).append(" SET ");

        for (int i = 0; i < columns.length; i++) {
            if (i > 0) {
                query.append(", ");
            }
            query.append(columns[i]).append(" = ?");
        }
        return this;
    }

    @Override
    public String toString() {
        query.append(";");
        return query.toString();
    }

}
