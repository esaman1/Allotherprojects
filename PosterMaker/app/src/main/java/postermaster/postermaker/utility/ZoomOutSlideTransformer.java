package postermaster.postermaker.utility;

import android.view.View;

public class ZoomOutSlideTransformer extends BaseTransformer {
    private static final float MIN_ALPHA = 0.5f;
    private static final float MIN_SCALE = 0.85f;

    /* access modifiers changed from: protected */
    public void onTransform(View view, float f) {
        if (f >= -1.0f || f <= 1.0f) {
            float height = (float) view.getHeight();
            float max = Math.max(MIN_SCALE, 1.0f - Math.abs(f));
            float f2 = 1.0f - max;
            float f3 = (f2 * height) / 2.0f;
            float width = (((float) view.getWidth()) * f2) / 2.0f;
            view.setPivotY(height * MIN_ALPHA);
            if (f < 0.0f) {
                view.setTranslationX(width - (f3 / 2.0f));
            } else {
                view.setTranslationX((-width) + (f3 / 2.0f));
            }
            view.setScaleX(max);
            view.setScaleY(max);
            view.setAlpha((((max - MIN_SCALE) / 0.14999998f) * MIN_ALPHA) + MIN_ALPHA);
        }
    }
}
