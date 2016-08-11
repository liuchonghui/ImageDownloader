package com.mfashiongallery.express.download;

import android.text.TextUtils;

import com.android.overlay.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Worker of URLConnection
 *
 * Created by liuchonghui on 16/8/9.
 */
public class URLConnectionWorker implements ImageDownloadWorker {
    protected String url, dir, key;
    protected int requsetTimes = 0;
    protected int bufferSize = 4096;

    public URLConnectionWorker(String url, String dir, String key) {
        this.url = url;
        this.dir = dir;
        this.key = key;
    }

    @Override
    public void run() {
        RandomAccessFile randomAccessFile = null;
        InputStream is = null;
        HttpURLConnection conn = null;

        while (requsetTimes >= 0 && requsetTimes < 3) {
            long totalSize = getDownloadFileSize(url);
            LogUtils.d("DOWN", "getDownLoadFileSize " + key + " " + totalSize);
            if (totalSize < 0) {
                requsetTimes++;
                ImageDownloadManager.getInstance().notifyDownloadFailure(url);
                continue;
            }
            String path = isFileExist(dir, key, totalSize);
            //如果有此文件返回
            if (!TextUtils.isEmpty(path)) {
                ImageDownloadManager.getInstance().notifyDownloadSuccess(url, path);
                return;
            }
            long completeSize = getlocalCacheFileSize(dir, key);
            LogUtils.d("DOWN", "getlocalCacheFileSize " + key + " " + completeSize);
            ImageDownloadManager.getInstance().notifyDownloadStart(url);
            try {
                URL downloadUrl = new URL(url);
                conn = (HttpURLConnection) downloadUrl.openConnection();
                conn.setConnectTimeout(2000);
                conn.setReadTimeout(2000);
                conn.setRequestProperty("Range", "bytes=" + completeSize + "-" +
                        totalSize);
                is = conn.getInputStream();
                File target = new File(dir, key);
                randomAccessFile = new RandomAccessFile(target.getAbsolutePath(), "rwd");
                randomAccessFile.seek(completeSize);
                byte[] buffer = new byte[bufferSize];
                int length = -1;
                int origin_percent = 0;
                while ((length = is.read(buffer)) != -1) {
                    randomAccessFile.write(buffer, 0, length);
                    completeSize += length;
                    int progress = (int) (((double) (completeSize) / totalSize) * 100);
                    if (progress - origin_percent >= 1) {
                        origin_percent = progress;
                        ImageDownloadManager.getInstance().notifyDownloadProgress(url, progress);
                    }
                }
                requsetTimes = -1;
                ImageDownloadManager.getInstance().notifyDownloadSuccess(url, target.getAbsolutePath());

            } catch (Exception e) {
                e.printStackTrace();
                if (requsetTimes >= 2) {
                    ImageDownloadManager.getInstance().notifyDownloadFailure(url);
                }
            } finally {
                requsetTimes++;
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (conn != null) {
                    try {
                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public long getDownloadFileSize(String inputUrl) {
        HttpURLConnection conn = null;
        long size = -1;
        try {
            URL url = new URL(inputUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            size = conn.getContentLength();
        } catch (Exception e) {
            e.printStackTrace();
            size = -1;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return size;
    }

    @Override
    public String isFileExist(String dir, String fileName, long targetSize) {
        File file = new File(dir, fileName);
        if (file != null && file.exists()) {
            long fileSize = file.length();
            if (targetSize == fileSize) {
                return file.getPath();
            }
        }
        return null;
    }

    @Override
    public long getlocalCacheFileSize(String dir, String fileName) {
        long fileSize = 0;
        File dF = new File(dir, fileName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(dF);
            fileSize = fis.available();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return fileSize;
    }
}
