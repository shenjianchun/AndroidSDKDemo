package com.example.android.view;

import com.example.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * RatingBar
 * Created by 14110105 on 2016-04-16.
 */
public class RatingBar1 extends Activity implements RatingBar.OnRatingBarChangeListener{

    RatingBar mSmallRatingBar;
    RatingBar mIndicatorRatingBar;
    TextView mRatingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_bar_1);

        mRatingText = (TextView) findViewById(R.id.rating);

        // We copy the most recently changed rating on to these indicator-only
        // rating bars
        mIndicatorRatingBar = (RatingBar) findViewById(R.id.indicator_ratingbar);
        mSmallRatingBar = (RatingBar) findViewById(R.id.small_ratingbar);

        mIndicatorRatingBar.setOnRatingBarChangeListener(this);
        mSmallRatingBar.setOnRatingBarChangeListener(this);

        // The different rating bars in the layout. Assign the listener to us.
        ((RatingBar)findViewById(R.id.ratingbar1)).setOnRatingBarChangeListener(this);
        ((RatingBar)findViewById(R.id.ratingbar2)).setOnRatingBarChangeListener(this);
    }


    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        int  numStars = ratingBar.getNumStars();
        mRatingText.setText("Rating:" + ratingBar.getRating() + "/" + ratingBar.getNumStars());


        // Since this rating bar is updated to reflect any of the other rating
        // bars, we should update it to the current values.
        if (mIndicatorRatingBar.getNumStars() != numStars) {

            mSmallRatingBar.setNumStars(numStars);
            mIndicatorRatingBar.setNumStars(numStars);
        }


        if (mIndicatorRatingBar.getRating() != rating) {
            mSmallRatingBar.setRating(rating);
            mIndicatorRatingBar.setRating(rating);
        }

        final float stepSize = ratingBar.getStepSize();
        if (mIndicatorRatingBar.getStepSize() != stepSize) {
            mSmallRatingBar.setStepSize(stepSize);
            mIndicatorRatingBar.setStepSize(stepSize);
        }

    }
}
