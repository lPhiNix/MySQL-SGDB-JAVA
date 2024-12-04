package org.phinix.lib.common.dao.dml;

import org.phinix.lib.common.util.Model;
import org.phinix.lib.common.util.factories.DMLStatementFactory;
import org.phinix.lib.service.MySQLConnection;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An abstract implementation of the DMLDao interface, providing common methods
 * for performing Data Manipulation Language (DML) operations (insert, update, delete).
 * <p>
 * This abstract class facilitates the implementation of DML operations for model objects
 * in a MySQL database. It uses reflection to dynamically build SQL statements based on the model's fields.
 */
public abstract class AbstractDMLDao implements DMLDao {
    private static final Logger logger = Logger.getLogger(AbstractDMLDao.class.getName());
    private final MySQLConnection mySQLConnection;

    /**
     * Constructor to initialize the DAO with a MySQL connection instance.
     *
     * @param database the MySQL connection instance to be used for database operations
     */
    public AbstractDMLDao(MySQLConnection database) {
        this.mySQLConnection = database;
    }

    /**
     * Inserts a new model object into the database.
     * <p>
     * This method generates an SQL INSERT statement using the model's fields, binds the field values
     * to a PreparedStatement, and executes the insert operation on the database.
     *
     * @param model the model object to be inserted into the database
     * @param <T>   the type of the model, which must extend {@link Model}
     * @return      the number of rows affected by the insert operation
     */
    @Override
    public <T extends Model> int insert(T model) {
        // Get the table name based on the model's dynamic name
        String tableName = model.getDynamicModelName();

        // Generate the SQL INSERT statement using the model and table name
        String query = DMLStatementFactory.buildInsertStatements(model, tableName);
        logger.info("Generated Insert Statement: " + query);

        try (PreparedStatement preparedStatement = mySQLConnection.getDatabase().prepareStatement(query)) {
            // Bind the model's field values to the PreparedStatement
            bindModelToPreparedStatement(model, preparedStatement);

            // Execute the insert operation and get the number of affected rows
            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Insert " + tableName + ": " + rowsAffected + " row(s) affected.");
            return rowsAffected;

        } catch (SQLException | IllegalAccessException e) {
            logger.log(Level.SEVERE, "Error inserting model: " + model.getClass().getSimpleName(), e);
            return -1;
        }
    }

    /**
     * Updates an existing model object in the database.
     * <p>
     * This method generates an SQL UPDATE statement using the model's fields, binds the field values
     * to a PreparedStatement, and executes the update operation. Primary key fields are also bound
     * to the statement to identify the row to update.
     *
     * @param model the model object to be updated in the database
     * @param <T>   the type of the model, which must extend {@link Model}
     * @return      the number of rows affected by the update operation
     */
    @Override
    public <T extends Model> int update(T model) {
        // Get the table name based on the model's dynamic name
        String tableName = model.getDynamicModelName();

        // Generate the SQL UPDATE statement using the model and table name
        String query = DMLStatementFactory.buildUpdateStatements(model, tableName);
        logger.info("Generated Update Statement: " + query);

        try (PreparedStatement preparedStatement = mySQLConnection.getDatabase().prepareStatement(query)) {
            // Bind the model's field values to the PreparedStatement
            int index = bindModelToPreparedStatement(model, preparedStatement);

            // Get the primary key values from the model and bind them to the statement
            Map<String, Object> primaryKeys = DMLStatementFactory.getPrimaryKeyValues(model);
            for (Object value : primaryKeys.values()) {
                preparedStatement.setObject(index++, value); // Bind primary key values
            }

            // Execute the update operation and get the number of affected rows
            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Update " + tableName + ": " + rowsAffected + " row(s) affected.");
            return rowsAffected;

        } catch (SQLException | IllegalAccessException e) {
            logger.log(Level.SEVERE, "Error updating model: " + model.getClass().getSimpleName(), e);
            return -1;
        }
    }

    /**
     * Deletes a model object from the database.
     * <p>
     * This method generates an SQL DELETE statement using the primary key fields of the model,
     * binds the values to a PreparedStatement, and executes the delete operation on the database.
     *
     * @param model the model object to be deleted from the database
     * @param <T>   the type of the model, which must extend {@link Model}
     * @return      the number of rows affected by the delete operation
     */
    @Override
    public <T extends Model> int delete(T model) {
        // Get the table name based on the model's dynamic name
        String tableName = model.getDynamicModelName();

        // Generate the SQL DELETE statement using the model and table name
        String query = DMLStatementFactory.buildDeleteStatements(model, tableName);
        logger.info("Generated Delete Statement: " + query);

        try (PreparedStatement preparedStatement = mySQLConnection.getDatabase().prepareStatement(query)) {
            // Get the primary key values from the model and bind them to the statement
            Map<String, Object> primaryKeys = DMLStatementFactory.getPrimaryKeyValues(model);
            int index = 1;
            for (Object value : primaryKeys.values()) {
                preparedStatement.setObject(index++, value); // Bind primary key values
            }

            // Execute the delete operation and get the number of affected rows
            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Delete " + tableName + ": " + rowsAffected + " row(s) affected.");
            return rowsAffected;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting model: " + model.getClass().getSimpleName(), e);
            return -1;
        }
    }

    /**
     * Deletes all records from the table associated with the given model class.
     * <p>
     * This method generates an SQL DELETE statement that removes all rows from the table,
     * and then executes the delete operation.
     *
     * @param modelClass the class of the model whose table's data is to be deleted
     * @param <T>        the type of the model, which must extend {@link Model}
     * @return           the number of rows affected by the delete operation
     */
    public <T extends Model> int deleteAll(Class<T> modelClass) {
        // Generate the SQL DELETE ALL statement for the table
        String query = DMLStatementFactory.buildDeleteAllStatement(modelClass);
        logger.info("Generated Delete All Statement: " + query);

        try (PreparedStatement preparedStatement = mySQLConnection.getDatabase().prepareStatement(query)) {
            // Execute the delete operation and get the number of affected rows
            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Delete All " + modelClass.getSimpleName().toLowerCase() + ": " + rowsAffected + " row(s) affected.");
            return rowsAffected;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting all rows from table: " + modelClass.getSimpleName().toLowerCase(), e);
            return -1;
        }
    }

    /**
     * Drops a database by its name.
     * <p>
     * JIJIJIJA
     *
     * @param databaseName  the name of the database to be dropped
     */
    public void dropDatabase(String databaseName) {
        // Generate the SQL DROP DATABASE statement
        String statement = "DROP DATABASE " + databaseName + ";";

        try (PreparedStatement preparedStatement = mySQLConnection.getDatabase().prepareStatement(statement)) {
            // Execute the drop database statement
            preparedStatement.executeUpdate();
            logger.info(databaseName + " Erased.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting database: " + databaseName);
        }
    }

    /**
     * Binds the fields of the given model to the provided PreparedStatement.
     * <p>
     * This method iterates over the fields of the model and sets their values
     * to the corresponding placeholders in the PreparedStatement.
     *
     * @param model                   the model object whose fields are to be bound
     * @param preparedStatement       the PreparedStatement to bind the model fields to
     * @param <T>                     the type of the model, which must extend {@link Model}
     * @return                        the next index to be used in the PreparedStatement
     * @throws IllegalAccessException if an error occurs while accessing the model's fields
     * @throws SQLException           if an error occurs while setting values to the PreparedStatement
     */
    private <T extends Model> int bindModelToPreparedStatement(T model, PreparedStatement preparedStatement) throws IllegalAccessException, SQLException {
        Field[] fields = model.getClass().getDeclaredFields();
        int index = 1;

        // Iterate over the fields and set their values in the PreparedStatement
        for (Field field : fields) {
            field.setAccessible(true); // Make field accessible for reflection
            preparedStatement.setObject(index++, field.get(model)); // Bind the field value to the statement
        }
        return index;
    }
}
