package com.projeto.airbender.models;

public class Airplane {
    private int id;
    private int luggageCappacity;
    private int minLinha;
    private String minCol;
    private int maxLinha;
    private String maxCol;
    private String economicStart;
    private String economicStop;
    private String normalStart;
    private String normalStop;
    private String luxuryStart;
    private String luxuryStop;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getLuggageCappacity() {
        return luggageCappacity;
    }

    public void setLuggageCappacity(int luggageCappacity) {
        this.luggageCappacity = luggageCappacity;
    }

    public int getMinLinha() {
        return minLinha;
    }

    public void setMinLinha(int minLinha) {
        this.minLinha = minLinha;
    }

    public String getMinCol() {
        return minCol;
    }

    public void setMinCol(String minCol) {
        this.minCol = minCol;
    }

    public int getMaxLinha() {
        return maxLinha;
    }

    public void setMaxLinha(int maxLinha) {
        this.maxLinha = maxLinha;
    }

    public String getMaxCol() {
        return maxCol;
    }

    public void setMaxCol(String maxCol) {
        this.maxCol = maxCol;
    }

    public String getEconomicStart() {
        return economicStart;
    }

    public void setEconomicStart(String economicStart) {
        this.economicStart = economicStart;
    }

    public String getEconomicStop() {
        return economicStop;
    }

    public void setEconomicStop(String economicStop) {
        this.economicStop = economicStop;
    }

    public String getNormalStart() {
        return normalStart;
    }

    public void setNormalStart(String normalStart) {
        this.normalStart = normalStart;
    }

    public String getNormalStop() {
        return normalStop;
    }

    public void setNormalStop(String normalStop) {
        this.normalStop = normalStop;
    }

    public String getLuxuryStart() {
        return luxuryStart;
    }

    public void setLuxuryStart(String luxuryStart) {
        this.luxuryStart = luxuryStart;
    }

    public String getLuxuryStop() {
        return luxuryStop;
    }

    public void setLuxuryStop(String luxuryStop) {
        this.luxuryStop = luxuryStop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Airplane(int id, int luggageCappacity, int minLinha, String minCol, int maxLinha, String maxCol, String economicStart, String economicStop, String normalStart, String normalStop, String luxuryStart, String luxuryStop, String status) {
        this.id = id;
        this.luggageCappacity = luggageCappacity;
        this.minLinha = minLinha;
        this.minCol = minCol;
        this.maxLinha = maxLinha;
        this.maxCol = maxCol;
        this.economicStart = economicStart;
        this.economicStop = economicStop;
        this.normalStart = normalStart;
        this.normalStop = normalStop;
        this.luxuryStart = luxuryStart;
        this.luxuryStop = luxuryStop;
        this.status = status;
    }



}
