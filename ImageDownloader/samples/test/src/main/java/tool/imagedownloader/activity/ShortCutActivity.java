package tool.imagedownloader.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import tool.imagedownloader.test.R;

/**
 * Created by liuchonghui on 2017/5/15.
 */
public class ShortCutActivity extends Activity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FrameLayout layout = new FrameLayout(this);
        Button button = new Button(this);
        button.setText("点击创建快捷方式");
        TextView text = new TextView(this);
        StringBuilder sb = new StringBuilder();
        sb.append("           请按照如下步骤操作");
        sb.append("\n");
        sb.append("1. 第一次看到本页面后，先别点按钮，先切去设置->更多应用->已下载->测试,找到该应用详情页，进入\n");
        sb.append("2. 往下找到权限管理，进入，桌面快捷方式的权限设置为允许，回到测试页面\n");
        sb.append("3. 点击按钮，查看快捷方式创建在桌面\n");
        sb.append("4. 执行 adb uninstall tool.imagedownloader.test\n");
        text.setText(sb.toString());

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        layout.addView(text);
        layout.addView(button, lp);
        setContentView(layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createShortCutByIntent(view.getContext(), getLaunchIntent(),
                        R.string.app_name, R.mipmap.ic_launcher);
                Toast.makeText(view.getContext(), "已创建快捷方式", Toast.LENGTH_LONG).show();
            }
        });
    }

    void createShortCutByIntent(Context context, Intent launchIntent, int titleResId, int iconResId) {
        try {
            Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            shortcutIntent.putExtra("duplicate", false);
            shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(titleResId));
            final Parcelable icon = Intent.ShortcutIconResource.fromContext(context, iconResId);
            shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
            shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
            context.sendBroadcast(shortcutIntent);
        } catch (Exception e) {
        }
    }

    Intent getLaunchIntent() {
        Intent intent = new Intent("tool.imagedownloader.test.launch_test_action");
        intent.putExtra("channel", "test");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }
}
