package postermaster.postermaker.crop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class CropOverlayView extends View {
    private static final float DEFAULT_CORNER_EXTENSION_DP;
    private static final float DEFAULT_CORNER_LENGTH_DP = 20.0f;
    private static final float DEFAULT_CORNER_OFFSET_DP;
    private static final float DEFAULT_CORNER_THICKNESS_DP = PaintUtil.getCornerThickness();
    private static final float DEFAULT_LINE_THICKNESS_DP = PaintUtil.getLineThickness();
    private static final float DEFAULT_SHOW_GUIDELINES_LIMIT = 100.0f;
    private static final int GUIDELINES_OFF = 0;
    private static final int GUIDELINES_ON = 2;
    private static final int GUIDELINES_ON_TOUCH = 1;
    private static final int SNAP_RADIUS_DP = 6;
    private boolean initializedCropWindow = false;
    private int mAspectRatioX = 1;
    private int mAspectRatioY = 1;
    private Paint mBackgroundPaint;
    private Rect mBitmapRect;
    private Paint mBorderPaint;
    private float mCornerExtension;
    private float mCornerLength;
    private float mCornerOffset;
    private Paint mCornerPaint;
    private boolean mFixAspectRatio = false;
    private Paint mGuidelinePaint;
    private int mGuidelines;
    private float mHandleRadius;
    private Handle mPressedHandle;
    private float mSnapRadius;
    private float mTargetAspectRatio = (((float) this.mAspectRatioX) / ((float) this.mAspectRatioY));
    private Pair<Float, Float> mTouchOffset;

    static {
        float f = DEFAULT_CORNER_THICKNESS_DP;
        DEFAULT_CORNER_OFFSET_DP = (f / 2.0f) - (DEFAULT_LINE_THICKNESS_DP / 2.0f);
        DEFAULT_CORNER_EXTENSION_DP = (f / 2.0f) + DEFAULT_CORNER_OFFSET_DP;
    }

    public CropOverlayView(Context context) {
        super(context);
        init(context);
    }

    public CropOverlayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        initCropWindow(this.mBitmapRect);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas, this.mBitmapRect);
        if (showGuidelines()) {
            int i = this.mGuidelines;
            if (i == 2) {
                drawRuleOfThirdsGuidelines(canvas);
            } else if (i == 1 && this.mPressedHandle != null) {
                drawRuleOfThirdsGuidelines(canvas);
            }
        }
        canvas.drawRect(Edge.LEFT.getCoordinate(), Edge.TOP.getCoordinate(), Edge.RIGHT.getCoordinate(), Edge.BOTTOM.getCoordinate(), this.mBorderPaint);
        drawCorners(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                onActionDown(motionEvent.getX(), motionEvent.getY());
                return true;
            case 1:
            case 3:
                getParent().requestDisallowInterceptTouchEvent(false);
                onActionUp();
                return true;
            case 2:
                onActionMove(motionEvent.getX(), motionEvent.getY());
                getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            default:
                return false;
        }
    }

    public void setBitmapRect(Rect rect) {
        this.mBitmapRect = rect;
        initCropWindow(this.mBitmapRect);
    }

    public void resetCropOverlayView() {
        if (this.initializedCropWindow) {
            initCropWindow(this.mBitmapRect);
            invalidate();
        }
    }

    public void setGuidelines(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException("Guideline value must be set between 0 and 2. See documentation.");
        }
        this.mGuidelines = i;
        if (this.initializedCropWindow) {
            initCropWindow(this.mBitmapRect);
            invalidate();
        }
    }

    public void setFixedAspectRatio(boolean z) {
        this.mFixAspectRatio = z;
        if (this.initializedCropWindow) {
            initCropWindow(this.mBitmapRect);
            invalidate();
        }
    }

    public void setAspectRatioX(int i) {
        if (i > 0) {
            this.mAspectRatioX = i;
            this.mTargetAspectRatio = ((float) this.mAspectRatioX) / ((float) this.mAspectRatioY);
            if (this.initializedCropWindow) {
                initCropWindow(this.mBitmapRect);
                invalidate();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Cannot set aspect ratio value to a number less than or equal to 0.");
    }

    public void setAspectRatioY(int i) {
        if (i > 0) {
            this.mAspectRatioY = i;
            this.mTargetAspectRatio = ((float) this.mAspectRatioX) / ((float) this.mAspectRatioY);
            if (this.initializedCropWindow) {
                initCropWindow(this.mBitmapRect);
                invalidate();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Cannot set aspect ratio value to a number less than or equal to 0.");
    }

    public void setInitialAttributeValues(int i, boolean z, int i2, int i3) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException("Guideline value must be set between 0 and 2. See documentation.");
        }
        this.mGuidelines = i;
        this.mFixAspectRatio = z;
        if (i2 > 0) {
            this.mAspectRatioX = i2;
            int i4 = this.mAspectRatioX;
            this.mTargetAspectRatio = ((float) i4) / ((float) this.mAspectRatioY);
            if (i3 > 0) {
                this.mAspectRatioY = i3;
                this.mTargetAspectRatio = ((float) i4) / ((float) this.mAspectRatioY);
                return;
            }
            throw new IllegalArgumentException("Cannot set aspect ratio value to a number less than or equal to 0.");
        }
        throw new IllegalArgumentException("Cannot set aspect ratio value to a number less than or equal to 0.");
    }

    private void init(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mHandleRadius = HandleUtil.getTargetRadius(context);
        this.mSnapRadius = TypedValue.applyDimension(1, 6.0f, displayMetrics);
        this.mBorderPaint = PaintUtil.newBorderPaint(context);
        this.mGuidelinePaint = PaintUtil.newGuidelinePaint();
        this.mBackgroundPaint = PaintUtil.newBackgroundPaint(context);
        this.mCornerPaint = PaintUtil.newCornerPaint(context);
        this.mCornerOffset = TypedValue.applyDimension(1, DEFAULT_CORNER_OFFSET_DP, displayMetrics);
        this.mCornerExtension = TypedValue.applyDimension(1, DEFAULT_CORNER_EXTENSION_DP, displayMetrics);
        this.mCornerLength = TypedValue.applyDimension(1, 20.0f, displayMetrics);
        this.mGuidelines = 1;
    }

    private void initCropWindow(Rect rect) {
        if (!this.initializedCropWindow) {
            this.initializedCropWindow = true;
        }
        if (!this.mFixAspectRatio) {
            float width = ((float) rect.width()) * 0.1f;
            float height = ((float) rect.height()) * 0.1f;
            Edge.LEFT.setCoordinate(((float) rect.left) + width);
            Edge.TOP.setCoordinate(((float) rect.top) + height);
            Edge.RIGHT.setCoordinate(((float) rect.right) - width);
            Edge.BOTTOM.setCoordinate(((float) rect.bottom) - height);
        } else if (AspectRatioUtil.calculateAspectRatio(rect) > this.mTargetAspectRatio) {
            Edge.TOP.setCoordinate((float) rect.top);
            Edge.BOTTOM.setCoordinate((float) rect.bottom);
            float width2 = ((float) getWidth()) / 2.0f;
            float max = Math.max(40.0f, AspectRatioUtil.calculateWidth(Edge.TOP.getCoordinate(), Edge.BOTTOM.getCoordinate(), this.mTargetAspectRatio));
            if (max == 40.0f) {
                this.mTargetAspectRatio = 40.0f / (Edge.BOTTOM.getCoordinate() - Edge.TOP.getCoordinate());
            }
            float f = max / 2.0f;
            Edge.LEFT.setCoordinate(width2 - f);
            Edge.RIGHT.setCoordinate(width2 + f);
        } else {
            Edge.LEFT.setCoordinate((float) rect.left);
            Edge.RIGHT.setCoordinate((float) rect.right);
            float height2 = ((float) getHeight()) / 2.0f;
            float max2 = Math.max(40.0f, AspectRatioUtil.calculateHeight(Edge.LEFT.getCoordinate(), Edge.RIGHT.getCoordinate(), this.mTargetAspectRatio));
            if (max2 == 40.0f) {
                this.mTargetAspectRatio = (Edge.RIGHT.getCoordinate() - Edge.LEFT.getCoordinate()) / 40.0f;
            }
            float f2 = max2 / 2.0f;
            Edge.TOP.setCoordinate(height2 - f2);
            Edge.BOTTOM.setCoordinate(height2 + f2);
        }
    }

    public static boolean showGuidelines() {
        return Math.abs(Edge.LEFT.getCoordinate() - Edge.RIGHT.getCoordinate()) >= DEFAULT_SHOW_GUIDELINES_LIMIT && Math.abs(Edge.TOP.getCoordinate() - Edge.BOTTOM.getCoordinate()) >= DEFAULT_SHOW_GUIDELINES_LIMIT;
    }

    private void drawRuleOfThirdsGuidelines(Canvas canvas) {
        float coordinate = Edge.LEFT.getCoordinate();
        float coordinate2 = Edge.TOP.getCoordinate();
        float coordinate3 = Edge.RIGHT.getCoordinate();
        float coordinate4 = Edge.BOTTOM.getCoordinate();
        float width = Edge.getWidth() / 3.0f;
        float f = coordinate + width;
        Canvas canvas2 = canvas;
        float f2 = coordinate2;
        float f3 = coordinate4;
        canvas2.drawLine(f, f2, f, f3, this.mGuidelinePaint);
        float f4 = coordinate3 - width;
        canvas2.drawLine(f4, f2, f4, f3, this.mGuidelinePaint);
        float height = Edge.getHeight() / 3.0f;
        float f5 = coordinate2 + height;
        Canvas canvas3 = canvas;
        float f6 = coordinate;
        float f7 = coordinate3;
        canvas3.drawLine(f6, f5, f7, f5, this.mGuidelinePaint);
        float f8 = coordinate4 - height;
        canvas3.drawLine(f6, f8, f7, f8, this.mGuidelinePaint);
    }

    private void drawBackground(Canvas canvas, Rect rect) {
        float coordinate = Edge.LEFT.getCoordinate();
        float coordinate2 = Edge.TOP.getCoordinate();
        float coordinate3 = Edge.RIGHT.getCoordinate();
        float coordinate4 = Edge.BOTTOM.getCoordinate();
        Canvas canvas2 = canvas;
        canvas2.drawRect((float) rect.left, (float) rect.top, (float) rect.right, coordinate2, this.mBackgroundPaint);
        canvas2.drawRect((float) rect.left, coordinate4, (float) rect.right, (float) rect.bottom, this.mBackgroundPaint);
        canvas.drawRect((float) rect.left, coordinate2, coordinate, coordinate4, this.mBackgroundPaint);
        canvas.drawRect(coordinate3, coordinate2, (float) rect.right, coordinate4, this.mBackgroundPaint);
    }

    private void drawCorners(Canvas canvas) {
        float coordinate = Edge.LEFT.getCoordinate();
        float coordinate2 = Edge.TOP.getCoordinate();
        float coordinate3 = Edge.RIGHT.getCoordinate();
        float coordinate4 = Edge.BOTTOM.getCoordinate();
        float f = this.mCornerOffset;
        Canvas canvas2 = canvas;
        canvas2.drawLine(coordinate - f, coordinate2 - this.mCornerExtension, coordinate - f, coordinate2 + this.mCornerLength, this.mCornerPaint);
        float f2 = this.mCornerOffset;
        canvas.drawLine(coordinate, coordinate2 - f2, coordinate + this.mCornerLength, coordinate2 - f2, this.mCornerPaint);
        float f3 = this.mCornerOffset;
        canvas2.drawLine(coordinate3 + f3, coordinate2 - this.mCornerExtension, coordinate3 + f3, coordinate2 + this.mCornerLength, this.mCornerPaint);
        float f4 = this.mCornerOffset;
        float f5 = coordinate3;
        canvas2.drawLine(f5, coordinate2 - f4, coordinate3 - this.mCornerLength, coordinate2 - f4, this.mCornerPaint);
        float f6 = this.mCornerOffset;
        canvas.drawLine(coordinate - f6, coordinate4 + this.mCornerExtension, coordinate - f6, coordinate4 - this.mCornerLength, this.mCornerPaint);
        float f7 = this.mCornerOffset;
        canvas.drawLine(coordinate, coordinate4 + f7, coordinate + this.mCornerLength, coordinate4 + f7, this.mCornerPaint);
        float f8 = this.mCornerOffset;
        canvas.drawLine(coordinate3 + f8, coordinate4 + this.mCornerExtension, coordinate3 + f8, coordinate4 - this.mCornerLength, this.mCornerPaint);
        float f9 = this.mCornerOffset;
        canvas2.drawLine(f5, coordinate4 + f9, coordinate3 - this.mCornerLength, coordinate4 + f9, this.mCornerPaint);
    }

    private void onActionDown(float f, float f2) {
        float coordinate = Edge.LEFT.getCoordinate();
        float coordinate2 = Edge.TOP.getCoordinate();
        float coordinate3 = Edge.RIGHT.getCoordinate();
        float coordinate4 = Edge.BOTTOM.getCoordinate();
        this.mPressedHandle = HandleUtil.getPressedHandle(f, f2, coordinate, coordinate2, coordinate3, coordinate4, this.mHandleRadius);
        Handle handle = this.mPressedHandle;
        if (handle != null) {
            this.mTouchOffset = HandleUtil.getOffset(handle, f, f2, coordinate, coordinate2, coordinate3, coordinate4);
            invalidate();
        }
    }

    private void onActionUp() {
        if (this.mPressedHandle != null) {
            this.mPressedHandle = null;
            invalidate();
        }
    }

    private void onActionMove(float f, float f2) {
        if (this.mPressedHandle != null) {
            float floatValue = f + ((Float) this.mTouchOffset.first).floatValue();
            float floatValue2 = f2 + ((Float) this.mTouchOffset.second).floatValue();
            if (this.mFixAspectRatio) {
                this.mPressedHandle.updateCropWindow(floatValue, floatValue2, this.mTargetAspectRatio, this.mBitmapRect, this.mSnapRadius);
            } else {
                this.mPressedHandle.updateCropWindow(floatValue, floatValue2, this.mBitmapRect, this.mSnapRadius);
            }
            invalidate();
        }
    }
}
