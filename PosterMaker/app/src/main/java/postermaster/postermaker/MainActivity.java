package postermaster.postermaker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.gummybutton.GummyButton;
import com.gummybutton.GummyButton.OnClickListener;

import postermaster.postermaker.creation.MyCreationActivity;

public class MainActivity extends AppCompatActivity {

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static int height1;
    public static float ratio;
    public static int width;
    private GummyButton cv_create_quote;
    private boolean isDataStored = false;
    private GummyButton lay_photos;
    private ImageView llimage1;
    /* access modifiers changed from: private */
    public LinearLayout llstart;
    /* access modifiers changed from: private */
    public Animation slideUpAnimation;
    private TextView tvApp;
    private InterstitialAd interstitialAd;
    private AdView mAdView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        getWindow().setFlags(1024, 1024);
        bind();

        tvApp = findViewById(R.id.tvApp);
        checkPermision();
//        tvApp.setTypeface(Typeface.createFromAsset(getAssets(), "font6.ttf"));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(this.getString(R.string.interstitial));
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });
    }

    public boolean checkPermision() {
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {
            return true;
        }
        return false;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ALL) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != 0) {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }
        }
    }

    private void bind() {
        this.llimage1 = (ImageView) findViewById(R.id.llimage1);
        this.llstart = (LinearLayout) findViewById(R.id.llstart);
        this.cv_create_quote = (GummyButton) findViewById(R.id.cv_create_quote);
        this.cv_create_quote.setAction(new OnClickListener() {
            public void onClick(MotionEvent motionEvent) {
                if (interstitialAd.isLoaded()) {

                    interstitialAd.show();
                } else {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.startActivityForResult(new Intent(mainActivity, TemplateActivity.class), 2000);
                }
                interstitialAd.setAdListener(new AdListener() {

                    @Override
                    public void onAdClosed() {
                        interstitialAd.loadAd(new AdRequest.Builder().build());
                        MainActivity mainActivity = MainActivity.this;
                        mainActivity.startActivityForResult(new Intent(mainActivity, TemplateActivity.class), 2000);

                    }

                });
//                MainActivity mainActivity = MainActivity.this;
//                mainActivity.startActivityForResult(new Intent(mainActivity, TemplateActivity.class), 2000);
//
            }
        });
        this.lay_photos = (GummyButton) findViewById(R.id.lay_photos);
        this.lay_photos.setAction(new OnClickListener() {
            public void onClick(MotionEvent motionEvent) {
                if (interstitialAd.isLoaded()) {

                    interstitialAd.show();
                } else {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.startActivityForResult(new Intent(mainActivity, MyCreationActivity.class), 1000);

                }
                interstitialAd.setAdListener(new AdListener() {

                    @Override
                    public void onAdClosed() {
                        interstitialAd.loadAd(new AdRequest.Builder().build());
                        MainActivity mainActivity = MainActivity.this;
                        mainActivity.startActivityForResult(new Intent(mainActivity, MyCreationActivity.class), 1000);


                    }

                });
                       }
        });
    }

//    /* access modifiers changed from: protected */
//    public void onResume() {
//        super.onResume();
////        new Timer().schedule(new TimerTask() {
////            public void run() {
////                MainActivity.this.runOnUiThread(new Runnable() {
////                    public void run() {
//        AnimationUtils.loadAnimation(MainActivity.this.getApplicationContext(), R.anim.slide_down_animation);
//        MainActivity.this.slideUpAnimation = AnimationUtils.loadAnimation(MainActivity.this.getApplicationContext(), R.anim.slide_up_animation);
//        MainActivity.this.llstart.setAnimation(MainActivity.this.slideUpAnimation);
//        MainActivity.this.llstart.setVisibility(View.VISIBLE);
////                    }
////                });
////            }
////        }, 5000);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {

        } else if (requestCode == 2000 && resultCode == RESULT_OK) {

        }
    }
}
