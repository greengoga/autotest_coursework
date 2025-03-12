package ru.netology.data;

import lombok.SneakyThrows;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/app";
    private static final String USER = "username";
    private static final String PASSWORD = "pass";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не удалось загрузить JDBC-драйвер", e);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        String sql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        try (Connection conn = getConnection();
             var statement = conn.createStatement();
             var resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        }
        return null;
    }

    @SneakyThrows
    public static void clearDatabase() {
        try (Connection conn = getConnection();
             var statement = conn.createStatement()) {
            statement.executeUpdate("DELETE FROM payment_entity;");
            statement.executeUpdate("DELETE FROM credit_request_entity;");
            statement.executeUpdate("DELETE FROM order_entity;");
        }
    }
}