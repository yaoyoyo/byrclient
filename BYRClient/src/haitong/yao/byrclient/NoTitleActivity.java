package haitong.yao.byrclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public abstract class NoTitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void findViewsById();

    protected abstract void setListeners();

}
