package postermaster.postermaker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;

import postermaster.postermaker.creation.MyCreationActivity;

public class ShareActivity extends AppCompatActivity implements OnClickListener {
    static int id;
    private String TAG = "Shareactiity";
    private TextView album_btn;
    private ImageView back;
    Editor editor;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    /* access modifiers changed from: private */
    private ImageView ivFacebook;
    private ImageView ivFinalImage;
    private ImageView ivHike;
    private ImageView ivInstagram;
    private ImageView ivShareMore;
    private ImageView ivWhatsApp;
    private LinearLayout llForMyCreation;
    String oldpath;
    private Uri phototUri = null;
    SharedPreferences preferences;
    /* access modifiers changed from: private */
    public ProgressBar progressBar;
    /* access modifiers changed from: private */
    public int progressStatus = 0;
    /* access modifiers changed from: private */
    public FrameLayout saveimageframe;
    private String strSavedImage;
    private TextView tvFinalImagePath;
    private TextView txtSave;
    private TextView tvName;
    private ImageView ivHome, ivAlbum;
    private InterstitialAd interstitialAd;
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_share);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor = this.preferences.edit();
        this.oldpath = getIntent().getStringExtra("uri");
        this.phototUri = Uri.parse(this.oldpath);
        this.strSavedImage = this.oldpath;

        tvName = findViewById(R.id.tvName);
        tvName.setTypeface(Typeface.createFromAsset(getAssets(), "font6.ttf"));
        ivHome = findViewById(R.id.ivHome);
        ivAlbum = findViewById(R.id.ivAlbum);
        ivHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ivAlbum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ShareActivity.this, MyCreationActivity.class), 1000);
            }
        });
        bindView();
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

    private void bindView() {
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.saveimageframe = (FrameLayout) findViewById(R.id.saveimageframe);
        setProgressBar();
        this.tvFinalImagePath = (TextView) findViewById(R.id.ic_path);
        this.tvFinalImagePath.setText(this.strSavedImage);
        this.ivFinalImage = (ImageView) findViewById(R.id.ivFinalImage);
        this.ivFinalImage.setImageURI(this.phototUri);
        this.ivFinalImage.setOnClickListener(this);
        this.ivWhatsApp = (ImageView) findViewById(R.id.iv_whatsapp);
        this.ivWhatsApp.setOnClickListener(this);
        this.ivFacebook = (ImageView) findViewById(R.id.iv_facebook);
        this.ivFacebook.setOnClickListener(this);
        this.ivInstagram = (ImageView) findViewById(R.id.iv_instagram);
        this.ivInstagram.setOnClickListener(this);
        this.ivHike = (ImageView) findViewById(R.id.iv_Hike);
        this.ivHike.setOnClickListener(this);
        this.ivShareMore = (ImageView) findViewById(R.id.iv_Share_More);
        this.ivShareMore.setOnClickListener(this);
        this.album_btn = (TextView) findViewById(R.id.album_btn);
        this.album_btn.setOnClickListener(this);
    }

    public void onClick(View view) {
        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileprovider", new File(oldpath));
        switch (view.getId()) {
            case R.id.album_btn /*2131296303*/:
                if (interstitialAd.isLoaded()) {

                    interstitialAd.show();
                }
                startActivityForResult(new Intent(this, MyCreationActivity.class), 1000);
                return;
            case R.id.iv_Hike /*2131296527*/:
                if (interstitialAd.isLoaded()) {

                    interstitialAd.show();
                }
                try {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setPackage("com.bsb.hike");
                    intent.setType("image/*");
                    StringBuilder sb = new StringBuilder();
                    sb.append(getResources().getString(R.string.app_name));
                    sb.append(" Created By : ");
                    sb.append("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    Log.e("qqqq", "phototUri" + phototUri);
                    startActivity(intent);
                    return;
                } catch (Exception unused) {
                    Toast.makeText(this, "Hike doesn't installed", Toast.LENGTH_LONG).show();
                    return;
                }
            case R.id.iv_Share_More /*2131296528*/:
                if (interstitialAd.isLoaded()) {

                    interstitialAd.show();
                }
                Intent intent2 = new Intent("android.intent.action.SEND");
                intent2.setType("image/*");
                StringBuilder sb2 = new StringBuilder();
                sb2.append(getResources().getString(R.string.app_name));
                sb2.append(" Created By : ");
                sb2.append("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                intent2.putExtra(Intent.EXTRA_TEXT, sb2.toString());
                intent2.putExtra(Intent.EXTRA_STREAM, photoURI);
                Log.e("qqqq", "phototUri" + phototUri);
                startActivity(Intent.createChooser(intent2, "Share Image using"));
                return;
            case R.id.iv_facebook /*2131296532*/:
                if (interstitialAd.isLoaded()) {

                    interstitialAd.show();
                }
                try {
                    Intent intent3 = new Intent();
                    intent3.setAction("android.intent.action.SEND");
                    intent3.setPackage("com.facebook.katana");
                    intent3.setType("image/*");
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(getResources().getString(R.string.app_name));
                    sb3.append(" Created By : ");
                    sb3.append("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    intent3.putExtra(Intent.EXTRA_TEXT, sb3.toString());
                    intent3.putExtra(Intent.EXTRA_STREAM, photoURI);
                    Log.e("qqqq", "phototUri" + phototUri);
                    startActivity(intent3);
                    return;
                } catch (Exception unused2) {
                    Toast.makeText(this, "Facebook doesn't installed", Toast.LENGTH_LONG).show();
                    return;
                }
            case R.id.iv_instagram /*2131296535*/:
                if (interstitialAd.isLoaded()) {

                    interstitialAd.show();
                }
                try {
                    Intent intent4 = new Intent("android.intent.action.SEND");
                    intent4.setPackage("com.instagram.android");
                    intent4.setType("image/*");
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(getResources().getString(R.string.app_name));
                    sb4.append(" Created By : ");
                    sb4.append("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    intent4.putExtra(Intent.EXTRA_TEXT, sb4.toString());
                    intent4.putExtra(Intent.EXTRA_STREAM, photoURI);
                    Log.e("qqqq", "phototUri" + phototUri);
                    startActivity(intent4);
                    return;
                } catch (Exception unused3) {
                    Toast.makeText(this, "Instagram doesn't installed", Toast.LENGTH_LONG).show();
                    return;
                }
            case R.id.iv_whatsapp /*2131296537*/:
                if (interstitialAd.isLoaded()) {

                    interstitialAd.show();
                }
                try {
                    Intent intent5 = new Intent();
                    intent5.setAction("android.intent.action.SEND");
                    intent5.setPackage("com.whatsapp");
                    intent5.setType("image/*");
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(getResources().getString(R.string.app_name));
                    sb5.append(" Created By : ");
                    sb5.append("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    intent5.putExtra(Intent.EXTRA_TEXT, sb5.toString());
                    intent5.putExtra(Intent.EXTRA_STREAM, photoURI);
                    Log.e("qqqq", "phototUri" + phototUri);
                    startActivity(intent5);
                    return;
                } catch (Exception unused3) {
                    Toast.makeText(this, "WhatsApp doesn't installed", Toast.LENGTH_LONG).show();
                    return;
                }
            default:
                return;
        }
    }

    private void setProgressBar() {
        new Thread(new Runnable() {
            public void run() {
                while (ShareActivity.this.progressStatus < 100) {
                    ShareActivity shareActivity = ShareActivity.this;
                    shareActivity.progressStatus = shareActivity.progressStatus + 1;
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ShareActivity.this.handler.post(new Runnable() {
                        public void run() {
                            ShareActivity.this.progressBar.setProgress(ShareActivity.this.progressStatus);
                            if (ShareActivity.this.progressStatus == 100) {
                                ShareActivity.this.saveimageframe.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {

        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}