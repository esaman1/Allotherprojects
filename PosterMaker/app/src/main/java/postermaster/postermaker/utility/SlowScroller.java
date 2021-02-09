package postermaster.postermaker.utility;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class SlowScroller extends Scroller {
    private int mScrollDuration = 800;

    public SlowScroller(Context context) {
        super(context);
    }

    public SlowScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        super.startScroll(i, i2, i3, i4, this.mScrollDuration);
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        super.startScroll(i, i2, i3, i4, this.mScrollDuration);
    }
}
