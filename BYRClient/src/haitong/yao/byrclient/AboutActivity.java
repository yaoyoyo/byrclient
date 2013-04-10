package haitong.yao.byrclient;

import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

public class AboutActivity extends NoTitleActivity {

    private TextView mTo;

    private TextPaint mTextPaint;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.act_about);
        findViewsById();
    }

    @Override
    protected void findViewsById() {
        mTo = (TextView) findViewById(R.id.about_to);
        mTextPaint = mTo.getPaint();
        mTextPaint.setFakeBoldText(true);
    }

    @Override
    protected void setListeners() {

    }

}
