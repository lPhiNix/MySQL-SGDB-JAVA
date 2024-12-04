package org.phinix.example.dao;

import org.phinix.lib.common.util.FieldInputManager;
import org.phinix.lib.common.dao.dml.AbstractDMLDao;
import org.phinix.lib.common.util.Model;
import org.phinix.lib.service.MySQLConnection;

import org.phinix.example.model.*;

/**
 * {@code BusDrivePlaceDao} is a concrete implementation of a Data Access Object (DAO)
 * that handles data manipulation operations (insert, update, delete) for the
 * {@link Bus}, {@link Driver}, {@link Place} and {@link Routes} model.
 * This class extends {@link AbstractDMLDao} and inherits
 * its generic methods to perform operations on the database.
 * <p>
 * The class uses {@link FieldInputManager} to create and populate instances of the model
 * with data provided by the user. Depending on the operation (insert, update, delete),
 * the corresponding method is called to interact with the database.
 */
public class BusDrivePlaceDMLDao extends AbstractDMLDao {
    /**
     * Constructor that initializes the DAO with a database connection.
     *
     * @param database an instance of {@link MySQLConnection} that will be used to perform
     *                 operations on the database
     */
    public BusDrivePlaceDMLDao(MySQLConnection database) {
        super(database);
    }

    /**
     * Inserts a new instance of the given model into the database.
     * <p>
     * This method uses the {@link FieldInputManager} class to create a new instance
     * of the model by asking the user to input values for the model's fields.
     * Then, it calls the {@link AbstractDMLDao#insert(T)} method to insert the instance
     * into the database.
     *
     * @param modelClass the model class to be inserted into the database
     * @param <T>        the model type, which must extend {@link Model}
     */
    public <T extends Model> void insert(Class<T> modelClass) {
        try {
            // Create and populate the model with data provided by the user
            T instance = FieldInputManager.createAndPopulateModel(modelClass);

            // Insert the model into the database
            int result = super.insert(instance);

            if (result > 0) {
                System.out.println("Insert " + modelClass.getSimpleName() + " successful.");
            } else {
                System.out.println("Error during insert" + " " + modelClass.getSimpleName() + ".");
            }
        } catch (Exception e) {
            System.err.println("Error during insert" + " " + modelClass.getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing instance of the given model in the database.
     * <p>
     * This method uses the {@link FieldInputManager} class to create a new instance
     * of the model, asking the user to input the new values for the model's fields.
     * Then, it calls the {@link AbstractDMLDao#update(T)} method to update the instance
     * in the database.
     *
     * @param modelClass the model class to be updated in the database
     * @param <T>        the model type, which must extend {@link Model}
     */
    public <T extends Model> void update(Class<T> modelClass) {
        try {
            // Create and populate the model with the new data provided by the user
            T instance = FieldInputManager.createAndPopulateModel(modelClass);

            // Update the model in the database
            int result = super.update(instance);

            if (result > 0) {
                System.out.println("Update " + modelClass.getSimpleName() + " successful.");
            } else {
                System.out.println("Error during update" + " " + modelClass.getSimpleName() + ".");
            }
        } catch (Exception e) {
            System.err.println("Error during update" + " " + modelClass.getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes an instance of the given model from the database.
     * <p>
     * This method uses the {@link FieldInputManager} class to create a new instance
     * of the model and ask the user to input the necessary primary keys to identify
     * the row to delete. Then, it calls the {@link AbstractDMLDao#delete(T)} method
     * to remove the instance from the database.
     *
     * @param modelClass the model class to be deleted from the database
     * @param <T>        the model type, which must extend {@link Model}
     */
    public <T extends Model> void delete(Class<T> modelClass) {
        try {
            // Create the model with only the primary keys for deletion
            T instance = FieldInputManager.createModelWithPrimaryKeys(modelClass);

            // Delete the model from the database
            int result = super.delete(instance);

            if (result > 0) {
                System.out.println("Delete " + modelClass.getSimpleName() + " successful.");
            } else {
                System.out.println("Error during delete" + " " + modelClass.getSimpleName() + ".");
            }
        } catch (Exception e) {
            System.err.println("Error during delete" + " " + modelClass.getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
