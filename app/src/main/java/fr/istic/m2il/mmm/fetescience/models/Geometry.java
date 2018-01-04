package fr.istic.m2il.mmm.fetescience.models;

/**
 * Created by ismael on 03/01/18.
 */

public class Geometry {
    private String[] cordinates = new String[2];
    private String type;

    public String[] getCordinates() {
        return cordinates;
    }

    public void setCordinates(String[] cordinates) {
        this.cordinates = cordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
