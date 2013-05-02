package haitong.yao.byrclient;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class StartActivity extends NoTitleActivity {

	private ImageView mLogo;
	private Handler mDelayHandler;

	@Override
	protected void onStart() {
		super.onStart();
		mDelayHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(StartActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}, 1000);
	}

	@Override
	protected void init(Bundle savedInstanceState) {

		setContentView(R.layout.act_start);
		mDelayHandler = new Handler();
		findViewsById();
	}

	@Override
	protected void findViewsById() {

		mLogo = (ImageView) findViewById(R.id.start_logo);
		mLogo.setImageDrawable(getResources()
				.getDrawable(R.drawable.start_logo));

	}

	@Override
	protected void setListeners() {

	}

}
