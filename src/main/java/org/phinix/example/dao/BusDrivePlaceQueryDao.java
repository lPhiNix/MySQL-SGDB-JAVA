package org.phinix.example.dao;

import org.phinix.example.model.Driver;
import org.phinix.example.model.Routes;
import org.phinix.lib.common.dao.queries.AbstractQueryDao;
import org.phinix.lib.common.util.FieldInputManager;
import org.phinix.lib.common.util.factories.QueryFactory;
import org.phinix.lib.service.MySQLConnection;
import org.phinix.lib.common.util.Model;


import java.sql.ResultSet;

/**
 * The {@code BusDrivePlaceQueryDao} class provides methods for executing queries
 * on specific database tables, such as {@link Driver} and {@link Routes}, by interacting
 * with the underlying MySQL database.
 * <p>
 * This class extends {@link AbstractQueryDao} and leverages its functionality
 * to execute SELECT queries dynamically based on primary key values provided
 * by the user.
 */
public class BusDrivePlaceQueryDao extends AbstractQueryDao {

    /**
     * Constructs a new {@code BusDrivePlaceQueryDao} with a given MySQL database connection.
     *
     * @param database the MySQL connection used for executing queries.
     */
    public BusDrivePlaceQueryDao(MySQLConnection database) {
        super(database);
    }

    /**
     * Prompts the user to input the primary key(s) for a {@link Driver} object,
     * constructs a SELECT query, and executes it to fetch the corresponding
     * driver data from the database.
     * <p>
     * The method uses {@link FieldInputManager} to dynamically populate a
     * {@code Driver} object with the primary key(s), and
     * {@link QueryFactory#buildSelectByPrimaryKeyQuery(Class, Model)} to construct
     * the query.
     */
    public void selectDriverAskingNumDriver() {
        try {
            // Dynamically creates a Driver model and populates it with primary key values
            Driver driver = FieldInputManager.createModelWithPrimaryKeys(Driver.class);

            // Generates a SELECT query based on the Driver's primary key
            String query = QueryFactory.buildSelectByPrimaryKeyQuery(Driver.class, driver);

            // Executes the query and fetches the results
            ResultSet results = super.executeQuery(query, driver);

            // Prints the result set to the console
            super.printResultSet(results);
        } catch (Exception ignored) {
            // Exception is ignored for simplicity; in production, proper handling is advised
        }
    }

    /**
     * Prompts the user to input the primary key(s) for a {@link Routes} object,
     * constructs a SELECT query, and executes it to fetch the corresponding
     * route data from the database.
     * <p>
     * The method uses {@link FieldInputManager} to dynamically populate a
     * {@code Routes} object with the primary key(s), and
     * {@link QueryFactory#buildSelectByPrimaryKeyQuery(Class, Model)} to construct
     * the query.
     */
    public void selectRouteAskingKeys() {
        try {
            // Dynamically creates a Routes model and populates it with primary key values
            Routes routes = FieldInputManager.createModelWithPrimaryKeys(Routes.class);

            // Generates a SELECT query based on the Routes' primary key
            String query = QueryFactory.buildSelectByPrimaryKeyQuery(Routes.class, routes);

            // Executes the query and fetches the results
            ResultSet results = super.executeQuery(query, routes);

            // Prints the result set to the console
            super.printResultSet(results);
        } catch (Exception ignored) {
            // Exception is ignored for simplicity; in production, proper handling is advised
        }
    }
}
