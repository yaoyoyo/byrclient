package haitong.yao.byrclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

/**
 * 元数据 分页
 * 
 * @author Mr.Yao
 * 
 */
public class Pagination implements Serializable {

	private static final long serialVersionUID = 2787075399886149321L;

	private int page_all_count; // 总页数
	private int page_current_count; // 当前页数
	private int item_page_count; // 每页元素个数
	private int item_all_count; // 所有元素个数

	public void setPageAllCount(int page_all_count) {
		this.page_all_count = page_all_count;
	}

	public int getPageAllCount() {
		return page_all_count;
	}

	public void setPageCurrentCount(int page_current_count) {
		this.page_current_count = page_current_count;
	}

	public int getPageCurrentCount() {
		return page_current_count;
	}

	public void setItemPageCount(int item_page_count) {
		this.item_page_count = item_page_count;
	}

	public int getItemPageCount() {
		return item_page_count;
	}

	public void setItemAllCount(int item_all_count) {
		this.item_all_count = item_all_count;
	}

	public int getItemAllCount() {
		return item_all_count;
	}

	public static Pagination parsePagination(String json) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		Log.e("haitong", "json: " + json);
		Pagination pagination = new Pagination();
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			pagination.setPageAllCount(obj.optInt("page_all_count"));
			pagination.setPageCurrentCount(obj.optInt("page_current_count"));
			pagination.setItemPageCount(obj.optInt("item_page_count"));
			pagination.setItemAllCount(obj.optInt("item_all_count"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return pagination;
	}

}
