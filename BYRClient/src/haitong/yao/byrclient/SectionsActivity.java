package haitong.yao.byrclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SectionsActivity extends NoTitleActivity {

    private TextView mStaff;
    private TextView mCampus;
    private TextView mTech;
    private TextView mInformation;
    private TextView mArt;
    private TextView mLife;
    private TextView mEntertament;
    private TextView mSports;
    private TextView mGame;

    private TextPaint mTextPaint;

    private Context mContext;

    @Override
    protected void init(Bundle savedInstanceState) {

        setContentView(R.layout.act_sections);
        mContext = getApplicationContext();

        findViewsById();
        setListeners();
    }

    @Override
    protected void findViewsById() {

        mStaff = (TextView) findViewById(R.id.sections_bbs_staff);
        mTextPaint = mStaff.getPaint();
        mTextPaint.setFakeBoldText(true);
        mCampus = (TextView) findViewById(R.id.sections_campus);
        mTextPaint = mCampus.getPaint();
        mTextPaint.setFakeBoldText(true);
        mTech = (TextView) findViewById(R.id.sections_tech);
        mTextPaint = mTech.getPaint();
        mTextPaint.setFakeBoldText(true);
        mInformation = (TextView) findViewById(R.id.sections_information);
        mTextPaint = mInformation.getPaint();
        mTextPaint.setFakeBoldText(true);
        mArt = (TextView) findViewById(R.id.sections_art);
        mTextPaint = mArt.getPaint();
        mTextPaint.setFakeBoldText(true);
        mLife = (TextView) findViewById(R.id.sections_life);
        mTextPaint = mLife.getPaint();
        mTextPaint.setFakeBoldText(true);
        mEntertament = (TextView) findViewById(R.id.sections_entertament);
        mTextPaint = mEntertament.getPaint();
        mTextPaint.setFakeBoldText(true);
        mSports = (TextView) findViewById(R.id.sections_sports);
        mTextPaint = mSports.getPaint();
        mTextPaint.setFakeBoldText(true);
        mGame = (TextView) findViewById(R.id.sections_game);
        mTextPaint = mGame.getPaint();
        mTextPaint.setFakeBoldText(true);
    }

    @Override
    protected void setListeners() {

        final Intent intent = new Intent();

        mStaff.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            }
        });

        mCampus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            }
        });

        mTech.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            }
        });

        mInformation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            }
        });

        mArt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            }
        });

        mLife.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            }
        });

        mEntertament.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            }
        });

        mSports.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            }
        });

        mGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            }
        });

    }

}
