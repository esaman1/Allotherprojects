package postermaster.postermaker.creation;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.ArrayList;

import postermaster.postermaker.R;
import postermaster.postermaker.main.Constants;

public class MyCreationActivity extends AppCompatActivity {
    public static ArrayList<String> IMAGEALLARY = new ArrayList<>();
    public static int pos;
    private ImageView Iv_back_creation;
    private LinearLayout adsContainerLayout;
    GallaryAdapter gallaryAdapter;
    private GridView lv_my_creation;
    private Typeface ttfHeader;
    private AdView mAdView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_my_creation);

        this.lv_my_creation = (GridView) findViewById(R.id.lv_my_creation);
        this.ttfHeader = Constants.getHeaderTypeface(this);
        ((TextView) findViewById(R.id.tvMC)).setTypeface(this.ttfHeader);
        this.gallaryAdapter = new GallaryAdapter(this, IMAGEALLARY);
        IMAGEALLARY.clear();
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append("/DCIM/Poster Maker/");
        listAllImages(new File(sb.toString()));
        this.lv_my_creation.setAdapter(this.gallaryAdapter);
        this.Iv_back_creation = (ImageView) findViewById(R.id.Iv_back_creation);
        this.Iv_back_creation.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    private void listAllImages(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (int length = listFiles.length - 1; length >= 0; length--) {
                String file2 = listFiles[length].toString();
                File file3 = new File(file2);
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(file3.length());
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("");
                sb3.append(file3.length());
                Log.d(sb2, sb3.toString());
                if (file3.length() <= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
                    Log.e("Invalid Image", "Delete Image");
                } else if (file3.toString().contains(".jpg") || file3.toString().contains(".png") || file3.toString().contains(".jpeg")) {
                    IMAGEALLARY.add(file2);
                }
                System.out.println(file2);
            }
            return;
        }
        System.out.println("Empty Folder");
    }
}