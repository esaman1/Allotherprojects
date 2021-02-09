package msl.demo;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpRequestTask extends AsyncTask<String, String, Void> {
    private RequestListener mRequestListener;
    String response = null;

    public interface RequestListener {
        void onError();

        void onResponse(String str);
    }

    public HttpRequestTask(RequestListener requestListener) {
        this.mRequestListener = requestListener;
    }

    /* access modifiers changed from: protected */
    public Void doInBackground(String... strArr) {
        try {
            String str = strArr[0];
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(str);
            httpGet.addHeader("application-id", "");
            httpGet.addHeader("secret-key", "");
            httpGet.addHeader("application-type", "REST");
            HttpResponse execute = defaultHttpClient.execute(httpGet);
            if (execute.getStatusLine().getStatusCode() == 200) {
                this.response = EntityUtils.toString(execute.getEntity());
                Log.i("Interstitial response", this.response);
            } else {
                Log.i("Interstitial response", "Failed to get response");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Void voidR) {
        super.onPostExecute(voidR);
        String str = this.response;
        if (str == null) {
            this.mRequestListener.onError();
        } else {
            this.mRequestListener.onResponse(str);
        }
    }
}
