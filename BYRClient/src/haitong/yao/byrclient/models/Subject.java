package haitong.yao.byrclient.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class Subject implements Serializable {

    private static final long serialVersionUID = -3466271850919985920L;

    private int id; // 文章id
    private String flag; // 文章标记 分别是m g ; b u o 8
    private int position; // 文章所在主题的位置或文章在默写浏览模式下的位置
    private boolean is_top; // 文章是否置顶
    private boolean is_subject; // 该文章是否是主题帖
    private boolean has_attachment; // 文章是否有附件
    private boolean is_admin; // 当前登陆用户是否对文章有管理权限 包括编辑，删除，修改附件
    private String title; // 文章标题
    private User user; // 文章发表用户，这是一个用户元数据
    private int post_time; // 文章发表时间，unixtimestamp
    private String board_name; // 所属版面名称
    private int reply_count; // 该主题回复文章数
    private String last_reply_user_id; // 该文章最后回复者的id
    private int last_reply_time; // 该文章最后回复的时间 unxitmestamp
    private Pagination pagination;
    private List<Article> article;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPostTime(int post_time) {
        this.post_time = post_time;
    }

    public int getPostTime() {
        return post_time;
    }

    public void setReplyCount(int reply_count) {
        this.reply_count = reply_count;
    }

    public int getReplyCount() {
        return reply_count;
    }

    public void setLastReplyTime(int last_reply_time) {
        this.last_reply_time = last_reply_time;
    }

    public int getLastReplyTime() {
        return last_reply_time;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setBoardName(String board_name) {
        this.board_name = board_name;
    }

    public String getBoardName() {
        return board_name;
    }

    public void setLastReplyUserId(String last_reply_user_id) {
        this.last_reply_user_id = last_reply_user_id;
    }

    public String getLastReplyUserId() {
        return last_reply_user_id;
    }

    public void setIsTop(boolean is_top) {
        this.is_top = is_top;
    }

    public boolean getIsTop() {
        return is_top;
    }

    public void setIsSubject(boolean is_subject) {
        this.is_subject = is_subject;
    }

    public boolean getIsSubject() {
        return is_subject;
    }

    public void setHasAttachment(boolean has_attachment) {
        this.has_attachment = has_attachment;
    }

    public boolean getHasAttachment() {
        return has_attachment;
    }

    public void setIsAdmin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public boolean getIsAdmin() {
        return is_admin;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        if (null == user) {
            user = new User();
        }
        return user;
    }

    public Pagination getPagination() {
        if (null == pagination) {
            pagination = new Pagination();
        }
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Article> getArticle() {
        if (null == article) {
            article = new ArrayList<Article>();
        }
        return article;
    }

    public void setArticle(List<Article> article) {
        this.article = article;
    }

    public static Subject parseArticle(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Subject subject = new Subject();
        JSONObject obj;
        try {
            obj = new JSONObject(json);
            subject.setId(obj.optInt("id"));
            subject.setFlag(obj.optString("flag"));
            subject.setPosition(obj.optInt("position"));
            subject.setIsTop(obj.optBoolean("is_top"));
            subject.setIsSubject(obj.optBoolean("is_subject"));
            subject.setHasAttachment(obj.optBoolean("has_attachment"));
            subject.setIsAdmin(obj.optBoolean("is_admin"));
            subject.setTitle(obj.optString("title"));
            String user = obj.optString("user");
            subject.setUser(User.parseUser(user));
            subject.setPostTime(obj.optInt("post_time"));
            subject.setBoardName(obj.optString("board_name"));
            subject.setReplyCount(obj.optInt("reply_count"));
            subject.setLastReplyUserId(obj.optString("last_reply_user_id"));
            subject.setLastReplyTime(obj.optInt("last_reply_time"));
            subject.setPagination(Pagination.parsePagination(obj
                    .optString("pagination")));
            JSONArray article = obj.optJSONArray("article");
            if (null != article && article.length() > 0) {
                List<Article> articles = new ArrayList<Article>();
                for (int i = 0; i < article.length(); i++) {
                    articles.add(Article.parseArticle(article.getString(i)));
                }
                subject.setArticle(articles);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subject;
    }

}
