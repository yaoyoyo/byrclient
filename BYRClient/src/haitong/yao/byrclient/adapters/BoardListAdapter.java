package haitong.yao.byrclient.adapters;

import haitong.yao.byrclient.R;
import haitong.yao.byrclient.models.Board;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BoardListAdapter extends BaseAdapter {

	private List<Board> mBoards = new ArrayList<Board>();

	private LayoutInflater mInflater;

	public BoardListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	public void setContent(List<Board> boards) {
		mBoards = boards;
	}

	@Override
	public int getCount() {
		if (null == mBoards) {
			return 0;
		}
		return mBoards.size();
	}

	@Override
	public Board getItem(int position) {
		if (null == mBoards || getCount() == 0 || position > mBoards.size()) {
			return null;
		}
		return mBoards.get(position);
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

		Board board = getItem(position);
		ViewHolder viewHolder = null;
		if (null == convertView || null == convertView.getTag()) {
			convertView = mInflater.inflate(R.layout.view_favourite_item, null);
			viewHolder = new ViewHolder();
			viewHolder.boardName = (TextView) convertView
					.findViewById(R.id.favourite_item_name);
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		try {
			String description = board.getDescription();
			description = description.replace('·', '\n');
			description = description.replace('，', '\n');
			viewHolder.boardName.setText(description);
			TextPaint tp = viewHolder.boardName.getPaint();
			tp.setFakeBoldText(true);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return convertView;
	}

	private class ViewHolder {
		TextView boardName;
	}

}
