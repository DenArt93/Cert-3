package org.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProvider {

    private Properties properties;

    public ConfigProvider() {
        properties = new Properties();
        try (InputStream input = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Проблема при чтении файла конфигурации", ex);
        }
    }

    public String getUser() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }

    public String getTokenUrl() {
        return properties.getProperty("token_url");
    }

    public String getAddBookUrl() {
        return properties.getProperty("add_book_url");
    }
}
