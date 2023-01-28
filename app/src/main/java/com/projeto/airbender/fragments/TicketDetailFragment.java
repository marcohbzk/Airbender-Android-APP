package com.projeto.airbender.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.projeto.airbender.R;
import com.projeto.airbender.activities.MainActivity;
import com.projeto.airbender.models.SingletonAirbender;
import com.projeto.airbender.models.TicketInfo;

import java.util.Objects;

public class TicketDetailFragment extends Fragment {

    private final TicketInfo ticketInfo;
    private ExtendedFloatingActionButton fabCheckin, fabPay;
    private FloatingActionButton fabBack;
    private MainActivity activity;
    private TextView tvAirportDeparture, tvAirportArrival, tvDate;
    private final int position;

    public TicketDetailFragment(TicketInfo ticketInfo, int position) {
        this.ticketInfo = ticketInfo;
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_detail, container, false);


        fabCheckin = view.findViewById(R.id.fabCheckIn);
        fabPay = view.findViewById(R.id.fabPay);
        fabBack = view.findViewById(R.id.fabBack);
        activity = (MainActivity) getActivity();

        loadTicket(view);


        if(position != 0)
            fabCheckin.setVisibility(View.GONE);
        if(position != 1)
            fabPay.setVisibility(View.GONE);

        fabPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonAirbender.getInstance(getContext()).payTicket(getContext(), ticketInfo.getTicket().getId());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                );
                fragmentTransaction.replace(R.id.frameLayout, new TicketFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        fabCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                );

                fragmentTransaction.replace(R.id.frameLayout, new GenerateQRCodeFragment(ticketInfo));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        return view;
    }
    private void loadTicket(View view){
        ((TextView)view.findViewById(R.id.tvAirportDeparture)).setText(ticketInfo.getAirportDeparture().getCity());
        ((TextView)view.findViewById(R.id.tvAirportArrival)).setText(ticketInfo.getAirportArrival().getCity());
        ((TextView)view.findViewById(R.id.tvDate)).setText(ticketInfo.getFlight().getDepartureDate());
        ((TextView)view.findViewById(R.id.tvName)).setText(String.format("%s %s", ticketInfo.getTicket().getfName(), ticketInfo.getTicket().getSurname()));
        ((TextView)view.findViewById(R.id.tvAge)).setText(String.format("%d years old", ticketInfo.getTicket().getAge()));
        ((TextView)view.findViewById(R.id.tvGender)).setText(Objects.equals(ticketInfo.getTicket().getGender(), "M") ? "Male" : "Female");
        ((TextView)view.findViewById(R.id.tvSeat)).setText(String.format("Seat %d%s", ticketInfo.getTicket().getSeatCol(), ticketInfo.getTicket().getSeatLinha()));
        ((TextView)view.findViewById(R.id.tvTariffType)).setText(ticketInfo.getTicket().getTariffType());
        ((TextView)view.findViewById(R.id.tvLuggage1)).setText(String.format("%dkg", ticketInfo.getTicket().getLuggage_1() == 2 ? 10 : 20));
        ((TextView)view.findViewById(R.id.tvLuggage2)).setText(String.format("%dkg", ticketInfo.getTicket().getLuggage_2() == 2 ? 10 : 20));
        if(ticketInfo.getTicket().getLuggage_1() == 0) {
            ((ImageView) view.findViewById(R.id.ivLuggage1)).setImageResource(Integer.valueOf(R.drawable.ic_baseline_no_luggage_24));
            ((TextView)view.findViewById(R.id.tvLuggage1)).setText("None");
        }
        if(ticketInfo.getTicket().getLuggage_2() == 0) {
            ((ImageView) view.findViewById(R.id.ivLuggage2)).setImageResource(Integer.valueOf(R.drawable.ic_baseline_no_luggage_24));
            ((TextView)view.findViewById(R.id.tvLuggage2)).setText("None");
        }
        ((TextView)view.findViewById(R.id.tvAirportDepartureCountry)).setText(ticketInfo.getAirportDeparture().getCountry());
        ((TextView)view.findViewById(R.id.tvAirportDepartureCity)).setText(ticketInfo.getAirportDeparture().getCity());
        ((TextView)view.findViewById(R.id.tvAirportDepartureCode)).setText(ticketInfo.getAirportDeparture().getCode());
        ((TextView)view.findViewById(R.id.tvAirportDepartureStatus)).setText(ticketInfo.getAirportDeparture().getStatus());
        ((TextView)view.findViewById(R.id.tvAirportArrivalCountry)).setText(ticketInfo.getAirportArrival().getCountry());
        ((TextView)view.findViewById(R.id.tvAirportArrivalCity)).setText(ticketInfo.getAirportArrival().getCity());
        ((TextView)view.findViewById(R.id.tvAirportArrivalCode)).setText(ticketInfo.getAirportArrival().getCode());
        ((TextView)view.findViewById(R.id.tvAirportArrivalStatus)).setText(ticketInfo.getAirportArrival().getStatus());
    }
}