package com.example.android.ui.systemui;

import com.example.android.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import my.nouilibrary.utils.T;

/**
 * This activity demonstrates how to use system UI flags to implement
 * a content browser style of UI (such as a book reader).
 * Created by 14110105 on 2016-03-28.
 */
public class ContentBrowser extends Activity implements SearchView.OnQueryTextListener {

    @Bind(R.id.content)
    Content mContent;
    @Bind(R.id.title)
    TextView mTitleView;
    @Bind(R.id.seekbar)
    SeekBar mSeekBar;

    /**
     * Implementation of a view for displaying immersive content, using system UI
     * flags to transition in and out of modes where the user is focused on that
     * content.
     */
    public static class Content extends ScrollView implements View.OnClickListener,
            View.OnSystemUiVisibilityChangeListener {

        TextView mText;
        TextView mTitleView;
        SeekBar mSeekView;
        boolean mNavVisibile;
        int mBaseSystemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_STABLE;
        int mLastSystemUiVis;

        Runnable mNavHider = new Runnable() {
            @Override
            public void run() {
                setNavVisibility(false);
            }
        };

        public Content(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);

            mText = new TextView(context);
            mText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            mText.setText(context.getString(R.string.alert_dialog_two_buttons2ultra_msg));
            mText.setClickable(false);
            mText.setOnClickListener(this);
            mText.setTextIsSelectable(true);

            addView(mText, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            setOnSystemUiVisibilityChangeListener(this);
        }

        public Content(Context context, AttributeSet attrs, int defStyleAttr) {
            this(context, attrs, defStyleAttr, 0);

        }

        public Content(Context context, AttributeSet attrs) {
            this(context, attrs, -1);
        }

        public Content(Context context) {
            this(context, null);
        }

        public void init(TextView title, SeekBar seek) {
            mTitleView = title;
            mSeekView = seek;
        }

        void setBaseSystemUiVisibility(int visibility) {
            mBaseSystemUiVisibility = visibility;
        }

        @Override
        public void onWindowSystemUiVisibilityChanged(int visible) {
            super.onWindowSystemUiVisibilityChanged(visible);

            int diff = mLastSystemUiVis ^ visible;
            mLastSystemUiVis = visible;
            if ((diff & SYSTEM_UI_FLAG_LOW_PROFILE) != 0 && (visible & SYSTEM_UI_FLAG_LOW_PROFILE) == 0) {
                setNavVisibility(true);
            }

        }

        @Override
        protected void onWindowVisibilityChanged(int visibility) {
            super.onWindowVisibilityChanged(visibility);

            // When we become visible, we show our navigation elements briefly
            // before hiding them.
            setNavVisibility(true);
            getHandler().postDelayed(mNavHider, 2000);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            super.onScrollChanged(l, t, oldl, oldt);
            setNavVisibility(false);
        }

        @Override
        public void onClick(View v) {
            int curVis = getSystemUiVisibility();
            setNavVisibility((curVis & SYSTEM_UI_FLAG_LOW_PROFILE) != 0);
        }

        @Override
        public void onSystemUiVisibilityChange(int visibility) {

        }

        void setNavVisibility(boolean visible) {
            int newVis = mBaseSystemUiVisibility;
            if (!visible) {
                newVis |= SYSTEM_UI_FLAG_LOW_PROFILE | SYSTEM_UI_FLAG_FULLSCREEN;
            }

            final boolean changed = newVis == mBaseSystemUiVisibility;

            if (changed || visible) {
                Handler handler = getHandler();
                if (handler != null) {
                    handler.removeCallbacks(mNavHider);
                }
            }

            setSystemUiVisibility(newVis);
            mTitleView.setVisibility(visible ? VISIBLE : INVISIBLE);
            mSeekView.setVisibility(visible ? VISIBLE : INVISIBLE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.content_browser);

        ButterKnife.bind(this);

        mContent.init(mTitleView, mSeekBar);

//        ActionBar bar = getActionBar();
//        bar.addTab(bar.newTab().setText("Tab 1").setTabListener(this));
//        bar.addTab(bar.newTab().setText("Tab 2").setTabListener(this));
//        bar.addTab(bar.newTab().setText("Tab 3").setTabListener(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.content_actions, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);

        MenuItem menuItem = menu.findItem(R.id.menu_item_share_action_provider_action_bar);
        ShareActionProvider shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        shareActionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        Uri uri = Uri.fromFile(getFileStreamPath("shared.png"));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareActionProvider.setShareIntent(shareIntent);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.stable_layout:
                item.setChecked(!item.isChecked());
                if (item.isChecked()) {
                    mContent.setBaseSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                } else {
                    mContent.setBaseSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSort(MenuItem item) {

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


}
