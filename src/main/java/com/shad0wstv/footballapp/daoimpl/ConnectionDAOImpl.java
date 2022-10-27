package com.shad0wstv.footballapp.daoimpl;

import com.shad0wstv.footballapp.dao.ConnectionDAO;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionDAOImpl implements ConnectionDAO {
    private Connection conn;
    private final Dotenv dotenv = Dotenv.load();

    public ConnectionDAOImpl() {
        dbConnection();
    }

    private void dbConnection() {
        try {
            this.conn = DriverManager.getConnection(Objects.requireNonNull(dotenv.get("DB_STRING_CONNECTION")), dotenv.get("DB_USER"), dotenv.get("DB_PASSWORD"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Connection connect() {
        return this.conn;
    }

    @Override
    public void disconnect() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
