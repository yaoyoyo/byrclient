package haitong.yao.byrclient;

import haitong.yao.byrclient.adapters.FavouriteListAdapter;
import haitong.yao.byrclient.models.Board;
import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.GetFavouriteTask;
import haitong.yao.byrclient.tasks.ITaskFinishListener;
import haitong.yao.byrclient.utils.BYRToast;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class FavouriteActivity extends NoTitleActivity implements
        OnItemClickListener, ITaskFinishListener {

    private final int TOP_LEVEL = 0;

    private GridView mFavouriteList;
    private FavouriteListAdapter mListAdapter;
    private View mLoadingView;

    private Context mContext;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.act_favourite);
        mContext = getApplicationContext();
        findViewsById();
        initAdapter();
        setListeners();
        getFavourites();
    }

    @Override
    protected void findViewsById() {
        mFavouriteList = (GridView) findViewById(R.id.favourite_gv);
        mLoadingView = findViewById(R.id.loading_view);
    }

    @Override
    protected void setListeners() {
        mFavouriteList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {

    }

    @Override
    public void onTaskFinished(AbsTask task, Object result) {

        mLoadingView.setVisibility(View.GONE);

        if (null == result) {
            BYRToast.showLongToast(mContext, R.string.fail_get_content);
        } else {
            mFavouriteList.setVisibility(View.VISIBLE);
            mListAdapter.setContent((ArrayList<Board>) result);
            mListAdapter.notifyDataSetChanged();
        }
    }

    private void initAdapter() {
        mListAdapter = new FavouriteListAdapter(mContext);
        mFavouriteList.setAdapter(mListAdapter);
    }

    private void getFavourites() {
        new GetFavouriteTask(mContext, TOP_LEVEL, this).execute();
    }
}
