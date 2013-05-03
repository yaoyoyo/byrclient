package haitong.yao.byrclient;

import haitong.yao.byrclient.adapters.MailListAdapter;
import haitong.yao.byrclient.models.Mail;
import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.GetMailsTask;
import haitong.yao.byrclient.tasks.ITaskFinishListener;
import haitong.yao.byrclient.utils.BYRToast;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MailboxActivity extends NoTitleActivity implements
		OnItemClickListener, ITaskFinishListener {

	private final int DIVIDER_HEIGHT = 6;

	private ListView mMailList;
	private MailListAdapter mListAdapter;
	private View mLoadingView;

	private Context mContext;
	private Handler mHandler;

	@Override
	protected void init(Bundle savedInstanceState) {
		setContentView(R.layout.act_mailbox);
		mContext = getApplicationContext();
		mHandler = new Handler();
		findViewsById();
		initAdapter();
		setListeners();
		getMails();
	}

	@Override
	protected void findViewsById() {
		mMailList = (ListView) findViewById(R.id.mailbox_list);
		mMailList.setDivider(null);
		mMailList.setDividerHeight(DIVIDER_HEIGHT);
		mLoadingView = findViewById(R.id.loading_view);
	}

	@Override
	protected void setListeners() {
		mMailList.setOnItemClickListener(this);
	}

	@Override
	public void onTaskFinished(AbsTask task, final Object result) {

		mHandler.post(new Runnable() {
			@Override
			public void run() {
				mLoadingView.setVisibility(View.GONE);

				if (null == result) {
					BYRToast.showLongToast(mContext, R.string.fail_get_content);
				} else {
					mMailList.setVisibility(View.VISIBLE);
					mListAdapter.setContent((ArrayList<Mail>) result);
					mListAdapter.notifyDataSetChanged();
				}
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

	private void initAdapter() {
		mListAdapter = new MailListAdapter(mContext);
		mMailList.setAdapter(mListAdapter);
	}

	private void getMails() {
		new GetMailsTask(mContext, "inbox", this).execute();
	}

}
