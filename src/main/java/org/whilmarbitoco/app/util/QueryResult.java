package org.whilmarbitoco.app.util;

import java.util.List;

public class QueryResult<T> {

    private final List<T> list;

    public QueryResult(List<T> list) {
        this.list = list;
    }

    public List<T> list() {
        return list;
    }

    public T firstResult() {
        if (this.list == null || this.list.isEmpty()) return null;
        return this.list.getFirst();
    }

    public T lastResult() {
        if (this.list == null || this.list.isEmpty()) return null;
        return this.list.getLast();
    }
}
