package haitong.yao.byrclient.tasks;

import haitong.yao.byrclient.net.NetUtil;
import android.content.Context;
import android.os.AsyncTask;

public class LoginTask extends AbsTask {

    private String mUsername;
    private String mPassword;

    public LoginTask(Context context, String username, String password,
            ITaskFinishListener listener) {
        mContext = context;
        mUsername = username;
        mPassword = password;
        mListener = listener;
    }

    @Override
    public void execute() {
        new Login().execute(mUsername, mPassword);
    }

    private class Login extends AsyncTask<String, Object, Object> {

        @Override
        protected Object doInBackground(String... params) {
            return NetUtil.login(mContext, params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (null != mListener) {
                mListener.onTaskFinished(LoginTask.this, result);
            }
        }

    }

}
