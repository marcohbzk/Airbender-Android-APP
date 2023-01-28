package com.projeto.airbender.models;

public class TicketInfo {
    private Ticket ticket;
    private Airport airportDeparture;
    private Airport airportArrival;
    private Flight flight;

    public TicketInfo(Ticket ticket, Airport airportDeparture, Airport airportArrival, Flight flight) {
        this.ticket = ticket;
        this.airportDeparture = airportDeparture;
        this.airportArrival = airportArrival;
        this.flight = flight;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
