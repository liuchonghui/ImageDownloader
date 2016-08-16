package tools.android.imagedownloader;

/**
 * Created by liuchonghui on 16/8/1.
 */
public abstract class ImageLoadAdapter implements ImageLoadListener {
    @Override
    public void onImageLoadStart(String url) {
    }

    @Override
    public void onImageLoadCancel(String url) {
    }

    @Override
    public void onImageLoadProgress(String url, int progress) {
    }

    @Override
    public void onImageLoadSuccess(String url, String path) {
    }

    @Override
    public void onImageLoadFailure(String url) {
    }

    @Override
    public void onImageLoadClear(boolean success, String url, String path) {
    }
}
