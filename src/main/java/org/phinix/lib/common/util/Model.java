package org.phinix.lib.common.util;

/**
 * Represents a base interface for all models in the application and tables in db.
 * <p>
 * This interface provides utility methods for obtaining model names
 * dynamically or statically based on the implementing class or the interface itself.
 */
public interface Model {

    /**
     * Retrieves the dynamic model name based on the implementing class (Table Name).
     * <p>
     * The default implementation uses the simple name of the class in lowercase.
     *
     * @return the lowercase name of the implementing class
     */
    default String getDynamicModelName() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    /**
     * Retrieves the static model name based on the {@code Model} interface (Table Name).
     * <p>
     * This is always the name of the interface in lowercase.
     *
     * @return the lowercase name of the {@code Model} interface
     */
    static String getModelName() {
        return Model.class.getSimpleName().toLowerCase();
    }
}

