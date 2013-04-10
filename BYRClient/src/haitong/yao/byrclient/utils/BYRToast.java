package haitong.yao.byrclient.utils;

import android.content.Context;
import android.widget.Toast;

public final class BYRToast {

	public static void showShortToast(Context context, String toast) {
		Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(Context context, String toast) {
		Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
	}

	public static void showShortToast(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
	}
}
