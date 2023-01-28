package com.projeto.airbender.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.projeto.airbender.R;
import com.projeto.airbender.adapters.BalanceReqAdapter;
import com.projeto.airbender.adapters.TicketViewAdapter;
import com.projeto.airbender.models.BalanceReq;
import com.projeto.airbender.models.DepthPageTransformer;

import java.util.ArrayList;

public class TicketFragment extends Fragment {

    private TicketViewAdapter ticketViewAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    public TicketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        ticketViewAdapter = new TicketViewAdapter(this);

        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        String[] tabTitles = new String[]{"Upcoming", "Pending", "Past"};
        viewPager.setAdapter(ticketViewAdapter);
        viewPager.setPageTransformer(new DepthPageTransformer());
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();
        return view;
    }
}

