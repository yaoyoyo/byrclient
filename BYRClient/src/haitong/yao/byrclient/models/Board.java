package haitong.yao.byrclient.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Board implements Serializable {

	private static final long serialVersionUID = 5912490810761133010L;

	private String name; // 版面名称
	private String manager; // 版主列表，以空格分隔各个id
	private String description; // 版面描述
	private String category; // 版面所属类别，原名称为class
	private String section; // 版面所属分区号
	private int post_today_count; // 今日发文总数
	private int post_threads_count; // 版面主题总数
	private int post_all_count; // 版面文章总数
	private boolean is_read_only; // 版面是否只读
	private boolean is_no_reply; // 版面是否不可回复
	private boolean allow_attachment; // 版面书否允许附件
	private boolean allow_anonymous; // 版面是否允许匿名发文
	private boolean allow_outgo; // 版面是否允许转信
	private boolean allow_post; // 当前登陆用户是否有发文/回复权限
	private int user_online_count; // 版面当前在线用户数
	private Pagination pagination;
	private List<Article> article;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManager() {
		return manager;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSection() {
		return section;
	}

	public void setPostTodayCount(int post_today_count) {
		this.post_today_count = post_today_count;
	}

	public int getPostTodayCount() {
		return post_today_count;
	}

	public void setPostThreadsCount(int post_threads_count) {
		this.post_threads_count = post_threads_count;
	}

	public int getPostThreadsCount() {
		return post_threads_count;
	}

	public void setPostAllCount(int post_all_count) {
		this.post_all_count = post_all_count;
	}

	public int getPostAllCount() {
		return post_all_count;
	}

	public void setUserOnlineCount(int user_online_count) {
		this.user_online_count = user_online_count;
	}

	public int getUserOnlineCount() {
		return user_online_count;
	}

	public void setIsReadOnly(boolean is_read_only) {
		this.is_read_only = is_read_only;
	}

	public boolean getIsReadOnly() {
		return is_read_only;
	}

	public void setIsNoReply(boolean is_no_reply) {
		this.is_no_reply = is_no_reply;
	}

	public boolean getIsNoReply() {
		return is_no_reply;
	}

	public void setAllowAttachment(boolean allow_attachment) {
		this.allow_attachment = allow_attachment;
	}

	public boolean getAllowAttachment() {
		return allow_attachment;
	}

	public void setAllowAnonymous(boolean allow_anonymous) {
		this.allow_anonymous = allow_anonymous;
	}

	public boolean getAllowAnonymous() {
		return allow_anonymous;
	}

	public void setAllowOutgo(boolean allow_outgo) {
		this.allow_outgo = allow_outgo;
	}

	public boolean getAllowOutgo() {
		return allow_outgo;
	}

	public void setAllowPost(boolean allow_post) {
		this.allow_post = allow_post;
	}

	public boolean getAllowPost() {
		return allow_post;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<Article> getArticle() {
		return article;
	}

	public void setArticle(List<Article> article) {
		this.article = article;
	}

	public static Board parseBoard(String json) {
		Board board = new Board();
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			board.setName(obj.optString("name"));
			board.setManager(obj.optString("manager"));
			board.setDescription(obj.optString("description"));
			board.setCategory(obj.optString("class"));
			board.setSection(obj.optString("section"));
			board.setPostTodayCount(obj.optInt("post_today_count"));
			board.setPostThreadsCount(obj.optInt("post_threads_count"));
			board.setPostAllCount(obj.optInt("post_all_count"));
			board.setUserOnlineCount(obj.optInt("user_online_count"));
			board.setIsReadOnly(obj.optBoolean("is_read_only"));
			board.setIsNoReply(obj.optBoolean("is_no_reply"));
			board.setAllowAnonymous(obj.optBoolean("allow_anonymous"));
			board.setAllowAttachment(obj.optBoolean("allow_attachment"));
			board.setAllowOutgo(obj.optBoolean("allow_outgo"));
			board.setAllowPost(obj.optBoolean("allow_post"));
			board.setPagination(Pagination.parsePagination(obj
					.optString("pagination")));
			JSONArray article = obj.optJSONArray("article");
			if (null != article && article.length() > 0) {
				List<Article> articles = new ArrayList<Article>();
				for (int i = 0; i < article.length(); i++) {
					articles.add(Article.parseArticle(article.getString(i)));
				}
				board.setArticle(articles);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return board;
	}

}
