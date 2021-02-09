package postermaster.postermaker.main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import postermaster.postermaker.PosterActivity;
import postermaster.postermaker.R;
import postermaster.postermaker.utility.ImageUtils;

public class PickColorImageActivity extends Activity {
    float f10r;
    int height;
    Bitmap imgBtmap;
    float initialX;
    float initialY;
    GetColorListener onGetColor;
    int pixel = -1;
    float rat1;
    float rat2;
    float screenHeight;
    float screenWidth;
    int visiblePosition = 0;
    String way;

    class C03222 implements OnClickListener {
        C03222() {
        }

        public void onClick(View view) {
            PickColorImageActivity.this.onGetColor.onColor(PickColorImageActivity.this.pixel, PickColorImageActivity.this.way, PickColorImageActivity.this.visiblePosition);
            PickColorImageActivity.this.finish();
        }
    }

    class C03233 implements OnClickListener {
        C03233() {
        }

        public void onClick(View view) {
            PickColorImageActivity.this.onBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.dialog_color);
        this.onGetColor = (GetColorListener) PosterActivity.f25c;
        this.way = getIntent().getStringExtra("way");
        this.visiblePosition = getIntent().getIntExtra("visiPosition", 0);
        this.pixel = getIntent().getIntExtra("color", 0);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = (float) displayMetrics.widthPixels;
        this.height = 60;
        this.screenHeight = (float) (displayMetrics.heightPixels - ImageUtils.dpToPx(this, this.height));
        ImageView imageView = (ImageView) findViewById(R.id.img_base);
        TextView textView = (TextView) findViewById(R.id.txt_head);
        this.imgBtmap = PosterActivity.withoutWatermark;
        int[] resizeDim = ImageUtils.getResizeDim((float) this.imgBtmap.getWidth(), (float) this.imgBtmap.getHeight(), (int) this.screenWidth, (int) this.screenHeight);
        final Bitmap createScaledBitmap = Bitmap.createScaledBitmap(this.imgBtmap, resizeDim[0], resizeDim[1], false);
        imageView.getLayoutParams().width = resizeDim[0];
        imageView.getLayoutParams().height = resizeDim[1];
        imageView.setImageBitmap(createScaledBitmap);
        final ImageView imageView2 = (ImageView) findViewById(R.id.img_putcolor);
        imageView2.setBackgroundColor(this.pixel);
        ImageView imageView3 = (ImageView) findViewById(R.id.img_done);
        ImageView imageView4 = (ImageView) findViewById(R.id.img_back);
        imageView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    PickColorImageActivity.this.initialX = motionEvent.getX();
                    PickColorImageActivity.this.initialY = motionEvent.getY();
                    try {
                        PickColorImageActivity.this.pixel = createScaledBitmap.getPixel((int) PickColorImageActivity.this.initialX, (int) PickColorImageActivity.this.initialY);
                        imageView2.setBackgroundColor(PickColorImageActivity.this.pixel);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        PickColorImageActivity.this.pixel = 0;
                    }
                } else if (action == 2) {
                    PickColorImageActivity.this.initialX = motionEvent.getX();
                    PickColorImageActivity.this.initialY = motionEvent.getY();
                    try {
                        PickColorImageActivity.this.pixel = createScaledBitmap.getPixel((int) PickColorImageActivity.this.initialX, (int) PickColorImageActivity.this.initialY);
                        imageView2.setBackgroundColor(PickColorImageActivity.this.pixel);
                    } catch (IllegalArgumentException e2) {
                        e2.printStackTrace();
                        PickColorImageActivity.this.pixel = 0;
                    }
                }
                return true;
            }
        });
        imageView3.setOnClickListener(new C03222());
        imageView4.setOnClickListener(new C03233());
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.onGetColor.onColor(0, this.way, this.visiblePosition);
        finish();
    }
}
