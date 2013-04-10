package haitong.yao.byrclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 元数据 收藏夹
 * 
 * @author Mr.Yao
 * 
 */
public class Favourite implements Serializable {

    private static final long serialVersionUID = -7443259500869211858L;

    private int level; // 收藏夹级数，顶层收藏夹level为0
    private String description; // 收藏夹目录
    private int position; // 收藏夹目录位置，该值用于删除收藏夹目录

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Favourite parseFavourite(String json) {
        Favourite favourite = new Favourite();
        JSONObject obj;
        try {
            obj = new JSONObject(json);
            favourite.setLevel(obj.optInt("level"));
            favourite.setPosition(obj.optInt("position"));
            favourite.setDescription(obj.optString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return favourite;
    }

}
