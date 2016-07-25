package com.example.android.app;

import com.example.android.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Demonstrates the use of custom animations in a FragmentTransaction when
 * pushing and popping a stack.
 * Created by 14110105 on 2016-04-20.
 */
public class FragmentCustomAnimations extends Activity {
    int mStackLevel = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stack);


        // Watch for button clicks.
        Button button = (Button) findViewById(R.id.new_fragment);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addFragmentToStack();
            }
        });
        button = (Button) findViewById(R.id.delete_fragment);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        if (savedInstanceState == null) {
            // Do first time initialization -- add initial fragment.
            Fragment newFragment = CountingFragment.newInstance(mStackLevel);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.simple_fragment, newFragment);
            fragmentTransaction.commit();
        } else {
            mStackLevel = savedInstanceState.getInt("level");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level", mStackLevel);
    }

    void addFragmentToStack() {
        mStackLevel++;

        CountingFragment countingFragment = CountingFragment.newInstance(mStackLevel);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.setCustomAnimations(R.animator.fragment_slide_left_enter, R.animator
                .fragment_slide_left_exit, R.animator.fragment_slide_right_enter, R.animator
                .fragment_slide_right_exit);
        fragmentTransaction.replace(R.id.simple_fragment, countingFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public static class CountingFragment extends Fragment {
        int mNum;

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
            Log.d("Fragment", "onAttach");
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
            Log.d("Fragment", "onCreate");
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
            ((TextView)tv).setText("Fragment #" + mNum);

            tv.setBackgroundResource(android.R.drawable.gallery_thumb);
            Log.d("Fragment", "onCreateView");
            return v;
        }


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.d("Fragment", "onActivityCreated");
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.d("Fragment", "onStart");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d("Fragment", "onResume");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.d("Fragment", "onPause");
        }


        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            Log.d("Fragment", "onSaveInstanceState");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d("Fragment", "onStop");
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.d("Fragment", "onDestroyView");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d("Fragment", "onDestroy");
        }

        @Override
        public void onDetach() {
            super.onDetach();
            Log.d("Fragment", "onDetach");
        }

        @Override
        public void onHiddenChanged(boolean hidden) {
            super.onHiddenChanged(hidden);
            Log.d("Fragment", "onHiddenChanged");
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            Log.d("Fragment", "setUserVisibleHint");
        }
    }


}
