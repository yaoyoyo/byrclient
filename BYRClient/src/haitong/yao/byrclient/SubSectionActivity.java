package haitong.yao.byrclient;

import haitong.yao.byrclient.adapters.BoardListAdapter;
import haitong.yao.byrclient.constant.IntentExtras;
import haitong.yao.byrclient.models.Section;
import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.GetSubSectionTask;
import haitong.yao.byrclient.tasks.ITaskFinishListener;
import haitong.yao.byrclient.utils.BYRToast;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class SubSectionActivity extends NoTitleActivity implements
		OnItemClickListener, ITaskFinishListener {

	private GridView mSubSectionList;
	private BoardListAdapter mListAdapter;
	private View mLoadingView;

	private Context mContext;

	private String mSectionName;

	@Override
	protected void init(Bundle savedInstanceState) {
		setContentView(R.layout.act_subsection);
		mContext = getApplicationContext();
		mSectionName = getIntent().getStringExtra(IntentExtras.SECTION_NAME);
		findViewsById();
		initAdapter();
		setListeners();
		getSubSections();
	}

	@Override
	protected void findViewsById() {
		mSubSectionList = (GridView) findViewById(R.id.subsection_gv);
		mLoadingView = findViewById(R.id.loading_view);
	}

	@Override
	protected void setListeners() {
		mSubSectionList.setOnItemClickListener(this);
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
			mSubSectionList.setVisibility(View.VISIBLE);
			Section section = (Section) result;
			mListAdapter.setContent(section.getBoard());
			mListAdapter.notifyDataSetChanged();
		}
	}

	private void initAdapter() {
		mListAdapter = new BoardListAdapter(mContext);
		mSubSectionList.setAdapter(mListAdapter);
	}

	private void getSubSections() {
		new GetSubSectionTask(mContext, mSectionName, this).execute();
	}
}
