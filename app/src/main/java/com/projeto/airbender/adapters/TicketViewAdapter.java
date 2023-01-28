package com.projeto.airbender.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.projeto.airbender.fragments.TicketsViewFragment;

public class TicketViewAdapter extends FragmentStateAdapter {
    public TicketViewAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new TicketsViewFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.clear();
        args.putInt(TicketsViewFragment.ARG_OBJECT, position);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
