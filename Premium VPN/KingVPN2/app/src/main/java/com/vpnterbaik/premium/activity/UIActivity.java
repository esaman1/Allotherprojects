package com.vpnterbaik.premium.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anchorfree.hydrasdk.HydraSdk;
import com.anchorfree.hydrasdk.api.response.RemainingTraffic;
import com.anchorfree.hydrasdk.callbacks.Callback;
import com.anchorfree.hydrasdk.exceptions.HydraException;
import com.anchorfree.hydrasdk.vpnservice.VPNState;
import com.bumptech.glide.Glide;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;
import com.rahman.dialog.Utilities.SmartDialogBuilder;
import com.vpnterbaik.premium.utils.AppData;
import com.vpnterbaik.premium.utils.Converter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.vpnterbaik.premium.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jonathanfinerty.once.Once;

import static com.vpnterbaik.premium.activity.MainActivity.selectedCountry;
import static com.vpnterbaik.premium.utils.AppData.APP_PREFERENCES;
import static jonathanfinerty.once.Once.beenDone;
import static jonathanfinerty.once.Once.markDone;

public abstract class UIActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AppData {

    protected static final String TAG = MainActivity.class.getSimpleName();

    private InterstitialAd mInterstitialAd;
    private int adCount = 0;
    VPNState state;
    int progressBarValue = 0;
    Handler handler = new Handler();
    private Handler customHandler = new Handler();
    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    public static Menu menuItem;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.tv_connect_message)
    TextView connectedMessage;

    @BindView(R.id.tv_timer)
    TextView timerTextView;

    @BindView(R.id.connect_btn)
    ImageView connectBtnTextView;
    @BindView(R.id.image_gif)
    ImageView imgGif;

    @BindView(R.id.connection_state)
    TextView connectionStateTextView;

    @BindView(R.id.connection_progress)
    ProgressBar connectionProgressBar;

    SharedPreferences prefs;
    Boolean isVip = false;

    LinearLayout ad;
    Date expiryDate;
    Date currentDate;
    Boolean dateLifetime = false;
    Boolean isSubs;

    private static final String SHOW_NEW_SESSION_DIALOG = "NewSessionDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MobileAds.initialize(this, getString(R.string.Admob_app_id));

        prefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        isVip = prefs.getBoolean(IS_VIP, false);
        Log.d("is VIP: ", isVip.toString());

        expiryDate = new Date(prefs.getLong(DATE_END, 0));
        dateLifetime = prefs.getBoolean(DATE_LIFETIME, false);
        isSubs = prefs.getBoolean(IS_SUBS, false);
        // get date now
        currentDate = new Date();

        ad = (LinearLayout) findViewById(R.id.ads);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.Admob_intertesial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        if (isVip) {
            ad.setVisibility(LinearLayout.GONE);
        } else {
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (!dateLifetime) {
            // jika tidak lifetime
            // compare between two date
//            Log.d(" DD lifetime: ", String.valueOf(dateLifetime));

            if (expiryDate.before(currentDate)) {
//                Log.d(" DD expiry: ", String.valueOf(expiryDate.before(currentDate)));
                // expied
                // jika pernah subscribe
                if (isSubs) {
//                    Log.d(" DD is Subs: ", String.valueOf(isSubs));
                    popupUpgradeSubs();
                } else {
//                    Log.d(" DD user baru: ", "true");
                    popupUpgrade();
                }
            }
        }

    }

    public void popupUpgrade() {
        if (!beenDone(Once.THIS_APP_SESSION, SHOW_NEW_SESSION_DIALOG)) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showDialog("Premium Plan", "Get faster speed with no Ads. Upgrade premium plan now", true);
                }
            }, 3000);

            markDone(SHOW_NEW_SESSION_DIALOG);
        }
    }

    public void popupUpgradeSubs() {
        if (!beenDone(Once.THIS_APP_SESSION, SHOW_NEW_SESSION_DIALOG)) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showDialog("Subscription ends", "Upgrade premium plan now", true);
                }
            }, 3000);

            markDone(SHOW_NEW_SESSION_DIALOG);
        }
    }

    public void showDialog(String title, String subTitle, boolean btnCancle) {
        new SmartDialogBuilder(this)
                .setTitle(title)
                .setSubTitle(subTitle)
                .setCancalable(true)
                .setNegativeButtonHide(btnCancle) //hide cancel button
                .setPositiveButton("Upgrade", new SmartDialogClickListener() {
                    @Override
                    public void onClick(SmartDialog smartDialog) {
                        startActivity(new Intent(UIActivity.this, SubsActivity.class));
                        smartDialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new SmartDialogClickListener() {
            @Override
            public void onClick(SmartDialog smartDialog) {
                smartDialog.dismiss();

            }
        }).build().show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        // menu.getItem(0).setIcon(getResources().getIdentifier("drawable/"+"abc",null,getPackageName()));
        menuItem = menu;
        if (selectedCountry != null)
            if (!selectedCountry.equalsIgnoreCase(""))
                //menuItem.findItem(R.id.action_glob).setIcon(R.drawable.de);
                menuItem.findItem(R.id.action_glob).setIcon(this.getResources().getIdentifier(selectedCountry.toLowerCase(), "drawable", this.getPackageName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_glob) {
            startActivity(new Intent(this, Servers.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upgrade) {
            startActivity(new Intent(this, Servers.class));
        } else if (id == R.id.nav_premium) {
            startActivity(new Intent(this, SubsActivity.class));
        } else if (id == R.id.nav_helpus) {
            String mailTo = String.valueOf(R.string.Help_to_improve_Email);
            Intent email_intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", String.valueOf(R.string.Help_to_improve_Email), null));
            email_intent.putExtra(Intent.EXTRA_SUBJECT, "Improve Comments");
            email_intent.putExtra(Intent.EXTRA_TEXT, " ");
            startActivity(Intent.createChooser(email_intent, "Send email"));
        } else if (id == R.id.nav_rate) {
            rateUs();
        } else if (id == R.id.nav_likeus) {

        } else if (id == R.id.nav_share) {

            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "share app");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Free VPN is world best vpn app its Super Faster Free VPN Unlimited Proxy Server lifetime free access https://play.google.com/store/apps/details?id=com.vpnterbaik.premium");
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }

        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, Settings.class));
        } else if (id == R.id.nav_faq) {
            startActivity(new Intent(this, Faq.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private Handler mUIHandler = new Handler(Looper.getMainLooper());
    final Runnable mUIUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            updateUI();
            checkRemainingTraffic();
            mUIHandler.postDelayed(mUIUpdateRunnable, 10000);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        isConnected(new Callback<Boolean>() {
            @Override
            public void success(@NonNull Boolean aBoolean) {
                if (aBoolean) {
                    startUIUpdateTask();
                }
            }

            @Override
            public void failure(@NonNull HydraException e) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopUIUpdateTask();
    }

    protected abstract void loginToVpn();

    @OnClick(R.id.connect_btn)
    public void onConnectBtnClick(View v) {
        isConnected(new Callback<Boolean>() {
            @Override
            public void success(@NonNull Boolean aBoolean) {
                if (aBoolean) {
                    disconnectAlert();
                    //disconnectFromVnp();
                } else {
                    updateUI();
                    connectToVpn();
                }
            }

            @Override
            public void failure(@NonNull HydraException e) {
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    protected abstract void isConnected(Callback<Boolean> callback);

    protected abstract void connectToVpn();

    protected abstract void disconnectFromVnp();

    protected abstract void chooseServer();

    protected abstract void getCurrentServer(Callback<String> callback);

    protected void startUIUpdateTask() {
        stopUIUpdateTask();
        mUIHandler.post(mUIUpdateRunnable);
    }

    protected void stopUIUpdateTask() {
        mUIHandler.removeCallbacks(mUIUpdateRunnable);
        updateUI();
    }

    protected abstract void checkRemainingTraffic();

    protected void updateUI() {
        HydraSdk.getVpnState(new Callback<VPNState>() {
            @Override
            public void success(@NonNull VPNState vpnState) {
                state = vpnState;
                switch (vpnState) {
                    case IDLE: {
                        loadIcon();
                        connectBtnTextView.setEnabled(true);
                        connectionStateTextView.setText(R.string.disconnected);
                        timerTextView.setVisibility(View.GONE);
                        hideConnectProgress();
                        break;
                    }
                    case CONNECTED: {
                        loadIcon();
                        connectBtnTextView.setEnabled(true);
                        connectionStateTextView.setText(R.string.connected);
                        timer();
                        timerTextView.setVisibility(View.VISIBLE);
                        hideConnectProgress();
                        loadAd();
                        break;
                    }
                    case CONNECTING_VPN:
                    case CONNECTING_CREDENTIALS:
                    case CONNECTING_PERMISSIONS: {
                        loadIcon();
                        connectionStateTextView.setText(R.string.connecting);
                        connectBtnTextView.setEnabled(false);
                        timerTextView.setVisibility(View.GONE);
                        showConnectProgress();
                        break;
                    }
                    case PAUSED: {
                        connectBtnTextView.setBackgroundResource(R.drawable.icon_connect);
                        connectionStateTextView.setText(R.string.paused);
                        break;
                    }
                }
            }

            @Override
            public void failure(@NonNull HydraException e) {

            }
        });

        getCurrentServer(new Callback<String>() {
            @Override
            public void success(@NonNull final String currentServer) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            @Override
            public void failure(@NonNull HydraException e) {
               /* currentServerBtn.setText(R.string.optimal_server);
                selectedServerTextView.setText("UNKNOWN");*/
            }
        });
    }

    protected void updateTrafficStats(long outBytes, long inBytes) {
        String outString = Converter.humanReadableByteCountOld(outBytes, false);
        String inString = Converter.humanReadableByteCountOld(inBytes, false);

        //trafficStats.setText(getResources().getString(R.string.traffic_stats, outString, inString));
    }

    protected void updateRemainingTraffic(RemainingTraffic remainingTrafficResponse) {
        if (remainingTrafficResponse.isUnlimited()) {
            //trafficLimitTextView.setText("UNLIMITED available");
        } else {
            String trafficUsed = Converter.megabyteCount(remainingTrafficResponse.getTrafficUsed()) + "Mb";
            String trafficLimit = Converter.megabyteCount(remainingTrafficResponse.getTrafficLimit()) + "Mb";

            //trafficLimitTextView.setText(getResources().getString(R.string.traffic_limit, trafficUsed, trafficLimit));
        }
    }

    protected void showConnectProgress() {
        // connectionProgressBar.setProgress(10);
        connectionProgressBar.setVisibility(View.VISIBLE);
        //connectionStateTextView.setVisibility(View.GONE);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                while (state == VPNState.CONNECTING_VPN || state == VPNState.CONNECTING_CREDENTIALS) {
                    progressBarValue++;

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            connectionProgressBar.setProgress(progressBarValue);
                            //  ShowText.setText(progressBarValue +"/"+Progressbar.getMax());

                        }
                    });
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    protected void hideConnectProgress() {
        connectionProgressBar.setVisibility(View.GONE);
        connectionStateTextView.setVisibility(View.VISIBLE);
    }

    protected void showMessage(String msg) {
        Toast.makeText(UIActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void rateUs() {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flag to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }

    protected void timer() {
        if (adCount == 0) {
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
        }
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            int hrs = mins / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerTextView.setText(String.format("%02d", hrs) + ":"
                    + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }

    };

    protected void loadAd() {

        if (adCount == 0) {
            if (mInterstitialAd.isLoaded()) {
                if (!isVip) {
                    mInterstitialAd.show();
                }
            }
        }
        adCount++;
    }

    protected void loadIcon() {
        if (state == VPNState.IDLE) {
            Glide.with(this).load(R.drawable.icon_connect).into(connectBtnTextView);
            Glide.with(this).load(R.drawable.ic_hare_connect).into(imgGif);
            connectedMessage.setVisibility(View.INVISIBLE);
        } else if (state == VPNState.CONNECTING_VPN || state == VPNState.CONNECTING_CREDENTIALS) {
            imgGif.setVisibility(View.VISIBLE);
            Glide.with(this).asGif().load(R.drawable.rabbit).into(imgGif);
            connectBtnTextView.setVisibility(View.INVISIBLE);
            connectedMessage.setVisibility(View.INVISIBLE);
        } else if (state == VPNState.CONNECTED) {
            Glide.with(this).load(R.drawable.icon_disconnect).into(connectBtnTextView);
            Glide.with(this).load(R.drawable.ic_hare_connected).into(imgGif);
            connectBtnTextView.setVisibility(View.VISIBLE);
            connectedMessage.setVisibility(View.VISIBLE);
        }
    }

    protected void disconnectAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to disconnet?");
        builder.setPositiveButton("Disconnect",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        adCount = 0;
                        disconnectFromVnp();
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }


}
