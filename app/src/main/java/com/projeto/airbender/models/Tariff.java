package com.projeto.airbender.models;

public class Tariff {
    private int id;
    private String startDate;
    private double economicPrice;
    private double normalPrice;
    private double luxuryPrice;
    private int flight_id;
    private boolean active;

    public Tariff(int id, String startDate, double economicPrice, double normalPrice, double luxuryPrice, int flight_id, boolean active) {
        this.id = id;
        this.startDate = startDate;
        this.economicPrice = economicPrice;
        this.normalPrice = normalPrice;
        this.luxuryPrice = luxuryPrice;
        this.flight_id = flight_id;
        this.active = active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public double getEconomicPrice() {
        return economicPrice;
    }

    public void setEconomicPrice(double economicPrice) {
        this.economicPrice = economicPrice;
    }

    public double getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(double normalPrice) {
        this.normalPrice = normalPrice;
    }

    public double getLuxuryPrice() {
        return luxuryPrice;
    }

    public void setLuxuryPrice(double luxuryPrice) {
        this.luxuryPrice = luxuryPrice;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
