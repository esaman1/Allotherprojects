package com.vpnterbaik.premium.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.vpnterbaik.premium.Fragments.FragmentFree;
import com.vpnterbaik.premium.Fragments.FragmentVip;
import com.vpnterbaik.premium.adapter.TabAdapter;
import com.vpnterbaik.premium.R;
import com.vpnterbaik.premium.utils.AppData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Servers extends AppCompatActivity implements AppData {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private AdRequest adRequest;

    SharedPreferences prefs;
    Boolean isVip = false;

    LinearLayout ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servers);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarold);
        toolbar.setTitle("Servers");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MobileAds.initialize(this, String.valueOf(R.string.Admob_app_id));

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentVip(), "Vip Server");
        adapter.addFragment(new FragmentFree(), "Free Server");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

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

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.Admob_intertesial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {
                    if (!isVip) {
                        mInterstitialAd.show();
                    }
                }
            }
        });

    }
}
