package haitong.yao.byrclient.views;

import haitong.yao.byrclient.R;
import haitong.yao.byrclient.models.Article;
import haitong.yao.byrclient.models.SingleAttachment;
import haitong.yao.byrclient.utils.Utils;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class SubjectItem extends FrameLayout {

    private Context mContext;
    private Handler mHandler;

    private int mPosition;
    private Article mArticle;

    private ImageLoader mImageLoader;
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
        mImageLoader = ImageLoader.getInstance();
        mPosition = position;
        initView();
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

        if (!TextUtils.isEmpty(portrait)) {
            mImageLoader.displayImage(portrait, mPortrait);
        }

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
            String url = singleAttachment.getThumbnailSmall()
                    + "?appkey=78e223c052793f0b";
            ImageView img = new ImageView(mContext);
            img.setScaleType(ScaleType.CENTER_INSIDE);
            mAttachments.addView(img, params);
            if (!TextUtils.isEmpty(url)) {
                mImageLoader.displayImage(url, img);
            }
        }

        for (SingleAttachment singleAttachment : files) {
            // String name = singleAttachment.getName();
            // TextView text = new TextView(mContext);
            // text.setSingleLine();
            // text.setText(name);
            // Linkify.addLinks(text, Linkify.ALL);
            // mAttachments.addView(text, params);
        }
    }

}
