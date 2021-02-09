package postermaster.postermaker.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.File;

import postermaster.postermaker.R;

public class FragmentImage extends Fragment {
    private static final int SELECT_PICTURE_FROM_CAMERA = 9062;
    private static final int SELECT_PICTURE_FROM_GALLERY = 9072;
    File f9f;

    class C02961 implements OnClickListener {
        C02961() {
        }

        public void onClick(View view) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            FragmentImage.this.f9f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
            intent.putExtra("output", Uri.fromFile(FragmentImage.this.f9f));
            FragmentImage.this.getActivity().startActivityForResult(intent, FragmentImage.SELECT_PICTURE_FROM_CAMERA);
        }
    }

    class C02972 implements OnClickListener {
        C02972() {
        }

        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.GET_CONTENT");
            FragmentImage.this.getActivity().startActivityForResult(Intent.createChooser(intent, FragmentImage.this.getString(R.string.select_picture).toString()), FragmentImage.SELECT_PICTURE_FROM_GALLERY);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_image, viewGroup, false);
        ((ImageView) inflate.findViewById(R.id.btn_cam)).setOnClickListener(new C02961());
        ((ImageView) inflate.findViewById(R.id.btn_gal)).setOnClickListener(new C02972());
        ((TextView) inflate.findViewById(R.id.textH)).setTypeface(Constants.getHeaderTypeface(getActivity()));
        ((TextView) inflate.findViewById(R.id.txtCam)).setTypeface(Constants.getTextTypeface(getActivity()));
        ((TextView) inflate.findViewById(R.id.txtGal)).setTypeface(Constants.getTextTypeface(getActivity()));
        return inflate;
    }
}
