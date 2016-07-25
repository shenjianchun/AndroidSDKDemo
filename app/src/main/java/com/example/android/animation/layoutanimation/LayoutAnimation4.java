package com.example.android.animation.layoutanimation;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;

/**
 * GridView Animation
 * Created by 14110105 on 2016-03-24.
 */
public class LayoutAnimation4 extends BaseActivity {

    @Bind(R.id.gv_apps)
    GridView mGridView;

    private List<ResolveInfo> mApps;

    @Override
    protected void initViewsAndEvents() {
        loadApps();
        mGridView.setAdapter(new AppsAdapter());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_animation_4;
    }

    /**
     * 获取手机安装过的应用
     */
    private void loadApps() {

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }

    public class AppsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Math.min(32, mApps.size());
        }

        @Override
        public Object getItem(int position) {
            return mApps.get(position % mApps.size());
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView = new ImageView(LayoutAnimation4.this);
            ResolveInfo resolveInfo = mApps.get(position % mApps.size());
            imageView.setImageDrawable(resolveInfo.activityInfo.loadIcon(getPackageManager()));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            final int w = (int) (36 * getResources().getDisplayMetrics().density);
            imageView.setLayoutParams(new GridView.LayoutParams(w, w));

            return imageView;
        }
    }


}
