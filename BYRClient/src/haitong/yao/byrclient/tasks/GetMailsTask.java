package haitong.yao.byrclient.tasks;

import haitong.yao.byrclient.net.NetUtil;
import android.content.Context;
import android.os.AsyncTask;

public class GetMailsTask extends AbsTask {

	private String mType;

	public GetMailsTask(Context context, String type,
			ITaskFinishListener listener) {
		mContext = context;
		mType = type;
		mListener = listener;
	}

	@Override
	public void execute() {
		new GetMails().execute(mType);
	}

	private class GetMails extends AsyncTask<String, Object, Object> {

		@Override
		protected Object doInBackground(String... params) {
			return NetUtil.getMails(mContext, mType);
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (null != mListener) {
				mListener.onTaskFinished(GetMailsTask.this, result);
			}
		}

	}

}
