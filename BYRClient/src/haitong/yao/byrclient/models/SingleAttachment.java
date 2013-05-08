package haitong.yao.byrclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * 元数据 单个附件
 * 
 * @author Mr.Yao
 * 
 */
public class SingleAttachment implements Serializable {

    private static final long serialVersionUID = -2536871572874756913L;

    private String name; // 文件名
    private String url; // 文件链接，在用户空间的文件，该值为空
    private String size; // 文件大小
    private String thumbnail_small; // 小缩略图链接(宽度120px)，用户空间的文件，该值为空
    private String thumbnail_middle; // 中缩略图链接(宽度240px)，用户空间的文件，该值为空

    private boolean isImg;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setThumbnailSmall(String thumbnail_small) {
        this.thumbnail_small = thumbnail_small;
    }

    public String getThumbnailSmall() {
        return thumbnail_small;
    }

    public void setThumbnailMiddle(String thumbnail_middle) {
        this.thumbnail_middle = thumbnail_middle;
    }

    public String getThumbnailMiddle() {
        return thumbnail_middle;
    }

    public boolean isImg() {
        return isImg;
    }

    public void setIsImg(boolean isImg) {
        this.isImg = isImg;
    }

    public static SingleAttachment parseSingleAttachment(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        SingleAttachment singleAttachment = new SingleAttachment();
        JSONObject obj;
        try {
            obj = new JSONObject(json);
            String name = obj.optString("name");
            if (!TextUtils.isEmpty(name)) {
                if (name.contains(".jpg") || name.contains(".png")
                        || name.contains(".gif") || name.contains(".jpeg")) {
                    singleAttachment.setIsImg(true);
                }
            }
            singleAttachment.setName(name);
            singleAttachment.setUrl(obj.optString("url"));
            singleAttachment.setSize(obj.optString("size"));
            singleAttachment
                    .setThumbnailSmall(obj.optString("thumbnail_small"));
            singleAttachment.setThumbnailMiddle(obj
                    .optString("thumbnail_middle"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return singleAttachment;
    }
}
