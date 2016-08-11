package com.mfashiongallery.express.download;

/**
 * Created by liuchonghui on 16/8/9.
 */
public interface ImageDownloadWorker extends Runnable {

    long getDownloadFileSize(String inputUrl);

    String isFileExist(String dir, String fileName, long targetSize);

    long getlocalCacheFileSize(String dir, String fileName);
}
