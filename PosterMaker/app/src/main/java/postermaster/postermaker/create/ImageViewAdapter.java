package postermaster.postermaker.create;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import postermaster.postermaker.R;

public class ImageViewAdapter extends BaseAdapter {
    private final int[] Imageid;
    private Context mContext;

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

    public ImageViewAdapter(Context context, int[] iArr) {
        this.mContext = context;
        this.Imageid = iArr;
    }

    public int getCount() {
        return this.Imageid.length;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.btxt_lst_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.grid_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(this.Imageid[i]);
        return view;
    }
}
