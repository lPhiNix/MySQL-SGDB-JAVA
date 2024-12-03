package org.phinix.lib.common.util;

import org.phinix.lib.common.util.DMLStatementsFactory;
import org.phinix.lib.common.util.Model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Scanner;

/**
 * The {@code FieldInputManager} class provides utility methods for creating and populating
 * model instances through user input. It uses reflection to dynamically create instances of
 * model classes and set their fields based on user input.
 * <p>
 * The main methods in this class are:
 * <ul>
 *     <li>{@link #createAndPopulateModel(Class)}: Creates a new model instance and populates
 *     all its fields by requesting input from the user.</li>
 *     <li>{@link #createModelWithPrimaryKeys(Class)}: Creates a new model instance and populates
 *     only its primary key fields.</li>
 * </ul>
 * The class makes use of reflection to interact with the model's fields and setters, ensuring
 * flexibility and extensibility for various model types.
 */
public class FieldInputManager {
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Creates and populates a new model instance by asking the user for input for each field.
     * The method uses reflection to dynamically create the instance and set the values for each field.
     *
     * @param modelClass the class type of the model to be created
     * @param <T>        the type of the model, which must extend {@link Model}
     * @return a new instance of the model with populated fields
     * @throws Exception if there are issues with instantiation or reflection
     */
    public static <T extends Model> T createAndPopulateModel(Class<T> modelClass) throws Exception {
        // Create a new instance of the model using the no-args constructor
        T instance = modelClass.getDeclaredConstructor().newInstance();

        // Iterate over all fields of the model class to populate them
        for (Field field : modelClass.getDeclaredFields()) {
            String input = getUserInput(field); // Get input from the user for the field
            Object value = parseValue(input, field.getType()); // Parse the input string to the correct field type
            invokeSetter(instance, field, value); // Invoke the setter method to set the field's value
        }
        return instance;
    }

    /**
     * Creates a new model instance and populates its primary key fields.
     * This method retrieves the primary key values dynamically and sets them.
     *
     * @param modelClass the class type of the model to be created
     * @param <T>        the type of the model, which must extend {@link Model}
     * @return a new instance of the model with populated primary key fields
     * @throws Exception if there are issues with instantiation or reflection
     */
    public static <T extends Model> T createModelWithPrimaryKeys(Class<T> modelClass) throws Exception {
        // Create a new instance of the model using the no-args constructor
        T instance = modelClass.getDeclaredConstructor().newInstance();

        // Get the primary key values from the model instance
        Map<String, Object> primaryKeys = DMLStatementsFactory.getPrimaryKeyValues(instance);

        // Iterate over the primary key entries to set their values
        for (Map.Entry<String, Object> entry : primaryKeys.entrySet()) {
            Field field = modelClass.getDeclaredField(entry.getKey());
            setFieldValue(instance, field);
        }
        return instance;
    }

    /**
     * Prompts the user for input and sets the value for a specific field.
     *
     * @param instance the instance of the model to set the field value on
     * @param field    the field to set the value for
     * @throws Exception if there is an issue with field access or setter invocation
     */
    private static <T extends Model> void setFieldValue(T instance, Field field) throws Exception {
        String input = getUserInput(field); // Get user input for the field
        Object value = parseValue(input, field.getType()); // Parse the input to the correct type for the field
        invokeSetter(instance, field, value); // Set the field value using the setter method
    }

    /**
     * Prompts the user for input corresponding to a field.
     *
     * @param field the field to prompt for
     * @return the user input as a string
     */
    private static String getUserInput(Field field) {
        // Prompt the user for input, displaying the field's name and type
        System.out.print("Enter value for " + field.getName() + " (" + field.getType().getSimpleName() + "): ");
        return scanner.nextLine();
    }

    /**
     * Uses reflection to invoke the setter method for a given field.
     *
     * @param instance the model instance
     * @param field    the field whose setter method will be invoked
     * @param value    the value to be set
     * @throws Exception if there is an issue with invoking the setter method
     */
    private static <T extends Model> void invokeSetter(T instance, Field field, Object value) throws Exception {
        // Generate the setter method name by capitalizing the field's name
        String setterName = "set" + capitalize(field.getName());
        // Get the setter method using reflection
        Method setterMethod = instance.getClass().getMethod(setterName, field.getType());
        setterMethod.invoke(instance, value); // Invoke the setter method to set the field's value
    }

    /**
     * Parses the user input string into the appropriate type for the field.
     *
     * @param input the string input provided by the user
     * @param type  the type of the field
     * @return the parsed value in the correct type
     */
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

    /**
     * Capitalizes the first letter of a string (used for generating setter method names).
     *
     * @param str the string to capitalize
     * @return the capitalized string
     */
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
