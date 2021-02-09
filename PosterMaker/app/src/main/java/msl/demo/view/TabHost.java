package msl.demo.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import postermaster.postermaker.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import msl.demo.HoriontalListAdapter;

public class TabHost extends HorizontalScrollView {
    private ContainerHost containerHost;
    private Context ctx;
    /* access modifiers changed from: private */
    public OnTabClickListener listener;
    private LinearLayout llayout;
    private String selectedTabName = null;
    private ArrayList<String> tabNameList = new ArrayList<>();
    private Typeface ttf;

    public interface OnTabClickListener {
        void onTabClick(int i, String str);
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.listener = onTabClickListener;
    }

    public TabHost(Context context) {
        super(context);
        intiTabHost(context);
    }

    public TabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        intiTabHost(context);
    }

    public TabHost(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        intiTabHost(context);
    }

    private void intiTabHost(Context context) {
        this.ctx = context;
        setOverScrollMode(2);
        setHorizontalScrollBarEnabled(false);
        this.llayout = new LinearLayout(this.ctx);
        new LayoutParams(-1, -1);
        addView(this.llayout);
    }

    public void attachContainerHost(ContainerHost containerHost2) {
        this.containerHost = containerHost2;
    }

    public ContainerHost getContainerHost() {
        return this.containerHost;
    }

    public void addTab(String str) {
        this.llayout.addView(createTabLayout(str));
        this.llayout.postInvalidate();
        this.llayout.requestLayout();
        this.tabNameList.add(str);
    }

    public void addTab(String str, HoriontalListAdapter horiontalListAdapter) {
        this.llayout.addView(createTabLayout(str));
        this.llayout.postInvalidate();
        this.llayout.requestLayout();
        ContainerHost containerHost2 = this.containerHost;
        if (containerHost2 != null) {
            containerHost2.addAdapter(str, horiontalListAdapter);
        }
        this.tabNameList.add(str);
    }

    public void addTabAtIndex(String str, int i) {
        this.llayout.addView(createTabLayout(str), i);
        this.llayout.postInvalidate();
        this.llayout.requestLayout();
        this.tabNameList.add(i, str);
    }

    public void addTabAtIndex(String str, int i, HoriontalListAdapter horiontalListAdapter) {
        this.llayout.addView(createTabLayout(str), i);
        this.llayout.postInvalidate();
        this.llayout.requestLayout();
        ContainerHost containerHost2 = this.containerHost;
        if (containerHost2 != null) {
            containerHost2.addAdapter(str, horiontalListAdapter);
        }
        this.tabNameList.add(i, str);
    }

    public void removeTabAtIndex(int i) {
        this.llayout.removeViewAt(i);
        this.llayout.postInvalidate();
        this.llayout.requestLayout();
        ContainerHost containerHost2 = this.containerHost;
        if (containerHost2 != null) {
            containerHost2.removeAdapter((String) this.tabNameList.get(i));
        }
        this.tabNameList.remove(i);
        if (!this.tabNameList.contains(this.selectedTabName)) {
            this.selectedTabName = null;
        }
    }

    public void removeAllTabs() {
        while (this.tabNameList.size() > 0) {
            removeTabAtIndex(0);
        }
    }

    public void removeTabsAtIndex(int[] iArr) {
        Arrays.sort(iArr);
        for (int length = iArr.length - 1; length >= 0; length--) {
            this.llayout.removeViewAt(iArr[length]);
            ContainerHost containerHost2 = this.containerHost;
            if (containerHost2 != null) {
                containerHost2.removeAdapter((String) this.tabNameList.get(iArr[length]));
            }
            this.tabNameList.remove(iArr[length]);
        }
        if (!this.tabNameList.contains(this.selectedTabName)) {
            this.selectedTabName = null;
        }
        this.llayout.postInvalidate();
        this.llayout.requestLayout();
    }

    public String getSelectedTabName() {
        return this.selectedTabName;
    }

    public List<String> getTabsNameList() {
        return this.tabNameList;
    }

    public int getTabsCount() {
        return this.tabNameList.size();
    }

    public void setTabSelected(int i) {
        int childCount = this.llayout.getChildCount();
        if (childCount != 0 && i >= 0 && i < childCount) {
            for (int i2 = 0; i2 < childCount; i2++) {
                this.llayout.getChildAt(i2).setBackgroundResource(R.drawable.sticker_border_inact);
            }
            this.llayout.getChildAt(i).setBackgroundResource(R.drawable.sticker_border_act);
            this.selectedTabName = (String) this.tabNameList.get(i);
            ContainerHost containerHost2 = this.containerHost;
            if (containerHost2 != null) {
                containerHost2.changeAdapter((String) this.tabNameList.get(i));
            }
        }
    }

    public boolean containTab(String str) {
        return this.tabNameList.contains(str);
    }

    public void setTabSelected(String str) {
        if (str.contains(str)) {
            setTabSelected(this.tabNameList.indexOf(str));
        }
    }

    private RelativeLayout createTabLayout(final String str) {
        RelativeLayout relativeLayout = (RelativeLayout) inflate(this.ctx, R.layout.tab_item, null);
        relativeLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int indexOfChild = ((LinearLayout) view.getParent()).indexOfChild(view);
                if (TabHost.this.listener != null) {
                    TabHost.this.listener.onTabClick(indexOfChild, str);
                }
                TabHost.this.setTabSelected(indexOfChild);
            }
        });
        ((TextView) relativeLayout.findViewById(R.id.tab_item_txt)).setText(str);
        ((TextView) relativeLayout.findViewById(R.id.tab_item_txt)).setTypeface(this.ttf);
        return relativeLayout;
    }
}
