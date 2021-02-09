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

public class FragmentTexture extends Fragment {
    Editor editor;
    OnGetImageOnTouch getImage;
    /* access modifiers changed from: private */
    public GridView gridView;
    int pos = 0;
    SharedPreferences preferences;
    SharedPreferences prefs;
    Typeface ttf;

    class C02981 implements OnItemClickListener {
        C02981() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (i > 8 && i < 15) {
                FragmentTexture.this.getImage.ongetPosition(i, "Texture", "");
            } else if (i <= 14) {
                FragmentTexture.this.getImage.ongetPosition(i, "Texture", "");
            } else {
                FragmentTexture.this.getImage.ongetPosition(i, "Texture", "");
            }
        }
    }

    class C03069 extends BroadcastReceiver {
        C03069() {
        }

        public void onReceive(Context context, Intent intent) {
            FragmentTexture.this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
            if ((!intent.getStringExtra("Billing").equals("BlackFriday") || !FragmentTexture.this.preferences.getBoolean("isBFDPurchased", false)) && ((!intent.getStringExtra("Billing").equals("Sports") || !FragmentTexture.this.preferences.getBoolean("isSEDPurchased", false)) && ((!intent.getStringExtra("Billing").equals("MonthlySub") || !FragmentTexture.this.preferences.getBoolean("isMSPurchased", false)) && ((!intent.getStringExtra("Billing").equals("YearlySub") || !FragmentTexture.this.preferences.getBoolean("isYSPurchased", false)) && (!intent.getStringExtra("Billing").equals("BgRwmAds") || !FragmentTexture.this.preferences.getBoolean("isAdsDisabled", false)))))) {
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(intent.getStringExtra("Billing"));
                Log.e("Billing", sb.toString());
                return;
            }
            if (intent.getStringExtra("Billing").equals("MonthlySub") || intent.getStringExtra("Billing").equals("YearlySub")) {
                Editor edit = FragmentTexture.this.preferences.edit();
                edit.putBoolean("removeWatermark", true);
                edit.putBoolean("isAdsDisabled", true);
                edit.putBoolean("isBFDPurchased", true);
                edit.putBoolean("isHSDPurchased", true);
                edit.putBoolean("isSEDPurchased", true);
                edit.commit();
                FragmentTexture.this.editor.putString("rating123", "yes");
                FragmentTexture.this.editor.putString("purchase", "yes");
                FragmentTexture.this.editor.commit();
            }
            if (intent.getStringExtra("Billing").equals("BgRwmAds") && FragmentTexture.this.preferences.getBoolean("isAdsDisabled", false)) {
                FragmentTexture.this.editor.putString("rating123", "yes");
                FragmentTexture.this.editor.putString("purchase", "yes");
                FragmentTexture.this.editor.commit();
                FragmentTexture.this.gridView.setAdapter(new FrameAdapter(FragmentTexture.this.getActivity(), "Texture", FragmentTexture.this.prefs));
                FragmentTexture.this.getImage.ongetPosition(FragmentTexture.this.pos, "Texture", "");
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
        this.gridView.setAdapter(new FrameAdapter(getActivity(), "Texture", this.prefs));
        this.gridView.setOnItemClickListener(new C02981());
        return inflate;
    }

    public void setMenuVisibility(boolean z) {
        if (z) {
            try {
                this.gridView.setAdapter(new FrameAdapter(getActivity(), "Texture", this.prefs));
            } catch (NullPointerException unused) {
            }
        }
        super.setMenuVisibility(z);
    }
}
