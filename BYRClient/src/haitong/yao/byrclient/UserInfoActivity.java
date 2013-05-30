package haitong.yao.byrclient;

import haitong.yao.byrclient.models.User;
import haitong.yao.byrclient.utils.BYRToast;
import haitong.yao.byrclient.utils.Utils;
import android.content.Context;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class UserInfoActivity extends NoTitleActivity {

    private User mUser;

    private TextView mName;
    private TextView mGender;
    private TextView mSign;
    private TextView mPostCount;
    private TextView mLoginCount;
    private TextView mLoginInfo;
    private ImageView mPortrait;

    private TextPaint mTextPaint;

    private Context mContext;

    private ImageLoader mImageLoader;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.act_userinfo);
        mContext = getApplicationContext();
        mImageLoader = ImageLoader.getInstance();
        findViewsById();
        initData();
    }

    @Override
    protected void findViewsById() {

        mName = (TextView) findViewById(R.id.userinfo_name);
        mTextPaint = mName.getPaint();
        mTextPaint.setFakeBoldText(true);
        mGender = (TextView) findViewById(R.id.userinfo_gender);
        mTextPaint = mGender.getPaint();
        mTextPaint.setFakeBoldText(true);
        mSign = (TextView) findViewById(R.id.userinfo_sign);
        mTextPaint = mSign.getPaint();
        mTextPaint.setFakeBoldText(true);
        mPostCount = (TextView) findViewById(R.id.userinfo_post_count);
        mTextPaint = mPostCount.getPaint();
        mTextPaint.setFakeBoldText(true);
        mLoginCount = (TextView) findViewById(R.id.userinfo_login_count);
        mTextPaint = mLoginCount.getPaint();
        mTextPaint.setFakeBoldText(true);
        mLoginInfo = (TextView) findViewById(R.id.userinfo_login_info);
        mTextPaint = mLoginInfo.getPaint();
        mTextPaint.setFakeBoldText(true);
        mPortrait = (ImageView) findViewById(R.id.userinfo_portrait);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void onDestroy() {
        mPortrait.setImageBitmap(null);
        super.onDestroy();
    }

    private void initData() {

        try {
            mUser = Utils.getUser(mContext);

            mName.setText(mUser.getId());

            String gender = mUser.getGender();
            if (gender.equals("m")) {
                gender = "男生";
            } else if (gender.equals("f")) {
                gender = "女生";
            } else {
                gender = "未知";
            }
            mGender.setText(gender);

            String sign = mUser.getAstro();
            if (null == sign || sign.length() <= 0) {
                sign = "未知";
            }
            mSign.setText(sign);

            mPostCount.setText("发帖：" + mUser.getPostCount());
            mLoginCount.setText("登录：" + mUser.getLoginCount());
            String loginTime = String.valueOf(mUser.getLastLoginTime()) + "000";
            mLoginInfo.setText(" 上次登录：\n\n    "
                    + Utils.BYR_DATE_FORMAT.format(Long.valueOf(loginTime))
                    + "\n    " + mUser.getLastLoginIp());

            String imageUrl = mUser.getFaceUrl();
            if (null != imageUrl && imageUrl.length() > 0) {
                mImageLoader.displayImage(imageUrl, mPortrait);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            BYRToast.showShortToast(mContext, e.toString());
            finish();
        }
    }

}
