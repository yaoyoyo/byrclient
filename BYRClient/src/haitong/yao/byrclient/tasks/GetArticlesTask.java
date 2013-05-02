package haitong.yao.byrclient.tasks;

import haitong.yao.byrclient.net.NetUtil;
import android.content.Context;
import android.os.AsyncTask;

public class GetArticlesTask extends AbsTask {

	private String mType;

	public GetArticlesTask(Context context, String type,
			ITaskFinishListener listener) {
		mContext = context;
		mType = type;
		mListener = listener;
	}

	@Override
	public void execute() {
		new GetArticles().execute(mType);
	}

	private class GetArticles extends AsyncTask<String, Object, Object> {

		@Override
		protected Object doInBackground(String... params) {
			return NetUtil.getArticles(mContext, mType);
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (null != mListener) {
				mListener.onTaskFinished(GetArticlesTask.this, result);
			}
		}

	}

}
