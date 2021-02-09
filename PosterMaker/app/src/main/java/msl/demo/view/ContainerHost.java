package msl.demo.view;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import postermaster.postermaker.R;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;
import msl.demo.DatabaseHandler;
import msl.demo.HoriontalListAdapter;
import msl.demo.LibContants;
import msl.demo.StickerInfo;

public class ContainerHost extends RelativeLayout {
    private String actAdapter = "";
    private HashMap<String, HoriontalListAdapter> adapterHashMap = new HashMap<>();
    /* access modifiers changed from: private */
    public Context ctx;
    private HorizontalListView horizontalListView;
    /* access modifiers changed from: private */
    public OnItemClickListener itemClickListener = null;

    class C03901 implements android.widget.AdapterView.OnItemClickListener {
        C03901() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (ContainerHost.this.itemClickListener != null) {
                Uri parse = Uri.parse(((StickerInfo) ContainerHost.this.getActiveAdapter().getItem(i)).getIMAGE_PATH());
                if ("android.resource".equals(parse.getScheme())) {
                    ContainerHost.this.itemClickListener.onItemClick(((StickerInfo) ContainerHost.this.getActiveAdapter().getItem(i)).getIMAGE_PATH());
                } else if (new File(parse.getPath()).exists()) {
                    ContainerHost.this.itemClickListener.onItemClick(((StickerInfo) ContainerHost.this.getActiveAdapter().getItem(i)).getIMAGE_PATH());
                } else {
                    Log.i("testing", "Starting AsyncTask");
                    ContainerHost containerHost = ContainerHost.this;
                    new SaveAndLoadAsync(i, (StickerInfo) containerHost.getActiveAdapter().getItem(i)).execute(new Object[0]);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String str);
    }

    private class SaveAndLoadAsync extends AsyncTask<Object, Void, Boolean> {
        private ProgressDialog pdia;
        private int position;
        private StickerInfo stickerInfo;

        public SaveAndLoadAsync(int i, StickerInfo stickerInfo2) {
            this.position = i;
            this.stickerInfo = stickerInfo2;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            this.pdia = new ProgressDialog(ContainerHost.this.ctx);
            this.pdia.setMessage(ContainerHost.this.ctx.getResources().getString(R.string.downloadin_file));
            this.pdia.setCancelable(false);
            this.pdia.show();
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Object... objArr) {
            try {
                String saveBitmapObject = saveBitmapObject(BitmapFactory.decodeStream(new URL(this.stickerInfo.getIMAGE_SERVER_PATH()).openStream()), this.stickerInfo.getSTICKER_NAME());
                DatabaseHandler dbHandler = DatabaseHandler.getDbHandler(ContainerHost.this.ctx);
                dbHandler.updateStickerImagePath(this.stickerInfo.getSTICKER_ID(), saveBitmapObject, true);
                dbHandler.close();
                this.stickerInfo.setIMAGE_PATH(saveBitmapObject);
                return Boolean.valueOf(true);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                return Boolean.valueOf(false);
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            this.pdia.dismiss();
            if (bool.booleanValue()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Sticker Saved to : ");
                sb.append(this.stickerInfo.getIMAGE_PATH());
                Log.i("testing", sb.toString());
                ((StickerInfo) ContainerHost.this.getActiveAdapter().getItem(this.position)).setIMAGE_PATH(this.stickerInfo.getIMAGE_PATH());
                ContainerHost.this.itemClickListener.onItemClick(this.stickerInfo.getIMAGE_PATH());
                return;
            }
            new Builder(ContainerHost.this.ctx).setTitle(ContainerHost.this.ctx.getResources().getString(R.string.no_internet)).setMessage(ContainerHost.this.ctx.getResources().getString(R.string.file_not_downloaded)).setCancelable(false).setPositiveButton(ContainerHost.this.ctx.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }

        private String saveBitmapObject(Bitmap bitmap, String str) {
            File saveFileLocation = LibContants.getSaveFileLocation();
            saveFileLocation.mkdirs();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(".png");
            File file = new File(saveFileLocation, sb.toString());
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
                return file.getPath();
            } catch (Exception e) {
                e.printStackTrace();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Exception");
                sb2.append(e.getMessage());
                Log.i("testing", sb2.toString());
                return null;
            }
        }
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    public ContainerHost(Context context) {
        super(context);
        initContainerHost(context);
    }

    public ContainerHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initContainerHost(context);
    }

    public ContainerHost(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initContainerHost(context);
    }

    public void initContainerHost(Context context) {
        this.ctx = context;
        this.horizontalListView = new HorizontalListView(context);
        this.horizontalListView.setLayoutParams(new LayoutParams(-1, -1));
        addView(this.horizontalListView);
        this.horizontalListView.setOnItemClickListener(new C03901());
    }

    public void addAdapter(String str, HoriontalListAdapter horiontalListAdapter) {
        this.adapterHashMap.put(str, horiontalListAdapter);
    }

    public void removeAdapter(String str) {
        if (this.adapterHashMap.containsKey(str)) {
            this.adapterHashMap.remove(str);
        }
        if (!this.adapterHashMap.containsKey(this.actAdapter)) {
            this.actAdapter = "";
        }
    }

    public void changeAdapter(String str) {
        if (this.adapterHashMap.containsKey(str)) {
            this.horizontalListView.setVisibility(View.VISIBLE);
            this.horizontalListView.setAdapter((ListAdapter) this.adapterHashMap.get(str));
        } else {
            this.horizontalListView.setVisibility(View.GONE);
        }
        if (this.adapterHashMap.containsKey(str)) {
            this.actAdapter = str;
        } else {
            this.actAdapter = "";
        }
    }

    public HoriontalListAdapter getActiveAdapter() {
        if (this.adapterHashMap.containsKey(this.actAdapter)) {
            return (HoriontalListAdapter) this.adapterHashMap.get(this.actAdapter);
        }
        return null;
    }

    public HoriontalListAdapter getAdapter(String str) {
        if (this.adapterHashMap.containsKey(str)) {
            return (HoriontalListAdapter) this.adapterHashMap.get(str);
        }
        return null;
    }
}
