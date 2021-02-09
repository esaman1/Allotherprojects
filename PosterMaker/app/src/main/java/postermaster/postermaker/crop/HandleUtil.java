package postermaster.postermaker.crop;

import android.content.Context;
import android.util.Pair;
import android.util.TypedValue;

public class HandleUtil {
    private static final int TARGET_RADIUS_DP = 24;

    private static boolean isInCenterTargetZone(float f, float f2, float f3, float f4, float f5, float f6) {
        return f > f3 && f < f5 && f2 > f4 && f2 < f6;
    }

    public static float getTargetRadius(Context context) {
        return TypedValue.applyDimension(1, 24.0f, context.getResources().getDisplayMetrics());
    }

    public static Handle getPressedHandle(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        if (isInCornerTargetZone(f, f2, f3, f4, f7)) {
            return Handle.TOP_LEFT;
        }
        if (isInCornerTargetZone(f, f2, f5, f4, f7)) {
            return Handle.TOP_RIGHT;
        }
        if (isInCornerTargetZone(f, f2, f3, f6, f7)) {
            return Handle.BOTTOM_LEFT;
        }
        if (isInCornerTargetZone(f, f2, f5, f6, f7)) {
            return Handle.BOTTOM_RIGHT;
        }
        if (isInCenterTargetZone(f, f2, f3, f4, f5, f6) && focusCenter()) {
            return Handle.CENTER;
        }
        if (isInHorizontalTargetZone(f, f2, f3, f5, f4, f7)) {
            return Handle.TOP;
        }
        if (isInHorizontalTargetZone(f, f2, f3, f5, f6, f7)) {
            return Handle.BOTTOM;
        }
        if (isInVerticalTargetZone(f, f2, f3, f4, f6, f7)) {
            return Handle.LEFT;
        }
        if (isInVerticalTargetZone(f, f2, f5, f4, f6, f7)) {
            return Handle.RIGHT;
        }
        if (!isInCenterTargetZone(f, f2, f3, f4, f5, f6) || focusCenter()) {
            return null;
        }
        return Handle.CENTER;
    }

    public static Pair<Float, Float> getOffset(Handle handle, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7;
        if (handle == null) {
            return null;
        }
        float f8 = 0.0f;
        switch (handle) {
            case TOP_LEFT:
                f8 = f3 - f;
                f7 = f4 - f2;
                break;
            case TOP_RIGHT:
                f8 = f5 - f;
                f7 = f4 - f2;
                break;
            case BOTTOM_LEFT:
                f8 = f3 - f;
                f7 = f6 - f2;
                break;
            case BOTTOM_RIGHT:
                f8 = f5 - f;
                f7 = f6 - f2;
                break;
            case LEFT:
                f8 = f3 - f;
                f7 = 0.0f;
                break;
            case TOP:
                f7 = f4 - f2;
                break;
            case RIGHT:
                f8 = f5 - f;
                f7 = 0.0f;
                break;
            case BOTTOM:
                f7 = f6 - f2;
                break;
            case CENTER:
                f8 = ((f5 + f3) / 2.0f) - f;
                f7 = ((f4 + f6) / 2.0f) - f2;
                break;
            default:
                f7 = 0.0f;
                break;
        }
        return new Pair<>(Float.valueOf(f8), Float.valueOf(f7));
    }

    private static boolean isInCornerTargetZone(float f, float f2, float f3, float f4, float f5) {
        return Math.abs(f - f3) <= f5 && Math.abs(f2 - f4) <= f5;
    }

    private static boolean isInHorizontalTargetZone(float f, float f2, float f3, float f4, float f5, float f6) {
        return f > f3 && f < f4 && Math.abs(f2 - f5) <= f6;
    }

    private static boolean isInVerticalTargetZone(float f, float f2, float f3, float f4, float f5, float f6) {
        return Math.abs(f - f3) <= f6 && f2 > f4 && f2 < f5;
    }

    private static boolean focusCenter() {
        return !CropOverlayView.showGuidelines();
    }
}
