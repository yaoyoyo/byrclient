package haitong.yao.byrclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

/**
 * 元数据 信件
 * 
 * @author Mr.Yao
 * 
 */
public class Mail implements Serializable {

	private static final long serialVersionUID = 9187178687019232948L;

	private int index; // 信件编号，此编号为/mail/:box/:num中的num
	private boolean is_m; // 是否标记为m
	private boolean is_read; // 是否已读
	private boolean is_reply; // 是否回复
	private boolean has_attachment; // 是否有附件
	private String title; // 信件标题
	private User user; // 发信人，此为user元数据，如果user不存在则为用户id
	private int post_time; // 发信时间
	private String box_name; // 所属信箱名
	private String content; // 信件内容 只存在于/mail/:box/:num中
	private Attachments attachment; // 信件的附件列表 只存在于/mail/:box/:num中

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setPostTime(int post_time) {
		this.post_time = post_time;
	}

	public int getPostTime() {
		return post_time;
	}

	public void setIsM(boolean is_m) {
		this.is_m = is_m;
	}

	public boolean getIsM() {
		return is_m;
	}

	public void setHasAttachment(boolean has_attachment) {
		this.has_attachment = has_attachment;
	}

	public boolean getHasAttachment() {
		return has_attachment;
	}

	public void setIsRead(boolean is_read) {
		this.is_read = is_read;
	}

	public boolean getIsRead() {
		return is_read;
	}

	public void setIsReply(boolean is_reply) {
		this.is_reply = is_reply;
	}

	public boolean getIsReply() {
		return is_reply;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setBoxName(String box_name) {
		this.box_name = box_name;
	}

	public String getBoxName() {
		return box_name;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setAttachment(Attachments attachment) {
		this.attachment = attachment;
	}

	public Attachments getAttachment() {
		return attachment;
	}

	public static Mail parseMail(String json) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		Mail mail = new Mail();
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			mail.setIndex(obj.optInt("index"));
			mail.setPostTime(obj.optInt("post_time"));
			mail.setIsM(obj.optBoolean("is_m"));
			mail.setIsRead(obj.optBoolean("is_read"));
			mail.setIsReply(obj.optBoolean("is_reply"));
			mail.setHasAttachment(obj.optBoolean("has_attachment"));
			mail.setTitle(obj.optString("title"));
			String user = obj.optString("user");
			mail.setUser(User.parseUser(user));
			mail.setBoxName(obj.optString("box_name"));
			if (obj.optBoolean("has_attachment")) {
				mail.setAttachment(Attachments.parseAttachments(obj.opt(
						"attachment").toString()));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mail;

	}
}
