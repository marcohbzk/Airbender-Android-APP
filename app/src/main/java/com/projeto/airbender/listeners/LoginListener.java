package com.projeto.airbender.listeners;

import java.util.Map;

public interface LoginListener {
    void onLogin(Map<String, String> map);
    void updateUserData(Map<String, String> map);
}
