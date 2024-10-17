package br.upe.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String connectionURL = "jdbc:derby:prog3";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionURL);
    }
}
