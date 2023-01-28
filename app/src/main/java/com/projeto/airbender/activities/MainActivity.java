package com.projeto.airbender.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.projeto.airbender.R;
import com.projeto.airbender.fragments.BalanceReqFragment;
import com.projeto.airbender.fragments.GenerateQRCodeFragment;
import com.projeto.airbender.fragments.HomeFragment;
import com.projeto.airbender.fragments.ProfileFragment;
import com.projeto.airbender.fragments.TicketFragment;
import com.projeto.airbender.models.SingletonAirbender;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showSnackIfOffline();

        SharedPreferences settings = getApplicationContext().getSharedPreferences("settings", 0);
        if(settings.getBoolean("FIRSTLOGIN", true)) {
            SingletonAirbender.getInstance(getApplicationContext()).requestTicketsAPI(getApplicationContext(), 0);
            SingletonAirbender.getInstance(getApplicationContext()).requestBalanceReqsAPI(getApplicationContext());
            SingletonAirbender.getInstance(getApplicationContext()).requestAirportsAPI(getApplicationContext());
            settings.edit().putBoolean("FIRSTLOGIN", false).apply();
        }


        try{
            MqttClient client = new MqttClient("tcp://" + getServer() + ":1883", getUserID() + "", null);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("android");
            options.setPassword("a".toCharArray());
            options.setCleanSession(false);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String messageBody = new String(message.getPayload());
                    System.out.println("Message arrived: " + messageBody);
                    //mosquittoListener.onMessage(topic, messageBody);
                    if(messageBody.equals("request")){
                        SingletonAirbender.getInstance(getApplicationContext()).requestBalanceReqsAPI(getApplicationContext());
                    }
                    if(messageBody.equals("ticket")) {
                        replaceFragment(new TicketFragment(), false);
                        SingletonAirbender.getInstance(getApplicationContext()).requestTicketsAPI(getApplicationContext(), 0);
                        SingletonAirbender.getInstance(getApplicationContext()).requestTicketsAPI(getApplicationContext(), 1);
                        SingletonAirbender.getInstance(getApplicationContext()).requestTicketsAPI(getApplicationContext(), 2);
                    }
                    if(topic.equals("airport")) {
                        SingletonAirbender.getInstance(getApplicationContext()).requestAirportsAPI(getApplicationContext());
                    }

                    if(topic.equals("config")) {
                        //SingletonAirbender.getInstance(getApplicationContext()).requestConfigAPI(getApplicationContext(), 1);
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("Delivery complete");
                }
            });

            client.connect(options);
            client.subscribe( getUserID() + "", 1);
            client.subscribe("airport", 1);
            client.subscribe("config", 1);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        replaceFragment(new HomeFragment(), false);
        bottomNav = findViewById(R.id.bottomNavigationView);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navHome:
                    replaceFragment(new HomeFragment(), false);
                    break;
                case R.id.navTickets:
                    replaceFragment(new TicketFragment(), false);
                    break;
                case R.id.navBalanceReq:
                    replaceFragment(new BalanceReqFragment(), false);
                    break;
                case R.id.navProfile:
                    replaceFragment(new ProfileFragment(), false);
                    break;
            }
            return true;
        });
    }


    public int getUserID() {
        SharedPreferences user = getApplicationContext().getSharedPreferences("user_data", 0);
        int id = user.getInt("ID", 0);
        return id;
    }

    public String getServer() {
        SharedPreferences user = getApplicationContext().getSharedPreferences("settings", 0);
        return user.getString("SERVER", "");
    }
    public void replaceFragment(Fragment newFragment, boolean keepBack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        );
        fragmentTransaction.replace(R.id.frameLayout, newFragment);

        if (keepBack)
            fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    private void showSnackIfOffline() {
        final boolean online = isOnline();
        runOnUiThread(new TimerTask() { //must run on main thread to update UI (show Snackbar), can be used only in Activity (FragmentActivity, AppCompatActivity...)
            @Override
            public void run() {
                if (!online)
                    Snackbar.make(findViewById(android.R.id.content), "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isOnline() {
        SharedPreferences sharedInfoUser = getSharedPreferences("settings", MODE_PRIVATE);
        try {
            return Runtime.getRuntime().exec("/system/bin/ping -c 1 " + sharedInfoUser.getString("SERVER", "10.0.2.2")).waitFor() == 0; //  "8.8.8.8" is the server to ping
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
