package haitong.yao.byrclient.tasks;

import haitong.yao.byrclient.net.NetUtil;
import android.content.Context;
import android.os.AsyncTask;

public class GetBoardTask extends AbsTask {

    private String mName;
    private int mPage;

    public GetBoardTask(Context context, String name, int page,
            ITaskFinishListener listener) {
        mContext = context;
        mName = name;
        mPage = page;
        mListener = listener;
    }

    @Override
    public void execute() {
        new GetBoard().execute(mName);
    }

    private class GetBoard extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... params) {
            return NetUtil.getBoard(mContext, mName, mPage);
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (null != mListener) {
                mListener.onTaskFinished(GetBoardTask.this, result);
            }
        }

    }

}
