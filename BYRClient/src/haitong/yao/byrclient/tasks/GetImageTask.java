package haitong.yao.byrclient.tasks;

import haitong.yao.byrclient.cache.ImageCache;
import haitong.yao.byrclient.net.NetUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public final class GetImageTask extends AbsTask {

    private ImageCache cache = ImageCache.getInstance();

    public GetImageTask(Context context, String url,
            ITaskFinishListener listener) {
        mContext = context;
        mUrl = url;
        mListener = listener;
    }

    @Override
    public void execute() {
        new GetImage().execute(mUrl);
    }

    private class GetImage extends AsyncTask<String, Object, Object> {

        @Override
        protected Object doInBackground(String... params) {
            Bitmap image = cache.getLocalImage(mUrl);
            if (null != image) {
                return image;
            }
            image = NetUtil.getBitmapFromUrl(mContext, mUrl);
            return image;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (null != mListener) {
                if (null != result && result instanceof Bitmap) {
                    cache.addLocalImage(mUrl, (Bitmap) result);
                }
                mListener.onTaskFinished(GetImageTask.this, result);
            }
        }
    }

}
