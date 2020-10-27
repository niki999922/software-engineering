package ru.akirakozov.sd.refactoring.servlet;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
    protected static final String START_HTML_WRAPPER = "<html><body>\n";
    protected static final String END_HTML_WRAPPER = "</body></html>\n";
    private static final String DATA_BASE_NAME = "jdbc:sqlite:test.db";

    static void clear() throws SQLException {
        try (Statement statement = DriverManager.getConnection(DATA_BASE_NAME).createStatement()) {
            String sqlQuery = "DELETE FROM PRODUCT";
            statement.executeUpdate(sqlQuery);
        }
    }

    static void add(String name, int price) throws SQLException {
        try (Statement statement = DriverManager.getConnection(DATA_BASE_NAME).createStatement()) {
            String sqlQuery = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
            statement.executeUpdate(sqlQuery);
        }
    }

    static String wrapResult(String res) {
        return START_HTML_WRAPPER + res + END_HTML_WRAPPER;
    }
}
