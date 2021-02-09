package msl.textmodule;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

public class AutoResizeTextView extends AppCompatTextView {
    private static final int NO_LINE_LIMIT = -1;
    private final RectF _availableSpaceRect;
    private boolean _initialized;
    private int _maxLines;
    private float _maxTextSize;
    private float _minTextSize;
    /* access modifiers changed from: private */
    public TextPaint _paint;
    private final SizeTester _sizeTester;
    /* access modifiers changed from: private */
    public float _spacingAdd;
    /* access modifiers changed from: private */
    public float _spacingMult;
    /* access modifiers changed from: private */
    public int _widthLimit;

    class C06571 implements SizeTester {
        final RectF textRect = new RectF();

        C06571() {
        }

        @TargetApi(16)
        public int onTestSize(int i, RectF rectF) {
            String str;
            AutoResizeTextView.this._paint.setTextSize((float) i);
            TransformationMethod transformationMethod = AutoResizeTextView.this.getTransformationMethod();
            if (transformationMethod != null) {
                str = transformationMethod.getTransformation(AutoResizeTextView.this.getText(), AutoResizeTextView.this).toString();
            } else {
                str = AutoResizeTextView.this.getText().toString();
            }
            if (AutoResizeTextView.this.getMaxLines() == 1) {
                this.textRect.bottom = AutoResizeTextView.this._paint.getFontSpacing();
                this.textRect.right = AutoResizeTextView.this._paint.measureText(str);
            } else {
                StaticLayout staticLayout = new StaticLayout(str, AutoResizeTextView.this._paint, AutoResizeTextView.this._widthLimit, Alignment.ALIGN_NORMAL, AutoResizeTextView.this._spacingMult, AutoResizeTextView.this._spacingAdd, true);
                if (AutoResizeTextView.this.getMaxLines() != -1 && staticLayout.getLineCount() > AutoResizeTextView.this.getMaxLines()) {
                    return 1;
                }
                this.textRect.bottom = (float) staticLayout.getHeight();
                int lineCount = staticLayout.getLineCount();
                int i2 = -1;
                for (int i3 = 0; i3 < lineCount; i3++) {
                    int lineEnd = staticLayout.getLineEnd(i3);
                    if (i3 < lineCount - 1 && lineEnd > 0 && !AutoResizeTextView.this.isValidWordWrap(str.charAt(lineEnd - 1), str.charAt(lineEnd))) {
                        return 1;
                    }
                    if (((float) i2) < staticLayout.getLineRight(i3) - staticLayout.getLineLeft(i3)) {
                        i2 = ((int) staticLayout.getLineRight(i3)) - ((int) staticLayout.getLineLeft(i3));
                    }
                }
                this.textRect.right = (float) i2;
            }
            this.textRect.offsetTo(0.0f, 0.0f);
            return rectF.contains(this.textRect) ? -1 : 1;
        }
    }

    private interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    public boolean isValidWordWrap(char c, char c2) {
        return c == ' ' || c == '-';
    }

    public AutoResizeTextView(Context context) {
        this(context, null, 16842884);
    }

    public AutoResizeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public AutoResizeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this._availableSpaceRect = new RectF();
        this._spacingMult = 1.0f;
        this._spacingAdd = 0.0f;
        this._initialized = false;
        this._minTextSize = TypedValue.applyDimension(2, 12.0f, getResources().getDisplayMetrics());
        this._maxTextSize = getTextSize();
        this._paint = new TextPaint(getPaint());
        if (this._maxLines == 0) {
            this._maxLines = -1;
        }
        this._sizeTester = new C06571();
        this._initialized = true;
    }

    public void setAllCaps(boolean z) {
        super.setAllCaps(z);
        adjustTextSize();
    }

    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        adjustTextSize();
    }

    public void setTextSize(float f) {
        this._maxTextSize = f;
        adjustTextSize();
    }

    public void setMaxLines(int i) {
        super.setMaxLines(i);
        this._maxLines = i;
        adjustTextSize();
    }

    public int getMaxLines() {
        return this._maxLines;
    }

    public void setSingleLine() {
        super.setSingleLine();
        this._maxLines = 1;
        adjustTextSize();
    }

    public void setSingleLine(boolean z) {
        super.setSingleLine(z);
        if (z) {
            this._maxLines = 1;
        } else {
            this._maxLines = -1;
        }
        adjustTextSize();
    }

    public void setLines(int i) {
        super.setLines(i);
        this._maxLines = i;
        adjustTextSize();
    }

    public void setTextSize(int i, float f) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        this._maxTextSize = TypedValue.applyDimension(i, f, resources.getDisplayMetrics());
        adjustTextSize();
    }

    public void setLineSpacing(float f, float f2) {
        super.setLineSpacing(f, f2);
        this._spacingMult = f2;
        this._spacingAdd = f;
    }

    public void setMinTextSize(float f) {
        this._minTextSize = f;
        adjustTextSize();
    }

    private void adjustTextSize() {
        if (this._initialized) {
            int i = (int) this._minTextSize;
            int measuredHeight = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
            this._widthLimit = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            if (this._widthLimit > 0) {
                this._paint = new TextPaint(getPaint());
                RectF rectF = this._availableSpaceRect;
                rectF.right = (float) this._widthLimit;
                rectF.bottom = (float) measuredHeight;
                superSetTextSize(i);
            }
        }
    }

    private void superSetTextSize(int i) {
        super.setTextSize(0, (float) binarySearch(i, (int) this._maxTextSize, this._sizeTester, this._availableSpaceRect));
    }

    private int binarySearch(int i, int i2, SizeTester sizeTester, RectF rectF) {
        int i3 = i2 - 1;
        int i4 = i;
        while (i <= i3) {
            int i5 = (i + i3) >>> 1;
            int onTestSize = sizeTester.onTestSize(i5, rectF);
            if (onTestSize < 0) {
                int i6 = i5 + 1;
                i4 = i;
                i = i6;
            } else if (onTestSize <= 0) {
                return i5;
            } else {
                i4 = i5 - 1;
                i3 = i4;
            }
        }
        return i4;
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        adjustTextSize();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            adjustTextSize();
        }
    }
}
