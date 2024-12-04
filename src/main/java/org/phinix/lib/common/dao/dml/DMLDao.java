package org.phinix.lib.common.dao.dml;

import org.phinix.lib.common.util.Model;

/**
 * Data Access Object (DAO) interface for performing Data Manipulation Language (DML) operations.
 * <p>
 * This interface defines methods for inserting, updating, and deleting model objects in the database.
 * The operations work generically with any class that implements the {@link Model} interface.
 */
public interface DMLDao {
    /**
     * Inserts a new model object into the database.
     * <p>
     * This method performs an insert operation for the given model.
     * It typically generates an SQL INSERT statement and executes it using the database connection.
     *
     * @param model the model object to be inserted into the database
     * @param <T>   the type of the model, which must extend {@link Model}
     * @return      the number of rows affected by the insert operation
     */
    <T extends Model> int insert(T model);

    /**
     * Updates an existing model object in the database.
     * <p>
     * This method performs an update operation for the given model.
     * It typically generates an SQL UPDATE statement using the fields of the model and executes it.
     *
     * @param model the model object to be updated in the database
     * @param <T>   the type of the model, which must extend {@link Model}
     * @return      the number of rows affected by the update operation
     */
    <T extends Model> int update(T model);

    /**
     * Deletes a model object from the database.
     * <p>
     * This method performs a delete operation for the given model.
     * It typically generates an SQL DELETE statement using the primary key values of the model.
     *
     * @param model the model object to be deleted from the database
     * @param <T>   the type of the model, which must extend {@link Model}
     * @return      the number of rows affected by the delete operation
     */
    <T extends Model> int delete(T model);
}
