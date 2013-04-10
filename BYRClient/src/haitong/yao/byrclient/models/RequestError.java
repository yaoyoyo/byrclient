package haitong.yao.byrclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 元数据 错误信息
 * 
 * @author Mr.Yao
 * 
 */
public class RequestError implements Serializable {

    private static final long serialVersionUID = -3465768113411700365L;

    private String request;
    private String code;
    private String msg;

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public static RequestError parseRequestError(String json) {
        RequestError requestError = new RequestError();
        JSONObject obj;
        try {
            obj = new JSONObject(json);
            requestError.setRequest(obj.optString("request"));
            requestError.setCode(obj.optString("code"));
            requestError.setMsg(obj.optString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestError;
    }

}
