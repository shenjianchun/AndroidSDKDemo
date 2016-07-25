package com.example.android.app;

import com.example.android.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Demonstration of hiding and showing fragments.
 * Created by 14110105 on 2016-04-20.
 */
public class FragmentHideShow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.fragment_hide_show);

        FragmentManager fragmentManager = getFragmentManager();
        addShowHideListener(R.id.frag1hide, fragmentManager.findFragmentById(R.id.fragment1));
        addShowHideListener(R.id.frag2hide, fragmentManager.findFragmentById(R.id.fragment2));

    }

    void addShowHideListener(int buttonId, final Fragment fragment) {

        final Button button = (Button) findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (fragment.isHidden()) {
                    transaction.show(fragment);
                    button.setText("hide");
                } else {
                    transaction.hide(fragment);
                    button.setText("show");
                }

                transaction.commit();

            }
        });

    }

    public static class FirstFragment extends Fragment {
        TextView mTextView;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Log.d("Fragment", "onAttach");
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d("Fragment", "onCreate");
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
                savedInstanceState) {
            View v = inflater.inflate(R.layout.labeled_text_edit, container, false);
            View tv = v.findViewById(R.id.msg);
            ((TextView)tv).setText("The fragment saves and restores this text.");

            // Retrieve the text editor, and restore the last saved state if needed.
            mTextView = (TextView)v.findViewById(R.id.saved);
            if (savedInstanceState != null) {
                mTextView.setText(savedInstanceState.getCharSequence("text"));
            }
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
            outState.putCharSequence("text", mTextView.getText());
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

    public static class SecondFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.labeled_text_edit, container, false);
            View tv = v.findViewById(R.id.msg);
            ((TextView)tv).setText("The TextView saves and restores this text.");

            // Retrieve the text editor and tell it to save and restore its state.
            // Note that you will often set this in the layout XML, but since
            // we are sharing our layout with the other fragment we will customize
            // it here.
            ((TextView)v.findViewById(R.id.saved)).setSaveEnabled(true);
            return v;
        }
    }

}
