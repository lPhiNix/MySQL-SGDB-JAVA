package org.phinix.example.dao;

import org.phinix.example.model.Driver;
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
        } catch (Exception e) {}
    }
}
