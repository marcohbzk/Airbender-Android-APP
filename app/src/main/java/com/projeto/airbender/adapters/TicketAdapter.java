package com.projeto.airbender.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.airbender.R;
import com.projeto.airbender.listeners.TicketDetailListener;
import com.projeto.airbender.listeners.TicketListener;
import com.projeto.airbender.models.BalanceReq;
import com.projeto.airbender.models.Ticket;
import com.projeto.airbender.models.TicketInfo;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private final ArrayList<TicketInfo> localDataSet;
    private final TicketListener ticketListener;

    public void removeItem(int adapterPosition) {
        localDataSet.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public Ticket getTicket(int adapterPosition) {
        return localDataSet.get(adapterPosition).getTicket();
    }

    public TicketAdapter(ArrayList<TicketInfo> dataSet, TicketListener ticketListener) {
        localDataSet = dataSet;
        this.ticketListener = ticketListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ticket_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getAirportDeparture().setText(localDataSet.get(position).getAirportDeparture().getCity());
        viewHolder.getAirportArrival().setText(localDataSet.get(position).getAirportArrival().getCity());
        viewHolder.getDate().setText(localDataSet.get(position).getFlight().getDepartureDate());
        viewHolder.getCard().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketListener.onItemClicked(localDataSet.get(position));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView airportDeparture, airportArrival, date;
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            airportDeparture = view.findViewById(R.id.tvAirportDeparture);
            airportArrival = view.findViewById(R.id.tvAirportArrival);
            date = view.findViewById(R.id.tvDate);
            cardView = view.findViewById(R.id.card_view);
        }

        public TextView getAirportDeparture() {
            return airportDeparture;
        }

        public TextView getAirportArrival() {
            return airportArrival;
        }

        public TextView getDate() {
            return date;
        }

        public CardView getCard() {
            return cardView;
        }
    }
}

