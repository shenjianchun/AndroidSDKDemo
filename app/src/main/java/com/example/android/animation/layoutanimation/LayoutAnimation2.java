package com.example.android.animation.layoutanimation;

import com.example.android.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * ListView 的 LayoutAnimation动画
 * Created by 14110105 on 2016-03-25.
 */
public class LayoutAnimation2 extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrings));

        AnimationSet set = new AnimationSet(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300);
        set.addAnimation(alphaAnimation);

        Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
        translateAnimation.setDuration(300);
        set.addAnimation(translateAnimation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 1f);
        ListView listView = getListView();
        listView.setLayoutAnimation(controller);
        listView.startLayoutAnimation();


    }


    private String[] mStrings = {
            "Bordeaux",
            "Lyon",
            "Marseille",
            "Nancy",
            "Paris",
            "Toulouse",
            "Strasbourg"
    };
}
