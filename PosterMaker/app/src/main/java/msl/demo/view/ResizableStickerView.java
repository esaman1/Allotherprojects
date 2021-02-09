package msl.demo.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.ItemTouchHelper.Callback;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import postermaster.postermaker.R;
import msl.demo.listener.MultiTouchListener;
import msl.demo.listener.MultiTouchListener.TouchCallbackListener;

public class ResizableStickerView extends RelativeLayout implements TouchCallbackListener {
    private static final int SELF_SIZE_DP = 30;
    public static final String TAG = "ResizableStickerView";
    private int alphaProg = 0;
    double angle = 0.0d;
    int baseh;
    int basew;
    int basex;
    int basey;
    private ImageView border_iv;
    private Bitmap btmp = null;
    float cX = 0.0f;
    float cY = 0.0f;
    /* access modifiers changed from: private */
    public double centerX;
    /* access modifiers changed from: private */
    public double centerY;
    private String colorType = "colored";
    private Context context;
    double dAngle = 0.0d;
    private ImageView delete_iv;
    private String drawableId;
    /* access modifiers changed from: private */
    public int f23s;
    private String field_four = "";
    private int field_one = 0;
    private String field_three = "";
    /* access modifiers changed from: private */
    public String field_two = "0,0";
    private ImageView flip_iv;
    /* access modifiers changed from: private */
    public int he;
    float heightMain = 0.0f;
    private int hueProg = 1;
    private int imgAlpha = 100;
    private int imgColor = 0;
    private boolean isBorderVisible = false;
    private boolean isColorFilterEnable = false;
    public boolean isMultiTouchEnabled = true;
    private boolean isSticker = true;
    private boolean isStrickerEditEnable = false;
    /* access modifiers changed from: private */
    public int leftMargin = 0;
    /* access modifiers changed from: private */
    public TouchEventListener listener = null;
    private OnTouchListener mTouchListener = new C04005();
    private OnTouchListener mTouchListener1 = new C04027();
    public ImageView main_iv;
    int margl;
    int margt;
    private OnTouchListener rTouchListener = new C04016();
    private Uri resUri = null;
    private ImageView rotate_iv;
    private float rotation;
    Animation scale;
    private int scaleRotateProg = 0;
    private ImageView scale_iv;
    /* access modifiers changed from: private */
    public double scale_orgHeight = -1.0d;
    /* access modifiers changed from: private */
    public double scale_orgWidth = -1.0d;
    /* access modifiers changed from: private */
    public float scale_orgX = -1.0f;
    /* access modifiers changed from: private */
    public float scale_orgY = -1.0f;
    int screenHeight = 300;
    int screenWidth = 300;
    /* access modifiers changed from: private */
    public String stkr_path = "";
    double tAngle = 0.0d;
    /* access modifiers changed from: private */
    public float this_orgX = -1.0f;
    /* access modifiers changed from: private */
    public float this_orgY = -1.0f;
    /* access modifiers changed from: private */
    public int topMargin = 0;
    double vAngle = 0.0d;
    /* access modifiers changed from: private */
    public int wi;
    float widthMain = 0.0f;
    private int xRotateProg = 0;
    private int yRotateProg = 0;
    private float yRotation;
    private int zRotateProg = 0;
    Animation zoomInScale;
    Animation zoomOutScale;

    class C03951 implements OnClickListener {
        C03951() {
        }

        public void onClick(View view) {
            ImageView imageView = ResizableStickerView.this.main_iv;
            float f = -180.0f;
            if (ResizableStickerView.this.main_iv.getRotationY() == -180.0f) {
                f = 0.0f;
            }
            imageView.setRotationY(f);
            ResizableStickerView.this.main_iv.invalidate();
            ResizableStickerView.this.requestLayout();
        }
    }

    class C03972 implements OnClickListener {
        C03972() {
        }

        public void onClick(View view) {
            final ViewGroup viewGroup = (ViewGroup) ResizableStickerView.this.getParent();
            ResizableStickerView.this.zoomInScale.setAnimationListener(new AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    viewGroup.removeView(ResizableStickerView.this);
                }
            });
            ResizableStickerView.this.main_iv.startAnimation(ResizableStickerView.this.zoomInScale);
            ResizableStickerView.this.setBorderVisibility(false);
            if (ResizableStickerView.this.listener != null) {
                ResizableStickerView.this.listener.onDelete();
            }
        }
    }

    class C03994 implements OnDismissListener {
        public void onDismiss(DialogInterface dialogInterface) {
        }

        C03994() {
        }
    }

    class C04005 implements OnTouchListener {
        C04005() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case 0:
                    ResizableStickerView resizableStickerView = ResizableStickerView.this;
                    resizableStickerView.this_orgX = resizableStickerView.getX();
                    ResizableStickerView resizableStickerView2 = ResizableStickerView.this;
                    resizableStickerView2.this_orgY = resizableStickerView2.getY();
                    ResizableStickerView.this.scale_orgX = motionEvent.getRawX();
                    ResizableStickerView.this.scale_orgY = motionEvent.getRawY();
                    ResizableStickerView resizableStickerView3 = ResizableStickerView.this;
                    resizableStickerView3.scale_orgWidth = (double) resizableStickerView3.getLayoutParams().width;
                    ResizableStickerView resizableStickerView4 = ResizableStickerView.this;
                    resizableStickerView4.scale_orgHeight = (double) resizableStickerView4.getLayoutParams().height;
                    ResizableStickerView resizableStickerView5 = ResizableStickerView.this;
                    resizableStickerView5.centerX = (double) (((View) resizableStickerView5.getParent()).getX() + ResizableStickerView.this.getX() + (((float) ResizableStickerView.this.getWidth()) / 2.0f));
                    int i = 0;
                    int identifier = ResizableStickerView.this.getResources().getIdentifier("status_bar_height", "dimen", "android");
                    if (identifier > 0) {
                        i = ResizableStickerView.this.getResources().getDimensionPixelSize(identifier);
                    }
                    ResizableStickerView resizableStickerView6 = ResizableStickerView.this;
                    double y = (double) (((View) resizableStickerView6.getParent()).getY() + ResizableStickerView.this.getY());
                    double d = (double) i;
                    Double.isNaN(y);
                    Double.isNaN(d);
                    double d2 = y + d;
                    double height = (double) (((float) ResizableStickerView.this.getHeight()) / 2.0f);
                    Double.isNaN(height);
                    resizableStickerView6.centerY = d2 + height;
                    break;
                case 1:
                    ResizableStickerView resizableStickerView7 = ResizableStickerView.this;
                    resizableStickerView7.wi = resizableStickerView7.getLayoutParams().width;
                    ResizableStickerView resizableStickerView8 = ResizableStickerView.this;
                    resizableStickerView8.he = resizableStickerView8.getLayoutParams().height;
                    break;
                case 2:
                    double atan2 = Math.atan2((double) (motionEvent.getRawY() - ResizableStickerView.this.scale_orgY), (double) (motionEvent.getRawX() - ResizableStickerView.this.scale_orgX));
                    double access$400 = (double) ResizableStickerView.this.scale_orgY;
                    double access$800 = ResizableStickerView.this.centerY;
                    Double.isNaN(access$400);
                    double d3 = access$400 - access$800;
                    double access$300 = (double) ResizableStickerView.this.scale_orgX;
                    double access$700 = ResizableStickerView.this.centerX;
                    Double.isNaN(access$300);
                    double abs = (Math.abs(atan2 - Math.atan2(d3, access$300 - access$700)) * 180.0d) / 3.141592653589793d;
                    String str = ResizableStickerView.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("angle_diff: ");
                    sb.append(abs);
                    Log.v(str, sb.toString());
                    ResizableStickerView resizableStickerView9 = ResizableStickerView.this;
                    double access$1100 = resizableStickerView9.getLength(resizableStickerView9.centerX, ResizableStickerView.this.centerY, (double) ResizableStickerView.this.scale_orgX, (double) ResizableStickerView.this.scale_orgY);
                    ResizableStickerView resizableStickerView10 = ResizableStickerView.this;
                    double access$11002 = resizableStickerView10.getLength(resizableStickerView10.centerX, ResizableStickerView.this.centerY, (double) motionEvent.getRawX(), (double) motionEvent.getRawY());
                    ResizableStickerView resizableStickerView11 = ResizableStickerView.this;
                    int dpToPx = resizableStickerView11.dpToPx(resizableStickerView11.getContext(), 30);
                    if (access$11002 > access$1100 && (abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d)) {
                        double round = (double) Math.round(Math.max((double) Math.abs(motionEvent.getRawX() - ResizableStickerView.this.scale_orgX), (double) Math.abs(motionEvent.getRawY() - ResizableStickerView.this.scale_orgY)));
                        LayoutParams layoutParams = (LayoutParams) ResizableStickerView.this.getLayoutParams();
                        double d4 = (double) layoutParams.width;
                        Double.isNaN(d4);
                        Double.isNaN(round);
                        layoutParams.width = (int) (d4 + round);
                        LayoutParams layoutParams2 = (LayoutParams) ResizableStickerView.this.getLayoutParams();
                        double d5 = (double) layoutParams2.height;
                        Double.isNaN(d5);
                        Double.isNaN(round);
                        layoutParams2.height = (int) (d5 + round);
                    } else if (access$11002 < access$1100 && (abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d)) {
                        int i2 = dpToPx / 2;
                        if (ResizableStickerView.this.getLayoutParams().width > i2 && ResizableStickerView.this.getLayoutParams().height > i2) {
                            double round2 = (double) Math.round(Math.max((double) Math.abs(motionEvent.getRawX() - ResizableStickerView.this.scale_orgX), (double) Math.abs(motionEvent.getRawY() - ResizableStickerView.this.scale_orgY)));
                            LayoutParams layoutParams3 = (LayoutParams) ResizableStickerView.this.getLayoutParams();
                            double d6 = (double) layoutParams3.width;
                            Double.isNaN(d6);
                            Double.isNaN(round2);
                            layoutParams3.width = (int) (d6 - round2);
                            LayoutParams layoutParams4 = (LayoutParams) ResizableStickerView.this.getLayoutParams();
                            double d7 = (double) layoutParams4.height;
                            Double.isNaN(d7);
                            Double.isNaN(round2);
                            layoutParams4.height = (int) (d7 - round2);
                        }
                    }
                    ResizableStickerView.this.scale_orgX = motionEvent.getRawX();
                    ResizableStickerView.this.scale_orgY = motionEvent.getRawY();
                    ResizableStickerView.this.postInvalidate();
                    ResizableStickerView.this.requestLayout();
                    break;
            }
            return true;
        }
    }

    class C04016 implements OnTouchListener {
        C04016() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            ResizableStickerView resizableStickerView = (ResizableStickerView) view.getParent();
            switch (motionEvent.getAction()) {
                case 0:
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onRotateDown(ResizableStickerView.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    ResizableStickerView.this.cX = rect.exactCenterX();
                    ResizableStickerView.this.cY = rect.exactCenterY();
                    ResizableStickerView.this.vAngle = (double) ((View) view.getParent()).getRotation();
                    ResizableStickerView resizableStickerView2 = ResizableStickerView.this;
                    resizableStickerView2.tAngle = (Math.atan2((double) (resizableStickerView2.cY - motionEvent.getRawY()), (double) (ResizableStickerView.this.cX - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                    ResizableStickerView resizableStickerView3 = ResizableStickerView.this;
                    resizableStickerView3.dAngle = resizableStickerView3.vAngle - ResizableStickerView.this.tAngle;
                    break;
                case 1:
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onRotateUp(ResizableStickerView.this);
                        break;
                    }
                    break;
                case 2:
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onRotateMove(ResizableStickerView.this);
                    }
                    ResizableStickerView resizableStickerView4 = ResizableStickerView.this;
                    resizableStickerView4.angle = (Math.atan2((double) (resizableStickerView4.cY - motionEvent.getRawY()), (double) (ResizableStickerView.this.cX - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (ResizableStickerView.this.angle + ResizableStickerView.this.dAngle));
                    ((View) view.getParent()).invalidate();
                    ((View) view.getParent()).requestLayout();
                    break;
            }
            return true;
        }
    }

    class C04027 implements OnTouchListener {
        C04027() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent motionEvent) {
            ResizableStickerView resizableStickerView = (ResizableStickerView) view.getParent();
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            LayoutParams layoutParams = (LayoutParams) ResizableStickerView.this.getLayoutParams();
            switch (motionEvent.getAction()) {
                case 0:
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleDown(ResizableStickerView.this);
                    }
                    ResizableStickerView.this.invalidate();
                    ResizableStickerView resizableStickerView2 = ResizableStickerView.this;
                    resizableStickerView2.basex = rawX;
                    resizableStickerView2.basey = rawY;
                    resizableStickerView2.basew = resizableStickerView2.getWidth();
                    ResizableStickerView resizableStickerView3 = ResizableStickerView.this;
                    resizableStickerView3.baseh = resizableStickerView3.getHeight();
                    ResizableStickerView.this.getLocationOnScreen(new int[2]);
                    ResizableStickerView.this.margl = layoutParams.leftMargin;
                    ResizableStickerView.this.margt = layoutParams.topMargin;
                    break;
                case 1:
                    ResizableStickerView resizableStickerView4 = ResizableStickerView.this;
                    resizableStickerView4.wi = resizableStickerView4.getLayoutParams().width;
                    ResizableStickerView resizableStickerView5 = ResizableStickerView.this;
                    resizableStickerView5.he = resizableStickerView5.getLayoutParams().height;
                    ResizableStickerView resizableStickerView6 = ResizableStickerView.this;
                    resizableStickerView6.leftMargin = ((LayoutParams) resizableStickerView6.getLayoutParams()).leftMargin;
                    ResizableStickerView resizableStickerView7 = ResizableStickerView.this;
                    resizableStickerView7.topMargin = ((LayoutParams) resizableStickerView7.getLayoutParams()).topMargin;
                    ResizableStickerView resizableStickerView8 = ResizableStickerView.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.valueOf(ResizableStickerView.this.leftMargin));
                    sb.append(",");
                    sb.append(String.valueOf(ResizableStickerView.this.topMargin));
                    resizableStickerView8.field_two = sb.toString();
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleUp(ResizableStickerView.this);
                        break;
                    }
                    break;
                case 2:
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleMove(ResizableStickerView.this);
                    }
                    float degrees = (float) Math.toDegrees(Math.atan2((double) (rawY - ResizableStickerView.this.basey), (double) (rawX - ResizableStickerView.this.basex)));
                    if (degrees < 0.0f) {
                        degrees += 360.0f;
                    }
                    int i = rawX - ResizableStickerView.this.basex;
                    int i2 = rawY - ResizableStickerView.this.basey;
                    int i3 = i2 * i2;
                    int sqrt = (int) (Math.sqrt((double) ((i * i) + i3)) * Math.cos(Math.toRadians((double) (degrees - ResizableStickerView.this.getRotation()))));
                    int sqrt2 = (int) (Math.sqrt((double) ((sqrt * sqrt) + i3)) * Math.sin(Math.toRadians((double) (degrees - ResizableStickerView.this.getRotation()))));
                    int i4 = (sqrt * 2) + ResizableStickerView.this.basew;
                    int i5 = (sqrt2 * 2) + ResizableStickerView.this.baseh;
                    if (i4 > ResizableStickerView.this.f23s) {
                        layoutParams.width = i4;
                        layoutParams.leftMargin = ResizableStickerView.this.margl - sqrt;
                    }
                    if (i5 > ResizableStickerView.this.f23s) {
                        layoutParams.height = i5;
                        layoutParams.topMargin = ResizableStickerView.this.margt - sqrt2;
                    }
                    ResizableStickerView.this.setLayoutParams(layoutParams);
                    ResizableStickerView.this.performLongClick();
                    break;
            }
            return true;
        }
    }

    public interface TouchEventListener {
        void onDelete();

        void onEdit(View view, Uri uri);

        void onRotateDown(View view);

        void onRotateMove(View view);

        void onRotateUp(View view);

        void onScaleDown(View view);

        void onScaleMove(View view);

        void onScaleUp(View view);

        void onTouchDown(View view);

        void onTouchMove(View view);

        void onTouchUp(View view);
    }

    public ResizableStickerView setOnTouchCallbackListener(TouchEventListener touchEventListener) {
        this.listener = touchEventListener;
        return this;
    }

    public ResizableStickerView(Context context2) {
        super(context2);
        init(context2);
    }

    public ResizableStickerView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        init(context2);
    }

    public ResizableStickerView(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        init(context2);
    }

    public void init(Context context2) {
        this.context = context2;
        this.main_iv = new ImageView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.flip_iv = new ImageView(this.context);
        this.rotate_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.f23s = dpToPx(this.context, 25);
        this.wi = dpToPx(this.context, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.he = dpToPx(this.context, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.scale_iv.setImageResource(R.drawable.sticker_scale);
        this.border_iv.setImageResource(R.drawable.sticker_border_gray);
        this.flip_iv.setImageResource(R.drawable.sticker_flip);
        this.rotate_iv.setImageResource(R.drawable.rotate);
        this.delete_iv.setImageResource(R.drawable.sticker_delete1);
        LayoutParams layoutParams = new LayoutParams(this.wi, this.he);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        layoutParams2.setMargins(5, 5, 5, 5);
        layoutParams2.addRule(17);
        int i = this.f23s;
        LayoutParams layoutParams3 = new LayoutParams(i, i);
        layoutParams3.addRule(12);
        layoutParams3.addRule(11);
        layoutParams3.setMargins(5, 5, 5, 5);
        int i2 = this.f23s;
        LayoutParams layoutParams4 = new LayoutParams(i2, i2);
        layoutParams4.addRule(10);
        layoutParams4.addRule(11);
        layoutParams4.setMargins(5, 5, 5, 5);
        int i3 = this.f23s;
        LayoutParams layoutParams5 = new LayoutParams(i3, i3);
        layoutParams5.addRule(12);
        layoutParams5.addRule(9);
        layoutParams5.setMargins(5, 5, 5, 5);
        int i4 = this.f23s;
        LayoutParams layoutParams6 = new LayoutParams(i4, i4);
        layoutParams6.addRule(10);
        layoutParams6.addRule(9);
        layoutParams6.setMargins(5, 5, 5, 5);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        setBackgroundResource(R.drawable.sticker_border_gray1);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(layoutParams7);
        this.border_iv.setScaleType(ScaleType.FIT_XY);
        this.border_iv.setTag("border_iv");
        addView(this.main_iv);
        this.main_iv.setLayoutParams(layoutParams2);
        addView(this.flip_iv);
        this.flip_iv.setLayoutParams(layoutParams4);
        this.flip_iv.setOnClickListener(new C03951());
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(layoutParams5);
        this.rotate_iv.setOnTouchListener(this.rTouchListener);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(layoutParams6);
        this.delete_iv.setOnClickListener(new C03972());
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(layoutParams3);
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.scale_iv.setTag("scale_iv");
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_anim);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_in);
        this.isMultiTouchEnabled = setDefaultTouchListener(true);
    }

    public boolean setDefaultTouchListener(boolean z) {
        if (z) {
            setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this));
            return true;
        }
        setOnTouchListener(null);
        return false;
    }

    public void setBorderVisibility(boolean z) {
        this.isBorderVisible = z;
        if (!z) {
            this.border_iv.setVisibility(View.GONE);
            this.scale_iv.setVisibility(View.GONE);
            this.flip_iv.setVisibility(View.GONE);
            this.rotate_iv.setVisibility(View.GONE);
            this.delete_iv.setVisibility(View.GONE);
            setBackgroundResource(0);
            if (this.isColorFilterEnable) {
                this.main_iv.setColorFilter(Color.parseColor("#303828"));
            }
        } else if (this.border_iv.getVisibility() != View.VISIBLE) {
            this.border_iv.setVisibility(View.VISIBLE);
            this.scale_iv.setVisibility(View.VISIBLE);
            this.flip_iv.setVisibility(View.VISIBLE);
            this.rotate_iv.setVisibility(View.VISIBLE);
            this.delete_iv.setVisibility(View.VISIBLE);
            setBackgroundResource(R.drawable.sticker_border_gray1);
            this.main_iv.startAnimation(this.scale);
        }
    }

    public boolean getBorderVisbilty() {
        return this.isBorderVisible;
    }

    public void opecitySticker(int i) {
        try {
            this.main_iv.setAlpha(((float) i) / 100.0f);
            this.imgAlpha = i;
        } catch (Exception unused) {
        }
    }

    public int getHueProg() {
        return this.hueProg;
    }

    public void setHueProg(int i) {
        this.hueProg = i;
        int i2 = this.hueProg;
        if (i2 == 0) {
            this.main_iv.setColorFilter(-1);
        } else if (i2 == 100) {
            this.main_iv.setColorFilter(-16777216);
        } else {
            this.main_iv.setColorFilter(ColorFilterGenerator.adjustHue((float) i));
        }
    }

    public String getColorType() {
        return this.colorType;
    }

    public void setColorType(String str) {
        this.colorType = str;
    }

    public int getAlphaProg() {
        return this.imgAlpha;
    }

    public void setAlphaProg(int i) {
        this.alphaProg = i;
        opecitySticker(i);
    }

    public int getColor() {
        return this.imgColor;
    }

    public void setColor(int i) {
        try {
            this.main_iv.setColorFilter(i);
            this.imgColor = i;
        } catch (Exception unused) {
        }
    }

    public void setBgDrawable(String str) {
        Glide.with(this.context).load(Integer.valueOf(getResources().getIdentifier(str, "drawable", this.context.getPackageName()))).dontAnimate().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image).into(this.main_iv);
        this.drawableId = str;
        this.main_iv.startAnimation(this.zoomOutScale);
    }

    public void setStrPath(String str) {
        try {
            this.main_iv.setImageBitmap(ImageUtils.getResampleImageBitmap(Uri.parse(str), this.context, this.screenWidth > this.screenHeight ? this.screenWidth : this.screenHeight));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.stkr_path = str;
        this.main_iv.startAnimation(this.zoomOutScale);
    }

    public void setMainImageUri(Uri uri) {
        this.resUri = uri;
        this.main_iv.setImageURI(this.resUri);
    }

    public Uri getMainImageUri() {
        return this.resUri;
    }

    public void setMainImageBitmap(Bitmap bitmap) {
        this.main_iv.setImageBitmap(bitmap);
    }

    public Bitmap getMainImageBitmap() {
        return this.btmp;
    }

    public void setComponentInfo(ComponentInfo componentInfo) {
        this.wi = componentInfo.getWIDTH();
        this.he = componentInfo.getHEIGHT();
        this.drawableId = componentInfo.getRES_ID();
        this.resUri = componentInfo.getRES_URI();
        this.btmp = componentInfo.getBITMAP();
        this.rotation = componentInfo.getROTATION();
        this.imgColor = componentInfo.getSTC_COLOR();
        this.yRotation = componentInfo.getY_ROTATION();
        this.imgAlpha = componentInfo.getSTC_OPACITY();
        this.stkr_path = componentInfo.getSTKR_PATH();
        this.colorType = componentInfo.getCOLORTYPE();
        this.hueProg = componentInfo.getSTC_HUE();
        this.field_two = componentInfo.getFIELD_TWO();
        if (!this.stkr_path.equals("")) {
            setStrPath(this.stkr_path);
        } else if (this.drawableId.equals("")) {
            this.main_iv.setImageBitmap(this.btmp);
        } else {
            setBgDrawable(this.drawableId);
        }
        if (this.colorType.equals("white")) {
            setColor(this.imgColor);
        } else {
            setHueProg(this.hueProg);
        }
        setRotation(this.rotation);
        opecitySticker(this.imgAlpha);
        if (this.field_two.equals("")) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(componentInfo.getPOS_X());
            setY(componentInfo.getPOS_Y());
        } else {
            String[] split = this.field_two.split(",");
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
            ((LayoutParams) getLayoutParams()).topMargin = parseInt2;
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(componentInfo.getPOS_X() + ((float) (parseInt * -1)));
            setY(componentInfo.getPOS_Y() + ((float) (parseInt2 * -1)));
        }
        if (componentInfo.getTYPE() == "SHAPE") {
            this.flip_iv.setVisibility(View.GONE);
            this.isSticker = false;
        }
        if (componentInfo.getTYPE() == "STICKER") {
            this.flip_iv.setVisibility(View.VISIBLE);
            this.isSticker = true;
        }
        this.main_iv.setRotationY(this.yRotation);
    }

    public void optimize(float f, float f2) {
        setX(getX() * f);
        setY(getY() * f2);
        getLayoutParams().width = (int) (((float) this.wi) * f);
        getLayoutParams().height = (int) (((float) this.he) * f2);
    }

    public void optimizeScreen(float f, float f2) {
        this.screenHeight = (int) f2;
        this.screenWidth = (int) f;
    }

    public void setMainLayoutWH(float f, float f2) {
        this.widthMain = f;
        this.heightMain = f2;
    }

    public float getMainWidth() {
        return this.widthMain;
    }

    public float getMainHeight() {
        return this.heightMain;
    }

    public void incrX() {
        setX(getX() + 1.0f);
    }

    public void decX() {
        setX(getX() - 1.0f);
    }

    public void incrY() {
        setY(getY() + 1.0f);
    }

    public void decY() {
        setY(getY() - 1.0f);
    }

    public ComponentInfo getComponentInfo() {
        Bitmap bitmap = this.btmp;
        if (bitmap != null) {
            this.stkr_path = saveBitmapObject1(bitmap);
        }
        ComponentInfo componentInfo = new ComponentInfo();
        componentInfo.setPOS_X(getX());
        componentInfo.setPOS_Y(getY());
        componentInfo.setWIDTH(this.wi);
        componentInfo.setHEIGHT(this.he);
        componentInfo.setRES_ID(this.drawableId);
        componentInfo.setSTC_COLOR(this.imgColor);
        componentInfo.setRES_URI(this.resUri);
        componentInfo.setSTC_OPACITY(this.imgAlpha);
        componentInfo.setCOLORTYPE(this.colorType);
        componentInfo.setBITMAP(this.btmp);
        componentInfo.setROTATION(getRotation());
        componentInfo.setY_ROTATION(this.main_iv.getRotationY());
        componentInfo.setXRotateProg(this.xRotateProg);
        componentInfo.setYRotateProg(this.yRotateProg);
        componentInfo.setZRotateProg(this.zRotateProg);
        componentInfo.setScaleProg(this.scaleRotateProg);
        componentInfo.setSTKR_PATH(this.stkr_path);
        componentInfo.setSTC_HUE(this.hueProg);
        componentInfo.setFIELD_ONE(this.field_one);
        componentInfo.setFIELD_TWO(this.field_two);
        componentInfo.setFIELD_THREE(this.field_three);
        componentInfo.setFIELD_FOUR(this.field_four);
        return componentInfo;
    }

    private void saveStkrBitmap(final Bitmap bitmap) {
        final ProgressDialog show = ProgressDialog.show(this.context, "", "", true);
        show.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    ResizableStickerView.this.stkr_path = ResizableStickerView.this.saveBitmapObject1(bitmap);
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Exception ");
                    sb.append(e.getMessage());
                    Log.i("testing", sb.toString());
                    e.printStackTrace();
                } catch (Throwable unused) {
                }
                show.dismiss();
            }
        }).start();
        show.setOnDismissListener(new C03994());
    }

    /* access modifiers changed from: private */
    public String saveBitmapObject1(Bitmap bitmap) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Maker Stickers/category1");
        file.mkdirs();
        StringBuilder sb = new StringBuilder();
        sb.append("raw1-");
        sb.append(System.currentTimeMillis());
        sb.append(".png");
        File file2 = new File(file, sb.toString());
        String absolutePath = file2.getAbsolutePath();
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return absolutePath;
        } catch (Exception e) {
            e.printStackTrace();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Exception");
            sb2.append(e.getMessage());
            Log.i("testing", sb2.toString());
            return "";
        }
    }

    public int dpToPx(Context context2, int i) {
        float f = (float) i;
        context2.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }

    /* access modifiers changed from: private */
    public double getLength(double d, double d2, double d3, double d4) {
        return Math.sqrt(Math.pow(d4 - d2, 2.0d) + Math.pow(d3 - d, 2.0d));
    }

    public void enableColorFilter(boolean z) {
        this.isColorFilterEnable = z;
    }

    public void onTouchCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchDown(view);
        }
    }

    public void onTouchUpCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchUp(view);
        }
    }

    public void onTouchMoveCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchMove(view);
        }
    }
}
