package com.projeto.airbender.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;

import com.projeto.airbender.models.Airplane;
import com.projeto.airbender.models.Airport;
import com.projeto.airbender.models.BalanceReq;
import com.projeto.airbender.models.Flight;
import com.projeto.airbender.models.FlightInfo;
import com.projeto.airbender.models.Tariff;
import com.projeto.airbender.models.Ticket;
import com.projeto.airbender.models.TicketInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonParser {
    public static Map<String, String> parseJsonUpdateUserData(String response) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            JSONObject user = new JSONObject(response);
            map.put("username", user.getString("username"));
            map.put("fName", user.getJSONObject("userData").getString("fName"));
            map.put("surname", user.getJSONObject("userData").getString("surname"));
            map.put("nif", user.getJSONObject("userData").getString("nif"));
            map.put("phone", user.getJSONObject("userData").getString("phone"));
            map.put("balance", String.valueOf(user.getJSONObject("client").getDouble("balance")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> parserJsonLogin(String response) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            JSONObject login = new JSONObject(response);
            map.put("token", login.getString("token"));
            map.put("role", login.getString("role"));
            map.put("username", login.getString("username"));
            map.put("fName", login.getString("fName"));
            map.put("surname", login.getString("surname"));
            map.put("nif", login.getString("nif"));
            map.put("phone", login.getString("phone"));
            map.put("balance", String.valueOf(login.getDouble("balance")));
            map.put("id", String.valueOf(login.getInt("id")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static BalanceReq parseBalanceReq(String response) {
        BalanceReq balanceReq = null;
        try {
            JSONObject json = new JSONObject(response);
            int id = json.optInt("id");
            double amount = json.optDouble("amount");
            String requestDate = json.optString("requestDate");
            String decisionDate = json.optString("decisionDate");
            String status = json.optString("status");
            int client_id = json.optInt("client_id");
            balanceReq = new BalanceReq(id, amount, requestDate, decisionDate, status, client_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return balanceReq;
    }

    public static TicketInfo parseTicket(String response) {
        Ticket ticket = null;
        Flight flight = null;
        Airport airportArrival = null;
        Airport airportDeparture = null;
        TicketInfo ticketInfo = null;
        try {
            JSONObject json = new JSONObject(response);
            ticket = new Ticket(json.optInt("id"), json.optString("fName"), json.optString("surname"),
                    json.optString("gender"), json.optInt("age"), json.optInt("checkedIn"), json.optInt("client_id"),
                    json.optInt("flight_id"), json.optString("seatLinha"), json.optInt("seatCol"), json.optInt("luggage_1"),
                    json.optInt("luggage_2"), json.optInt("receipt_id"), json.optInt("tariff_id"), json.optString("tariffType"), "a");

            JSONObject jsonFlight = json.getJSONObject("flight");
            flight = new Flight(jsonFlight.optInt("id"), jsonFlight.optString("departureDate"), json.optString("duration"),
                    jsonFlight.optInt("airplane_id"), jsonFlight.optInt("airportDeparture_id"), jsonFlight.optInt("airportArrival_id"), jsonFlight.optString("status"), "a");

            JSONObject jsonAirportArrival = jsonFlight.getJSONObject("airportArrival");
            airportArrival = new Airport(jsonAirportArrival.optInt("id"), jsonAirportArrival.optString("country"), jsonAirportArrival.optString("code"),
                    jsonAirportArrival.optString("city"), jsonAirportArrival.optInt("search"), jsonAirportArrival.optString("status"), "a");

            JSONObject jsonAirportDeparture = jsonFlight.getJSONObject("airportDeparture");
            airportDeparture = new Airport(jsonAirportDeparture.optInt("id"), jsonAirportDeparture.optString("country"), jsonAirportDeparture.optString("code"),
                    jsonAirportDeparture.optString("city"), jsonAirportDeparture.optInt("search"), jsonAirportDeparture.optString("status"), "a");

            ticketInfo = new TicketInfo(ticket, airportDeparture, airportArrival, flight);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ticketInfo;
    }

    public static ArrayList<TicketInfo> parseTickets(JSONArray response) {
        Ticket ticket = null;
        Flight flight = null;
        Airport airportArrival = null;
        Airport airportDeparture = null;
        ArrayList<TicketInfo> ticketsInfo = new ArrayList<TicketInfo>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject json = response.optJSONObject(i);
                ticket = new Ticket(json.optInt("id"), json.optString("fName"), json.optString("surname"),
                        json.optString("gender"), json.optInt("age"), json.optInt("checkedIn"), json.optInt("client_id"),
                        json.optInt("flight_id"), json.optString("seatLinha"), json.optInt("seatCol"), json.optInt("luggage_1"),
                        json.optInt("luggage_2"), json.optInt("receipt_id"), json.optInt("tariff_id"), json.optString("tariffType"), "A");

                JSONObject jsonFlight = json.getJSONObject("flight");
                flight = new Flight(jsonFlight.optInt("id"), jsonFlight.optString("departureDate"), jsonFlight.optString("duration"),
                        jsonFlight.optInt("airplane_id"), jsonFlight.optInt("airportDeparture_id"), jsonFlight.optInt("airportArrival_id"), jsonFlight.optString("status"), "a");

                JSONObject jsonAirportArrival = jsonFlight.getJSONObject("airportArrival");
                airportArrival = new Airport(jsonAirportArrival.optInt("id"), jsonAirportArrival.optString("country"), jsonAirportArrival.optString("code"),
                        jsonAirportArrival.optString("city"), jsonAirportArrival.optInt("search"), jsonAirportArrival.optString("status"), "a");

                JSONObject jsonAirportDeparture = jsonFlight.getJSONObject("airportDeparture");
                airportDeparture = new Airport(jsonAirportDeparture.optInt("id"), jsonAirportDeparture.optString("country"), jsonAirportDeparture.optString("code"),
                        jsonAirportDeparture.optString("city"), jsonAirportDeparture.optInt("search"), jsonAirportDeparture.optString("status"), "a");

                ticketsInfo.add(new TicketInfo(ticket, airportDeparture, airportArrival, flight));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ticketsInfo;
    }

    public static ArrayList<BalanceReq> parseBalanceReqs(JSONArray response) {
        ArrayList<BalanceReq> balanceReqs = new ArrayList<BalanceReq>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject balanceReq = response.optJSONObject(i);
                int id = balanceReq.optInt("id");
                double amount = balanceReq.optDouble("amount");
                String requestDate = balanceReq.optString("requestDate");
                String decisionDate = balanceReq.optString("decisionDate");
                String status = balanceReq.optString("status");
                int client_id = balanceReq.optInt("client_id");
                balanceReqs.add(new BalanceReq(id, amount, requestDate, decisionDate, status, client_id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balanceReqs;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network ni = cm.getActiveNetwork();
        return ni != null && cm.getNetworkInfo(ni).isConnected();
    }

    public static ArrayList<Airport> parseAirports(JSONArray response) {
        ArrayList<Airport> airports = new ArrayList<Airport>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject airport = response.optJSONObject(i);
                int id = airport.optInt("id");
                String country = airport.optString("country");
                String city = airport.optString("city");
                String code = airport.optString("code");
                int search = airport.optInt("search");
                String status = airport.optString("status");
                airports.add(new Airport(id, country, code, city, search, status, "a"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return airports;
    }

    public static FlightInfo parseFlight(String response) {
        Flight flight = null;
        Airplane airplane = null;
        Tariff tariff = null;
        Airport airportArrival = null;
        Airport airportDeparture = null;
        FlightInfo flightInfo = null;
        try {
            JSONObject json = new JSONObject(response);
            flight = new Flight(json.optInt("id"), json.optString("departureDate"), json.optString("duration"),
                    json.optInt("airplane_id"), json.optInt("airportDeparture_id"), json.optInt("airportArrival_id"), json.optString("status"), "a");
            JSONObject jsonAirplane = json.getJSONObject("airplane");
            airplane = new Airplane(jsonAirplane.optInt("id"), jsonAirplane.optInt("luggageCapacity"), jsonAirplane.optInt("minLinha"),
                    jsonAirplane.optString("minCol"), jsonAirplane.optInt("maxLinha"), jsonAirplane.optString("maxCol"), jsonAirplane.optString("economicStart"), jsonAirplane.optString("economicStop"), jsonAirplane.optString("normalStart"), jsonAirplane.optString("normalStop"), jsonAirplane.optString("luxuryStart"), jsonAirplane.optString("luxuryStop"), jsonAirplane.optString("status"));
            JSONArray temp = json.getJSONArray("tariff");
            JSONObject jsonTariff = temp.getJSONObject(0);
            for (int i = 0; i < temp.length(); i++) {
                if (temp.getJSONObject(i).getInt("active") == 1)
                     jsonTariff = temp.getJSONObject(i);
            }
            tariff = new Tariff(jsonTariff.optInt("id"), jsonTariff.getString("startDate"), jsonTariff.optDouble("economicPrice"), jsonTariff.optDouble("normalPrice"), jsonTariff.optDouble("luxuryPrice"), jsonTariff.optInt("flight_id"), jsonTariff.optBoolean("active"));

            JSONObject jsonAirportArrival = json.getJSONObject("airportArrival");
            airportArrival = new Airport(jsonAirportArrival.optInt("id"), jsonAirportArrival.optString("country"), jsonAirportArrival.optString("code"),
                    jsonAirportArrival.optString("city"), jsonAirportArrival.optInt("search"), jsonAirportArrival.optString("status"), "a");

            JSONObject jsonAirportDeparture = json.getJSONObject("airportDeparture");
            airportDeparture = new Airport(jsonAirportDeparture.optInt("id"), jsonAirportDeparture.optString("country"), jsonAirportDeparture.optString("code"),
                    jsonAirportDeparture.optString("city"), jsonAirportDeparture.optInt("search"), jsonAirportDeparture.optString("status"), "a");

            flightInfo = new FlightInfo(flight, tariff, airplane, airportDeparture, airportArrival);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flightInfo;
    }
}
