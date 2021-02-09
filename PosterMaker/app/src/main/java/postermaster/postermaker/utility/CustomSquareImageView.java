package postermaster.postermaker.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomSquareImageView extends ImageView {
    public CustomSquareImageView(Context context) {
        super(context);
    }

    public CustomSquareImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(i, i);
    }
}
