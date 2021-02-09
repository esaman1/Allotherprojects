package postermaster.postermaker.main;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import postermaster.postermaker.R;
import java.io.File;
import java.util.ArrayList;

public class FragmentBackImage extends Fragment {
    public static ArrayList<Bitmap> thumbnails = new ArrayList<>();
    Context f8c;
    OnGetImageOnTouch getImage;
    /* access modifiers changed from: private */
    public GridView gridView;
    ImageAdapter imageAdapter;
    ArrayList<String> uri = new ArrayList<>();

    class C02742 implements OnDismissListener {
        C02742() {
        }

        public void onDismiss(DialogInterface dialogInterface) {
            FragmentBackImage.this.gridView.setAdapter(FragmentBackImage.this.imageAdapter);
        }
    }

    class ImageAdapter extends BaseAdapter {
        Context context;
        LayoutInflater mInflater = ((LayoutInflater) this.context.getSystemService("layout_inflater"));

        public long getItemId(int i) {
            return (long) i;
        }

        public ImageAdapter(Context context2) {
            this.context = context2;
        }

        public int getCount() {
            return FragmentBackImage.thumbnails.size();
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {
            View view2;
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view2 = this.mInflater.inflate(R.layout.grid_item22, null);
                viewHolder.imageview = (ImageView) view2.findViewById(R.id.image);
                view2.setTag(viewHolder);
            } else {
                view2 = view;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.imageview.setId(i);
            viewHolder.imageview.setImageBitmap((Bitmap) FragmentBackImage.thumbnails.get(i));
            viewHolder.imageview.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    FragmentBackImage.this.getImage.ongetPosition(i, "Temp_Path", "");
                }
            });
            return view2;
        }
    }

    class ViewHolder {
        ImageView imageview;

        ViewHolder() {
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_my_backgund, viewGroup, false);
        this.getImage = (OnGetImageOnTouch) getActivity();
        this.gridView = (GridView) inflate.findViewById(R.id.gridview);
        this.f8c = getActivity();
        this.imageAdapter = new ImageAdapter(this.f8c);
        return inflate;
    }

    public void setMenuVisibility(boolean z) {
        if (z) {
            try {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage(getResources().getString(R.string.plzwait));
                progressDialog.setCancelable(false);
                progressDialog.show();
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            FragmentBackImage.thumbnails.clear();
                            FragmentBackImage.this.getFromSdcard();
                            FragmentBackImage.this.f8c = FragmentBackImage.this.getActivity();
                            FragmentBackImage.this.imageAdapter = new ImageAdapter(FragmentBackImage.this.f8c);
                            Thread.sleep(1000);
                        } catch (Exception unused) {
                        }
                        progressDialog.dismiss();
                    }
                }).start();
                progressDialog.setOnDismissListener(new C02742());
            } catch (NullPointerException unused) {
            }
        }
        super.setMenuVisibility(z);
    }

    public void getFromSdcard() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Maker Stickers/category1");
        if (file.exists()) {
            int length = file.listFiles().length;
            thumbnails.clear();
            this.uri.clear();
            for (int i = 0; i < length; i++) {
                Options options = new Options();
                options.inSampleSize = 5;
                thumbnails.add(BitmapFactory.decodeFile(file.listFiles()[i].getAbsolutePath(), options));
                this.uri.add(file.listFiles()[i].getAbsolutePath());
            }
        }
    }
}
