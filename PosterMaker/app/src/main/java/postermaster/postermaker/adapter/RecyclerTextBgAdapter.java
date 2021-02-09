package postermaster.postermaker.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import postermaster.postermaker.R;
import com.bumptech.glide.Glide;

public class RecyclerTextBgAdapter extends Adapter<RecyclerTextBgAdapter.ViewHolder> {
    Context context;
    int[] makeUpEditImage;
    int selected_position = 500;

    public static class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        ImageView imageView;
        LinearLayout layout;
        ImageView viewImage;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.item_image);
            this.viewImage = (ImageView) view.findViewById(R.id.view_image);
            this.layout = (LinearLayout) view.findViewById(R.id.lay);
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return i;
    }

    public RecyclerTextBgAdapter(Context context2, int[] iArr) {
        this.context = context2;
        this.makeUpEditImage = iArr;
    }

    public int getItemCount() {
        return this.makeUpEditImage.length;
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Glide.with(this.context).load(Integer.valueOf(this.makeUpEditImage[i])).thumbnail(0.1f).dontAnimate().centerCrop().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image).into(viewHolder.imageView);
        if (this.selected_position == i) {
            viewHolder.viewImage.setVisibility(View.VISIBLE);
        } else {
            viewHolder.viewImage.setVisibility(View.INVISIBLE);
        }
        viewHolder.layout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RecyclerTextBgAdapter recyclerTextBgAdapter = RecyclerTextBgAdapter.this;
                recyclerTextBgAdapter.notifyItemChanged(recyclerTextBgAdapter.selected_position);
                RecyclerTextBgAdapter recyclerTextBgAdapter2 = RecyclerTextBgAdapter.this;
                recyclerTextBgAdapter2.selected_position = i;
                recyclerTextBgAdapter2.notifyItemChanged(recyclerTextBgAdapter2.selected_position);
            }
        });
    }

    public void setSelected(int i) {
        this.selected_position = i;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_adapterbg, viewGroup, false));
        viewGroup.setId(i);
        viewGroup.setFocusable(false);
        viewGroup.setFocusableInTouchMode(false);
        return viewHolder;
    }
}