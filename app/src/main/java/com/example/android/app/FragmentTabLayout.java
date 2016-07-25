package com.example.android.app;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Fragment with TabLayout
 * Created by 14110105 on 2016-04-22.
 */
public class FragmentTabLayout extends BaseActivity {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;


    @Override
    protected void initViewsAndEvents() {

//        mTabLayout.addTab(mTabLayout.newTab().setText("AlertDialog"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("CustomAnimation"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("HideShow"));

        mFragments.add(CountingFragment.newInstance(1));
        mFragments.add(CountingFragment.newInstance(2));
        mFragments.add(CountingFragment.newInstance(3));


        mTitles.add("1");
        mTitles.add("2");
        mTitles.add("3");

//        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setAdapter(mFragmentStatePagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

    }


    private FragmentPagerAdapter mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    };

    private FragmentStatePagerAdapter mFragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    };

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_tab_layout;
    }


    public static class CountingFragment extends Fragment {
        int mNum;


        public int getNum() {
            return mNum;
        }

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static CountingFragment newInstance(int num) {
            CountingFragment f = new CountingFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Log.d("Fragment", "onAttach" + " " + mNum);
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
            Log.d("Fragment", "onCreate" + " " + mNum);
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.hello_world, container, false);
            View tv = v.findViewById(R.id.text);
            ((TextView) tv).setText("Fragment #" + mNum);

            tv.setBackgroundResource(android.R.drawable.gallery_thumb);
            Log.d("Fragment", "onCreateView" + " " + mNum);
            return v;
        }


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.d("Fragment", "onActivityCreated" + " " + mNum);
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.d("Fragment", "onStart" + " " + mNum);
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d("Fragment", "onResume" + " " + mNum);
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.d("Fragment", "onPause" + " " + mNum);
        }


        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            Log.d("Fragment", "onSaveInstanceState" + " " + mNum);
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d("Fragment", "onStop" + " " + mNum);
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.d("Fragment", "onDestroyView" + " " + mNum);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d("Fragment", "onDestroy" + " " + mNum);
        }

        @Override
        public void onDetach() {
            super.onDetach();
            Log.d("Fragment", "onDetach" + " " + mNum);
        }

        @Override
        public void onHiddenChanged(boolean hidden) {
            super.onHiddenChanged(hidden);
            Log.d("Fragment", "onHiddenChanged" + " " + mNum);
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            Log.d("Fragment", "setUserVisibleHint" + " " + mNum + " isVisibleToUser=" + isVisibleToUser);
        }
    }


}
