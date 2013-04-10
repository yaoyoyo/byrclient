package haitong.yao.byrclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 元数据 分区
 * 
 * @author Mr.Yao
 * 
 */
public class Section implements Serializable {

    private static final long serialVersionUID = -7743120210511170455L;

    private String name; // 分区名称
    private String description; // 分区表述
    private boolean is_root; // 是否是根分区
    private String parent; // 该分区所属根分区名称

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

    public void setIsRoot(boolean is_root) {
        this.is_root = is_root;
    }

    public boolean getIsRoot() {
        return is_root;
    }

    public static Section parseSection(String json) {
        Section section = new Section();
        JSONObject obj;
        try {
            obj = new JSONObject(json);
            section.setName(obj.optString("name"));
            section.setDescription(obj.optString("description"));
            section.setIsRoot(obj.optBoolean("is_root"));
            section.setParent(obj.optString("parent"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return section;
    }
}
