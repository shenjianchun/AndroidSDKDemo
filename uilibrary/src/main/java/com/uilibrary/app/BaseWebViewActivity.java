package com.uilibrary.app;

import com.uilibrary.widget.WebViewLayout;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import my.uilibrary.R;

/**
 * Created by 14110105 on 2016-05-17.
 */
public class BaseWebViewActivity extends BaseActivity implements WebViewLayout.WebViewCallback {

    public static final String BUNDLE_KEY_URL = "BUNDLE_KEY_URL";
    public static final String BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE";
    public static final String BUNDLE_KEY_SHOW_BOTTOM_BAR = "BUNDLE_KEY_SHOW_BOTTOM_BAR";


    private ImageButton mActionBackIb;
    private ImageButton mActionCloseIb;
    private TextView mTitleTv;

    private String mWebUrl;
    private String mWebTitle;
    /**
     * 是否显示底部控制栏
     */
    private boolean isShowBottomBar;

    private WebViewLayout mWebViewLayout;



    @Override
    protected void initViewsAndEvents() {

        List<String> lists = Collections.emptyList();

        mWebUrl = getIntent().getStringExtra(BUNDLE_KEY_URL);
        mWebTitle = getIntent().getStringExtra(BUNDLE_KEY_TITLE);
        isShowBottomBar = getIntent().getBooleanExtra(BUNDLE_KEY_SHOW_BOTTOM_BAR, false);

        mActionBackIb = (ImageButton) findViewById(R.id.ib_action_back);
        mActionCloseIb = (ImageButton) findViewById(R.id.ib_close);
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        mWebViewLayout = (WebViewLayout) findViewById(R.id.webview_layout);

        mActionBackIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mActionCloseIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWebViewLayout.setBrowserControllerVisibility(isShowBottomBar);
        mWebViewLayout.setCallback(this);

        mWebViewLayout.loadUrl("http://www.baidu.com/");

    }

    @Override
    public void onReceivedTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTv.setText(title);
        } else {

        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_base_webview;
    }

    @Override
    public void onBackPressed() {
        if (mWebViewLayout.canGoBack()) {
            mWebViewLayout.goBack();
            mActionCloseIb.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

}
