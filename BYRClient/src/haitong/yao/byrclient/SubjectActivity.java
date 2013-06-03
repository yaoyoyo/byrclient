package haitong.yao.byrclient;

import haitong.yao.byrclient.adapters.SubjectAdapter;
import haitong.yao.byrclient.constant.IntentExtras;
import haitong.yao.byrclient.models.Article;
import haitong.yao.byrclient.models.Pagination;
import haitong.yao.byrclient.models.Subject;
import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.GetSubjectTask;
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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

/**
 * @author Mr.Yao 帖子主界面，包括主帖及回复
 */
public class SubjectActivity extends NoTitleActivity implements
        ITaskFinishListener, ListBarClickListener, OnItemClickListener {

    private static final String STATE_PAUSE_ON_SCROLL = "STATE_PAUSE_ON_SCROLL";
    private static final String STATE_PAUSE_ON_FLING = "STATE_PAUSE_ON_FLING";

    private final int DIVIDER_HEIGHT = 6;
    private final int START_PAGE = 1;

    private ListView mArticleList;
    private SubjectAdapter mListAdapter;
    private View mLoadingView;
    private ListFunctionBar mFunctionBar;

    private Context mContext;
    private ImageLoader mImageLoader;
    private boolean mPauseOnScroll = true;
    private boolean mPauseOnFling = true;

    private String mBoardName;
    private int mId;
    private int mCurrentPage;
    private Pagination mPagination;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.act_subject);
        mContext = getApplicationContext();
        mImageLoader = ImageLoader.getInstance();
        mBoardName = getIntent().getStringExtra(IntentExtras.BOARD_NAME);
        mId = getIntent().getIntExtra(IntentExtras.SUBJECT_ID, 0);
        mCurrentPage = START_PAGE;
        findViewsById();
        initAdapter();
        setListeners();
        getSubject(START_PAGE);
    }

    @Override
    protected void findViewsById() {
        mArticleList = (ListView) findViewById(R.id.subject_list);
        mArticleList.setDivider(null);
        mArticleList.setDividerHeight(DIVIDER_HEIGHT);
        mArticleList
                .setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mLoadingView = findViewById(R.id.loading_view);
        mFunctionBar = (ListFunctionBar) findViewById(R.id.list_function_bar);
        mFunctionBar.setRefreshText(getResources().getString(
                R.string.list_function_bar_thread));
        mFunctionBar.setVisibility(View.GONE);
    }

    @Override
    protected void setListeners() {
        mArticleList.setOnItemClickListener(this);
        mFunctionBar.setBarClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        applyScrollListener();
    }

    private void applyScrollListener() {
        mArticleList.setOnScrollListener(new PauseOnScrollListener(
                mImageLoader, mPauseOnScroll, mPauseOnFling));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mPauseOnScroll = savedInstanceState.getBoolean(STATE_PAUSE_ON_SCROLL,
                true);
        mPauseOnFling = savedInstanceState.getBoolean(STATE_PAUSE_ON_FLING,
                true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_PAUSE_ON_SCROLL, mPauseOnScroll);
        outState.putBoolean(STATE_PAUSE_ON_FLING, mPauseOnFling);
    }

    @Override
    public void home() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void turnto() {
        EditText editText = (EditText) findViewById(R.id.list_function_bar_page);
        String page = editText.getText().toString().trim();
        editText.setText("");
        hideKeyboard();
        if (TextUtils.isEmpty(page)) {
            BYRToast.showShortToast(mContext, R.string.notification_empty_page);
            return;
        }
        int totalPage = mPagination.getPageAllCount();
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
        getSubject(mCurrentPage);
    }

    @Override
    public void next() {
        int totalPage = mPagination.getPageAllCount();
        if (mCurrentPage == totalPage) {
            BYRToast.showShortToast(mContext, R.string.notification_last_page);
        } else if (mCurrentPage > totalPage) {
            BYRToast.showShortToast(mContext,
                    R.string.notification_page_out_range);
        } else {
            mCurrentPage++;
            mArticleList.setVisibility(View.GONE);
            mFunctionBar.setVisibility(View.GONE);
            mLoadingView.setVisibility(View.VISIBLE);
            getSubject(mCurrentPage);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {

        if (null == mArticleList || null == mListAdapter) {
            return;
        }
    }

    @Override
    public void onTaskFinished(AbsTask task, Object result) {

        mLoadingView.setVisibility(View.GONE);

        if (null == result) {
            BYRToast.showLongToast(mContext, R.string.fail_get_content);
        } else {
            mArticleList.setVisibility(View.VISIBLE);
            mFunctionBar.setVisibility(View.VISIBLE);
            Subject subject = (Subject) result;
            mPagination = subject.getPagination();
            List<Article> articles = subject.getArticle();
            mListAdapter.setContent(articles);
            mListAdapter.notifyDataSetChanged();
        }

    }

    private void initAdapter() {
        mListAdapter = new SubjectAdapter(this, mImageLoader);
        mArticleList.setAdapter(mListAdapter);
    }

    private void getSubject(int page) {
        new GetSubjectTask(mContext, mBoardName, mId, page, this).execute();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.list_function_bar_page)
                .getWindowToken(), 0);
    }

}
