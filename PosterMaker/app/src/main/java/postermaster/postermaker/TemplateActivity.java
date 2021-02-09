package postermaster.postermaker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.lang.reflect.Field;

import postermaster.postermaker.main.Constants;
import postermaster.postermaker.main.CropActivity;
import postermaster.postermaker.main.FragmentBackImage;
import postermaster.postermaker.main.FragmentBackgrounds;
import postermaster.postermaker.main.FragmentColor;
import postermaster.postermaker.main.FragmentImage;
import postermaster.postermaker.main.FragmentTexture;
import postermaster.postermaker.main.OnGetImageOnTouch;
import postermaster.postermaker.utility.ImageUtils;
import postermaster.postermaker.utility.SlowScroller;
import postermaster.postermaker.utility.ZoomOutSlideTransformer;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class TemplateActivity extends AppCompatActivity implements MaterialTabListener, OnClickListener, OnGetImageOnTouch {
    private static final int SELECT_PICTURE_FROM_CAMERA = 9062;
    private static final int SELECT_PICTURE_FROM_GALLERY = 9072;
    public static Bitmap bitmap;
    private final int OPEN_CUSTOM_ACITIVITY = 4;
    private final int OPEN_UPDATE_ACITIVITY = 1123;
    private ViewPagerAdapter adapter;
    /* access modifiers changed from: private */
    public Animation animSlideDown;
    private Animation animSlideUp;
    private File f21f;
    private InterstitialAd interstitialAd;
    FragmentBackgrounds fragmentbackgund = null;
    FragmentColor fragmentcolor = null;
    FragmentImage fragmentimage = null;
    FragmentTexture fragmenttexture = null;
    String hex12;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    RelativeLayout image_container;
    private RelativeLayout lay_crop;
    private ViewPager pager;
    String position;
    String profile;
    float screenHeight;
    float screenWidth;
    /* access modifiers changed from: private */
    public MaterialTabHost tabHost;

    /* access modifiers changed from: private */
    public Typeface ttf;
    private Typeface ttfHeader;

    class C03412 implements DialogInterface.OnClickListener {
        C03412() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    class C06961 extends SimpleOnPageChangeListener {
        C06961() {
        }

        public void onPageSelected(int i) {
            TemplateActivity.this.tabHost.setSelectedNavigationItem(i);
            if (TemplateActivity.this.image_container.getVisibility() == View.VISIBLE) {
                TemplateActivity.this.image_container.startAnimation(TemplateActivity.this.animSlideDown);
                TemplateActivity.this.image_container.setVisibility(View.GONE);
            }
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public int getCount() {
            return 4;
        }

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            if (i == 0) {
                if (interstitialAd.isLoaded()) {

                    interstitialAd.show();
                }
                TemplateActivity.this.fragmentbackgund = new FragmentBackgrounds();
                return TemplateActivity.this.fragmentbackgund;
            } else if (i == 1) {
                if (interstitialAd.isLoaded()) {

                    interstitialAd.show();
                }
                TemplateActivity.this.fragmenttexture = new FragmentTexture();
                return TemplateActivity.this.fragmenttexture;
            } else if (i == 2) {
                if (interstitialAd.isLoaded()) {

                    interstitialAd.show();
                }
                TemplateActivity.this.fragmentimage = new FragmentImage();
                return TemplateActivity.this.fragmentimage;
            } else if (i != 3) {
                return null;
            } else {
                TemplateActivity.this.fragmentcolor = new FragmentColor();
                return TemplateActivity.this.fragmentcolor;
            }
        }

        public CharSequence getPageTitle(int i) {
            String str = "";
            if (i == 0) {
                TemplateActivity templateActivity = TemplateActivity.this;
                return ImageUtils.getSpannableString(templateActivity, templateActivity.ttf, R.string.txt_defalut);
            } else if (i == 1) {
                TemplateActivity templateActivity2 = TemplateActivity.this;
                return ImageUtils.getSpannableString(templateActivity2, templateActivity2.ttf, R.string.txt_backgrounds);
            } else if (i == 2) {
                TemplateActivity templateActivity3 = TemplateActivity.this;
                return ImageUtils.getSpannableString(templateActivity3, templateActivity3.ttf, R.string.txt_texture);
            } else if (i == 3) {
                TemplateActivity templateActivity4 = TemplateActivity.this;
                return ImageUtils.getSpannableString(templateActivity4, templateActivity4.ttf, R.string.txt_image);
            } else if (i != 4) {
                return str;
            } else {
                TemplateActivity templateActivity5 = TemplateActivity.this;
                return ImageUtils.getSpannableString(templateActivity5, templateActivity5.ttf, R.string.txt_color);
            }
        }
    }

    private float getnewHeight(int i, int i2, float f, float f2) {
        return (((float) i2) * f) / ((float) i);
    }

    private float getnewWidth(int i, int i2, float f, float f2) {
        return (((float) i) * f2) / ((float) i2);
    }

    public void onTabReselected(MaterialTab materialTab) {
    }

    public void onTabUnselected(MaterialTab materialTab) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_template);
        this.ttf = Constants.getTextTypeface(this);
        this.ttfHeader = Constants.getHeaderTypeface(this);
        this.tabHost = (MaterialTabHost) findViewById(R.id.tabHost);
        tabHost.setTextColor(R.color.white);
        this.pager = (ViewPager) findViewById(R.id.pager);
        MaterialTab materialTab = new MaterialTab(this, false);
        MaterialTab materialTab2 = new MaterialTab(this, false);
        MaterialTab materialTab3 = new MaterialTab(this, false);
        MaterialTab materialTab4 = new MaterialTab(this, false);
        this.tabHost.addTab(materialTab.setText(ImageUtils.getSpannableString(this, this.ttf, R.string.txt_backgrounds)).setTabListener(this));
        this.tabHost.addTab(materialTab2.setText(ImageUtils.getSpannableString(this, this.ttf, R.string.txt_texture)).setTabListener(this));
        this.tabHost.addTab(materialTab3.setText(ImageUtils.getSpannableString(this, this.ttf, R.string.txt_image)).setTabListener(this));
        this.tabHost.addTab(materialTab4.setText(ImageUtils.getSpannableString(this, this.ttf, R.string.txt_color)).setTabListener(this));
        initUI();
        this.animSlideUp = Constants.getAnimUp(this);
        this.animSlideDown = Constants.getAnimDown(this);
        this.image_container = (RelativeLayout) findViewById(R.id.image_container);
        this.lay_crop = (RelativeLayout) findViewById(R.id.lay_crop);
        this.image1 = (ImageView) findViewById(R.id.image1);
        this.image2 = (ImageView) findViewById(R.id.image2);
        this.image3 = (ImageView) findViewById(R.id.image3);
        this.image4 = (ImageView) findViewById(R.id.image4);
        this.image5 = (ImageView) findViewById(R.id.image5);
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
        this.image1.setOnClickListener(this);
        this.image2.setOnClickListener(this);
        this.image3.setOnClickListener(this);
        this.image4.setOnClickListener(this);
        this.image5.setOnClickListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = (float) displayMetrics.widthPixels;
        this.screenHeight = (float) displayMetrics.heightPixels;
        this.f21f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        ((TextView) findViewById(R.id.txt_appname)).setTypeface(this.ttfHeader);
    }

    private void initUI() {
        this.adapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.adapter.notifyDataSetChanged();
        this.pager.setAdapter(this.adapter);
        this.pager.setOnPageChangeListener(new C06961());
        this.pager.setPageTransformer(true, new ZoomOutSlideTransformer());
        changePagerScroller();
    }

    public void onTabSelected(MaterialTab materialTab) {
        ViewPager viewPager = this.pager;
        if (viewPager != null) {
            viewPager.setCurrentItem(materialTab.getPosition(), true);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.btn_bck) {
            switch (id) {
                case R.id.image1 /*2131296501*/:
                    if (interstitialAd.isLoaded()) {

                        interstitialAd.show();
                    }
                    callActivity("1:1");
                    return;
                case R.id.image2 /*2131296502*/:
                    callActivity("16:9");
                    return;
                case R.id.image3 /*2131296503*/:
                    if (interstitialAd.isLoaded()) {

                        interstitialAd.show();
                    }
                    callActivity("9:16");
                    return;
                case R.id.image4 /*2131296504*/:
                    callActivity("4:3");
                    return;
                case R.id.image5 /*2131296505*/:
                    callActivity("3:4");
                    return;
                default:
                    return;
            }
        } else {
            onBackPressed();
        }
    }

    public void ongetPosition(int i, String str, String str2) {
        Bitmap bitmap2;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(i);
        this.position = sb.toString();
        this.profile = str;
        this.hex12 = str2;
        this.image_container.startAnimation(this.animSlideUp);
        this.image_container.setVisibility(View.VISIBLE);
        if (str.equals("Background")) {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = 10;
            options.inJustDecodeBounds = false;
            bitmap2 = BitmapFactory.decodeResource(getResources(), Constants.Imageid0[i], options);
        } else if (str.equals("Texture")) {
            bitmap2 = BitmapFactory.decodeResource(getResources(), Constants.Imageid1[i]);
        } else if (str.equals("Color")) {
            bitmap2 = Bitmap.createBitmap(300, 300, Config.ARGB_8888);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("#");
            sb2.append(str2);
            bitmap2.eraseColor(Color.parseColor(sb2.toString()));
        } else {
            bitmap2 = (!str.equals("Temp_Path") || FragmentBackImage.thumbnails.size() == 0) ? null : (Bitmap) FragmentBackImage.thumbnails.get(i);
        }
        for (int i2 = 0; i2 < 5; i2++) {
            if (i2 == 0) {
                this.image1.setImageBitmap(cropInRatio(bitmap2, 1, 1));
            } else if (i2 == 1) {
                this.image2.setImageBitmap(cropInRatio(bitmap2, 16, 9));
            } else if (i2 == 2) {
                this.image3.setImageBitmap(cropInRatio(bitmap2, 9, 16));
            } else if (i2 == 3) {
                this.image4.setImageBitmap(cropInRatio(bitmap2, 4, 3));
            } else if (i2 == 4) {
                this.image5.setImageBitmap(cropInRatio(bitmap2, 3, 4));
            }
        }
    }

    private void changePagerScroller() {
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            declaredField.set(this.pager, new SlowScroller(this.pager.getContext()));
        } catch (Exception e) {
            Log.e("texting", "error of change scroller ", e);
        }
    }

    private void callActivity(String str) {
        if (this.image_container.getVisibility() == View.VISIBLE) {
            this.image_container.startAnimation(this.animSlideDown);
            this.image_container.setVisibility(View.GONE);
        }
        Intent intent = new Intent(this, PosterActivity.class);
        intent.putExtra("ratio", str);
        intent.putExtra("position", this.position);
        intent.putExtra("profile", this.profile);
        intent.putExtra("hex", this.hex12);
        intent.putExtra("loadUserFrame", true);
        startActivityForResult(intent, 1000);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == RESULT_OK && i == 1000) {
            setResult(RESULT_OK);
            finish();
        } else {
            try {
                this.fragmentbackgund.onActivityResult(i, i2, intent);
                this.fragmenttexture.onActivityResult(i, i2, intent);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (RuntimeException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            if (i2 == -1) {
                if (intent != null || i == SELECT_PICTURE_FROM_CAMERA || i == 4 || i == 1123) {
                    if (i == SELECT_PICTURE_FROM_GALLERY) {
                        try {
                            bitmap = Constants.getBitmapFromUri(this, intent.getData(), this.screenWidth, this.screenHeight);
                            bitmap = ImageUtils.resizeBitmap(bitmap, (int) this.screenWidth, (int) this.screenHeight);
                            Intent intent2 = new Intent(this, CropActivity.class);
                            intent2.putExtra("value", "image");
                            startActivityForResult(intent2, 4);
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (i == SELECT_PICTURE_FROM_CAMERA) {
                        try {
                            bitmap = Constants.getBitmapFromUri(this, Uri.fromFile(this.f21f), this.screenWidth, this.screenHeight);
                            bitmap = ImageUtils.resizeBitmap(bitmap, (int) this.screenWidth, (int) this.screenHeight);
                            Intent intent3 = new Intent(this, CropActivity.class);
                            intent3.putExtra("value", "image");
                            startActivityForResult(intent3, 4);
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (i == 4) {
                        Intent intent4 = new Intent(this, PosterActivity.class);
                        intent4.putExtra("ratio", "cropImg");
                        intent4.putExtra("loadUserFrame", true);
                        startActivityForResult(intent4, 1123);
                    }
                    if (i == 1123) {
                        this.tabHost.setSelectedNavigationItem(0);
                        this.adapter = new ViewPagerAdapter(getSupportFragmentManager());
                        this.adapter.notifyDataSetChanged();
                        this.pager.setAdapter(this.adapter);
                        this.pager.setCurrentItem(0, true);
                        return;
                    }
                    return;
                }
                AlertDialog create = new Builder(this, 16974126).setMessage(Constants.getSpannableString(this, Typeface.DEFAULT, R.string.picUpImg)).setPositiveButton(Constants.getSpannableString(this, Typeface.DEFAULT, R.string.ok), (DialogInterface.OnClickListener) new C03412()).create();
                create.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_;
                create.show();
            }
        }
    }

    public Bitmap cropInRatio(Bitmap bitmap2, int i, int i2) {
        float width = (float) bitmap2.getWidth();
        float height = (float) bitmap2.getHeight();
        float f = getnewHeight(i, i2, width, height);
        float f2 = getnewWidth(i, i2, width, height);
        return (f2 == width && f == height) ? bitmap2 : (f > height || f >= height) ? (f2 > width || f2 >= width) ? null : Bitmap.createBitmap(bitmap2, (int) ((width - f2) / 2.0f), 0, (int) f2, (int) height) : Bitmap.createBitmap(bitmap2, 0, (int) ((height - f) / 2.0f), (int) width, (int) f);
    }

    public void onBackPressed() {
        if (this.image_container.getVisibility() == View.VISIBLE) {
            this.image_container.startAnimation(this.animSlideDown);
            this.image_container.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }
}
