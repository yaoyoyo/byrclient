package haitong.yao.byrclient.tasks;

import haitong.yao.byrclient.net.NetUtil;
import android.content.Context;
import android.os.AsyncTask;

public class GetSubjectTask extends AbsTask {

    private String mName;
    private int mId;
    private int mPage;

    public GetSubjectTask(Context context, String name, int id, int page,
            ITaskFinishListener listener) {
        mContext = context;
        mName = name;
        mId = id;
        mPage = page;
        mListener = listener;
    }

    @Override
    public void execute() {
        new GetSubject().execute(mName, mId, mPage);
    }

    private class GetSubject extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... params) {
            return NetUtil.getSubject(mContext, mName, mId, mPage);
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (null != mListener) {
                mListener.onTaskFinished(GetSubjectTask.this, result);
            }
        }

    }

}
