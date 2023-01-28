package com.projeto.airbender.listeners;

import com.projeto.airbender.models.BalanceReq;

import java.util.ArrayList;

public interface BalanceReqListener {
    void onRefreshBalanceReqList(ArrayList<BalanceReq> balanceReqs);
}
