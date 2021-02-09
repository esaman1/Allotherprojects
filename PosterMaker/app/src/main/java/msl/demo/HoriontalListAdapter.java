package msl.demo;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import postermaster.postermaker.R;
import com.bumptech.glide.Glide;
import java.util.List;
import msl.demo.view.SquareFrameLayoutList;
import msl.demo.view.SquareImageViewList;

public class HoriontalListAdapter extends ArrayAdapter<StickerInfo> {
    Context context;

    class ViewHolder {
        SquareImageViewList mThumbnail;
        SquareFrameLayoutList root;
        Uri uri;

        public ViewHolder(View view) {
            this.root = (SquareFrameLayoutList) view.findViewById(R.id.root);
            this.mThumbnail = (SquareImageViewList) view.findViewById(R.id.thumbnail_image);
        }
    }

    public HoriontalListAdapter(Context context2, List<StickerInfo> list) {
        super(context2, 0, list);
        this.context = context2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.horizontal_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Uri ");
        sb.append(((StickerInfo) getItem(i)).getIMAGE_PATH());
        Log.i("testings", sb.toString());
        Uri parse = Uri.parse(((StickerInfo) getItem(i)).getIMAGE_PATH());
        if (viewHolder.uri == null || !viewHolder.uri.equals(parse)) {
            Glide.with(this.context).load(parse.toString()).thumbnail(0.5f).dontAnimate().skipMemoryCache(true).centerCrop().placeholder((int) R.drawable.sticker_loading).error((int) R.drawable.sticker_no_image).into(viewHolder.mThumbnail);
            viewHolder.uri = parse;
        }
        return view;
    }
}
