package org.phinix.lib.common.util.factories;

import org.phinix.lib.common.util.Model;
import java.util.Map;

/**
 * {@code QueryFactory} is a utility class for generating SQL query statements (SELECT).
 * It uses reflection to construct queries based on models.
 */
public class QueryFactory {
    /**
     * Builds a SELECT statement to fetch all records from the table corresponding to the model.
     *
     * @param modelClass the model class for which the query should be generated
     * @return the generated SQL query
     */
    public static String buildSelectAllQuery(Class<? extends Model> modelClass) {
        String tableName = modelClass.getSimpleName().toLowerCase();
        return "SELECT * FROM " + tableName;
    }

    /**
     * Builds a SELECT statement with search parameters, using primary keys as filters.
     *
     * @param modelClass the model class for which the query should be generated
     * @param model the model instance from which primary keys will be extracted
     * @return the generated SQL query
     */
    public static String buildSelectByPrimaryKeyQuery(Class<? extends Model> modelClass, Model model) {
        String tableName = modelClass.getSimpleName().toLowerCase();
        StringBuilder query = new StringBuilder("SELECT * FROM " + tableName + " WHERE ");

        // Get primary key values from the model
        Map<String, Object> primaryKeys = DMLStatementsFactory.getPrimaryKeyValues(model);

        // Generate conditions for primary keys
        for (String key : primaryKeys.keySet()) {
            query.append(key).append(" = ? AND ");
        }

        // Remove the last "AND"
        query.setLength(query.length() - 4);

        return query.toString();
    }
}
