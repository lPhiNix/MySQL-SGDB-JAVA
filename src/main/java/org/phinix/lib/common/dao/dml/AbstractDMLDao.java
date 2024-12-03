package org.phinix.lib.common.dao.dml;

import org.phinix.lib.common.util.Model;
import org.phinix.lib.common.util.DMLStatementsFactory;
import org.phinix.lib.service.MySQLConnection;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDMLDao implements DMLDao {
    private static final Logger logger = Logger.getLogger(AbstractDMLDao.class.getName());
    private final MySQLConnection mySQLConnection;

    public AbstractDMLDao(MySQLConnection database) {
        this.mySQLConnection = database;
    }

    @Override
    public <T extends Model> int insert(T model) {
        String tableName = model.getDynamicModelName();

        String query = DMLStatementsFactory.buildInsertStatements(model, tableName);
        logger.info("Generated Insert Statement: " + query);

        try (PreparedStatement preparedStatement = mySQLConnection.getDatabase().prepareStatement(query)) {
            bindModelToPreparedStatement(model, preparedStatement);

            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Insert " + tableName + ": " + rowsAffected + " row(s) affected.");
            return rowsAffected;

        } catch (SQLException | IllegalAccessException e) {
            logger.log(Level.SEVERE, "Error inserting model: " + model.getClass().getSimpleName(), e);
            return -1;
        }
    }

    @Override
    public <T extends Model> int update(T model) {
        String tableName = model.getDynamicModelName();

        String query = DMLStatementsFactory.buildUpdateStatements(model, tableName);
        logger.info("Generated Update Statement: " + query);

        try (PreparedStatement preparedStatement = mySQLConnection.getDatabase().prepareStatement(query)) {
            int index = bindModelToPreparedStatement(model, preparedStatement);

            Map<String, Object> primaryKeys = DMLStatementsFactory.getPrimaryKeyValues(model);
            for (Object value : primaryKeys.values()) {
                preparedStatement.setObject(index++, value);
            }

            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Update " + tableName + ": " + rowsAffected + " row(s) affected.");
            return rowsAffected;

        } catch (SQLException | IllegalAccessException e) {
            logger.log(Level.SEVERE, "Error updating model: " + model.getClass().getSimpleName(), e);
            return -1;
        }
    }

    @Override
    public <T extends Model> int delete(T model) {
        String tableName = model.getDynamicModelName();

        String query = DMLStatementsFactory.buildDeleteStatements(model, tableName);
        logger.info("Generated Delete Statement: " + query);

        try (PreparedStatement preparedStatement = mySQLConnection.getDatabase().prepareStatement(query)) {
            Map<String, Object> primaryKeys = DMLStatementsFactory.getPrimaryKeyValues(model);
            int index = 1;
            for (Object value : primaryKeys.values()) {
                preparedStatement.setObject(index++, value);
            }

            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Delete " + tableName + ": " + rowsAffected + " row(s) affected.");
            return rowsAffected;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting model: " + model.getClass().getSimpleName(), e);
            return -1;
        }
    }

    public <T extends Model> int deleteAll(Class<T> modelClass) {
        String query = DMLStatementsFactory.buildDeleteAllStatement(modelClass);
        logger.info("Generated Delete All Statement: " + query);

        try (PreparedStatement preparedStatement = mySQLConnection.getDatabase().prepareStatement(query)) {
            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Delete All " + modelClass.getSimpleName().toLowerCase() + ": " + rowsAffected + " row(s) affected.");
            return rowsAffected;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting all rows from table: " + modelClass.getSimpleName().toLowerCase(), e);
            return -1;
        }
    }

    public void dropDatabase(String databaseName) throws SQLException {
        String statement = "DROP DATABASE " + databaseName + ";";

        try (PreparedStatement preparedStatement = mySQLConnection.getDatabase().prepareStatement(statement)) {
            preparedStatement.executeUpdate();
            logger.info(databaseName + " Erased.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting database: " + databaseName);
        }
    }

    private <T extends Model> int bindModelToPreparedStatement(T model, PreparedStatement preparedStatement) throws IllegalAccessException, SQLException {
        Field[] fields = model.getClass().getDeclaredFields();
        int index = 1;

        for (Field field : fields) {
            field.setAccessible(true);
            preparedStatement.setObject(index++, field.get(model));
        }
        return index;
    }
}
