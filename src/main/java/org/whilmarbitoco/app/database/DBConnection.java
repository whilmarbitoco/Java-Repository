package org.whilmarbitoco.app.database;

import org.whilmarbitoco.app.core.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection conn;

    public static Connection getConnection() {
        String url = Config.url();
        String user = Config.username();
        String password = Config.password();

        try {
            if (conn == null) {
                conn = DriverManager.getConnection(url, user, password);;
            }
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

}
