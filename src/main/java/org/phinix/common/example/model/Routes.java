package org.phinix.common.example.model;

import org.phinix.lib.common.util.Model;
import org.phinix.lib.common.util.PrimaryKey;

public class Routes implements Model {
    @PrimaryKey
    private String register;
    @PrimaryKey
    private int numDriver;
    @PrimaryKey
    private int idPlace;
    private String dayWeek;

    public Routes(Bus bus, Driver driver, Place place, String dayWeek) {
        this.register = bus.getRegister();
        this.numDriver = driver.getNumDriver();
        this.idPlace = place.getIdPlace();
        this.dayWeek = dayWeek;
    }

    public Routes() {}

    public Bus getBus(String busRegister) {
        return new Bus(busRegister);
    }

    public Driver getDriver(int driverNumber) {
        return new Driver(driverNumber);
    }

    public Place getPlace(int placeId) {
        return new Place(placeId);
    }

    public String getRegister() {
        return register;
    }

    public int getNumDriver() {
        return numDriver;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public void setNumDriver(int numDriver) {
        this.numDriver = numDriver;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    @Override
    public String toString() {
        return "Routes{" +
                "busRegister='" + register + '\'' +
                ", driverNumber=" + numDriver +
                ", placeId=" + idPlace +
                ", weakDay=" + dayWeek +
                '}';
    }
}
