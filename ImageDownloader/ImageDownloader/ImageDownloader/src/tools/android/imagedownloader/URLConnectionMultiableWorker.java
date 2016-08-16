package tools.android.imagedownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Worker of URLConnection with multithreaded
 *
 * Created by liuchonghui on 16/8/9.
 */
public class URLConnectionMultiableWorker implements ImageDownloadWorker {
    protected String url, dir, key;
    protected int bufferSize = 4096;
    protected int executorNumber = 1;
    protected ExecutorService executor;
    protected long totalSize = -1;

    public URLConnectionMultiableWorker(String url, String dir, String key) {
        this.url = url;
        this.dir = dir;
        this.key = key;
    }

    @Override
    public void run() {
        int requsetTimes = 0;
        RandomAccessFile randomAccessFile = null;
        while (requsetTimes >= 0 && requsetTimes < 3) {
            totalSize = getDownloadFileSize(url);
            if (totalSize < 0) {
                requsetTimes++;
                ImageDownloadManager.getInstance().notifyDownloadFailure(url);
                continue;
            }
            requsetTimes = -1;
        }
        if (totalSize < 0) {
            ImageDownloadManager.getInstance().notifyDownloadClear(false, url, null);
            return;
        }
        ImageDownloadManager.getInstance().notifyDownloadStart(url);
        try {
            File target = new File(dir, key);
            if (target.exists()) {
                target.delete();
            }
            randomAccessFile = new RandomAccessFile(target, "rwd");
            randomAccessFile.setLength(totalSize);
            randomAccessFile.close();
            randomAccessFile = null;

            if (executor == null) {
                if (totalSize > 1000000) {
                    executorNumber = 2; // max is 2
                }
                executor = Executors.newFixedThreadPool(executorNumber, new ThreadFactory() {
                    public Thread newThread(Runnable runnable) {
                        Thread thread = new Thread(runnable,
                                "IDW download-worker");
                        thread.setPriority(Thread.MAX_PRIORITY - 2);
                        return thread;
                    }
                });
            }
            long block = (totalSize + executorNumber - 1) / executorNumber;
            for (int i = 0; i < executorNumber; i++) {
                long startPos = block * i;
                long endPos = block * (i + 1) - 1;
                executor.submit(new SingleDownloadRunnable(target.getAbsolutePath(),
                        totalSize, startPos, endPos));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected long completeSize = 0;
    protected int originProgress = 0;

    protected void notifyDownloadProgress(final String url, final long singleLength) {
        completeSize += singleLength;
        int progress = (int) (((double) (completeSize) / totalSize) * 100);
        if (progress - originProgress >= 1) {
            originProgress = progress;
            ImageDownloadManager.getInstance().notifyDownloadProgress(url, progress);
        }
    }

    protected long allBlock = 0;

    protected void notifyDownloadBlockComplete(long startPos, long endPos) {
        allBlock = allBlock + (endPos - startPos + 1);
        if (allBlock >= totalSize) {
            ImageDownloadManager.getInstance().notifyDownloadSuccess(url,
                    new File(dir, key).getAbsolutePath());
        }
    }

    protected class SingleDownloadRunnable implements Runnable {
        RandomAccessFile randomAccessFile = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        String filePath;
        long totalSize, startPos, endPos;

        public SingleDownloadRunnable(String filePath, long totalSize, long startPos, long endPos) {
            this.filePath = filePath;
            this.totalSize = totalSize;
            this.startPos = startPos;
            this.endPos = endPos;
        }

        @Override
        public void run() {
            try {
                URL downloadUrl = new URL(url);
                conn = (HttpURLConnection) downloadUrl.openConnection();
                conn.setConnectTimeout(2000);
                conn.setReadTimeout(2000);
                conn.setRequestProperty("Range", "bytes=" + startPos + "-" +
                        endPos);
                is = conn.getInputStream();
                randomAccessFile = new RandomAccessFile(filePath, "rwd");
                randomAccessFile.seek(startPos);
                byte[] buffer = new byte[bufferSize];
                int length = -1;
                while ((length = is.read(buffer)) != -1) {
                    randomAccessFile.write(buffer, 0, length);
                    notifyDownloadProgress(url, length);
                }

                notifyDownloadBlockComplete(startPos, endPos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
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
