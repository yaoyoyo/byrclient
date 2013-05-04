package haitong.yao.byrclient.models;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * 元数据 附件
 * 
 * @author Mr.Yao
 * 
 */
public class Attachments implements Serializable {

	private static final long serialVersionUID = 8505905510467509801L;

	private SingleAttachment[] array; // 文件列表
	private String remain_space; // 剩余空间大小
	private int remain_count; // 剩余附件个数

	public void setArray(SingleAttachment[] array) {
		this.array = array;
	}

	public SingleAttachment[] getArray() {
		return array;
	}

	public void setRemainSpace(String remain_space) {
		this.remain_space = remain_space;
	}

	public String getRemainSpace() {
		return remain_space;
	}

	public void setRemainCount(int remain_count) {
		this.remain_count = remain_count;
	}

	public int getRemainCount() {
		return remain_count;
	}

	public static Attachments parseAttachments(String json) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		Attachments attachments = new Attachments();
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			JSONArray tempArray = obj.optJSONArray("array");
			if (null != tempArray) {
				int size = tempArray.length();
				SingleAttachment[] tempAttachment = new SingleAttachment[] {};
				for (int i = 0; i < size; i++) {
					JSONObject tempObj = tempArray.optJSONObject(i);
					String tempJsonString = tempObj.toString();
					tempAttachment[i] = SingleAttachment
							.parseSingleAttachment(tempJsonString);
				}
				attachments.setArray(tempAttachment);
			}
			attachments.setRemainSpace(obj.optString("remain_space"));
			attachments.setRemainCount(obj.optInt("remain_count"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return attachments;
	}
}
