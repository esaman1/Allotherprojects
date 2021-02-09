package postermaster.postermaker.crop;

import android.graphics.Rect;

public class AspectRatioUtil {
    public static float calculateAspectRatio(float f, float f2, float f3, float f4) {
        return (f3 - f) / (f4 - f2);
    }

    public static float calculateBottom(float f, float f2, float f3, float f4) {
        return ((f3 - f) / f4) + f2;
    }

    public static float calculateHeight(float f, float f2, float f3) {
        return (f2 - f) / f3;
    }

    public static float calculateLeft(float f, float f2, float f3, float f4) {
        return f2 - ((f3 - f) * f4);
    }

    public static float calculateRight(float f, float f2, float f3, float f4) {
        return ((f3 - f2) * f4) + f;
    }

    public static float calculateTop(float f, float f2, float f3, float f4) {
        return f3 - ((f2 - f) / f4);
    }

    public static float calculateWidth(float f, float f2, float f3) {
        return (f2 - f) * f3;
    }

    public static float calculateAspectRatio(Rect rect) {
        return ((float) rect.width()) / ((float) rect.height());
    }
}
