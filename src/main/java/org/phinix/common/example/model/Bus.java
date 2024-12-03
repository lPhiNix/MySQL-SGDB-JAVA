package org.phinix.common.example.model;

import org.phinix.lib.common.util.Model;
import org.phinix.lib.common.util.PrimaryKey;

import java.util.Objects;

public class Bus implements Model {
    @PrimaryKey
    private String register;
    private String licence;
    private String type;
    public Bus(String register, String licence, String type) {
        this.register = register;
        this.licence = licence;
        this.type = type;
    }

    public Bus(String register) {
        this.register = register;
    }

    public String getRegister() {
        return register;
    }

    public String getLicence() {
        return licence;
    }

    public String getType() {
        return type;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return Objects.equals(register, bus.register);
    }

    @Override
    public int hashCode() {
        return Objects.hash(register);
    }

    @Override
    public String toString() {
        return "Bus{" +
                "register='" + register + '\'' +
                ", licence='" + licence + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
