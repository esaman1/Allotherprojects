package msl.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.Log;
import java.io.IOException;

public class ImageUtils {
    public static Bitmap getResampleImageBitmap(Uri uri, Context context, int i) throws IOException {
        try {
            return resampleImage(getRealPathFromURI(uri, context), i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint({"UseValueOf"})
    public static Bitmap resampleImage(String str, int i) throws Exception {
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            Options options2 = new Options();
            options2.inSampleSize = getClosestResampleSize(options.outWidth, options.outHeight, i);
            Bitmap decodeFile = BitmapFactory.decodeFile(str, options2);
            if (decodeFile == null) {
                return null;
            }
            Matrix matrix = new Matrix();
            if (decodeFile.getWidth() > i || decodeFile.getHeight() > i) {
                Options resampling = getResampling(decodeFile.getWidth(), decodeFile.getHeight(), i);
                matrix.postScale(((float) resampling.outWidth) / ((float) decodeFile.getWidth()), ((float) resampling.outHeight) / ((float) decodeFile.getHeight()));
            }
            if (new Integer(VERSION.SDK).intValue() > 4) {
                int exifRotation = ExifUtils.getExifRotation(str);
                if (exifRotation != 0) {
                    matrix.postRotate((float) exifRotation);
                }
            }
            return Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Options getResampling(int i, int i2, int i3) {
        Options options = new Options();
        float f = i > i2 ? ((float) i3) / ((float) i) : i2 > i ? ((float) i3) / ((float) i2) : ((float) i3) / ((float) i);
        options.outWidth = (int) ((((float) i) * f) + 0.5f);
        options.outHeight = (int) ((((float) i2) * f) + 0.5f);
        return options;
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

    public static Options getBitmapDims(Uri uri, Context context) {
        String realPathFromURI = getRealPathFromURI(uri, context);
        StringBuilder sb = new StringBuilder();
        sb.append("Path ");
        sb.append(realPathFromURI);
        Log.i("texting", sb.toString());
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(realPathFromURI, options);
        return options;
    }

    public static String getRealPathFromURI(Uri uri, Context context) {
        try {
            Cursor query = context.getContentResolver().query(uri, null, null, null, null);
            if (query == null) {
                return uri.getPath();
            }
            query.moveToFirst();
            String string = query.getString(query.getColumnIndex("_data"));
            query.close();
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return uri.toString();
        }
    }

    public static int[] getResizeDim(float f, float f2, int i, int i2) {
        float f3 = (float) i;
        float f4 = (float) i2;
        float f5 = f / f2;
        float f6 = f2 / f;
        if (f > f3) {
            float f7 = f3 * f6;
            if (f7 > f4) {
                f3 = f4 * f5;
            } else {
                f4 = f7;
            }
        } else if (f2 > f4) {
            float f8 = f4 * f5;
            if (f8 > f3) {
                f4 = f3 * f6;
            } else {
                f3 = f8;
            }
        } else if (f5 > 0.75f) {
            float f9 = f3 * f6;
            if (f9 > f4) {
                f3 = f4 * f5;
            } else {
                f4 = f9;
            }
        } else if (f6 > 1.5f) {
            float f10 = f4 * f5;
            if (f10 > f3) {
                f4 = f3 * f6;
            } else {
                f3 = f10;
            }
        } else {
            float f11 = f3 * f6;
            if (f11 > f4) {
                f3 = f4 * f5;
            } else {
                f4 = f11;
            }
        }
        return new int[]{(int) f3, (int) f4};
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        float f = (float) i;
        float f2 = (float) i2;
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        float f3 = width / height;
        float f4 = height / width;
        if (width > f) {
            float f5 = f * f4;
            if (f5 > f2) {
                f = f2 * f3;
            } else {
                f2 = f5;
            }
        } else if (height > f2) {
            float f6 = f2 * f3;
            if (f6 > f) {
                f2 = f * f4;
            } else {
                f = f6;
            }
        } else if (f3 > 0.75f) {
            float f7 = f * f4;
            if (f7 > f2) {
                f = f2 * f3;
            } else {
                f2 = f7;
            }
        } else if (f4 > 1.5f) {
            float f8 = f2 * f3;
            if (f8 > f) {
                f2 = f * f4;
            } else {
                f = f8;
            }
        } else {
            float f9 = f * f4;
            if (f9 > f2) {
                f = f2 * f3;
            } else {
                f2 = f9;
            }
        }
        return Bitmap.createScaledBitmap(bitmap, (int) f, (int) f2, false);
    }

    public static int dpToPx(Context context, int i) {
        float f = (float) i;
        context.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }

    public static Bitmap mergelogo(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        float width2 = (float) bitmap2.getWidth();
        float height2 = (float) bitmap2.getHeight();
        float f = width2 / height2;
        float f2 = height2 / width2;
        if (width2 > width) {
            bitmap2 = Bitmap.createScaledBitmap(bitmap2, (int) width, (int) (width * f2), false);
        } else if (height2 > height) {
            bitmap2 = Bitmap.createScaledBitmap(bitmap2, (int) (f * height), (int) height, false);
        }
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        canvas.drawBitmap(bitmap2, (float) (bitmap.getWidth() - bitmap2.getWidth()), (float) (bitmap.getHeight() - bitmap2.getHeight()), null);
        return createBitmap;
    }

    public static Bitmap mergelogo(Bitmap bitmap, Bitmap bitmap2, float f) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap2, bitmap.getWidth(), bitmap.getHeight(), true), 0.0f, 0.0f, null);
        return createBitmap;
    }

    public static Bitmap CropBitmapTransparency(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int i = -1;
        int height = bitmap.getHeight();
        int i2 = -1;
        int i3 = width;
        int i4 = 0;
        while (i4 < bitmap.getHeight()) {
            int i5 = i2;
            int i6 = i;
            int i7 = i3;
            for (int i8 = 0; i8 < bitmap.getWidth(); i8++) {
                if (((bitmap.getPixel(i8, i4) >> 24) & 255) > 0) {
                    if (i8 < i7) {
                        i7 = i8;
                    }
                    if (i8 > i6) {
                        i6 = i8;
                    }
                    if (i4 < height) {
                        height = i4;
                    }
                    if (i4 > i5) {
                        i5 = i4;
                    }
                }
            }
            i4++;
            i3 = i7;
            i = i6;
            i2 = i5;
        }
        if (i < i3 || i2 < height) {
            return null;
        }
        return Bitmap.createBitmap(bitmap, i3, height, (i - i3) + 1, (i2 - height) + 1);
    }

    public static Bitmap bitmapmasking(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode(null);
        return createBitmap;
    }

    public static Bitmap getTiledBitmap(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        Options options = new Options();
        options.inScaled = false;
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, options), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    private void setTextSizeForWidth(Paint paint, float f, String str) {
        paint.setTextSize(48.0f);
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        paint.setTextSize((f * 48.0f) / ((float) rect.width()));
    }

    public static Bitmap cropCenterBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width < i && height < i2) {
            return bitmap;
        }
        int i3 = 0;
        int i4 = width > i ? (width - i) / 2 : 0;
        if (height > i2) {
            i3 = (height - i2) / 2;
        }
        if (i > width) {
            i = width;
        }
        if (i2 > height) {
            i2 = height;
        }
        return Bitmap.createBitmap(bitmap, i4, i3, i, i2);
    }
}
