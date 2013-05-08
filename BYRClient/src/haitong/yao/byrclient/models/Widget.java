package haitong.yao.byrclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * 元数据 Widget
 * 
 * @author Mr.Yao
 * 
 */
public class Widget implements Serializable {

    private static final long serialVersionUID = 8984390422201483703L;

    private String name; // widget标识
    private String title; // widget标题
    private int time; // 上次修改时间

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public static Widget parseWidget(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Widget widget = new Widget();
        JSONObject obj;
        try {
            obj = new JSONObject(json);
            widget.setName(obj.optString("name"));
            widget.setTitle(obj.optString("title"));
            widget.setTime(obj.optInt("time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return widget;
    }

}
