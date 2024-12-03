package org.phinix.common.example.model;

import org.phinix.lib.common.util.Model;
import org.phinix.lib.common.util.PrimaryKey;

import java.util.Objects;

public class Place implements Model {
    @PrimaryKey
    private int idPlace;
    private String city;
    private int cp;
    private String site;

    public Place(int idPlace, String city, int cp, String site) {
        this.idPlace = idPlace;
        this.city = city;
        this.cp = cp;
        this.site = site;
    }

    public Place(int idPlace) {
        this.idPlace = idPlace;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public String getCity() {
        return city;
    }

    public int getCp() {
        return cp;
    }

    public String getSite() {
        return site;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(idPlace, place.idPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlace);
    }

    @Override
    public String toString() {
        return "Place{" +
                "idPlace='" + idPlace + '\'' +
                ", city='" + city + '\'' +
                ", cp='" + cp + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
