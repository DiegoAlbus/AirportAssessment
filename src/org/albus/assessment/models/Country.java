package org.albus.assessment.models;

public class Country {
    private int id;
    private String code;
    private String name;
    private String continent;
    private String wikipediaLink;
    private String keywords;

    public Country(int id, String code, String name, String continent, String wikipediaLink, String keywords) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.wikipediaLink = wikipediaLink;
        this.keywords = keywords;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // Testing
    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", continent='" + continent + '\'' +
                ", wikipediaLink='" + wikipediaLink + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
