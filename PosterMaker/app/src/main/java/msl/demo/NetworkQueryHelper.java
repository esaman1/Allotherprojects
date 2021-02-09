package msl.demo;

import android.content.Context;
import android.util.Log;
import msl.demo.HttpRequestTask.RequestListener;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkQueryHelper {
    private static NetworkQueryHelper networkQueryHelper;
    /* access modifiers changed from: private */
    public int categoriesCount = 0;
    private Context context = null;
    /* access modifiers changed from: private */
    public int dbVersion = 0;
    /* access modifiers changed from: private */
    public boolean isVersionRecieved = false;

    public interface ResponseListener {
        void onResponse(JSONObject jSONObject);
    }

    public static NetworkQueryHelper getInstance() {
        if (networkQueryHelper == null) {
            networkQueryHelper = new NetworkQueryHelper();
        }
        return networkQueryHelper;
    }

    public void getServerDbVersion(final ResponseListener responseListener) {
        if (this.isVersionRecieved) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("dbVersion", this.dbVersion);
                jSONObject.put("categoryCount", this.categoriesCount);
                responseListener.onResponse(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            new HttpRequestTask(new RequestListener() {
                public void onResponse(String str) {
                    try {
                        JSONObject jSONObject = (JSONObject) new JSONObject(str).getJSONArray("data").get(0);
                        NetworkQueryHelper.this.dbVersion = jSONObject.getInt("Version");
                        NetworkQueryHelper.this.categoriesCount = jSONObject.getInt("Category_Count");
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("dbVersion", NetworkQueryHelper.this.dbVersion);
                        jSONObject2.put("categoryCount", NetworkQueryHelper.this.categoriesCount);
                        responseListener.onResponse(jSONObject2);
                        NetworkQueryHelper.this.isVersionRecieved = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        responseListener.onResponse(null);
                    }
                }

                public void onError() {
                    responseListener.onResponse(null);
                }
            }).execute(new String[]{getDataVersionURL()});
        }
    }

    public void getCategoriesData(int i, final ResponseListener responseListener) {
        new HttpRequestTask(new RequestListener() {
            public void onResponse(String str) {
                try {
                    responseListener.onResponse(new JSONObject(str));
                } catch (JSONException e) {
                    e.printStackTrace();
                    responseListener.onResponse(null);
                }
            }

            public void onError() {
                responseListener.onResponse(null);
            }
        }).execute(new String[]{getFetchCategoryURL(i)});
    }

    public void getStickersData(String str, int i, int i2, final ResponseListener responseListener) {
        new HttpRequestTask(new RequestListener() {
            public void onResponse(String str) {
                try {
                    responseListener.onResponse(new JSONObject(str));
                } catch (JSONException e) {
                    e.printStackTrace();
                    responseListener.onResponse(null);
                }
            }

            public void onError() {
                responseListener.onResponse(null);
            }
        }).execute(new String[]{getFetchStickerDataURL(str, i, i2)});
    }

    private String getFetchCategoryURL(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.backendless.com/v1/data/Category_Master?pageSize=");
        sb.append(String.valueOf(i));
        sb.append("&sortBy=Sequence%20asc&where=Package_Name%3D%27");
        sb.append(LibContants.getPackageId());
        sb.append("%27");
        return sb.toString();
    }

    private String getDataVersionURL() {
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.backendless.com/v1/data/Data_Version?where=Package_Name%3D%27");
        sb.append(LibContants.getPackageId());
        sb.append("%27");
        return sb.toString();
    }

    private String getFetchStickerDataURL(String str, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.backendless.com/v1/data/Stickers?pageSize=");
        sb.append(String.valueOf(i2));
        sb.append("&sortBy=Sequence%20asc&where=Main_Category%3D%27");
        sb.append(str);
        sb.append("%27%20and%20Sequence%3E");
        sb.append(String.valueOf(i));
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(" Server URL ");
        sb3.append(sb2);
        Log.i("testing", sb3.toString());
        return sb2;
    }
}
