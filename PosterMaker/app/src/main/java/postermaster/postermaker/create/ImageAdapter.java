package postermaster.postermaker.create;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import postermaster.postermaker.R;
import postermaster.postermaker.utility.ImageUtils;

public class ImageAdapter extends BaseAdapter {
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

    public ImageAdapter(Context context, int[] iArr) {
        this.mContext = context;
        this.Imageid = iArr;
    }

    public int getCount() {
        return this.Imageid.length;
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
        fillImage(this.Imageid[i], viewHolder.imageView);
        return view;
    }

    /* access modifiers changed from: 0000 */
    public void fillImage(int i, ImageView imageView) {
        Options options = new Options();
        options.inSampleSize = 12;
        imageView.setImageBitmap(ImageUtils.getThumbnail(BitmapFactory.decodeResource(this.mContext.getResources(), i, options), 50, 50));
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources resources, int i, int i2, int i3) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, i, options);
        options.inSampleSize = 3;
        options.inJustDecodeBounds = false;
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, i, options), 100, 100, false);
    }
}
