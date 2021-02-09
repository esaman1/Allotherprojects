package postermaster.postermaker.create;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class RepeatListener implements OnTouchListener {
    /* access modifiers changed from: private */
    public final OnClickListener clickListener;
    /* access modifiers changed from: private */
    public View downView;
    private ImageView guideline;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    private Runnable handlerRunnable = new C02501();
    private int initialInterval;
    /* access modifiers changed from: private */
    public final int normalInterval;

    class C02501 implements Runnable {
        C02501() {
        }

        public void run() {
            RepeatListener.this.handler.postDelayed(this, (long) RepeatListener.this.normalInterval);
            RepeatListener.this.clickListener.onClick(RepeatListener.this.downView);
        }
    }

    public RepeatListener(int i, int i2, ImageView imageView, OnClickListener onClickListener) {
        if (onClickListener == null) {
            throw new IllegalArgumentException("null runnable");
        } else if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("negative interval");
        } else {
            this.initialInterval = i;
            this.normalInterval = i2;
            this.clickListener = onClickListener;
            this.guideline = imageView;
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 3) {
            switch (action) {
                case 0:
                    if (this.guideline.getVisibility() == View.GONE) {
                        this.guideline.setVisibility(View.VISIBLE);
                    }
                    this.handler.removeCallbacks(this.handlerRunnable);
                    this.handler.postDelayed(this.handlerRunnable, (long) this.initialInterval);
                    this.downView = view;
                    this.downView.setPressed(true);
                    this.clickListener.onClick(view);
                    return true;
                case 1:
                    this.guideline.setVisibility(View.GONE);
                    break;
                default:
                    return false;
            }
        }
        this.handler.removeCallbacks(this.handlerRunnable);
        this.downView.setPressed(false);
        this.downView = null;
        return true;
    }
}
