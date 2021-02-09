package postermaster.postermaker.create;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import postermaster.postermaker.utility.GPUImageFilterTools.FilterAdjuster;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter;

public class BlurOperationTwoAsync extends AsyncTask<String, Void, String> {
    ImageView background_blur;
    Bitmap btmp;
    Activity context;

    /* access modifiers changed from: protected */
    public void onPreExecute() {
    }

    /* access modifiers changed from: protected */
    public String doInBackground(String... strArr) {
        this.btmp = gaussinBlur(this.context, this.btmp);
        return "yes";
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(String str) {
        this.background_blur.setImageBitmap(this.btmp);
    }

    private Bitmap gaussinBlur(Activity activity, Bitmap bitmap) {
        GPUImage gPUImage = new GPUImage(activity);
        GPUImageGaussianBlurFilter gPUImageGaussianBlurFilter = new GPUImageGaussianBlurFilter();
        gPUImage.setFilter(gPUImageGaussianBlurFilter);
        new FilterAdjuster(gPUImageGaussianBlurFilter).adjust(100);
        gPUImage.requestRender();
        return gPUImage.getBitmapWithFilterApplied(bitmap);
    }
}
