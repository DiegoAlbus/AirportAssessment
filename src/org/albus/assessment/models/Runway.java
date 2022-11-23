package org.albus.assessment.models;

public class Runway {
    private int id;
    private int airport_ref;
    private String airport_ident;
    private String length_ft;
    private String width_ft;
    private String surface;
    private String lighted;
    private String closed;
    private String le_ident;
    private String le_latitude_deg;
    private String le_longitude_deg;
    private String le_elevation_ft;
    private String le_heading_degT;
    private String le_displaced_threshold_ft;
    private String he_ident;
    private String he_latitude_deg;
    private String he_longitude_deg;
    private String he_elevation_ft;
    private String he_heading_degT;
    private String he_displaced_threshold_ft;

    public Runway(int id, int airport_ref, String airport_ident, String length_ft, String width_ft, String surface, String lighted, String closed, String le_ident, String le_latitude_deg, String le_longitude_deg, String le_elevation_ft, String le_heading_degT, String le_displaced_threshold_ft, String he_ident, String he_latitude_deg, String he_longitude_deg, String he_elevation_ft, String he_heading_degT, String he_displaced_threshold_ft) {
        this.id = id;
        this.airport_ref = airport_ref;
        this.airport_ident = airport_ident;
        this.length_ft = length_ft;
        this.width_ft = width_ft;
        this.surface = surface;
        this.lighted = lighted;
        this.closed = closed;
        this.le_ident = le_ident;
        this.le_latitude_deg = le_latitude_deg;
        this.le_longitude_deg = le_longitude_deg;
        this.le_elevation_ft = le_elevation_ft;
        this.le_heading_degT = le_heading_degT;
        this.le_displaced_threshold_ft = le_displaced_threshold_ft;
        this.he_ident = he_ident;
        this.he_latitude_deg = he_latitude_deg;
        this.he_longitude_deg = he_longitude_deg;
        this.he_elevation_ft = he_elevation_ft;
        this.he_heading_degT = he_heading_degT;
        this.he_displaced_threshold_ft = he_displaced_threshold_ft;
    }

    @Override
    public String toString() {
        return "Runway{" +
                "id=" + id +
                ", airport_ref=" + airport_ref +
                ", airport_ident='" + airport_ident + '\'' +
                ", length_ft='" + length_ft + '\'' +
                ", width_ft='" + width_ft + '\'' +
                ", surface='" + surface + '\'' +
                ", lighted='" + lighted + '\'' +
                ", closed='" + closed + '\'' +
                ", le_ident='" + le_ident + '\'' +
                ", le_latitude_deg='" + le_latitude_deg + '\'' +
                ", le_longitude_deg='" + le_longitude_deg + '\'' +
                ", le_elevation_ft='" + le_elevation_ft + '\'' +
                ", le_heading_degT='" + le_heading_degT + '\'' +
                ", le_displaced_threshold_ft='" + le_displaced_threshold_ft + '\'' +
                ", he_ident='" + he_ident + '\'' +
                ", he_latitude_deg='" + he_latitude_deg + '\'' +
                ", he_longitude_deg='" + he_longitude_deg + '\'' +
                ", he_elevation_ft='" + he_elevation_ft + '\'' +
                ", he_heading_degT='" + he_heading_degT + '\'' +
                ", he_displaced_threshold_ft='" + he_displaced_threshold_ft + '\'' +
                '}';
    }
}
