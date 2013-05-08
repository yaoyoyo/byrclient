package haitong.yao.byrclient;

import haitong.yao.byrclient.adapters.ArticleListAdapter;
import haitong.yao.byrclient.models.Article;
import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.GetArticlesTask;
import haitong.yao.byrclient.tasks.ITaskFinishListener;
import haitong.yao.byrclient.utils.BYRToast;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RecommendedActivity extends NoTitleActivity implements
        OnItemClickListener, ITaskFinishListener {

    private final int DIVIDER_HEIGHT = 6;

    private ListView mRecommendList;
    private ArticleListAdapter mListAdapter;
    private View mLoadingView;

    private Context mContext;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.act_recommended);
        mContext = getApplicationContext();
        findViewsById();
        initAdapter();
        setListeners();
        getArticles();
    }

    @Override
    protected void findViewsById() {
        mRecommendList = (ListView) findViewById(R.id.recommended_list);
        mRecommendList.setDivider(null);
        mRecommendList.setDividerHeight(DIVIDER_HEIGHT);
        mLoadingView = findViewById(R.id.loading_view);
    }

    @Override
    protected void setListeners() {
        mRecommendList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

    }

    @Override
    public void onTaskFinished(AbsTask task, Object result) {

        mLoadingView.setVisibility(View.GONE);

        if (null == result) {
            BYRToast.showLongToast(mContext, R.string.fail_get_content);
        } else {
            mRecommendList.setVisibility(View.VISIBLE);
            mListAdapter.setContent((List<Article>) result);
            mListAdapter.notifyDataSetChanged();
        }

    }

    private void initAdapter() {
        mListAdapter = new ArticleListAdapter(mContext,
                ArticleListAdapter.TYPE_SPECIAL);
        mRecommendList.setAdapter(mListAdapter);
    }

    private void getArticles() {
        new GetArticlesTask(mContext, "recommended", this).execute();
    }

}
