package com.gummybutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.ItemTouchHelper.Callback;

import postermaster.postermaker.R;

public class GummyButton extends FrameLayout implements ScaleAnimationFinishedListener {
    private OnClickListener action;
    private int durationXPressed;
    private int durationXUnpressed;
    private int durationYPressed;
    private int durationYUnpressed;
    private boolean executeAction;
    private Rect hitRect;
    private ScaleAnimatedView scaleAnimatedView;
    private float scalePressed;
    private float scaleUnpressed;

    public interface OnClickListener {
        void onClick(MotionEvent motionEvent);
    }

    public GummyButton(Context context) {
        super(context);
        init(null);
    }

    public GummyButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public GummyButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        setWillNotDraw(false);
        this.scaleAnimatedView = new ScaleAnimatedView(this);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.GummyButton, 0, 0);
            try {
                this.scaleUnpressed = obtainStyledAttributes.getFloat(0, 1.0f);
                this.scalePressed = obtainStyledAttributes.getFloat(1, 0.8f);
                this.durationXPressed = obtainStyledAttributes.getInt(2, 75);
                this.durationYPressed = obtainStyledAttributes.getInt(3, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                this.durationXUnpressed = obtainStyledAttributes.getInt(4, 50);
                this.durationYUnpressed = obtainStyledAttributes.getInt(5, 150);
            } finally {
                if (obtainStyledAttributes != null) {
                    obtainStyledAttributes.recycle();
                }
            }
        }
    }

    public float getScaleUnpressed() {
        return this.scaleUnpressed;
    }

    public void setScaleUnpressed(float f) {
        this.scaleUnpressed = f;
    }

    public float getScalePressed() {
        return this.scalePressed;
    }

    public void setScalePressed(float f) {
        this.scalePressed = f;
    }

    public int getDurationXPressed() {
        return this.durationXPressed;
    }

    public void setDurationXPressed(int i) {
        this.durationXPressed = i;
    }

    public int getDurationYPressed() {
        return this.durationYPressed;
    }

    public void setDurationYPressed(int i) {
        this.durationYPressed = i;
    }

    public int getDurationXUnpressed() {
        return this.durationXUnpressed;
    }

    public void setDurationXUnpressed(int i) {
        this.durationXUnpressed = i;
    }

    public int getDurationYUnpressed() {
        return this.durationYUnpressed;
    }

    public void setDurationYUnpressed(int i) {
        this.durationYUnpressed = i;
    }

    public void setAction(OnClickListener onClickListener) {
        this.action = onClickListener;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.scaleAnimatedView.removeOnAnimationFinishedListener(this);
        if (motionEvent.getAction() == 0) {
            this.hitRect = new Rect(getLeft(), getTop(), getRight(), getBottom());
            this.executeAction = true;
            animateButtonPressed();
            return true;
        } else if (motionEvent.getAction() == 3) {
            this.executeAction = false;
            onTouchEnded();
            return true;
        } else if (motionEvent.getAction() == 2) {
            if (this.hitRect.contains(getLeft() + ((int) motionEvent.getX()), getTop() + ((int) motionEvent.getY()))) {
                return true;
            }
            this.executeAction = false;
            onTouchEnded();
            return true;
        } else if (motionEvent.getAction() != 1) {
            return false;
        } else {
            onTouchEnded();
            if (this.executeAction) {
                OnClickListener onClickListener = this.action;
                if (onClickListener != null) {
                    onClickListener.onClick(motionEvent);
                    return true;
                }
            }
            return true;
        }
    }

    public void onScaleAnimationFinished(View view) {
        this.scaleAnimatedView.removeOnAnimationFinishedListener(this);
        animateButtonUnpressed();
    }

    private void animateButtonPressed() {
        this.scaleAnimatedView.startScaleAnimation(getScaleX(), this.scalePressed, getScaleY(), this.scalePressed, this.durationXPressed, this.durationYPressed);
    }

    private void animateButtonUnpressed() {
        this.scaleAnimatedView.startScaleAnimation(getScaleX(), this.scaleUnpressed, getScaleY(), this.scaleUnpressed, this.durationXUnpressed, this.durationYUnpressed);
    }

    private void onTouchEnded() {
        if (this.scaleAnimatedView.isNotAnimating()) {
            animateButtonUnpressed();
        } else {
            this.scaleAnimatedView.addOnAnimationFinishedListener(this);
        }
    }
}
