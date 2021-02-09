package postermaster.postermaker.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import postermaster.postermaker.R;
import postermaster.postermaker.adapter.FrameAdapter;

public class FragmentBackgrounds extends Fragment {
    Editor editor;
    OnGetImageOnTouch getImage;
    /* access modifiers changed from: private */
    public GridView gridView;
    int pos = 0;
    SharedPreferences preferences;
    SharedPreferences prefs;
    Typeface ttf;

    class C02761 implements OnItemClickListener {
        C02761() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            FragmentBackgrounds.this.getImage.ongetPosition(i, "Background", "");
        }
    }

    class C02849 extends BroadcastReceiver {
        C02849() {
        }

        public void onReceive(Context context, Intent intent) {
            FragmentBackgrounds.this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
            if ((!intent.getStringExtra("Billing").equals("BlackFriday") || !FragmentBackgrounds.this.preferences.getBoolean("isBFDPurchased", false)) && ((!intent.getStringExtra("Billing").equals("Sports") || !FragmentBackgrounds.this.preferences.getBoolean("isSEDPurchased", false)) && ((!intent.getStringExtra("Billing").equals("MonthlySub") || !FragmentBackgrounds.this.preferences.getBoolean("isMSPurchased", false)) && ((!intent.getStringExtra("Billing").equals("YearlySub") || !FragmentBackgrounds.this.preferences.getBoolean("isYSPurchased", false)) && (!intent.getStringExtra("Billing").equals("BgRwmAds") || !FragmentBackgrounds.this.preferences.getBoolean("isAdsDisabled", false)))))) {
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(intent.getStringExtra("Billing"));
                Log.e("Billing", sb.toString());
                return;
            }
            if (intent.getStringExtra("Billing").equals("MonthlySub") || intent.getStringExtra("Billing").equals("YearlySub")) {
                Editor edit = FragmentBackgrounds.this.preferences.edit();
                edit.putBoolean("removeWatermark", true);
                edit.putBoolean("isAdsDisabled", true);
                edit.putBoolean("isBFDPurchased", true);
                edit.putBoolean("isHSDPurchased", true);
                edit.putBoolean("isSEDPurchased", true);
                edit.commit();
                FragmentBackgrounds.this.editor.putString("rating123", "yes");
                FragmentBackgrounds.this.editor.putString("purchase", "yes");
                FragmentBackgrounds.this.editor.commit();
            }
            if (intent.getStringExtra("Billing").equals("BgRwmAds") && FragmentBackgrounds.this.preferences.getBoolean("isAdsDisabled", false)) {
                FragmentBackgrounds.this.editor.putString("rating123", "yes");
                FragmentBackgrounds.this.editor.putString("purchase", "yes");
                FragmentBackgrounds.this.editor.commit();
                FragmentBackgrounds.this.gridView.setAdapter(new FrameAdapter(FragmentBackgrounds.this.getActivity(), "Background", FragmentBackgrounds.this.prefs));
                FragmentBackgrounds.this.getImage.ongetPosition(FragmentBackgrounds.this.pos, "Background", "");
            }
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_my_backgund, viewGroup, false);
        this.editor = getActivity().getSharedPreferences("MY_PREFS_NAME", 0).edit();
        this.prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", 0);
        this.ttf = Constants.getTextTypeface(getActivity());
        this.getImage = (OnGetImageOnTouch) getActivity();
        this.gridView = (GridView) inflate.findViewById(R.id.gridview);
        this.gridView.setAdapter(new FrameAdapter(getActivity(), "Background", this.prefs));
        this.gridView.setOnItemClickListener(new C02761());
        return inflate;
    }

    public void setMenuVisibility(boolean z) {
        if (z) {
            try {
                this.gridView.setAdapter(new FrameAdapter(getActivity(), "Background", this.prefs));
            } catch (NullPointerException unused) {
            }
        }
        super.setMenuVisibility(z);
    }
}
