package org.whilmarbitoco.app.database.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(getPath())) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        }
        return props;
    }

    public static Connection getConnection() {
        Properties props = loadProperties();
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    private static String getPath() {
        return (System.getProperty("user.dir") + "/src/main/java/org/whilmarbitoco/config/db.properties");
    }
}
