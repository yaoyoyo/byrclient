package haitong.yao.byrclient;

import haitong.yao.byrclient.constant.IntentExtras;
import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.ITaskFinishListener;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * @author Mr.Yao 帖子主界面，包括主帖及回复
 */
public class SubjectActivity extends NoTitleActivity implements
        ITaskFinishListener {

    private ListView mArticleList;
    private View mLoadingView;

    private String mBoardName;
    private int mId;

    private Context mContext;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.act_subject);

        mContext = getApplicationContext();

        initData();
        findViewsById();
        initAdapter();
        setListeners();
        getSubject();
    }

    @Override
    protected void findViewsById() {
        mArticleList = (ListView) findViewById(R.id.subject_list);
        mLoadingView = findViewById(R.id.loading_view);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onTaskFinished(AbsTask task, Object result) {
        // TODO Auto-generated method stub

    }

    private void initData() {
        mId = getIntent().getIntExtra(IntentExtras.SUBJECT_ID, 0);
        mBoardName = getIntent().getStringExtra(IntentExtras.BOARD_NAME);
        if (null == mBoardName) {
            mBoardName = "";
        }
    }

    private void initAdapter() {

    }

    private void getSubject() {

    }

}
