package org.phinix.example.model;

import org.phinix.lib.common.util.Model;
import org.phinix.lib.common.util.PrimaryKey;

import java.util.Objects;

public class Driver implements Model {
    @PrimaryKey
    private int numDriver;
    private String name;
    private String surname;

    public Driver(int numDriver, String name, String surname) {
        this.numDriver = numDriver;
        this.name = name;
        this.surname = surname;
    }

    public Driver(int numDriver) {
        this.numDriver = numDriver;
    }

    public Driver() {}

    public int getNumDriver() {
        return numDriver;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setNumDriver(int numDriver) {
        this.numDriver = numDriver;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(numDriver, driver.numDriver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numDriver);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "numDriver='" + numDriver + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
