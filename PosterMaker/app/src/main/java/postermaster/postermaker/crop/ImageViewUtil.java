package postermaster.postermaker.crop;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

public class ImageViewUtil {
    public static Rect getBitmapRectCenterInside(Bitmap bitmap, View view) {
        return getBitmapRectCenterInsideHelper(bitmap.getWidth(), bitmap.getHeight(), view.getWidth(), view.getHeight());
    }

    public static Rect getBitmapRectCenterInside(int i, int i2, int i3, int i4) {
        return getBitmapRectCenterInsideHelper(i, i2, i3, i4);
    }

    private static Rect getBitmapRectCenterInsideHelper(int i, int i2, int i3, int i4) {
        double d;
        double d2;
        double d3;
        double d4;
        int i5;
        if (i3 < i) {
            double d5 = (double) i3;
            double d6 = (double) i;
            Double.isNaN(d5);
            Double.isNaN(d6);
            d = d5 / d6;
        } else {
            d = Double.POSITIVE_INFINITY;
        }
        if (i4 < i2) {
            double d7 = (double) i4;
            double d8 = (double) i2;
            Double.isNaN(d7);
            Double.isNaN(d8);
            d2 = d7 / d8;
        } else {
            d2 = Double.POSITIVE_INFINITY;
        }
        if (d == Double.POSITIVE_INFINITY && d2 == Double.POSITIVE_INFINITY) {
            d4 = (double) i2;
            d3 = (double) i;
        } else if (d <= d2) {
            double d9 = (double) i3;
            double d10 = (double) i2;
            Double.isNaN(d10);
            Double.isNaN(d9);
            double d11 = d10 * d9;
            double d12 = (double) i;
            Double.isNaN(d12);
            double d13 = d11 / d12;
            d3 = d9;
            d4 = d13;
        } else {
            d4 = (double) i4;
            double d14 = (double) i;
            Double.isNaN(d14);
            Double.isNaN(d4);
            double d15 = d14 * d4;
            double d16 = (double) i2;
            Double.isNaN(d16);
            d3 = d15 / d16;
        }
        double d17 = (double) i3;
        int i6 = 0;
        if (d3 == d17) {
            double d18 = (double) i4;
            Double.isNaN(d18);
            i5 = (int) Math.round((d18 - d4) / 2.0d);
        } else {
            double d19 = (double) i4;
            if (d4 == d19) {
                Double.isNaN(d17);
                i6 = (int) Math.round((d17 - d3) / 2.0d);
                i5 = 0;
            } else {
                Double.isNaN(d17);
                i6 = (int) Math.round((d17 - d3) / 2.0d);
                Double.isNaN(d19);
                i5 = (int) Math.round((d19 - d4) / 2.0d);
            }
        }
        return new Rect(i6, i5, ((int) Math.ceil(d3)) + i6, ((int) Math.ceil(d4)) + i5);
    }
}
