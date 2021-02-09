package msl.textmodule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import postermaster.postermaker.R;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

public class TextActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {
    /* access modifiers changed from: private */
    public AutoFitEditText autoFitEditText;
    private int bgAlpha = 255;
    /* access modifiers changed from: private */
    public int bgColor = 0;
    /* access modifiers changed from: private */
    public String bgDrawable = "0";
    private RelativeLayout bg_rel;
    private ImageView btn_back;
    private ImageView btn_ok;
    private Bundle bundle;
    View clickedView;
    private RelativeLayout color_rel;
    boolean firsttime = true;
    private String fontName = "";
    private RelativeLayout font_grid_rel;
    private GridView font_gridview;
    String hex = "";
    String hex1 = "";
    /* access modifiers changed from: private */
    public TextView hint_txt;
    private ImageView ic_kb;
    int[] imageId = {R.drawable.btxt0, R.drawable.btxt1, R.drawable.btxt2, R.drawable.btxt3, R.drawable.btxt4, R.drawable.btxt5, R.drawable.btxt6, R.drawable.btxt7, R.drawable.btxt8, R.drawable.btxt9, R.drawable.btxt10, R.drawable.btxt11, R.drawable.btxt12, R.drawable.btxt13, R.drawable.btxt14, R.drawable.btxt15, R.drawable.btxt16, R.drawable.btxt17};
    private InputMethodManager imm;
    private boolean isKbOpened = true;
    /* access modifiers changed from: private */
    public ImageView lay_back_img;
    /* access modifiers changed from: private */
    public RelativeLayout lay_below;
    private RelativeLayout lay_txtbg;
    private RelativeLayout lay_txtcolor;
    private RelativeLayout lay_txtfont;
    private RelativeLayout lay_txtshadow;
    /* access modifiers changed from: private */
    public RelativeLayout laykeyboard;
    String[] pallete = {"#4182b6", "#4149b6", "#7641b6", "#b741a7", "#c54657", "#d1694a", "#24352a", "#b8c847", "#67bb43", "#41b691", "#293b2f", "#1c0101", "#420a09", "#b4794b", "#4b86b4", "#93b6d2", "#72aa52", "#67aa59", "#fa7916", "#16a1fa", "#165efa", "#1697fa"};
    int processValue = 100;
    private SeekBar seekBar;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    /* access modifiers changed from: private */
    public int shadowColor = Color.parseColor("#7641b6");
    private int shadowProg = 5;
    private RelativeLayout shadow_rel;
    private int tAlpha = 100;
    /* access modifiers changed from: private */
    public int tColor = Color.parseColor("#4149b6");
    private String text = "";
    private Typeface ttf;
    int value = 0;
    int value2 = 0;
    private float xpos;
    private float ypos;

    class C04131 implements Runnable {
        C04131() {
        }

        public void run() {
            TextActivity.this.initUIData();
            TextActivity.this.laykeyboard.performClick();
        }
    }

    class C04142 implements OnGlobalLayoutListener {
        C04142() {
        }

        public void onGlobalLayout() {
            TextActivity textActivity = TextActivity.this;
            if (textActivity.isKeyboardShown(textActivity.autoFitEditText.getRootView())) {
                TextActivity.this.lay_below.setVisibility(View.INVISIBLE);
                TextActivity.this.setSelected(R.id.laykeyboard);
                TextActivity.this.firsttime = false;
            } else if (!TextActivity.this.firsttime) {
                TextActivity textActivity2 = TextActivity.this;
                textActivity2.setSelected(textActivity2.clickedView.getId());
                TextActivity.this.clickedView.performClick();
            }
        }
    }

    class C04153 implements TextWatcher {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        C04153() {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence.length() == 0) {
                TextActivity.this.hint_txt.setVisibility(View.VISIBLE);
            } else {
                TextActivity.this.hint_txt.setVisibility(View.GONE);
            }
        }
    }

    class C04164 implements OnItemClickListener {
        C04164() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            TextActivity.this.lay_back_img.setVisibility(View.GONE);
            StringBuilder sb = new StringBuilder();
            sb.append("btxt");
            sb.append(String.valueOf(i));
            String sb2 = sb.toString();
            int identifier = TextActivity.this.getResources().getIdentifier(sb2, "drawable", TextActivity.this.getPackageName());
            TextActivity.this.bgDrawable = sb2;
            TextActivity.this.bgColor = 0;
            ImageView access$600 = TextActivity.this.lay_back_img;
            TextActivity textActivity = TextActivity.this;
            access$600.setImageBitmap(textActivity.getTiledBitmap(textActivity, identifier, textActivity.autoFitEditText.getWidth(), TextActivity.this.autoFitEditText.getHeight()));
        }
    }

    class C04175 implements OnClickListener {

        class C06581 implements OnAmbilWarnaListener {
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
            }

            C06581() {
            }

            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                TextActivity.this.bgDrawable = "0";
                TextActivity.this.bgColor = i;
                TextActivity.this.lay_back_img.setImageBitmap(null);
                TextActivity.this.lay_back_img.setBackgroundColor(TextActivity.this.bgColor);
                TextActivity.this.lay_back_img.setVisibility(View.GONE);
            }
        }

        C04175() {
        }

        public void onClick(View view) {
            TextActivity textActivity = TextActivity.this;
            new AmbilWarnaDialog(textActivity, textActivity.bgColor, new C06581()).show();
        }
    }

    class C04186 implements OnClickListener {

        class C06591 implements OnAmbilWarnaListener {
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
            }

            C06591() {
            }

            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                TextActivity.this.updateColor(i, 2);
            }
        }

        C04186() {
        }

        public void onClick(View view) {
            TextActivity textActivity = TextActivity.this;
            new AmbilWarnaDialog(textActivity, textActivity.shadowColor, new C06591()).show();
        }
    }

    class C04197 implements OnClickListener {

        class C06601 implements OnAmbilWarnaListener {
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
            }

            C06601() {
            }

            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                TextActivity.this.updateColor(i, 1);
            }
        }

        C04197() {
        }

        public void onClick(View view) {
            TextActivity textActivity = TextActivity.this;
            new AmbilWarnaDialog(textActivity, textActivity.tColor, new C06601()).show();
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar4) {
    }

    public void onStopTrackingTouch(SeekBar seekBar4) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle2) {
        super.onCreate(bundle2);
        setContentView(R.layout.activity_text);
        this.ttf = Typeface.createFromAsset(getAssets(), "font6.ttf");
        this.imm = (InputMethodManager) getSystemService("input_method");
        initUI();
        initViewPager();
        this.lay_back_img.post(new C04131());
        ((TextView) findViewById(R.id.headertext)).setTypeface(this.ttf);
        this.autoFitEditText.getViewTreeObserver().addOnGlobalLayoutListener(new C04142());
    }

    /* access modifiers changed from: private */
    public boolean isKeyboardShown(View view) {
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        return ((float) (view.getBottom() - rect.bottom)) > view.getResources().getDisplayMetrics().density * 128.0f;
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initUI() {
        this.font_gridview = (GridView) findViewById(R.id.font_gridview);
        this.autoFitEditText = (AutoFitEditText) findViewById(R.id.auto_fit_edit_text);
        this.lay_back_img = (ImageView) findViewById(R.id.lay_back_txt);
        this.btn_back = (ImageView) findViewById(R.id.btn_back);
        this.btn_ok = (ImageView) findViewById(R.id.btn_ok);
        this.hint_txt = (TextView) findViewById(R.id.hint_txt);
        this.lay_below = (RelativeLayout) findViewById(R.id.lay_below);
        this.laykeyboard = (RelativeLayout) findViewById(R.id.laykeyboard);
        this.lay_txtfont = (RelativeLayout) findViewById(R.id.lay_txtfont);
        this.lay_txtcolor = (RelativeLayout) findViewById(R.id.lay_txtcolor);
        this.lay_txtshadow = (RelativeLayout) findViewById(R.id.lay_txtshadow);
        this.lay_txtbg = (RelativeLayout) findViewById(R.id.lay_txtbg);
        this.font_grid_rel = (RelativeLayout) findViewById(R.id.font_grid_rel);
        this.color_rel = (RelativeLayout) findViewById(R.id.color_rel);
        this.shadow_rel = (RelativeLayout) findViewById(R.id.shadow_rel);
        this.bg_rel = (RelativeLayout) findViewById(R.id.bg_rel);
        this.ic_kb = (ImageView) findViewById(R.id.ic_kb);
        this.clickedView = this.lay_txtfont;
        this.ic_kb.setOnClickListener(this);
        this.seekBar = (SeekBar) findViewById(R.id.seekBar1);
        this.seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        this.seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        this.seekBar.setProgress(this.processValue);
        this.autoFitEditText.addTextChangedListener(new C04153());
        findViewById(R.id.txt_bg_none).setOnClickListener(this);
        HorizontalListView horizontalListView = (HorizontalListView) findViewById(R.id.listview);
        horizontalListView.setAdapter((ListAdapter) new ImageViewAdapter(this, this.imageId));
        horizontalListView.setOnItemClickListener(new C04164());
        findViewById(R.id.color_picker3).setOnClickListener(new C04175());
        findViewById(R.id.color_picker2).setOnClickListener(new C04186());
        findViewById(R.id.color_picker1).setOnClickListener(new C04197());
        HorizontalListView horizontalListView2 = (HorizontalListView) findViewById(R.id.color_listview3);
        final ColorListAdapter colorListAdapter = new ColorListAdapter(this, this.pallete);
        horizontalListView2.setAdapter((ListAdapter) colorListAdapter);
        horizontalListView2.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                TextActivity.this.bgDrawable = "0";
                TextActivity.this.bgColor = ((Integer) colorListAdapter.getItem(i)).intValue();
                TextActivity.this.lay_back_img.setImageBitmap(null);
                TextActivity.this.lay_back_img.setBackgroundColor(TextActivity.this.bgColor);
                TextActivity.this.lay_back_img.setVisibility(View.GONE);
            }
        });
        HorizontalListView horizontalListView3 = (HorizontalListView) findViewById(R.id.color_listview2);
        final ColorListAdapter colorListAdapter2 = new ColorListAdapter(this, this.pallete);
        horizontalListView3.setAdapter((ListAdapter) colorListAdapter2);
        horizontalListView3.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                TextActivity.this.updateColor(((Integer) colorListAdapter2.getItem(i)).intValue(), 2);
            }
        });
        HorizontalListView horizontalListView4 = (HorizontalListView) findViewById(R.id.color_listview1);
        final ColorListAdapter colorListAdapter3 = new ColorListAdapter(this, this.pallete);
        horizontalListView4.setAdapter((ListAdapter) colorListAdapter3);
        horizontalListView4.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                TextActivity.this.updateColor(((Integer) colorListAdapter3.getItem(i)).intValue(), 1);
            }
        });
        this.btn_back.setOnClickListener(this);
        this.btn_ok.setOnClickListener(this);
        this.laykeyboard.setOnClickListener(this);
        this.lay_txtfont.setOnClickListener(this);
        this.lay_txtcolor.setOnClickListener(this);
        this.lay_txtshadow.setOnClickListener(this);
        this.lay_txtbg.setOnClickListener(this);
        this.seekBar.setOnSeekBarChangeListener(this);
        this.seekBar2.setOnSeekBarChangeListener(this);
        this.seekBar3.setOnSeekBarChangeListener(this);
        this.seekBar2.setProgress(5);
        ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.autoFitEditText, 0);
    }

    private void initViewPager() {
        new AssetsGrid(this, getResources().getStringArray(R.array.fonts_array));
        this.font_gridview = (GridView) findViewById(R.id.font_gridview);
    }

    /* access modifiers changed from: private */
    public void initUIData() {
        this.bundle = getIntent().getExtras();
        Bundle bundle2 = this.bundle;
        if (bundle2 != null) {
            this.xpos = bundle2.getFloat("X", 0.0f);
            this.ypos = this.bundle.getFloat("Y", 0.0f);
            this.text = this.bundle.getString("text", "");
            this.fontName = this.bundle.getString("fontName", "");
            this.tColor = this.bundle.getInt("tColor", Color.parseColor("#4149b6"));
            this.tAlpha = this.bundle.getInt("tAlpha", 100);
            this.shadowColor = this.bundle.getInt("shadowColor", Color.parseColor("#7641b6"));
            this.shadowProg = this.bundle.getInt("shadowProg", 5);
            this.bgDrawable = this.bundle.getString("bgDrawable", "0");
            this.bgColor = this.bundle.getInt("bgColor", 0);
            this.bgAlpha = this.bundle.getInt("bgAlpha", 255);
            this.autoFitEditText.setText(this.text);
            this.seekBar.setProgress(this.tAlpha);
            this.seekBar2.setProgress(this.shadowProg);
            updateColor(this.tColor, 1);
            updateColor(this.shadowColor, 2);
            if (!this.bgDrawable.equals("0")) {
                this.lay_back_img.setImageBitmap(getTiledBitmap(this, getResources().getIdentifier(this.bgDrawable, "drawable", getPackageName()), this.autoFitEditText.getWidth(), this.autoFitEditText.getHeight()));
                this.lay_back_img.setVisibility(View.VISIBLE);
                this.lay_back_img.postInvalidate();
                this.lay_back_img.requestLayout();
            }
            int i = this.bgColor;
            if (i != 0) {
                this.lay_back_img.setBackgroundColor(i);
                this.lay_back_img.setVisibility(View.VISIBLE);
            }
            this.seekBar3.setProgress(this.bgAlpha);
            try {
                this.autoFitEditText.setTypeface(Typeface.createFromAsset(getAssets(), this.fontName));
            } catch (Exception unused) {
            }
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            this.imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            finish();
        } else if (id == R.id.btn_ok) {
            if (this.autoFitEditText.getText().toString().trim().length() > 0) {
                this.imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtras(getInfoBundle());
                setResult(-1, intent);
                finish();
                return;
            }
            Toast.makeText(this, getResources().getString(R.string.textlib_warn_text), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.laykeyboard || id == R.id.ic_kb) {
            this.isKbOpened = true;
            this.firsttime = true;
            setSelected(view.getId());
            this.imm.showSoftInput(this.autoFitEditText, 0);
        } else if (id == R.id.lay_txtfont) {
            setSelected(view.getId());
            this.clickedView = view;
            this.font_grid_rel.setVisibility(View.GONE);
            this.color_rel.setVisibility(View.GONE);
            this.shadow_rel.setVisibility(View.GONE);
            this.bg_rel.setVisibility(View.GONE);
            this.lay_below.setVisibility(View.GONE);
            this.imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        } else if (id == R.id.lay_txtcolor) {
            setSelected(view.getId());
            this.clickedView = view;
            this.font_grid_rel.setVisibility(View.GONE);
            this.color_rel.setVisibility(View.GONE);
            this.shadow_rel.setVisibility(View.GONE);
            this.bg_rel.setVisibility(View.GONE);
            this.lay_below.setVisibility(View.GONE);
            this.imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        } else if (id == R.id.lay_txtshadow) {
            setSelected(view.getId());
            this.clickedView = view;
            this.font_grid_rel.setVisibility(View.GONE);
            this.color_rel.setVisibility(View.GONE);
            this.shadow_rel.setVisibility(View.GONE);
            this.bg_rel.setVisibility(View.GONE);
            this.lay_below.setVisibility(View.GONE);
            this.imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        } else if (id == R.id.lay_txtbg) {
            setSelected(view.getId());
            this.clickedView = view;
            this.font_grid_rel.setVisibility(View.GONE);
            this.color_rel.setVisibility(View.GONE);
            this.shadow_rel.setVisibility(View.GONE);
            this.bg_rel.setVisibility(View.GONE);
            this.lay_below.setVisibility(View.GONE);
            this.imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        } else if (id == R.id.txt_bg_none) {
            this.lay_back_img.setVisibility(View.GONE);
            this.bgDrawable = "0";
            this.bgColor = 0;
        }
    }

    public void onProgressChanged(SeekBar seekBar4, int i, boolean z) {
        this.processValue = i;
        this.value = i;
        int id = seekBar4.getId();
        if (id == R.id.seekBar1) {
            this.autoFitEditText.setAlpha(((float) seekBar4.getProgress()) / ((float) seekBar4.getMax()));
        } else if (id == R.id.seekBar2) {
            if (this.hex1.equals("")) {
                this.autoFitEditText.setShadowLayer((float) i, 0.0f, 0.0f, Color.parseColor("#fdab52"));
                return;
            }
            AutoFitEditText autoFitEditText2 = this.autoFitEditText;
            float f = (float) i;
            StringBuilder sb = new StringBuilder();
            sb.append("#");
            sb.append(this.hex1);
            autoFitEditText2.setShadowLayer(f, 0.0f, 0.0f, Color.parseColor(sb.toString()));
        } else if (id == R.id.seekBar3) {
            this.lay_back_img.setAlpha(((float) i) / 255.0f);
        }
    }

    private Bundle getInfoBundle() {
        if (this.bundle == null) {
            this.bundle = new Bundle();
        }
        this.text = this.autoFitEditText.getText().toString().trim().replace("\n", " ");
        this.bundle.putFloat("X", this.xpos);
        this.bundle.putFloat("Y", this.ypos);
        this.bundle.putString("text", this.text);
        this.bundle.putString("fontName", this.fontName);
        this.bundle.putInt("tColor", this.tColor);
        this.bundle.putInt("tAlpha", this.seekBar.getProgress());
        this.bundle.putInt("shadowColor", this.shadowColor);
        this.bundle.putInt("shadowProg", this.seekBar2.getProgress());
        this.bundle.putString("bgDrawable", this.bgDrawable);
        this.bundle.putInt("bgColor", this.bgColor);
        this.bundle.putInt("bgAlpha", this.seekBar3.getProgress());
        return this.bundle;
    }

    /* access modifiers changed from: private */
    public void updateColor(int i, int i2) {
        if (i2 == 1) {
            this.tColor = i;
            this.hex = Integer.toHexString(i);
            AutoFitEditText autoFitEditText2 = this.autoFitEditText;
            StringBuilder sb = new StringBuilder();
            sb.append("#");
            sb.append(this.hex);
            autoFitEditText2.setTextColor(Color.parseColor(sb.toString()));
        } else if (i2 == 2) {
            this.shadowColor = i;
            int progress = this.seekBar2.getProgress();
            this.hex1 = Integer.toHexString(i);
            AutoFitEditText autoFitEditText3 = this.autoFitEditText;
            float f = (float) progress;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("#");
            sb2.append(this.hex1);
            autoFitEditText3.setShadowLayer(f, 0.0f, 0.0f, Color.parseColor(sb2.toString()));
        }
    }

    public void setSelected(int i) {
        if (i == R.id.laykeyboard) {
            this.laykeyboard.getChildAt(0).setBackgroundResource(R.drawable.textlib_kb1);
            this.lay_txtfont.getChildAt(0).setBackgroundResource(R.drawable.textlib_text);
            this.lay_txtcolor.getChildAt(0).setBackgroundResource(R.drawable.textlib_tcolor);
            this.lay_txtshadow.getChildAt(0).setBackgroundResource(R.drawable.textlib_tshadow);
            this.lay_txtbg.getChildAt(0).setBackgroundResource(R.drawable.textlib_tbg);
        }
        if (i == R.id.lay_txtfont) {
            this.laykeyboard.getChildAt(0).setBackgroundResource(R.drawable.textlib_kb);
            this.lay_txtfont.getChildAt(0).setBackgroundResource(R.drawable.textlib_text1);
            this.lay_txtcolor.getChildAt(0).setBackgroundResource(R.drawable.textlib_tcolor);
            this.lay_txtshadow.getChildAt(0).setBackgroundResource(R.drawable.textlib_tshadow);
            this.lay_txtbg.getChildAt(0).setBackgroundResource(R.drawable.textlib_tbg);
        }
        if (i == R.id.lay_txtcolor) {
            this.laykeyboard.getChildAt(0).setBackgroundResource(R.drawable.textlib_kb);
            this.lay_txtfont.getChildAt(0).setBackgroundResource(R.drawable.textlib_text);
            this.lay_txtcolor.getChildAt(0).setBackgroundResource(R.drawable.textlib_tcolor1);
            this.lay_txtshadow.getChildAt(0).setBackgroundResource(R.drawable.textlib_tshadow);
            this.lay_txtbg.getChildAt(0).setBackgroundResource(R.drawable.textlib_tbg);
        }
        if (i == R.id.lay_txtshadow) {
            this.laykeyboard.getChildAt(0).setBackgroundResource(R.drawable.textlib_kb);
            this.lay_txtfont.getChildAt(0).setBackgroundResource(R.drawable.textlib_text);
            this.lay_txtcolor.getChildAt(0).setBackgroundResource(R.drawable.textlib_tcolor);
            this.lay_txtshadow.getChildAt(0).setBackgroundResource(R.drawable.textlib_tshadow1);
            this.lay_txtbg.getChildAt(0).setBackgroundResource(R.drawable.textlib_tbg);
        }
        if (i == R.id.lay_txtbg) {
            this.laykeyboard.getChildAt(0).setBackgroundResource(R.drawable.textlib_kb);
            this.lay_txtfont.getChildAt(0).setBackgroundResource(R.drawable.textlib_text);
            this.lay_txtcolor.getChildAt(0).setBackgroundResource(R.drawable.textlib_tcolor);
            this.lay_txtshadow.getChildAt(0).setBackgroundResource(R.drawable.textlib_tshadow);
            this.lay_txtbg.getChildAt(0).setBackgroundResource(R.drawable.textlib_tbg1);
        }
    }

    /* access modifiers changed from: private */
    public Bitmap getTiledBitmap(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, new Options()), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }
}
