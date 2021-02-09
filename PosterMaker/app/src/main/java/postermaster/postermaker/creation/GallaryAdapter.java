package postermaster.postermaker.creation;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import postermaster.postermaker.BuildConfig;
import postermaster.postermaker.R;

public class GallaryAdapter extends BaseAdapter {
    private static LayoutInflater inflater;
    /* access modifiers changed from: private */
    public Activity activity;
    ArrayList<String> imagegallary = new ArrayList<>();

    static class ViewHolder {
        ImageView imgDelete;
        ImageView imgIcon;
        ImageView imgSetAs;
        ImageView imgShare;
        TextView txtDuration;
        TextView txtTitle;

        ViewHolder() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public GallaryAdapter(Activity activity2, ArrayList<String> arrayList) {
        this.activity = activity2;
        this.imagegallary = arrayList;
        inflater = (LayoutInflater) this.activity.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.imagegallary.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        int i2 = this.activity.getResources().getDisplayMetrics().widthPixels;
        if (view == null) {
            view = LayoutInflater.from(this.activity).inflate(R.layout.details_list_img, viewGroup, false);
            viewHolder = new ViewHolder();
            view.setLayoutParams(new LayoutParams(i2, -2));
            view.setPadding(8, 8, 8, 8);
            viewHolder.imgIcon = (ImageView) view.findViewById(R.id.Iv_details_img);
            viewHolder.imgDelete = (ImageView) view.findViewById(R.id.Iv_deledt_img_list);
            viewHolder.imgShare = (ImageView) view.findViewById(R.id.Iv_share_img_list);
            viewHolder.imgSetAs = (ImageView) view.findViewById(R.id.Iv_set_as_img_list);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imgIcon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GallaryAdapter.this.getItemId(i);
                MyCreationActivity.pos = i;
                Dialog dialog = new Dialog(GallaryAdapter.this.activity, 16973839);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                GallaryAdapter.this.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                double d = (double) displayMetrics.heightPixels;
                Double.isNaN(d);
                int i = (int) (d * 1.0d);
                double d2 = (double) displayMetrics.widthPixels;
                Double.isNaN(d2);
                int i2 = (int) (d2 * 1.0d);
                dialog.requestWindowFeature(1);
                dialog.getWindow().setFlags(1024, 1024);
                dialog.setContentView(R.layout.activity_full_image_disply);
                dialog.getWindow().setLayout(i2, i);
                dialog.setCanceledOnTouchOutside(true);
                ((ImageView) dialog.findViewById(R.id.iv_image)).setImageURI(Uri.parse((String) MyCreationActivity.IMAGEALLARY.get(MyCreationActivity.pos)));
                dialog.show();
            }
        });
        viewHolder.imgShare.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Builder builder = new Builder(GallaryAdapter.this.activity);
                builder.setTitle("Share File...");
                builder.setMessage("Do you want to share this file?");
                builder.setIcon(R.drawable.ic_share);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        GallaryAdapter gallaryAdapter = GallaryAdapter.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append(activity.getResources().getString(R.string.app_name));
                        sb.append(" Created By : ");
                        sb.append("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                        gallaryAdapter.shareImage(sb.toString(), (String) GallaryAdapter.this.imagegallary.get(i));
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });
        viewHolder.imgSetAs.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Builder builder = new Builder(GallaryAdapter.this.activity);
                builder.setTitle("Set As Wallpaper...");
                builder.setMessage("Do you want to Set As Wallpaper??");
                builder.setIcon(R.drawable.ic_mobile);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        GallaryAdapter.this.setWallpaper("", (String) GallaryAdapter.this.imagegallary.get(i));
                        GallaryAdapter.this.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
        viewHolder.imgDelete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Builder builder = new Builder(GallaryAdapter.this.activity);
                builder.setTitle("Confirm Delete...");
                builder.setMessage("Are you sure you want delete this?");
                builder.setIcon(R.drawable.ic_delete1);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        File file = new File((String) GallaryAdapter.this.imagegallary.get(i));
                        if (file.exists()) {
                            file.delete();
                        }
                        GallaryAdapter.this.imagegallary.remove(i);
                        GallaryAdapter.this.notifyDataSetChanged();
                        if (GallaryAdapter.this.imagegallary.size() == 0) {
                            Toast.makeText(GallaryAdapter.this.activity, "No Image Found..", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
        Glide.with(this.activity).load((String) this.imagegallary.get(i)).centerCrop().into(viewHolder.imgIcon);
        System.gc();
        return view;
    }

    /* access modifiers changed from: private */
    public void setWallpaper(String str, String str2) {
        WallpaperManager instance = WallpaperManager.getInstance(this.activity);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        int i2 = displayMetrics.widthPixels;
        try {
            Options options = new Options();
            options.inPreferredConfig = Config.ARGB_8888;
            instance.setBitmap(BitmapFactory.decodeFile(str2, options));
            instance.suggestDesiredDimensions(i2 / 2, i / 2);
            Toast.makeText(this.activity, "Wallpaper Set", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shareImage(final String strr, String str2) {
        MediaScannerConnection.scanFile(this.activity, new String[]{str2}, null, new OnScanCompletedListener() {
            public void onScanCompleted(String str, Uri uri) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/*");
                intent.putExtra("android.intent.extra.TEXT", strr);
                intent.putExtra("android.intent.extra.STREAM", uri);
                intent.addFlags(524288);
                GallaryAdapter.this.activity.startActivity(Intent.createChooser(intent, "Share Image"));
            }
        });
    }
}