package haitong.yao.byrclient.adapters;

import haitong.yao.byrclient.R;
import haitong.yao.byrclient.models.Mail;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MailListAdapter extends BaseAdapter {

    private List<Mail> mMails = new ArrayList<Mail>();

    private LayoutInflater mInflater;

    public MailListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setContent(ArrayList<Mail> mails) {
        mMails = mails;
    }

    @Override
    public int getCount() {
        if (null == mMails) {
            return 0;
        }
        return mMails.size();
    }

    @Override
    public Mail getItem(int position) {
        if (null == mMails || getCount() == 0 || position > mMails.size()) {
            return null;
        }
        return mMails.get(position);
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

        Mail mail = getItem(position);
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
            viewHolder.title.setText(mail.getTitle());
            viewHolder.board.setText("From: " + mail.getUser().getId());
            if (mail.getHasAttachment()) {
                viewHolder.hasAttachment.setVisibility(View.VISIBLE);
            } else {
                viewHolder.hasAttachment.setVisibility(View.GONE);
            }
            if (!mail.getIsRead()) {
                viewHolder.title.setTextColor(0xFFFF4500);
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
