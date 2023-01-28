package com.projeto.airbender.listeners;

import com.projeto.airbender.models.Airport;

import java.util.ArrayList;

public interface AirportListener {
    void onRefreshAirports(ArrayList<Airport> airports);
}
