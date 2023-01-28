package com.projeto.airbender.utils;


import android.content.ContentValues;

import com.projeto.airbender.models.Airplane;
import com.projeto.airbender.models.Airport;
import com.projeto.airbender.models.BalanceReq;
import com.projeto.airbender.models.Config;
import com.projeto.airbender.models.Flight;
import com.projeto.airbender.models.Tariff;
import com.projeto.airbender.models.Ticket;

public class ContentValuesHelper {
    public ContentValues getAirplane(Airplane airplane) {
        ContentValues values = new ContentValues();
        values.put("id", airplane.getId());
        values.put("luggageCapacity", airplane.getLuggageCappacity());
        values.put("minLinha", airplane.getMinLinha());
        values.put("minCol", String.valueOf(airplane.getMinCol()));
        values.put("maxLinha", airplane.getMaxLinha());
        values.put("maxCol", String.valueOf(airplane.getMaxCol()));
        values.put("economicStart", String.valueOf(airplane.getEconomicStart()));
        values.put("economicStop", String.valueOf(airplane.getEconomicStop()));
        values.put("normalStart", String.valueOf(airplane.getNormalStart()));
        values.put("normalStop", String.valueOf(airplane.getNormalStop()));
        values.put("luxuryStart", String.valueOf(airplane.getLuxuryStart()));
        values.put("luxuryStop", String.valueOf(airplane.getLuxuryStop()));
        values.put("status", String.valueOf(airplane.getStatus()));
        return values;
    }

    public ContentValues getAirport(Airport airport, String type) {
        ContentValues values = new ContentValues();
        values.put("id", airport.getId());
        values.put("country", airport.getCountry());
        values.put("code", airport.getCode());
        values.put("city", airport.getCity());
        values.put("search", airport.getSearch());
        values.put("status", airport.getStatus());
        values.put("type", type);
        return values;
    }
    public ContentValues getBalanceReq(BalanceReq balanceReq) {
        ContentValues values = new ContentValues();
        values.put("id", balanceReq.getId());
        values.put("amount", balanceReq.getAmount());
        values.put("requestDate", balanceReq.getRequestDate());
        values.put("decisionDate", balanceReq.getDecisionDate());
        values.put("status", balanceReq.getStatus());
        values.put("client_id", balanceReq.getClient_id());
        return values;
    }

    public ContentValues getConfig(Config config) {
        ContentValues values = new ContentValues();
        values.put("id", config.getId());
        values.put("description", config.getDescription());
        values.put("weight", config.getWeight());
        values.put("price", config.getPrice());
        values.put("active", config.isActive());
        return values;
    }

    public ContentValues getFlight(Flight flight, String type) {
        ContentValues values = new ContentValues();
        values.put("id", flight.getId());
        values.put("departureDate", flight.getDepartureDate());
        values.put("duration", flight.getDuration());
        values.put("airplane_id", flight.getAirplane());
        values.put("airportDeparture_id", flight.getAirportDeparture());
        values.put("airportArrival_id", flight.getAirportArrival());
        values.put("status", flight.getStatus());
        values.put("type", type);
        return values;
    }

    public ContentValues getTariff(Tariff tariff, String type) {
        ContentValues values = new ContentValues();
        values.put("id", tariff.getId());
        values.put("startDate", tariff.getStartDate());
        values.put("economicPrice", tariff.getEconomicPrice());
        values.put("normalPrice", tariff.getNormalPrice());
        values.put("luxuryPrice", tariff.getLuxuryPrice());
        values.put("flight_id", tariff.getFlight_id());
        values.put("active", tariff.isActive());
        values.put("type", type);
        return values;
    }

    public ContentValues getTicket(Ticket ticket, String type) {
        ContentValues values = new ContentValues();
        values.put("id", ticket.getId());
        values.put("fName", ticket.getfName());
        values.put("surname", ticket.getSurname());
        values.put("gender", ticket.getGender());
        values.put("age", ticket.getAge());
        values.put("checkedIn", ticket.getCheckedIn());
        values.put("client_id", ticket.getClient_id());
        values.put("flight_id", ticket.getFlight_id());
        values.put("seatLinha", ticket.getSeatLinha());
        values.put("seatCol", ticket.getSeatCol());
        values.put("luggage_1", ticket.getLuggage_1());
        values.put("luggage_2", ticket.getLuggage_2());
        values.put("receipt_id", ticket.getReceipt_id());
        values.put("tariff_id", ticket.getTariff_id());
        values.put("tariffType", ticket.getTariffType());
        values.put("type", type);
        return values;
    }





}
