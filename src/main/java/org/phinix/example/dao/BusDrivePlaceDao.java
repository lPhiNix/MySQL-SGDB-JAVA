package org.phinix.example.dao;

import org.phinix.example.util.FieldInputManager;
import org.phinix.lib.common.dao.dml.AbstractDMLDao;
import org.phinix.lib.common.util.Model;
import org.phinix.lib.service.MySQLConnection;

import java.util.Scanner;

public class BusDrivePlaceDao extends AbstractDMLDao {
    private final Scanner scanner = new Scanner(System.in);

    public BusDrivePlaceDao(MySQLConnection database) {
        super(database);
    }

    public <T extends Model> void insert(Class<T> modelClass) {
        try {
            T instance = FieldInputManager.createAndPopulateModel(modelClass);
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

    public <T extends Model> void update(Class<T> modelClass) {
        try {
            T instance = FieldInputManager.createAndPopulateModel(modelClass);
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

    public <T extends Model> void delete(Class<T> modelClass) {
        try {
            T instance = FieldInputManager.createModelWithPrimaryKeys(modelClass);
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
