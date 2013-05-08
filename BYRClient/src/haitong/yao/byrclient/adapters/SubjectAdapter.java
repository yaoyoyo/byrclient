package haitong.yao.byrclient.adapters;

import haitong.yao.byrclient.models.Article;
import haitong.yao.byrclient.views.SubjectItem;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SubjectAdapter extends BaseAdapter {

    private List<Article> mArticles = new ArrayList<Article>();
    private Context mContext;
    private Handler mHandler;

    public SubjectAdapter(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
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
        if (null == convertView) {
            convertView = new SubjectItem(mContext, mHandler, position);
        }
        ((SubjectItem) convertView).setArticle(article);
        return convertView;
    }

}
