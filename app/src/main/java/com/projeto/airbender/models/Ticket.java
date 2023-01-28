package com.projeto.airbender.models;

public class Ticket {
    private int id;
    private String fName;
    private String surname;
    private String gender;
    private int age;
    private int checkedIn;
    private int client_id;
    private int flight_id;
    private String seatLinha;
    private int seatCol;
    private int luggage_1;
    private int luggage_2;
    private int receipt_id;
    private int tariff_id;
    private String tariffType;
    private String type;

    public Ticket(int id, String fName, String surname, String gender, int age, int checkedIn, int client_id, int flight_id, String seatLinha, int seatCol, int luggage_1, int luggage_2, int receipt_id, int tariff_id, String tariffType, String type) {
        this.id = id;
        this.fName = fName;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
        this.checkedIn = checkedIn;
        this.client_id = client_id;
        this.flight_id = flight_id;
        this.seatLinha = seatLinha;
        this.seatCol = seatCol;
        this.luggage_1 = luggage_1;
        this.luggage_2 = luggage_2;
        this.receipt_id = receipt_id;
        this.tariff_id = tariff_id;
        this.tariffType = tariffType;
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(int checkedIn) {
        this.checkedIn = checkedIn;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public String getSeatLinha() {
        return seatLinha;
    }

    public void setSeatLinha(String seatLinha) {
        this.seatLinha = seatLinha;
    }

    public int getSeatCol() {
        return seatCol;
    }

    public void setSeatCol(int seatCol) {
        this.seatCol = seatCol;
    }

    public int getLuggage_1() {
        return luggage_1;
    }

    public void setLuggage_1(int luggage_1) {
        this.luggage_1 = luggage_1;
    }

    public int getLuggage_2() {
        return luggage_2;
    }

    public void setLuggage_2(int luggage_2) {
        this.luggage_2 = luggage_2;
    }

    public int getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(int receipt_id) {
        this.receipt_id = receipt_id;
    }

    public int getTariff_id() {
        return tariff_id;
    }

    public void setTariff_id(int tariff_id) {
        this.tariff_id = tariff_id;
    }

    public String getTariffType() {
        return tariffType;
    }

    public void setTariffType(String tariffType) {
        this.tariffType = tariffType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
