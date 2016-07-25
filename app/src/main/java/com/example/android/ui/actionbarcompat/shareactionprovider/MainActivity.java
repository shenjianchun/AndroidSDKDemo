package com.example.android.ui.actionbarcompat.shareactionprovider;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Actionbar Share action provider
 * Created by 14110105 on 2016-03-07.
 */
public class MainActivity extends BaseActivity {

    public static final ArrayList<ContentItem> mItems = getSampleContent();

    private ShareActionProvider mShareActionProvider;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void initViewsAndEvents() {
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setShareIntent(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_actionbar_shareactionprovider;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_actionbarcompat_actionshare_provider, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        int currentViewPagerItem = mViewPager.getCurrentItem();
        setShareIntent(currentViewPagerItem);
        return super.onCreateOptionsMenu(menu);
    }

    private void setShareIntent(int position) {
        if (mShareActionProvider != null) {
            ContentItem item = mItems.get(position);
            mShareActionProvider.setShareIntent(item.getShareIntent(this));
        }

    }


    private final PagerAdapter mPagerAdapter = new PagerAdapter() {

        LayoutInflater mInflater;

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            if (mInflater == null) {
                mInflater = MainActivity.this.getLayoutInflater();
            }

            ContentItem item = mItems.get(position);
            if (item.contentType == ContentItem.CONTENT_TYPE_IMAGE) {
                ImageView imageView = (ImageView) mInflater.inflate(R.layout.item_image, container, false);
                imageView.setImageURI(item.getContentUri());
                container.addView(imageView);
                return imageView;
            } else {
                TextView textView = (TextView) mInflater.inflate(R.layout.item_text, container, false);
                textView.setText(item.contentResourceId);
                container.addView(textView);
                return textView;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };


    /**
     * @return An ArrayList of ContentItem's to be displayed in this sample
     */
    static ArrayList<ContentItem> getSampleContent() {
        ArrayList<ContentItem> items = new ArrayList<ContentItem>();

        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "photo_1.jpg"));
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, R.string.quote_1));
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, R.string.quote_2));
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "photo_2.jpg"));
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, R.string.quote_3));
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "photo_3.jpg"));

        return items;
    }

}
