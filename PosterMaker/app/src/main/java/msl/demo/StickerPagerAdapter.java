package msl.demo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class StickerPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private ArrayList<CategoryRowInfo> dataList = null;
    private HashMap<String, StickerGridFragment> hashMap = new HashMap<>();

    public StickerPagerAdapter(Context context2, FragmentManager fragmentManager, ArrayList<CategoryRowInfo> arrayList) {
        super(fragmentManager);
        this.context = context2;
        this.dataList = arrayList;
    }

    public Fragment getItem(int i) {
        String category_name = ((CategoryRowInfo) this.dataList.get(i)).getCATEGORY_NAME();
        StickerGridFragment stickerGridFragment = new StickerGridFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryName", category_name);
        bundle.putInt("totalItems", ((CategoryRowInfo) this.dataList.get(i)).getTOTAL_ITEMS());
        stickerGridFragment.setArguments(bundle);
        this.hashMap.put(category_name, stickerGridFragment);
        StringBuilder sb = new StringBuilder();
        sb.append("Not Contain : ");
        sb.append(category_name);
        Log.i("testing", sb.toString());
        return stickerGridFragment;
    }

    public CharSequence getPageTitle(int i) {
        return ((CategoryRowInfo) this.dataList.get(i)).getCATEGORY_NAME();
    }

    public int getCount() {
        return this.dataList.size();
    }
}
