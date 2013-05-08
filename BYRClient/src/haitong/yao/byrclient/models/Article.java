package haitong.yao.byrclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * 元数据 文章
 * 
 * @author Mr.Yao
 * 
 */
public class Article implements Serializable {

    private static final long serialVersionUID = -2997593770040231598L;

    private int id; // 文章id
    private int group_id; // 该文章所属主题的id
    private int reply_id; // 该文章回复文章的id
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
    private String content; // 文章内容
    private Attachments attachment; // 文章附件列表，这是一个附件元数据
    private int previous_id; // 该文章的前一篇文章id 只存在于/article/:board/:id中
    private int next_id; // 该文章的后一篇文章id 只存在于/article/:board/:id中
    private int threads_previous_id; // 该文章同主题前一篇文章id 只存在于/article/:board/:id中
    private int threads_next_id; // 该文章同主题后一篇文章id 只存在于/article/:board/:id中
    private int reply_count; // 该主题回复文章数
    private String last_reply_user_id; // 该文章最后回复者的id
    private int last_reply_time; // 该文章最后回复的时间 unxitmestamp

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

    public void setPreviousId(int previous_id) {
        this.previous_id = previous_id;
    }

    public int getPreviousId() {
        return previous_id;
    }

    public void setNextId(int next_id) {
        this.next_id = next_id;
    }

    public int getNextId() {
        return next_id;
    }

    public void setThreadsPreviousId(int threads_previous_id) {
        this.threads_previous_id = threads_previous_id;
    }

    public int getThreadsPreviousId() {
        return threads_previous_id;
    }

    public void setThreadsNextId(int threads_next_id) {
        this.threads_next_id = threads_next_id;
    }

    public int getThreadsNextId() {
        return threads_next_id;
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
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

    public void setAttachment(Attachments attachment) {
        this.attachment = attachment;
    }

    public Attachments getAttachment() {
        if (null == attachment) {
            attachment = new Attachments();
        }
        return attachment;
    }

    public static Article parseArticle(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Article article = new Article();
        JSONObject obj;
        try {
            obj = new JSONObject(json);
            article.setId(obj.optInt("id"));
            article.setGroupId(obj.optInt("group_id"));
            article.setReplyId(obj.optInt("reply_id"));
            article.setFlag(obj.optString("flag"));
            article.setPosition(obj.optInt("position"));
            article.setIsTop(obj.optBoolean("is_top"));
            article.setIsSubject(obj.optBoolean("is_subject"));
            article.setHasAttachment(obj.optBoolean("has_attachment"));
            article.setIsAdmin(obj.optBoolean("is_admin"));
            article.setTitle(obj.optString("title"));
            String user = obj.optString("user");
            article.setUser(User.parseUser(user));
            article.setPostTime(obj.optInt("post_time"));
            article.setBoardName(obj.optString("board_name"));
            article.setContent(obj.optString("content"));
            if (obj.optBoolean("has_attachment")) {
                article.setAttachment(Attachments.parseAttachments(obj
                        .optString("attachment")));
            }
            article.setPreviousId(obj.optInt("previous_id"));
            article.setNextId(obj.optInt("next_id"));
            article.setThreadsPreviousId(obj.optInt("threads_previous_id"));
            article.setThreadsNextId(obj.optInt("threads_next_id"));
            article.setReplyCount(obj.optInt("reply_count"));
            article.setLastReplyUserId(obj.optString("last_reply_user_id"));
            article.setLastReplyTime(obj.optInt("last_reply_time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return article;
    }

}
