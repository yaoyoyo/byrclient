package haitong.yao.byrclient.tasks;

import haitong.yao.byrclient.R;
import haitong.yao.byrclient.models.Board;
import haitong.yao.byrclient.models.Section;
import haitong.yao.byrclient.net.NetUtil;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

public class GetSubSectionTask extends AbsTask {

    private String mName;

    public GetSubSectionTask(Context context, String name,
            ITaskFinishListener listener) {
        mContext = context;
        mName = name;
        mListener = listener;
    }

    @Override
    public void execute() {
        new GetSubSection().execute(mName);
    }

    private class GetSubSection extends AsyncTask<String, Object, Object> {

        @Override
        protected Object doInBackground(String... params) {
            return NetUtil.getSection(mContext, mName);
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (null != result) {
                Section section = (Section) result;
                List<String> subSection = section.getSubSection();
                List<Board> boards = section.getBoard();
                if (null != subSection && subSection.size() > 0) {
                    for (String name : subSection) {
                        Board b = new Board();
                        b.setName(name);
                        b.setDescription(translate(name));
                        b.setIsSubSection(true);
                        boards.add(0, b);
                    }
                }
            }
            if (null != mListener) {
                mListener.onTaskFinished(GetSubSectionTask.this, result);
            }
        }

    }

    private String translate(String name) {
        Resources res = mContext.getResources();
        if (name.equals("School")) {
            return res.getString(R.string.sections_school);
        } else if (name.equals("Association")) {
            return res.getString(R.string.sections_association);
        } else if (name.equals("Court")) {
            return res.getString(R.string.sections_court);
        } else if (name.equals("cn_bbs")) {
            return res.getString(R.string.sections_cn_bbs);
        } else if (name.equals("BYR_Team")) {
            return res.getString(R.string.sections_byr_team);
        } else if (name.equals("Board")) {
            return res.getString(R.string.sections_board);
        } else if (name.equals("BM_Affair")) {
            return res.getString(R.string.sections_bm_affair);
        } else if (name.equals("BBSLOG")) {
            return res.getString(R.string.sections_bbslog);
        } else if (name.equals("Advertise")) {
            return res.getString(R.string.sections_advertise);
        } else if (name.equals("China")) {
            return res.getString(R.string.sections_china);
        } else if (name.equals("OnlineGames")) {
            return res.getString(R.string.sections_onlinegames);
        }
        return name;
    }

}
