package org.phinix.lib.common.dao.queries;

import org.phinix.lib.common.util.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@code QueryDao} is an interface that defines methods for performing queries on any table.
 * The methods in this interface are oriented towards executing SELECT statements in the database.
 */
public interface QueryDao {
    /**
     * Executes a SELECT query without parameters and returns a result set.
     *
     * @param query the SQL query to be executed
     * @return the ResultSet containing the query results
     * @throws SQLException if an error occurs during query execution
     */
    ResultSet executeQuery(String query, Model model) throws SQLException;
}
