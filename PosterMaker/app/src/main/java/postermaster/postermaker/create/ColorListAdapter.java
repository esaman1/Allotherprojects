package postermaster.postermaker.create;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import postermaster.postermaker.R;

public class ColorListAdapter extends BaseAdapter {
    private final String[] colorIdArr;
    private Context mContext;

    public class ViewHolder {
        ImageView imageView;

        public ViewHolder() {
        }
    }

    public long getItemId(int i) {
        return 0;
    }

    public ColorListAdapter(Context context, String[] strArr) {
        this.mContext = context;
        this.colorIdArr = strArr;
    }

    public int getCount() {
        return this.colorIdArr.length;
    }

    public Object getItem(int i) {
        return Integer.valueOf(Color.parseColor(this.colorIdArr[i]));
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
        viewHolder.imageView.setBackgroundColor(Color.parseColor(this.colorIdArr[i]));
        return view;
    }
}
