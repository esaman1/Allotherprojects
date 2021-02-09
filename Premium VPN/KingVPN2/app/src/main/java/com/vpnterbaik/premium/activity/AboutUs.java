package com.vpnterbaik.premium.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;

import com.vpnterbaik.premium.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.vpnterbaik.premium.utils.AppData;


public class AboutUs extends AppCompatActivity implements AppData {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    SharedPreferences prefs;
    Boolean isVip = false;
    LinearLayout ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarold);
        toolbar.setTitle("About Us");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        MobileAds.initialize(this,
                String.valueOf(R.string.Admob_app_id));

        prefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        isVip = prefs.getBoolean(IS_VIP, false);

        ad = (LinearLayout) findViewById(R.id.ads);
        if (isVip) {
            ad.setVisibility(LinearLayout.GONE);
        } else {
            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        load_ad();


    }

    public void load_ad() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(String.valueOf(R.string.Admob_intertesial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        if (mInterstitialAd.isLoaded()) {
            if (!isVip) {
                mInterstitialAd.show();
            }
        }
    }
}
