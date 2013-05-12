package haitong.yao.byrclient.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.text.TextUtils;

public class ImageCache {

    private static ImageCache instance = new ImageCache();

    private Map<String, SoftReference<Bitmap>> localImages = new HashMap<String, SoftReference<Bitmap>>();

    private ImageCache() {

    }

    public static ImageCache getInstance() {
        return instance;
    }

    public Bitmap getLocalImage(String url) {
        Bitmap image = null;
        SoftReference<Bitmap> sf = localImages.get(url);
        if (null != sf) {
            image = sf.get();
        }
        return image;
    }

    public void addLocalImage(String url, Bitmap image) {
        if (TextUtils.isEmpty(url) || null == image) {
            return;
        }
        if (localImages.containsKey(url)) {
            return;
        }
        localImages.put(url, new SoftReference<Bitmap>(image));
    }

    public void removeLocalImage(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (localImages.containsKey(url)) {
            localImages.remove(url);
        }
    }

    public void removeLocalImages(List<String> urls) {
        if (null == urls) {
            return;
        }
        for (String url : urls) {
            removeLocalImage(url);
        }
    }

    public void clearCache() {
        for (String key : localImages.keySet()) {
            removeLocalImage(key);
        }
    }

}
