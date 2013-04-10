package haitong.yao.byrclient.views;

import haitong.yao.byrclient.R;
import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public final class TitleBar extends FrameLayout implements OnClickListener {

	private Button mLeft;
	private TextView mMiddle;
	private Button mRight;

	private TitleBarClickListener mClickListener;

	public TitleBar(Context context) {
		super(context);
		getView();
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		getView();
	}

	private void getView() {
		LayoutInflater.from(getContext()).inflate(R.layout.view_titlebar, this);
		mLeft = (Button) findViewById(R.id.title_left);
		mMiddle = (TextView) findViewById(R.id.title_middle);
		TextPaint tp = mMiddle.getPaint();
		tp.setFakeBoldText(true);
		mRight = (Button) findViewById(R.id.title_right);
	}

	public void setLeft(boolean isShown, String text, int resId) {
		if (isShown) {
			mLeft.setVisibility(View.VISIBLE);
			mLeft.setText(text);
			mLeft.setBackgroundResource(resId);
			mLeft.setOnClickListener(this);
		}
	}

	public void setMiddle(String text) {
		mMiddle.setText(text);
	}

	public void setRight(boolean isShown, String text, int resId) {
		if (isShown) {
			mRight.setVisibility(View.VISIBLE);
			mRight.setText(text);
			mRight.setBackgroundResource(resId);
			mRight.setOnClickListener(this);
		}
	}

	public void setTitleBarClickListener(TitleBarClickListener listener) {
		mClickListener = listener;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.title_left:
			mClickListener.clickLeft();
			break;
		case R.id.title_right:
			mClickListener.clickRight();
			break;
		default:
			break;
		}

	};

	public interface TitleBarClickListener {

		public void clickLeft();

		public void clickRight();
	}

}
