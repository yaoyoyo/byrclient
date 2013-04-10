package haitong.yao.byrclient.adapters;

import haitong.yao.byrclient.R;
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

public class ToptenListAdapter extends BaseAdapter {

    private List<Article> mArticles = new ArrayList<Article>();

    private LayoutInflater mInflater;

    public ToptenListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setContent(ArrayList<Article> articles) {
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
            convertView = mInflater.inflate(R.layout.view_topten_item, null);
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
            viewHolder.board.setText("From: " + article.getBoardName());
            if (article.getHasAttachment()) {
                viewHolder.hasAttachment.setVisibility(View.VISIBLE);
            } else {
                viewHolder.hasAttachment.setVisibility(View.GONE);
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
