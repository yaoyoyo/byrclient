package haitong.yao.byrclient.adapters;

import haitong.yao.byrclient.R;
import haitong.yao.byrclient.models.Article;
import haitong.yao.byrclient.models.SingleAttachment;
import haitong.yao.byrclient.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SubjectAdapter extends BaseAdapter {

    private List<Article> mArticles = new ArrayList<Article>();
    private Context mContext;
    private LayoutInflater mInflater;

    public SubjectAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
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
            convertView = mInflater.inflate(R.layout.view_subject_item, null);
            viewHolder = new ViewHolder();
            viewHolder.id = (TextView) convertView
                    .findViewById(R.id.subject_item_id);
            viewHolder.portrait = (ImageView) convertView
                    .findViewById(R.id.subject_item_protrait);
            viewHolder.floor = (TextView) convertView
                    .findViewById(R.id.subject_item_floor);
            viewHolder.info = (TextView) convertView
                    .findViewById(R.id.subject_item_info);
            viewHolder.content = (TextView) convertView
                    .findViewById(R.id.subject_item_content);

            LinearLayout attachmentsLayout = (LinearLayout) convertView
                    .findViewById(R.id.subject_item_attachments);
            LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            int imageSize = article.getAttachment().getImages().size();
            int fileSize = article.getAttachment().getFiles().size();
            for (int i = 0; i < imageSize; i++) {
                viewHolder.imgs[i] = new ImageView(mContext);
                attachmentsLayout.addView(viewHolder.imgs[i], layoutParams);
            }
            for (int i = 0; i < fileSize; i++) {
                viewHolder.files[i] = new TextView(mContext);
                attachmentsLayout.addView(viewHolder.files[i], layoutParams);
            }

            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        try {
            viewHolder.id.setText(article.getUser().getId());
            int floor = article.getPosition();
            if (floor == 0) {
                viewHolder.floor.setText("楼主");
            } else if (floor == 1) {
                viewHolder.floor.setText("沙发");
            } else {
                viewHolder.floor.setText(floor + "楼");
            }
            viewHolder.info.setText("用户：" + article.getUser().getId() + "("
                    + article.getUser().getUserName() + ")" + "\n发表时间："
                    + Utils.getDate(article.getPostTime()));
            viewHolder.content.setText(article.getContent());
            Linkify.addLinks(viewHolder.content, Linkify.ALL);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private class ViewHolder {

        TextView id;
        ImageView portrait;
        TextView floor;
        TextView info;
        TextView content;
        ImageView[] imgs;
        TextView[] files;
    }

}
