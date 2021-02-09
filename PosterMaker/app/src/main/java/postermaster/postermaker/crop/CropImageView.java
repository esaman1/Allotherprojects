package postermaster.postermaker.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import postermaster.postermaker.R;

public class CropImageView extends FrameLayout {
    public static final int DEFAULT_ASPECT_RATIO_X = 1;
    public static final int DEFAULT_ASPECT_RATIO_Y = 1;
    public static final boolean DEFAULT_FIXED_ASPECT_RATIO = false;
    public static final int DEFAULT_GUIDELINES = 1;
    private static final int DEFAULT_IMAGE_RESOURCE = 0;
    private static final String DEGREES_ROTATED = "DEGREES_ROTATED";
    private static final Rect EMPTY_RECT = new Rect();
    private int mAspectRatioX = 1;
    private int mAspectRatioY = 1;
    private Bitmap mBitmap;
    private CropOverlayView mCropOverlayView;
    private int mDegreesRotated = 0;
    private boolean mFixAspectRatio = false;
    private int mGuidelines = 1;
    private int mImageResource = 0;
    private ImageView mImageView;
    private int mLayoutHeight;
    private int mLayoutWidth;

    public CropImageView(Context context) {
        super(context);
        init(context);
    }

    public CropImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt(DEGREES_ROTATED, this.mDegreesRotated);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            if (this.mBitmap != null) {
                this.mDegreesRotated = bundle.getInt(DEGREES_ROTATED);
                int i = this.mDegreesRotated;
                rotateImage(i);
                this.mDegreesRotated = i;
            }
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            this.mCropOverlayView.setBitmapRect(ImageViewUtil.getBitmapRectCenterInside(bitmap, this));
        } else {
            this.mCropOverlayView.setBitmapRect(EMPTY_RECT);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        double d;
        double d2;
        int i3;
        int i4;
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        if (this.mBitmap != null) {
            super.onMeasure(i, i2);
            if (size2 == 0) {
                size2 = this.mBitmap.getHeight();
            }
            if (size < this.mBitmap.getWidth()) {
                double d3 = (double) size;
                double width = (double) this.mBitmap.getWidth();
                Double.isNaN(d3);
                Double.isNaN(width);
                d = d3 / width;
            } else {
                d = Double.POSITIVE_INFINITY;
            }
            if (size2 < this.mBitmap.getHeight()) {
                double d4 = (double) size2;
                double height = (double) this.mBitmap.getHeight();
                Double.isNaN(d4);
                Double.isNaN(height);
                d2 = d4 / height;
            } else {
                d2 = Double.POSITIVE_INFINITY;
            }
            if (d == Double.POSITIVE_INFINITY && d2 == Double.POSITIVE_INFINITY) {
                i4 = this.mBitmap.getWidth();
                i3 = this.mBitmap.getHeight();
            } else if (d <= d2) {
                double height2 = (double) this.mBitmap.getHeight();
                Double.isNaN(height2);
                i3 = (int) (height2 * d);
                i4 = size;
            } else {
                double width2 = (double) this.mBitmap.getWidth();
                Double.isNaN(width2);
                i4 = (int) (width2 * d2);
                i3 = size2;
            }
            int onMeasureSpec = getOnMeasureSpec(mode, size, i4);
            int onMeasureSpec2 = getOnMeasureSpec(mode2, size2, i3);
            this.mLayoutWidth = onMeasureSpec;
            this.mLayoutHeight = onMeasureSpec2;
            this.mCropOverlayView.setBitmapRect(ImageViewUtil.getBitmapRectCenterInside(this.mBitmap.getWidth(), this.mBitmap.getHeight(), this.mLayoutWidth, this.mLayoutHeight));
            setMeasuredDimension(this.mLayoutWidth, this.mLayoutHeight);
            return;
        }
        this.mCropOverlayView.setBitmapRect(EMPTY_RECT);
        setMeasuredDimension(size, size2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mLayoutWidth > 0 && this.mLayoutHeight > 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
            layoutParams.width = this.mLayoutWidth;
            layoutParams.height = this.mLayoutHeight;
            setLayoutParams(layoutParams);
        }
    }

    public int getImageResource() {
        return this.mImageResource;
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        this.mImageView.setImageBitmap(this.mBitmap);
        CropOverlayView cropOverlayView = this.mCropOverlayView;
        if (cropOverlayView != null) {
            cropOverlayView.resetCropOverlayView();
        }
    }

    public void setImageBitmap(Bitmap bitmap, ExifInterface exifInterface) {
        if (bitmap != null) {
            if (exifInterface == null) {
                setImageBitmap(bitmap);
                return;
            }
            Matrix matrix = new Matrix();
            int attributeInt = exifInterface.getAttributeInt("Orientation", 1);
            int i = attributeInt != 3 ? attributeInt != 6 ? attributeInt != 8 ? -1 : 270 : 90 : 180;
            if (i == -1) {
                setImageBitmap(bitmap);
                return;
            }
            matrix.postRotate((float) i);
            setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
            bitmap.recycle();
        }
    }

    public void setImageResource(int i) {
        if (i != 0) {
            setImageBitmap(BitmapFactory.decodeResource(getResources(), i));
        }
    }

    public Bitmap getCroppedImage() {
        Rect bitmapRectCenterInside = ImageViewUtil.getBitmapRectCenterInside(this.mBitmap, this.mImageView);
        float width = ((float) this.mBitmap.getWidth()) / ((float) bitmapRectCenterInside.width());
        float height = ((float) this.mBitmap.getHeight()) / ((float) bitmapRectCenterInside.height());
        return Bitmap.createBitmap(this.mBitmap, (int) ((Edge.LEFT.getCoordinate() - ((float) bitmapRectCenterInside.left)) * width), (int) ((Edge.TOP.getCoordinate() - ((float) bitmapRectCenterInside.top)) * height), (int) (Edge.getWidth() * width), (int) (Edge.getHeight() * height));
    }

    public RectF getActualCropRect() {
        Rect bitmapRectCenterInside = ImageViewUtil.getBitmapRectCenterInside(this.mBitmap, this.mImageView);
        float width = ((float) this.mBitmap.getWidth()) / ((float) bitmapRectCenterInside.width());
        float height = ((float) this.mBitmap.getHeight()) / ((float) bitmapRectCenterInside.height());
        float coordinate = (Edge.LEFT.getCoordinate() - ((float) bitmapRectCenterInside.left)) * width;
        float coordinate2 = (Edge.TOP.getCoordinate() - ((float) bitmapRectCenterInside.top)) * height;
        return new RectF(Math.max(0.0f, coordinate), Math.max(0.0f, coordinate2), Math.min((float) this.mBitmap.getWidth(), (Edge.getWidth() * width) + coordinate), Math.min((float) this.mBitmap.getHeight(), (Edge.getHeight() * height) + coordinate2));
    }

    public void setFixedAspectRatio(boolean z) {
        this.mCropOverlayView.setFixedAspectRatio(z);
    }

    public void setGuidelines(int i) {
        this.mCropOverlayView.setGuidelines(i);
    }

    public void setAspectRatio(int i, int i2) {
        this.mAspectRatioX = i;
        this.mCropOverlayView.setAspectRatioX(this.mAspectRatioX);
        this.mAspectRatioY = i2;
        this.mCropOverlayView.setAspectRatioY(this.mAspectRatioY);
    }

    public void rotateImage(int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        Bitmap bitmap = this.mBitmap;
        this.mBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.mBitmap.getHeight(), matrix, true);
        setImageBitmap(this.mBitmap);
        this.mDegreesRotated += i;
        this.mDegreesRotated %= 360;
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.crop_image_view, this, true);
        this.mImageView = (ImageView) inflate.findViewById(R.id.ImageView_image);
        setImageResource(this.mImageResource);
        this.mCropOverlayView = (CropOverlayView) inflate.findViewById(R.id.CropOverlayView);
        this.mCropOverlayView.setInitialAttributeValues(this.mGuidelines, this.mFixAspectRatio, this.mAspectRatioX, this.mAspectRatioY);
    }

    private static int getOnMeasureSpec(int i, int i2, int i3) {
        if (i == 1073741824) {
            return i2;
        }
        if (i == Integer.MIN_VALUE) {
            i3 = Math.min(i3, i2);
        }
        return i3;
    }
}
