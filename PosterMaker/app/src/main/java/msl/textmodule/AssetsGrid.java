package msl.textmodule;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import postermaster.postermaker.R;

public class AssetsGrid extends BaseAdapter {
    private final String[] Imageid;
    private Context mContext;

    public class ViewHolder {
        TextView txtView;

        public ViewHolder() {
        }
    }

    public long getItemId(int i) {
        return 0;
    }

    public AssetsGrid(Context context, String[] strArr) {
        this.mContext = context;
        this.Imageid = strArr;
    }

    public int getCount() {
        return this.Imageid.length;
    }

    public Object getItem(int i) {
        return this.Imageid[i];
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.libtext_grid_assets, null);
            viewHolder = new ViewHolder();
            viewHolder.txtView = (TextView) view.findViewById(R.id.grid_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtView.setTypeface(Typeface.createFromAsset(this.mContext.getAssets(), this.Imageid[i]));
        return view;
    }
}
