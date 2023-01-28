package com.projeto.airbender.listeners;

import com.projeto.airbender.models.BalanceReq;
import com.projeto.airbender.models.Ticket;
import com.projeto.airbender.models.TicketInfo;

import java.util.ArrayList;

public interface TicketListener {
    void onRefreshTicketList(ArrayList<TicketInfo> tickets);
    void onItemClicked(TicketInfo ticket);
}
