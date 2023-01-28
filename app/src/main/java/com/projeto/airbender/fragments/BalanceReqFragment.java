package com.projeto.airbender.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.projeto.airbender.R;
import com.projeto.airbender.adapters.BalanceReqAdapter;
import com.projeto.airbender.listeners.BalanceReqListener;
import com.projeto.airbender.models.BalanceReq;
import com.projeto.airbender.models.SingletonAirbender;
import com.projeto.airbender.utils.JsonParser;

import java.util.ArrayList;

public class BalanceReqFragment extends Fragment implements BalanceReqListener {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddBalanceReq;
    private SwipeRefreshLayout pullToRefresh;
    private BalanceReqAdapter balanceReqAdapter;

    public BalanceReqFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance_req, container, false);

        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        recyclerView = view.findViewById(R.id.recyclerView);
        fabAddBalanceReq = view.findViewById(R.id.fabAddBalanceReq);

        SingletonAirbender.getInstance(getContext()).setBalanceReqListener(this);

        balanceReqAdapter = new BalanceReqAdapter(new ArrayList<BalanceReq>());

        recyclerView.setAdapter(balanceReqAdapter);

        SingletonAirbender.getInstance(getContext()).getAllBalanceReqsDB(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("This action cannot be undone.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform the action
                        SingletonAirbender.getInstance(getContext()).deleteBalanceReq(getContext(), balanceReqAdapter.getBalanceReq(viewHolder.getAdapterPosition()));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SingletonAirbender.getInstance(getContext()).getAllBalanceReqsDB(getContext());
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SingletonAirbender.getInstance(getContext()).requestBalanceReqsAPI(getContext());
                pullToRefresh.setRefreshing(false);
            }
        });

        fabAddBalanceReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!JsonParser.isConnectionInternet(getContext())) {
                    Snackbar.make(view, "No internet connection", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                final Dialog dialog = new Dialog(getContext());

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_ask_balance);

                final EditText etAmount = dialog.findViewById(R.id.etAmount);
                final Button btnOK = dialog.findViewById(R.id.btnOK);

                btnOK.setOnClickListener((v) -> {
                    if (etAmount.getText().toString().isEmpty()) {
                        etAmount.setError("Amount is required");
                        etAmount.requestFocus();
                        return;
                    }

                    int amount = Integer.parseInt(etAmount.getText().toString());

                    SingletonAirbender.getInstance(getContext()).addBalanceReq(amount, getContext());

                    dialog.dismiss();
                });

                dialog.show();
            }
        });

        return view;
    }

    @Override
    public void onRefreshBalanceReqList(ArrayList<BalanceReq> balanceReqs) {
        balanceReqAdapter = new BalanceReqAdapter(balanceReqs);
        recyclerView.setAdapter(balanceReqAdapter);
    }
}