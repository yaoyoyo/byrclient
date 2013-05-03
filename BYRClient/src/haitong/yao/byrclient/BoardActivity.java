package haitong.yao.byrclient;

import haitong.yao.byrclient.adapters.ArticleListAdapter;
import haitong.yao.byrclient.constant.IntentExtras;
import haitong.yao.byrclient.models.Article;
import haitong.yao.byrclient.models.Board;
import haitong.yao.byrclient.models.Pagination;
import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.GetBoardTask;
import haitong.yao.byrclient.tasks.ITaskFinishListener;
import haitong.yao.byrclient.utils.BYRToast;
import haitong.yao.byrclient.views.ListFunctionBar;
import haitong.yao.byrclient.views.ListFunctionBar.ListBarClickListener;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BoardActivity extends NoTitleActivity implements
		OnItemClickListener, ITaskFinishListener, ListBarClickListener {

	private final int DIVIDER_HEIGHT = 6;
	private final int START_PAGE = 1;

	private ListView mArticleList;
	private ArticleListAdapter mListAdapter;
	private View mLoadingView;
	private ListFunctionBar mFunctionBar;

	private Context mContext;

	private String mBoardName;
	private int mCurrentPage;
	private Pagination mPagination;

	@Override
	protected void init(Bundle savedInstanceState) {
		setContentView(R.layout.act_board);
		mContext = getApplicationContext();
		mBoardName = getIntent().getStringExtra(IntentExtras.BOARD_NAME);
		mCurrentPage = START_PAGE;
		findViewsById();
		initAdapter();
		setListeners();
		getBoard(START_PAGE);
	}

	@Override
	protected void findViewsById() {
		mArticleList = (ListView) findViewById(R.id.board_list);
		mArticleList.setDivider(null);
		mArticleList.setDividerHeight(DIVIDER_HEIGHT);
		mLoadingView = findViewById(R.id.loading_view);
		mFunctionBar = (ListFunctionBar) findViewById(R.id.list_function_bar);
		mFunctionBar.setVisibility(View.GONE);
	}

	@Override
	protected void setListeners() {
		mArticleList.setOnItemClickListener(this);
		mFunctionBar.setBarClickListener(this);
	}

	@Override
	public void home() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	public void refresh() {
		mArticleList.setVisibility(View.GONE);
		mFunctionBar.setVisibility(View.GONE);
		mLoadingView.setVisibility(View.VISIBLE);
		getBoard(mCurrentPage);
	}

	@Override
	public void turnto() {
		EditText editText = (EditText) findViewById(R.id.list_function_bar_page);
		String page = editText.getText().toString().trim();
		if (TextUtils.isEmpty(page)) {
			BYRToast.showShortToast(mContext, R.string.notification_empty_page);
			return;
		}
		if (null != mPagination) {
			int totalPage = mPagination.getItemAllCount();
			if (Integer.valueOf(page) > totalPage) {
				BYRToast.showShortToast(mContext,
						R.string.notification_page_out_range);
				return;
			} else if (Integer.valueOf(page) < 0) {
				BYRToast.showShortToast(mContext,
						R.string.notification_page_below_zero);
				return;
			}
			mCurrentPage = Integer.valueOf(page);
			mArticleList.setVisibility(View.GONE);
			mFunctionBar.setVisibility(View.GONE);
			mLoadingView.setVisibility(View.VISIBLE);
			getBoard(mCurrentPage);
		} else {
			BYRToast.showShortToast(mContext, R.string.fail_get_pagination);
		}
	}

	@Override
	public void next() {
		if (null != mPagination) {
			int totalPage = mPagination.getItemAllCount();
			if (mCurrentPage == totalPage) {
				BYRToast.showShortToast(mContext,
						R.string.notification_last_page);
			} else if (mCurrentPage > totalPage) {
				BYRToast.showShortToast(mContext,
						R.string.notification_page_out_range);
			} else {
				mCurrentPage++;
				mArticleList.setVisibility(View.GONE);
				mFunctionBar.setVisibility(View.GONE);
				mLoadingView.setVisibility(View.VISIBLE);
				getBoard(mCurrentPage);
			}
		} else {
			BYRToast.showShortToast(mContext, R.string.fail_get_pagination);
		}
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
			mArticleList.setVisibility(View.VISIBLE);
			mFunctionBar.setVisibility(View.VISIBLE);
			Board board = (Board) result;
			mPagination = board.getPagination();
			List<Article> articles = board.getArticle();
			mListAdapter.setContent(articles);
			mListAdapter.notifyDataSetChanged();
		}

	}

	private void initAdapter() {
		mListAdapter = new ArticleListAdapter(mContext,
				ArticleListAdapter.TYPE_NORMAL);
		mArticleList.setAdapter(mListAdapter);
	}

	private void getBoard(int page) {
		new GetBoardTask(mContext, mBoardName, page, this).execute();
	}

}
