package msl.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageViewList extends ImageView {
    public SquareImageViewList(Context context) {
        super(context);
    }

    public SquareImageViewList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(i2, i2);
    }
}
