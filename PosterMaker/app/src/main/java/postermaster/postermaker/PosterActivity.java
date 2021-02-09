package postermaster.postermaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.gummybutton.GummyButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import postermaster.postermaker.adapter.AssetsGridMain;
import postermaster.postermaker.adapter.RecyclerItemClickListener;
import postermaster.postermaker.adapter.RecyclerOverLayAdapter;
import postermaster.postermaker.adapter.RecyclerTextBgAdapter;
import postermaster.postermaker.adapter.StickerViewPagerAdapter;
import postermaster.postermaker.create.BitmapDataObject;
import postermaster.postermaker.create.BlurOperationAsync;
import postermaster.postermaker.create.DatabaseHandler;
import postermaster.postermaker.create.RepeatListener;
import postermaster.postermaker.create.TemplateInfo;
import postermaster.postermaker.main.Constants;
import postermaster.postermaker.main.CropActivity;
import postermaster.postermaker.main.CropActivityTwo;
import postermaster.postermaker.main.GetColorListener;
import postermaster.postermaker.main.ListFragment;
import postermaster.postermaker.main.OnSetImageSticker;
import postermaster.postermaker.main.PickColorImageActivity;
import postermaster.postermaker.main.SelectImageTwoActivity;
import postermaster.postermaker.sticker_fragment.GetSnapListener;
import postermaster.postermaker.utility.GPUImageFilterTools.FilterAdjuster;
import postermaster.postermaker.utility.ImageUtils;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;
import msl.demo.view.ComponentInfo;
import msl.demo.view.ResizableStickerView;
import msl.demo.view.ResizableStickerView.TouchEventListener;
import msl.textmodule.AutofitTextRel;
import msl.textmodule.TextActivity;
import msl.textmodule.TextInfo;
import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;


public class PosterActivity extends AppCompatActivity implements OnClickListener, OnSeekBarChangeListener, TouchEventListener, AutofitTextRel.TouchEventListener, GetSnapListener, OnSetImageSticker, GetColorListener {
    private static final int OPEN_CUSTOM_ACITIVITY = 4;
    private static final int SELECT_PICTURE_FROM_CAMERA = 905;
    private static final int SELECT_PICTURE_FROM_GALLERY = 907;
    private static final int TEXT_ACTIVITY = 908;
    private static final int TYPE_SHAPE = 9062;
    private static final int TYPE_STICKER = 9072;
    public static PosterActivity activity = null;
    public static Bitmap btmSticker = null;
    public static Button btn_layControls = null;
    public static Activity f25c = null;
    public static Bitmap imgBtmap = null;
    public static boolean isUpadted = false;
    public static boolean isUpdated = false;
    public static FrameLayout lay_container;
    public static RelativeLayout txt_stkr_rel;
    public static Bitmap withoutWatermark;
    private InterstitialAd mInterstitialAd;
    boolean OneShow = true;
    private ViewPager _mViewPager;
    AssetsGridMain adapter;
    RecyclerOverLayAdapter adaptor_overlay;
    RecyclerTextBgAdapter adaptor_txtBg;
    private RelativeLayout add_sticker;
    private RelativeLayout add_text;
    boolean adshow = true;
    int alpha = 80;
    private SeekBar alphaSeekbar;
    private Animation animSlideDown;
    private Animation animSlideUp;
    private Animation animSlide_RightLeft;
    private Animation animSlide_leftRight;
    ImageView background_blur;
    ImageView background_img;
    int bgAlpha = 0;
    int bgColor = -16777216;
    String bgDrawable = "0";
    LinearLayout bgShow;
    /* access modifiers changed from: private */
    public Bitmap bitmap;
    ImageButton btn_Up;
    ImageView btn_bck1;
    ImageButton btn_up_down;
    ImageButton btn_up_down1;
    private RelativeLayout center_rel;
    boolean checkMemory;
    LinearLayout colorShow;
    String color_Type;
    LinearLayout controlsShow;
    LinearLayout controlsShowStkr;
    private int curTileId = 0;
    ProgressDialog dialogIs;
    boolean dialogShow = true;
    float distance;
    int distanceScroll;
    int dsfc;
    private boolean editMode = false;
    Editor editor;
    private File f26f = null;
    /* access modifiers changed from: private */
    public File file1;
    /* access modifiers changed from: private */
    public String filename;
    private View focusedCopy = null;
    /* access modifiers changed from: private */
    public View focusedView;
    String fontName = "";
    LinearLayout fontsShow;
    String frame_Name = "";
    ImageView guideline;
    /* access modifiers changed from: private */
    public Handler handler1 = new Handler();
    String hex;
    private LineColorPicker horizontalPicker;
    private LineColorPicker horizontalPickerColor;
    /* access modifiers changed from: private */
    public float hr = 1.0f;
    private SeekBar hueSeekbar;
    int[] imageId = {R.drawable.btxt0, R.drawable.btxt1, R.drawable.btxt2, R.drawable.btxt3, R.drawable.btxt4, R.drawable.btxt5, R.drawable.btxt6, R.drawable.btxt7, R.drawable.btxt8, R.drawable.btxt9, R.drawable.btxt10, R.drawable.btxt11, R.drawable.btxt12, R.drawable.btxt13, R.drawable.btxt14, R.drawable.btxt15, R.drawable.btxt16, R.drawable.btxt17};
    ImageView img_oK;
    int int_overlay_selection = 0;
    /* access modifiers changed from: private */
    private View[] layArr = new View[5];
    RelativeLayout lay_SeekbarMain;
    RelativeLayout lay_StkrMain;
    RelativeLayout lay_TextMain;
    RelativeLayout lay_color;
    LinearLayout lay_colorOacity;
    RelativeLayout lay_colorOpacity;
    RelativeLayout lay_controlStkr;
    LinearLayout lay_dupliStkr;
    GummyButton lay_dupliText;
    GummyButton lay_edit;
    private LinearLayout lay_effects;
    RelativeLayout lay_filter;
    RelativeLayout lay_handletails;
    RelativeLayout lay_hue;
    ScrollView lay_scroll;
    RelativeLayout lay_sticker;
    private LinearLayout lay_textEdit;
    private RelativeLayout lay_touchremove;
    ListFragment listFragment;
    private LinearLayout logo_ll;
    /* access modifiers changed from: private */
    public RelativeLayout main_rel;
    private int min = 0;
    Options options = new Options();
    String overlay_Name = "";
    int overlay_blur;
    int overlay_opacty;
    String[] pallete = {"#ffffff", "#cccccc", "#999999", "#666666", "#333333", "#000000", "#ffee90", "#ffd700", "#daa520", "#b8860b", "#ccff66", "#adff2f", "#00fa9a", "#00ff7f", "#00ff00", "#32cd32", "#3cb371", "#99cccc", "#66cccc", "#339999", "#669999", "#006666", "#336666", "#ffcccc", "#ff9999", "#ff6666", "#ff3333", "#ff0033", "#cc0033"};
    float parentY;
    private LineColorPicker pickerBg;
    String position;
    SharedPreferences preferences;
    SharedPreferences prefs;
    private int processs;
    String profile;
    String ratio;
    RelativeLayout rellative;
    float rotation = 0.0f;

    LinearLayout sadowShow;
    float screenHeight;
    float screenWidth;
    /* access modifiers changed from: private */
    public SeekBar seek;
    private SeekBar seekBar3;
    private SeekBar seekBar_shadow;
    /* access modifiers changed from: private */
    public int seekValue = 90;
    /* access modifiers changed from: private */
    public SeekBar seek_blur;
    /* access modifiers changed from: private */
    public SeekBar seek_tailys;
    private LinearLayout seekbar_container;
    private LinearLayout seekbar_handle;
    private RelativeLayout select_backgnd;
    private RelativeLayout select_effect;
    int shadowColor = -16777216;
    private LineColorPicker shadowPickerColor;
    int shadowProg = 0;
    RelativeLayout shape_rel;
    boolean showtailsSeek = false;
    int sizeFull = 0;
    int stkrColorSet = Color.parseColor("#ffffff");
    int tAlpha = 100;
    int tColor = -1;
    PagerSlidingTabStrip tabs;
    String temp_Type = "";
    String temp_path = "";
    /* access modifiers changed from: private */
    public List<TemplateInfo> templateList = new ArrayList();
    int template_id;
    int textColorSet = Color.parseColor("#ffffff");
    ImageView trans_img;
    private Typeface ttf;
    private Typeface ttfHeader;
    HashMap<Integer, Object> txtShapeList;
    ArrayList<String> uriArry = new ArrayList<>();
    private RelativeLayout user_image;
    SeekBar verticalSeekBar = null;
    /* access modifiers changed from: private */
    public float wr = 1.0f;
    float yAtLayoutCenter = -1.0f;

    public class BlurOperationTwoAsync extends AsyncTask<String, Void, String> {
        ImageView background_blur;
        Bitmap btmp;
        Activity context;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        public BlurOperationTwoAsync(PosterActivity posterActivity, Bitmap bitmap, ImageView imageView) {
            this.context = posterActivity;
            this.btmp = bitmap;
            this.background_blur = imageView;
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... strArr) {
            this.btmp = PosterActivity.this.gaussinBlur(this.context, this.btmp);
            return "yes";
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String str) {
            this.background_blur.setImageBitmap(this.btmp);
            PosterActivity.txt_stkr_rel.removeAllViews();
            if (PosterActivity.this.temp_path.equals("")) {
                LordStickersAsync lordStickersAsync = new LordStickersAsync();
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(PosterActivity.this.template_id);
                lordStickersAsync.execute(new String[]{sb.toString()});
                return;
            }
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Maker Stickers/category1");
            if (file.exists()) {
                if (file.listFiles().length >= 7) {
                    LordStickersAsync lordStickersAsync2 = new LordStickersAsync();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("");
                    sb2.append(PosterActivity.this.template_id);
                    lordStickersAsync2.execute(new String[]{sb2.toString()});
                } else if (new File(PosterActivity.this.temp_path).exists()) {
                    LordStickersAsync lordStickersAsync3 = new LordStickersAsync();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("");
                    sb3.append(PosterActivity.this.template_id);
                    lordStickersAsync3.execute(new String[]{sb3.toString()});
                } else {
                    LordStickersAsync lordStickersAsync4 = new LordStickersAsync();
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("");
                    sb4.append(PosterActivity.this.template_id);
                    lordStickersAsync4.execute(new String[]{sb4.toString()});
                }
            } else if (new File(PosterActivity.this.temp_path).exists()) {
                LordStickersAsync lordStickersAsync5 = new LordStickersAsync();
                StringBuilder sb5 = new StringBuilder();
                sb5.append("");
                sb5.append(PosterActivity.this.template_id);
                lordStickersAsync5.execute(new String[]{sb5.toString()});
            } else {
                LordStickersAsync lordStickersAsync6 = new LordStickersAsync();
                StringBuilder sb6 = new StringBuilder();
                sb6.append("");
                sb6.append(PosterActivity.this.template_id);
                lordStickersAsync6.execute(new String[]{sb6.toString()});
            }
        }
    }

    class C03286 implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }

        C03286() {
        }
    }

    class C03297 implements OnClickListener {
        C03297() {
        }

        public void onClick(View view) {
            PosterActivity.this.updatePositionSticker("decX");
        }
    }

    class C03308 implements OnClickListener {
        C03308() {
        }

        public void onClick(View view) {
            PosterActivity.this.updatePositionSticker("incrX");
        }
    }

    class C03319 implements OnClickListener {
        C03319() {
        }

        public void onClick(View view) {
            PosterActivity.this.updatePositionSticker("decY");
        }
    }

    class C06342 implements OnColorChangedListener {
        C06342() {
        }

        public void onColorChanged(int i) {
            PosterActivity.this.updateBgColor(i);
        }
    }

    class C06353 implements OnColorChangedListener {
        C06353() {
        }

        public void onColorChanged(int i) {
            PosterActivity.this.updateShadow(i);
        }
    }

    class C06364 implements OnColorChangedListener {
        C06364() {
        }

        public void onColorChanged(int i) {
            PosterActivity.this.updateColor(i);
        }
    }

    private class LordStickersAsync extends AsyncTask<String, String, Boolean> {
        private LordStickersAsync() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {
            DatabaseHandler dbHandler = DatabaseHandler.getDbHandler(PosterActivity.this.getApplicationContext());
            dbHandler.getComponentInfoList(PosterActivity.this.template_id, "SHAPE");
            ArrayList textInfoList = dbHandler.getTextInfoList(PosterActivity.this.template_id);
            ArrayList componentInfoList = dbHandler.getComponentInfoList(PosterActivity.this.template_id, "STICKER");
            dbHandler.close();
            PosterActivity.this.txtShapeList = new HashMap<>();
            Iterator it2 = textInfoList.iterator();
            while (it2.hasNext()) {
                TextInfo textInfo = (TextInfo) it2.next();
                PosterActivity.this.txtShapeList.put(Integer.valueOf(textInfo.getORDER()), textInfo);
            }
            Iterator it3 = componentInfoList.iterator();
            while (it3.hasNext()) {
                ComponentInfo componentInfo = (ComponentInfo) it3.next();
                PosterActivity.this.txtShapeList.put(Integer.valueOf(componentInfo.getORDER()), componentInfo);
            }
            return Boolean.valueOf(true);
        }

        /* access modifiers changed from: protected */
        @RequiresApi(api = 17)
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (PosterActivity.this.txtShapeList.size() == 0) {
                PosterActivity.this.dialogIs.dismiss();
            }
            ArrayList arrayList = new ArrayList(PosterActivity.this.txtShapeList.keySet());
            Collections.sort(arrayList);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                Object obj = PosterActivity.this.txtShapeList.get(arrayList.get(i));
                if (obj instanceof ComponentInfo) {
                    ComponentInfo componentInfo = (ComponentInfo) obj;
                    String stkr_path = componentInfo.getSTKR_PATH();
                    if (stkr_path.equals("")) {
                        ResizableStickerView resizableStickerView = new ResizableStickerView(PosterActivity.this);
                        PosterActivity.txt_stkr_rel.addView(resizableStickerView);
                        resizableStickerView.optimizeScreen(PosterActivity.this.screenWidth, PosterActivity.this.screenHeight);
                        resizableStickerView.setMainLayoutWH((float) PosterActivity.this.main_rel.getWidth(), (float) PosterActivity.this.main_rel.getHeight());
                        resizableStickerView.setComponentInfo(componentInfo);
                        resizableStickerView.setId(View.generateViewId());
                        resizableStickerView.optimize(PosterActivity.this.wr, PosterActivity.this.hr);
                        resizableStickerView.setOnTouchCallbackListener(PosterActivity.this);
                        resizableStickerView.setBorderVisibility(false);
                        PosterActivity.this.sizeFull++;
                    } else {
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Maker Stickers/category1");
                        if (!file.exists() && !file.mkdirs()) {
                            Log.d("", "Can't create directory to save image.");
                            PosterActivity posterActivity = PosterActivity.this;
                            Toast.makeText(posterActivity, posterActivity.getResources().getString(R.string.create_dir_err), Toast.LENGTH_LONG).show();
                            return;
                        } else if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Maker Stickers/category1").exists()) {
                            PosterActivity.this.file1 = new File(stkr_path);
                            if (PosterActivity.this.file1.exists()) {
                                ResizableStickerView resizableStickerView2 = new ResizableStickerView(PosterActivity.this);
                                PosterActivity.txt_stkr_rel.addView(resizableStickerView2);
                                resizableStickerView2.optimizeScreen(PosterActivity.this.screenWidth, PosterActivity.this.screenHeight);
                                resizableStickerView2.setMainLayoutWH((float) PosterActivity.this.main_rel.getWidth(), (float) PosterActivity.this.main_rel.getHeight());
                                resizableStickerView2.setComponentInfo(componentInfo);
                                resizableStickerView2.setId(View.generateViewId());
                                resizableStickerView2.optimize(PosterActivity.this.wr, PosterActivity.this.hr);
                                resizableStickerView2.setOnTouchCallbackListener(PosterActivity.this);
                                resizableStickerView2.setBorderVisibility(false);
                                PosterActivity.this.sizeFull++;
                            } else if (PosterActivity.this.file1.getName().replace(".png", "").length() < 7) {
                                PosterActivity posterActivity2 = PosterActivity.this;
                                posterActivity2.dialogShow = false;
                                new SaveStickersAsync(obj).execute(new String[]{PosterActivity.this.file1.getName()});
                            } else {
                                if (PosterActivity.this.OneShow) {
                                    PosterActivity posterActivity3 = PosterActivity.this;
                                    posterActivity3.dialogShow = true;
                                    posterActivity3.errorDialogTempInfo();
                                    PosterActivity.this.OneShow = false;
                                }
                                PosterActivity.this.sizeFull++;
                            }
                        } else {
                            PosterActivity.this.file1 = new File(stkr_path);
                            if (PosterActivity.this.file1.exists()) {
                                ResizableStickerView resizableStickerView3 = new ResizableStickerView(PosterActivity.this);
                                PosterActivity.txt_stkr_rel.addView(resizableStickerView3);
                                resizableStickerView3.optimizeScreen(PosterActivity.this.screenWidth, PosterActivity.this.screenHeight);
                                resizableStickerView3.setMainLayoutWH((float) PosterActivity.this.main_rel.getWidth(), (float) PosterActivity.this.main_rel.getHeight());
                                resizableStickerView3.setComponentInfo(componentInfo);
                                resizableStickerView3.setId(View.generateViewId());
                                resizableStickerView3.optimize(PosterActivity.this.wr, PosterActivity.this.hr);
                                resizableStickerView3.setOnTouchCallbackListener(PosterActivity.this);
                                resizableStickerView3.setBorderVisibility(false);
                                PosterActivity.this.sizeFull++;
                            } else if (PosterActivity.this.file1.getName().replace(".png", "").length() < 7) {
                                PosterActivity posterActivity4 = PosterActivity.this;
                                posterActivity4.dialogShow = false;
                                new SaveStickersAsync(obj).execute(new String[]{PosterActivity.this.file1.getName()});
                            } else {
                                if (PosterActivity.this.OneShow) {
                                    PosterActivity posterActivity5 = PosterActivity.this;
                                    posterActivity5.dialogShow = true;
                                    posterActivity5.errorDialogTempInfo();
                                    PosterActivity.this.OneShow = false;
                                }
                                PosterActivity.this.sizeFull++;
                            }
                        }
                    }
                } else {
                    AutofitTextRel autofitTextRel = new AutofitTextRel(PosterActivity.this);
                    PosterActivity.txt_stkr_rel.addView(autofitTextRel);
                    TextInfo textInfo = (TextInfo) obj;
                    autofitTextRel.setTextInfo(textInfo, false);
                    autofitTextRel.setId(View.generateViewId());
                    autofitTextRel.optimize(PosterActivity.this.wr, PosterActivity.this.hr);
                    autofitTextRel.setOnTouchCallbackListener(PosterActivity.this);
                    autofitTextRel.setBorderVisibility(false);
                    PosterActivity.this.fontName = textInfo.getFONT_NAME();
                    PosterActivity.this.tColor = textInfo.getTEXT_COLOR();
                    PosterActivity.this.shadowColor = textInfo.getSHADOW_COLOR();
                    PosterActivity.this.shadowProg = textInfo.getSHADOW_PROG();
                    PosterActivity.this.tAlpha = textInfo.getTEXT_ALPHA();
                    PosterActivity.this.bgDrawable = textInfo.getBG_DRAWABLE();
                    PosterActivity.this.bgAlpha = textInfo.getBG_ALPHA();
                    PosterActivity.this.rotation = textInfo.getROTATION();
                    PosterActivity.this.bgColor = textInfo.getBG_COLOR();
                    PosterActivity.this.sizeFull++;
                }
            }
            if (PosterActivity.this.txtShapeList.size() == PosterActivity.this.sizeFull && PosterActivity.this.dialogShow) {
                PosterActivity.this.dialogIs.dismiss();
            }
        }
    }

    private class LordTemplateAsync extends AsyncTask<String, String, Boolean> {
        int indx;
        String postion;

        private LordTemplateAsync() {
            this.indx = 0;
            this.postion = "1";
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            PosterActivity posterActivity = PosterActivity.this;
            posterActivity.dialogIs = new ProgressDialog(posterActivity);
            PosterActivity.this.dialogIs.setMessage(PosterActivity.this.getResources().getString(R.string.plzwait));
            PosterActivity.this.dialogIs.setCancelable(false);
            PosterActivity.this.dialogIs.show();
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {
            this.indx = Integer.parseInt(strArr[0]);
            TemplateInfo templateInfo = (TemplateInfo) PosterActivity.this.templateList.get(this.indx);
            PosterActivity.this.template_id = templateInfo.getTEMPLATE_ID();
            PosterActivity.this.frame_Name = templateInfo.getFRAME_NAME();
            PosterActivity.this.temp_path = templateInfo.getTEMP_PATH();
            PosterActivity.this.ratio = templateInfo.getRATIO();
            PosterActivity.this.profile = templateInfo.getPROFILE_TYPE();
            String seek_value = templateInfo.getSEEK_VALUE();
            PosterActivity.this.hex = templateInfo.getTEMPCOLOR();
            PosterActivity.this.overlay_Name = templateInfo.getOVERLAY_NAME();
            PosterActivity.this.overlay_opacty = templateInfo.getOVERLAY_OPACITY();
            PosterActivity.this.overlay_blur = templateInfo.getOVERLAY_BLUR();
            PosterActivity.this.seekValue = Integer.parseInt(seek_value);
            return Boolean.valueOf(true);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (PosterActivity.this.profile.equals("Background")) {
                this.postion = PosterActivity.this.frame_Name.replace("b", "");
            } else if (!PosterActivity.this.profile.equals("Color")) {
                if (PosterActivity.this.profile.equals("Texture")) {
                    this.postion = PosterActivity.this.frame_Name.replace("t", "");
                    PosterActivity.this.seek_tailys.setProgress(PosterActivity.this.seekValue);
                } else if (PosterActivity.this.profile.equals("Temp_Path") && !PosterActivity.this.frame_Name.equals("") && !PosterActivity.this.ratio.equals("")) {
                    this.postion = PosterActivity.this.frame_Name.replace("b", "");
                }
            }
            if (!PosterActivity.this.overlay_Name.equals("")) {
                PosterActivity.this.adaptor_overlay.setSelected(Integer.parseInt(PosterActivity.this.overlay_Name.replace("o", "")) - 1);
                PosterActivity posterActivity = PosterActivity.this;
                posterActivity.setBitmapOverlay(posterActivity.getResources().getIdentifier(PosterActivity.this.overlay_Name, "drawable", PosterActivity.this.getPackageName()));
            }
            PosterActivity.this.seek.setProgress(PosterActivity.this.overlay_opacty);
            PosterActivity.this.seek_blur.setProgress(PosterActivity.this.overlay_blur);
            String valueOf = String.valueOf(Integer.parseInt(this.postion));
            if (((TemplateInfo) PosterActivity.this.templateList.get(this.indx)).getTYPE().equals("USER")) {
                PosterActivity posterActivity2 = PosterActivity.this;
                posterActivity2.drawBackgroundImage(posterActivity2.ratio, valueOf, PosterActivity.this.profile, "created");
            }
            if (((TemplateInfo) PosterActivity.this.templateList.get(this.indx)).getTYPE().equals("FREESTYLE")) {
                PosterActivity posterActivity3 = PosterActivity.this;
                posterActivity3.drawBackgroundImage(posterActivity3.ratio, valueOf, PosterActivity.this.profile, "created");
            }
            if (((TemplateInfo) PosterActivity.this.templateList.get(this.indx)).getTYPE().equals("FRIDAY")) {
                PosterActivity posterActivity4 = PosterActivity.this;
                posterActivity4.drawBackgroundImage(posterActivity4.ratio, valueOf, PosterActivity.this.profile, "created");
            }
            if (((TemplateInfo) PosterActivity.this.templateList.get(this.indx)).getTYPE().equals("SALES")) {
                PosterActivity posterActivity5 = PosterActivity.this;
                posterActivity5.drawBackgroundImage(posterActivity5.ratio, valueOf, PosterActivity.this.profile, "created");
            }
            if (((TemplateInfo) PosterActivity.this.templateList.get(this.indx)).getTYPE().equals("SPORTS")) {
                PosterActivity posterActivity6 = PosterActivity.this;
                posterActivity6.drawBackgroundImage(posterActivity6.ratio, valueOf, PosterActivity.this.profile, "created");
            }
        }
    }

    private class SaveStickersAsync extends AsyncTask<String, String, Boolean> {
        Object objk;
        String stkr_path;

        public SaveStickersAsync(Object obj) {
            this.objk = obj;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {
            String str = strArr[0];
            this.stkr_path = ((ComponentInfo) this.objk).getSTKR_PATH();
            try {
                Bitmap decodeResource = BitmapFactory.decodeResource(PosterActivity.this.getResources(), PosterActivity.this.getResources().getIdentifier(str, "drawable", PosterActivity.this.getPackageName()));
                if (decodeResource != null) {
                    return Boolean.valueOf(Constants.saveBitmapObject(PosterActivity.this, decodeResource, this.stkr_path));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Boolean.valueOf(false);
        }

        /* access modifiers changed from: protected */
        @RequiresApi(api = 17)
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            PosterActivity.this.sizeFull++;
            if (PosterActivity.this.txtShapeList.size() == PosterActivity.this.sizeFull) {
                PosterActivity.this.dialogShow = true;
            }
            if (bool.booleanValue()) {
                ResizableStickerView resizableStickerView = new ResizableStickerView(PosterActivity.this);
                PosterActivity.txt_stkr_rel.addView(resizableStickerView);
                resizableStickerView.optimizeScreen(PosterActivity.this.screenWidth, PosterActivity.this.screenHeight);
                resizableStickerView.setMainLayoutWH((float) PosterActivity.this.main_rel.getWidth(), (float) PosterActivity.this.main_rel.getHeight());
                resizableStickerView.setComponentInfo((ComponentInfo) this.objk);
                resizableStickerView.setId(View.generateViewId());
                resizableStickerView.optimize(PosterActivity.this.wr, PosterActivity.this.hr);
                resizableStickerView.setOnTouchCallbackListener(PosterActivity.this);
                resizableStickerView.setBorderVisibility(false);
            }
            if (PosterActivity.this.dialogShow) {
                PosterActivity.this.dialogIs.dismiss();
            }
        }
    }

    private class SavebackgrundAsync extends AsyncTask<String, String, Boolean> {
        private String crted;
        private String profile;
        private String ratio;

        private SavebackgrundAsync() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {
            String str = strArr[0];
            this.ratio = strArr[1];
            this.profile = strArr[2];
            this.crted = strArr[3];
            try {
                Bitmap decodeResource = BitmapFactory.decodeResource(PosterActivity.this.getResources(), PosterActivity.this.getResources().getIdentifier(str, "drawable", PosterActivity.this.getPackageName()));
                if (decodeResource != null) {
                    return Boolean.valueOf(Constants.saveBitmapObject(PosterActivity.this, decodeResource, PosterActivity.this.temp_path));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Boolean.valueOf(false);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (bool.booleanValue()) {
                try {
                    PosterActivity.this.bitmapRatio(this.ratio, this.profile, ImageUtils.getResampleImageBitmap(Uri.parse(PosterActivity.this.temp_path), PosterActivity.this, (int) (PosterActivity.this.screenWidth > PosterActivity.this.screenHeight ? PosterActivity.this.screenWidth : PosterActivity.this.screenHeight)), this.crted);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PosterActivity.txt_stkr_rel.removeAllViews();
            }
        }
    }

    public class ShowDialog extends AsyncTask {
        private ProgressDialog progressDialog;

        /* access modifiers changed from: protected */
        public Object doInBackground(Object[] objArr) {
            return null;
        }

        public ShowDialog() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(PosterActivity.this);
            this.progressDialog.setTitle("Please Wait");
            this.progressDialog.setMessage("Loading Ads");
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Object obj) {
            super.onPostExecute(obj);
            this.progressDialog.dismiss();
        }
    }

    private float getnewHeight(int i, int i2, float f, float f2) {
        return (((float) i2) * f) / ((float) i);
    }

    private float getnewWidth(int i, int i2, float f, float f2) {
        return (((float) i) * f2) / ((float) i2);
    }

    public void onEdit(View view, Uri uri) {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_poster);
        intilization();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });
        f25c = this;
        this.options.inScaled = false;
        activity = this;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = (float) displayMetrics.widthPixels;
        this.screenHeight = (float) (displayMetrics.heightPixels - ImageUtils.dpToPx(this, 105));
        this.prefs = getSharedPreferences("MY_PREFS_NAME", 0);
        this.editor = getSharedPreferences("MY_PREFS_NAME", 0).edit();
        initViewPager();
        this.ttfHeader = Constants.getHeaderTypeface(this);
        ((TextView) findViewById(R.id.tvPM)).setTypeface(this.ttfHeader);

        if (getIntent().getBooleanExtra("loadUserFrame", false)) {
            Bundle extras = getIntent().getExtras();
            if (!extras.getString("ratio").equals("cropImg")) {
                this.ratio = extras.getString("ratio");
                this.position = extras.getString("position");
                this.profile = extras.getString("profile");
                this.hex = extras.getString("hex");
                drawBackgroundImage(this.ratio, this.position, this.profile, "nonCreated");
            } else if (extras.getString("ratio").equals("cropImg")) {
                this.ratio = "";
                this.position = "1";
                this.profile = "Temp_Path";
                this.hex = "";
                setImageBitmapAndResizeLayout(ImageUtils.resizeBitmap(CropActivity.bitmapImage, (int) this.screenWidth, (int) this.screenHeight), "nonCreated");
            }
        } else {
            this.temp_Type = getIntent().getExtras().getString("Temp_Type");
            DatabaseHandler dbHandler = DatabaseHandler.getDbHandler(getApplicationContext());
            if (this.temp_Type.equals("MY_TEMP")) {
                this.templateList = dbHandler.getTemplateListDes("USER");
            } else if (this.temp_Type.equals("FREE_TEMP")) {
                this.templateList = dbHandler.getTemplateList("FREESTYLE");
            } else if (this.temp_Type.equals("FRIDAY_TEMP")) {
                this.templateList = dbHandler.getTemplateList("FRIDAY");
            } else if (this.temp_Type.equals("SALE_TEMP")) {
                this.templateList = dbHandler.getTemplateList("SALES");
            } else if (this.temp_Type.equals("SPORT_TEMP")) {
                this.templateList = dbHandler.getTemplateList("SPORTS");
            }
            dbHandler.close();
            final int intExtra = getIntent().getIntExtra("position", 0);
            this.center_rel.post(new Runnable() {
                public void run() {
                    LordTemplateAsync lordTemplateAsync = new LordTemplateAsync();
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(intExtra);
                    lordTemplateAsync.execute(new String[]{sb.toString()});
                }
            });
        }
        int[] iArr = new int[this.pallete.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = Color.parseColor(this.pallete[i]);
        }
        this.horizontalPicker.setColors(iArr);
        this.horizontalPickerColor.setColors(iArr);
        this.shadowPickerColor.setColors(iArr);
        this.pickerBg.setColors(iArr);
        this.horizontalPicker.setSelectedColor(this.textColorSet);
        this.horizontalPickerColor.setSelectedColor(this.stkrColorSet);
        this.shadowPickerColor.setSelectedColor(iArr[5]);
        this.pickerBg.setSelectedColor(iArr[5]);
        int color = this.horizontalPicker.getColor();
        int color2 = this.horizontalPickerColor.getColor();
        int color3 = this.shadowPickerColor.getColor();
        int color4 = this.pickerBg.getColor();
        updateColor(color);
        updateColor(color2);
        updateShadow(color3);
        updateBgColor(color4);
        C06342 c06342 = new C06342();
        C06353 c06353 = new C06353();
        C06364 c06364 = new C06364();
        this.horizontalPicker.setOnColorChangedListener(c06364);
        this.horizontalPickerColor.setOnColorChangedListener(c06364);
        this.shadowPickerColor.setOnColorChangedListener(c06353);
        this.pickerBg.setOnColorChangedListener(c06342);
        this.guideline = (ImageView) findViewById(R.id.guidelines);
        this.select_backgnd.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.select_effect.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.user_image.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.add_text.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.add_sticker.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.rellative = (RelativeLayout) findViewById(R.id.rellative);
        this.btn_bck1 = (ImageView) findViewById(R.id.btn_bck1);
        this.btn_bck1.setOnClickListener(this);
        this.lay_scroll = (ScrollView) findViewById(R.id.lay_scroll);
        this.lay_scroll.setOnTouchListener(new C03286());
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.addRule(13);
        this.lay_scroll.setLayoutParams(layoutParams);
        this.lay_scroll.postInvalidate();
        this.lay_scroll.requestLayout();
        ((TextView) findViewById(R.id.txt_add_text)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_add_sticker)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_select_dframe)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_image)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_select_edit)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_fonts)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_colors)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_shadow)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_bg)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_controls)).setTypeface(this.ttf);
        ImageButton imageButton = (ImageButton) findViewById(R.id.btnRight);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.btnUp);
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.btnDown);
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.btnLeftS);
        ImageButton imageButton5 = (ImageButton) findViewById(R.id.btnRightS);
        ImageButton imageButton6 = (ImageButton) findViewById(R.id.btnUpS);
        ImageButton imageButton7 = (ImageButton) findViewById(R.id.btnDownS);
        ((ImageButton) findViewById(R.id.btnLeft)).setOnTouchListener(new RepeatListener(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 100, this.guideline, new C03297()));
        imageButton.setOnTouchListener(new RepeatListener(Callback.DEFAULT_DRAG_ANIMATION_DURATION, 100, this.guideline, new C03308()));
        imageButton2.setOnTouchListener(new RepeatListener(Callback.DEFAULT_DRAG_ANIMATION_DURATION, 100, this.guideline, new C03319()));
        imageButton3.setOnTouchListener(new RepeatListener(Callback.DEFAULT_DRAG_ANIMATION_DURATION, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("incrY");
            }
        }));
        imageButton4.setOnTouchListener(new RepeatListener(Callback.DEFAULT_DRAG_ANIMATION_DURATION, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("decX");
            }
        }));
        imageButton5.setOnTouchListener(new RepeatListener(Callback.DEFAULT_DRAG_ANIMATION_DURATION, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("incrX");
            }
        }));
        imageButton6.setOnTouchListener(new RepeatListener(Callback.DEFAULT_DRAG_ANIMATION_DURATION, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("decY");
            }
        }));
        imageButton7.setOnTouchListener(new RepeatListener(Callback.DEFAULT_DRAG_ANIMATION_DURATION, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("incrY");
            }
        }));
    }

    /* access modifiers changed from: private */
    public void drawBackgroundImage(String str, String str2, String str3, String str4) {
        this.lay_sticker.setVisibility(View.GONE);
        int parseInt = Integer.parseInt(str2);
        if (!str3.equals("no")) {
            if (str3.equals("Background")) {
                this.lay_handletails.setVisibility(View.GONE);
                StringBuilder sb = new StringBuilder();
                sb.append("b");
                sb.append(String.valueOf(parseInt));
                this.frame_Name = sb.toString();
                this.temp_path = "";
                Options options2 = new Options();
                options2.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), Constants.Imageid0[parseInt], options2);
                Options options3 = new Options();
                int i = options2.outWidth;
                int i2 = options2.outHeight;
                float f = this.screenWidth;
                float f2 = this.screenHeight;
                if (f >= f2) {
                    f = f2;
                }
                options3.inSampleSize = ImageUtils.getClosestResampleSize(i, i2, (int) f);
                options2.inJustDecodeBounds = false;
                bitmapRatio(str, str3, BitmapFactory.decodeResource(getResources(), Constants.Imageid0[parseInt], options3), str4);
            } else if (str3.equals("Texture")) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("t");
                sb2.append(String.valueOf(parseInt));
                this.frame_Name = sb2.toString();
                this.temp_path = "";
                this.curTileId = Constants.Imageid1[parseInt];
                this.showtailsSeek = true;
                this.lay_handletails.setVisibility(View.VISIBLE);
                bitmapRatio(str, str3, ImageUtils.getTiledBitmap(this, this.curTileId, (int) this.screenWidth, (int) this.screenHeight), str4);
            } else if (str3.equals("Color")) {
                this.lay_handletails.setVisibility(View.GONE);
                this.temp_path = "";
                String str5 = this.hex;
                Bitmap createBitmap = Bitmap.createBitmap(500, 650, Config.ARGB_8888);
                StringBuilder sb3 = new StringBuilder();
                sb3.append("#");
                sb3.append(str5);
                createBitmap.eraseColor(Color.parseColor(sb3.toString()));
                bitmapRatio(str, str3, createBitmap, str4);
            } else if (str3.equals("Temp_Path")) {
                this.profile = "Temp_Path";
                if (str.equals("")) {
                    this.frame_Name = "";
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("b");
                    sb4.append(String.valueOf(parseInt));
                    this.frame_Name = sb4.toString();
                }
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Maker Stickers/category1");
                if (file.exists() || file.mkdirs()) {
                    File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Maker Stickers/category1");
                    if (!file2.exists()) {
                        if (str4.equals("nonCreated")) {
                            this.uriArry.clear();
                            for (File absolutePath : file2.listFiles()) {
                                this.uriArry.add(absolutePath.getAbsolutePath());
                            }
                            this.temp_path = (String) this.uriArry.get(parseInt);
                        }
                        File file3 = new File(this.temp_path);
                        if (file3.exists()) {
                            try {
                                bitmapRatio(str, str3, ImageUtils.getResampleImageBitmap(Uri.parse(this.temp_path), this, (int) (this.screenWidth > this.screenHeight ? this.screenWidth : this.screenHeight)), str4);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (!str.equals("")) {
                            new SavebackgrundAsync().execute(new String[]{file3.getName().replace(".png", ""), str, str3, str4});
                        } else if (this.OneShow) {
                            errorDialogTempInfo();
                            this.OneShow = false;
                        }
                    } else {
                        if (str4.equals("nonCreated")) {
                            this.uriArry.clear();
                            for (File absolutePath2 : file2.listFiles()) {
                                this.uriArry.add(absolutePath2.getAbsolutePath());
                            }
                            this.temp_path = (String) this.uriArry.get(parseInt);
                        }
                        if (new File(this.temp_path).exists()) {
                            try {
                                bitmapRatio(str, str3, ImageUtils.getResampleImageBitmap(Uri.parse(this.temp_path), this, (int) (this.screenWidth > this.screenHeight ? this.screenWidth : this.screenHeight)), str4);
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        } else if (str.equals("") && this.OneShow) {
                            errorDialogTempInfo();
                            this.OneShow = false;
                        }
                    }
                } else {
                    Log.d("", "Can't create directory to save image.");
                    Toast.makeText(this, getResources().getString(R.string.create_dir_err), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void bitmapRatio(String str, String str2, Bitmap bitmap2, String str3) {
        if (!str.equals("")) {
            bitmap2 = str.equals("1:1") ? cropInRatio(bitmap2, 1, 1) : str.equals("16:9") ? cropInRatio(bitmap2, 16, 9) : str.equals("9:16") ? cropInRatio(bitmap2, 9, 16) : str.equals("4:3") ? cropInRatio(bitmap2, 4, 3) : str.equals("3:4") ? cropInRatio(bitmap2, 3, 4) : null;
        }
        Bitmap resizeBitmap = ImageUtils.resizeBitmap(bitmap2, (int) this.screenWidth, (int) this.screenHeight);
        if (str3.equals("created")) {
            if (str2.equals("Texture")) {
                setImageBitmapAndResizeLayout(Constants.getTiledBitmap((Activity) this, this.curTileId, resizeBitmap, this.seek_tailys), "created");
            } else {
                setImageBitmapAndResizeLayout(resizeBitmap, "created");
            }
        } else if (str2.equals("Texture")) {
            setImageBitmapAndResizeLayout(Constants.getTiledBitmap((Activity) this, this.curTileId, resizeBitmap, this.seek_tailys), "nonCreated");
        } else {
            setImageBitmapAndResizeLayout(resizeBitmap, "nonCreated");
        }
    }

    public Bitmap cropInRatio(Bitmap bitmap2, int i, int i2) {
        float width = (float) bitmap2.getWidth();
        float height = (float) bitmap2.getHeight();
        float f = getnewHeight(i, i2, width, height);
        float f2 = getnewWidth(i, i2, width, height);
        return (f2 == width && f == height) ? bitmap2 : (f > height || f >= height) ? (f2 > width || f2 >= width) ? null : Bitmap.createBitmap(bitmap2, (int) ((width - f2) / 2.0f), 0, (int) f2, (int) height) : Bitmap.createBitmap(bitmap2, 0, (int) ((height - f) / 2.0f), (int) width, (int) f);
    }

    private void setImageBitmapAndResizeLayout(Bitmap bitmap2, String str) {
        this.main_rel.getLayoutParams().width = bitmap2.getWidth();
        this.main_rel.getLayoutParams().height = bitmap2.getHeight();
        this.main_rel.postInvalidate();
        this.main_rel.requestLayout();
        this.background_img.setImageBitmap(bitmap2);
        imgBtmap = bitmap2;
        this.main_rel.post(new Runnable() {

            /* renamed from: postermaster.postermaker.PosterActivity$10$C03241 */
            class C03241 implements Runnable {
                C03241() {
                }

                public void run() {
                    int[] iArr = new int[2];
                    PosterActivity.this.lay_scroll.getLocationOnScreen(iArr);
                    PosterActivity.this.parentY = (float) iArr[1];
                    PosterActivity.this.yAtLayoutCenter = PosterActivity.this.parentY;
                }
            }

            public void run() {
                ImageView imageView = PosterActivity.this.guideline;
                PosterActivity posterActivity = PosterActivity.this;
                imageView.setImageBitmap(Constants.guidelines_bitmap(posterActivity, posterActivity.main_rel.getWidth(), PosterActivity.this.main_rel.getHeight()));
                PosterActivity.this.lay_scroll.post(new C03241());
            }
        });
        try {
            float width = (float) bitmap2.getWidth();
            float height = (float) bitmap2.getHeight();
            bitmap2 = ImageUtils.resizeBitmap(bitmap2, this.center_rel.getWidth(), this.center_rel.getHeight());
            float height2 = (float) bitmap2.getHeight();
            this.wr = ((float) bitmap2.getWidth()) / width;
            this.hr = height2 / height;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.min != 0) {
            this.background_blur.setVisibility(View.VISIBLE);
        } else {
            this.background_blur.setVisibility(View.GONE);
        }
        if (str.equals("created")) {
            new BlurOperationTwoAsync(this, bitmap2, this.background_blur).execute(new String[]{""});
            return;
        }
        new BlurOperationAsync(this, bitmap2, this.background_blur).execute(new String[]{""});
    }

    private void intilization() {
        this.ttf = Constants.getTextTypeface(this);
        lay_container = (FrameLayout) findViewById(R.id.lay_container);
        this.center_rel = (RelativeLayout) findViewById(R.id.center_rel);
        this.lay_touchremove = (RelativeLayout) findViewById(R.id.lay_touchremove);
        this.lay_TextMain = (RelativeLayout) findViewById(R.id.lay_TextMain);
        this.lay_StkrMain = (RelativeLayout) findViewById(R.id.lay_StkrMain);
        this.lay_SeekbarMain = (RelativeLayout) findViewById(R.id.lay_SeekbarMain);
        this.btn_up_down = (ImageButton) findViewById(R.id.btn_up_down);
        this.btn_up_down1 = (ImageButton) findViewById(R.id.btn_up_down1);
        this.btn_Up = (ImageButton) findViewById(R.id.btn_Up);
        this.main_rel = (RelativeLayout) findViewById(R.id.main_rel);
        this.background_img = (ImageView) findViewById(R.id.background_img);
        this.background_blur = (ImageView) findViewById(R.id.background_blur);
        txt_stkr_rel = (RelativeLayout) findViewById(R.id.txt_stkr_rel);
        this.user_image = (RelativeLayout) findViewById(R.id.user_image);
        this.select_backgnd = (RelativeLayout) findViewById(R.id.select_backgnd);
        this.select_effect = (RelativeLayout) findViewById(R.id.select_effect);
        this.add_sticker = (RelativeLayout) findViewById(R.id.add_sticker);
        this.add_text = (RelativeLayout) findViewById(R.id.add_text);
        this.lay_effects = (LinearLayout) findViewById(R.id.lay_effects);
        this.lay_sticker = (RelativeLayout) findViewById(R.id.lay_sticker);
        this.lay_handletails = (RelativeLayout) findViewById(R.id.lay_handletails);
        this.seekbar_container = (LinearLayout) findViewById(R.id.seekbar_container);
        this.seekbar_handle = (LinearLayout) findViewById(R.id.seekbar_handle);
        this.shape_rel = (RelativeLayout) findViewById(R.id.shape_rel);
        this.seek_tailys = (SeekBar) findViewById(R.id.seek_tailys);
        this.alphaSeekbar = (SeekBar) findViewById(R.id.alpha_seekBar);
        this.seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        this.seekBar_shadow = (SeekBar) findViewById(R.id.seekBar_shadow);
        this.hueSeekbar = (SeekBar) findViewById(R.id.hue_seekBar);
        this.trans_img = (ImageView) findViewById(R.id.trans_img);
        this.alphaSeekbar.setOnSeekBarChangeListener(this);
        this.seekBar3.setOnSeekBarChangeListener(this);
        this.seekBar_shadow.setOnSeekBarChangeListener(this);
        this.hueSeekbar.setOnSeekBarChangeListener(this);
        this.seek_tailys.setOnSeekBarChangeListener(this);
        this.seek = (SeekBar) findViewById(R.id.seek);
        this.lay_filter = (RelativeLayout) findViewById(R.id.lay_filter);
        this.lay_dupliText = (GummyButton) findViewById(R.id.lay_dupliText);
        this.lay_dupliStkr = (LinearLayout) findViewById(R.id.lay_dupliStkr);
        this.lay_edit = (GummyButton) findViewById(R.id.lay_edit);
        this.lay_dupliText.setAction(new GummyButton.OnClickListener() {
            public void onClick(MotionEvent motionEvent) {
                int childCount = PosterActivity.txt_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = PosterActivity.txt_stkr_rel.getChildAt(i);
                    if (childAt instanceof AutofitTextRel) {
                        AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                        if (autofitTextRel.getBorderVisibility()) {
                            AutofitTextRel autofitTextRel2 = new AutofitTextRel(PosterActivity.this);
                            PosterActivity.txt_stkr_rel.addView(autofitTextRel2);
                            PosterActivity.this.removeImageViewControll();
                            autofitTextRel2.setTextInfo(autofitTextRel.getTextInfo(), false);
                            autofitTextRel2.setId(View.generateViewId());
                            autofitTextRel2.setOnTouchCallbackListener(PosterActivity.this);
                            autofitTextRel2.setBorderVisibility(true);
                        }
                    }
                }
            }
        });
        this.lay_dupliStkr.setOnClickListener(this);
        this.lay_edit.setAction(new GummyButton.OnClickListener() {
            public void onClick(MotionEvent motionEvent) {
                PosterActivity.this.doubleTabPrass();
            }
        });
        this.seek_blur = (SeekBar) findViewById(R.id.seek_blur);
        this.trans_img.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.o1));
        this.hueSeekbar.setProgress(1);
        this.seek.setMax(255);
        this.seek.setProgress(80);
        this.seek_blur.setMax(255);
        this.seekBar_shadow.setProgress(0);
        this.seekBar3.setProgress(255);
        this.seek_blur.setProgress(this.min);
        this.trans_img.setImageAlpha(this.alpha);
        this.seek.setOnSeekBarChangeListener(this);
        this.seek_blur.setOnSeekBarChangeListener(this);
        this.seek_tailys.setMax(290);
        this.seek_tailys.setProgress(90);
        this.logo_ll = (LinearLayout) findViewById(R.id.logo_ll);
        this.img_oK = (ImageView) findViewById(R.id.btn_done);
        btn_layControls = (Button) findViewById(R.id.btn_layControls);
        this.img_oK.setOnClickListener(this);
        btn_layControls.setOnClickListener(this);
        this.user_image.setOnClickListener(this);
        this.select_backgnd.setOnClickListener(this);
        this.select_effect.setOnClickListener(this);
        this.add_sticker.setOnClickListener(this);
        this.add_text.setOnClickListener(this);
        this.lay_touchremove.setOnClickListener(this);
        this.center_rel.setOnClickListener(this);
        this.lay_textEdit = (LinearLayout) findViewById(R.id.lay_textEdit);
        this.animSlideUp = Constants.getAnimUp(this);
        this.animSlideDown = Constants.getAnimDown(this);
        this.animSlide_leftRight = Constants.getAnimUpDown(this);
        this.animSlide_RightLeft = Constants.getAnimDownUp(this);
        this.verticalSeekBar = (SeekBar) findViewById(R.id.seekBar2);
        this.verticalSeekBar.setOnSeekBarChangeListener(this);
        this.horizontalPicker = (LineColorPicker) findViewById(R.id.picker);
        this.horizontalPickerColor = (LineColorPicker) findViewById(R.id.picker1);
        this.shadowPickerColor = (LineColorPicker) findViewById(R.id.pickerShadow);
        this.pickerBg = (LineColorPicker) findViewById(R.id.pickerBg);
        this.lay_color = (RelativeLayout) findViewById(R.id.lay_color);
        this.lay_hue = (RelativeLayout) findViewById(R.id.lay_hue);
        initOverlayRecycler();
        this.lay_effects.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.lay_textEdit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.seekbar_container.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.seekbar_handle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.fontsShow = (LinearLayout) findViewById(R.id.fontsShow);
        this.colorShow = (LinearLayout) findViewById(R.id.colorShow);
        this.sadowShow = (LinearLayout) findViewById(R.id.sadowShow);
        this.bgShow = (LinearLayout) findViewById(R.id.bgShow);
        this.controlsShow = (LinearLayout) findViewById(R.id.controlsShow);
        this.layArr[0] = (RelativeLayout) findViewById(R.id.lay_controls);
        this.layArr[1] = (RelativeLayout) findViewById(R.id.lay_fonts);
        this.layArr[2] = (RelativeLayout) findViewById(R.id.lay_colors);
        this.layArr[3] = (RelativeLayout) findViewById(R.id.lay_shadow);
        this.layArr[4] = (RelativeLayout) findViewById(R.id.lay_backgnd);
        setSelected(R.id.lay_controls);
        this.adapter = new AssetsGridMain(this, getResources().getStringArray(R.array.fonts_array));
        this.adapter.setSelected(0);
        GridView gridView = (GridView) findViewById(R.id.font_gridview);
        gridView.setAdapter(this.adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                PosterActivity posterActivity = PosterActivity.this;
                posterActivity.setTextFonts((String) posterActivity.adapter.getItem(i));
                PosterActivity.this.adapter.setSelected(i);
            }
        });
        this.adaptor_txtBg = new RecyclerTextBgAdapter(this, this.imageId);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.txtBg_recylr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(this.adaptor_txtBg);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(View view, int i) {
                PosterActivity posterActivity = PosterActivity.this;
                StringBuilder sb = new StringBuilder();
                sb.append("btxt");
                sb.append(String.valueOf(i));
                posterActivity.setTextBgTexture(sb.toString());
            }
        }));
        this.lay_colorOpacity = (RelativeLayout) findViewById(R.id.lay_colorOpacity);
        this.lay_controlStkr = (RelativeLayout) findViewById(R.id.lay_controlStkr);
        this.lay_colorOacity = (LinearLayout) findViewById(R.id.lay_colorOacity);
        this.controlsShowStkr = (LinearLayout) findViewById(R.id.controlsShowStkr);
        this.lay_colorOpacity.setOnClickListener(this);
        this.lay_controlStkr.setOnClickListener(this);
        this.listFragment = new ListFragment();
        showFragment(this.listFragment);
    }

    private void initOverlayRecycler() {
        this.adaptor_overlay = new RecyclerOverLayAdapter(this, Constants.overlayArr);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.overlay_recylr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(this.adaptor_overlay);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(View view, int i) {
                PosterActivity posterActivity = PosterActivity.this;
                StringBuilder sb = new StringBuilder();
                sb.append("o");
                sb.append(i + 1);
                posterActivity.overlay_Name = sb.toString();
                PosterActivity posterActivity2 = PosterActivity.this;
                posterActivity2.setBitmapOverlay(posterActivity2.getResources().getIdentifier(PosterActivity.this.overlay_Name, "drawable", PosterActivity.this.getPackageName()));
                int i2 = PosterActivity.this.int_overlay_selection % 3;
                PosterActivity.this.int_overlay_selection++;
            }
        }));
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.lay_container, fragment, "fragment").commit();
    }

    @RequiresApi(api = 17)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_sticker /*2131296298*/:
                removeScroll();
                removeImageViewControll();
                if (this.lay_SeekbarMain.getVisibility() == View.VISIBLE) {
                    this.lay_SeekbarMain.startAnimation(this.animSlideDown);
                    this.lay_SeekbarMain.setVisibility(View.GONE);
                }
                this.lay_effects.setVisibility(View.GONE);
                this.lay_StkrMain.setVisibility(View.GONE);
                this.select_backgnd.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.select_effect.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.user_image.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_text.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_sticker.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.img_oK.setVisibility(View.GONE);
                btn_layControls.setVisibility(View.GONE);
                if (this.lay_TextMain.getVisibility() == View.VISIBLE) {
                    this.lay_TextMain.startAnimation(this.animSlideDown);
                    this.lay_TextMain.setVisibility(View.GONE);
                }
                this.lay_sticker.setVisibility(View.VISIBLE);
                return;
            case R.id.add_text /*2131296299*/:
                removeScroll();
                removeImageViewControll();
                if (this.lay_SeekbarMain.getVisibility() == View.VISIBLE) {
                    this.lay_SeekbarMain.startAnimation(this.animSlideDown);
                    this.lay_SeekbarMain.setVisibility(View.GONE);
                }
                this.lay_effects.setVisibility(View.GONE);
                this.lay_StkrMain.setVisibility(View.GONE);
                this.select_backgnd.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.select_effect.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.user_image.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_sticker.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_text.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.lay_TextMain.setVisibility(View.GONE);
                openTextActivity();
                return;
            case R.id.btnColor /*2131296351*/:
                new AmbilWarnaDialog(this, 0, new OnAmbilWarnaListener() {
                    public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                    }

                    public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                        PosterActivity.this.updateColor(i);
                    }
                }).show();
                return;
            case R.id.btn_Up /*2131296360*/:
                if (this.seekbar_handle.getVisibility() == View.VISIBLE) {
                    hideSeekBarContainer();
                    return;
                } else {
                    showSeekBarContainer();
                    return;
                }
            case R.id.btn_bck1 /*2131296363*/:
                this.lay_scroll.smoothScrollTo(0, this.distanceScroll);
                return;
            case R.id.btn_bckprass /*2131296364*/:
                removeScroll();
                onBackPressed();
                return;
            case R.id.btn_done /*2131296369*/:
                if (lay_container.getVisibility() == View.VISIBLE) {
                    lay_container.animate().translationY((float) (-lay_container.getBottom())).setDuration(200).setInterpolator(new AccelerateInterpolator()).start();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            PosterActivity.lay_container.setVisibility(View.GONE);
                        }
                    }, 200);
                }
                removeScroll();
                this.select_backgnd.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.select_effect.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_text.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_sticker.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.user_image.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.lay_effects.setVisibility(View.GONE);
                removeImageViewControll();
                if (this.lay_SeekbarMain.getVisibility() == View.VISIBLE) {
                    this.lay_SeekbarMain.startAnimation(this.animSlideDown);
                    this.lay_SeekbarMain.setVisibility(View.GONE);
                }
                if (this.lay_TextMain.getVisibility() == View.VISIBLE) {
                    this.lay_TextMain.startAnimation(this.animSlideDown);
                    this.lay_TextMain.setVisibility(View.GONE);
                }
                if (this.lay_StkrMain.getVisibility() == View.VISIBLE) {
                    this.lay_StkrMain.startAnimation(this.animSlideDown);
                    this.lay_StkrMain.setVisibility(View.GONE);
                }
                this.guideline.setVisibility(View.GONE);
                showDialogSave();
                return;
            case R.id.btn_layControls /*2131296372*/:
                removeScroll();
                removeImageViewControll();
                if (this.lay_TextMain.getVisibility() == View.VISIBLE) {
                    this.lay_TextMain.startAnimation(this.animSlideDown);
                    this.lay_TextMain.setVisibility(View.GONE);
                }
                if (this.lay_SeekbarMain.getVisibility() == View.VISIBLE) {
                    this.lay_SeekbarMain.startAnimation(this.animSlideDown);
                    this.lay_SeekbarMain.setVisibility(View.GONE);
                }
                if (this.lay_StkrMain.getVisibility() == View.VISIBLE) {
                    this.lay_StkrMain.startAnimation(this.animSlideDown);
                    this.lay_StkrMain.setVisibility(View.GONE);
                }
                if (lay_container.getVisibility() == View.GONE) {
                    btn_layControls.setVisibility(View.GONE);
                    this.listFragment.getLayoutChild();
                    lay_container.setVisibility(View.VISIBLE);
                    lay_container.animate().translationX((float) lay_container.getLeft()).setDuration(200).setInterpolator(new DecelerateInterpolator()).start();
                    return;
                }
                lay_container.setVisibility(View.VISIBLE);
                lay_container.animate().translationX((float) (-lay_container.getRight())).setDuration(200).setInterpolator(new AccelerateInterpolator()).start();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        PosterActivity.lay_container.setVisibility(View.GONE);
                        PosterActivity.btn_layControls.setVisibility(View.VISIBLE);
                    }
                }, 200);
                return;
            case R.id.btn_piclColor /*2131296375*/:
                int removeBoderPosition = getRemoveBoderPosition();
                removeImageViewControll();
                withoutWatermark = viewToBitmap(this.main_rel);
                Intent intent = new Intent(this, PickColorImageActivity.class);
                intent.putExtra("way", "txtColor");
                intent.putExtra("visiPosition", removeBoderPosition);
                intent.putExtra("color", this.textColorSet);
                startActivity(intent);
                return;
            case R.id.btn_piclColor2 /*2131296376*/:
                int removeBoderPosition2 = getRemoveBoderPosition();
                removeImageViewControll();
                withoutWatermark = viewToBitmap(this.main_rel);
                Intent intent2 = new Intent(this, PickColorImageActivity.class);
                intent2.putExtra("way", "txtShadow");
                intent2.putExtra("visiPosition", removeBoderPosition2);
                intent2.putExtra("color", this.shadowColor);
                startActivity(intent2);
                return;
            case R.id.btn_piclColor3 /*2131296377*/:
                int removeBoderPosition3 = getRemoveBoderPosition();
                removeImageViewControll();
                withoutWatermark = viewToBitmap(this.main_rel);
                Intent intent3 = new Intent(this, PickColorImageActivity.class);
                intent3.putExtra("way", "txtBg");
                intent3.putExtra("visiPosition", removeBoderPosition3);
                intent3.putExtra("color", this.bgColor);
                startActivity(intent3);
                return;
            case R.id.btn_piclColorS /*2131296378*/:
                int removeBoderPosition4 = getRemoveBoderPosition();
                removeImageViewControll();
                withoutWatermark = viewToBitmap(this.main_rel);
                Intent intent4 = new Intent(this, PickColorImageActivity.class);
                intent4.putExtra("way", "stkr");
                intent4.putExtra("visiPosition", removeBoderPosition4);
                intent4.putExtra("color", this.stkrColorSet);
                startActivity(intent4);
                return;
            case R.id.btn_txtColor /*2131296379*/:
                new AmbilWarnaDialog(this, this.textColorSet, new OnAmbilWarnaListener() {
                    public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                    }

                    public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                        PosterActivity.this.updateColor(i);
                    }
                }).show();
                return;
            case R.id.btn_txtColor1 /*2131296380*/:
                new AmbilWarnaDialog(this, this.stkrColorSet, new OnAmbilWarnaListener() {
                    public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                    }

                    public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                        PosterActivity.this.updateColor(i);
                    }
                }).show();
                return;
            case R.id.btn_txtColor2 /*2131296381*/:
                new AmbilWarnaDialog(this, this.shadowColor, new OnAmbilWarnaListener() {
                    public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                    }

                    public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                        PosterActivity.this.updateShadow(i);
                    }
                }).show();
                return;
            case R.id.btn_txtColor3 /*2131296382*/:
                new AmbilWarnaDialog(this, this.bgColor, new OnAmbilWarnaListener() {
                    public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                    }

                    public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                        PosterActivity.this.updateBgColor(i);
                    }
                }).show();
                return;
            case R.id.btn_up_down /*2131296384*/:
                this.focusedCopy = this.focusedView;
                removeScroll();
                this.lay_StkrMain.requestLayout();
                this.lay_StkrMain.postInvalidate();
                if (this.seekbar_container.getVisibility() == View.VISIBLE) {
                    hideResContainer();
                    return;
                } else {
                    showResContainer();
                    return;
                }
            case R.id.btn_up_down1 /*2131296385*/:
                this.focusedCopy = this.focusedView;
                removeScroll();
                this.lay_TextMain.requestLayout();
                this.lay_TextMain.postInvalidate();
                if (this.lay_textEdit.getVisibility() == View.VISIBLE) {
                    hideTextResContainer();
                    return;
                } else {
                    showTextResContainer();
                    return;
                }
            case R.id.center_rel /*2131296399*/:
                this.lay_effects.setVisibility(View.GONE);
                this.lay_StkrMain.setVisibility(View.GONE);
                this.guideline.setVisibility(View.GONE);
                onTouchApply();
                return;
            case R.id.lay_backgnd /*2131296551*/:
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.VISIBLE);
                this.controlsShow.setVisibility(View.GONE);
                setSelected(R.id.lay_backgnd);
                return;
            case R.id.lay_colorOpacity /*2131296557*/:
                this.lay_colorOacity.setVisibility(View.VISIBLE);
                this.controlsShowStkr.setVisibility(View.GONE);
                this.lay_colorOpacity.setBackgroundResource(R.drawable.trans);
                this.lay_controlStkr.setBackgroundColor(getResources().getColor(R.color.bg_colors));
                return;
            case R.id.lay_colors /*2131296558*/:
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.VISIBLE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                setSelected(R.id.lay_colors);
                return;
            case R.id.lay_controlStkr /*2131296560*/:
                this.lay_colorOacity.setVisibility(View.GONE);
                this.controlsShowStkr.setVisibility(View.VISIBLE);
                this.lay_controlStkr.setBackgroundResource(R.drawable.trans);
                this.lay_colorOpacity.setBackgroundColor(getResources().getColor(R.color.bg_colors));
                return;
            case R.id.lay_controls /*2131296561*/:
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.VISIBLE);
                setSelected(R.id.lay_controls);
                return;
            case R.id.lay_dupliStkr /*2131296563*/:
                int childCount = txt_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = txt_stkr_rel.getChildAt(i);
                    if (childAt instanceof ResizableStickerView) {
                        ResizableStickerView resizableStickerView = (ResizableStickerView) childAt;
                        if (resizableStickerView.getBorderVisbilty()) {
                            ResizableStickerView resizableStickerView2 = new ResizableStickerView(this);
                            ResizableStickerView resizableStickerView3 = resizableStickerView2;
                            resizableStickerView3.setComponentInfo(resizableStickerView.getComponentInfo());
                            resizableStickerView2.setId(View.generateViewId());
                            resizableStickerView3.setMainLayoutWH((float) this.main_rel.getWidth(), (float) this.main_rel.getHeight());
                            txt_stkr_rel.addView(resizableStickerView2);
                            removeImageViewControll();
                            resizableStickerView3.setOnTouchCallbackListener(this);
                            resizableStickerView3.setBorderVisibility(true);
                        }
                    }
                }
                return;
            case R.id.lay_dupliText /*2131296564*/:
                int childCount2 = txt_stkr_rel.getChildCount();
                for (int i2 = 0; i2 < childCount2; i2++) {
                    View childAt2 = txt_stkr_rel.getChildAt(i2);
                    if (childAt2 instanceof AutofitTextRel) {
                        AutofitTextRel autofitTextRel = (AutofitTextRel) childAt2;
                        if (autofitTextRel.getBorderVisibility()) {
                            AutofitTextRel autofitTextRel2 = new AutofitTextRel(this);
                            txt_stkr_rel.addView(autofitTextRel2);
                            removeImageViewControll();
                            AutofitTextRel autofitTextRel3 = autofitTextRel2;
                            autofitTextRel3.setTextInfo(autofitTextRel.getTextInfo(), false);
                            autofitTextRel3.setId(View.generateViewId());
                            autofitTextRel3.setOnTouchCallbackListener(this);
                            autofitTextRel3.setBorderVisibility(true);
                        }
                    }
                }
                return;
            case R.id.lay_edit /*2131296565*/:
                doubleTabPrass();
                return;
            case R.id.lay_fonts /*2131296568*/:
                this.fontsShow.setVisibility(View.VISIBLE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                setSelected(R.id.lay_fonts);
                return;
            case R.id.lay_shadow /*2131296577*/:
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.VISIBLE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                setSelected(R.id.lay_shadow);
                return;
            case R.id.lay_touchremove /*2131296582*/:
                this.lay_effects.setVisibility(View.GONE);
                this.lay_StkrMain.setVisibility(View.GONE);
                this.guideline.setVisibility(View.GONE);
                onTouchApply();
                return;
            case R.id.o0 /*2131296690*/:
                this.adaptor_overlay.setSelected(500);
                this.overlay_Name = "";
                this.lay_filter.setVisibility(View.GONE);
                this.trans_img.setVisibility(View.GONE);
                return;
            case R.id.select_backgnd /*2131296799*/:
                removeScroll();
                removeImageViewControll();
                this.lay_effects.setVisibility(View.GONE);
                this.lay_StkrMain.setVisibility(View.GONE);
                if (this.lay_SeekbarMain.getVisibility() == View.VISIBLE) {
                    this.lay_SeekbarMain.startAnimation(this.animSlideDown);
                    this.lay_SeekbarMain.setVisibility(View.GONE);
                }
                this.select_backgnd.setBackgroundResource(R.drawable.trans);
                this.select_effect.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.user_image.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_text.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_sticker.setBackgroundColor(getResources().getColor(R.color.colormediam));
                if (this.lay_TextMain.getVisibility() == View.VISIBLE) {
                    this.lay_TextMain.startAnimation(this.animSlideDown);
                    this.lay_TextMain.setVisibility(View.GONE);
                }
                startActivityForResult(new Intent(this, SelectImageTwoActivity.class), 4);
                return;
            case R.id.select_effect /*2131296801*/:
                removeScroll();
                removeImageViewControll();
                if (this.lay_SeekbarMain.getVisibility() == View.VISIBLE) {
                    this.lay_SeekbarMain.startAnimation(this.animSlideDown);
                    this.lay_SeekbarMain.setVisibility(View.GONE);
                }
                if (this.lay_effects.getVisibility() != View.VISIBLE) {
                    this.lay_effects.setVisibility(View.VISIBLE);
                    this.lay_effects.startAnimation(this.animSlideUp);
                }
                if (this.lay_TextMain.getVisibility() == View.VISIBLE) {
                    this.lay_TextMain.startAnimation(this.animSlideDown);
                    this.lay_TextMain.setVisibility(View.GONE);
                }
                this.lay_StkrMain.setVisibility(View.GONE);
                this.select_backgnd.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.select_effect.setBackgroundResource(R.drawable.trans);
                this.user_image.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_text.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_sticker.setBackgroundColor(getResources().getColor(R.color.colormediam));
                return;
            case R.id.txt_bg_none /*2131296888*/:
                this.seekBar3.setProgress(1);
                this.adaptor_txtBg.setSelected(500);
                int childCount3 = txt_stkr_rel.getChildCount();
                for (int i3 = 0; i3 < childCount3; i3++) {
                    View childAt3 = txt_stkr_rel.getChildAt(i3);
                    if (childAt3 instanceof AutofitTextRel) {
                        AutofitTextRel autofitTextRel4 = (AutofitTextRel) childAt3;
                        if (autofitTextRel4.getBorderVisibility()) {
                            autofitTextRel4.setBgAlpha(0);
                            this.bgDrawable = "0";
                            this.bgColor = 0;
                        }
                    }
                }
                return;
            case R.id.user_image /*2131296909*/:
                removeScroll();
                removeImageViewControll();
                this.lay_effects.setVisibility(View.GONE);
                this.lay_StkrMain.setVisibility(View.GONE);
                if (this.lay_SeekbarMain.getVisibility() == View.VISIBLE) {
                    this.lay_SeekbarMain.startAnimation(this.animSlideDown);
                    this.lay_SeekbarMain.setVisibility(View.GONE);
                }
                this.select_backgnd.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.select_effect.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.user_image.setBackgroundResource(R.drawable.trans);
                this.add_text.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_sticker.setBackgroundColor(getResources().getColor(R.color.colormediam));
                if (this.lay_TextMain.getVisibility() == View.VISIBLE) {
                    this.lay_TextMain.startAnimation(this.animSlideDown);
                    this.lay_TextMain.setVisibility(View.GONE);
                }
                showDialogPicker();
                return;
            default:
                return;
        }
    }

    private void showResContainer() {
        this.btn_up_down.animate().setDuration(500).start();
        this.btn_up_down.setBackgroundResource(R.drawable.textlib_down);
        this.seekbar_container.setVisibility(View.VISIBLE);
        this.lay_StkrMain.startAnimation(this.animSlideUp);
        this.lay_StkrMain.requestLayout();
        this.lay_StkrMain.postInvalidate();
        this.lay_StkrMain.post(new Runnable() {
            public void run() {
                PosterActivity posterActivity = PosterActivity.this;
                posterActivity.stickerScrollView(posterActivity.focusedView);
            }
        });
    }

    private void hideResContainer() {
        this.btn_up_down.animate().setDuration(500).start();
        this.btn_up_down.setBackgroundResource(R.drawable.textlib_up);
        this.lay_StkrMain.startAnimation(this.animSlideDown);
        this.seekbar_container.setVisibility(View.GONE);
        this.lay_StkrMain.requestLayout();
        this.lay_StkrMain.postInvalidate();
        this.lay_StkrMain.post(new Runnable() {
            public void run() {
                PosterActivity posterActivity = PosterActivity.this;
                posterActivity.stickerScrollView(posterActivity.focusedView);
            }
        });
    }

    private void showTextResContainer() {
        this.btn_up_down1.animate().setDuration(500).start();
        this.btn_up_down1.setBackgroundResource(R.drawable.textlib_down);
        this.lay_textEdit.setVisibility(View.VISIBLE);
        this.lay_TextMain.startAnimation(this.animSlideUp);
        this.lay_TextMain.requestLayout();
        this.lay_TextMain.postInvalidate();
        this.lay_TextMain.post(new Runnable() {
            public void run() {
                PosterActivity posterActivity = PosterActivity.this;
                posterActivity.textScrollView(posterActivity.focusedView);
            }
        });
    }

    private void hideTextResContainer() {
        this.btn_up_down1.animate().setDuration(500).start();
        this.btn_up_down1.setBackgroundResource(R.drawable.textlib_up);
        this.lay_TextMain.startAnimation(this.animSlideDown);
        this.lay_textEdit.setVisibility(View.GONE);
        this.lay_TextMain.requestLayout();
        this.lay_TextMain.postInvalidate();
        this.lay_TextMain.post(new Runnable() {
            public void run() {
                PosterActivity posterActivity = PosterActivity.this;
                posterActivity.textScrollView(posterActivity.focusedView);
            }
        });
    }

    private void hideSeekBarContainer() {
        this.btn_Up.setBackgroundResource(R.drawable.textlib_up);
        this.lay_SeekbarMain.startAnimation(this.animSlideDown);
        this.seekbar_handle.setVisibility(View.GONE);
    }

    private void showSeekBarContainer() {
        this.btn_Up.setBackgroundResource(R.drawable.textlib_down);
        this.seekbar_handle.setVisibility(View.VISIBLE);
        this.lay_SeekbarMain.startAnimation(this.animSlideUp);
    }

    /* access modifiers changed from: private */
    public void setBitmapOverlay(int i) {
        this.lay_filter.setVisibility(View.VISIBLE);
        this.trans_img.setVisibility(View.VISIBLE);
        try {
            this.trans_img.setImageBitmap(BitmapFactory.decodeResource(getResources(), i));
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            Options options2 = new Options();
            options2.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), i, options2);
            Options options3 = new Options();
            options3.inSampleSize = ImageUtils.getClosestResampleSize(options2.outWidth, options2.outHeight, this.main_rel.getWidth() < this.main_rel.getHeight() ? this.main_rel.getWidth() : this.main_rel.getHeight());
            options2.inJustDecodeBounds = false;
            this.trans_img.setImageBitmap(BitmapFactory.decodeResource(getResources(), i, options3));
        }
    }

    private void initViewPager() {
        this.tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        this._mViewPager = (ViewPager) findViewById(R.id.imageviewPager);
        this._mViewPager.setAdapter(new StickerViewPagerAdapter(this, getSupportFragmentManager()));
        this.tabs.setViewPager(this._mViewPager);
        this.tabs.setTypeface(this.ttf, 0);
        this._mViewPager.setCurrentItem(0);
        tabs.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /* access modifiers changed from: private */
    public void setTextBgTexture(String str) {
        getResources().getIdentifier(str, "drawable", getPackageName());
        int childCount = txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txt_stkr_rel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setBgDrawable(str);
                    autofitTextRel.setBgAlpha(this.seekBar3.getProgress());
                    this.bgColor = 0;
                    ((AutofitTextRel) txt_stkr_rel.getChildAt(i)).getTextInfo().setBG_DRAWABLE(str);
                    this.bgDrawable = autofitTextRel.getBgDrawable();
                    this.bgAlpha = this.seekBar3.getProgress();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void setTextFonts(String str) {
        this.fontName = str;
        int childCount = txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txt_stkr_rel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setTextFont(str);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateColor(int i) {
        int childCount = txt_stkr_rel.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = txt_stkr_rel.getChildAt(i2);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setTextColor(i);
                    this.tColor = i;
                    this.textColorSet = i;
                    this.horizontalPicker.setSelectedColor(i);
                }
            }
            if (childAt instanceof ResizableStickerView) {
                ResizableStickerView resizableStickerView = (ResizableStickerView) childAt;
                if (resizableStickerView.getBorderVisbilty()) {
                    resizableStickerView.setColor(i);
                    this.stkrColorSet = i;
                    this.horizontalPickerColor.setSelectedColor(i);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateShadow(int i) {
        int childCount = txt_stkr_rel.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = txt_stkr_rel.getChildAt(i2);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setTextShadowColor(i);
                    this.shadowColor = i;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateBgColor(int i) {
        int childCount = txt_stkr_rel.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = txt_stkr_rel.getChildAt(i2);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setBgAlpha(this.seekBar3.getProgress());
                    autofitTextRel.setBgColor(i);
                    this.bgColor = i;
                    this.bgDrawable = "0";
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void updatePositionSticker(String str) {
        int childCount = txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txt_stkr_rel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    if (str.equals("incrX")) {
                        autofitTextRel.incrX();
                    }
                    if (str.equals("decX")) {
                        autofitTextRel.decX();
                    }
                    if (str.equals("incrY")) {
                        autofitTextRel.incrY();
                    }
                    if (str.equals("decY")) {
                        autofitTextRel.decY();
                    }
                }
            }
            if (childAt instanceof ResizableStickerView) {
                ResizableStickerView resizableStickerView = (ResizableStickerView) childAt;
                if (resizableStickerView.getBorderVisbilty()) {
                    if (str.equals("incrX")) {
                        resizableStickerView.incrX();
                    }
                    if (str.equals("decX")) {
                        resizableStickerView.decX();
                    }
                    if (str.equals("incrY")) {
                        resizableStickerView.incrY();
                    }
                    if (str.equals("decY")) {
                        resizableStickerView.decY();
                    }
                }
            }
        }
    }

    private void createFrame() {
        this.main_rel.setDrawingCacheEnabled(true);
        final Bitmap createBitmap = Bitmap.createBitmap(this.main_rel.getDrawingCache());
        this.main_rel.setDrawingCacheEnabled(false);
        final ProgressDialog show = ProgressDialog.show(this, "", ImageUtils.getSpannableString(this, this.ttf, R.string.plzwait), true);
        show.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                DatabaseHandler databaseHandler = null;
                try {
                    if (PosterActivity.this.ratio.equals("")) {
                        PosterActivity.this.temp_path = Constants.saveBitmapObject1(PosterActivity.imgBtmap);
                    }
                    String saveBitmapObject = Constants.saveBitmapObject(PosterActivity.this, ImageUtils.resizeBitmap(createBitmap, ((int) PosterActivity.this.screenWidth) / 3, ((int) PosterActivity.this.screenHeight) / 3));
                    if (saveBitmapObject != null) {
                        TemplateInfo templateInfo = new TemplateInfo();
                        templateInfo.setTHUMB_URI(saveBitmapObject);
                        templateInfo.setFRAME_NAME(PosterActivity.this.frame_Name);
                        templateInfo.setRATIO(PosterActivity.this.ratio);
                        templateInfo.setPROFILE_TYPE(PosterActivity.this.profile);
                        templateInfo.setSEEK_VALUE(String.valueOf(PosterActivity.this.seekValue));
                        templateInfo.setTYPE("USER");
                        templateInfo.setTEMP_PATH(PosterActivity.this.temp_path);
                        templateInfo.setTEMPCOLOR(PosterActivity.this.hex);
                        templateInfo.setOVERLAY_NAME(PosterActivity.this.overlay_Name);
                        templateInfo.setOVERLAY_OPACITY(PosterActivity.this.seek.getProgress());
                        templateInfo.setOVERLAY_BLUR(PosterActivity.this.seek_blur.getProgress());
                        databaseHandler = DatabaseHandler.getDbHandler(PosterActivity.this.getApplicationContext());
                        PosterActivity.this.saveComponent1(databaseHandler.insertTemplateRow(templateInfo), databaseHandler);
                        PosterActivity.isUpdated = true;
                    }
                    if (databaseHandler != null) {
                        databaseHandler.close();
                    }
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Exception ");
                    sb.append(e.getMessage());
                    Log.i("testing", sb.toString());
                    e.printStackTrace();
                    if (databaseHandler != null) {
                        databaseHandler.close();
                    }
                } catch (Throwable unused) {
                    if (databaseHandler != null) {
                        databaseHandler.close();
                    }
                }
                show.dismiss();
            }
        }).start();
        show.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                final PrettyDialog prettyDialog = new PrettyDialog(PosterActivity.this);
                prettyDialog.setIcon(Integer.valueOf(R.drawable.ic_close));
                prettyDialog.setTitle("Design Saved");
                prettyDialog.setMessage("Your Design successfully saved in My Designs.");
                prettyDialog.setIconCallback(new PrettyDialogCallback() {
                    public void onClick() {
                        prettyDialog.dismiss();
                    }
                });
                prettyDialog.addButton("Ok", Integer.valueOf(R.color.pdlg_color_white), Integer.valueOf(R.color.green), new PrettyDialogCallback() {
                    public void onClick() {
                        prettyDialog.dismiss();
                        if (!PosterActivity.this.temp_Type.equals("")) {
                            PosterActivity.this.editor.putBoolean("isChanged", true);
                            PosterActivity.this.editor.commit();
                        }
                        PosterActivity.this.finish();
                    }
                });
                prettyDialog.show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void saveComponent1(long j, DatabaseHandler databaseHandler) {
        int childCount = txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txt_stkr_rel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                TextInfo textInfo = ((AutofitTextRel) childAt).getTextInfo();
                textInfo.setTEMPLATE_ID((int) j);
                textInfo.setORDER(i);
                textInfo.setTYPE("TEXT");
                databaseHandler.insertTextRow(textInfo);
            } else {
                saveShapeAndSticker(j, i, TYPE_STICKER, databaseHandler);
            }
        }
    }

    private void saveComponent(long j, DatabaseHandler databaseHandler) {
        int childCount = this.shape_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            saveShapeAndSticker(j, i, TYPE_SHAPE, databaseHandler);
        }
    }

    public void saveShapeAndSticker(long j, int i, int i2, DatabaseHandler databaseHandler) {
        ComponentInfo componentInfo = ((ResizableStickerView) txt_stkr_rel.getChildAt(i)).getComponentInfo();
        componentInfo.setTEMPLATE_ID((int) j);
        componentInfo.setTYPE("STICKER");
        componentInfo.setORDER(i);
        databaseHandler.insertComponentInfoRow(componentInfo);
    }

    private void openTextActivity() {
        Intent intent = new Intent(this, TextActivity.class);
        Bundle bundle = new Bundle();
        bundle.putFloat("X", (float) ((txt_stkr_rel.getWidth() / 2) - ImageUtils.dpToPx(this, 100)));
        bundle.putFloat("Y", (float) ((txt_stkr_rel.getHeight() / 2) - ImageUtils.dpToPx(this, 100)));
        bundle.putInt("wi", ImageUtils.dpToPx(this, Callback.DEFAULT_DRAG_ANIMATION_DURATION));
        bundle.putInt("he", ImageUtils.dpToPx(this, Callback.DEFAULT_DRAG_ANIMATION_DURATION));
        bundle.putString("text", "");
        bundle.putString("fontName", this.fontName);
        bundle.putInt("tColor", this.tColor);
        bundle.putInt("tAlpha", this.tAlpha);
        bundle.putInt("shadowColor", this.shadowColor);
        bundle.putInt("shadowProg", this.shadowProg);
        bundle.putString("bgDrawable", this.bgDrawable);
        bundle.putInt("bgColor", this.bgColor);
        bundle.putInt("bgAlpha", this.bgAlpha);
        bundle.putFloat("rotation", this.rotation);
        bundle.putString("view", "mosaic");
        intent.putExtras(bundle);
        startActivityForResult(intent, TEXT_ACTIVITY);
    }

    private void onTouchApply() {
        removeScroll();
        this.select_backgnd.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.select_effect.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.user_image.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.add_text.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.add_sticker.setBackgroundColor(getResources().getColor(R.color.colormediam));
        if (this.lay_TextMain.getVisibility() == View.VISIBLE) {
            this.lay_TextMain.startAnimation(this.animSlideDown);
            this.lay_TextMain.setVisibility(View.GONE);
        }
        if (this.showtailsSeek) {
            this.lay_handletails.setVisibility(View.VISIBLE);
        }
        if (this.lay_SeekbarMain.getVisibility() == View.GONE) {
            this.lay_SeekbarMain.setVisibility(View.VISIBLE);
            this.lay_SeekbarMain.startAnimation(this.animSlideUp);
        }
        removeImageViewControll();
    }

    public void setSelected(int i) {
        int i2 = 0;
        while (true) {
            View[] viewArr = this.layArr;
            if (i2 < viewArr.length) {
                if (viewArr[i2].getId() == i) {
                    this.layArr[i2].setBackgroundResource(R.drawable.trans);
                } else {
                    this.layArr[i2].setBackgroundColor(getResources().getColor(R.color.colormediam));
                }
                i2++;
            } else {
                return;
            }
        }
    }

    @RequiresApi(api = 17)
    public void onSnapFilter(int i, int i2, String str) {
        this.lay_sticker.setVisibility(View.GONE);
        this.add_sticker.setBackgroundResource(R.drawable.trans);
        this.img_oK.setVisibility(View.VISIBLE);
        btn_layControls.setVisibility(View.VISIBLE);
        if (this.lay_TextMain.getVisibility() == View.VISIBLE) {
            this.lay_TextMain.startAnimation(this.animSlideDown);
            this.lay_TextMain.setVisibility(View.GONE);
        }
        if (!str.equals("")) {
            this.color_Type = "white";
            addSticker("", str, null);
        } else if (i2 == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("a_");
            sb.append(String.valueOf(i + 1));
            setDrawable("white", sb.toString());
        } else if (i2 == 1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("b_");
            sb2.append(String.valueOf(i + 1));
            setDrawable("white", sb2.toString());
        } else if (i2 == 2) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("c_");
            sb3.append(String.valueOf(i + 1));
            setDrawable("white", sb3.toString());
        } else if (i2 == 3) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("d_");
            sb4.append(String.valueOf(i + 1));
            setDrawable("white", sb4.toString());
        } else if (i2 == 4) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("e_");
            sb5.append(String.valueOf(i + 1));
            setDrawable("white", sb5.toString());
        } else if (i2 == 5) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append("f_");
            sb6.append(String.valueOf(i + 1));
            setDrawable("white", sb6.toString());
        } else if (i2 == 6) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("g_");
            sb7.append(String.valueOf(i + 1));
            setDrawable("white", sb7.toString());
        } else if (i2 == 7) {
            StringBuilder sb8 = new StringBuilder();
            sb8.append("h_");
            sb8.append(String.valueOf(i + 1));
            setDrawable("white", sb8.toString());
        } else if (i2 == 8) {
            StringBuilder sb9 = new StringBuilder();
            sb9.append("i_");
            sb9.append(String.valueOf(i + 1));
            setDrawable("white", sb9.toString());
        } else if (i2 == 9) {
            StringBuilder sb10 = new StringBuilder();
            sb10.append("j_");
            sb10.append(String.valueOf(i + 1));
            setDrawable("white", sb10.toString());
        } else if (i2 == 10) {
            StringBuilder sb11 = new StringBuilder();
            sb11.append("k_");
            sb11.append(String.valueOf(i + 1));
            setDrawable("white", sb11.toString());
        } else if (i2 == 11) {
            StringBuilder sb12 = new StringBuilder();
            sb12.append("l_");
            sb12.append(String.valueOf(i + 1));
            setDrawable("white", sb12.toString());
        } else if (i2 == 12) {
            StringBuilder sb13 = new StringBuilder();
            sb13.append("m_");
            sb13.append(String.valueOf(i + 1));
            setDrawable("white", sb13.toString());
        } else if (i2 == 13) {
            StringBuilder sb14 = new StringBuilder();
            sb14.append("n_");
            sb14.append(String.valueOf(i + 1));
            setDrawable("white", sb14.toString());
        } else if (i2 == 14) {
            StringBuilder sb15 = new StringBuilder();
            sb15.append("o_");
            sb15.append(String.valueOf(i + 1));
            setDrawable("white", sb15.toString());
        } else if (i2 == 15) {
            StringBuilder sb16 = new StringBuilder();
            sb16.append("p_");
            sb16.append(String.valueOf(i + 1));
            setDrawable("white", sb16.toString());
        } else if (i2 == 16) {
            StringBuilder sb17 = new StringBuilder();
            sb17.append("q_");
            sb17.append(String.valueOf(i + 1));
            setDrawable("white", sb17.toString());
        } else if (i2 == 17) {
            StringBuilder sb18 = new StringBuilder();
            sb18.append("r_");
            sb18.append(String.valueOf(i + 1));
            setDrawable("white", sb18.toString());
        } else if (i2 == 18) {
            StringBuilder sb19 = new StringBuilder();
            sb19.append("s_");
            sb19.append(String.valueOf(i + 1));
            setDrawable("white", sb19.toString());
        } else if (i2 == 19) {
            StringBuilder sb20 = new StringBuilder();
            sb20.append("t_");
            sb20.append(String.valueOf(i + 1));
            setDrawable("white", sb20.toString());
        } else if (i2 == 20) {
            StringBuilder sb21 = new StringBuilder();
            sb21.append("sh");
            sb21.append(String.valueOf(i + 1));
            setDrawable("white", sb21.toString());
        } else if (i2 == 21) {
            StringBuilder sb22 = new StringBuilder();
            sb22.append("u_");
            sb22.append(String.valueOf(i + 1));
            setDrawable("white", sb22.toString());
        }
    }

    @RequiresApi(api = 17)
    private void setDrawable(String str, String str2) {
        this.color_Type = str;
        if (str.equals("white")) {
            this.lay_color.setVisibility(View.VISIBLE);
            this.lay_hue.setVisibility(View.GONE);
        } else {
            this.lay_color.setVisibility(View.GONE);
            this.lay_hue.setVisibility(View.VISIBLE);
        }
        this.lay_effects.setVisibility(View.GONE);
        this.lay_TextMain.setVisibility(View.GONE);
        addSticker(str2, "", null);
    }

    /* access modifiers changed from: private */
    public Bitmap viewToBitmap(View view) {
        try {
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
            view.draw(new Canvas(createBitmap));
            return createBitmap;
        } finally {
            view.destroyDrawingCache();
        }
    }

    /* access modifiers changed from: private */
    public void saveBitmap(final boolean z) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.plzwait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            public void run() {
                String str;
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Poster Maker");
                    if (!file.exists()) {
                        if (!file.mkdirs()) {
                            Log.d("", "Can't create directory to save image.");
                            Toast.makeText(PosterActivity.this.getApplicationContext(), PosterActivity.this.getResources().getString(R.string.create_dir_err), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("Photo_");
                    sb.append(System.currentTimeMillis());
                    String sb2 = sb.toString();
                    if (z) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(sb2);
                        sb3.append(".png");
                        str = sb3.toString();
                    } else {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(sb2);
                        sb4.append(".jpg");
                        str = sb4.toString();
                    }
                    PosterActivity posterActivity = PosterActivity.this;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(file.getPath());
                    sb5.append(File.separator);
                    sb5.append(str);
                    posterActivity.filename = sb5.toString();
                    File file2 = new File(PosterActivity.this.filename);
                    try {
                        if (!file2.exists()) {
                            file2.createNewFile();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        if (z) {
                            PosterActivity.this.checkMemory = PosterActivity.this.bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
                        } else {
                            Bitmap createBitmap = Bitmap.createBitmap(PosterActivity.this.bitmap.getWidth(), PosterActivity.this.bitmap.getHeight(), PosterActivity.this.bitmap.getConfig());
                            Canvas canvas = new Canvas(createBitmap);
                            canvas.drawColor(-1);
                            canvas.drawBitmap(PosterActivity.this.bitmap, 0.0f, 0.0f, null);
                            PosterActivity.this.checkMemory = createBitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
                            createBitmap.recycle();
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        PosterActivity.isUpadted = true;
                        PosterActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Thread.sleep(1000);
                    progressDialog.dismiss();
                } catch (Exception unused) {
                }
            }
        }).start();
        progressDialog.setOnDismissListener(new OnDismissListener() {

            /* renamed from: postermaster.postermaker.PosterActivity$35$C03271 */
            class C03271 implements DialogInterface.OnClickListener {
                C03271() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }

            public void onDismiss(DialogInterface dialogInterface) {
                if (PosterActivity.this.checkMemory) {
                    Context applicationContext = PosterActivity.this.getApplicationContext();
                    StringBuilder sb = new StringBuilder();
                    sb.append(PosterActivity.this.getString(R.string.saved).toString());
                    sb.append(" ");
                    sb.append(PosterActivity.this.filename);
                    Toast.makeText(applicationContext, sb.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PosterActivity.this, ShareActivity.class);
                    intent.putExtra("uri", PosterActivity.this.filename);
                    intent.putExtra("way", "Poster");
                    startActivityForResult(intent, 1000);
                    return;
                }
                AlertDialog create = new AlertDialog.Builder(PosterActivity.this, 16974126).setMessage(Constants.getSpannableString(PosterActivity.this, Typeface.DEFAULT, R.string.memoryerror)).setPositiveButton(Constants.getSpannableString(PosterActivity.this, Typeface.DEFAULT, R.string.ok), new C03271()).create();
                create.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_;
                create.show();
            }
        });
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        int id = seekBar.getId();
        int i2 = 0;
        if (id == R.id.alpha_seekBar) {
            int childCount = txt_stkr_rel.getChildCount();
            while (i2 < childCount) {
                View childAt = txt_stkr_rel.getChildAt(i2);
                if (childAt instanceof ResizableStickerView) {
                    ResizableStickerView resizableStickerView = (ResizableStickerView) childAt;
                    if (resizableStickerView.getBorderVisbilty()) {
                        resizableStickerView.setAlphaProg(i);
                    }
                }
                i2++;
            }
        } else if (id == R.id.hue_seekBar) {
            int childCount2 = txt_stkr_rel.getChildCount();
            while (i2 < childCount2) {
                View childAt2 = txt_stkr_rel.getChildAt(i2);
                if (childAt2 instanceof ResizableStickerView) {
                    ResizableStickerView resizableStickerView2 = (ResizableStickerView) childAt2;
                    if (resizableStickerView2.getBorderVisbilty()) {
                        resizableStickerView2.setHueProg(i);
                    }
                }
                i2++;
            }
        } else if (id != R.id.seek) {
            switch (id) {
                case R.id.seekBar2 /*2131296792*/:
                    this.processs = i;
                    int childCount3 = txt_stkr_rel.getChildCount();
                    while (i2 < childCount3) {
                        View childAt3 = txt_stkr_rel.getChildAt(i2);
                        if (childAt3 instanceof AutofitTextRel) {
                            AutofitTextRel autofitTextRel = (AutofitTextRel) childAt3;
                            if (autofitTextRel.getBorderVisibility()) {
                                autofitTextRel.setTextAlpha(i);
                            }
                        }
                        i2++;
                    }
                    return;
                case R.id.seekBar3 /*2131296793*/:
                    int childCount4 = txt_stkr_rel.getChildCount();
                    while (i2 < childCount4) {
                        View childAt4 = txt_stkr_rel.getChildAt(i2);
                        if (childAt4 instanceof AutofitTextRel) {
                            AutofitTextRel autofitTextRel2 = (AutofitTextRel) childAt4;
                            if (autofitTextRel2.getBorderVisibility()) {
                                autofitTextRel2.setBgAlpha(i);
                                this.bgAlpha = i;
                            }
                        }
                        i2++;
                    }
                    return;
                case R.id.seekBar_shadow /*2131296794*/:
                    int childCount5 = txt_stkr_rel.getChildCount();
                    while (i2 < childCount5) {
                        View childAt5 = txt_stkr_rel.getChildAt(i2);
                        if (childAt5 instanceof AutofitTextRel) {
                            AutofitTextRel autofitTextRel3 = (AutofitTextRel) childAt5;
                            if (autofitTextRel3.getBorderVisibility()) {
                                autofitTextRel3.setTextShadowProg(i);
                                this.shadowProg = i;
                            }
                        }
                        i2++;
                    }
                    return;
                case R.id.seek_blur /*2131296795*/:
                    if (i != 0) {
                        this.background_blur.setVisibility(View.VISIBLE);
                        this.min = i;
                        this.background_blur.setImageAlpha(i);
                        return;
                    }
                    this.background_blur.setVisibility(View.GONE);
                    return;
                case R.id.seek_tailys /*2131296796*/:
                    this.background_blur.setVisibility(View.GONE);
                    this.seekValue = i;
                    addTilesBG(this.curTileId);
                    return;
                default:
                    return;
            }
        } else {
            this.alpha = i;
            this.trans_img.setImageAlpha(this.alpha);
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getId() == R.id.seek_tailys) {
            if (this.min != 0) {
                this.background_blur.setVisibility(View.VISIBLE);
            } else {
                this.background_blur.setVisibility(View.GONE);
            }
            new BlurOperationAsync(this, imgBtmap, this.background_blur).execute(new String[]{""});
        }
    }

    private void addTilesBG(int i) {
        if (i != 0) {
            setImageBitmapAndResizeLayout1(Constants.getTiledBitmap((Activity) this, i, imgBtmap, this.seek_tailys));
        }
    }

    private void setImageBitmapAndResizeLayout1(Bitmap bitmap2) {
        this.main_rel.getLayoutParams().width = bitmap2.getWidth();
        this.main_rel.getLayoutParams().height = bitmap2.getHeight();
        this.main_rel.postInvalidate();
        this.main_rel.requestLayout();
        this.background_img.setImageBitmap(bitmap2);
        imgBtmap = bitmap2;
    }

    public void onTouchDown(View view) {
        touchDown(view, "hideboder");
    }

    public void onTouchUp(View view) {
        touchUp(view);
    }

    public void onTouchMove(View view) {
        touchMove(view);
    }

    private void touchDown(View view, String str) {
        this.focusedView = view;
        if (str.equals("hideboder")) {
            removeImageViewControll();
        }
        if (view instanceof ResizableStickerView) {
            this.lay_effects.setVisibility(View.GONE);
            this.lay_TextMain.setVisibility(View.GONE);
            this.lay_StkrMain.setVisibility(View.GONE);
            ResizableStickerView resizableStickerView = (ResizableStickerView) view;
            this.stkrColorSet = resizableStickerView.getColor();
            this.horizontalPickerColor.setSelectedColor(this.stkrColorSet);
            this.alphaSeekbar.setProgress(resizableStickerView.getAlphaProg());
            this.hueSeekbar.setProgress(resizableStickerView.getHueProg());
        }
        if (view instanceof AutofitTextRel) {
            this.lay_effects.setVisibility(View.GONE);
            this.lay_StkrMain.setVisibility(View.GONE);
            this.lay_TextMain.setVisibility(View.GONE);
            AutofitTextRel autofitTextRel = (AutofitTextRel) view;
            this.textColorSet = autofitTextRel.getTextColor();
            this.horizontalPicker.setSelectedColor(this.textColorSet);
            this.fontName = autofitTextRel.getFontName();
            this.tColor = autofitTextRel.getTextColor();
            this.shadowColor = autofitTextRel.getTextShadowColor();
            this.shadowProg = autofitTextRel.getTextShadowProg();
            this.tAlpha = autofitTextRel.getTextAlpha();
            this.bgDrawable = autofitTextRel.getBgDrawable();
            this.bgAlpha = autofitTextRel.getBgAlpha();
            this.rotation = view.getRotation();
            this.bgColor = autofitTextRel.getBgColor();
            String[] stringArray = getResources().getStringArray(R.array.fonts_array);
            this.adapter.setSelected(0);
            for (int i = 0; i < stringArray.length; i++) {
                if (stringArray[i].equals(this.fontName)) {
                    this.adapter.setSelected(i);
                }
            }
            if (this.bgDrawable.equals("0") || this.bgAlpha == 0) {
                this.adaptor_txtBg.setSelected(500);
            } else {
                this.adaptor_txtBg.setSelected(Integer.parseInt(this.bgDrawable.replace("btxt", "")));
            }
            this.verticalSeekBar.setProgress(this.tAlpha);
            this.seekBar_shadow.setProgress(this.shadowProg);
            this.seekBar3.setProgress(this.bgAlpha);
        }
        if (this.guideline.getVisibility() == View.GONE) {
            this.guideline.setVisibility(View.VISIBLE);
        }
    }

    private void touchMove(View view) {
        boolean z = view instanceof ResizableStickerView;
        if (z) {
            ResizableStickerView resizableStickerView = (ResizableStickerView) view;
            this.alphaSeekbar.setProgress(resizableStickerView.getAlphaProg());
            this.hueSeekbar.setProgress(resizableStickerView.getHueProg());
        } else {
            this.lay_StkrMain.setVisibility(View.GONE);
        }
        if (z) {
            this.lay_effects.setVisibility(View.GONE);
            this.lay_TextMain.setVisibility(View.GONE);
            this.lay_StkrMain.setVisibility(View.GONE);
            removeScroll();
        }
        if (view instanceof AutofitTextRel) {
            this.lay_effects.setVisibility(View.GONE);
            this.lay_TextMain.setVisibility(View.GONE);
            this.lay_StkrMain.setVisibility(View.GONE);
            removeScroll();
        }
    }

    private void touchUp(final View view) {
        if (this.focusedCopy != this.focusedView) {
            this.seekbar_container.setVisibility(View.VISIBLE);
            this.lay_textEdit.setVisibility(View.VISIBLE);
        }
        if (view instanceof AutofitTextRel) {
            if (this.lay_TextMain.getVisibility() == View.GONE) {
                this.select_backgnd.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.select_effect.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.user_image.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_text.setBackgroundResource(R.drawable.trans);
                this.add_sticker.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.lay_TextMain.setVisibility(View.VISIBLE);
                this.lay_TextMain.startAnimation(this.animSlideUp);
                this.lay_TextMain.post(new Runnable() {
                    public void run() {
                        PosterActivity.this.textScrollView(view);
                    }
                });
            }
            int i = this.processs;
            if (i != 0) {
                this.verticalSeekBar.setProgress(i);
            }
        }
        if (view instanceof ResizableStickerView) {
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(((ResizableStickerView) view).getColorType());
            if (sb.toString().equals("white")) {
                this.lay_color.setVisibility(View.VISIBLE);
                this.lay_hue.setVisibility(View.GONE);
            } else {
                this.lay_color.setVisibility(View.GONE);
                this.lay_hue.setVisibility(View.VISIBLE);
            }
            if (this.lay_StkrMain.getVisibility() == View.GONE) {
                this.select_backgnd.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.select_effect.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.user_image.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_text.setBackgroundColor(getResources().getColor(R.color.colormediam));
                this.add_sticker.setBackgroundResource(R.drawable.trans);
                this.lay_StkrMain.setVisibility(View.VISIBLE);
                this.lay_StkrMain.startAnimation(this.animSlideUp);
                this.lay_StkrMain.post(new Runnable() {
                    public void run() {
                        PosterActivity.this.stickerScrollView(view);
                    }
                });
            }
        }
        if (this.guideline.getVisibility() == View.VISIBLE) {
            this.guideline.setVisibility(View.GONE);
        }
        if (this.lay_SeekbarMain.getVisibility() == View.VISIBLE) {
            this.lay_SeekbarMain.startAnimation(this.animSlideDown);
            this.lay_SeekbarMain.setVisibility(View.GONE);
        }
    }

    public void onDelete() {
        removeScroll();
        if (this.lay_StkrMain.getVisibility() == View.VISIBLE) {
            this.lay_StkrMain.startAnimation(this.animSlideDown);
            this.lay_StkrMain.setVisibility(View.GONE);
        }
        if (this.lay_TextMain.getVisibility() == View.VISIBLE) {
            this.lay_TextMain.startAnimation(this.animSlideDown);
            this.lay_TextMain.setVisibility(View.GONE);
        }
        this.select_backgnd.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.select_effect.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.user_image.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.add_text.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.add_sticker.setBackgroundColor(getResources().getColor(R.color.colormediam));
        this.guideline.setVisibility(View.GONE);
    }

    public void onRotateDown(View view) {
        touchDown(view, "viewboder");
    }

    public void onRotateMove(View view) {
        touchMove(view);
    }

    public void onRotateUp(View view) {
        touchUp(view);
    }

    public void onScaleDown(View view) {
        touchDown(view, "viewboder");
    }

    public void onScaleMove(View view) {
        touchMove(view);
    }

    public void onScaleUp(View view) {
        touchUp(view);
    }

    public void onDoubleTap() {
        doubleTabPrass();
    }

    /* access modifiers changed from: private */
    public void doubleTabPrass() {
        this.editMode = true;
        RelativeLayout relativeLayout = txt_stkr_rel;
        TextInfo textInfo = ((AutofitTextRel) relativeLayout.getChildAt(relativeLayout.getChildCount() - 1)).getTextInfo();
        Intent intent = new Intent(this, TextActivity.class);
        Bundle bundle = new Bundle();
        bundle.putFloat("X", textInfo.getPOS_X());
        bundle.putFloat("Y", textInfo.getPOS_Y());
        bundle.putInt("wi", textInfo.getWIDTH());
        bundle.putInt("he", textInfo.getHEIGHT());
        bundle.putString("text", textInfo.getTEXT());
        bundle.putString("fontName", textInfo.getFONT_NAME());
        bundle.putInt("tColor", textInfo.getTEXT_COLOR());
        bundle.putInt("tAlpha", textInfo.getTEXT_ALPHA());
        bundle.putInt("shadowColor", textInfo.getSHADOW_COLOR());
        bundle.putInt("shadowProg", textInfo.getSHADOW_PROG());
        bundle.putString("bgDrawable", textInfo.getBG_DRAWABLE());
        bundle.putInt("bgColor", textInfo.getBG_COLOR());
        bundle.putInt("bgAlpha", textInfo.getBG_ALPHA());
        bundle.putFloat("rotation", textInfo.getROTATION());
        intent.putExtras(bundle);
        startActivityForResult(intent, TEXT_ACTIVITY);
    }

    public void stickerScrollView(View view) {
        if (view != null) {
            view.getWidth();
            int height = view.getHeight();
            int[] iArr = new int[2];
            this.lay_scroll.getLocationOnScreen(iArr);
            this.parentY = (float) iArr[1];
            this.distance = this.parentY - ((float) ImageUtils.dpToPx(this, 50));
            int[] iArr2 = new int[2];
            view.getLocationOnScreen(iArr2);
            float f = (float) (iArr2[1] + height);
            int[] iArr3 = new int[2];
            this.seekbar_container.getLocationOnScreen(iArr3);
            float f2 = (float) iArr3[1];
            if (this.parentY + ((float) this.lay_scroll.getHeight()) < f) {
                f = this.parentY + ((float) this.lay_scroll.getHeight());
            }
            if (f > f2) {
                this.distanceScroll = (int) (f - f2);
                int i = this.distanceScroll;
                this.dsfc = i;
                if (((float) i) < this.distance) {
                    this.lay_scroll.setY((this.parentY - ((float) ImageUtils.dpToPx(this, 50))) - ((float) this.distanceScroll));
                } else {
                    int scrollY = this.lay_scroll.getScrollY();
                    this.lay_scroll.setLayoutParams(new LayoutParams(-1, -2));
                    this.lay_scroll.postInvalidate();
                    this.lay_scroll.requestLayout();
                    int i2 = (int) ((f - this.distance) - f2);
                    int height2 = this.lay_scroll.getHeight() - i2;
                    this.distanceScroll = scrollY + i2;
                    this.lay_scroll.getLayoutParams().height = height2;
                    this.lay_scroll.postInvalidate();
                    this.lay_scroll.requestLayout();
                }
                this.lay_scroll.post(new Runnable() {
                    public void run() {
                        PosterActivity.this.btn_bck1.performClick();
                    }
                });
            }
        }
    }

    public void textScrollView(View view) {
        if (view != null) {
            view.getWidth();
            int height = view.getHeight();
            int[] iArr = new int[2];
            this.lay_scroll.getLocationOnScreen(iArr);
            this.parentY = (float) iArr[1];
            this.distance = this.parentY - ((float) ImageUtils.dpToPx(this, 50));
            int[] iArr2 = new int[2];
            view.getLocationOnScreen(iArr2);
            float f = (float) (iArr2[1] + height);
            int[] iArr3 = new int[2];
            this.lay_textEdit.getLocationOnScreen(iArr3);
            float f2 = (float) iArr3[1];
            if (this.parentY + ((float) this.lay_scroll.getHeight()) < f) {
                f = this.parentY + ((float) this.lay_scroll.getHeight());
            }
            if (f > f2) {
                this.distanceScroll = (int) (f - f2);
                int i = this.distanceScroll;
                this.dsfc = i;
                if (((float) i) < this.distance) {
                    this.lay_scroll.setY((this.parentY - ((float) ImageUtils.dpToPx(this, 50))) - ((float) this.distanceScroll));
                } else {
                    this.lay_scroll.getHeight();
                    int scrollY = this.lay_scroll.getScrollY();
                    this.lay_scroll.setLayoutParams(new LayoutParams(-1, -2));
                    this.lay_scroll.postInvalidate();
                    this.lay_scroll.requestLayout();
                    int i2 = (int) ((f - this.distance) - f2);
                    int height2 = this.lay_scroll.getHeight() - i2;
                    this.distanceScroll = scrollY + i2;
                    this.lay_scroll.getLayoutParams().height = height2;
                    this.lay_scroll.postInvalidate();
                    this.lay_scroll.requestLayout();
                }
                this.lay_scroll.post(new Runnable() {
                    public void run() {
                        PosterActivity.this.btn_bck1.performClick();
                    }
                });
            }
        }
    }

    private void removeScroll() {
        int[] iArr = new int[2];
        this.lay_scroll.getLocationOnScreen(iArr);
        if (((float) iArr[1]) != ((float) ImageUtils.dpToPx(this, 50))) {
            this.lay_scroll.setY(this.yAtLayoutCenter - ((float) ImageUtils.dpToPx(this, 50)));
        }
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.addRule(13);
        this.lay_scroll.setLayoutParams(layoutParams);
        this.lay_scroll.postInvalidate();
        this.lay_scroll.requestLayout();
    }

    @RequiresApi(api = 17)
    private void addSticker(String str, String str2, Bitmap bitmap2) {
        if (this.lay_StkrMain.getVisibility() == View.GONE) {
            this.lay_StkrMain.setVisibility(View.VISIBLE);
            this.lay_StkrMain.startAnimation(this.animSlideUp);
        }
        this.hueSeekbar.setProgress(1);
        removeImageViewControll();
        ComponentInfo componentInfo = new ComponentInfo();
        componentInfo.setPOS_X((float) ((this.main_rel.getWidth() / 2) - ImageUtils.dpToPx(this, 70)));
        componentInfo.setPOS_Y((float) ((this.main_rel.getHeight() / 2) - ImageUtils.dpToPx(this, 70)));
        componentInfo.setWIDTH(ImageUtils.dpToPx(this, 140));
        componentInfo.setHEIGHT(ImageUtils.dpToPx(this, 140));
        componentInfo.setROTATION(0.0f);
        componentInfo.setRES_ID(str);
        componentInfo.setBITMAP(bitmap2);
        componentInfo.setCOLORTYPE("white");
        componentInfo.setTYPE("STICKER");
        componentInfo.setSTC_OPACITY(100);
        componentInfo.setSTC_COLOR(0);
        componentInfo.setSTKR_PATH(str2);
        componentInfo.setSTC_HUE(this.hueSeekbar.getProgress());
        componentInfo.setFIELD_TWO("0,0");
        ResizableStickerView resizableStickerView = new ResizableStickerView(this);
        resizableStickerView.optimizeScreen(this.screenWidth, this.screenHeight);
        resizableStickerView.setMainLayoutWH((float) this.main_rel.getWidth(), (float) this.main_rel.getHeight());
        resizableStickerView.setComponentInfo(componentInfo);
        resizableStickerView.setId(View.generateViewId());
        txt_stkr_rel.addView(resizableStickerView);
        resizableStickerView.setOnTouchCallbackListener(this);
        resizableStickerView.setBorderVisibility(true);
    }

    public void removeImageViewControll() {
        this.guideline.setVisibility(View.GONE);
        this.lay_effects.setVisibility(View.GONE);
        int childCount = txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txt_stkr_rel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                ((AutofitTextRel) childAt).setBorderVisibility(false);
            }
            if (childAt instanceof ResizableStickerView) {
                ((ResizableStickerView) childAt).setBorderVisibility(false);
            }
        }
    }

    private BitmapDataObject getBitmapDataObject(String str) {
        try {
            return (BitmapDataObject) new ObjectInputStream(new FileInputStream(new File(str))).readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @RequiresApi(api = 17)
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == RESULT_OK && i == 1000) {
            Log.e("qqqq", "Back from Share");
            setResult(RESULT_OK);
            finish();
        } else {
            if (i2 == -1) {
                this.lay_StkrMain.setVisibility(View.GONE);
                if (intent != null || i == SELECT_PICTURE_FROM_CAMERA || i == 4 || i == TEXT_ACTIVITY) {
                    if (i == TEXT_ACTIVITY) {
                        Bundle extras = intent.getExtras();
                        TextInfo textInfo = new TextInfo();
                        textInfo.setPOS_X(extras.getFloat("X", 0.0f));
                        textInfo.setPOS_Y(extras.getFloat("Y", 0.0f));
                        textInfo.setWIDTH(extras.getInt("wi", ImageUtils.dpToPx(this, Callback.DEFAULT_DRAG_ANIMATION_DURATION)));
                        textInfo.setHEIGHT(extras.getInt("he", ImageUtils.dpToPx(this, Callback.DEFAULT_DRAG_ANIMATION_DURATION)));
                        textInfo.setTEXT(extras.getString("text", ""));
                        textInfo.setFONT_NAME(extras.getString("fontName", ""));
                        textInfo.setTEXT_COLOR(extras.getInt("tColor", Color.parseColor("#4149b6")));
                        textInfo.setTEXT_ALPHA(extras.getInt("tAlpha", 100));
                        textInfo.setSHADOW_COLOR(extras.getInt("shadowColor", Color.parseColor("#7641b6")));
                        textInfo.setSHADOW_PROG(extras.getInt("shadowProg", 5));
                        textInfo.setBG_COLOR(extras.getInt("bgColor", 0));
                        textInfo.setBG_DRAWABLE(extras.getString("bgDrawable", "0"));
                        textInfo.setBG_ALPHA(extras.getInt("bgAlpha", 255));
                        textInfo.setROTATION(extras.getFloat("rotation", 0.0f));
                        textInfo.setFIELD_TWO(extras.getString("field_two", ""));
                        StringBuilder sb = new StringBuilder();
                        sb.append("");
                        sb.append(extras.getFloat("X", 0.0f));
                        sb.append(" ,");
                        sb.append(extras.getFloat("Y", 0.0f));
                        Log.e("double tab 22", sb.toString());
                        this.fontName = extras.getString("fontName", "");
                        this.tColor = extras.getInt("tColor", Color.parseColor("#4149b6"));
                        this.shadowColor = extras.getInt("shadowColor", Color.parseColor("#7641b6"));
                        this.shadowProg = extras.getInt("shadowProg", 0);
                        this.tAlpha = extras.getInt("tAlpha", 100);
                        this.bgDrawable = extras.getString("bgDrawable", "0");
                        this.bgAlpha = extras.getInt("bgAlpha", 255);
                        this.rotation = extras.getFloat("rotation", 0.0f);
                        this.bgColor = extras.getInt("bgColor", 0);
                        if (this.editMode) {
                            RelativeLayout relativeLayout = txt_stkr_rel;
                            ((AutofitTextRel) relativeLayout.getChildAt(relativeLayout.getChildCount() - 1)).setTextInfo(textInfo, false);
                            RelativeLayout relativeLayout2 = txt_stkr_rel;
                            ((AutofitTextRel) relativeLayout2.getChildAt(relativeLayout2.getChildCount() - 1)).setBorderVisibility(true);
                            this.editMode = false;
                        } else {
                            this.verticalSeekBar.setProgress(100);
                            this.seekBar_shadow.setProgress(0);
                            this.seekBar3.setProgress(255);
                            AutofitTextRel autofitTextRel = new AutofitTextRel(this);
                            txt_stkr_rel.addView(autofitTextRel);
                            autofitTextRel.setTextInfo(textInfo, false);
                            autofitTextRel.setId(View.generateViewId());
                            autofitTextRel.setOnTouchCallbackListener(this);
                            autofitTextRel.setBorderVisibility(true);
                        }
                        if (this.lay_TextMain.getVisibility() == View.GONE) {
                            this.select_backgnd.setBackgroundColor(getResources().getColor(R.color.colormediam));
                            this.select_effect.setBackgroundColor(getResources().getColor(R.color.colormediam));
                            this.add_sticker.setBackgroundColor(getResources().getColor(R.color.colormediam));
                            this.user_image.setBackgroundColor(getResources().getColor(R.color.colormediam));
                            this.add_text.setBackgroundResource(R.drawable.trans);
                            this.lay_TextMain.setVisibility(View.VISIBLE);
                            this.lay_TextMain.startAnimation(this.animSlideUp);
                        }
                    }
                    if (i == SELECT_PICTURE_FROM_GALLERY) {
                        try {
                            btmSticker = ImageUtils.resizeBitmap(Constants.getBitmapFromUri(this, intent.getData(), this.screenWidth, this.screenHeight), (int) this.screenWidth, (int) this.screenWidth);
                            Intent intent2 = new Intent(this, CropActivityTwo.class);
                            intent2.putExtra("value", "sticker");
                            startActivity(intent2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (i == SELECT_PICTURE_FROM_CAMERA) {
                        try {
                            btmSticker = ImageUtils.resizeBitmap(Constants.getBitmapFromUri(this, Uri.fromFile(this.f26f), this.screenWidth, this.screenHeight), (int) this.screenWidth, (int) this.screenWidth);
                            Intent intent3 = new Intent(this, CropActivityTwo.class);
                            intent3.putExtra("value", "sticker");
                            startActivity(intent3);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (i == 4) {
                        Bundle extras2 = intent.getExtras();
                        this.profile = extras2.getString("profile");
                        if (this.profile.equals("no")) {
                            this.showtailsSeek = false;
                            if (this.lay_SeekbarMain.getVisibility() == View.GONE) {
                                this.lay_SeekbarMain.setVisibility(View.VISIBLE);
                                this.lay_SeekbarMain.startAnimation(this.animSlideUp);
                            }
                            this.ratio = "";
                            this.position = "1";
                            this.profile = "Temp_Path";
                            this.hex = "";
                            setImageBitmapAndResizeLayout(ImageUtils.resizeBitmap(CropActivityTwo.bitmapImage, (int) this.screenWidth, (int) this.screenHeight), "nonCreated");
                            return;
                        }
                        if (this.profile.equals("Texture")) {
                            this.showtailsSeek = true;
                            this.lay_handletails.setVisibility(View.VISIBLE);
                        } else {
                            this.showtailsSeek = false;
                            this.lay_handletails.setVisibility(View.GONE);
                        }
                        if (this.lay_SeekbarMain.getVisibility() == View.GONE) {
                            this.lay_SeekbarMain.setVisibility(View.VISIBLE);
                            this.lay_SeekbarMain.startAnimation(this.animSlideUp);
                        }
                        this.ratio = extras2.getString("ratio");
                        String string = extras2.getString("position");
                        this.hex = extras2.getString("color");
                        drawBackgroundImage(this.ratio, string, this.profile, "nonCreated");
                        return;
                    }
                    return;
                }
                AlertDialog create = new AlertDialog.Builder(this, 16974126).setMessage(Constants.getSpannableString(this, Typeface.DEFAULT, R.string.picUpImg)).setPositiveButton(Constants.getSpannableString(this, Typeface.DEFAULT, R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create();
                create.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_;
                create.show();
            } else if (i == TEXT_ACTIVITY) {
                this.editMode = false;
            }
        }
    }

    private void showDialogPicker() {
        final PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog.setTitle(getResources().getString(R.string.select_picture));
        prettyDialog.setMessage(getResources().getString(R.string.image_selection));
        prettyDialog.setIcon(Integer.valueOf(R.drawable.ic_close));
        prettyDialog.setIconCallback(new PrettyDialogCallback() {
            public void onClick() {
                prettyDialog.dismiss();
            }
        });
//        prettyDialog.addButton("Gallery", Integer.valueOf(R.color.pdlg_color_white), Integer.valueOf(R.color.pdlg_color_green), R.drawable.gallery2, new PrettyDialogCallback() {
////            public void onClick() {
////                prettyDialog.dismiss();
////                PosterActivity.this.onGalleryButtonClick();
////            }
////        });
        prettyDialog.addButton("Gallery", Integer.valueOf(R.color.pdlg_color_white), Integer.valueOf(R.color.green), new PrettyDialogCallback() {
            public void onClick() {
                prettyDialog.dismiss();
                PosterActivity.this.onGalleryButtonClick();
            }
        });
        prettyDialog.show();
    }

    public void onCameraButtonClick() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.f26f = new File(Environment.getExternalStorageDirectory(), ".temp.jpg");
        StringBuilder sb = new StringBuilder();
        sb.append(getPackageName());
        sb.append(".provider");
        intent.putExtra("output", FileProvider.getUriForFile(this, sb.toString(), this.f26f));
        intent.addFlags(1);
        startActivityForResult(intent, SELECT_PICTURE_FROM_CAMERA);
    }

    public void onGalleryButtonClick() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.PICK");
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), SELECT_PICTURE_FROM_GALLERY);
    }

    public void onBackPressed() {
        if (this.lay_TextMain.getVisibility() == View.VISIBLE) {
            if (this.lay_textEdit.getVisibility() == View.VISIBLE) {
                hideTextResContainer();
                removeScroll();
                return;
            }
            showBackDialog();
        } else if (this.lay_sticker.getVisibility() == View.VISIBLE) {
            this.lay_sticker.setVisibility(View.GONE);
            this.add_sticker.setBackgroundResource(R.drawable.trans);
            this.img_oK.setVisibility(View.VISIBLE);
            btn_layControls.setVisibility(View.VISIBLE);
        } else if (this.lay_StkrMain.getVisibility() == View.VISIBLE) {
            if (this.seekbar_container.getVisibility() == View.VISIBLE) {
                hideResContainer();
                removeScroll();
                return;
            }
            showBackDialog();
        } else if (this.lay_SeekbarMain.getVisibility() == View.VISIBLE) {
            this.lay_SeekbarMain.startAnimation(this.animSlideDown);
            this.lay_SeekbarMain.setVisibility(View.GONE);
        } else if (this.lay_effects.getVisibility() == View.VISIBLE) {
            this.lay_effects.startAnimation(this.animSlideDown);
            this.lay_effects.setVisibility(View.GONE);
        } else if (lay_container.getVisibility() == View.VISIBLE) {
            lay_container.animate().translationX((float) (-lay_container.getRight())).setDuration(200).setInterpolator(new AccelerateInterpolator()).start();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    PosterActivity.lay_container.setVisibility(View.GONE);
                    PosterActivity.btn_layControls.setVisibility(View.VISIBLE);
                }
            }, 200);
        } else {
            showBackDialog();
        }
    }

    private void showBackDialog() {
        final PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog.setTitle(getString(R.string.alert));
        prettyDialog.setMessage(getString(R.string.leavepage_alert));
        prettyDialog.setIcon(Integer.valueOf(R.drawable.ic_close));
        prettyDialog.setIconCallback(new PrettyDialogCallback() {
            public void onClick() {
                prettyDialog.dismiss();
            }
        });
        prettyDialog.addButton("Yes", Integer.valueOf(R.color.white), Integer.valueOf(R.color.green), new PrettyDialogCallback() {
            public void onClick() {
                PosterActivity.this.finish();
                prettyDialog.dismiss();
            }
        });
        prettyDialog.addButton("No", Integer.valueOf(R.color.white), Integer.valueOf(R.color.red), new PrettyDialogCallback() {
            public void onClick() {
                prettyDialog.dismiss();
            }
        });
        prettyDialog.show();
    }

    @RequiresApi(api = 17)
    public void ongetSticker() {
        this.color_Type = "colored";
        addSticker("", "", CropActivityTwo.bitmapImage);
    }

    public void onColor(int i, String str, int i2) {
        if (i != 0) {
            int childCount = txt_stkr_rel.getChildCount();
            int i3 = 0;
            if (str.equals("txtShadow")) {
                while (i3 < childCount) {
                    View childAt = txt_stkr_rel.getChildAt(i3);
                    if (childAt instanceof AutofitTextRel) {
                        ((AutofitTextRel) txt_stkr_rel.getChildAt(i2)).setBorderVisibility(true);
                        AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                        if (autofitTextRel.getBorderVisibility()) {
                            this.shadowColor = i;
                            autofitTextRel.setTextShadowColor(i);
                        }
                    }
                    i3++;
                }
            } else if (str.equals("txtBg")) {
                while (i3 < childCount) {
                    View childAt2 = txt_stkr_rel.getChildAt(i3);
                    if (childAt2 instanceof AutofitTextRel) {
                        ((AutofitTextRel) txt_stkr_rel.getChildAt(i2)).setBorderVisibility(true);
                        AutofitTextRel autofitTextRel2 = (AutofitTextRel) childAt2;
                        if (autofitTextRel2.getBorderVisibility()) {
                            this.bgColor = i;
                            this.bgDrawable = "0";
                            autofitTextRel2.setBgColor(i);
                            autofitTextRel2.setBgAlpha(this.seekBar3.getProgress());
                        }
                    }
                    i3++;
                }
            } else {
                View childAt3 = txt_stkr_rel.getChildAt(i2);
                if (childAt3 instanceof AutofitTextRel) {
                    ((AutofitTextRel) txt_stkr_rel.getChildAt(i2)).setBorderVisibility(true);
                    AutofitTextRel autofitTextRel3 = (AutofitTextRel) childAt3;
                    if (autofitTextRel3.getBorderVisibility()) {
                        this.tColor = i;
                        this.textColorSet = i;
                        autofitTextRel3.setTextColor(i);
                    }
                }
                if (childAt3 instanceof ResizableStickerView) {
                    ((ResizableStickerView) txt_stkr_rel.getChildAt(i2)).setBorderVisibility(true);
                    ResizableStickerView resizableStickerView = (ResizableStickerView) childAt3;
                    if (resizableStickerView.getBorderVisbilty()) {
                        this.stkrColorSet = i;
                        resizableStickerView.setColor(i);
                    }
                }
            }
        } else {
            removeScroll();
            if (this.lay_TextMain.getVisibility() == View.VISIBLE) {
                this.lay_TextMain.startAnimation(this.animSlideDown);
                this.lay_TextMain.setVisibility(View.GONE);
            }
            if (this.lay_StkrMain.getVisibility() == View.VISIBLE) {
                this.lay_StkrMain.startAnimation(this.animSlideDown);
                this.lay_StkrMain.setVisibility(View.GONE);
            }
        }
    }

    /* access modifiers changed from: private */
    public void saveFinalImage() {
        this.bitmap = viewToBitmap(this.main_rel);
        this.logo_ll.setVisibility(View.VISIBLE);
        this.logo_ll.setDrawingCacheEnabled(true);
        Bitmap createBitmap = Bitmap.createBitmap(this.logo_ll.getDrawingCache());
        this.logo_ll.setDrawingCacheEnabled(false);
        this.logo_ll.setVisibility(View.INVISIBLE);
        Bitmap bitmap2 = this.bitmap;
        withoutWatermark = bitmap2;
        this.bitmap = ImageUtils.mergelogo(bitmap2, createBitmap);
        saveBitmap(true);
    }

    private void showDialogSave() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        final PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog.setTitle("Save As");
        prettyDialog.setMessage("Is final Picture for sharing and posting.");
        prettyDialog.setIcon(Integer.valueOf(R.drawable.ic_close));
        prettyDialog.setIconCallback(new PrettyDialogCallback() {
            public void onClick() {
                prettyDialog.dismiss();
            }
        });
        prettyDialog.addButton("Image", Integer.valueOf(R.color.pdlg_color_white), Integer.valueOf(R.color.green), new PrettyDialogCallback() {
            public void onClick() {
                prettyDialog.dismiss();
                final PrettyDialog prettyDialog = new PrettyDialog(PosterActivity.this);
                prettyDialog.setTitle("Confirmation!!!");
                prettyDialog.setMessage("Do you want to save image with watermark or without watermark ?");
                prettyDialog.setIcon(Integer.valueOf(R.drawable.ic_close));
                prettyDialog.setIconCallback(new PrettyDialogCallback() {
                    public void onClick() {
                        prettyDialog.dismiss();
                    }
                });
                prettyDialog.addButton("With Watermark", Integer.valueOf(R.color.pdlg_color_white), Integer.valueOf(R.color.green), new PrettyDialogCallback() {
                    public void onClick() {
                        prettyDialog.dismiss();
                        PosterActivity.this.saveFinalImage();
                    }
                });
                prettyDialog.addButton("Without Watermark", Integer.valueOf(R.color.pdlg_color_white), Integer.valueOf(R.color.green), new PrettyDialogCallback() {
                    public void onClick() {
                        prettyDialog.dismiss();
                        PosterActivity.this.bitmap = PosterActivity.this.viewToBitmap(PosterActivity.this.main_rel);
                        PosterActivity.withoutWatermark = PosterActivity.this.bitmap;
                        PosterActivity.this.saveBitmap(true);
                    }
                });
                prettyDialog.show();
            }
        });
        prettyDialog.show();
    }

    /* access modifiers changed from: private */
    public void errorDialogTempInfo() {
        final Dialog dialog = new Dialog(this, 16974126);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.error_dialog);
        ((TextView) dialog.findViewById(R.id.txtapp)).setTypeface(this.ttfHeader);
        ((TextView) dialog.findViewById(R.id.txt)).setTypeface(this.ttf);
        Button button = (Button) dialog.findViewById(R.id.btn_ok);
        button.setTypeface(this.ttf);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PosterActivity.this.finish();
            }
        });
        Button button2 = (Button) dialog.findViewById(R.id.btn_conti);
        button2.setTypeface(this.ttf);
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /* access modifiers changed from: private */
    public Bitmap gaussinBlur(Activity activity2, Bitmap bitmap2) {
        GPUImage gPUImage = new GPUImage(activity2);
        GPUImageGaussianBlurFilter gPUImageGaussianBlurFilter = new GPUImageGaussianBlurFilter();
        gPUImage.setFilter(gPUImageGaussianBlurFilter);
        new FilterAdjuster(gPUImageGaussianBlurFilter).adjust(100);
        gPUImage.requestRender();
        return gPUImage.getBitmapWithFilterApplied(bitmap2);
    }

    public int getRemoveBoderPosition() {
        int childCount = txt_stkr_rel.getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = txt_stkr_rel.getChildAt(i2);
            if ((childAt instanceof AutofitTextRel) && ((AutofitTextRel) childAt).getBorderVisibility()) {
                i = i2;
            }
            if ((childAt instanceof ResizableStickerView) && ((ResizableStickerView) childAt).getBorderVisbilty()) {
                i = i2;
            }
        }
        return i;
    }

    public void freeMemory() {
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.bitmap = null;
        }
        Bitmap bitmap3 = imgBtmap;
        if (bitmap3 != null) {
            bitmap3.recycle();
            imgBtmap = null;
        }
        Bitmap bitmap4 = withoutWatermark;
        if (bitmap4 != null) {
            bitmap4.recycle();
            withoutWatermark = null;
        }
        Bitmap bitmap5 = btmSticker;
        if (bitmap5 != null) {
            bitmap5.recycle();
            btmSticker = null;
        }
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

}
