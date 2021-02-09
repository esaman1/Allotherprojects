package msl.textmodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
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

import postermaster.postermaker.R;
import msl.textmodule.listener.MultiTouchListener;
import msl.textmodule.listener.MultiTouchListener.TouchCallbackListener;

public class AutofitTextRel extends RelativeLayout implements TouchCallbackListener {
    double angle = 0.0d;
    /* access modifiers changed from: private */
    public ImageView background_iv;
    int baseh;
    int basew;
    int basex;
    int basey;
    private int bgAlpha = 255;
    private int bgColor = 0;
    /* access modifiers changed from: private */
    public String bgDrawable = "0";
    private ImageView border_iv;
    float cX = 0.0f;
    float cY = 0.0f;
    private Context context;
    double dAngle = 0.0d;
    private ImageView delete_iv;
    /* access modifiers changed from: private */
    public int f24s;
    private String field_four = "";
    private int field_one = 0;
    private String field_three = "";
    /* access modifiers changed from: private */
    public String field_two = "0,0";
    private String fontName = "";
    private GestureDetector gd = null;
    /* access modifiers changed from: private */
    public int he;
    int height;
    private boolean isBorderVisible = false;
    public boolean isMultiTouchEnabled = true;
    /* access modifiers changed from: private */
    public int leftMargin = 0;
    /* access modifiers changed from: private */
    public TouchEventListener listener = null;
    private OnTouchListener mTouchListener1 = new C04073();
    int margl;
    int margt;
    private int progress = 0;
    private OnTouchListener rTouchListener = new C04062();
    float ratio;
    private ImageView rotate_iv;
    private float rotation;
    Animation scale;
    private ImageView scale_iv;
    int sh = 1794;
    private int shadowColor = 0;
    private int shadowProg = 0;
    int sw = 1080;
    private int tAlpha = 100;
    double tAngle = 0.0d;
    private int tColor = -16777216;
    private String text = "";
    /* access modifiers changed from: private */
    public AutoResizeTextView text_iv;
    /* access modifiers changed from: private */
    public int topMargin = 0;
    double vAngle = 0.0d;
    /* access modifiers changed from: private */
    public int wi;
    int width;
    private int xRotateProg = 0;
    private int yRotateProg = 0;
    private int zRotateProg = 0;
    Animation zoomInScale;
    Animation zoomOutScale;

    class C04051 implements OnClickListener {
        C04051() {
        }

        public void onClick(View view) {
            final ViewGroup viewGroup = (ViewGroup) AutofitTextRel.this.getParent();
            AutofitTextRel.this.zoomInScale.setAnimationListener(new AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    viewGroup.removeView(AutofitTextRel.this);
                }
            });
            AutofitTextRel.this.text_iv.startAnimation(AutofitTextRel.this.zoomInScale);
            AutofitTextRel.this.background_iv.startAnimation(AutofitTextRel.this.zoomInScale);
            AutofitTextRel.this.setBorderVisibility(false);
            if (AutofitTextRel.this.listener != null) {
                AutofitTextRel.this.listener.onDelete();
            }
        }
    }

    class C04062 implements OnTouchListener {
        C04062() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            AutofitTextRel autofitTextRel = (AutofitTextRel) view.getParent();
            switch (motionEvent.getAction()) {
                case 0:
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateDown(AutofitTextRel.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    AutofitTextRel.this.cX = rect.exactCenterX();
                    AutofitTextRel.this.cY = rect.exactCenterY();
                    AutofitTextRel.this.vAngle = (double) ((View) view.getParent()).getRotation();
                    AutofitTextRel autofitTextRel2 = AutofitTextRel.this;
                    autofitTextRel2.tAngle = (Math.atan2((double) (autofitTextRel2.cY - motionEvent.getRawY()), (double) (AutofitTextRel.this.cX - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                    AutofitTextRel autofitTextRel3 = AutofitTextRel.this;
                    autofitTextRel3.dAngle = autofitTextRel3.vAngle - AutofitTextRel.this.tAngle;
                    break;
                case 1:
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateUp(AutofitTextRel.this);
                        break;
                    }
                    break;
                case 2:
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateMove(AutofitTextRel.this);
                    }
                    AutofitTextRel autofitTextRel4 = AutofitTextRel.this;
                    autofitTextRel4.angle = (Math.atan2((double) (autofitTextRel4.cY - motionEvent.getRawY()), (double) (AutofitTextRel.this.cX - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (AutofitTextRel.this.angle + AutofitTextRel.this.dAngle));
                    ((View) view.getParent()).invalidate();
                    ((View) view.getParent()).requestLayout();
                    break;
            }
            return true;
        }
    }

    class C04073 implements OnTouchListener {
        C04073() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent motionEvent) {
            AutofitTextRel autofitTextRel = (AutofitTextRel) view.getParent();
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            LayoutParams layoutParams = (LayoutParams) AutofitTextRel.this.getLayoutParams();
            switch (motionEvent.getAction()) {
                case 0:
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleDown(AutofitTextRel.this);
                    }
                    AutofitTextRel.this.invalidate();
                    AutofitTextRel autofitTextRel2 = AutofitTextRel.this;
                    autofitTextRel2.basex = rawX;
                    autofitTextRel2.basey = rawY;
                    autofitTextRel2.basew = autofitTextRel2.getWidth();
                    AutofitTextRel autofitTextRel3 = AutofitTextRel.this;
                    autofitTextRel3.baseh = autofitTextRel3.getHeight();
                    AutofitTextRel.this.getLocationOnScreen(new int[2]);
                    AutofitTextRel.this.margl = layoutParams.leftMargin;
                    AutofitTextRel.this.margt = layoutParams.topMargin;
                    break;
                case 1:
                    AutofitTextRel autofitTextRel4 = AutofitTextRel.this;
                    autofitTextRel4.wi = autofitTextRel4.getLayoutParams().width;
                    AutofitTextRel autofitTextRel5 = AutofitTextRel.this;
                    autofitTextRel5.he = autofitTextRel5.getLayoutParams().height;
                    AutofitTextRel autofitTextRel6 = AutofitTextRel.this;
                    autofitTextRel6.leftMargin = ((LayoutParams) autofitTextRel6.getLayoutParams()).leftMargin;
                    AutofitTextRel autofitTextRel7 = AutofitTextRel.this;
                    autofitTextRel7.topMargin = ((LayoutParams) autofitTextRel7.getLayoutParams()).topMargin;
                    AutofitTextRel autofitTextRel8 = AutofitTextRel.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.valueOf(AutofitTextRel.this.leftMargin));
                    sb.append(",");
                    sb.append(String.valueOf(AutofitTextRel.this.topMargin));
                    autofitTextRel8.field_two = sb.toString();
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleUp(AutofitTextRel.this);
                        break;
                    }
                    break;
                case 2:
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleMove(AutofitTextRel.this);
                    }
                    float degrees = (float) Math.toDegrees(Math.atan2((double) (rawY - AutofitTextRel.this.basey), (double) (rawX - AutofitTextRel.this.basex)));
                    if (degrees < 0.0f) {
                        degrees += 360.0f;
                    }
                    int i = rawX - AutofitTextRel.this.basex;
                    int i2 = rawY - AutofitTextRel.this.basey;
                    int i3 = i2 * i2;
                    int sqrt = (int) (Math.sqrt((double) ((i * i) + i3)) * Math.cos(Math.toRadians((double) (degrees - AutofitTextRel.this.getRotation()))));
                    int sqrt2 = (int) (Math.sqrt((double) ((sqrt * sqrt) + i3)) * Math.sin(Math.toRadians((double) (degrees - AutofitTextRel.this.getRotation()))));
                    int i4 = (sqrt * 2) + AutofitTextRel.this.basew;
                    int i5 = (sqrt2 * 2) + AutofitTextRel.this.baseh;
                    if (i4 > AutofitTextRel.this.f24s) {
                        layoutParams.width = i4;
                        layoutParams.leftMargin = AutofitTextRel.this.margl - sqrt;
                    }
                    if (i5 > AutofitTextRel.this.f24s) {
                        layoutParams.height = i5;
                        layoutParams.topMargin = AutofitTextRel.this.margt - sqrt2;
                    }
                    AutofitTextRel.this.setLayoutParams(layoutParams);
                    if (!AutofitTextRel.this.bgDrawable.equals("0")) {
                        AutofitTextRel autofitTextRel9 = AutofitTextRel.this;
                        autofitTextRel9.wi = autofitTextRel9.getLayoutParams().width;
                        AutofitTextRel autofitTextRel10 = AutofitTextRel.this;
                        autofitTextRel10.he = autofitTextRel10.getLayoutParams().height;
                        AutofitTextRel autofitTextRel11 = AutofitTextRel.this;
                        autofitTextRel11.setBgDrawable(autofitTextRel11.bgDrawable);
                        break;
                    }
                    break;
            }
            return true;
        }
    }

    class C04084 extends SimpleOnGestureListener {
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return true;
        }

        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        C04084() {
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (AutofitTextRel.this.listener != null) {
                AutofitTextRel.this.listener.onDoubleTap();
            }
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            super.onLongPress(motionEvent);
        }
    }

    public interface TouchEventListener {
        void onDelete();

        void onDoubleTap();

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

    public AutofitTextRel setOnTouchCallbackListener(TouchEventListener touchEventListener) {
        this.listener = touchEventListener;
        return this;
    }

    public AutofitTextRel(Context context2) {
        super(context2);
        init(context2);
    }

    public AutofitTextRel(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        init(context2);
    }

    public AutofitTextRel(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        init(context2);
    }

    public void init(Context context2) {
        this.context = context2;
        Display defaultDisplay = ((Activity) this.context).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.width = point.x;
        this.height = point.y;
        this.ratio = ((float) this.width) / ((float) this.height);
        this.text_iv = new AutoResizeTextView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.background_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.rotate_iv = new ImageView(this.context);
        this.f24s = dpToPx(this.context, 30);
        this.wi = dpToPx(this.context, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.he = dpToPx(this.context, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.scale_iv.setImageResource(R.drawable.textlib_scale);
        this.background_iv.setImageResource(0);
        this.rotate_iv.setImageResource(R.drawable.rotate);
        this.delete_iv.setImageResource(R.drawable.textlib_clear);
        LayoutParams layoutParams = new LayoutParams(this.wi, this.he);
        int i = this.f24s;
        LayoutParams layoutParams2 = new LayoutParams(i, i);
        layoutParams2.addRule(12);
        layoutParams2.addRule(11);
        layoutParams2.setMargins(5, 5, 5, 5);
        int i2 = this.f24s;
        LayoutParams layoutParams3 = new LayoutParams(i2, i2);
        layoutParams3.addRule(12);
        layoutParams3.addRule(9);
        layoutParams3.setMargins(5, 5, 5, 5);
        LayoutParams layoutParams4 = new LayoutParams(-1, -1);
        layoutParams4.setMargins(5, 5, 5, 5);
        layoutParams4.addRule(17);
        int i3 = this.f24s;
        LayoutParams layoutParams5 = new LayoutParams(i3, i3);
        layoutParams5.addRule(10);
        layoutParams5.addRule(9);
        layoutParams5.setMargins(5, 5, 5, 5);
        LayoutParams layoutParams6 = new LayoutParams(-1, -1);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        setBackgroundResource(R.drawable.textlib_border_gray);
        addView(this.background_iv);
        this.background_iv.setLayoutParams(layoutParams7);
        this.background_iv.setScaleType(ScaleType.FIT_XY);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(layoutParams6);
        this.border_iv.setTag("border_iv");
        addView(this.text_iv);
        this.text_iv.setText(this.text);
        this.text_iv.setTextColor(this.tColor);
        this.text_iv.setTextSize(400.0f);
        this.text_iv.setLayoutParams(layoutParams4);
        this.text_iv.setGravity(17);
        this.text_iv.setMinTextSize(10.0f);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(layoutParams5);
        this.delete_iv.setOnClickListener(new C04051());
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(layoutParams3);
        this.rotate_iv.setOnTouchListener(this.rTouchListener);
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(layoutParams2);
        this.scale_iv.setTag("scale_iv");
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_anim);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_in);
        initGD();
        this.isMultiTouchEnabled = setDefaultTouchListener(true);
    }

    public boolean setDefaultTouchListener(boolean z) {
        if (z) {
            setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this).setGestureListener(this.gd));
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
            this.delete_iv.setVisibility(View.GONE);
            this.rotate_iv.setVisibility(View.GONE);
            setBackgroundResource(0);
        } else if (this.border_iv.getVisibility() != View.VISIBLE) {
            this.border_iv.setVisibility(View.VISIBLE);
            this.scale_iv.setVisibility(View.VISIBLE);
            this.delete_iv.setVisibility(View.VISIBLE);
            this.rotate_iv.setVisibility(View.VISIBLE);
            setBackgroundResource(R.drawable.textlib_border_gray);
            this.text_iv.startAnimation(this.scale);
        }
    }

    public boolean getBorderVisibility() {
        return this.isBorderVisible;
    }

    public void setText(String str) {
        this.text_iv.setText(str);
        this.text = str;
        this.text_iv.startAnimation(this.zoomOutScale);
    }

    public String getText() {
        return this.text_iv.getText().toString();
    }

    public void setTextFont(String str) {
        Typeface typeface;
        try {
            if (!str.equals("default")) {
                if (!str.equals("")) {
                    typeface = Typeface.createFromAsset(this.context.getAssets(), str);
                    this.text_iv.setTypeface(typeface);
                    this.fontName = str;
                    return;
                }
            }
            typeface = Typeface.DEFAULT;
            this.text_iv.setTypeface(typeface);
            this.fontName = str;
        } catch (Exception unused) {
        }
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setTextColor(int i) {
        this.text_iv.setTextColor(i);
        this.tColor = i;
    }

    public int getTextColor() {
        return this.tColor;
    }

    public void setTextAlpha(int i) {
        this.text_iv.setAlpha(((float) i) / 100.0f);
        this.tAlpha = i;
    }

    public int getTextAlpha() {
        return this.tAlpha;
    }

    public void setTextShadowColor(int i) {
        this.shadowColor = i;
        this.text_iv.setShadowLayer((float) this.shadowProg, 0.0f, 0.0f, this.shadowColor);
    }

    public int getTextShadowColor() {
        return this.shadowColor;
    }

    public void setTextShadowProg(int i) {
        this.shadowProg = i;
        this.text_iv.setShadowLayer((float) this.shadowProg, 0.0f, 0.0f, this.shadowColor);
    }

    public int getTextShadowProg() {
        return this.shadowProg;
    }

    public void setBgDrawable(String str) {
        this.bgDrawable = str;
        this.bgColor = 0;
        this.background_iv.setImageBitmap(getTiledBitmap(this.context, getResources().getIdentifier(str, "drawable", this.context.getPackageName()), this.wi, this.he));
        this.background_iv.setBackgroundColor(this.bgColor);
    }

    public String getBgDrawable() {
        return this.bgDrawable;
    }

    public void setBgColor(int i) {
        this.bgDrawable = "0";
        this.bgColor = i;
        this.background_iv.setImageBitmap(null);
        this.background_iv.setBackgroundColor(i);
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public void setBgAlpha(int i) {
        this.background_iv.setAlpha(((float) i) / 255.0f);
        this.bgAlpha = i;
    }

    public int getBgAlpha() {
        return this.bgAlpha;
    }

    public TextInfo getTextInfo() {
        TextInfo textInfo = new TextInfo();
        textInfo.setPOS_X(getX());
        textInfo.setPOS_Y(getY());
        textInfo.setWIDTH(this.wi);
        textInfo.setHEIGHT(this.he);
        textInfo.setTEXT(this.text);
        textInfo.setFONT_NAME(this.fontName);
        textInfo.setTEXT_COLOR(this.tColor);
        textInfo.setTEXT_ALPHA(this.tAlpha);
        textInfo.setSHADOW_COLOR(this.shadowColor);
        textInfo.setSHADOW_PROG(this.shadowProg);
        textInfo.setBG_COLOR(this.bgColor);
        textInfo.setBG_DRAWABLE(this.bgDrawable);
        textInfo.setBG_ALPHA(this.bgAlpha);
        textInfo.setROTATION(getRotation());
        textInfo.setXRotateProg(this.xRotateProg);
        textInfo.setYRotateProg(this.yRotateProg);
        textInfo.setZRotateProg(this.zRotateProg);
        textInfo.setCurveRotateProg(this.progress);
        textInfo.setFIELD_ONE(this.field_one);
        textInfo.setFIELD_TWO(this.field_two);
        textInfo.setFIELD_THREE(this.field_three);
        textInfo.setFIELD_FOUR(this.field_four);
        return textInfo;
    }

    public void setTextInfo(TextInfo textInfo, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(textInfo.getPOS_X());
        sb.append(" ,");
        sb.append(textInfo.getPOS_Y());
        sb.append(" ,");
        sb.append(textInfo.getWIDTH());
        sb.append(" ,");
        sb.append(textInfo.getHEIGHT());
        sb.append(" ,");
        sb.append(textInfo.getFIELD_TWO());
        Log.e("set Text value", sb.toString());
        this.wi = textInfo.getWIDTH();
        this.he = textInfo.getHEIGHT();
        this.text = textInfo.getTEXT();
        this.fontName = textInfo.getFONT_NAME();
        this.tColor = textInfo.getTEXT_COLOR();
        this.tAlpha = textInfo.getTEXT_ALPHA();
        this.shadowColor = textInfo.getSHADOW_COLOR();
        this.shadowProg = textInfo.getSHADOW_PROG();
        this.bgColor = textInfo.getBG_COLOR();
        this.bgDrawable = textInfo.getBG_DRAWABLE();
        this.bgAlpha = textInfo.getBG_ALPHA();
        this.rotation = textInfo.getROTATION();
        this.field_two = textInfo.getFIELD_TWO();
        setText(this.text);
        setTextFont(this.fontName);
        setTextColor(this.tColor);
        setTextAlpha(this.tAlpha);
        setTextShadowColor(this.shadowColor);
        setTextShadowProg(this.shadowProg);
        int i = this.bgColor;
        if (i != 0) {
            setBgColor(i);
        } else {
            this.background_iv.setBackgroundColor(0);
        }
        if (this.bgDrawable.equals("0")) {
            this.background_iv.setImageBitmap(null);
        } else {
            setBgDrawable(this.bgDrawable);
        }
        setBgAlpha(this.bgAlpha);
        setRotation(textInfo.getROTATION());
        if (this.field_two.equals("")) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(textInfo.getPOS_X());
            setY(textInfo.getPOS_Y());
            return;
        }
        String[] split = this.field_two.split(",");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
        ((LayoutParams) getLayoutParams()).topMargin = parseInt2;
        getLayoutParams().width = this.wi;
        getLayoutParams().height = this.he;
        setX(textInfo.getPOS_X() + ((float) (parseInt * -1)));
        setY(textInfo.getPOS_Y() + ((float) (parseInt2 * -1)));
    }

    public void optimize(float f, float f2) {
        setX(getX() * f);
        setY(getY() * f2);
        getLayoutParams().width = (int) (((float) this.wi) * f);
        getLayoutParams().height = (int) (((float) this.he) * f2);
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

    public int dpToPx(Context context2, int i) {
        float f = (float) i;
        context2.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }

    private Bitmap getTiledBitmap(Context context2, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context2.getResources(), i, new Options()), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    private void initGD() {
        this.gd = new GestureDetector(this.context, new C04084());
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

    public float getNewX(float f) {
        return ((float) this.width) * (f / ((float) this.sw));
    }

    public float getNewY(float f) {
        return ((float) this.height) * (f / ((float) this.sh));
    }
}