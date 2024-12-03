package org.phinix.example.util;

import org.phinix.lib.common.util.DMLStatementsFactory;
import org.phinix.lib.common.util.Model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Scanner;

public class FieldInputManager {
    private static final Scanner scanner = new Scanner(System.in);
    public static <T extends Model> T createAndPopulateModel(Class<T> modelClass) throws Exception {
        T instance = modelClass.getDeclaredConstructor().newInstance();

        for (Field field : modelClass.getDeclaredFields()) {
            String input = getUserInput(field);
            Object value = parseValue(input, field.getType());
            invokeSetter(instance, field, value);
        }
        return instance;
    }

    public static <T extends Model> T createModelWithPrimaryKeys(Class<T> modelClass) throws Exception {
        T instance = modelClass.getDeclaredConstructor().newInstance();
        Map<String, Object> primaryKeys = DMLStatementsFactory.getPrimaryKeyValues(instance);
        for (Map.Entry<String, Object> entry : primaryKeys.entrySet()) {
            Field field = modelClass.getDeclaredField(entry.getKey());
            setFieldValue(instance, field);
        }
        return instance;
    }

    private static <T extends Model> void setFieldValue(T instance, Field field) throws Exception {
        String input = getUserInput(field);
        Object value = parseValue(input, field.getType());
        invokeSetter(instance, field, value);
    }

    private static String getUserInput(Field field) {
        System.out.print("Enter value for " + field.getName() + " (" + field.getType().getSimpleName() + "): ");
        return scanner.nextLine();
    }

    private static <T extends Model> void invokeSetter(T instance, Field field, Object value) throws Exception {
        String setterName = "set" + capitalize(field.getName());
        Method setterMethod = instance.getClass().getMethod(setterName, field.getType());
        setterMethod.invoke(instance, value);
    }

    private static Object parseValue(String input, Class<?> type) {
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(input);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return Double.parseDouble(input);
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return Boolean.parseBoolean(input);
        } else if (type.equals(String.class)) {
            return input;
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + type.getName());
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
