package postermaster.postermaker.main;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener;

import java.io.File;
import java.lang.reflect.Field;

import postermaster.postermaker.R;
import postermaster.postermaker.utility.ImageUtils;
import postermaster.postermaker.utility.SlowScroller;
import postermaster.postermaker.utility.ZoomOutSlideTransformer;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class SelectImageTwoActivity extends AppCompatActivity implements MaterialTabListener, OnClickListener, OnGetImageOnTouch {
    private static final int OPEN_CUSTOM_ACITIVITY = 4;
    private static final int SELECT_PICTURE_FROM_CAMERA = 9062;
    private static final int SELECT_PICTURE_FROM_GALLERY = 9072;
    public static Bitmap bitmap;
    ViewPagerAdapter adapter;
    /* access modifiers changed from: private */
    public Animation animSlideDown;
    private Animation animSlideUp;
    Editor editor;
    File f22f;
    FragmentBackgrounds fragmentbackgund = null;
    FragmentColor fragmentcolor = null;
    FragmentImage fragmentimage = null;
    FragmentTexture fragmenttexture = null;
    String hex12 = "";
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    RelativeLayout image_container;
    RelativeLayout lay_crop;
    ViewPager pager;
    String position;
    SharedPreferences preferences;
    SharedPreferences prefs;
    String profile;
    float screenHeight;
    float screenWidth;
    MaterialTabHost tabHost;
    /* access modifiers changed from: private */
    public Typeface ttf;
    private Typeface ttfHeader;

    class C03442 implements DialogInterface.OnClickListener {
        C03442() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    class C06971 extends SimpleOnPageChangeListener {
        C06971() {
        }

        public void onPageSelected(int i) {
            SelectImageTwoActivity.this.tabHost.setSelectedNavigationItem(i);
            if (SelectImageTwoActivity.this.image_container.getVisibility() == View.VISIBLE) {
                SelectImageTwoActivity.this.image_container.startAnimation(SelectImageTwoActivity.this.animSlideDown);
                SelectImageTwoActivity.this.image_container.setVisibility(View.GONE);
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
                SelectImageTwoActivity.this.fragmentbackgund = new FragmentBackgrounds();
                return SelectImageTwoActivity.this.fragmentbackgund;
            } else if (i == 1) {
                SelectImageTwoActivity.this.fragmenttexture = new FragmentTexture();
                return SelectImageTwoActivity.this.fragmenttexture;
            } else if (i == 2) {
                SelectImageTwoActivity.this.fragmentimage = new FragmentImage();
                return SelectImageTwoActivity.this.fragmentimage;
            } else if (i != 3) {
                return null;
            } else {
                SelectImageTwoActivity.this.fragmentcolor = new FragmentColor();
                return SelectImageTwoActivity.this.fragmentcolor;
            }
        }

        public CharSequence getPageTitle(int i) {
            String str = "";
            if (i == 0) {
                SelectImageTwoActivity selectImageTwoActivity = SelectImageTwoActivity.this;
                return ImageUtils.getSpannableString(selectImageTwoActivity, selectImageTwoActivity.ttf, R.string.txt_backgrounds);
            } else if (i == 1) {
                SelectImageTwoActivity selectImageTwoActivity2 = SelectImageTwoActivity.this;
                return ImageUtils.getSpannableString(selectImageTwoActivity2, selectImageTwoActivity2.ttf, R.string.txt_texture);
            } else if (i == 2) {
                SelectImageTwoActivity selectImageTwoActivity3 = SelectImageTwoActivity.this;
                return ImageUtils.getSpannableString(selectImageTwoActivity3, selectImageTwoActivity3.ttf, R.string.txt_image);
            } else if (i != 3) {
                return str;
            } else {
                SelectImageTwoActivity selectImageTwoActivity4 = SelectImageTwoActivity.this;
                return ImageUtils.getSpannableString(selectImageTwoActivity4, selectImageTwoActivity4.ttf, R.string.txt_color);
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

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_select_image);
        this.ttf = Constants.getTextTypeface(this);
        this.ttfHeader = Constants.getHeaderTypeface(this);
        this.tabHost = (MaterialTabHost) findViewById(R.id.tabHost);
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
        this.image1.setOnClickListener(this);
        this.image2.setOnClickListener(this);
        this.image3.setOnClickListener(this);
        this.image4.setOnClickListener(this);
        this.image5.setOnClickListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = (float) displayMetrics.widthPixels;
        this.screenHeight = (float) displayMetrics.heightPixels;
        this.f22f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        ((TextView) findViewById(R.id.txt_appname)).setTypeface(this.ttfHeader);
        this.prefs = getSharedPreferences("MY_PREFS_NAME", 0);
        this.editor = getSharedPreferences("MY_PREFS_NAME", 0).edit();
    }

    private void initUI() {
        this.adapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.adapter.notifyDataSetChanged();
        this.pager.setAdapter(this.adapter);
        this.pager.setOnPageChangeListener(new C06971());
        this.pager.setPageTransformer(true, new ZoomOutSlideTransformer());
        changePagerScroller();
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
                    callActivity("1:1");
                    return;
                case R.id.image2 /*2131296502*/:
                    callActivity("16:9");
                    return;
                case R.id.image3 /*2131296503*/:
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

    public void onBackPressed() {
        if (this.image_container.getVisibility() == View.VISIBLE) {
            this.image_container.startAnimation(this.animSlideDown);
            this.image_container.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }

    private void callActivity(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("ratio", str);
        bundle.putString("position", this.position);
        bundle.putString("profile", this.profile);
        bundle.putString("color", this.hex12);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(-1, intent);
        finish();
    }

    public void ongetPosition(int i, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(i);
        this.position = sb.toString();
        this.profile = str;
        this.hex12 = str2;
        if (this.image_container.getVisibility() != View.VISIBLE) {
            this.image_container.startAnimation(this.animSlideUp);
            this.image_container.setVisibility(View.VISIBLE);
        }
        Bitmap bitmap2 = null;
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
        } else if (str.equals("Temp_Path") && FragmentBackImage.thumbnails.size() != 0) {
            bitmap2 = (Bitmap) FragmentBackImage.thumbnails.get(i);
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

    public Bitmap cropInRatio(Bitmap bitmap2, int i, int i2) {
        float width = (float) bitmap2.getWidth();
        float height = (float) bitmap2.getHeight();
        float f = getnewHeight(i, i2, width, height);
        float f2 = getnewWidth(i, i2, width, height);
        return (f2 == width && f == height) ? bitmap2 : (f > height || f >= height) ? (f2 > width || f2 >= width) ? null : Bitmap.createBitmap(bitmap2, (int) ((width - f2) / 2.0f), 0, (int) f2, (int) height) : Bitmap.createBitmap(bitmap2, 0, (int) ((height - f) / 2.0f), (int) width, (int) f);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
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
            if (intent != null || i == SELECT_PICTURE_FROM_CAMERA || i == 4) {
                if (i == SELECT_PICTURE_FROM_GALLERY) {
                    try {
                        bitmap = Constants.getBitmapFromUri(this, intent.getData(), this.screenWidth, this.screenHeight);
                        bitmap = ImageUtils.resizeBitmap(bitmap, (int) this.screenWidth, (int) this.screenHeight);
                        Intent intent2 = new Intent(this, CropActivityTwo.class);
                        intent2.putExtra("value", "image");
                        startActivityForResult(intent2, 4);
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
                if (i == SELECT_PICTURE_FROM_CAMERA) {
                    try {
                        bitmap = Constants.getBitmapFromUri(this, Uri.fromFile(this.f22f), this.screenWidth, this.screenHeight);
                        bitmap = ImageUtils.resizeBitmap(bitmap, (int) this.screenWidth, (int) this.screenHeight);
                        Intent intent3 = new Intent(this, CropActivityTwo.class);
                        intent3.putExtra("value", "image");
                        startActivityForResult(intent3, 4);
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                if (i == 4) {
                    Bundle bundle = new Bundle();
                    bundle.putString("profile", "no");
                    Intent intent4 = new Intent();
                    intent4.putExtras(bundle);
                    setResult(-1, intent4);
                    finish();
                    return;
                }
                return;
            }
            AlertDialog create = new Builder(this, 16974126).setMessage(Constants.getSpannableString(this, Typeface.DEFAULT, R.string.picUpImg)).setPositiveButton(Constants.getSpannableString(this, Typeface.DEFAULT, R.string.ok), new C03442()).create();
            create.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_;
            create.show();
        }
    }
}
