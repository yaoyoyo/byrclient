package haitong.yao.byrclient.adapters;

import haitong.yao.byrclient.R;
import haitong.yao.byrclient.cache.ReadItems;
import haitong.yao.byrclient.models.Article;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleListAdapter extends BaseAdapter {

    public static final int TYPE_NORMAL = 0x01;
    public static final int TYPE_SPECIAL = 0x02;

    private List<Article> mArticles = new ArrayList<Article>();

    private LayoutInflater mInflater;

    private int mType;

    public ArticleListAdapter(Context context, int type) {
        mInflater = LayoutInflater.from(context);
        mType = type;
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
            convertView = mInflater.inflate(R.layout.view_article_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView
                    .findViewById(R.id.topten_item_title);
            viewHolder.board = (TextView) convertView
                    .findViewById(R.id.topten_item_board);
            viewHolder.hasAttachment = (ImageView) convertView
                    .findViewById(R.id.topten_item_has_attachment);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        try {
            viewHolder.title.setText(article.getTitle());
            if (mType == TYPE_NORMAL) {
                viewHolder.board.setText("By: " + article.getUser().getId());
            } else if (mType == TYPE_SPECIAL) {
                viewHolder.board.setText("From: " + article.getUser().getId());
            }
            if (article.getHasAttachment()) {
                viewHolder.hasAttachment.setVisibility(View.VISIBLE);
            } else {
                viewHolder.hasAttachment.setVisibility(View.GONE);
            }
            if (ReadItems.isArticleRead(article.getId())) {
                viewHolder.title.setTextColor(0xFFFFFFFF);
                viewHolder.board.setTextColor(0xFFFFFFFF);
            } else {
                viewHolder.title.setTextColor(0xFFDDDDDD);
                viewHolder.board.setTextColor(0xFFDDDDDD);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private class ViewHolder {

        TextView title;
        TextView board;
        ImageView hasAttachment;
    }

}
