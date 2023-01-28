package com.projeto.airbender.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.projeto.airbender.R;
import com.projeto.airbender.listeners.FlightListener;
import com.projeto.airbender.models.FlightInfo;
import com.projeto.airbender.models.SingletonAirbender;

public class FlightDetailFragment extends Fragment implements FlightListener {
    private final String airportDeparture;
    private final String airportArrival;
    private final String departureDate;
    private TextView tvAirportDeparture, tvAirportArrival, tvDate, tvAirportDepartureCountry, tvAirportDepartureCity, tvAirportDepartureCode, tvAirportDepartureStatus, tvAirportArrivalCountry, tvAirportArrivalCity, tvAirportArrivalCode, tvAirportArrivalStatus, tvEconomicPrice, tvNormalPrice, tvLuxuryPrice;
    private FloatingActionButton fabBack;

    public FlightDetailFragment(String airportDeparture, String airportArrival, String departureDate) {
        this.airportDeparture = airportDeparture;
        this.airportArrival = airportArrival;
        this.departureDate = departureDate;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_detail, container, false);
        fabBack = view.findViewById(R.id.fabBack);

        SingletonAirbender.getInstance(getContext()).setFlightListener(this);
        SingletonAirbender.getInstance(getContext()).requestFlightAPI(getContext(), airportDeparture, airportArrival, departureDate);

        tvAirportDeparture = ((TextView)view.findViewById(R.id.tvAirportDeparture));
        tvAirportArrival = ((TextView)view.findViewById(R.id.tvAirportArrival));
        tvDate = ((TextView)view.findViewById(R.id.tvDate));
        tvAirportDepartureCountry = ((TextView)view.findViewById(R.id.tvAirportDepartureCountry));
        tvAirportDepartureCity = ((TextView)view.findViewById(R.id.tvAirportDepartureCity));
        tvAirportDepartureCode = ((TextView)view.findViewById(R.id.tvAirportDepartureCode));
        tvAirportDepartureStatus = ((TextView)view.findViewById(R.id.tvAirportDepartureStatus));
        tvAirportArrivalCountry = ((TextView)view.findViewById(R.id.tvAirportArrivalCountry));
        tvAirportArrivalCity = ((TextView)view.findViewById(R.id.tvAirportArrivalCity));
        tvAirportArrivalCode = ((TextView)view.findViewById(R.id.tvAirportArrivalCode));
        tvAirportArrivalStatus = ((TextView)view.findViewById(R.id.tvAirportArrivalStatus));
        tvEconomicPrice = ((TextView)view.findViewById(R.id.tvEconomicPrice));
        tvNormalPrice = ((TextView)view.findViewById(R.id.tvNormalPrice));
        tvLuxuryPrice = ((TextView)view.findViewById(R.id.tvLuxuryPrice));

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getActivity()).onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onRefreshFlight(FlightInfo flight) {
        if(flight != null) {
            loadFlight(flight);
        } else {
            Snackbar.make(getView(), "No flights found", Snackbar.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }
    }
    private void loadFlight(FlightInfo flightInfo) {
        tvAirportDeparture.setText(flightInfo.getAirportDeparture().getCity());
        tvAirportArrival.setText(flightInfo.getAirportArrival().getCity());
        tvDate.setText(flightInfo.getFlight().getDepartureDate());
        tvAirportDepartureCountry.setText(flightInfo.getAirportDeparture().getCountry());
        tvAirportDepartureCity.setText(flightInfo.getAirportDeparture().getCity());
        tvAirportDepartureCode.setText(flightInfo.getAirportDeparture().getCode());
        tvAirportDepartureStatus.setText(flightInfo.getAirportDeparture().getStatus());
        tvAirportArrivalCountry.setText(flightInfo.getAirportArrival().getCountry());
        tvAirportArrivalCity.setText(flightInfo.getAirportArrival().getCity());
        tvAirportArrivalCode.setText(flightInfo.getAirportArrival().getCode());
        tvAirportArrivalStatus.setText(flightInfo.getAirportArrival().getStatus());
        tvEconomicPrice.setText(String.format("%s€", flightInfo.getTariff().getEconomicPrice()));
        tvNormalPrice.setText(String.format("%s€", flightInfo.getTariff().getNormalPrice()));
        tvLuxuryPrice.setText(String.format("%s€", flightInfo.getTariff().getLuxuryPrice()));
    }
}