package haitong.yao.byrclient.views;

import haitong.yao.byrclient.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

public final class ListFunctionBar extends FrameLayout implements
		OnClickListener {

	private ListBarClickListener mClickListener;

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.list_function_bar_home:
			mClickListener.home();
			break;
		case R.id.list_function_bar_refresh:
			mClickListener.refresh();
			break;
		case R.id.list_function_bar_goto:
			mClickListener.turnto();
			break;
		case R.id.list_function_bar_next_page:
			mClickListener.next();
			break;
		default:
			break;
		}

	};

	public ListFunctionBar(Context context) {
		super(context);
		getView();
	}

	public ListFunctionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		getView();
	}

	private void getView() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.view_list_function_bar, this);
		findViewById(R.id.list_function_bar_home).setOnClickListener(this);
		findViewById(R.id.list_function_bar_refresh).setOnClickListener(this);
		findViewById(R.id.list_function_bar_goto).setOnClickListener(this);
		findViewById(R.id.list_function_bar_next_page).setOnClickListener(this);
	}

	public void setBarClickListener(ListBarClickListener listener) {
		mClickListener = listener;
	}

	public void setRefreshText(String text) {
		((TextView) findViewById(R.id.list_function_bar_refresh)).setText(text);
	}

	public interface ListBarClickListener {

		public void home();

		public void refresh();

		public void turnto();

		public void next();
	}

}
