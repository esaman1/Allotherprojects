package postermaster.postermaker.main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import postermaster.postermaker.PosterActivity;
import postermaster.postermaker.R;
import postermaster.postermaker.crop.CropImageView;
import postermaster.postermaker.utility.ImageUtils;

public class CropActivityTwo extends Activity {
    public static Bitmap bitmapImage;
    Bitmap bitmap;
    Animation bottomDown;
    Animation bottomUp;
    CropImageView cropimage;
    TextView custom;
    private InterstitialAd interstitialAd;
    ImageView done;
    RelativeLayout footer;
    OnSetImageSticker getSticker;
    RelativeLayout header;
    TextView headertext;
    TextView ratio1;
    TextView ratio10;
    TextView ratio11;
    TextView ratio12;
    TextView ratio13;
    TextView ratio14;
    TextView ratio15;
    TextView ratio2;
    TextView ratio3;
    TextView ratio4;
    TextView ratio5;
    TextView ratio6;
    TextView ratio7;
    TextView ratio8;
    TextView ratio9;
    RelativeLayout rel;
    float screenHeight;
    float screenWidth;
    TextView square;
    Typeface ttf;
    Typeface ttfHeader;
    String value;

    class C02641 implements OnClickListener {
        C02641() {
        }

        public void onClick(View view) {
            CropActivityTwo.this.finish();
        }
    }

    class C02652 implements OnClickListener {
        C02652() {
        }

        public void onClick(View view) {
            if (interstitialAd.isLoaded()) {

                interstitialAd.show();
            }
            CropActivityTwo cropActivityTwo = CropActivityTwo.this;
            cropActivityTwo.bitmap = cropActivityTwo.cropimage.getCroppedImage();
            if (CropActivityTwo.this.value.equals("image")) {
                CropActivityTwo.bitmapImage = CropActivityTwo.this.bitmap;
                CropActivityTwo.this.setResult(-1);
                CropActivityTwo.this.finish();
                return;
            }
            CropActivityTwo cropActivityTwo2 = CropActivityTwo.this;
            cropActivityTwo2.bitmap = ImageUtils.resizeBitmap(cropActivityTwo2.bitmap, (int) CropActivityTwo.this.screenWidth, (int) CropActivityTwo.this.screenHeight);
            CropActivityTwo.bitmapImage = CropActivityTwo.this.bitmap;
            CropActivityTwo.this.getSticker.ongetSticker();
            CropActivityTwo.this.finish();
        }
    }

    class C02663 implements OnClickListener {
        C02663() {
        }

        public void onClick(View view) {
            CropActivityTwo.this.cropimage.setFixedAspectRatio(false);
        }
    }

    class C02674 implements OnClickListener {
        C02674() {
        }

        public void onClick(View view) {
            CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
            CropActivityTwo.this.cropimage.setAspectRatio(1, 1);
        }
    }

    class C02685 implements OnClickListener {
        C02685() {
        }

        public void onClick(View view) {
            CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
            CropActivityTwo.this.cropimage.setAspectRatio(1, 2);
        }
    }

    class C02696 implements OnClickListener {
        C02696() {
        }

        public void onClick(View view) {
            CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
            CropActivityTwo.this.cropimage.setAspectRatio(2, 1);
        }
    }

    class C02707 implements OnClickListener {
        C02707() {
        }

        public void onClick(View view) {
            CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
            CropActivityTwo.this.cropimage.setAspectRatio(2, 3);
        }
    }

    class C02718 implements OnClickListener {
        C02718() {
        }

        public void onClick(View view) {
            CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
            CropActivityTwo.this.cropimage.setAspectRatio(3, 2);
        }
    }

    class C02729 implements OnClickListener {
        C02729() {
        }

        public void onClick(View view) {
            CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
            CropActivityTwo.this.cropimage.setAspectRatio(3, 4);
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_crop);
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
        this.getSticker = PosterActivity.activity;
        this.header = (RelativeLayout) findViewById(R.id.header);
        this.rel = (RelativeLayout) findViewById(R.id.rel);
        this.footer = (RelativeLayout) findViewById(R.id.footer);
        this.footer.setVisibility(View.INVISIBLE);
        this.cropimage = (CropImageView) findViewById(R.id.cropimage);
        this.done = (ImageView) findViewById(R.id.done);
        this.custom = (TextView) findViewById(R.id.cutom);
        this.square = (TextView) findViewById(R.id.square);
        this.ratio1 = (TextView) findViewById(R.id.ratio1);
        this.ratio2 = (TextView) findViewById(R.id.ratio2);
        this.ratio3 = (TextView) findViewById(R.id.ratio3);
        this.ratio4 = (TextView) findViewById(R.id.ratio4);
        this.ratio5 = (TextView) findViewById(R.id.ratio5);
        this.ratio6 = (TextView) findViewById(R.id.ratio6);
        this.ratio7 = (TextView) findViewById(R.id.ratio7);
        this.ratio8 = (TextView) findViewById(R.id.ratio8);
        this.ratio9 = (TextView) findViewById(R.id.ratio9);
        this.ratio10 = (TextView) findViewById(R.id.ratio10);
        this.ratio11 = (TextView) findViewById(R.id.ratio11);
        this.ratio12 = (TextView) findViewById(R.id.ratio12);
        this.ratio13 = (TextView) findViewById(R.id.ratio13);
        this.ratio14 = (TextView) findViewById(R.id.ratio14);
        this.ratio15 = (TextView) findViewById(R.id.ratio15);
        this.headertext = (TextView) findViewById(R.id.headertext);
        this.bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_up);
        this.bottomDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_down);
        this.footer.setVisibility(View.VISIBLE);
        this.footer.startAnimation(this.bottomUp);
        this.ttf = Constants.getTextTypeface(this);
        this.ttfHeader = Constants.getHeaderTypeface(this);
        this.headertext.setTypeface(this.ttfHeader);
        this.custom.setTypeface(this.ttf, 1);
        this.square.setTypeface(this.ttf, 1);
        if (getIntent().getExtras().getString("value").equals("image")) {
            this.value = "image";
            this.bitmap = SelectImageTwoActivity.bitmap;
        } else if (getIntent().getExtras().getString("value").equals("sticker")) {
            this.value = "sticker";
            this.bitmap = PosterActivity.btmSticker;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = (float) displayMetrics.widthPixels;
        this.screenHeight = (float) (displayMetrics.heightPixels - ImageUtils.dpToPx(this, 105));
        getIntent().getIntExtra("forcal", 102);
        this.cropimage.setImageBitmap(this.bitmap);
        findViewById(R.id.btn_bck).setOnClickListener(new C02641());
        this.done.setOnClickListener(new C02652());
        this.custom.setOnClickListener(new C02663());
        this.square.setOnClickListener(new C02674());
        this.ratio1.setOnClickListener(new C02685());
        this.ratio2.setOnClickListener(new C02696());
        this.ratio3.setOnClickListener(new C02707());
        this.ratio4.setOnClickListener(new C02718());
        this.ratio5.setOnClickListener(new C02729());
        this.ratio6.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(3, 5);
            }
        });
        this.ratio7.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(4, 3);
            }
        });
        this.ratio8.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(4, 5);
            }
        });
        this.ratio9.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(4, 7);
            }
        });
        this.ratio10.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(5, 3);
            }
        });
        this.ratio11.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(5, 4);
            }
        });
        this.ratio12.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(5, 6);
            }
        });
        this.ratio13.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(5, 7);
            }
        });
        this.ratio14.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(9, 16);
            }
        });
        this.ratio15.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(16, 9);
            }
        });
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
