package org.phinix.lib.common.dao.queries;

import org.phinix.lib.common.util.Model;
import org.phinix.lib.common.util.factories.DMLStatementsFactory;
import org.phinix.lib.service.MySQLConnection;
import org.phinix.lib.common.util.factories.QueryFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@code AbstractQueryDao} is an abstract class that implements the {@link QueryDao} interface.
 * This class handles the execution of SQL queries, delegating the construction of statements to {@link QueryFactory}.
 */
public abstract class AbstractQueryDao implements QueryDao {
    private static final Logger logger = Logger.getLogger(AbstractQueryDao.class.getName());
    private final MySQLConnection mySQLConnection;

    /**
     * Constructor that initializes the DAO with a database connection.
     *
     * @param database the MySQL connection to be used for executing queries
     */
    public AbstractQueryDao(MySQLConnection database) {
        this.mySQLConnection = database;
    }

    /**
     * Executes a SELECT query without parameters and returns a result set.
     *
     * @param query the SQL query to be executed
     * @return the ResultSet containing the query results
     * @throws SQLException if an error occurs during query execution
     */
    @Override
    public ResultSet executeQuery(String query, Model model) throws SQLException {
        try {
            PreparedStatement preparedStatement = mySQLConnection.getDatabase().prepareStatement(query);
            // Bind the primary key values to the PreparedStatement
            Map<String, Object> primaryKeys = DMLStatementsFactory.getPrimaryKeyValues(model);
            int index = 1;
            for (Object value : primaryKeys.values()) {
                preparedStatement.setObject(index++, value); // Bind primary key values
            }

            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing query", e);
            throw e;
        }
    }

    /**
     * Processes the ResultSet and prints it to the console.
     *
     * @param resultSet the ResultSet obtained from the query
     */
    public void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Prints the column names
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnLabel(i) + "\t");
        }
        System.out.println();

        // Prints the data for each row
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Processes the ResultSet and stores the results in a list of strings.
     *
     * @param resultSet the ResultSet obtained from the query
     * @return a list with the rows of the ResultSet as strings
     * @throws SQLException if an error occurs while extracting the data
     */
    public List<String> getResultSetAsList(ResultSet resultSet) throws SQLException {
        List<String> resultList = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Loops through the ResultSet and stores the values as strings
        while (resultSet.next()) {
            StringBuilder row = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                row.append(resultSet.getString(i)).append("\t");
            }
            resultList.add(row.toString().trim());
        }

        return resultList;
    }
}
