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
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class SubSectionActivity extends NoTitleActivity implements
		OnItemClickListener, ITaskFinishListener {

	private static final float THRESHOLD_FLING_VELOCITY = 200f;
	private static final float THRESHOLD_FLING_DISTANCE_LEFT = 100f;
	private static final float THRESHOLD_FLING_DISTANCE_RIGHT = -100f;

	private GridView mSubSectionList;
	private BoardListAdapter mListAdapter;
	private View mLoadingView;

	private Context mContext;

	private String mSectionName;

	private GestureDetector mDetector;

	@Override
	protected void init(Bundle savedInstanceState) {
		setContentView(R.layout.act_subsection);
		mContext = getApplicationContext();
		mSectionName = getIntent().getStringExtra(IntentExtras.SECTION_NAME);
		mDetector = new GestureDetector(new SlideGesture());
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
		mSubSectionList.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (mDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mDetector.onTouchEvent(event);
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

	private void slideToOtherSection(int name) {
		mSectionName = String.valueOf(name);
		mLoadingView.setVisibility(View.VISIBLE);
		mSubSectionList.setVisibility(View.GONE);
		new GetSubSectionTask(mContext, mSectionName, this).execute();
	}

	private class SlideGesture implements OnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			float deltaX = e2.getX() - e1.getX();
			float deltaY = e2.getY() - e1.getY();
			if (!mSubSectionList.isShown()) {
				return false;
			}
			if (Math.abs(deltaX) / 3 < Math.abs(deltaY)) {
				return false;
			}

			int name = 0;
			try {
				name = Integer.valueOf(mSectionName);
			} catch (Exception e) {
				name = -1;
			}
			if (name == -1) {
				return false;
			}

			if (deltaX > THRESHOLD_FLING_DISTANCE_LEFT
					&& Math.abs(velocityX) > THRESHOLD_FLING_VELOCITY
					&& name != 0) {
				name--;
				slideToOtherSection(name);
				return true;
			} else if (deltaX < THRESHOLD_FLING_DISTANCE_RIGHT
					&& Math.abs(velocityX) > THRESHOLD_FLING_VELOCITY
					&& name != 8) {
				name++;
				slideToOtherSection(name);
				return true;
			}

			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
		}

	}

}
