package org.phinix.lib.common.util;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class DMLStatementsFactory {
    public static <T extends Model> String buildInsertStatements(T model, String tableName) {
        Field[] fields = model.getClass().getDeclaredFields();

        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        for (Field field : fields) {
            columns.append(field.getName()).append(",");
            placeholders.append("?,");
        }

        columns.setLength(columns.length() - 1); // Elimina la última coma
        placeholders.setLength(placeholders.length() - 1);

        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
    }

    public static <T extends Model> String buildUpdateStatements(T model, String tableName) {
        Field[] fields = model.getClass().getDeclaredFields();
        StringBuilder setClause = new StringBuilder();

        for (Field field : fields) {
            setClause.append(field.getName()).append(" = ?,");
        }
        setClause.setLength(setClause.length() - 1); // Elimina la última coma

        Map<String, Object> primaryKeys = getPrimaryKeyValues(model);
        StringBuilder whereClause = new StringBuilder();
        for (String key : primaryKeys.keySet()) {
            whereClause.append(key).append(" = ? AND ");
        }
        whereClause.setLength(whereClause.length() - 5); // Elimina el último "AND"

        return "UPDATE " + tableName + " SET " + setClause + " WHERE " + whereClause;
    }

    public static <T extends Model> String buildDeleteStatements(T model, String tableName) {
        Map<String, Object> primaryKeys = getPrimaryKeyValues(model);
        StringBuilder whereClause = new StringBuilder();
        for (String key : primaryKeys.keySet()) {
            whereClause.append(key).append(" = ? AND ");
        }
        whereClause.setLength(whereClause.length() - 5); // Elimina el último "AND"

        return "DELETE FROM " + tableName + " WHERE " + whereClause;
    }

    public static <T extends Model> String buildDeleteAllStatement(Class<T> modelClass) {
        String tableName = modelClass.getSimpleName().toLowerCase();
        return "DELETE FROM " + tableName;
    }

    public static <T extends Model> Map<String, Object> getPrimaryKeyValues(T model) {
        Field[] fields = model.getClass().getDeclaredFields();
        Map<String, Object> primaryKeys = new LinkedHashMap<>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                field.setAccessible(true);
                try {
                    primaryKeys.put(field.getName(), field.get(model));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing primary key field: " + field.getName(), e);
                }
            }
        }

        if (primaryKeys.isEmpty()) {
            throw new IllegalArgumentException("Model does not have fields annotated with @PrimaryKey.");
        }

        return primaryKeys;
    }
}