package postermaster.postermaker.sticker_fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import postermaster.postermaker.R;
import postermaster.postermaker.main.Constants;
import postermaster.postermaker.utility.CustomSquareImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class StickerGrid extends BaseAdapter {
    LayoutInflater inflater;
    private Context mContext;
    ArrayList<Bitmap> thumbnails;
    String val;

    public class ViewHolder {
        CustomSquareImageView image;

        public ViewHolder() {
        }
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public StickerGrid(Context context, String str, ArrayList<Bitmap> arrayList) {
        this.mContext = context;
        this.val = str;
        this.thumbnails = arrayList;
        this.inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        if (this.val.equals("offer")) {
            return Constants.Imageid_st1.length;
        }
        if (this.val.equals("sale")) {
            return Constants.Imageid_st2.length;
        }
        if (this.val.equals("banner")) {
            return Constants.Imageid_st3.length;
        }
        if (this.val.equals("sports")) {
            return Constants.Imageid_st4.length;
        }
        if (this.val.equals("ribbon")) {
            return Constants.Imageid_st5.length;
        }
        if (this.val.equals("birth")) {
            return Constants.Imageid_st6.length;
        }
        if (this.val.equals("decorat")) {
            return Constants.Imageid_st7.length;
        }
        if (this.val.equals("party")) {
            return Constants.Imageid_st8.length;
        }
        if (this.val.equals("music")) {
            return Constants.Imageid_st9.length;
        }
        if (this.val.equals("festival")) {
            return Constants.Imageid_st10.length;
        }
        if (this.val.equals("love")) {
            return Constants.Imageid_st11.length;
        }
        if (this.val.equals("college")) {
            return Constants.Imageid_st12.length;
        }
        if (this.val.equals("circle")) {
            return Constants.Imageid_st13.length;
        }
        if (this.val.equals("coffee")) {
            return Constants.Imageid_st14.length;
        }
        if (this.val.equals("cares")) {
            return Constants.Imageid_st15.length;
        }
        if (this.val.equals("nature")) {
            return Constants.Imageid_st16.length;
        }
        if (this.val.equals("word")) {
            return Constants.Imageid_st17.length;
        }
        if (this.val.equals("hallow")) {
            return Constants.Imageid_st18.length;
        }
        if (this.val.equals("animal")) {
            return Constants.Imageid_st19.length;
        }
        if (this.val.equals("cartoon")) {
            return Constants.Imageid_st20.length;
        }
        if (this.val.equals("shape")) {
            return Constants.Imageid_st23.length;
        }
        if (this.val.equals("white")) {
            return Constants.Imageid_st21.length;
        }
        if (this.val.equals("img")) {
            return this.thumbnails.size();
        }
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_snap, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (CustomSquareImageView) view.findViewById(R.id.thumbnail_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (this.val.equals("offer")) {
            fillImage(Constants.Imageid_st1[i], viewHolder.image);
        } else if (this.val.equals("sale")) {
            fillImage(Constants.Imageid_st2[i], viewHolder.image);
        } else if (this.val.equals("banner")) {
            fillImage(Constants.Imageid_st3[i], viewHolder.image);
        } else if (this.val.equals("sports")) {
            fillImage(Constants.Imageid_st4[i], viewHolder.image);
        } else if (this.val.equals("ribbon")) {
            fillImage(Constants.Imageid_st5[i], viewHolder.image);
        } else if (this.val.equals("birth")) {
            fillImage(Constants.Imageid_st6[i], viewHolder.image);
        } else if (this.val.equals("decorat")) {
            fillImage(Constants.Imageid_st7[i], viewHolder.image);
        } else if (this.val.equals("party")) {
            fillImage(Constants.Imageid_st8[i], viewHolder.image);
        } else if (this.val.equals("music")) {
            fillImage(Constants.Imageid_st9[i], viewHolder.image);
        } else if (this.val.equals("festival")) {
            fillImage(Constants.Imageid_st10[i], viewHolder.image);
        } else if (this.val.equals("love")) {
            fillImage(Constants.Imageid_st11[i], viewHolder.image);
        } else if (this.val.equals("college")) {
            fillImage(Constants.Imageid_st12[i], viewHolder.image);
        } else if (this.val.equals("circle")) {
            fillImage(Constants.Imageid_st13[i], viewHolder.image);
        } else if (this.val.equals("coffee")) {
            fillImage(Constants.Imageid_st14[i], viewHolder.image);
        } else if (this.val.equals("cares")) {
            fillImage(Constants.Imageid_st15[i], viewHolder.image);
        } else if (this.val.equals("nature")) {
            fillImage(Constants.Imageid_st16[i], viewHolder.image);
        } else if (this.val.equals("word")) {
            fillImage(Constants.Imageid_st17[i], viewHolder.image);
        } else if (this.val.equals("hallow")) {
            fillImage(Constants.Imageid_st18[i], viewHolder.image);
        } else if (this.val.equals("animal")) {
            fillImage(Constants.Imageid_st19[i], viewHolder.image);
        } else if (this.val.equals("cartoon")) {
            fillImage(Constants.Imageid_st20[i], viewHolder.image);
        } else if (this.val.equals("shape")) {
            fillImage(Constants.Imageid_st23[i], viewHolder.image);
        } else if (this.val.equals("white")) {
            fillImage(Constants.Imageid_st21[i], viewHolder.image);
        } else if (this.val.equals("img")) {
            viewHolder.image.setImageBitmap((Bitmap) this.thumbnails.get(i));
        }
        return view;
    }

    /* access modifiers changed from: 0000 */
    public void fillImage(int i, ImageView imageView) {
        Glide.with(this.mContext).load(Integer.valueOf(i)).thumbnail(0.1f).dontAnimate().centerCrop().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image).into(imageView);
    }
}
