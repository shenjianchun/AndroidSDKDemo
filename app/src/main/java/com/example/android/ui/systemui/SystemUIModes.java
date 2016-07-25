package com.example.android.ui.systemui;

import com.example.android.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import my.nouilibrary.utils.T;

/**
 * System UI Mode
 * Created by 14110105 on 2016-03-30.
 */
public class SystemUIModes extends Activity implements SearchView.OnQueryTextListener {


    public static class IV extends ImageView implements View.OnSystemUiVisibilityChangeListener {

        SystemUIModes mActivity;
        ActionMode mActionMode;

        public IV(Context context) {
            super(context);
        }

        public IV(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public IV(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public IV(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public void setActivity(SystemUIModes act) {
            this.mActivity = act;
            setOnSystemUiVisibilityChangeListener(this);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mActivity.refreshSizes();
        }

        @Override
        public void onSystemUiVisibilityChange(int visibility) {
//            setSystemUiVisibility(visibility);
            mActivity.updateCheckControls();
            mActivity.refreshSizes();
        }


        private class MyAciomModeCallback implements ActionMode.Callback {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                mode.setTitle("My Action Mode!");
                mode.setSubtitle(null);
                mode.setTitleOptionalHint(false);
                menu.add("Sort By Size").setIcon(android.R.drawable.ic_menu_sort_by_size);
                menu.add("Sort By Alpha").setIcon(android.R.drawable.ic_menu_sort_alphabetically);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionMode = null;
                mActivity.clearActionMode();
            }


        }

        public void startActionMode() {
            if (mActionMode == null) {
                mActionMode = mActivity.startActionMode(new MyAciomModeCallback());
            }
        }

        public void stopActionMode() {
            if (mActionMode != null) {
                mActionMode.finish();
                mActionMode = null;
            }
        }

    }

    IV mImage;
    CheckBox[] mCheckControls = new CheckBox[8];
    int[] mCheckFlags = new int[]{View.SYSTEM_UI_FLAG_LOW_PROFILE,
            View.SYSTEM_UI_FLAG_FULLSCREEN, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION,
            View.SYSTEM_UI_FLAG_IMMERSIVE, View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY,
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN,
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    };
    TextView mMetricsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.system_ui_modes);

        mImage = (IV) findViewById(R.id.image);
        mImage.setActivity(this);

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton
                .OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSystemUI();
            }
        };

        mCheckControls[0] = (CheckBox) findViewById(R.id.modeLowProfile);
        mCheckControls[1] = (CheckBox) findViewById(R.id.modeFullscreen);
        mCheckControls[2] = (CheckBox) findViewById(R.id.modeHideNavigation);
        mCheckControls[3] = (CheckBox) findViewById(R.id.modeImmersive);
        mCheckControls[4] = (CheckBox) findViewById(R.id.modeImmersiveSticky);
        mCheckControls[5] = (CheckBox) findViewById(R.id.layoutStable);
        mCheckControls[6] = (CheckBox) findViewById(R.id.layoutFullscreen);
        mCheckControls[7] = (CheckBox) findViewById(R.id.layoutHideNavigation);

        for (int i = 0; i < mCheckControls.length; i++) {
            mCheckControls[i].setOnCheckedChangeListener(onCheckedChangeListener);
        }
        mMetricsText = (TextView) findViewById(R.id.metricsText);

        ((CheckBox) findViewById(R.id.windowFullscreen)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        setFullscreen(isChecked);
                    }
                }
        );

        ((CheckBox) findViewById(R.id.windowOverscan)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        setOverscan(isChecked);
                    }
                }
        );

        ((CheckBox) findViewById(R.id.windowTranslucentStatus)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        setTranslucentStatus(isChecked);
                    }
                }
        );

        ((CheckBox) findViewById(R.id.windowTranslucentNav)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        setTranslucentNavigation(isChecked);
                    }
                }
        );

        ((CheckBox) findViewById(R.id.windowHideActionBar)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getActionBar().hide();
                } else {
                    getActionBar().show();
                }
            }
        });

        ((CheckBox) findViewById(R.id.windowActionMode)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mImage.startActionMode();
                } else {
                    mImage.stopActionMode();
                }
            }
        });

    }

    private void setFullscreen(boolean on) {

        Window window = getWindow();

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }

        window.setAttributes(layoutParams);
    }


    private void setOverscan(boolean on) {

        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }

    private void setTranslucentNavigation(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateCheckControls();
    }

    public void updateSystemUI() {
        int visibility = 0;
        for (int i = 0; i < mCheckControls.length; i++) {
            if (mCheckControls[i].isChecked()) {
                visibility |= mCheckFlags[i];
            }
        }
        mImage.setSystemUiVisibility(visibility);
    }

    public void updateCheckControls() {
        int visibility = mImage.getSystemUiVisibility();
        for (int i = 0; i < mCheckControls.length; i++) {
            mCheckControls[i].setChecked((visibility & mCheckFlags[i]) != 0);
        }
    }

    public void clearActionMode() {
        ((CheckBox) findViewById(R.id.windowActionMode)).setChecked(false);
    }

    void refreshSizes() {
        mMetricsText.setText(getDisplaySize() + " " + getViewSize());
    }

    public String getDisplaySize() {

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return String.format("DisplayMetrics = (%d x %d)", metrics.heightPixels, metrics
                .widthPixels);
    }

    private String getViewSize() {
        return String.format("View = (%d,%d - %d,%d)", mImage.getLeft(), mImage.getTop(), mImage
                .getRight(), mImage.getBottom());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.content_actions, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);

        MenuItem menuItem = menu.findItem(R.id.menu_item_share_action_provider_action_bar);
        ShareActionProvider shareActionProvider = (ShareActionProvider) menuItem
                .getActionProvider();
        shareActionProvider.setShareHistoryFileName(ShareActionProvider
                .DEFAULT_SHARE_HISTORY_FILE_NAME);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        Uri uri = Uri.fromFile(getFileStreamPath("shared.png"));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareActionProvider.setShareIntent(shareIntent);

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        T.showShort(this, "Search for: " + query + "...");
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public void onSort(MenuItem item) {

    }

}
