package com.projeto.airbender.models;

public class Airport {
    private int id;
    private String country;
    private String code;
    private String city;
    private int search;
    private String status;
    private String type;

    public Airport(int id, String country, String code, String city, int search, String status, String type) {
        this.id = id;
        this.country = country;
        this.code = code;
        this.city = city;
        this.search = search;
        this.status = status;
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSearch() {
        return search;
    }

    public void setSearch(int search) {
        this.search = search;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
