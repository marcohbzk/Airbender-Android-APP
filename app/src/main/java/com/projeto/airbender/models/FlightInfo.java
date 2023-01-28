package com.projeto.airbender.models;

public class FlightInfo {
    private Flight flight;
    private Tariff tariff;
    private Airplane airplane;
    private Airport airportDeparture;
    private Airport airportArrival;

    public FlightInfo(Flight flight, Tariff tariff, Airplane airplane, Airport airportDeparture, Airport airportArrival) {
        this.flight = flight;
        this.tariff = tariff;
        this.airplane = airplane;
        this.airportDeparture = airportDeparture;
        this.airportArrival = airportArrival;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Airport getAirportDeparture() {
        return airportDeparture;
    }

    public void setAirportDeparture(Airport airportDeparture) {
        this.airportDeparture = airportDeparture;
    }

    public Airport getAirportArrival() {
        return airportArrival;
    }

    public void setAirportArrival(Airport airportArrival) {
        this.airportArrival = airportArrival;
    }
}
