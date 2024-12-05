package org.phinix.example;

import org.phinix.example.dao.BusDrivePlaceDMLDao;
import org.phinix.example.dao.BusDrivePlaceQueryDao;
import org.phinix.example.model.Bus;
import org.phinix.example.model.Driver;
import org.phinix.example.model.Place;
import org.phinix.example.model.Routes;
import org.phinix.lib.service.MySQLConnection;

import java.util.Scanner;

public class Main {
    private static final MySQLConnection connection = MySQLConnection.getInstance(
            "jdbc:mysql://localhost:3306/busDrivePlace", "pablo", "12345"
    );
    private static final BusDrivePlaceDMLDao dmlDao = new BusDrivePlaceDMLDao(connection);
    private static final BusDrivePlaceQueryDao queryDao = new BusDrivePlaceQueryDao(connection);
    public static void main(String[] args) {
        System.out.flush();

        Scanner scanner = new Scanner(System.in);
        int input = 0;
        do {
            try {
                System.out.println(getFormatMenu());
                System.out.print("Insert function you want to use: ");
                input = scanner.nextInt();
                printLine();

                selectFunction(input);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (input != 6);

        System.out.println("Closing Application . . .");
    }

    public static void selectFunction(int input) {
        switch (input) {
            case 1 -> selectEntityToInsert();
            case 2 -> dmlDao.update(Routes.class);
            case 3 -> dmlDao.delete(Routes.class);
            case 4 -> queryDao.selectDriverAskingNumDriver();
            case 5 -> queryDao.selectRouteAskingKeys();
            case 6 -> System.exit(0);
            default -> System.out.println("Function doesn't found");
        }
    }

    public static void selectEntityToInsert() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select table name you want to insert (bus, driver, place, routes): ");
        String tableName = scanner.next();

        switch (tableName) {
            case "bus" -> dmlDao.insert(Bus.class);
            case "driver" -> dmlDao.insert(Driver.class);
            case "place" -> dmlDao.insert(Place.class);
            case "routes" -> dmlDao.insert(Routes.class);
            default -> System.out.println("Table doesn't found.");
        }
    }

    public static String getFormatMenu() {
        return """
                --------------------------------------
                      - BUS DRIVE PLACE SGDB -
                --------------------------------------
                1. Insert (Bus, Driver, Place, Routes)
                2. Update (Routes for week day)
                3. Delete (Routes asking pks)
                4. Query (Driver asking num driver)
                5. Query (Routes asking pks)
                6. Exit.
                --------------------------------------
                """;
    }

    public static void printLine() {
        System.out.println("--------------------------------------");
    }
}
