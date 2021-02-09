package postermaster.postermaker;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class Glob {
    public static String GSM_link = "http://appbankstudio.in/appbank/service/storeGCM/app_next_studio";
    public static String _url = null;
    public static String acc_link = "https://play.google.com/store/apps/developer?id=App+Next+Studio";
    public static int appID = 585;
    public static String app_name = "Poster Maker : Poster Design With Photo";
    public static boolean dialog = true;
    public static Bitmap finalBitmap = null;
    public static Bitmap finalEditedBitmapImage3 = null;
    public static Uri testUri;

    public static Boolean CheckNet(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return Boolean.valueOf(activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    public static boolean getBoolPref(Context context, String str) {
        return context.getSharedPreferences(context.getPackageName(), 0).getBoolean(str, false);
    }

    public static int getPref(Context context, String str) {
        return context.getSharedPreferences(context.getPackageName(), 0).getInt(str, 1);
    }

    public static void setBoolPref(Context context, String str, boolean z) {
        Editor edit = context.getSharedPreferences(context.getPackageName(), 0).edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public static void setPref(Context context, String str, int i) {
        Editor edit = context.getSharedPreferences(context.getPackageName(), 0).edit();
        edit.putInt(str, i);
        edit.apply();
    }
}
