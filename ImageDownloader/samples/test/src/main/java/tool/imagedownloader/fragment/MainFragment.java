package tool.imagedownloader.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

import tool.imagedownloader.activity.FeiDianActivity;
import tool.imagedownloader.activity.LuPingActivity;
import tool.imagedownloader.activity.VideoViewActivity;
import tool.imagedownloader.activity.YangLiActivity;
import tool.imagedownloader.test.R;

/**
 * @author liu_chonghui
 */
public class MainFragment extends BaseFragment {

    protected int getPageLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTransaction();

        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void startTransaction() {
        getActivity().overridePendingTransition(R.anim.push_left_in,
                R.anim.push_still);
    }

    protected void initData() {
        if (getActivity().getIntent() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (!isViewNull()) {
            return mView;
        }
        mView = inflater.inflate(getPageLayout(), container, false);
        intView(mView);
        return mView;
    }

    @SuppressLint("InflateParams")
    protected void intView(View view) {
        view.findViewById(R.id.yangli).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), YangLiActivity.class));
            }
        });
        view.findViewById(R.id.feidian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FeiDianActivity.class));
            }
        });
        view.findViewById(R.id.luping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LuPingActivity.class));
            }
        });
        view.findViewById(R.id.chajian0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.putExtra("royalMessages", "T01020978");
                intent.putExtra("from", getActivity().getPackageName()); // 数据统计用
                intent.setData(Uri.parse("mifg://fashiongallery/push_preview"));
                Log.d("DIAOQI", "strintent=" + intent.toUri(Intent.URI_INTENT_SCHEME));
                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.chajian1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = "http://video.mi.com/player?id=V001338875";
                intent.setData(Uri.parse(url));
                Log.d("DIAOQI", "strintent=" + intent.toUri(Intent.URI_INTENT_SCHEME));
                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.chajian2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.putExtra("royalMessages", "T01020978"); // 后台下发的内容ID
                intent.putExtra("from", getActivity().getPackageName()); // 数据统计打点用
                intent.setData(Uri.parse(
                        "mifg://fashiongallery/express_preview")); // 写死
                Log.d("DIAOQI", "strintent=" + intent.toUri(Intent.URI_INTENT_SCHEME));
                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.chajian3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String fallback = "http://app.mi.com/details?id=com.mfashiongallery.emag";
                String encodeUrl = URLEncoder.encode(fallback);
                StringBuilder sb = new StringBuilder();
                sb.append("http://fashiongallery.mi.com/express_preview?");
                sb.append("mifb=").append(encodeUrl);
                sb.append("&from=").append(getActivity().getPackageName());
                sb.append("&id=").append("T01015354");
                intent.setData(Uri.parse(sb.toString()));
                Log.d("DIAOQI", "strintent=" + intent.toUri(Intent.URI_INTENT_SCHEME));
                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.testtest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String fallback = "http://app.mi.com/details?id=com.mfashiongallery.emag";
                String encodeUrl = URLEncoder.encode(fallback);
                StringBuilder sb = new StringBuilder();
                sb.append("http://fashiongallery.mi.com/express_preview?");
                sb.append("mifb=").append(encodeUrl);
                sb.append("&from=").append(getActivity().getPackageName());
                sb.append("&id=").append("T01015354");
                intent.setData(Uri.parse(sb.toString()));
                Log.d("DIAOQI", "strintent=" + intent.toUri(Intent.URI_INTENT_SCHEME));
                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.testweb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                StringBuilder sb = new StringBuilder();
                sb.append("mifg://fashiongallery/h5?");
                sb.append("from=").append("com.mfashiongallery.emag");
                String url = "http://www.baidu.com";
                sb.append("&url=").append(url);
                intent.setData(Uri.parse(sb.toString()));
                Log.d("DIAOQI", "strintent=" + intent.toUri(Intent.URI_INTENT_SCHEME));
                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.testweb2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String strintent = "intent://fashiongallery/h5?from=com.mfashiongallery.emag&url=http://www.baidu.com#Intent;scheme=mifg;end";
                try {
                    intent = Intent.parseUri(strintent, Intent.URI_INTENT_SCHEME);
                    Log.d("DIAOQI", "strintent=" + intent.toUri(Intent.URI_INTENT_SCHEME));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.testvideo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.miui.videoplayer.LOCAL_VIDEO_PLAY");
                intent.setData(Uri.fromFile(new File("/mnt/sdcard/video_guanggao.mp4")));
                intent.putExtra("StartActivityWhenLocked", true);
                Log.d("DIAOQI", "strintent=" + intent.toUri(Intent.URI_INTENT_SCHEME));
                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.testvideo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String playUrl = "http://image.cdn.mvideo.xiaomi.com/mfsv2/download/fdsc3/p01InEmhXMlU/DTqPYfUemVAl1h.mp4";
                String encodeUrl = URLEncoder.encode(playUrl);
                StringBuilder sb = new StringBuilder();
                sb.append("mivideo://video/play_lock?");
                sb.append("id=").append(playUrl);
                intent.setData(Uri.parse(sb.toString()));
                Log.d("DIAOQI", "strintent=" + intent.toUri(Intent.URI_INTENT_SCHEME));
                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.testvideo3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String playUrl = "http://image.cdn.mvideo.xiaomi.com/mfsv2/download/fdsc3/p01InEmhXMlU/DTqPYfUemVAl1h.mp4";
                String encodeUrl = URLEncoder.encode(playUrl);
                StringBuilder sb = new StringBuilder();
                sb.append("mivideo://video/play_lock?");
                sb.append("StartActivityWhenLocked=").append(true);
                sb.append("&from=").append("com.mfashiongallery.emag");
                sb.append("&id=").append(encodeUrl);
                intent.setData(Uri.parse(sb.toString()));
                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.shangcheng1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setData(Uri.parse("mab://ab.xiaomi.com/d?url=aHR0cDovL20ubWkuY29tLzEvIy9wcm9kdWN0L3ZpZXc_cHJvZHVjdF9pZD00MDI1&cid=20036.00000&client_id=180100041078"));
                String strintent = intent.toUri(Intent.URI_INTENT_SCHEME);
                Log.d("MMM", "strintent=" + strintent);
                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.videoview1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoViewActivity.class);
                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.yidianzixun1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                sb.append("http://mb.yidianzixun.com/article/");
                sb.append("0EjO3Jtt").append("?");
                sb.append("s=mb&ref=browser_news");
                Uri uri = Uri.parse(sb.toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setClassName("com.android.browser",
                        "com.android.browser.BrowserActivity");
                intent.putExtra(Browser.EXTRA_APPLICATION_ID,
                        "com.mfasiongallery.emag");
                intent.putExtra("enter_news_comment_mode",true);
                Log.d("DIAOQI", "strintent=" + intent.toUri(Intent.URI_INTENT_SCHEME));
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    boolean firstResume = true;

    @Override
    public void onResume() {
        super.onResume();
        if (firstResume) {
            firstResume = false;
        }
        if (!firstResume) {
        }
    }

    public boolean holdGoBack() {
        // if (myOneKeyShare != null && myOneKeyShare.isShow()) {
        // return true;
        // }
        // if (popupAttacher != null && popupAttacher.isShowing()) {
        // return true;
        // }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag = false;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (holdGoBack()) {
                // if (myOneKeyShare != null && myOneKeyShare.isShow()) {
                // myOneKeyShare.close();
                // }
                // if (popupAttacher != null && popupAttacher.isShowing()) {
                // popupAttacher.closePop();
                // }
                flag = true;
            }
        }
        return flag;
    }

    public void leaveCurrentPage() {
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.push_still,
                R.anim.push_right_out);
    }

    public String getLauncherClassName(String packageName) {
        if (packageName == null) {
            return null;
        }
        PackageManager pm = getActivity().getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(packageName);
        List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
        if(list != null) {
            for(ResolveInfo r : list) {
                if (packageName.equals(r.activityInfo.applicationInfo.packageName)) {
                    return r.activityInfo.name;
                }
            }
        }
        return null;
    }
}
