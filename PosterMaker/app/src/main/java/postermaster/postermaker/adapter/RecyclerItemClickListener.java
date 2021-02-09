package postermaster.postermaker.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements OnItemTouchListener {
    GestureDetector mGestureDetector;
    private OnItemClickListener mListener;

    class C02441 extends SimpleOnGestureListener {
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return true;
        }

        C02441() {
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    public RecyclerItemClickListener(Context context, OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
        this.mGestureDetector = new GestureDetector(context, new C02441());
    }

    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        View findChildViewUnder = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (!(findChildViewUnder == null || this.mListener == null || !this.mGestureDetector.onTouchEvent(motionEvent))) {
            this.mListener.onItemClick(findChildViewUnder, recyclerView.getChildPosition(findChildViewUnder));
        }
        return false;
    }
}
