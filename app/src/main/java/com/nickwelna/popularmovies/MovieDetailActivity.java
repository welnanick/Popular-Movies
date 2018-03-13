package com.nickwelna.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.nickwelna.popularmovies.networking.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.movie_title)
    public TextView movieTitle;
    @BindView(R.id.movie_release_date)
    public TextView movieReleaseDate;
    @BindView(R.id.movie_vote_average)
    public TextView movieVoteAverage;
    @BindView(R.id.movie_plot_synopsis)
    public TextView moviePlotSynopsis;
    @BindView(R.id.movie_poster)
    public ImageView moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        Movie movie = intent.getParcelableExtra(MainActivity.MOVIE_TAG);

        movieTitle.setText(movie.getTitle());
        movieReleaseDate.setText(movie.getReleaseDate());
        movieVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        moviePlotSynopsis.setText(movie.getOverview());

        RequestOptions test = new RequestOptions().override(Target.SIZE_ORIGINAL);
        Glide.with(this)
             .load(getString(R.string.image_url_base) + getString(R.string.detail_image_size) +
                     movie.getPosterPath()).apply(test).into(moviePoster);

    }

}
