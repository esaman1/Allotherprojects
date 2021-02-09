package postermaster.postermaker.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

import postermaster.postermaker.R;
import postermaster.postermaker.utility.ExifUtils;
import postermaster.postermaker.utility.ImageUtils;

public class Constants {
    static int DesignerScreenHeight = 1519;
    static int DesignerScreenWidth = 1080;
    public static int[] Imageid0 = {R.drawable.b16, R.drawable.b17, R.drawable.b18, R.drawable.b19, R.drawable.b20, R.drawable.b21, R.drawable.b22, R.drawable.b23, R.drawable.b24, R.drawable.b25, R.drawable.b26, R.drawable.b27, R.drawable.b28, R.drawable.b29, R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5, R.drawable.b6, R.drawable.b7, R.drawable.b8, R.drawable.b9, R.drawable.b10, R.drawable.b11, R.drawable.b12, R.drawable.b13, R.drawable.b14, R.drawable.b15, R.drawable.b30, R.drawable.b31, R.drawable.b32, R.drawable.b33, R.drawable.b34, R.drawable.b35, R.drawable.b36, R.drawable.b37, R.drawable.b38, R.drawable.b39, R.drawable.b40};
    public static int[] Imageid1 = {R.drawable.t12, R.drawable.t13, R.drawable.t14, R.drawable.t15, R.drawable.t16, R.drawable.t17, R.drawable.t18, R.drawable.t19, R.drawable.t20, R.drawable.t21, R.drawable.t22, R.drawable.t23, R.drawable.t24, R.drawable.t25, R.drawable.t26, R.drawable.t27, R.drawable.t28, R.drawable.t29, R.drawable.t30, R.drawable.t31, R.drawable.t32, R.drawable.t33, R.drawable.t34, R.drawable.t35, R.drawable.t36, R.drawable.t37, R.drawable.t38, R.drawable.t39, R.drawable.t40, R.drawable.t1, R.drawable.t2, R.drawable.t3, R.drawable.t4, R.drawable.t5, R.drawable.t6, R.drawable.t7, R.drawable.t8, R.drawable.t9, R.drawable.t10, R.drawable.t11};
    public static int[] Imageid_st1 = {R.drawable.a_1, R.drawable.a_2, R.drawable.a_3, R.drawable.a_4, R.drawable.a_5, R.drawable.a_6, R.drawable.a_7, R.drawable.a_8, R.drawable.a_9, R.drawable.a_10, R.drawable.a_11, R.drawable.a_12, R.drawable.a_13, R.drawable.a_14, R.drawable.a_15, R.drawable.a_16, R.drawable.a_17, R.drawable.a_18, R.drawable.a_19, R.drawable.a_20, R.drawable.a_21, R.drawable.a_22, R.drawable.a_23, R.drawable.a_24, R.drawable.a_25};
    public static int[] Imageid_st10 = {R.drawable.j_1, R.drawable.j_2, R.drawable.j_3, R.drawable.j_4, R.drawable.j_5, R.drawable.j_6, R.drawable.j_7, R.drawable.j_8, R.drawable.j_9, R.drawable.j_10, R.drawable.j_11, R.drawable.j_12, R.drawable.j_13, R.drawable.j_14};
    public static int[] Imageid_st11 = {R.drawable.k_1, R.drawable.k_2, R.drawable.k_3, R.drawable.k_4, R.drawable.k_5, R.drawable.k_6, R.drawable.k_7, R.drawable.k_8, R.drawable.k_9, R.drawable.k_10, R.drawable.k_11, R.drawable.k_12, R.drawable.k_13, R.drawable.k_14, R.drawable.k_15, R.drawable.k_16, R.drawable.k_17, R.drawable.k_18, R.drawable.k_19, R.drawable.k_20, R.drawable.k_21, R.drawable.k_22, R.drawable.k_23, R.drawable.k_24, R.drawable.k_25, R.drawable.k_26, R.drawable.k_27, R.drawable.k_28, R.drawable.k_29, R.drawable.k_30, R.drawable.k_31, R.drawable.k_32, R.drawable.k_33, R.drawable.k_34, R.drawable.k_35, R.drawable.k_36, R.drawable.k_37};
    public static int[] Imageid_st12 = {R.drawable.l_1, R.drawable.l_2, R.drawable.l_3, R.drawable.l_4, R.drawable.l_5, R.drawable.l_6, R.drawable.l_7, R.drawable.l_8, R.drawable.l_9, R.drawable.l_10, R.drawable.l_11, R.drawable.l_12};
    public static int[] Imageid_st13 = {R.drawable.m_1, R.drawable.m_2, R.drawable.m_3, R.drawable.m_4, R.drawable.m_5, R.drawable.m_6, R.drawable.m_7, R.drawable.m_8, R.drawable.m_9, R.drawable.m_10, R.drawable.m_11, R.drawable.m_12, R.drawable.m_13, R.drawable.m_14, R.drawable.m_15, R.drawable.m_16, R.drawable.m_17, R.drawable.m_18};
    public static int[] Imageid_st14 = {R.drawable.n_1, R.drawable.n_2, R.drawable.n_3, R.drawable.n_4, R.drawable.n_5, R.drawable.n_6, R.drawable.n_7, R.drawable.n_8, R.drawable.n_9, R.drawable.n_10, R.drawable.n_11};
    public static int[] Imageid_st15 = {R.drawable.o_1, R.drawable.o_2, R.drawable.o_3, R.drawable.o_4, R.drawable.o_5, R.drawable.o_6, R.drawable.o_7, R.drawable.o_8, R.drawable.o_9, R.drawable.o_10, R.drawable.o_11, R.drawable.o_12, R.drawable.o_13, R.drawable.o_14, R.drawable.o_15};
    public static int[] Imageid_st16 = {R.drawable.p_1, R.drawable.p_2, R.drawable.p_3, R.drawable.p_4, R.drawable.p_5, R.drawable.p_6, R.drawable.p_7, R.drawable.p_8, R.drawable.p_9, R.drawable.p_10, R.drawable.p_11, R.drawable.p_12, R.drawable.p_13, R.drawable.p_14, R.drawable.p_15, R.drawable.p_16};
    public static int[] Imageid_st17 = {R.drawable.q_1, R.drawable.q_2, R.drawable.q_3, R.drawable.q_4, R.drawable.q_5, R.drawable.q_6, R.drawable.q_7, R.drawable.q_8, R.drawable.q_9, R.drawable.q_10};
    public static int[] Imageid_st18 = {R.drawable.r_1, R.drawable.r_2, R.drawable.r_3, R.drawable.r_4, R.drawable.r_5, R.drawable.r_6, R.drawable.r_7, R.drawable.r_8, R.drawable.r_9, R.drawable.r_10, R.drawable.r_11, R.drawable.r_12, R.drawable.r_13, R.drawable.r_14, R.drawable.r_15, R.drawable.r_16, R.drawable.r_17, R.drawable.r_18, R.drawable.r_19, R.drawable.r_20, R.drawable.r_21, R.drawable.r_22, R.drawable.r_23, R.drawable.r_24, R.drawable.r_25, R.drawable.r_26, R.drawable.r_27};
    public static int[] Imageid_st19 = {R.drawable.s_1, R.drawable.s_2, R.drawable.s_3, R.drawable.s_4, R.drawable.s_5, R.drawable.s_6, R.drawable.s_7, R.drawable.s_8, R.drawable.s_9, R.drawable.s_10, R.drawable.s_11, R.drawable.s_12, R.drawable.s_13, R.drawable.s_14, R.drawable.s_15, R.drawable.s_16, R.drawable.s_17, R.drawable.s_18, R.drawable.s_19, R.drawable.s_20, R.drawable.s_21, R.drawable.s_22, R.drawable.s_23, R.drawable.s_24, R.drawable.s_25, R.drawable.s_26, R.drawable.s_27, R.drawable.s_28, R.drawable.s_29, R.drawable.s_30, R.drawable.s_31, R.drawable.s_32, R.drawable.s_33, R.drawable.s_34, R.drawable.s_35, R.drawable.s_36};
    public static int[] Imageid_st2 = {R.drawable.b_1, R.drawable.b_2, R.drawable.b_3, R.drawable.b_4, R.drawable.b_5, R.drawable.b_6, R.drawable.b_7, R.drawable.b_8, R.drawable.b_9, R.drawable.b_10, R.drawable.b_11, R.drawable.b_12, R.drawable.b_13, R.drawable.b_14, R.drawable.b_15, R.drawable.b_16, R.drawable.b_17, R.drawable.b_18, R.drawable.b_19, R.drawable.b_20, R.drawable.b_21, R.drawable.b_22, R.drawable.b_23, R.drawable.b_24, R.drawable.b_25, R.drawable.b_26};
    public static int[] Imageid_st20 = {R.drawable.t_1, R.drawable.t_2, R.drawable.t_3, R.drawable.t_4, R.drawable.t_5, R.drawable.t_6, R.drawable.t_7, R.drawable.t_8, R.drawable.t_9, R.drawable.t_10, R.drawable.t_11, R.drawable.t_12, R.drawable.t_13, R.drawable.t_14, R.drawable.t_15, R.drawable.t_16, R.drawable.t_17, R.drawable.t_18, R.drawable.t_19, R.drawable.t_20, R.drawable.t_21, R.drawable.t_22, R.drawable.t_23, R.drawable.t_24, R.drawable.t_25, R.drawable.t_26, R.drawable.t_27, R.drawable.t_28, R.drawable.t_29, R.drawable.t_30, R.drawable.t_31, R.drawable.t_32, R.drawable.t_33, R.drawable.t_34};
    public static int[] Imageid_st21 = {R.drawable.u_1, R.drawable.u_2, R.drawable.u_3, R.drawable.u_4, R.drawable.u_5, R.drawable.u_6, R.drawable.u_7, R.drawable.u_8, R.drawable.u_9, R.drawable.u_10, R.drawable.u_11, R.drawable.u_12, R.drawable.u_13, R.drawable.u_14, R.drawable.u_15, R.drawable.u_16, R.drawable.u_17, R.drawable.u_18, R.drawable.u_19, R.drawable.u_20, R.drawable.u_21, R.drawable.u_22, R.drawable.u_23, R.drawable.u_24, R.drawable.u_25, R.drawable.u_26, R.drawable.u_27, R.drawable.u_28, R.drawable.u_29, R.drawable.u_30, R.drawable.u_31, R.drawable.u_32, R.drawable.u_33, R.drawable.u_34, R.drawable.u_35, R.drawable.u_36, R.drawable.u_37, R.drawable.u_38, R.drawable.u_39, R.drawable.u_40, R.drawable.u_40, R.drawable.u_41, R.drawable.u_42};
    public static int[] Imageid_st22 = {R.drawable.t_1, R.drawable.t_2, R.drawable.t_3, R.drawable.t_4, R.drawable.t_5, R.drawable.t_6, R.drawable.t_7, R.drawable.t_8, R.drawable.t_9, R.drawable.t_10, R.drawable.t_11, R.drawable.t_12, R.drawable.t_13, R.drawable.t_14, R.drawable.t_15, R.drawable.t_16, R.drawable.t_17, R.drawable.t_18, R.drawable.t_19, R.drawable.t_20, R.drawable.t_21, R.drawable.t_22, R.drawable.t_23, R.drawable.t_24, R.drawable.t_25, R.drawable.t_26, R.drawable.t_27, R.drawable.t_28, R.drawable.t_29, R.drawable.t_30, R.drawable.t_31, R.drawable.t_32, R.drawable.t_33, R.drawable.t_34};
    public static int[] Imageid_st23 = {R.drawable.sh1, R.drawable.sh2, R.drawable.sh3, R.drawable.sh4, R.drawable.sh5, R.drawable.sh6, R.drawable.sh7, R.drawable.sh8, R.drawable.sh9, R.drawable.sh10, R.drawable.sh11, R.drawable.sh12, R.drawable.sh13, R.drawable.sh14, R.drawable.sh15, R.drawable.sh16, R.drawable.sh17, R.drawable.sh18, R.drawable.sh19, R.drawable.sh20, R.drawable.sh21, R.drawable.sh22, R.drawable.sh23, R.drawable.sh24, R.drawable.sh25, R.drawable.sh26, R.drawable.sh27, R.drawable.sh28, R.drawable.sh29, R.drawable.sh30, R.drawable.sh31, R.drawable.sh32, R.drawable.sh33, R.drawable.sh34, R.drawable.sh35, R.drawable.sh36, R.drawable.sh37, R.drawable.sh38, R.drawable.sh39, R.drawable.sh40, R.drawable.sh41, R.drawable.sh42};
    public static int[] Imageid_st3 = {R.drawable.c_1, R.drawable.c_2, R.drawable.c_3, R.drawable.c_4, R.drawable.c_5, R.drawable.c_6, R.drawable.c_7, R.drawable.c_8, R.drawable.c_9, R.drawable.c_10, R.drawable.c_11, R.drawable.c_12};
    public static int[] Imageid_st4 = {R.drawable.d_1, R.drawable.d_2, R.drawable.d_3, R.drawable.d_4, R.drawable.d_5, R.drawable.d_6, R.drawable.d_7, R.drawable.d_8, R.drawable.d_9, R.drawable.d_10, R.drawable.d_11, R.drawable.d_12, R.drawable.d_13, R.drawable.d_14, R.drawable.d_15, R.drawable.d_16, R.drawable.d_17, R.drawable.d_18};
    public static int[] Imageid_st5 = {R.drawable.e_1, R.drawable.e_2, R.drawable.e_3, R.drawable.e_4, R.drawable.e_5, R.drawable.e_6, R.drawable.e_7, R.drawable.e_8, R.drawable.e_9, R.drawable.e_10};
    public static int[] Imageid_st6 = {R.drawable.f_1, R.drawable.f_2, R.drawable.f_3, R.drawable.f_4, R.drawable.f_5, R.drawable.f_6, R.drawable.f_7, R.drawable.f_8, R.drawable.f_9, R.drawable.f_10, R.drawable.f_11, R.drawable.f_12, R.drawable.f_13, R.drawable.f_14, R.drawable.f_15, R.drawable.f_16, R.drawable.f_17, R.drawable.f_18, R.drawable.f_19, R.drawable.f_20, R.drawable.f_21, R.drawable.f_22, R.drawable.f_23, R.drawable.f_24, R.drawable.f_25, R.drawable.f_26, R.drawable.f_27};
    public static int[] Imageid_st7 = {R.drawable.g_1, R.drawable.g_2, R.drawable.g_3, R.drawable.g_4, R.drawable.g_5, R.drawable.g_6, R.drawable.g_7, R.drawable.g_8, R.drawable.g_9, R.drawable.g_10, R.drawable.g_11, R.drawable.g_12, R.drawable.g_13, R.drawable.g_14, R.drawable.g_15, R.drawable.g_16, R.drawable.g_17, R.drawable.g_18, R.drawable.g_19, R.drawable.g_20, R.drawable.g_21, R.drawable.g_22, R.drawable.g_23, R.drawable.g_24, R.drawable.g_25, R.drawable.g_26, R.drawable.g_27, R.drawable.g_28, R.drawable.g_29, R.drawable.g_30, R.drawable.g_31, R.drawable.g_32, R.drawable.g_33, R.drawable.g_34, R.drawable.g_35, R.drawable.g_36};
    public static int[] Imageid_st8 = {R.drawable.h_1, R.drawable.h_2, R.drawable.h_3, R.drawable.h_4, R.drawable.h_5, R.drawable.h_6, R.drawable.h_7, R.drawable.h_8, R.drawable.h_9, R.drawable.h_10, R.drawable.h_11, R.drawable.h_12, R.drawable.h_13};
    public static int[] Imageid_st9 = {R.drawable.i_1, R.drawable.i_2, R.drawable.i_3, R.drawable.i_4, R.drawable.i_5, R.drawable.i_6, R.drawable.i_7, R.drawable.i_8, R.drawable.i_9, R.drawable.i_10, R.drawable.i_11, R.drawable.i_12, R.drawable.i_13, R.drawable.i_14, R.drawable.i_15, R.drawable.i_16, R.drawable.i_17};
    public static int aspectRatioHeight = 1;
    public static int aspectRatioWidth = 1;
    public static int currentScreenHeight = 1;
    public static int currentScreenWidth = 1;
    static int multiplier = 10000;
    public static int[] overlayArr = {R.drawable.o1, R.drawable.o2, R.drawable.o3, R.drawable.o4, R.drawable.o5, R.drawable.o6, R.drawable.o7, R.drawable.o8, R.drawable.o9, R.drawable.o10, R.drawable.o11, R.drawable.o12, R.drawable.o13, R.drawable.o14, R.drawable.o15, R.drawable.o16, R.drawable.o17, R.drawable.o18, R.drawable.o19, R.drawable.o20, R.drawable.o21, R.drawable.o22, R.drawable.o23, R.drawable.o24, R.drawable.o25, R.drawable.o26, R.drawable.o27, R.drawable.o28, R.drawable.o29, R.drawable.o30, R.drawable.o31};
    private static int sh = 1794;
    private static int sw = 1080;

    public static Bitmap merge(Bitmap bitmap, Bitmap bitmap2, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Drawable[] drawableArr = {new BitmapDrawable(bitmap), new BitmapDrawable(bitmap2)};
        drawableArr[1].setAlpha(i);
        LayerDrawable layerDrawable = new LayerDrawable(drawableArr);
        Canvas canvas = new Canvas(createBitmap);
        layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        layerDrawable.draw(canvas);
        return createBitmap;
    }

    public static CharSequence getSpannableString(Context context, Typeface typeface, int i) {
        SpannableStringBuilder append = new SpannableStringBuilder().append(new SpannableString(context.getResources().getString(i)));
        return append.subSequence(0, append.length());
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int i, int i2) {
        float f = (float) i;
        float f2 = (float) i2;
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        StringBuilder sb = new StringBuilder();
        sb.append(f);
        sb.append("  ");
        sb.append(f2);
        sb.append("  and  ");
        sb.append(width);
        sb.append("  ");
        sb.append(height);
        Log.i("testings", sb.toString());
        float f3 = width / height;
        float f4 = height / width;
        if (width > f) {
            float f5 = f * f4;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("if (wd > wr) ");
            sb2.append(f);
            sb2.append("  ");
            sb2.append(f5);
            Log.i("testings", sb2.toString());
            if (f5 > f2) {
                f = f2 * f3;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("  if (he > hr) ");
                sb3.append(f);
                sb3.append("  ");
                sb3.append(f2);
                Log.i("testings", sb3.toString());
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(" in else ");
                sb4.append(f);
                sb4.append("  ");
                sb4.append(f5);
                Log.i("testings", sb4.toString());
                f2 = f5;
            }
        } else if (height > f2) {
            float f6 = f2 * f3;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("  if (he > hr) ");
            sb5.append(f6);
            sb5.append("  ");
            sb5.append(f2);
            Log.i("testings", sb5.toString());
            if (f6 > f) {
                f2 = f * f4;
            } else {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(" in else ");
                sb6.append(f6);
                sb6.append("  ");
                sb6.append(f2);
                Log.i("testings", sb6.toString());
                f = f6;
            }
        } else if (f3 > 0.75f) {
            f2 = f * f4;
            Log.i("testings", " if (rat1 > .75f) ");
        } else if (f4 > 1.5f) {
            f = f2 * f3;
            Log.i("testings", " if (rat2 > 1.5f) ");
        } else {
            float f7 = f * f4;
            Log.i("testings", " in else ");
            if (f7 > f2) {
                f = f2 * f3;
                StringBuilder sb7 = new StringBuilder();
                sb7.append("  if (he > hr) ");
                sb7.append(f);
                sb7.append("  ");
                sb7.append(f2);
                Log.i("testings", sb7.toString());
            } else {
                StringBuilder sb8 = new StringBuilder();
                sb8.append(" in else ");
                sb8.append(f);
                sb8.append("  ");
                sb8.append(f7);
                Log.i("testings", sb8.toString());
                f2 = f7;
            }
        }
        return Bitmap.createScaledBitmap(bitmap, (int) f, (int) f2, false);
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri, float f, float f2) throws IOException {
        try {
            ParcelFileDescriptor openFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = openFileDescriptor.getFileDescriptor();
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            Options options2 = new Options();
            if (f <= f2) {
                f = f2;
            }
            int i = (int) f;
            options2.inSampleSize = ImageUtils.getClosestResampleSize(options.outWidth, options.outHeight, i);
            Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options2);
            Matrix matrix = new Matrix();
            if (decodeFileDescriptor.getWidth() > i || decodeFileDescriptor.getHeight() > i) {
                Options resampling = ImageUtils.getResampling(decodeFileDescriptor.getWidth(), decodeFileDescriptor.getHeight(), i);
                matrix.postScale(((float) resampling.outWidth) / ((float) decodeFileDescriptor.getWidth()), ((float) resampling.outHeight) / ((float) decodeFileDescriptor.getHeight()));
            }
            String realPathFromURI = ImageUtils.getRealPathFromURI(uri, context);
            if (new Integer(VERSION.SDK).intValue() > 4) {
                int exifRotation = ExifUtils.getExifRotation(realPathFromURI);
                if (exifRotation != 0) {
                    matrix.postRotate((float) exifRotation);
                }
            }
            Bitmap createBitmap = Bitmap.createBitmap(decodeFileDescriptor, 0, 0, decodeFileDescriptor.getWidth(), decodeFileDescriptor.getHeight(), matrix, true);
            openFileDescriptor.close();
            return createBitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getClosestResampleSize(int i, int i2, int i3) {
        int max = Math.max(i, i2);
        int i4 = 1;
        while (true) {
            if (i4 >= Integer.MAX_VALUE) {
                break;
            } else if (i4 * i3 > max) {
                i4--;
                break;
            } else {
                i4++;
            }
        }
        if (i4 > 0) {
            return i4;
        }
        return 1;
    }

    public static Typeface getHeaderTypeface(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "font6.ttf");
    }

    public static Typeface getTextTypeface(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "VERDANA.ttf");
    }

    public static Typeface getTextTypefaceFont(Activity activity, String str) {
        return Typeface.createFromAsset(activity.getAssets(), str);
    }

    public static Animation getAnimUp(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.slide_up);
    }

    public static Animation getAnimDown(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.slide_down);
    }

    public static Animation getAnimUpDown(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.left_right_slide);
    }

    public static Animation getAnimDownUp(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.right_left_slide);
    }

    public static File getSaveFileLocation(String str) {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        StringBuilder sb = new StringBuilder();
        sb.append(".Poster Maker Stickers/");
        sb.append(str);
        return new File(externalStoragePublicDirectory, sb.toString());
    }

    public static boolean saveBitmapObject(Activity activity, Bitmap bitmap, String str) {
        Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        File file = new File(str);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            boolean compress = copy.compress(CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            copy.recycle();
            activity.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
            return compress;
        } catch (Exception e) {
            e.printStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("Exception");
            sb.append(e.getMessage());
            Log.i("testing", sb.toString());
            return false;
        }
    }

    public static String saveBitmapObject1(Bitmap bitmap) {
        File saveFileLocation = getSaveFileLocation("category1");
        saveFileLocation.mkdirs();
        StringBuilder sb = new StringBuilder();
        sb.append("raw1-");
        sb.append(System.currentTimeMillis());
        sb.append(".png");
        File file = new File(saveFileLocation, sb.toString());
        String absolutePath = file.getAbsolutePath();
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
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

    public static String saveBitmapObject(Activity activity, Bitmap bitmap) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Maker Stickers/Mydesigns");
        file.mkdirs();
        StringBuilder sb = new StringBuilder();
        sb.append("thumb-");
        sb.append(System.currentTimeMillis());
        sb.append(".png");
        File file2 = new File(file, sb.toString());
        if (file2.exists()) {
            file2.delete();
        }
        try {
            bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(file2));
            return file2.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Exception");
            sb2.append(e.getMessage());
            Log.i("MAINACTIVITY", sb2.toString());
            Toast.makeText(activity, activity.getResources().getString(R.string.save_err), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap guidelines_bitmap(Activity activity, int i, int i2) {
        Activity activity2 = activity;
        int i3 = i;
        int i4 = i2;
        Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(-1);
        paint.setStrokeWidth((float) ImageUtils.dpToPx(activity2, 2));
        paint.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f}, 1.0f));
        paint.setStyle(Style.STROKE);
        Paint paint2 = new Paint();
        paint2.setColor(-16777216);
        paint2.setStrokeWidth((float) ImageUtils.dpToPx(activity2, 2));
        paint2.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f}, 1.0f));
        paint2.setStyle(Style.STROKE);
        int i5 = i3 / 4;
        float f = (float) i5;
        float f2 = (float) i4;
        Canvas canvas2 = canvas;
        float f3 = f2;
        Paint paint3 = paint;
        canvas2.drawLine(f, 0.0f, f, f3, paint3);
        int i6 = i3 / 2;
        float f4 = (float) i6;
        canvas2.drawLine(f4, 0.0f, f4, f3, paint3);
        int i7 = (i3 * 3) / 4;
        float f5 = (float) i7;
        Canvas canvas3 = canvas;
        int i8 = i7;
        canvas3.drawLine(f5, 0.0f, f5, f3, paint);
        int i9 = i4 / 4;
        float f6 = (float) i9;
        float f7 = (float) i3;
        float f8 = f7;
        int i10 = i9;
        canvas3.drawLine(0.0f, f6, f8, f6, paint);
        int i11 = i4 / 2;
        float f9 = (float) i11;
        int i12 = i11;
        Paint paint4 = paint;
        canvas3.drawLine(0.0f, f9, f8, f9, paint4);
        int i13 = (i4 * 3) / 4;
        float f10 = (float) i13;
        canvas3.drawLine(0.0f, f10, f8, f10, paint4);
        float f11 = (float) (i5 + 2);
        float f12 = f2;
        Paint paint5 = paint2;
        canvas3.drawLine(f11, 0.0f, f11, f12, paint5);
        float f13 = (float) (i6 + 2);
        canvas3.drawLine(f13, 0.0f, f13, f12, paint5);
        float f14 = (float) (i8 + 2);
        canvas3.drawLine(f14, 0.0f, f14, f12, paint2);
        float f15 = (float) (i10 + 2);
        float f16 = f7;
        canvas3.drawLine(0.0f, f15, f16, f15, paint2);
        float f17 = (float) (i12 + 2);
        Paint paint6 = paint2;
        canvas3.drawLine(0.0f, f17, f16, f17, paint6);
        float f18 = (float) (i13 + 2);
        canvas3.drawLine(0.0f, f18, f16, f18, paint6);
        return createBitmap;
    }

    public static Bitmap getTiledBitmap(Activity activity, int i, Bitmap bitmap, SeekBar seekBar) {
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint();
        int progress = seekBar.getProgress() + 10;
        Options options = new Options();
        options.inScaled = false;
        paint.setShader(new BitmapShader(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), i, options), progress, progress, true), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    public static Bitmap getTiledBitmap(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, new Options()), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    public static float[] getOptimumSize(int i, int i2, int i3, int i4) {
        float f = (float) i3;
        float f2 = (float) i4;
        float f3 = (float) i;
        float f4 = (float) i2;
        StringBuilder sb = new StringBuilder();
        sb.append(f);
        sb.append("  ");
        sb.append(f2);
        sb.append("  and  ");
        sb.append(f3);
        sb.append("  ");
        sb.append(f4);
        Log.i("testings", sb.toString());
        float f5 = f3 / f4;
        float f6 = f4 / f3;
        if (f3 > f) {
            float f7 = f * f6;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("if (wd > wr) ");
            sb2.append(f);
            sb2.append("  ");
            sb2.append(f7);
            Log.i("testings", sb2.toString());
            if (f7 > f2) {
                f = f2 * f5;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("  if (he > hr) ");
                sb3.append(f);
                sb3.append("  ");
                sb3.append(f2);
                Log.i("testings", sb3.toString());
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(" in else ");
                sb4.append(f);
                sb4.append("  ");
                sb4.append(f7);
                Log.i("testings", sb4.toString());
                f2 = f7;
            }
        } else if (f4 > f2) {
            float f8 = f2 * f5;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("  if (he > hr) ");
            sb5.append(f8);
            sb5.append("  ");
            sb5.append(f2);
            Log.i("testings", sb5.toString());
            if (f8 > f) {
                f2 = f * f6;
            } else {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(" in else ");
                sb6.append(f8);
                sb6.append("  ");
                sb6.append(f2);
                Log.i("testings", sb6.toString());
                f = f8;
            }
        } else if (f5 > 0.75f) {
            f2 = f * f6;
            Log.i("testings", " if (rat1 > .75f) ");
        } else if (f6 > 1.5f) {
            f = f2 * f5;
            Log.i("testings", " if (rat2 > 1.5f) ");
        } else {
            float f9 = f * f6;
            Log.i("testings", " in else ");
            if (f9 > f2) {
                f = f2 * f5;
                StringBuilder sb7 = new StringBuilder();
                sb7.append("  if (he > hr) ");
                sb7.append(f);
                sb7.append("  ");
                sb7.append(f2);
                Log.i("testings", sb7.toString());
            } else {
                StringBuilder sb8 = new StringBuilder();
                sb8.append(" in else ");
                sb8.append(f);
                sb8.append("  ");
                sb8.append(f9);
                Log.i("testings", sb8.toString());
                f2 = f9;
            }
        }
        return new float[]{f, f2};
    }

    public static float getNewX(float f) {
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = getOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        int i3 = aspectRatioWidth;
        int i4 = multiplier;
        float[] optimumSize2 = getOptimumSize(i3 * i4, aspectRatioHeight * i4, currentScreenWidth, currentScreenHeight);
        float f2 = optimumSize2[1];
        float f3 = optimumSize[1];
        return (optimumSize2[0] / optimumSize[0]) * f;
    }

    public static String getMargin(String str) {
        String[] split = str.split(",");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = getOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        int i3 = aspectRatioWidth;
        int i4 = multiplier;
        float[] optimumSize2 = getOptimumSize(i3 * i4, aspectRatioHeight * i4, currentScreenWidth, currentScreenHeight);
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf((int) (((float) parseInt) * (optimumSize2[0] / optimumSize[0]))));
        sb.append(",");
        sb.append(String.valueOf((int) (((float) parseInt2) * (optimumSize2[1] / optimumSize[1]))));
        return sb.toString();
    }

    public static float getNewY(float f) {
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = getOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        int i3 = aspectRatioWidth;
        int i4 = multiplier;
        float[] optimumSize2 = getOptimumSize(i3 * i4, aspectRatioHeight * i4, currentScreenWidth, currentScreenHeight);
        float f2 = optimumSize2[0];
        float f3 = optimumSize[0];
        return (optimumSize2[1] / optimumSize[1]) * f;
    }

    public static int getNewWidth(float f) {
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = getOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        int i3 = aspectRatioWidth;
        int i4 = multiplier;
        float[] optimumSize2 = getOptimumSize(i3 * i4, aspectRatioHeight * i4, currentScreenWidth, currentScreenHeight);
        float f2 = optimumSize2[0];
        float f3 = optimumSize[0];
        return (int) ((optimumSize2[1] / optimumSize[1]) * f);
    }

    public static int getNewHeight(float f) {
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = getOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        int i3 = aspectRatioWidth;
        int i4 = multiplier;
        float[] optimumSize2 = getOptimumSize(i3 * i4, aspectRatioHeight * i4, currentScreenWidth, currentScreenHeight);
        float f2 = optimumSize2[0];
        float f3 = optimumSize[0];
        return (int) ((optimumSize2[1] / optimumSize[1]) * f);
    }

    public static float getNewSize(Context context, float f) {
        return (context.getResources().getDisplayMetrics().density / 3.0f) * f;
    }
}
