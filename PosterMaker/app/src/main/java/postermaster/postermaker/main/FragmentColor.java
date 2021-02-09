package postermaster.postermaker.main;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import postermaster.postermaker.R;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

public class FragmentColor extends Fragment {
    /* access modifiers changed from: private */
    public int bColor = Color.parseColor("#4149b6");
    OnGetImageOnTouch getImage;
    String hex = "";
    ImageView img_color;
    float screenWidth;

    class C02851 implements OnClickListener {

        class C06171 implements OnAmbilWarnaListener {
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
            }

            C06171() {
            }

            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                FragmentColor.this.updateColor(i);
            }
        }

        C02851() {
        }

        public void onClick(View view) {
            new AmbilWarnaDialog(FragmentColor.this.getActivity(), FragmentColor.this.bColor, new C06171()).show();
        }
    }

    class C02862 implements OnClickListener {
        C02862() {
        }

        public void onClick(View view) {
            if (!FragmentColor.this.hex.equals("")) {
                FragmentColor.this.getImage.ongetPosition(0, "Color", FragmentColor.this.hex);
            }
        }
    }

    class C06183 implements OnAmbilWarnaListener {
        public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
        }

        C06183() {
        }

        public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
            FragmentColor.this.updateColor(i);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.getImage = (OnGetImageOnTouch) getActivity();
        View inflate = layoutInflater.inflate(R.layout.fragment_color, viewGroup, false);
        this.img_color = (ImageView) inflate.findViewById(R.id.img_color);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = (float) displayMetrics.widthPixels;
        ((ImageButton) inflate.findViewById(R.id.img_colorPicker)).setOnClickListener(new C02851());
        this.img_color.setOnClickListener(new C02862());
        ((TextView) inflate.findViewById(R.id.textH)).setTypeface(Constants.getHeaderTypeface(getActivity()));
        ((TextView) inflate.findViewById(R.id.txtGal)).setTypeface(Constants.getTextTypeface(getActivity()));
        return inflate;
    }

//    public void setMenuVisibility(boolean z) {
//        if (z) {
//            try {
//                new AmbilWarnaDialog(getActivity(), this.bColor, new C06183()).show();
//            } catch (NullPointerException unused) {
//            }
//        }
//        super.setMenuVisibility(z);
//    }

    /* access modifiers changed from: private */
    public void updateColor(int i) {
        this.bColor = i;
        this.hex = Integer.toHexString(i);
        ImageView imageView = this.img_color;
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        sb.append(this.hex);
        imageView.setBackgroundColor(Color.parseColor(sb.toString()));
        this.getImage.ongetPosition(0, "Color", this.hex);
    }
}
