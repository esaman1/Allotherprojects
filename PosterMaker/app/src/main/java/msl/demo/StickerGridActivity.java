package msl.demo;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.astuetz.PagerSlidingTabStrip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import postermaster.postermaker.R;
import msl.demo.NetworkQueryHelper.ResponseListener;

public class StickerGridActivity extends AppCompatActivity {
    private static final String MyPREFERENCES = "MyPrefs";
    protected static boolean isRunning = false;
    protected static Context mContext;
    /* access modifiers changed from: private */
    public StickerPagerAdapter adapter = null;
    /* access modifiers changed from: private */
    public int categoriesCount = 0;
    /* access modifiers changed from: private */
    public int curVer;
    /* access modifiers changed from: private */
    public int dbVer;
    /* access modifiers changed from: private */
    public TextView headerText;
    /* access modifiers changed from: private */
    public ViewPager pager;
    private ProgressDialog ringProgressDialog;
    SharedPreferences sharedpreferences;
    private PagerSlidingTabStrip tabHost;
    private Typeface ttf;

    class C03862 implements OnClickListener {
        C03862() {
        }

        public void onClick(View view) {
            StickerGridActivity.this.finish();
            StickerGridActivity.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        }
    }

    class C03875 implements Comparator<CategoryRowInfo> {
        C03875() {
        }

        public int compare(CategoryRowInfo categoryRowInfo, CategoryRowInfo categoryRowInfo2) {
            if (categoryRowInfo.getSEQUENCE() == categoryRowInfo2.getSEQUENCE()) {
                return 0;
            }
            return categoryRowInfo.getSEQUENCE() > categoryRowInfo2.getSEQUENCE() ? 1 : -1;
        }
    }

    class C06531 implements OnPageChangeListener {
        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        C06531() {
        }

        public void onPageSelected(int i) {
            if (StickerGridActivity.this.adapter != null) {
                StickerGridActivity.this.headerText.setText(StickerGridActivity.this.adapter.getPageTitle(i));
            }
        }
    }

    class C06543 implements ResponseListener {
        C06543() {
        }

        public void onResponse(JSONObject jSONObject) {
            if (jSONObject != null) {
                try {
                    StickerGridActivity.this.dbVer = jSONObject.getInt("dbVersion");
                    StickerGridActivity.this.categoriesCount = jSONObject.getInt("categoryCount");
                    if (StickerGridActivity.this.curVer != StickerGridActivity.this.dbVer) {
                        StickerGridActivity.this.fetchAndInsertCategory();
                        return;
                    }
                    ArrayList categoriesList = DatabaseHandler.getDbHandler(StickerGridActivity.this).getCategoriesList();
                    StickerGridActivity.this.intiCategoryList(categoriesList);
                    StringBuilder sb = new StringBuilder();
                    sb.append(categoriesList.size());
                    sb.append("  ");
                    sb.append(categoriesList.toString());
                    Log.i("testing", sb.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    StickerGridActivity.this.showNetworkErrorDialog();
                }
            } else {
                StickerGridActivity.this.showNetworkErrorDialog();
            }
        }
    }

    class C06554 implements ResponseListener {
        C06554() {
        }

        public void onResponse(JSONObject jSONObject) {
            if (jSONObject != null) {
                try {
                    JSONArray jSONArray = jSONObject.getJSONArray("data");
                    StringBuilder sb = new StringBuilder();
                    sb.append(jSONArray);
                    sb.append(" Server Categories Data ");
                    sb.append(jSONArray.toString());
                    Log.i("testing", sb.toString());
                    DatabaseHandler dbHandler = DatabaseHandler.getDbHandler(StickerGridActivity.this);
                    dbHandler.disableAllRow();
                    ArrayList categoriesList = dbHandler.getCategoriesList();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(categoriesList);
                    sb2.append(" Local Categories Data ");
                    sb2.append(categoriesList.toString());
                    Log.i("testing", sb2.toString());
                    Iterator it2 = StickerGridActivity.this.getNewCategoryList(jSONArray, categoriesList, dbHandler).iterator();
                    while (it2.hasNext()) {
                        dbHandler.insertCategoryMasterRow((CategoryRowInfo) it2.next());
                    }
                    Editor edit = StickerGridActivity.this.sharedpreferences.edit();
                    edit.putInt("DataVersion", StickerGridActivity.this.dbVer);
                    edit.commit();
                    ArrayList categoriesList2 = dbHandler.getCategoriesList();
                    StickerGridActivity.this.intiCategoryList(categoriesList2);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(categoriesList2.size());
                    sb3.append(" and ");
                    sb3.append(categoriesList2.toString());
                    Log.i("testing", sb3.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    StickerGridActivity.this.showNetworkErrorDialog();
                }
            } else {
                StickerGridActivity.this.showNetworkErrorDialog();
            }
        }
    }

    private class SelectionAsync extends AsyncTask<Object, Object, Integer> {
        ArrayList<CategoryRowInfo> categoriesList;
        String tabName = "";

        public SelectionAsync(ArrayList<CategoryRowInfo> arrayList) {
            this.categoriesList = arrayList;
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Object... objArr) {
            try {
                this.tabName = StickerGridActivity.this.getIntent().getStringExtra("tabName");
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(this.tabName);
                Log.i("testing", sb.toString());
                if (this.tabName.trim().length() > 0) {
                    for (int i = 0; i < this.categoriesList.size(); i++) {
                        if (((CategoryRowInfo) this.categoriesList.get(i)).getCATEGORY_NAME().equals(this.tabName)) {
                            return Integer.valueOf(i);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Integer.valueOf(-1);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer num) {
            super.onPostExecute(num);
            if (num.intValue() >= 0) {
                StickerGridActivity.this.headerText.setText(((CategoryRowInfo) this.categoriesList.get(0)).getCATEGORY_NAME());
                StickerGridActivity.this.pager.setCurrentItem(num.intValue());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_sticker_list);
        mContext = this;
        isRunning = true;
        this.sharedpreferences = getSharedPreferences(MyPREFERENCES, 0);
        initUI();
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getString(R.string.please_wait));
        sb.append("...");
        this.ringProgressDialog = ProgressDialog.show(this, "", sb.toString(), true);
        this.ringProgressDialog.setCancelable(false);
        if (isNetworkAvailable()) {
            checkDataVersionChanges();
        } else {
            ArrayList categoriesList = DatabaseHandler.getDbHandler(this).getCategoriesList();
            if (categoriesList.size() == 0) {
                showNetworkErrorDialog();
            } else {
                intiCategoryList(categoriesList);
            }
        }
        this.ttf = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        this.headerText.setTypeface(this.ttf);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    private void initUI() {
        this.headerText = (TextView) findViewById(R.id.headertext);
        this.tabHost = (PagerSlidingTabStrip) findViewById(R.id.tabHost);
        this.pager = (ViewPager) findViewById(R.id.pager);
        this.tabHost.setDividerColor(Color.parseColor("#620b80"));
        this.tabHost.setIndicatorColor(Color.parseColor("#620b80"));
        this.tabHost.setIndicatorHeight(dpToPx(this, 5));
        this.tabHost.setOnPageChangeListener(new C06531());
        findViewById(R.id.btn_back).setOnClickListener(new C03862());
    }

    private void checkDataVersionChanges() {
        this.curVer = this.sharedpreferences.getInt("DataVersion", 0);
        NetworkQueryHelper.getInstance().getServerDbVersion(new C06543());
    }

    /* access modifiers changed from: private */
    public void fetchAndInsertCategory() {
        NetworkQueryHelper.getInstance().getCategoriesData(this.categoriesCount, new C06554());
    }

    /* access modifiers changed from: private */
    public void intiCategoryList(ArrayList<CategoryRowInfo> arrayList) {
        Collections.sort(arrayList, new C03875());
        this.adapter = new StickerPagerAdapter(this, getSupportFragmentManager(), arrayList);
        this.pager.setAdapter(this.adapter);
        this.tabHost.setViewPager(this.pager);
        if (arrayList.size() > 0) {
            this.headerText.setText(((CategoryRowInfo) arrayList.get(0)).getCATEGORY_NAME());
            new SelectionAsync(arrayList).execute(new Object[0]);
        }
        if (this.ringProgressDialog.isShowing()) {
            this.ringProgressDialog.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public ArrayList<CategoryRowInfo> getNewCategoryList(JSONArray jSONArray, ArrayList<CategoryRowInfo> arrayList, DatabaseHandler databaseHandler) {
        ArrayList<CategoryRowInfo> arrayList2 = new ArrayList<>();
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((CategoryRowInfo) it2.next()).getCATEGORY_NAME());
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                String string = jSONObject.getString("Category_Name");
                if (arrayList3.contains(string)) {
                    databaseHandler.updateCategoryRow(string, jSONObject.getInt("Sequence"), jSONObject.getInt("Total_Items"));
                    arrayList3.remove(string);
                } else {
                    arrayList2.add(new CategoryRowInfo(string, jSONObject.getInt("Sequence"), jSONObject.getInt("Total_Items")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            databaseHandler.deleteCategoryMasterRow((String) it3.next());
        }
        return arrayList2;
    }

    /* access modifiers changed from: private */
    public void showNetworkErrorDialog() {
        if (this.ringProgressDialog.isShowing()) {
            this.ringProgressDialog.dismiss();
        }
        if (isRunning) {
            new Builder(mContext, VERSION.SDK_INT >= 14 ? 16974126 : 16973835).setTitle(mContext.getResources().getString(R.string.error)).setMessage(mContext.getResources().getString(R.string.something_wrong)).setCancelable(false).setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            }).create().show();
        }
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }

    private int dpToPx(Context context, int i) {
        float f = (float) i;
        context.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }
}
