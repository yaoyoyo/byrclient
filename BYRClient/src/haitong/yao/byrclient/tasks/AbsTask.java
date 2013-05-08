package haitong.yao.byrclient.tasks;

import android.content.Context;

public abstract class AbsTask {

    protected Context mContext;
    protected String mUrl;
    protected ITaskFinishListener mListener;

    public abstract void execute();

    public String getUrl() {
        return mUrl;
    }
}
