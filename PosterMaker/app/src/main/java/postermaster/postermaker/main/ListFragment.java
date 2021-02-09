package postermaster.postermaker.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;
import com.woxthebox.draglistview.DragListView.DragListListenerAdapter;

import java.util.ArrayList;

import postermaster.postermaker.PosterActivity;
import postermaster.postermaker.R;
import postermaster.postermaker.adapter.ItemAdapter;

public class ListFragment extends Fragment {
    private ArrayList<View> arrView = new ArrayList<>();
    RelativeLayout lay_Notext;
    private DragListView mDragListView;
    /* access modifiers changed from: private */
    public ArrayList<Pair<Long, View>> mItemArray = new ArrayList<>();

    class C03082 implements OnClickListener {

        class C03071 implements Runnable {
            C03071() {
            }

            public void run() {
                PosterActivity.lay_container.setVisibility(View.GONE);
                PosterActivity.btn_layControls.setVisibility(View.VISIBLE);
            }
        }

        C03082() {
        }

        public void onClick(View view) {
            if (PosterActivity.lay_container.getVisibility() == View.VISIBLE) {
                PosterActivity.lay_container.animate().translationX((float) (-PosterActivity.lay_container.getRight())).setDuration(200).setInterpolator(new AccelerateInterpolator()).start();
                new Handler().postDelayed(new C03071(), 200);
            }
        }
    }

    class C06951 extends DragListListenerAdapter {
        public void onItemDragStarted(int i) {
        }

        C06951() {
        }

        public void onItemDragEnded(int i, int i2) {
            if (i != i2) {
                for (int size = ListFragment.this.mItemArray.size() - 1; size >= 0; size--) {
                    ((View) ((Pair) ListFragment.this.mItemArray.get(size)).second).bringToFront();
                }
                PosterActivity.txt_stkr_rel.requestLayout();
                PosterActivity.txt_stkr_rel.postInvalidate();
            }
        }
    }

    private static class MyDragItem extends DragItem {
        MyDragItem(Context context, int i) {
            super(context, i);
        }

        public void onBindDragView(View view, View view2) {
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
            view.draw(new Canvas(createBitmap));
            ((ImageView) view2.findViewById(R.id.backimg)).setImageBitmap(createBitmap);
        }
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.list_layout, viewGroup, false);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.swipe_refresh_layout);
        this.mDragListView = (DragListView) inflate.findViewById(R.id.drag_list_view);
        this.mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);
        this.mDragListView.setDragListListener(new C06951());
        ((TextView) inflate.findViewById(R.id.txt_Nolayers)).setTypeface(Constants.getTextTypeface(getActivity()));
        this.lay_Notext = (RelativeLayout) inflate.findViewById(R.id.lay_text);
        ((RelativeLayout) inflate.findViewById(R.id.lay_frame)).setOnClickListener(new C03082());
        return inflate;
    }

    public void getLayoutChild() {
        this.arrView.clear();
        this.mItemArray.clear();
        if (PosterActivity.txt_stkr_rel.getChildCount() != 0) {
            this.lay_Notext.setVisibility(View.GONE);
            for (int childCount = PosterActivity.txt_stkr_rel.getChildCount() - 1; childCount >= 0; childCount--) {
                this.mItemArray.add(new Pair(Long.valueOf((long) childCount), PosterActivity.txt_stkr_rel.getChildAt(childCount)));
                this.arrView.add(PosterActivity.txt_stkr_rel.getChildAt(childCount));
            }
        } else {
            this.lay_Notext.setVisibility(View.VISIBLE);
        }
        setupListRecyclerView();
    }

    private void setupListRecyclerView() {
        this.mDragListView.setLayoutManager(new LinearLayoutManager(getContext()));
        DragListView dragListView = this.mDragListView;
        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), this.mItemArray, R.layout.list_item, R.id.touch_rel, false);
        dragListView.setAdapter(itemAdapter, true);
        this.mDragListView.setCanDragHorizontally(false);
        this.mDragListView.setCustomDragItem(new MyDragItem(getContext(), R.layout.list_item));
    }
}
