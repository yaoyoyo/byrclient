package haitong.yao.byrclient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends NoTitleActivity {

    private TextView mTopten;
    private TextView mHot;
    private TextView mSections;
    private TextView mRecommended;
    private TextView mByr;
    private TextView mFavourite;
    private TextView mUser;
    private TextView mMailbox;

    private TextPaint mTextPaint;

    private Context mContext;

    @Override
    protected void init(Bundle savedInstanceState) {

        setContentView(R.layout.act_main);
        mContext = getApplicationContext();

        findViewsById();
        setListeners();
    }

    @Override
    protected void findViewsById() {

        mTopten = (TextView) findViewById(R.id.main_topten);
        mTextPaint = mTopten.getPaint();
        mTextPaint.setFakeBoldText(true);
        mHot = (TextView) findViewById(R.id.main_hot);
        mTextPaint = mHot.getPaint();
        mTextPaint.setFakeBoldText(true);
        mSections = (TextView) findViewById(R.id.main_sections);
        mTextPaint = mSections.getPaint();
        mTextPaint.setFakeBoldText(true);
        mRecommended = (TextView) findViewById(R.id.main_recommended);
        mTextPaint = mRecommended.getPaint();
        mTextPaint.setFakeBoldText(true);
        mByr = (TextView) findViewById(R.id.main_byr);
        mTextPaint = mByr.getPaint();
        mTextPaint.setFakeBoldText(true);
        mFavourite = (TextView) findViewById(R.id.main_favourite);
        mTextPaint = mFavourite.getPaint();
        mTextPaint.setFakeBoldText(true);
        mUser = (TextView) findViewById(R.id.main_user);
        mTextPaint = mUser.getPaint();
        mTextPaint.setFakeBoldText(true);
        mMailbox = (TextView) findViewById(R.id.main_mailbox);
        mTextPaint = mMailbox.getPaint();
        mTextPaint.setFakeBoldText(true);
    }

    @Override
    protected void setListeners() {

        final Intent intent = new Intent();

        mTopten.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                intent.setClass(mContext, ToptenActivity.class);
                startActivity(intent);
            }
        });

        mHot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                intent.setClass(mContext, FocusActivity.class);
                startActivity(intent);
            }
        });

        mSections.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                intent.setClass(mContext, SectionsActivity.class);
                startActivity(intent);
            }
        });

        mRecommended.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                intent.setClass(mContext, RecommendedActivity.class);
                startActivity(intent);
            }
        });

        mByr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                intent.setClass(mContext, AboutActivity.class);
                startActivity(intent);
            }
        });

        mFavourite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                intent.setClass(mContext, FavouriteActivity.class);
                startActivity(intent);
            }
        });

        mUser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                intent.setClass(mContext, UserInfoActivity.class);
                startActivity(intent);
            }
        });

        mMailbox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                intent.setClass(mContext, MailboxActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setTitle(R.string.main_sure_exit)
                    .setCancelable(true)
                    .setPositiveButton(R.string.main_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int id) {
                                    finish();
                                }
                            })
                    .setNegativeButton(R.string.main_cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int id) {
                                    dialog.cancel();
                                }
                            });
            builder.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
