package postermaster.postermaker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import postermaster.postermaker.R;

public class AssetsGridMain extends BaseAdapter {
    private final String[] Imageid;
    private Context mContext;
    int selPos = -1;

    public class ViewHolder {
        RelativeLayout layItem;
        TextView txtView;

        public ViewHolder() {
        }
    }

    public long getItemId(int i) {
        return 0;
    }

    public AssetsGridMain(Context context, String[] strArr) {
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
            view = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.libtext_grid_assets_main, null);
            viewHolder = new ViewHolder();
            viewHolder.layItem = (RelativeLayout) view.findViewById(R.id.layItem);
            viewHolder.txtView = (TextView) view.findViewById(R.id.grid_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (i == 0) {
            viewHolder.txtView.setTypeface(Typeface.DEFAULT);
        } else {
            viewHolder.txtView.setTypeface(Typeface.createFromAsset(this.mContext.getAssets(), this.Imageid[i]));
        }
        viewHolder.layItem.setBackgroundColor(0);
        int i2 = this.selPos;
        if (i2 >= 0 && i == i2) {
            viewHolder.layItem.setBackgroundColor(Color.parseColor("#777777"));
        }
        return view;
    }

    public void setSelected(int i) {
        this.selPos = i;
        notifyDataSetChanged();
    }
}
