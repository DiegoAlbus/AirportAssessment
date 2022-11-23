package org.albus.assessment.models;

public class Airport {
    private int id;
    private String ident;
    private String type;
    private String name;
    private String latitude_deg;
    private String longitude_deg;
    private String elevation_ft;
    private String continent;
    private String iso_country;
    private String iso_region;
    private String municipality;
    private String scheduled_service;
    private String gps_code;
    private String iata_code;
    private String local_code;
    private String home_link;
    private String wikipedia_link;
    private String keywords;

    public Airport(int id, String ident, String type, String name, String latitude_deg, String longitude_deg, String elevation_ft, String continent, String iso_country, String iso_region, String municipality, String scheduled_service, String gps_code, String iata_code, String local_code, String home_link, String wikipedia_link, String keywords) {
        this.id = id;
        this.ident = ident;
        this.type = type;
        this.name = name;
        this.latitude_deg = latitude_deg;
        this.longitude_deg = longitude_deg;
        this.elevation_ft = elevation_ft;
        this.continent = continent;
        this.iso_country = iso_country;
        this.iso_region = iso_region;
        this.municipality = municipality;
        this.scheduled_service = scheduled_service;
        this.gps_code = gps_code;
        this.iata_code = iata_code;
        this.local_code = local_code;
        this.home_link = home_link;
        this.wikipedia_link = wikipedia_link;
        this.keywords = keywords;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Testing
    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", ident='" + ident + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", latitude_deg='" + latitude_deg + '\'' +
                ", longitude_deg='" + longitude_deg + '\'' +
                ", elevation_ft='" + elevation_ft + '\'' +
                ", continent='" + continent + '\'' +
                ", iso_country='" + iso_country + '\'' +
                ", iso_region='" + iso_region + '\'' +
                ", municipality='" + municipality + '\'' +
                ", scheduled_service='" + scheduled_service + '\'' +
                ", gps_code='" + gps_code + '\'' +
                ", iata_code='" + iata_code + '\'' +
                ", local_code='" + local_code + '\'' +
                ", home_link='" + home_link + '\'' +
                ", wikipedia_link='" + wikipedia_link + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
