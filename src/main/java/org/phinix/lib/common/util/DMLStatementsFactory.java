package org.phinix.lib.common.util;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A utility class for generating SQL DML (Data Manipulation Language) statements
 * such as INSERT, UPDATE, and DELETE for model objects.
 * <p>
 * This class uses reflection to build SQL statements dynamically based on the fields
 * of the provided model class. The fields annotated with @PrimaryKey are used to
 * identify primary key constraints for UPDATE and DELETE statements.
 */
public class DMLStatementsFactory {

    /**
     * Builds an INSERT SQL statement for the given model and table name.
     * <p>
     * The method generates an INSERT statement by reflecting on the fields of the model.
     * It automatically generates placeholders for the values to be inserted.
     *
     * @param model     the model object to generate the statement for
     * @param tableName the name of the table to insert data into
     * @param <T>       the type of the model, which must extend {@link Model}
     * @return the generated SQL INSERT statement
     */
    public static <T extends Model> String buildInsertStatements(T model, String tableName) {
        // Get the declared fields of the model class
        Field[] fields = model.getClass().getDeclaredFields();

        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        // Build the columns and placeholders part of the query
        for (Field field : fields) {
            columns.append(field.getName()).append(",");
            placeholders.append("?," );
        }

        // Remove the trailing commas from the columns and placeholders
        columns.setLength(columns.length() - 1);
        placeholders.setLength(placeholders.length() - 1);

        // Return the full INSERT statement
        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
    }

    /**
     * Builds an UPDATE SQL statement for the given model and table name.
     * <p>
     * The method generates an UPDATE statement, where fields of the model are set to new values.
     * The primary key fields are used to build the WHERE clause.
     *
     * @param model     the model object to generate the statement for
     * @param tableName the name of the table to update
     * @param <T>       the type of the model, which must extend {@link Model}
     * @return the generated SQL UPDATE statement
     */
    public static <T extends Model> String buildUpdateStatements(T model, String tableName) {
        // Get the declared fields of the model class
        Field[] fields = model.getClass().getDeclaredFields();
        StringBuilder setClause = new StringBuilder();

        // Build the SET clause of the UPDATE statement
        for (Field field : fields) {
            setClause.append(field.getName()).append(" = ?,");
        }
        setClause.setLength(setClause.length() - 1); // Remove the trailing comma

        // Retrieve primary key values to construct the WHERE clause
        Map<String, Object> primaryKeys = getPrimaryKeyValues(model);
        StringBuilder whereClause = new StringBuilder();
        for (String key : primaryKeys.keySet()) {
            whereClause.append(key).append(" = ? AND ");
        }
        whereClause.setLength(whereClause.length() - 5); // Remove the trailing "AND"

        // Return the full UPDATE statement
        return "UPDATE " + tableName + " SET " + setClause + " WHERE " + whereClause;
    }

    /**
     * Builds a DELETE SQL statement for the given model and table name.
     * <p>
     * The method generates a DELETE statement based on the primary key fields of the model,
     * which are used to build the WHERE clause for identifying the row to delete.
     *
     * @param model     the model object to generate the statement for
     * @param tableName the name of the table to delete data from
     * @param <T>       the type of the model, which must extend {@link Model}
     * @return the generated SQL DELETE statement
     */
    public static <T extends Model> String buildDeleteStatements(T model, String tableName) {
        // Retrieve primary key values to construct the WHERE clause
        Map<String, Object> primaryKeys = getPrimaryKeyValues(model);
        StringBuilder whereClause = new StringBuilder();
        for (String key : primaryKeys.keySet()) {
            whereClause.append(key).append(" = ? AND ");
        }
        whereClause.setLength(whereClause.length() - 5); // Remove the trailing "AND"

        // Return the full DELETE statement
        return "DELETE FROM " + tableName + " WHERE " + whereClause;
    }

    /**
     * Builds a DELETE SQL statement that removes all records from the given model's table.
     * <p>
     * The table name is inferred from the model class name.
     *
     * @param modelClass the class of the model to generate the statement for
     * @param <T>        the type of the model, which must extend {@link Model}
     * @return the generated SQL DELETE statement for removing all records
     */
    public static <T extends Model> String buildDeleteAllStatement(Class<T> modelClass) {
        // Derive the table name from the model class simple name in lowercase
        String tableName = modelClass.getSimpleName().toLowerCase();
        return "DELETE FROM " + tableName;
    }

    /**
     * Retrieves the primary key values of the given model.
     * <p>
     * The method scans the model fields for those annotated with {@link PrimaryKey}
     * and collects their values into a map.
     *
     * @param model the model object from which to retrieve primary key values
     * @param <T>   the type of the model, which must extend {@link Model}
     * @return a map containing primary key field names and their corresponding values
     * @throws IllegalArgumentException if the model does not have any fields annotated with @PrimaryKey
     */
    public static <T extends Model> Map<String, Object> getPrimaryKeyValues(T model) {
        // Get the declared fields of the model class
        Field[] fields = model.getClass().getDeclaredFields();
        Map<String, Object> primaryKeys = new LinkedHashMap<>();

        // Find fields annotated with @PrimaryKey and add them to the map
        for (Field field : fields) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                field.setAccessible(true); // Make the field accessible for reflection
                try {
                    primaryKeys.put(field.getName(), field.get(model)); // Add the field value to the map
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing primary key field: " + field.getName(), e);
                }
            }
        }

        // Throw an exception if no primary key fields are found
        if (primaryKeys.isEmpty()) {
            throw new IllegalArgumentException("Model does not have fields annotated with @PrimaryKey.");
        }

        return primaryKeys;
    }
}
