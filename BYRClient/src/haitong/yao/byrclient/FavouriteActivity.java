package haitong.yao.byrclient;

import haitong.yao.byrclient.adapters.BoardListAdapter;
import haitong.yao.byrclient.constant.IntentExtras;
import haitong.yao.byrclient.models.Board;
import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.GetFavouriteTask;
import haitong.yao.byrclient.tasks.ITaskFinishListener;
import haitong.yao.byrclient.utils.BYRToast;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class FavouriteActivity extends NoTitleActivity implements
        OnItemClickListener, ITaskFinishListener {

    private final int TOP_LEVEL = 0;

    private GridView mFavouriteList;
    private BoardListAdapter mListAdapter;
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
        Board board = mListAdapter.getItem(position);
        if (null == board || TextUtils.isEmpty(board.getName())) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(IntentExtras.BOARD_NAME, board.getName());
        intent.setClass(FavouriteActivity.this, BoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onTaskFinished(AbsTask task, Object result) {

        mLoadingView.setVisibility(View.GONE);

        if (null == result) {
            BYRToast.showLongToast(mContext, R.string.fail_get_content);
        } else {
            mFavouriteList.setVisibility(View.VISIBLE);
            mListAdapter.setContent((List<Board>) result);
            mListAdapter.notifyDataSetChanged();
        }
    }

    private void initAdapter() {
        mListAdapter = new BoardListAdapter(mContext);
        mFavouriteList.setAdapter(mListAdapter);
    }

    private void getFavourites() {
        new GetFavouriteTask(mContext, TOP_LEVEL, this).execute();
    }
}
