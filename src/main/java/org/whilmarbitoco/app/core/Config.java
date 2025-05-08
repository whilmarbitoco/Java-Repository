package org.whilmarbitoco.app.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final Properties props = Config.loadProperties(getPath());


    private static Properties loadProperties(String path) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(path)) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        }
        return props;
    }

    public static String url() {
        return props.getProperty("db.url");
    }

    public static String username() {
        return props.getProperty("db.user");
    }

    public static String password() {
        return props.getProperty("db.password");
    }

    public static boolean debug() {
        return props.getProperty("db.debug").equals("true");
    }

    private static String getPath() {
        return (System.getProperty("user.dir") + "/src/main/java/org/whilmarbitoco/config/db.properties");
    }
}
