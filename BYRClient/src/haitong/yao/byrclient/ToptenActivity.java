package haitong.yao.byrclient;

import java.util.ArrayList;

import haitong.yao.byrclient.adapters.ToptenListAdapter;
import haitong.yao.byrclient.models.Article;
import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.GetArticlesTask;
import haitong.yao.byrclient.tasks.ITaskFinishListener;
import haitong.yao.byrclient.utils.BYRToast;
import haitong.yao.byrclient.views.ListFunctionBar;
import haitong.yao.byrclient.views.ListFunctionBar.ListBarClickListener;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ToptenActivity extends NoTitleActivity implements
		OnItemClickListener, ITaskFinishListener, ListBarClickListener {

	private final int DIVIDER_HEIGHT = 6;

	private ListView mToptenList;
	private ToptenListAdapter mListAdapter;
	private View mLoadingView;
	private ListFunctionBar mFunctionBar;

	private Context mContext;

	@Override
	protected void init(Bundle savedInstanceState) {
		setContentView(R.layout.act_topten);
		mContext = getApplicationContext();
		findViewsById();
		initAdapter();
		setListeners();
		getArticles();
	}

	@Override
	protected void findViewsById() {
		mToptenList = (ListView) findViewById(R.id.topten_list);
		mToptenList.setDivider(null);
		mToptenList.setDividerHeight(DIVIDER_HEIGHT);
		mLoadingView = findViewById(R.id.loading_view);
		mFunctionBar = (ListFunctionBar) findViewById(R.id.list_function_bar);
		mFunctionBar.setVisibility(View.GONE);
	}

	@Override
	protected void setListeners() {
		mToptenList.setOnItemClickListener(this);
		mFunctionBar.setBarClickListener(this);
	}

	@Override
	public void home() {
		finish();
	}

	@Override
	public void refresh() {
		mToptenList.setVisibility(View.GONE);
		mFunctionBar.setVisibility(View.GONE);
		mLoadingView.setVisibility(View.VISIBLE);
		getArticles();
	}

	@Override
	public void turnto() {

	}

	@Override
	public void next() {
		BYRToast.showShortToast(mContext, R.string.notification_last_page);
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
			mToptenList.setVisibility(View.VISIBLE);
			mFunctionBar.setVisibility(View.VISIBLE);
			mListAdapter.setContent((ArrayList<Article>) result);
			mListAdapter.notifyDataSetChanged();
		}

	}

	private void initAdapter() {
		mListAdapter = new ToptenListAdapter(mContext);
		mToptenList.setAdapter(mListAdapter);
	}

	private void getArticles() {
		new GetArticlesTask(mContext, "topten", this).execute();
	}

}
