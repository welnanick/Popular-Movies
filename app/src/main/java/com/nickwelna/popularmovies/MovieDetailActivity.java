package com.nickwelna.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.nickwelna.popularmovies.data.FavoritesContract.FavoritesEntry;
import com.nickwelna.popularmovies.networking.Movie;
import com.nickwelna.popularmovies.networking.MovieDBClient;
import com.nickwelna.popularmovies.networking.ReviewList;
import com.nickwelna.popularmovies.networking.VideoList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.movie_title)
    public TextView movieTitle;
    @BindView(R.id.movie_release_date)
    public TextView movieReleaseDate;
    @BindView(R.id.movie_length)
    public TextView movieLength;
    @BindView(R.id.movie_vote_average)
    public TextView movieVoteAverage;
    @BindView(R.id.movie_plot_synopsis)
    public TextView moviePlotSynopsis;
    @BindView(R.id.movie_poster)
    public ImageView moviePoster;
    @BindView(R.id.favorite)
    public CheckBox favorite;
    private MovieDBClient client;
    private String api_key;
    private Movie movie;
    public static final String VIDEOS_TAG = "videos";
    public static final String REVIEWS_TAG = "reviews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        movie = intent.getParcelableExtra(MainActivity.MOVIE_TAG);
        api_key = intent.getStringExtra(MainActivity.KEY_TAG);

        movieTitle.setText(movie.getTitle());
        movieReleaseDate.setText(movie.getReleaseDate());
        movieLength.setText(getString(R.string.runtime_text, movie.getRuntime()));
        movieVoteAverage.setText(getString(R.string.vote_average_text, movie.getVoteAverage()));
        moviePlotSynopsis.setText(movie.getOverview());

        RequestOptions test = new RequestOptions().override(Target.SIZE_ORIGINAL);
        Glide.with(this)
             .load(getString(R.string.image_url_base) + getString(R.string.detail_image_size) +
                     movie.getPosterPath()).apply(test).into(moviePoster);

        Retrofit.Builder builder = new Builder().baseUrl(getString(R.string.base_url))
                                                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        client = retrofit.create(MovieDBClient.class);
        Uri uri = FavoritesEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(String.valueOf(movie.getId())).build();

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {

            if (cursor.moveToFirst()) {

                favorite.setChecked(true);

            }
            else {

                favorite.setChecked(false);

            }
            cursor.close();

        }

        favorite.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(FavoritesEntry.MOVIE_ID, movie.getId());
                    contentValues.put(FavoritesEntry.MOVIE_TITLE, movie.getTitle());

                    Uri uri =
                            getContentResolver().insert(FavoritesEntry.CONTENT_URI, contentValues);

                    if (uri == null) {

                        Toast.makeText(getBaseContext(), R.string.generic_error, Toast.LENGTH_LONG)
                             .show();

                    }

                }
                else {

                    Uri uri = FavoritesEntry.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(String.valueOf(movie.getId())).build();

                    int numberDeleted = getContentResolver().delete(uri, null, null);

                    if (numberDeleted == 0) {

                        Toast.makeText(getBaseContext(), R.string.generic_error, Toast.LENGTH_LONG)
                             .show();

                    }

                }

            }
        });

    }

    public void viewTrailers(View view) {

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if (isConnected) {

                Call<VideoList> trailersCall = client.getVideos(movie.getId(), api_key);

                trailersCall.enqueue(new Callback<VideoList>() {

                    @Override
                    public void onResponse(Call<VideoList> call, Response<VideoList> response) {

                        VideoList videoList = response.body();

                        if (videoList != null) {

                            Intent movieDetailIntent =
                                    new Intent(MovieDetailActivity.this, TrailerActivity.class);
                            movieDetailIntent.putExtra(VIDEOS_TAG, videoList);
                            startActivity(movieDetailIntent);

                        }

                    }

                    @Override
                    public void onFailure(Call<VideoList> call, Throwable t) {

                        Toast.makeText(MovieDetailActivity.this, R.string.generic_error,
                                Toast.LENGTH_LONG).show();

                    }

                });

            }
            else {

                Toast.makeText(this, R.string.connection_error, Toast.LENGTH_LONG).show();

            }

        }

    }

    public void viewReviews(View view) {

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if (isConnected) {

                Call<ReviewList> reviewsCall = client.getReviews(movie.getId(), api_key);

                reviewsCall.enqueue(new Callback<ReviewList>() {

                    @Override
                    public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {

                        ReviewList reviewList = response.body();

                        if (reviewList != null) {

                            Intent movieDetailIntent =
                                    new Intent(MovieDetailActivity.this, ReviewsActivity.class);
                            movieDetailIntent.putExtra(REVIEWS_TAG, reviewList);
                            startActivity(movieDetailIntent);

                        }

                    }

                    @Override
                    public void onFailure(Call<ReviewList> call, Throwable t) {

                        Toast.makeText(MovieDetailActivity.this, R.string.generic_error,
                                Toast.LENGTH_LONG).show();

                    }

                });

            }
            else {

                Toast.makeText(this, R.string.connection_error, Toast.LENGTH_LONG).show();

            }

        }

    }

}
