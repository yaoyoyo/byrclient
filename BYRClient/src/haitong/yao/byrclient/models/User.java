package haitong.yao.byrclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 元数据 用户
 * 
 * @author Mr.Yao
 * 
 */
public class User implements Serializable {

    private static final long serialVersionUID = 5859822625507741056L;

    private String id;
    private String user_name;
    private String face_url;
    private int face_width;
    private int face_height;
    private String gender;
    private String astro;
    private int life;
    private String qq;
    private String msn;
    private String home_page;
    private String level;
    private boolean is_online;
    private int post_count;
    private int last_login_time;
    private String last_login_ip;
    private boolean is_hide;
    private boolean is_register;
    private int first_login_time;
    private int login_count;
    private boolean is_admin;
    private int stay_count;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getUserName() {
        return user_name;
    }

    public void setFaceUrl(String face_url) {
        this.face_url = face_url;
    }

    public String getFaceUrl() {
        return face_url;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setAstro(String astro) {
        this.astro = astro;
    }

    public String getAstro() {
        return astro;
    }

    public void setQQ(String qq) {
        this.qq = qq;
    }

    public String getQQ() {
        return qq;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getMsn() {
        return msn;
    }

    public void setHomePage(String home_page) {
        this.home_page = home_page;
    }

    public String getHomePage() {
        return home_page;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLastLoginIp(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public String getLastLoginIp() {
        return last_login_ip;
    }

    public void setFaceWidth(int face_width) {
        this.face_width = face_width;
    }

    public int getFaceWidth() {
        return face_width;
    }

    public void setFaceHeight(int face_height) {
        this.face_height = face_height;
    }

    public int getFaceHeight() {
        return face_height;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public void setPostCount(int post_count) {
        this.post_count = post_count;
    }

    public int getPostCount() {
        return post_count;
    }

    public void setLastLoginTime(int last_login_time) {
        this.last_login_time = last_login_time;
    }

    public int getLastLoginTime() {
        return last_login_time;
    }

    public void setFirstLoginTime(int first_login_time) {
        this.first_login_time = first_login_time;
    }

    public int getFirstLoginTime() {
        return first_login_time;
    }

    public void setLoginCount(int login_count) {
        this.login_count = login_count;
    }

    public int getLoginCount() {
        return login_count;
    }

    public void setStayCount(int stay_count) {
        this.stay_count = stay_count;
    }

    public int getStayCount() {
        return stay_count;
    }

    public void setIsOnline(boolean is_online) {
        this.is_online = is_online;
    }

    public boolean getIsOnline() {
        return is_online;
    }

    public void setIsHide(boolean is_hide) {
        this.is_hide = is_hide;
    }

    public boolean getIsHide() {
        return is_hide;
    }

    public void setIsRegister(boolean is_register) {
        this.is_register = is_register;
    }

    public boolean getIsRegister() {
        return is_register;
    }

    public void setIsAdmin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public boolean getIsAdmin() {
        return is_admin;
    }

    public static User parseUser(String json) {
        User u = new User();
        JSONObject obj;
        try {
            obj = new JSONObject(json);
            u.setAstro(obj.optString("astro"));
            u.setFaceHeight(obj.optInt("face_height"));
            u.setFaceUrl(obj.optString("face_url"));
            u.setFaceWidth(obj.optInt("face_width"));
            u.setFirstLoginTime(obj.optInt("first_login_time"));
            u.setGender(obj.optString("gender"));
            u.setHomePage(obj.optString("home_page"));
            u.setId(obj.optString("id"));
            u.setIsAdmin(obj.optBoolean("is_admin"));
            u.setIsHide(obj.optBoolean("is_hide"));
            u.setIsOnline(obj.optBoolean("is_online"));
            u.setIsRegister(obj.optBoolean("is_register"));
            u.setLastLoginIp(obj.optString("last_login_ip"));
            u.setLastLoginTime(obj.optInt("last_login_time"));
            u.setLevel(obj.optString("level"));
            u.setLife(obj.optInt("life"));
            u.setLoginCount(obj.optInt("login_count"));
            u.setMsn(obj.optString("msn"));
            u.setPostCount(obj.optInt("post_count"));
            u.setQQ(obj.optString("qq"));
            u.setStayCount(obj.optInt("stay_count"));
            u.setUserName(obj.optString("user_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
}
