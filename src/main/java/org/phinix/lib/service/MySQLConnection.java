package org.phinix.lib.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * MySQLConnection class provides a singleton-based management system for establishing and handling
 * MySQL database connections.
 * <p>
 * This class ensures that only one connection instance exists for the application.
 * It initializes the connection using the provided user credentials and allows
 * controlled access to the database connection.
 */
public class MySQLConnection {
    private static final Logger logger = Logger.getLogger(MySQLConnection.class.getName());
    static {
        // Custom logger config to synchronize out.print() with logger.info()
        StreamHandler handler = new StreamHandler(System.out, new java.util.logging.SimpleFormatter()) {
            @Override
            public synchronized void publish(java.util.logging.LogRecord record) {
                String coloredMessage = "\u001B[31m" + getFormatter().format(record) + "\u001B[0m";
                System.out.print(coloredMessage);
                flush();
            }
        };
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // MySQL driver class name

    private static final String URL = "jdbc:mysql://localhost:3306/busDrivePlace"; // JDBC URL for the database connection

    private static volatile MySQLConnection instance; // Singleton instance of the MySQLConnection class
    private final Connection database;
    private final String user;
    private final String password;

    /**
     * Private constructor to prevent direct instantiation. Initializes the database connection
     * using the provided credentials.
     *
     * @param user     the username for the database connection
     * @param password the password for the database connection
     * @throws RuntimeException if the database driver fails to initialize or connection cannot be established
     */
    private MySQLConnection(String user, String password) {
        validateCredentials(user); // Ensure that user credentials are valid

        this.user = user;
        this.password = password;

        try {
            // Load the MySQL driver class
            Class.forName(DRIVER);

            // Establish the connection to the database
            this.database = DriverManager.getConnection(URL, user, password);

            logger.info("Successfully initializing MySQL Database.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error initializing MySQL Database driver.", e);
            throw new RuntimeException("Failed to initialize MySQL Driver.");
        }
    }

    /**
     * Validates the provided username for the database connection.
     *
     * @param user the username to validate
     * @throws IllegalArgumentException if the username is null or empty
     */
    private void validateCredentials(String user) {
        if (user == null || user.isEmpty()) {
            throw new IllegalArgumentException("User must not be null or empty.");
        }
    }

    /**
     * Returns the singleton instance of the MySQLConnection class. Creates a new instance if it doesn't already exist.
     *
     * @param user     the username for the database connection
     * @param password the password for the database connection
     * @return the singleton instance of MySQLConnection
     */
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

    /**
     * Closes the database connection and releases resources.
     *
     * @throws RuntimeException if an error occurs during shutdown
     */
    public void shutdown() {
        synchronized (this) {
            try {
                if (database != null) {
                    // Close the database connection
                    database.close();
                    logger.info("MySQL Database has been shut down.");
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Error during MySQL Database shutdown.", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Gets the active database connection.
     *
     * @return the {@link Connection} object
     */
    public Connection getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
