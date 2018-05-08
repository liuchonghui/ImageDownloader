package tool.imagedownloader.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tool.imagedownloader.test.R;
import tools.android.simpledownloader.DownloadAdapter;
import tools.android.simpledownloader.SimpleDownloadManager;

public class OneButtonActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_button_activity);

        findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PPP", "click container");
                Toast.makeText(getApplicationContext(), "click container", Toast.LENGTH_LONG);
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PPP", "click button");
                String url = "https://gist.github.com/liuchonghui/b9757b65748eb42548213ec7b9572116/raw/5c236aecbae3006700e6a98336818bcab2cca265/1.6_20.bobo.0c3607dcac07e3f64541bdda58358b4f.zip";
                Toast.makeText(getApplicationContext(), "click button", Toast.LENGTH_LONG);
                SimpleDownloadManager.Companion.get().downloadSimpleFile(getApplicationContext(), "54321", ".x", null, url, new DownloadAdapter() {
                    @Override
                    public void onDownloadStart(String url) {
                        Log.d("PPP", "onDownloadStart");
                    }
                    @Override
                    public void onDownloadFailure(String url, String message) {
                        Log.d("PPP", "onDownloadFailure|" + message);
                    }
                    @Override
                    public void onDownloadProgress(String url, int progress) {
                        Log.d("PPP", "onDownloadProgress|" + progress);
                    }
                    @Override
                    public void onDownloadSuccess(String url, String path) {
                        Log.d("PPP", "onDownloadSuccess|" + path);
                    }
                });
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                String retJson = null;
                try {
                    Request request = new Request.Builder().url("http://awild.space/g/fetch_plugin").build();
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();
                    retJson = response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (retJson != null && retJson.length() > 0) {
                    final String content = retJson;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("PPP", "click button|" + content);
                        }
                    });
                }
            }
        }).start();
    }
}
