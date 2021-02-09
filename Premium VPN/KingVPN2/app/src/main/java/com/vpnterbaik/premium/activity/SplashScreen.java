package com.vpnterbaik.premium.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.vpnterbaik.premium.utils.AppData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SplashScreen extends AppCompatActivity implements AppData{

    SharedPreferences prefs;
    BillingProcessor bp;

    Date expiryDate;
    Date currentDate;
    Boolean dateLifetime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        expiryDate = new Date(prefs.getLong(DATE_END, 0));
        dateLifetime = prefs.getBoolean(DATE_LIFETIME, false);

        currentDate = new Date();

        if (!dateLifetime){
            // jika tidak lifetime
            // compare between two date
            if (expiryDate.before(currentDate)) {
                // expied
                prefs.edit().putBoolean(IS_VIP, false).apply();
//                Log.d(" expiry: ", "change VIP to false");
            }
        }

        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

}
