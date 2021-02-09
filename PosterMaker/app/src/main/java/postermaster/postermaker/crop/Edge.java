package postermaster.postermaker.crop;

import android.graphics.Rect;
import android.view.View;

public enum Edge {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM;
    
    public static final int MIN_CROP_LENGTH_PX = 40;
    private float mCoordinate;

    public void setCoordinate(float f) {
        this.mCoordinate = f;
    }

    public void offset(float f) {
        this.mCoordinate += f;
    }

    public float getCoordinate() {
        return this.mCoordinate;
    }

    public void adjustCoordinate(float f, float f2, Rect rect, float f3, float f4) {
        switch (this) {
            case LEFT:
                this.mCoordinate = adjustLeft(f, rect, f3, f4);
                return;
            case TOP:
                this.mCoordinate = adjustTop(f2, rect, f3, f4);
                return;
            case RIGHT:
                this.mCoordinate = adjustRight(f, rect, f3, f4);
                return;
            case BOTTOM:
                this.mCoordinate = adjustBottom(f2, rect, f3, f4);
                return;
            default:
                return;
        }
    }

    public void adjustCoordinate(float f) {
        float coordinate = LEFT.getCoordinate();
        float coordinate2 = TOP.getCoordinate();
        float coordinate3 = RIGHT.getCoordinate();
        float coordinate4 = BOTTOM.getCoordinate();
        switch (this) {
            case LEFT:
                this.mCoordinate = AspectRatioUtil.calculateLeft(coordinate2, coordinate3, coordinate4, f);
                return;
            case TOP:
                this.mCoordinate = AspectRatioUtil.calculateTop(coordinate, coordinate3, coordinate4, f);
                return;
            case RIGHT:
                this.mCoordinate = AspectRatioUtil.calculateRight(coordinate, coordinate2, coordinate4, f);
                return;
            case BOTTOM:
                this.mCoordinate = AspectRatioUtil.calculateBottom(coordinate, coordinate2, coordinate3, f);
                return;
            default:
                return;
        }
    }

    public boolean isNewRectangleOutOfBounds(Edge edge, Rect rect, float f) {
        float snapOffset = edge.snapOffset(rect);
        switch (this) {
            case LEFT:
                if (edge.equals(TOP)) {
                    float f2 = (float) rect.top;
                    float coordinate = BOTTOM.getCoordinate() - snapOffset;
                    float coordinate2 = RIGHT.getCoordinate();
                    return isOutOfBounds(f2, AspectRatioUtil.calculateLeft(f2, coordinate2, coordinate, f), coordinate, coordinate2, rect);
                } else if (edge.equals(BOTTOM)) {
                    float f3 = (float) rect.bottom;
                    float coordinate3 = TOP.getCoordinate() - snapOffset;
                    float coordinate4 = RIGHT.getCoordinate();
                    return isOutOfBounds(coordinate3, AspectRatioUtil.calculateLeft(coordinate3, coordinate4, f3, f), f3, coordinate4, rect);
                }
                break;
            case TOP:
                if (edge.equals(LEFT)) {
                    float f4 = (float) rect.left;
                    float coordinate5 = RIGHT.getCoordinate() - snapOffset;
                    float coordinate6 = BOTTOM.getCoordinate();
                    return isOutOfBounds(AspectRatioUtil.calculateTop(f4, coordinate5, coordinate6, f), f4, coordinate6, coordinate5, rect);
                } else if (edge.equals(RIGHT)) {
                    float f5 = (float) rect.right;
                    float coordinate7 = LEFT.getCoordinate() - snapOffset;
                    float coordinate8 = BOTTOM.getCoordinate();
                    return isOutOfBounds(AspectRatioUtil.calculateTop(coordinate7, f5, coordinate8, f), coordinate7, coordinate8, f5, rect);
                }
                break;
            case RIGHT:
                if (edge.equals(TOP)) {
                    float f6 = (float) rect.top;
                    float coordinate9 = BOTTOM.getCoordinate() - snapOffset;
                    float coordinate10 = LEFT.getCoordinate();
                    return isOutOfBounds(f6, coordinate10, coordinate9, AspectRatioUtil.calculateRight(coordinate10, f6, coordinate9, f), rect);
                } else if (edge.equals(BOTTOM)) {
                    float f7 = (float) rect.bottom;
                    float coordinate11 = TOP.getCoordinate() - snapOffset;
                    float coordinate12 = LEFT.getCoordinate();
                    return isOutOfBounds(coordinate11, coordinate12, f7, AspectRatioUtil.calculateRight(coordinate12, coordinate11, f7, f), rect);
                }
                break;
            case BOTTOM:
                if (edge.equals(LEFT)) {
                    float f8 = (float) rect.left;
                    float coordinate13 = RIGHT.getCoordinate() - snapOffset;
                    float coordinate14 = TOP.getCoordinate();
                    return isOutOfBounds(coordinate14, f8, AspectRatioUtil.calculateBottom(f8, coordinate14, coordinate13, f), coordinate13, rect);
                } else if (edge.equals(RIGHT)) {
                    float f9 = (float) rect.right;
                    float coordinate15 = LEFT.getCoordinate() - snapOffset;
                    float coordinate16 = TOP.getCoordinate();
                    return isOutOfBounds(coordinate16, coordinate15, AspectRatioUtil.calculateBottom(coordinate15, coordinate16, f9, f), f9, rect);
                }
                break;
        }
        return true;
    }

    private boolean isOutOfBounds(float f, float f2, float f3, float f4, Rect rect) {
        return f < ((float) rect.top) || f2 < ((float) rect.left) || f3 > ((float) rect.bottom) || f4 > ((float) rect.right);
    }

    public float snapToRect(Rect rect) {
        float f = this.mCoordinate;
        switch (this) {
            case LEFT:
                this.mCoordinate = (float) rect.left;
                break;
            case TOP:
                this.mCoordinate = (float) rect.top;
                break;
            case RIGHT:
                this.mCoordinate = (float) rect.right;
                break;
            case BOTTOM:
                this.mCoordinate = (float) rect.bottom;
                break;
        }
        return this.mCoordinate - f;
    }

    public float snapOffset(Rect rect) {
        float f;
        float f2 = this.mCoordinate;
        switch (this) {
            case LEFT:
                f = (float) rect.left;
                break;
            case TOP:
                f = (float) rect.top;
                break;
            case RIGHT:
                f = (float) rect.right;
                break;
            case BOTTOM:
                f = (float) rect.bottom;
                break;
            default:
                f = f2;
                break;
        }
        return f - f2;
    }

    public void snapToView(View view) {
        switch (this) {
            case LEFT:
                this.mCoordinate = 0.0f;
                return;
            case TOP:
                this.mCoordinate = 0.0f;
                return;
            case RIGHT:
                this.mCoordinate = (float) view.getWidth();
                return;
            case BOTTOM:
                this.mCoordinate = (float) view.getHeight();
                return;
            default:
                return;
        }
    }

    public static float getWidth() {
        return RIGHT.getCoordinate() - LEFT.getCoordinate();
    }

    public static float getHeight() {
        return BOTTOM.getCoordinate() - TOP.getCoordinate();
    }

    public boolean isOutsideMargin(Rect rect, float f) {
        boolean z = true;
        switch (this) {
            case LEFT:
                return this.mCoordinate - ((float) rect.left) < f;
            case TOP:
                return this.mCoordinate - ((float) rect.top) < f;
            case RIGHT:
                return ((float) rect.right) - this.mCoordinate < f;
            case BOTTOM:
                if (((float) rect.bottom) - this.mCoordinate >= f) {
                    z = false;
                }
                return z;
            default:
                return false;
        }
    }

    public boolean isOutsideFrame(Rect rect) {
        boolean z = true;
        switch (this) {
            case LEFT:
                return ((double) (this.mCoordinate - ((float) rect.left))) < 0.0d;
            case TOP:
                return ((double) (this.mCoordinate - ((float) rect.top))) < 0.0d;
            case RIGHT:
                return ((double) (((float) rect.right) - this.mCoordinate)) < 0.0d;
            case BOTTOM:
                if (((double) (((float) rect.bottom) - this.mCoordinate)) >= 0.0d) {
                    z = false;
                }
                return z;
            default:
                return false;
        }
    }

    private static float adjustLeft(float f, Rect rect, float f2, float f3) {
        if (f - ((float) rect.left) < f2) {
            return (float) rect.left;
        }
        float f4 = Float.POSITIVE_INFINITY;
        float coordinate = f >= RIGHT.getCoordinate() - 40.0f ? RIGHT.getCoordinate() - 40.0f : Float.POSITIVE_INFINITY;
        if ((RIGHT.getCoordinate() - f) / f3 <= 40.0f) {
            f4 = RIGHT.getCoordinate() - (f3 * 40.0f);
        }
        return Math.min(f, Math.min(coordinate, f4));
    }

    private static float adjustRight(float f, Rect rect, float f2, float f3) {
        if (((float) rect.right) - f < f2) {
            return (float) rect.right;
        }
        float f4 = Float.NEGATIVE_INFINITY;
        float coordinate = f <= LEFT.getCoordinate() + 40.0f ? LEFT.getCoordinate() + 40.0f : Float.NEGATIVE_INFINITY;
        if ((f - LEFT.getCoordinate()) / f3 <= 40.0f) {
            f4 = LEFT.getCoordinate() + (f3 * 40.0f);
        }
        return Math.max(f, Math.max(coordinate, f4));
    }

    private static float adjustTop(float f, Rect rect, float f2, float f3) {
        if (f - ((float) rect.top) < f2) {
            return (float) rect.top;
        }
        float f4 = Float.POSITIVE_INFINITY;
        float coordinate = f >= BOTTOM.getCoordinate() - 40.0f ? BOTTOM.getCoordinate() - 40.0f : Float.POSITIVE_INFINITY;
        if ((BOTTOM.getCoordinate() - f) * f3 <= 40.0f) {
            f4 = BOTTOM.getCoordinate() - (40.0f / f3);
        }
        return Math.min(f, Math.min(coordinate, f4));
    }

    private static float adjustBottom(float f, Rect rect, float f2, float f3) {
        if (((float) rect.bottom) - f < f2) {
            return (float) rect.bottom;
        }
        float f4 = Float.NEGATIVE_INFINITY;
        float coordinate = f <= TOP.getCoordinate() + 40.0f ? TOP.getCoordinate() + 40.0f : Float.NEGATIVE_INFINITY;
        if ((f - TOP.getCoordinate()) * f3 <= 40.0f) {
            f4 = TOP.getCoordinate() + (40.0f / f3);
        }
        return Math.max(f, Math.max(f4, coordinate));
    }
}
