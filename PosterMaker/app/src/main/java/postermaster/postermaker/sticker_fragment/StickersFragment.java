package postermaster.postermaker.sticker_fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;

import postermaster.postermaker.R;

public class StickersFragment extends Fragment {
    int asuncCount = 0;
    String catName;
    Editor editor;
    GridView grid;
    GetSnapListener onGetSnap;
    /* access modifiers changed from: private */
    public ProgressDialog pdia;
    int positn;
    SharedPreferences prefs;
    int size_list;
    ArrayList<String> stkrNameList = new ArrayList<>();
    ArrayList<Bitmap> thumbnails = new ArrayList<>();
    ArrayList<String> uri = new ArrayList<>();

    private class lordStickersAsync extends AsyncTask<String, String, Boolean> {
        ProgressDialog ringProgressDialog;

        private lordStickersAsync() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            this.ringProgressDialog = new ProgressDialog(StickersFragment.this.getActivity());
            this.ringProgressDialog.setMessage(StickersFragment.this.getResources().getString(R.string.plzwait));
            this.ringProgressDialog.setCancelable(false);
            this.ringProgressDialog.show();
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Maker Stickers/category1");
            if (file.exists()) {
                File[] listFiles = file.listFiles();
                int length = file.listFiles().length;
                for (int i = 0; i < length; i++) {
                    Options options = new Options();
                    options.inSampleSize = 5;
                    StickersFragment.this.thumbnails.add(BitmapFactory.decodeFile(listFiles[i].getAbsolutePath(), options));
                    StickersFragment.this.uri.add(listFiles[i].getAbsolutePath());
                }
            }
            return Boolean.valueOf(true);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (bool.booleanValue()) {
                StickersFragment.this.grid.setAdapter(new StickerGrid(StickersFragment.this.getActivity(), StickersFragment.this.catName, StickersFragment.this.thumbnails));
            }
            this.ringProgressDialog.dismiss();
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.item_frag, viewGroup, false);
        this.catName = getArguments().getString("categoryName");
        this.positn = Integer.parseInt(getArguments().getString("positionIs"));
        this.onGetSnap = (GetSnapListener) getActivity();
        this.prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", 0);
        this.editor = getActivity().getSharedPreferences("MY_PREFS_NAME", 0).edit();
        this.grid = (GridView) inflate.findViewById(R.id.grid);
        if (this.catName.equals("img")) {
            this.stkrNameList.clear();
            for (int i = 1; i <= 12; i++) {
                ArrayList<String> arrayList = this.stkrNameList;
                StringBuilder sb = new StringBuilder();
                sb.append("bh");
                sb.append(i);
                arrayList.add(sb.toString());
            }
            this.thumbnails.clear();
            this.uri.clear();
            new lordStickersAsync().execute(new String[]{""});
        } else {
            this.grid.setAdapter(new StickerGrid(getActivity(), this.catName, this.thumbnails));
        }
        this.grid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (StickersFragment.this.catName.equals("img")) {
                    StickersFragment.this.onGetSnap.onSnapFilter(i, StickersFragment.this.positn, (String) StickersFragment.this.uri.get(i));
                } else {
                    StickersFragment.this.onGetSnap.onSnapFilter(i, StickersFragment.this.positn, "");
                }
            }
        });
        return inflate;
    }
}