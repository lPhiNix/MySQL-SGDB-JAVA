package org.phinix.example.dao;

import org.phinix.example.model.Driver;
import org.phinix.example.model.Routes;
import org.phinix.lib.common.dao.queries.AbstractQueryDao;
import org.phinix.lib.common.util.FieldInputManager;
import org.phinix.lib.common.util.factories.QueryFactory;
import org.phinix.lib.service.MySQLConnection;

import java.sql.ResultSet;
public class BusDrivePlaceQueryDao extends AbstractQueryDao {
    /**
     * Constructor that initializes the DAO with a database connection.
     *
     * @param database the MySQL connection to be used for executing queries
     */
    public BusDrivePlaceQueryDao(MySQLConnection database) {
        super(database);
    }

    public void selectDriverAskingNumDriver() {
        try {
            Driver driver = FieldInputManager.createModelWithPrimaryKeys(Driver.class);
            String query = QueryFactory.buildSelectByPrimaryKeyQuery(Driver.class, driver);
            ResultSet results = super.executeQuery(query, driver);
            super.printResultSet(results);
        } catch (Exception ignored) {}
    }

    public void selectRouteAskingKeys() {
        try {
            Routes routes = FieldInputManager.createModelWithPrimaryKeys(Routes.class);
            String query = QueryFactory.buildSelectByPrimaryKeyQuery(Routes.class, routes);
            ResultSet results = super.executeQuery(query, routes);
            super.printResultSet(results);
        } catch (Exception ignored) {}
    }
}
