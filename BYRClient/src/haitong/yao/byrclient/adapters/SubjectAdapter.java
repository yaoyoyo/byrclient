package haitong.yao.byrclient.adapters;

import haitong.yao.byrclient.R;
import haitong.yao.byrclient.models.Article;
import haitong.yao.byrclient.models.SingleAttachment;
import haitong.yao.byrclient.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class SubjectAdapter extends BaseAdapter {

    private List<Article> mArticles = new ArrayList<Article>();
    private Activity mActivity;
    private ImageLoader mImageLoader;
    private AnimateFirstDisplayListener mDisplayListener;

    public SubjectAdapter(Activity activity, ImageLoader imageLoader) {
        mActivity = activity;
        mImageLoader = imageLoader;
        mDisplayListener = new AnimateFirstDisplayListener();
    }

    public void setContent(List<Article> articles) {
        mArticles = articles;
    }

    @Override
    public int getCount() {
        if (null == mArticles) {
            return 0;
        }
        return mArticles.size();
    }

    @Override
    public Article getItem(int position) {
        if (null == mArticles || getCount() == 0 || position > mArticles.size()) {
            return null;
        }
        return mArticles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (null == getItem(position)) {
            return null;
        }

        Article article = getItem(position);
        ViewHolder viewHolder = null;

        if (null == convertView || null == convertView.getTag()) {
            convertView = LayoutInflater.from(mActivity).inflate(
                    R.layout.view_subject_item, null);
            viewHolder = new ViewHolder();
            viewHolder.id = (TextView) convertView
                    .findViewById(R.id.subject_item_id);
            viewHolder.floor = (TextView) convertView
                    .findViewById(R.id.subject_item_floor);
            viewHolder.info = (TextView) convertView
                    .findViewById(R.id.subject_item_info);
            viewHolder.content = (TextView) convertView
                    .findViewById(R.id.subject_item_content);
            viewHolder.portrait = (ImageView) convertView
                    .findViewById(R.id.subject_item_protrait);
            viewHolder.image = (ImageView) convertView
                    .findViewById(R.id.subject_item_image);
            viewHolder.attachment = (TextView) convertView
                    .findViewById(R.id.subject_item_attachment);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();

        int floor = article.getPosition();
        String id = article.getUser().getId();
        String name = article.getUser().getUserName();
        String time = Utils.getDate(article.getPostTime());
        String content = article.getContent();
        String portrait = article.getUser().getFaceUrl();
        String format = "";

        viewHolder.id.setText(id);
        viewHolder.content.setText(content);

        if (floor == 0) {
            viewHolder.floor.setText(mActivity.getResources().getString(
                    R.string.subject_item_lz));
        } else if (floor == 1) {
            viewHolder.floor.setText(mActivity.getResources().getString(
                    R.string.subject_item_first));
        } else {
            format = mActivity.getResources().getString(
                    R.string.subject_item_floor);
            viewHolder.floor.setText(String.format(format, floor));
        }

        format = mActivity.getResources().getString(R.string.subject_item_info);
        viewHolder.info.setText(String.format(format, id, name, time));

        mImageLoader.displayImage(portrait, viewHolder.portrait,
                mDisplayListener);

        List<SingleAttachment> images = article.getAttachment().getImages();
        List<SingleAttachment> files = article.getAttachment().getFiles();

        if (images.size() > 0) {
            viewHolder.image.setVisibility(View.VISIBLE);
            String url = images.get(0).getThumbnailMiddle()
                    + "?appkey=78e223c052793f0b";
            mImageLoader.displayImage(url, viewHolder.image, mDisplayListener);
        } else {
            viewHolder.image.setVisibility(View.GONE);
        }

        if (files.size() > 0) {
            viewHolder.attachment.setVisibility(View.VISIBLE);
        } else {
            viewHolder.attachment.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class ViewHolder {

        public TextView id;
        public TextView floor;
        public TextView info;
        public TextView content;
        public TextView attachment;

        public ImageView portrait;
        public ImageView image;

    }

    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

}
