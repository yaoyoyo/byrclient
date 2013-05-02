package haitong.yao.byrclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * 元数据 提醒
 * 
 * @author Mr.Yao
 * 
 */
public class Refer implements Serializable {

	private static final long serialVersionUID = 7908295942942811741L;

	private int index; // 提醒编号，此编号用于提醒的相关操作
	private int id; // 提醒文章的id
	private int group_id; // 提醒文章的group id
	private int reply_id; // 提醒文章的reply id
	private String board_name; // 提醒文章所在版面
	private String title; // 提醒文章的标题
	private String user; // 提醒文章的发信人，此为user元数据，如果user不存在则为用户id
	private int time; // 发出提醒的时间
	private boolean is_read; // 提醒是否已读

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setGroupId(int group_id) {
		this.group_id = group_id;
	}

	public int getGroupId() {
		return group_id;
	}

	public void setReplyId(int reply_id) {
		this.reply_id = reply_id;
	}

	public int getReplyId() {
		return reply_id;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public void setBoardName(String board_name) {
		this.board_name = board_name;
	}

	public String getBoardName() {
		return board_name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setIsRead(boolean is_read) {
		this.is_read = is_read;
	}

	public boolean getIsRead() {
		return is_read;
	}

	public static Refer parseRefer(String json) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		Refer refer = new Refer();
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			refer.setIndex(obj.optInt("index"));
			refer.setId(obj.optInt("id"));
			refer.setGroupId(obj.optInt("group_id"));
			refer.setReplyId(obj.optInt("reply_id"));
			refer.setTime(obj.optInt("time"));
			refer.setBoardName(obj.optString("board_name"));
			refer.setTitle(obj.optString("title"));
			refer.setUser(obj.optString("user"));
			refer.setIsRead(obj.optBoolean("is_read"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return refer;
	}

}
