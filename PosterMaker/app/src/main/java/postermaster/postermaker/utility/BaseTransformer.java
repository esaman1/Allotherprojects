package postermaster.postermaker.utility;

import androidx.viewpager.widget.ViewPager.PageTransformer;
import android.view.View;

public abstract class BaseTransformer implements PageTransformer {
    /* access modifiers changed from: protected */
    public boolean hideOffscreenPages() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isPagingEnabled() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onPostTransform(View view, float f) {
    }

    /* access modifiers changed from: protected */
    public abstract void onTransform(View view, float f);

    public void transformPage(View view, float f) {
        onPreTransform(view, f);
        onTransform(view, f);
        onPostTransform(view, f);
    }

    /* access modifiers changed from: protected */
    public void onPreTransform(View view, float f) {
        float width = (float) view.getWidth();
        float f2 = 0.0f;
        view.setRotationX(0.0f);
        view.setRotationY(0.0f);
        view.setRotation(0.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setPivotX(0.0f);
        view.setPivotY(0.0f);
        view.setTranslationY(0.0f);
        view.setTranslationX(!isPagingEnabled() ? (-width) * f : 0.0f);
        if (hideOffscreenPages()) {
            if (f > -1.0f && f < 1.0f) {
                f2 = 1.0f;
            }
            view.setAlpha(f2);
            return;
        }
        view.setAlpha(1.0f);
    }
}