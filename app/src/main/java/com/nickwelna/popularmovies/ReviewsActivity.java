package com.nickwelna.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nickwelna.popularmovies.networking.ReviewList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsActivity extends AppCompatActivity {

    @BindView(R.id.review_recycler_view)
    RecyclerView reviewRecyclerView;
    @BindView(R.id.no_reviews_text_view)
    TextView noReviewsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        ReviewList reviewList = intent.getParcelableExtra(MovieDetailActivity.REVIEWS_TAG);

        if (reviewList.getResults().size() > 0) {

            noReviewsTextView.setVisibility(View.GONE);
            reviewRecyclerView.setHasFixedSize(true);
            reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            reviewRecyclerView.setAdapter(new ReviewListAdapter(reviewList.getResults()));

        }
        else {

            noReviewsTextView.setVisibility(View.VISIBLE);

        }

    }

}
