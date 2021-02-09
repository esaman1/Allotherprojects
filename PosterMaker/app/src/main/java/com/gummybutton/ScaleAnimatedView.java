package com.gummybutton;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class ScaleAnimatedView {
    private List<ScaleAnimationFinishedListener> animationFinishedListeners = new ArrayList();
    private int durationX;
    private int durationY;
    /* access modifiers changed from: private */
    public boolean firedCallback;
    /* access modifiers changed from: private */
    public boolean isAnimatingX;
    /* access modifiers changed from: private */
    public boolean isAnimatingY;
    private ValueAnimator scaleAnimationX;
    private ValueAnimator scaleAnimationY;
    private float scaleFromX;
    private float scaleFromY;
    private float scaleToX;
    private float scaleToY;
    /* access modifiers changed from: private */
    public View view;

    class C02141 implements AnimatorUpdateListener {
        C02141() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            ScaleAnimatedView.this.view.setScaleX(Float.parseFloat(valueAnimator.getAnimatedValue().toString()));
        }
    }

    class C02152 implements AnimatorListener {
        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }

        C02152() {
        }

        public void onAnimationEnd(Animator animator) {
            ScaleAnimatedView.this.isAnimatingX = false;
            if (ScaleAnimatedView.this.isNotAnimating() && !ScaleAnimatedView.this.firedCallback) {
                ScaleAnimatedView.this.firedCallback = true;
                ScaleAnimatedView.this.notifyOnAnimationFinished();
            }
        }
    }

    class C02163 implements AnimatorUpdateListener {
        C02163() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            ScaleAnimatedView.this.view.setScaleY(Float.parseFloat(valueAnimator.getAnimatedValue().toString()));
        }
    }

    class C02174 implements AnimatorListener {
        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }

        C02174() {
        }

        public void onAnimationEnd(Animator animator) {
            ScaleAnimatedView.this.isAnimatingY = false;
            if (ScaleAnimatedView.this.isNotAnimating() && !ScaleAnimatedView.this.firedCallback) {
                ScaleAnimatedView.this.firedCallback = true;
                ScaleAnimatedView.this.notifyOnAnimationFinished();
            }
        }
    }

    private boolean isAnimationInitialized(ValueAnimator valueAnimator) {
        return valueAnimator != null;
    }

    public ScaleAnimatedView(View view2) {
        this.view = view2;
    }

    public void startScaleAnimation(float f, float f2, float f3, float f4, int i, int i2) {
        this.scaleFromX = f;
        this.scaleToX = f2;
        this.scaleFromY = f3;
        this.scaleToY = f4;
        this.durationX = i;
        this.durationY = i2;
        stopScaleAnimation();
        executeScaleAnimation();
    }

    public View getView() {
        return this.view;
    }

    private void executeScaleAnimation() {
        this.firedCallback = false;
        this.isAnimatingX = true;
        this.scaleAnimationX = ValueAnimator.ofFloat(new float[]{this.scaleFromX, this.scaleToX});
        this.scaleAnimationX.setDuration((long) this.durationX);
        this.scaleAnimationX.addUpdateListener(new C02141());
        this.scaleAnimationX.addListener(new C02152());
        this.scaleAnimationX.start();
        this.isAnimatingY = true;
        this.scaleAnimationY = ValueAnimator.ofFloat(new float[]{this.scaleFromY, this.scaleToY});
        this.scaleAnimationY.setDuration((long) this.durationY);
        this.scaleAnimationY.addUpdateListener(new C02163());
        this.scaleAnimationY.addListener(new C02174());
        this.scaleAnimationY.start();
    }

    public void stopScaleAnimation() {
        if (isAnimationInitialized(this.scaleAnimationX)) {
            this.scaleAnimationX.removeAllUpdateListeners();
            this.scaleAnimationX.end();
            this.scaleAnimationX = null;
            this.isAnimatingX = false;
            this.view.setScaleX(1.0f);
        }
        if (isAnimationInitialized(this.scaleAnimationY)) {
            this.scaleAnimationY.removeAllUpdateListeners();
            this.scaleAnimationY.end();
            this.scaleAnimationY = null;
            this.isAnimatingY = false;
            this.view.setScaleY(1.0f);
        }
    }

    public boolean isNotAnimating() {
        return !this.isAnimatingX && !this.isAnimatingY;
    }

    public void addOnAnimationFinishedListener(ScaleAnimationFinishedListener scaleAnimationFinishedListener) {
        this.animationFinishedListeners.add(scaleAnimationFinishedListener);
    }

    public void removeOnAnimationFinishedListener(ScaleAnimationFinishedListener scaleAnimationFinishedListener) {
        this.animationFinishedListeners.remove(scaleAnimationFinishedListener);
    }

    /* access modifiers changed from: private */
    public void notifyOnAnimationFinished() {
        for (ScaleAnimationFinishedListener onScaleAnimationFinished : this.animationFinishedListeners) {
            onScaleAnimationFinished.onScaleAnimationFinished(this.view);
        }
    }
}
