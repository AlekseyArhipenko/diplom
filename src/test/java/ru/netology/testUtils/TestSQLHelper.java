package ru.netology.testUtils;

import lombok.Getter;
import java.sql.*;

public class TestSQLHelper {

    private static String dbUrl = System.getProperty("db.url");
    private static String dbUser = System.getProperty("db.user");
    private static String dbPass = System.getProperty("db.pass");

    @Getter private static final String payTable = "payment_entity";
    @Getter private static final String creditTable = "credit_request_entity";

    public static void cleanTables() throws SQLException {
        String cleanCreditTables = "DELETE FROM credit_request_entity;";
        String cleanOrderTable = "DELETE FROM order_entity;";
        String cleanPaymentTable = "DELETE FROM payment_entity;";
        Connection c = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement s = c.createStatement();
        s.executeUpdate(cleanCreditTables);
        s.executeUpdate(cleanOrderTable);
        s.executeUpdate(cleanPaymentTable);
        c.close();
    }

    public static String getOperationStatus(String table) {
        String status = "";
        try (Connection c = DriverManager.getConnection(dbUrl, dbUser, dbPass);
             Statement s = c.createStatement()) {
            try (ResultSet resultSet = s.executeQuery("SELECT * FROM " + table + " ORDER BY id DESC LIMIT 1;")) {
                while (resultSet.next()) status = resultSet.getString("status");
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return status;
    }
}