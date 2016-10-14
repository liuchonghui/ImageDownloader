package tool.imagedownloader.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.net.URLEncoder;

import tool.imagedownloader.activity.FeiDianActivity;
import tool.imagedownloader.activity.YangLiActivity;
import tool.imagedownloader.test.R;

/**
 * @author liu_chonghui
 */
public class Main2Fragment extends BaseFragment {

    protected int getPageLayout() {
        return R.layout.activity_main2;
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
        EditText action = (EditText) view.findViewById(R.id.text_action);
        EditText uri = (EditText) view.findViewById(R.id.text_uri);
        EditText key1 = (EditText) view.findViewById(R.id.text_key1);
        EditText key2 = (EditText) view.findViewById(R.id.text_key2);
        EditText key3 = (EditText) view.findViewById(R.id.text_key3);
        EditText value1 = (EditText) view.findViewById(R.id.text_value1);
        EditText value2 = (EditText) view.findViewById(R.id.text_value2);
        EditText value3 = (EditText) view.findViewById(R.id.text_value3);
        Button showStrintent = (Button) view.findViewById(R.id.show_strintent);
        final TextView strintent = (TextView) view.findViewById(R.id.strintent);
        Button launch = (Button) view.findViewById(R.id.launch);

        final Intent intent = new Intent();
        if (action.getText() != null && action.getText().toString() != null) {
            intent.setAction(action.getText().toString());
        }
        if (uri.getText() != null && uri.getText().toString() != null) {
            String encode = URLEncoder.encode(uri.getText().toString());
            intent.setData(Uri.parse(encode));
        }
        Bundle bundle = new Bundle();
        if (key1.getText() != null && key1.getText().toString() != null
                && value1.getText() != null && value1.getText().toString() != null) {
            bundle.putString(key1.getText().toString(), value1.getText().toString());
        }
        if (key2.getText() != null && key2.getText().toString() != null
                && value2.getText() != null && value2.getText().toString() != null) {
            bundle.putString(key2.getText().toString(), value2.getText().toString());
        }
        if (key3.getText() != null && key3.getText().toString() != null
                && value3.getText() != null && value3.getText().toString() != null) {
            bundle.putString(key3.getText().toString(), value3.getText().toString());
        }
        intent.putExtras(bundle);
        final Intent launchIntent = intent;
        showStrintent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringIntent = null;
                stringIntent = launchIntent.toUri(Intent.URI_INTENT_SCHEME);
                if (stringIntent != null && stringIntent.length() > 0) {
                    strintent.setText(stringIntent);
                } else {
                    strintent.setText(" ");
                }
            }
        });
        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(launchIntent);
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

}
