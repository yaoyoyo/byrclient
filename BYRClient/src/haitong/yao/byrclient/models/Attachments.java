package haitong.yao.byrclient.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * 元数据 附件
 * 
 * @author Mr.Yao
 * 
 */
public class Attachments implements Serializable {

    private static final long serialVersionUID = 8505905510467509801L;

    private String remain_space; // 剩余空间大小
    private int remain_count; // 剩余附件个数

    private List<SingleAttachment> images = new ArrayList<SingleAttachment>();
    private List<SingleAttachment> files = new ArrayList<SingleAttachment>();

    public void setRemainSpace(String remain_space) {
        this.remain_space = remain_space;
    }

    public String getRemainSpace() {
        return remain_space;
    }

    public void setRemainCount(int remain_count) {
        this.remain_count = remain_count;
    }

    public int getRemainCount() {
        return remain_count;
    }

    public List<SingleAttachment> getImages() {
        if (null == images) {
            images = new ArrayList<SingleAttachment>();
        }
        return images;
    }

    public void setImages(List<SingleAttachment> images) {
        this.images = images;
    }

    public List<SingleAttachment> getFiles() {
        if (null == files) {
            files = new ArrayList<SingleAttachment>();
        }
        return files;
    }

    public void setFiles(List<SingleAttachment> files) {
        this.files = files;
    }

    public static Attachments parseAttachments(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Attachments attachments = new Attachments();
        JSONObject obj;
        try {
            obj = new JSONObject(json);
            JSONArray tempArray = obj.optJSONArray("array");
            if (null != tempArray) {
                int size = tempArray.length();
                for (int i = 0; i < size; i++) {
                    JSONObject tempObj = tempArray.optJSONObject(i);
                    String tempJsonString = tempObj.toString();
                    SingleAttachment tempAttachment = SingleAttachment
                            .parseSingleAttachment(tempJsonString);
                    if (tempAttachment.isImg()) {
                        attachments.getImages().add(tempAttachment);
                    } else {
                        attachments.getFiles().add(tempAttachment);
                    }
                }
            }
            attachments.setRemainSpace(obj.optString("remain_space"));
            attachments.setRemainCount(obj.optInt("remain_count"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return attachments;
    }
}
