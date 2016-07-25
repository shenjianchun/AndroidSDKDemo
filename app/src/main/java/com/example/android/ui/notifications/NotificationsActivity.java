package com.example.android.ui.notifications;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import org.apache.commons.collections4.CollectionUtils;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Notifications Demo
 * Created by 14110105 on 2016-03-10.
 */
public class NotificationsActivity extends BaseActivity {

    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    List<Fragment> mFragments;
    String[] mTitles;
    @Override
    protected void initViewsAndEvents() {

        mFragments = new ArrayList<>();
        mTitles = new String[]{"Basic", "Visibility", "HeadsUp", "OtherMetadata", "Custom"};
        mFragments.add(BasicNotificationFragment.newInstance());
        mFragments.add(VisibilityMetadataFragment.newInstance());
        mFragments.add(HeadsUpNotificationFragment.newInstance());
        mFragments.add(OtherMetadataFragment.newInstance());
        mFragments.add(CustomNotificationFragment.newInstance());
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_notifications;
    }


    private FragmentPagerAdapter mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {



        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return CollectionUtils.isEmpty(mFragments) ? 0 : mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    };

}
