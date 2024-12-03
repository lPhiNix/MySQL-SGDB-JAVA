package org.phinix.example;

import org.phinix.example.dao.BusDrivePlaceDao;
import org.phinix.example.model.Bus;
import org.phinix.example.model.Driver;
import org.phinix.example.model.Place;
import org.phinix.lib.service.MySQLConnection;

public class Main {

    public static void main(String[] args) {
        MySQLConnection connection = MySQLConnection.getInstance("pablo", "12345");
        BusDrivePlaceDao dao = new BusDrivePlaceDao(connection);

        dao.update(Bus.class);
    }

    public static String getFormatMenu() {
        return """
                
                """;
    }
}
