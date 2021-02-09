package postermaster.postermaker.create;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import postermaster.postermaker.R;
import java.util.ArrayList;

public class ImageSdAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> thumbnails;

    public class ViewHolder {
        ImageView imageView;

        public ViewHolder() {
        }
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public ImageSdAdapter(Context context, ArrayList<Bitmap> arrayList) {
        this.mContext = context;
        this.thumbnails = arrayList;
    }

    public int getCount() {
        return this.thumbnails.size();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.bg_lst_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.grid_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageBitmap((Bitmap) this.thumbnails.get(i));
        return view;
    }
}
