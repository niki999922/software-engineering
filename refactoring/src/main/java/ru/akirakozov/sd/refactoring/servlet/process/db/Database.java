package ru.akirakozov.sd.refactoring.servlet.process.db;

import ru.akirakozov.sd.refactoring.servlet.HTMLBuilder;

import java.sql.*;
import java.util.List;
import java.util.function.Function;

public class Database {
    private final String name;

    public Database(String name) {
        this.name = name;
    }

    public void executeQuery(HTMLBuilder builder, String query, Function<ResultSet, List<String>> handler) throws SQLException {
        try (Connection c = DriverManager.getConnection(name)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            List<String> res = handler.apply(rs);
            res.forEach(builder::add);

            rs.close();
            stmt.close();
        }
    }

    public void updateQuery(String query) throws SQLException {
        try (Connection c = DriverManager.getConnection(name)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        }
    }

}
