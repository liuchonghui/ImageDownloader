package tool.imagedownloader.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tool.imagedownloader.test.R;

/**
 * Created by liuchonghui on 16/12/28.
 */
public class LuPingFragment extends BaseFragment {

    protected int getPageLayout() {
        return R.layout.activity_videoview;
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

    protected void intView(View view) {
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
}