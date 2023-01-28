package com.projeto.airbender.listeners;

import com.projeto.airbender.models.Flight;
import com.projeto.airbender.models.FlightInfo;

import java.util.ArrayList;

public interface FlightListener {
    void onRefreshFlight(FlightInfo flight);
}
