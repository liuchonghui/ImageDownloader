package tools.android.imagedownloader;

/**
 * Created by liuchonghui on 16/8/1.
 */
public interface ImageLoadListener {
    void onImageLoadStart(String url);
    void onImageLoadCancel(String url);
    void onImageLoadProgress(String url, int progress);
    void onImageLoadSuccess(String url, String path);
    void onImageLoadFailure(String url, String message);
    void onImageLoadClear(boolean success, String url, String path);
}
