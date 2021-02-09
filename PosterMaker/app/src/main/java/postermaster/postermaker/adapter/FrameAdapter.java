package postermaster.postermaker.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import postermaster.postermaker.R;
import postermaster.postermaker.main.Constants;
import com.bumptech.glide.Glide;

public class FrameAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private Context mContext;
    SharedPreferences prefs;
    String val;

    public class ViewHolder {
        ImageView mThumbnail;

        public ViewHolder() {
        }
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public FrameAdapter(Context context, String str, SharedPreferences sharedPreferences) {
        this.mContext = context;
        this.val = str;
        this.prefs = sharedPreferences;
        this.inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        if (this.val.equals("Background")) {
            return Constants.Imageid0.length;
        }
        if (this.val.equals("Texture")) {
            return Constants.Imageid1.length;
        }
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.picker_grid_item_gallery_thumbnail, null);
            viewHolder = new ViewHolder();
            viewHolder.mThumbnail = (ImageView) view.findViewById(R.id.thumbnail_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (this.val.equals("Background")) {
            Glide.with(this.mContext).load(Integer.valueOf(Constants.Imageid0[i])).thumbnail(0.1f).dontAnimate().centerCrop().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image).into(viewHolder.mThumbnail);
        } else if (this.val.equals("Texture")) {
            Glide.with(this.mContext).load(Integer.valueOf(Constants.Imageid1[i])).thumbnail(0.1f).dontAnimate().centerCrop().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image).into(viewHolder.mThumbnail);
        }
        return view;
    }
}
