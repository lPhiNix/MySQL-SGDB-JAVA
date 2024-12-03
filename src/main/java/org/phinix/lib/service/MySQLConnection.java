package org.phinix.lib.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnection {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/busDrivePlace";
    private static final Logger logger = Logger.getLogger(MySQLConnection.class.getName());
    private static volatile MySQLConnection instance; // Database global instance (Singleton)
    private final Connection database;
    private final String user;
    private final String password;

    private MySQLConnection(String user, String password) {
        validateCredentials(user);

        this.user = user;
        this.password = password;

        try {
            Class.forName(DRIVER);

            this.database = DriverManager.getConnection(URL, user, password);

            logger.info("Successfully initializing MySQL Database.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error initializing MySQL Database driver.", e);
            throw new RuntimeException("Failed to initializing MySQL Driver");
        }
    }

    private void validateCredentials(String user) {
        if (user == null || user.isEmpty()) {
            throw new IllegalArgumentException("User must not be null or empty.");
        }
    }

    public static MySQLConnection getInstance(String user, String password) {
        if (instance == null) {
            synchronized (MySQLConnection.class) {
                if (instance == null) {
                    instance = new MySQLConnection(user, password);
                }
            }
        }
        return instance;
    }

    public void shutdown() {
        synchronized (this) {
            try {
                if (database != null) {
                    database.close();
                    logger.info("MySQL Database has been shut down.");
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Error during MySQL Database shutdown.", e);
                throw new RuntimeException(e);
            }
        }
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Connection getDatabase() {
        return database;
    }
}
