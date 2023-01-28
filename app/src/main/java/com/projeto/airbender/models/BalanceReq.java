package com.projeto.airbender.models;

public class BalanceReq {
    private int id, client_id;
    private double amount;
    private String requestDate, decisionDate, status;

    public BalanceReq(int id, double amount, String requestDate, String decisionDate, String status, int client_id) {
        this.id = id;
        this.amount = amount;
        this.requestDate = requestDate;
        this.decisionDate = decisionDate;
        this.status = status;
        this.client_id = client_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(String decisionDate) {
        this.decisionDate = decisionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
