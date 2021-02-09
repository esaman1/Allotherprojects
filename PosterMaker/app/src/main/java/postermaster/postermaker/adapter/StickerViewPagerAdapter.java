package postermaster.postermaker.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import  androidx.fragment.app.FragmentPagerAdapter;
import postermaster.postermaker.R;
import postermaster.postermaker.sticker_fragment.StickersFragment;

public class StickerViewPagerAdapter extends FragmentPagerAdapter {
    private String[] TITLES;
    private Context _context;
    String[] cateName = {"offer", "sale", "banner", "sports", "ribbon", "birth", "decorat", "party", "music", "festival", "love", "college", "circle", "coffee", "cares", "nature", "word", "hallow", "animal", "cartoon", "shape", "white"};

    public StickerViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this._context = context;
        this.TITLES = new String[]{context.getResources().getString(R.string.cat1), context.getResources().getString(R.string.cat2), context.getResources().getString(R.string.cat3), context.getResources().getString(R.string.cat4), context.getResources().getString(R.string.cat5), context.getResources().getString(R.string.cat6), context.getResources().getString(R.string.cat7), context.getResources().getString(R.string.cat8), context.getResources().getString(R.string.cat9), context.getResources().getString(R.string.cat10), context.getResources().getString(R.string.cat11), context.getResources().getString(R.string.cat12), context.getResources().getString(R.string.cat13), context.getResources().getString(R.string.cat14), context.getResources().getString(R.string.cat15), context.getResources().getString(R.string.cat16), context.getResources().getString(R.string.cat17), context.getResources().getString(R.string.cat18), context.getResources().getString(R.string.cat19), context.getResources().getString(R.string.cat20), context.getResources().getString(R.string.cat21), context.getResources().getString(R.string.cat22)};
    }

    public Fragment getItem(int i) {
        String str = this.cateName[i];
        StickersFragment stickersFragment = new StickersFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryName", str);
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(i);
        bundle.putString("positionIs", sb.toString());
        stickersFragment.setArguments(bundle);
        return stickersFragment;
    }

    public CharSequence getPageTitle(int i) {
        return this.TITLES[i];
    }

    public int getCount() {
        return this.TITLES.length;
    }
}
