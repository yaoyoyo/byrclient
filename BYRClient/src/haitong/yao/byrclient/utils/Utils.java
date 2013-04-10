package haitong.yao.byrclient.utils;

import haitong.yao.byrclient.models.User;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public final class Utils {

    public static final String KEY_USER_TOKEN = "local_user_token";
    public static final String KEY_USERNAME = "local_username";
    public static final String KEY_PASSWORD = "local_userpassword";

    public static final String USER_ID = "user_id";
    public static final String USER_PORTRAIT = "user_portrait";
    public static final String USER_GENDER = "user_gender";
    public static final String USER_SIGN = "user_sign";
    public static final String USER_POST_COUNT = "user_post_count";
    public static final String USER_LOGIN_COUNT = "user_login_count";
    public static final String USER_LAST_LOGIN_IP = "user_last_login_ip";
    public static final String USER_LAST_LOGIN_TIME = "user_last_login_time";

    public static final SimpleDateFormat BYR_DATE_FORMAT = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");

    private static final String PREF_NAME = "BYRClient_prefs";

    private static SharedPreferences mPreferences;

    public synchronized static void clearPrefs(Context context) {
        mPreferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        Editor editor = mPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public synchronized static void saveContent(Context context, String key,
            String value) {
        mPreferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public synchronized static String getContent(Context context, String key) {
        mPreferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        String value = mPreferences.getString(key, null);
        return value;
    }

    public synchronized static void saveUser(Context context, User user) {
        if (null == user) {
            return;
        }
        mPreferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        Editor editor = mPreferences.edit();
        editor.putString(USER_ID, user.getId());
        editor.putString(USER_PORTRAIT, user.getFaceUrl());
        editor.putString(USER_GENDER, user.getGender());
        editor.putString(USER_SIGN, user.getAstro());
        editor.putInt(USER_POST_COUNT, user.getPostCount());
        editor.putInt(USER_LOGIN_COUNT, user.getLoginCount());
        editor.putString(USER_LAST_LOGIN_IP, user.getLastLoginIp());
        editor.putInt(USER_LAST_LOGIN_TIME, user.getLastLoginTime());
        editor.commit();
    }

    public synchronized static User getUser(Context context) {
        User user = new User();
        mPreferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        user.setId(mPreferences.getString(USER_ID, null));
        user.setFaceUrl(mPreferences.getString(USER_PORTRAIT, null));
        user.setGender(mPreferences.getString(USER_GENDER, null));
        user.setAstro(mPreferences.getString(USER_SIGN, null));
        user.setPostCount(mPreferences.getInt(USER_POST_COUNT, 0));
        user.setLoginCount(mPreferences.getInt(USER_LOGIN_COUNT, 0));
        user.setLastLoginIp(mPreferences.getString(USER_LAST_LOGIN_IP, null));
        user.setLastLoginTime(mPreferences.getInt(USER_LAST_LOGIN_TIME, 0));
        return user;
    }

    // public static String ToDBC(String input) {
    // char[] c = input.toCharArray();
    // for (int i = 0; i < c.length; i++) {
    // if (c[i] == 12288) {
    // c[i] = (char) 32;
    // continue;
    // }
    // if (c[i] > 65280 && c[i] < 65375) {
    // c[i] = (char) (c[i] - 65248);
    // }
    // }
    // return new String(c);
    // }

}
