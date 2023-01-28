package com.projeto.airbender.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Base64;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.projeto.airbender.activities.AdminActivity;
import com.projeto.airbender.fragments.SelectAirportFragment;
import com.projeto.airbender.listeners.AirportListener;
import com.projeto.airbender.listeners.BalanceReqListener;
import com.projeto.airbender.listeners.FlightListener;
import com.projeto.airbender.listeners.LoginListener;
import com.projeto.airbender.listeners.TicketListener;
import com.projeto.airbender.utils.ContentValuesHelper;
import com.projeto.airbender.utils.DBHelper;
import com.projeto.airbender.utils.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SingletonAirbender {
    private static SingletonAirbender instance = null;
    private static RequestQueue requestQueue = null;
    private final DBHelper dbHelper;

    private ArrayList<BalanceReq> balanceReqs;
    private ArrayList<TicketInfo> tickets;
    private ArrayList<Airport> airports;
    private Flight flight;

    private static final String PATH = "/sis/airbender/backend/web/api/";

    private LoginListener loginListener;
    private BalanceReqListener balanceReqListener;
    private TicketListener ticketUpcomingListener;
    private TicketListener ticketPendingListener;
    private TicketListener ticketPastListener;
    private AirportListener airportListener;
    private FlightListener flightListener;

    private final ContentValuesHelper contentValuesHelper;

    public static synchronized SingletonAirbender getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonAirbender(context);
            requestQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonAirbender(Context context) {
        dbHelper = new DBHelper(context);
        contentValuesHelper = new ContentValuesHelper();
    }

    public String makeURL(String server, String params, String token) {
        if (token == null)
            return "http://" + server + PATH + params;
        else
            return "http://" + server + PATH + params + "?access-token=" + token;
    }

    public String getServer(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings", 0);
        return preferences.getString("SERVER", "");
    }

    public String getToken(Context context) {
        SharedPreferences user = context.getSharedPreferences("user_data", 0);
        return user.getString("TOKEN", "");
    }

    public int getUserID(Context context) {
        SharedPreferences user = context.getSharedPreferences("user_data", 0);
        return user.getInt("ID", 0);
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setBalanceReqListener(BalanceReqListener balanceReqListener) {
        this.balanceReqListener = balanceReqListener;
    }

    public void setTicketPendingListener(TicketListener ticketListener) {
        this.ticketPendingListener = ticketListener;
    }

    public void setTicketUpcomingListener(TicketListener ticketListener) {
        this.ticketUpcomingListener = ticketListener;
    }

    public void setTicketPastListener(TicketListener ticketListener) {
        this.ticketPastListener = ticketListener;
    }

    public void setAirportListener(AirportListener airportListener) {
        this.airportListener = airportListener;
    }

    public void setFlightListener(FlightListener flightListener) {
        this.flightListener = flightListener;
    }

    public void getUserData(final Context context) {
        if (!JsonParser.isConnectionInternet(context)) {
            return;
        } else {
            StringRequest req = new StringRequest(Request.Method.GET, makeURL(getServer(context), "user", getToken(context)), new Response.Listener<String>() {
                // Sucesso
                @Override
                public void onResponse(String response) {
                    loginListener.updateUserData(JsonParser.parseJsonUpdateUserData(response));
                }
            },
                    // erro
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                if (error.networkResponse.statusCode == 500) {
                                    Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (error.networkResponse.statusCode == 403) {
                                    Toast.makeText(context, "Wrong credentials", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (Exception ex) {
                                Toast.makeText(context, "Server not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
            requestQueue.add(req);
        }

    }

    public void loginAPI(final Context context, String username, String password) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.GET, makeURL(getServer(context), "login", null), new Response.Listener<String>() {
                // Sucesso
                @Override
                public void onResponse(String response) {
                    loginListener.onLogin(JsonParser.parserJsonLogin(response));
                }
            },
                    // erro
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                if (error.networkResponse.statusCode == 500) {
                                    Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (error.networkResponse.statusCode == 403) {
                                    Toast.makeText(context, "Wrong credentials", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (Exception ex) {
                                Toast.makeText(context, "Server not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            ) {
                // Parametros a serem enviados
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();

                    if (!(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O))
                        return null;

                    byte[] base64 = Base64.getEncoder().encode((username + ':' + password).getBytes());
                    params.put("Authorization", "Basic " + new String(base64));

                    return params;
                }
            };
            requestQueue.add(req);
        }

    }

    public BalanceReq getBalanceReq(int id) {
        for (BalanceReq balanceReq : balanceReqs) {
            if (balanceReq.getId() == id) {
                return balanceReq;
            }
        }
        return null;
    }

    private Flight findFlight(ArrayList<Flight> flights, int flight_id) {
        for (Flight flight : flights)
            if (flight.getId() == flight_id)
                return flight;
        return null;
    }

    private Airport findAirport(ArrayList<Airport> airports, int airport_id) {
        for (Airport airport : airports) {
            if (airport.getId() == airport_id) {
                return airport;
            }
        }
        return null;
    }

    public boolean airportExists(Airport airport) {
        ArrayList<Airport> airports = dbHelper.getAllAirports();
        for (Airport a : airports) {
            if (a.getId() == airport.getId())
                return true;
        }
        return false;
    }

    public void replaceAirportToType(Airport airport, String type) {
        ArrayList<Airport> airports = dbHelper.getAllAirports();
        for (Airport a : airports) {
            if (a.getId() == airport.getId() && !Objects.equals(a.getType(), type)) {
                dbHelper.updateDB("airports", contentValuesHelper.getAirport(airport, type));
            }
        }
    }

    public void replaceFlightToType(Flight flight, String type) {
        ArrayList<Flight> flights = dbHelper.getAllFlights();
        for (Flight f : flights) {
            if (f.getId() == flight.getId() && !Objects.equals(f.getType(), type)) {
                dbHelper.updateDB("flights", contentValuesHelper.getFlight(flight, type));
            }
        }
    }


    public void replaceTicketToType(Ticket ticket, String type) {
        ArrayList<Ticket> tickets = dbHelper.getAllTickets();
        for (Ticket t : tickets) {
            if (t.getId() == ticket.getId() && !Objects.equals(t.getType(), type)) {
                dbHelper.updateDB("tickets", contentValuesHelper.getTicket(ticket, type));
            }
        }
    }

    public boolean flightExists(Flight flight) {
        ArrayList<Flight> flights = dbHelper.getAllFlights();
        for (Flight f : flights) {
            if (f.getId() == flight.getId())
                return true;
        }
        return false;
    }

    public boolean ticketExists(Ticket ticket) {
        ArrayList<Ticket> tickets = dbHelper.getAllTickets();
        for (Ticket t : tickets) {
            if (t.getId() == ticket.getId())
                return true;
        }
        return false;
    }

    public void getTicketsFromDB(int position) {
        final int UPCOMING = 0, PENDING = 1, PAST = 2;
        String type = position == UPCOMING ? "upcoming" : position == PENDING ? "pending" : "past";
        ArrayList<TicketInfo> ticketInfo = new ArrayList<TicketInfo>();
        ArrayList<Ticket> tickets = dbHelper.getTickets("type", type);
        ArrayList<Flight> flights = dbHelper.getAllFlights();
        ArrayList<Airport> airports = dbHelper.getAllAirports();
        for (Ticket ticket : tickets) {
            Flight flight = findFlight(flights, ticket.getFlight_id());
            Airport airportArrival = findAirport(airports, flight.getAirportArrival());
            Airport airportDeparture = findAirport(airports, flight.getAirportDeparture());

            ticketInfo.add(new TicketInfo(ticket, airportDeparture, airportArrival, flight));
        }
        if (position == UPCOMING) {
            if (ticketUpcomingListener != null)
                ticketUpcomingListener.onRefreshTicketList(ticketInfo);
        } else if (position == PENDING) {
            if (ticketPendingListener != null)
                ticketPendingListener.onRefreshTicketList(ticketInfo);
        } else {
            if (ticketPastListener != null)
                ticketPastListener.onRefreshTicketList(ticketInfo);
        }
    }

    public void requestTicketsAPI(final Context context, int position) {
        if (!JsonParser.isConnectionInternet(context)) {
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "No internet connection", Snackbar.LENGTH_SHORT).show();
            return;
        }
        final int UPCOMING = 0, PENDING = 1, PAST = 2;
        String path = position == UPCOMING ? "upcoming" : position == PENDING ? "pending" : "past";
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, makeURL(getServer(context), "tickets/" + path, getToken(context)), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                tickets = JsonParser.parseTickets(response);
                dbHelper.deleteDB("tickets", "type", path);

                for (TicketInfo ticket : tickets) {
                    if (!airportExists(ticket.getAirportArrival()))
                        dbHelper.insertDB("airports", contentValuesHelper.getAirport(ticket.getAirportArrival(), path));
                    else
                        replaceAirportToType(ticket.getAirportArrival(), path);
                    if (!airportExists(ticket.getAirportDeparture()))
                        dbHelper.insertDB("airports", contentValuesHelper.getAirport(ticket.getAirportDeparture(), path));
                    else
                        replaceAirportToType(ticket.getAirportArrival(), path);
                    if (!flightExists(ticket.getFlight()))
                        dbHelper.insertDB("flights", contentValuesHelper.getFlight(ticket.getFlight(), path));
                    else
                        replaceFlightToType(ticket.getFlight(), path);
                    if (!ticketExists(ticket.getTicket()))
                        dbHelper.insertDB("tickets", contentValuesHelper.getTicket(ticket.getTicket(), path));
                    else
                        replaceTicketToType(ticket.getTicket(), path);
                }
                getTicketsFromDB(position);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(req);
    }

    public void getAllBalanceReqsDB(final Context context) {
        if (balanceReqListener != null)
            balanceReqListener.onRefreshBalanceReqList(dbHelper.getBalanceReq());
    }

    public void getAllAirportsDB(final Context context) {
        if (airportListener != null)
            airportListener.onRefreshAirports(dbHelper.getAllAirports());
    }

    public void addBalanceReq(final int amount, final Context context) {
        if (!JsonParser.isConnectionInternet(context)) {
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "No internet connection", Snackbar.LENGTH_SHORT).show();
        } else {
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("amount", amount);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, makeURL(getServer(context), "balance-req", getToken(context)), jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                requestBalanceReqsAPI(context);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                    }
                }

                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }
                };
                requestQueue.add(jsonObjectRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Airport> requestAirportsAPI(final Context context) {
        System.out.println("requestAirportsAPI");
        if (!JsonParser.isConnectionInternet(context)) {
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Could not update to most recent information", Snackbar.LENGTH_SHORT).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, makeURL(getServer(context), "airports", getToken(context)), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    airports = JsonParser.parseAirports(response);
                    dbHelper.deleteDB("airports", "type", "buy");
                    for (Airport airport : airports) {
                        if (!airportExists(airport))
                            dbHelper.insertDB("airports", contentValuesHelper.getAirport(airport, "buy"));
                    }
                    if (airportListener != null)
                        airportListener.onRefreshAirports(dbHelper.getAllAirports());
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            requestQueue.add(req);
        }
        return airports;
    }

    public Flight requestFlightAPI(final Context context, final String airportDeparture, final String airportArrival, final String departureDate) {
        if (!JsonParser.isConnectionInternet(context)) {
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Could not update to most recent information", Snackbar.LENGTH_SHORT).show();
        } else {
            String mpath = "flights/" + dbHelper.getAirports("city", airportDeparture).get(0).getId() + "/" + dbHelper.getAirports("city", airportArrival).get(0).getId() + "/" + departureDate;
            StringRequest req = new StringRequest(Request.Method.GET, makeURL(getServer(context), mpath, getToken(context)), new Response.Listener<String>() {
                // Sucesso
                @Override
                public void onResponse(String response) {
                    FlightInfo flightInfo = JsonParser.parseFlight(response);
                    if (airportListener != null)
                        flightListener.onRefreshFlight(flightInfo);
                }
            },
                    // erro
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                if (error.networkResponse.statusCode == 500) {
                                    Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (error.networkResponse.statusCode == 403) {
                                    Toast.makeText(context, "Wrong credentials", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (Exception ex) {
                                Toast.makeText(context, "Server not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
            requestQueue.add(req);
        }
        return flight;
    }

    public ArrayList<BalanceReq> requestBalanceReqsAPI(final Context context) {
        if (!JsonParser.isConnectionInternet(context)) {
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Could not update to most recent information", Snackbar.LENGTH_SHORT).show();
            if (balanceReqListener != null)
                balanceReqListener.onRefreshBalanceReqList(dbHelper.getBalanceReq());
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, makeURL(getServer(context), "balance-req", getToken(context)), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    balanceReqs = JsonParser.parseBalanceReqs(response);
                    dbHelper.deleteAllDB("balanceReq");
                    for (BalanceReq balanceReq : balanceReqs) {
                        dbHelper.insertDB("balanceReq", contentValuesHelper.getBalanceReq(balanceReq));
                    }
                    if (balanceReqListener != null)
                        balanceReqListener.onRefreshBalanceReqList(dbHelper.getBalanceReq());
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            requestQueue.add(req);
        }
        return balanceReqs;
    }

    public void deleteBalanceReq(Context context, BalanceReq balanceReq) {
        if (!JsonParser.isConnectionInternet(context)) {
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "No internet connection", Snackbar.LENGTH_SHORT).show();
            if (balanceReqListener != null)
                balanceReqListener.onRefreshBalanceReqList(dbHelper.getBalanceReq());
        } else {
            StringRequest req = new StringRequest(Request.Method.DELETE, makeURL(getServer(context), "balance-req/" + balanceReq.getId(), getToken(context)), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dbHelper.deleteID("balanceReq", balanceReq.getId());
                    Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Deleted Sucessfully", Snackbar.LENGTH_SHORT).show();
                    if (balanceReqListener != null)
                        balanceReqListener.onRefreshBalanceReqList(dbHelper.getBalanceReq());
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse.statusCode == 403) {
                                if (balanceReqListener != null)
                                    balanceReqListener.onRefreshBalanceReqList(dbHelper.getBalanceReq());
                                Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Cannot delete decided balance requests", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
            requestQueue.add(req);
        }
    }

    public void checkIn(Context context, String ticket) {
        if (!JsonParser.isConnectionInternet(context)) {
            Snackbar.make(((AdminActivity) context).findViewById(android.R.id.content), "No internet connection", Snackbar.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.PUT, makeURL(getServer(context), "tickets/checkin/" + ticket, getToken(context)), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Snackbar.make(((AdminActivity) context).findViewById(android.R.id.content), "Checked in successfully!", Snackbar.LENGTH_SHORT).show();
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            );
            requestQueue.add(req);
        }
    }

    public void updateUser(Context context, String fname, String surname, String nif, String phone) {
        if (!JsonParser.isConnectionInternet(context)) {
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "No internet connection", Snackbar.LENGTH_SHORT).show();
        } else {
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("fName", fname);
                jsonBody.put("surname", surname);
                jsonBody.put("nif", nif);
                jsonBody.put("phone", phone);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, makeURL(getServer(context), "user/change", getToken(context)), jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                getUserData(context);
                                Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Updated successfully!", Snackbar.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(((Activity) context).findViewById(android.R.id.content), "There was an error while trying to update!", Snackbar.LENGTH_SHORT).show();
                    }
                }

                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }
                };
                requestQueue.add(jsonObjectRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void payTicket(Context context, int id) {
        if (!JsonParser.isConnectionInternet(context)) {
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "No internet connection", Snackbar.LENGTH_SHORT).show();
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, makeURL(getServer(context), "tickets/pay/" + id, getToken(context)), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            requestTicketsAPI(context, 1);
                            requestTicketsAPI(context, 0);
                            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Payed successfully!", Snackbar.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(((Activity) context).findViewById(android.R.id.content), "There was an error while trying to pay!", Snackbar.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }

    public void deleteTicket(Context context, Ticket ticket) {
        if (!JsonParser.isConnectionInternet(context)) {
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "No internet connection", Snackbar.LENGTH_SHORT).show();
            getTicketsFromDB(1);
        } else {
            StringRequest req = new StringRequest(Request.Method.DELETE, makeURL(getServer(context), "tickets/" + ticket.getId(), getToken(context)), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dbHelper.deleteID("tickets", ticket.getId());
                    Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Deleted Sucessfully", Snackbar.LENGTH_SHORT).show();
                    getTicketsFromDB(1);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            getTicketsFromDB(1);
                            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Cannot delete tickets", Snackbar.LENGTH_SHORT).show();
                        }
                    }
            );
            requestQueue.add(req);
        }
    }
}
