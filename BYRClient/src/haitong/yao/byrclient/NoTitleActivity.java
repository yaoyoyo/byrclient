package haitong.yao.byrclient;

import haitong.yao.byrclient.cache.ImageCache;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public abstract class NoTitleActivity extends Activity {

    private List<String> mImageUrls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (mImageUrls.size() > 0) {
            ImageCache cache = ImageCache.getInstance();
            cache.removeLocalImages(mImageUrls);
        }
        super.onDestroy();
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void findViewsById();

    protected abstract void setListeners();

}
