package haitong.yao.byrclient.views;

import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.ITaskFinishListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SubjectItem implements ITaskFinishListener {

    TextView id;
    ImageView portrait;
    TextView floor;
    TextView info;
    TextView content;
    ImageView[] imgs;
    TextView[] files;

    @Override
    public void onTaskFinished(AbsTask task, Object result) {
        // TODO Auto-generated method stub

    }

}
