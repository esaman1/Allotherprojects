package msl.demo.listener;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import msl.demo.listener.ScaleGestureDetector.SimpleOnScaleGestureListener;
import msl.demo.view.ResizableStickerView;

public class MultiTouchListener implements OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    Bitmap bitmap = null;
    boolean bt = false;
    boolean checkstickerWH = false;
    private boolean disContinueHandleTransparecy = true;
    public boolean isRotateEnabled = true;
    public boolean isRotationEnabled = false;
    public boolean isTranslateEnabled = true;
    private TouchCallbackListener listener = null;
    private int mActivePointerId = -1;
    private float mPrevX;
    private float mPrevY;
    private ScaleGestureDetector mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());
    public float maximumScale = 8.0f;
    public float minimumScale = 0.5f;

    private class ScaleGestureListener extends SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private Vector2D mPrevSpanVector;

        private ScaleGestureListener() {
            this.mPrevSpanVector = new Vector2D();
        }

        public boolean onScaleBegin(View view, ScaleGestureDetector scaleGestureDetector) {
            this.mPivotX = scaleGestureDetector.getFocusX();
            this.mPivotY = scaleGestureDetector.getFocusY();
            this.mPrevSpanVector.set(scaleGestureDetector.getCurrentSpanVector());
            return true;
        }

        public boolean onScale(View view, ScaleGestureDetector scaleGestureDetector) {
            TransformInfo transformInfo = new TransformInfo();
            float f = 0.0f;
            transformInfo.deltaAngle = MultiTouchListener.this.isRotateEnabled ? Vector2D.getAngle(this.mPrevSpanVector, scaleGestureDetector.getCurrentSpanVector()) : 0.0f;
            transformInfo.deltaX = MultiTouchListener.this.isTranslateEnabled ? scaleGestureDetector.getFocusX() - this.mPivotX : 0.0f;
            if (MultiTouchListener.this.isTranslateEnabled) {
                f = scaleGestureDetector.getFocusY() - this.mPivotY;
            }
            transformInfo.deltaY = f;
            transformInfo.pivotX = this.mPivotX;
            transformInfo.pivotY = this.mPivotY;
            transformInfo.minimumScale = MultiTouchListener.this.minimumScale;
            transformInfo.maximumScale = MultiTouchListener.this.maximumScale;
            MultiTouchListener.this.move(view, transformInfo);
            return false;
        }
    }

    public interface TouchCallbackListener {
        void onTouchCallback(View view);

        void onTouchMoveCallback(View view);

        void onTouchUpCallback(View view);
    }

    private class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;

        private TransformInfo() {
        }
    }

    private static float adjustAngle(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    public MultiTouchListener setOnTouchCallbackListener(TouchCallbackListener touchCallbackListener) {
        this.listener = touchCallbackListener;
        return this;
    }

    public MultiTouchListener enableRotation(boolean z) {
        this.isRotationEnabled = z;
        return this;
    }

    public MultiTouchListener setMinScale(float f) {
        this.minimumScale = f;
        return this;
    }

    /* access modifiers changed from: private */
    public void move(View view, TransformInfo transformInfo) {
        if (this.isRotationEnabled) {
            view.setRotation(adjustAngle(view.getRotation() + transformInfo.deltaAngle));
        }
    }

    private static void adjustTranslation(View view, float f, float f2) {
        float[] fArr = {f, f2};
        view.getMatrix().mapVectors(fArr);
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(view.getTranslationY() + fArr[1]);
    }

    private static void computeRenderOffset(View view, float f, float f2) {
        if (view.getPivotX() != f || view.getPivotY() != f2) {
            float[] fArr = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr);
            view.setPivotX(f);
            view.setPivotY(f2);
            float[] fArr2 = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr2);
            float f3 = fArr2[1] - fArr[1];
            view.setTranslationX(view.getTranslationX() - (fArr2[0] - fArr[0]));
            view.setTranslationY(view.getTranslationY() - f3);
        }
    }

    public boolean handleTransparency(View view, MotionEvent motionEvent) {
        String str = "MOVE_TESTs";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("touch test: ");
            sb.append(view.getWidth());
            sb.append(" / ");
            sb.append(((ResizableStickerView) view).getMainWidth());
            Log.i(str, sb.toString());
            boolean z = true;
            boolean z2 = ((float) view.getWidth()) < ((ResizableStickerView) view).getMainWidth() && ((float) view.getHeight()) < ((ResizableStickerView) view).getMainHeight();
            if (z2 && ((ResizableStickerView) view).getBorderVisbilty()) {
                return false;
            }
            int action = motionEvent.getAction();
            if (motionEvent.getAction() == 2 && this.bt) {
                return true;
            }
            if (motionEvent.getAction() != 1 || !this.bt) {
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                int rawX = (int) (motionEvent.getRawX() - ((float) iArr[0]));
                int rawY = (int) (motionEvent.getRawY() - ((float) iArr[1]));
                float rotation = view.getRotation();
                Matrix matrix = new Matrix();
                matrix.postRotate(-rotation);
                float[] fArr = {(float) rawX, (float) rawY};
                matrix.mapPoints(fArr);
                int i = (int) fArr[0];
                int i2 = (int) fArr[1];
                if (motionEvent.getAction() == 0) {
                    this.bt = false;
                    boolean borderVisbilty = ((ResizableStickerView) view).getBorderVisbilty();
                    if (borderVisbilty) {
                        ((ResizableStickerView) view).setBorderVisibility(false);
                    }
                    this.bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
                    view.draw(new Canvas(this.bitmap));
                    if (borderVisbilty) {
                        ((ResizableStickerView) view).setBorderVisibility(true);
                    }
                    i = (int) (((float) i) * (((float) this.bitmap.getWidth()) / (((float) this.bitmap.getWidth()) * view.getScaleX())));
                    i2 = (int) (((float) i2) * (((float) this.bitmap.getHeight()) / (((float) this.bitmap.getHeight()) * view.getScaleX())));
                }
                if (i >= 0 && i2 >= 0 && i <= this.bitmap.getWidth()) {
                    if (i2 <= this.bitmap.getHeight()) {
                        if (this.bitmap.getPixel(i, i2) != 0) {
                            z = false;
                        }
                        if (motionEvent.getAction() != 0) {
                            return z;
                        }
                        this.bt = z;
                        if (z) {
                            if (!z2) {
                                ((ResizableStickerView) view).setBorderVisibility(false);
                                return z;
                            }
                        }
                        return z;
                    }
                }
                return false;
            }
            this.bt = false;
            if (this.bitmap != null) {
                this.bitmap.recycle();
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mScaleGestureDetector.onTouchEvent(view, motionEvent);
        RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
        int i = 0;
        if (this.disContinueHandleTransparecy) {
            if (handleTransparency(view, motionEvent)) {
                return false;
            }
            this.disContinueHandleTransparecy = false;
        }
        if (!this.isTranslateEnabled) {
            return true;
        }
        int action = motionEvent.getAction();
        int actionMasked = motionEvent.getActionMasked() & action;
        if (actionMasked != 6) {
            switch (actionMasked) {
                case 0:
                    if (relativeLayout != null) {
                        relativeLayout.requestDisallowInterceptTouchEvent(true);
                    }
                    TouchCallbackListener touchCallbackListener = this.listener;
                    if (touchCallbackListener != null) {
                        touchCallbackListener.onTouchCallback(view);
                    }
                    view.bringToFront();
                    if (view instanceof ResizableStickerView) {
                        ((ResizableStickerView) view).setBorderVisibility(true);
                    }
                    this.mPrevX = motionEvent.getX();
                    this.mPrevY = motionEvent.getY();
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    return true;
                case 1:
                    this.mActivePointerId = -1;
                    this.disContinueHandleTransparecy = true;
                    TouchCallbackListener touchCallbackListener2 = this.listener;
                    if (touchCallbackListener2 != null) {
                        touchCallbackListener2.onTouchUpCallback(view);
                    }
                    float rotation = view.getRotation();
                    if (Math.abs(90.0f - Math.abs(rotation)) <= 5.0f) {
                        rotation = rotation > 0.0f ? 90.0f : -90.0f;
                    }
                    if (Math.abs(0.0f - Math.abs(rotation)) <= 5.0f) {
                        rotation = rotation > 0.0f ? 0.0f : -0.0f;
                    }
                    if (Math.abs(180.0f - Math.abs(rotation)) <= 5.0f) {
                        rotation = rotation > 0.0f ? 180.0f : -180.0f;
                    }
                    view.setRotation(rotation);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Final Rotation : ");
                    sb.append(rotation);
                    Log.i("testing", sb.toString());
                    return true;
                case 2:
                    if (relativeLayout != null) {
                        relativeLayout.requestDisallowInterceptTouchEvent(true);
                    }
                    TouchCallbackListener touchCallbackListener3 = this.listener;
                    if (touchCallbackListener3 != null) {
                        touchCallbackListener3.onTouchMoveCallback(view);
                    }
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (findPointerIndex == -1) {
                        return true;
                    }
                    float x = motionEvent.getX(findPointerIndex);
                    float y = motionEvent.getY(findPointerIndex);
                    if (this.mScaleGestureDetector.isInProgress()) {
                        return true;
                    }
                    adjustTranslation(view, x - this.mPrevX, y - this.mPrevY);
                    return true;
                case 3:
                    this.mActivePointerId = -1;
                    return true;
                default:
                    return true;
            }
        } else {
            int i2 = (65280 & action) >> 8;
            if (motionEvent.getPointerId(i2) != this.mActivePointerId) {
                return true;
            }
            if (i2 == 0) {
                i = 1;
            }
            this.mPrevX = motionEvent.getX(i);
            this.mPrevY = motionEvent.getY(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            return true;
        }
    }
}
