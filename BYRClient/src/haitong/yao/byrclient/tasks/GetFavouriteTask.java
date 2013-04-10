package haitong.yao.byrclient.tasks;

import haitong.yao.byrclient.net.NetUtil;
import android.content.Context;
import android.os.AsyncTask;

public class GetFavouriteTask extends AbsTask {

    private int mLevel;

    public GetFavouriteTask(Context context, int level,
            ITaskFinishListener listener) {
        mContext = context;
        mLevel = level;
        mListener = listener;
    }

    @Override
    public void execute() {
        new GetFavourite().execute(mLevel);
    }

    private class GetFavourite extends AsyncTask<Integer, Object, Object> {

        @Override
        protected Object doInBackground(Integer... params) {
            return NetUtil.getFavourites(mContext, params[0]);
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (null != mListener) {
                mListener.onTaskFinished(GetFavouriteTask.this, result);
            }
        }

    }

}
