package tool.imagedownloader.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

import tool.imagedownloader.test.R;

/**
 * Created by liuchonghui on 2017/2/16.
 */
public class NestedActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockwallpaper_preview_detail2);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        GridLayoutManager lm = new GridLayoutManager(this, 2) {
            @Override
            public void scrollToPositionWithOffset(int position, int offset) {
                super.scrollToPositionWithOffset(position, offset);
                Log.d("CCC", "scrollToPositionWithOffset(" + position + ", " + offset + ")");
            }
        };
        recycler.setLayoutManager(lm);
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            items.add("i:" + i);
        }
        ItemAdapter mAdapter = new ItemAdapter(items);
        recycler.setAdapter(mAdapter);
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.viewHolder> {
        private ArrayList<String> items = new ArrayList<>();

        public ItemAdapter(ArrayList<String> items) {
            this.items = items;
        }

        @Override
        public viewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_item_card,
                    viewGroup, false);
            return new viewHolder(view);
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public void onBindViewHolder(viewHolder viewHolder, int position) {
            String info = items.get(position);
            View view = viewHolder.itemView;
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(info);
            //手动更改高度，不同位置的高度有所不同
            textView.setHeight(100 + (position % 3) * 30);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class viewHolder extends RecyclerView.ViewHolder {
            public viewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
