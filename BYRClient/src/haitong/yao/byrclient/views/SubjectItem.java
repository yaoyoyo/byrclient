package haitong.yao.byrclient.views;

import haitong.yao.byrclient.R;
import haitong.yao.byrclient.models.Article;
import haitong.yao.byrclient.models.SingleAttachment;
import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.GetImageTask;
import haitong.yao.byrclient.tasks.ITaskFinishListener;
import haitong.yao.byrclient.utils.Utils;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SubjectItem extends FrameLayout implements ITaskFinishListener {

    private Context mContext;
    private Handler mHandler;
    private HashMap<String, ImageView> mHashMap;

    private int mPosition;
    private Article mArticle;

    private View mParent;
    private TextView mId;
    private ImageView mPortrait;
    private TextView mFloor;
    private TextView mInfo;
    private TextView mContent;
    private LinearLayout mAttachments;

    public SubjectItem(Context context, Handler handler, int position) {
        super(context);
        mContext = context;
        mHandler = handler;
        mHashMap = new HashMap<String, ImageView>();
        mPosition = position;
        initView();
    }

    @Override
    public void onTaskFinished(final AbsTask task, final Object result) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (null != result) {
                    String url = task.getUrl();
                    ImageView img = mHashMap.get(url);
                    if (null != img) {
                        img.setImageBitmap((Bitmap) result);
                    }
                }
            }
        });
    }

    public void setArticle(Article article) {
        mArticle = article;
        notifyDataChanged();
    }

    private void initView() {
        mParent = LayoutInflater.from(mContext).inflate(
                R.layout.view_subject_item, this);
        mPortrait = (ImageView) mParent
                .findViewById(R.id.subject_item_protrait);
        mId = (TextView) mParent.findViewById(R.id.subject_item_id);
        mFloor = (TextView) mParent.findViewById(R.id.subject_item_floor);
        mInfo = (TextView) mParent.findViewById(R.id.subject_item_info);
        mContent = (TextView) mParent.findViewById(R.id.subject_item_content);
        mAttachments = (LinearLayout) mParent
                .findViewById(R.id.subject_item_attachments);
        mAttachments.setOrientation(LinearLayout.VERTICAL);
    }

    private void notifyDataChanged() {
        if (null == mArticle) {
            return;
        }

        int floor = mArticle.getPosition();
        String id = mArticle.getUser().getId();
        String name = mArticle.getUser().getUserName();
        String time = Utils.getDate(mArticle.getPostTime());
        String content = mArticle.getContent();
        String portrait = mArticle.getUser().getFaceUrl();
        String format = "";

        mId.setText(id);

        if (floor == 0) {
            mFloor.setText(mContext.getResources().getString(
                    R.string.subject_item_lz));
        } else if (floor == 1) {
            mFloor.setText(mContext.getResources().getString(
                    R.string.subject_item_first));
        } else {
            format = mContext.getResources().getString(
                    R.string.subject_item_floor);
            mFloor.setText(String.format(format, floor));
        }

        format = mContext.getResources().getString(R.string.subject_item_info);
        mInfo.setText(String.format(format, id, name, time));

        mContent.setText(content);
        Linkify.addLinks(mContent, Linkify.ALL);

        mHashMap.put(portrait, mPortrait);
        new GetImageTask(mContext, portrait, this).execute();

        setAttachmentView();

    }

    private void setAttachmentView() {
        if (!mArticle.hasAttachment()) {
            return;
        }

        android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        List<SingleAttachment> images = mArticle.getAttachment().getImages();
        List<SingleAttachment> files = mArticle.getAttachment().getFiles();

        for (SingleAttachment singleAttachment : images) {
            // String url = singleAttachment.getUrl();
            // ImageView img = new ImageView(mContext);
            // img.setScaleType(ScaleType.CENTER_INSIDE);
            // mAttachments.addView(img, params);
            // mHashMap.put(url, img);
            // new GetImageTask(mContext, url, this).execute();
        }

        for (SingleAttachment singleAttachment : files) {
            String name = singleAttachment.getName();
            TextView text = new TextView(mContext);
            text.setSingleLine();
            text.setText(name);
            Linkify.addLinks(text, Linkify.ALL);
            mAttachments.addView(text, params);
        }
    }

}
