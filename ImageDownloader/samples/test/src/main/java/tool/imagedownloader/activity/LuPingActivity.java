package tool.imagedownloader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import tool.imagedownloader.fragment.LuPingFragment;
import tool.imagedownloader.test.R;

/**
 * Created by liuchonghui on 16/12/28.
 */
public class LuPingActivity extends BaseFragmentActivity {
    protected LuPingFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame);
        initFragment();
    }

    public void initFragment() {
        if (mFragment == null) {
            mFragment = new LuPingFragment();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fm.beginTransaction();
        fragTransaction.add(R.id.content_frame, mFragment);
        fragTransaction.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mFragment != null) {
            mFragment.onNewIntent(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mFragment != null && mFragment.holdGoBack()) {
                return mFragment.onKeyDown(keyCode, event);
            } else {
                if (mFragment != null) {
                    mFragment.leaveCurrentPage();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}