package haitong.yao.byrclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * 元数据 信箱
 * 
 * @author Mr.Yao
 * 
 */
public class Mailbox implements Serializable {

    private static final long serialVersionUID = 3261228133557639747L;

    private boolean new_mail; // 是否有新邮件
    private boolean full_mail; // 信箱是否已满
    private String space_used; // 信箱已用空间
    private boolean can_send; // 当前用户是否能发信

    public void setNewMail(boolean new_mail) {
        this.new_mail = new_mail;
    }

    public boolean getNewMail() {
        return new_mail;
    }

    public void setFullMail(boolean full_mail) {
        this.full_mail = full_mail;
    }

    public boolean getFullMail() {
        return full_mail;
    }

    public void setCanSend(boolean can_send) {
        this.can_send = can_send;
    }

    public boolean getCanSend() {
        return can_send;
    }

    public void setSpaceUsed(String space_used) {
        this.space_used = space_used;
    }

    public String getSpaceUsed() {
        return space_used;
    }

    public static Mailbox parseMailbox(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Mailbox mailbox = new Mailbox();
        JSONObject obj;
        try {
            obj = new JSONObject(json);
            mailbox.setNewMail(obj.optBoolean("new_mail"));
            mailbox.setFullMail(obj.optBoolean("full_mail"));
            mailbox.setCanSend(obj.optBoolean("can_send"));
            mailbox.setSpaceUsed(obj.optString("space_used"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mailbox;
    }

}
