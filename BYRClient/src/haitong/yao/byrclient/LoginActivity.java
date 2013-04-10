package haitong.yao.byrclient;

import haitong.yao.byrclient.models.RequestError;
import haitong.yao.byrclient.models.User;
import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.LoginTask;
import haitong.yao.byrclient.tasks.ITaskFinishListener;
import haitong.yao.byrclient.utils.BYRToast;
import haitong.yao.byrclient.utils.Utils;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends NoTitleActivity implements
        ITaskFinishListener {

    private TextView mTitle;
    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private Button mClear;
    private Button mRegister;

    private TextPaint mTextPaint;

    private Context mContext;

    private Handler mUIHandler;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.act_login);
        mContext = getApplicationContext();
        mUIHandler = new Handler();
        findViewsById();
        setListeners();
        initData();
    }

    @Override
    protected void findViewsById() {
        mTitle = (TextView) findViewById(R.id.login_title);
        mTextPaint = mTitle.getPaint();
        mTextPaint.setFakeBoldText(true);
        mUsername = (EditText) findViewById(R.id.login_username);
        mPassword = (EditText) findViewById(R.id.login_password);
        mLogin = (Button) findViewById(R.id.login_login);
        mTextPaint = mLogin.getPaint();
        mTextPaint.setFakeBoldText(true);
        mClear = (Button) findViewById(R.id.login_clear);
        mTextPaint = mClear.getPaint();
        mTextPaint.setFakeBoldText(true);
        mRegister = (Button) findViewById(R.id.login_register);
        mTextPaint = mRegister.getPaint();
        mTextPaint.setFakeBoldText(true);
    }

    @Override
    protected void setListeners() {

        mLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    BYRToast.showShortToast(mContext,
                            R.string.warning_empty_username);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    BYRToast.showShortToast(mContext,
                            R.string.warning_empty_password);
                    return;
                }
                new LoginTask(mContext, username, password, LoginActivity.this)
                        .execute();
            }
        });

        mClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mUsername.setText(null);
                mPassword.setText(null);
                Utils.clearPrefs(mContext);
            }
        });

        mRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Uri uri = Uri.parse("http://bbs.byr.cn/#!reg");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        String u = Utils.getContent(mContext, Utils.KEY_USERNAME);
        String p = Utils.getContent(mContext, Utils.KEY_PASSWORD);
        if (!TextUtils.isEmpty(u) && !TextUtils.isEmpty(p)) {
            mUsername.setText(u);
            mPassword.setText(p);
        }
    }

    @Override
    public void onTaskFinished(AbsTask task, final Object result) {

        if (null == result) {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    BYRToast.showShortToast(mContext, R.string.error_network);
                }
            });
            return;
        }

        if (result instanceof RequestError) {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    BYRToast.showShortToast(mContext,
                            ((RequestError) result).getMsg());
                }
            });
            return;
        }

        if (result instanceof User) {
            Utils.saveUser(mContext, (User) result);
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

    }

}
