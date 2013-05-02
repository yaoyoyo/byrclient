package haitong.yao.byrclient.tasks;

import haitong.yao.byrclient.net.NetUtil;
import android.content.Context;
import android.os.AsyncTask;

public final class GetImageTask extends AbsTask {

	public GetImageTask(Context context, String url,
			ITaskFinishListener listener) {
		mContext = context;
		mUrl = url;
		mListener = listener;
	}

	@Override
	public void execute() {
		new GetImage().execute(mUrl);
	}

	private class GetImage extends AsyncTask<String, Object, Object> {

		@Override
		protected Object doInBackground(String... params) {
			return NetUtil.getBitmapFromUrl(mContext, mUrl);
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (null != mListener) {
				mListener.onTaskFinished(GetImageTask.this, result);
			}
		}
	}

}
