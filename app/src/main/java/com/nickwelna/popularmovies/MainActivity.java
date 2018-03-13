package com.nickwelna.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.nickwelna.popularmovies.MovieListAdapter.MovieListAdapterOnClickHandler;
import com.nickwelna.popularmovies.networking.Movie;
import com.nickwelna.popularmovies.networking.MovieDBClient;
import com.nickwelna.popularmovies.networking.MovieList;
import com.nickwelna.popularmovies.networking.MovieListResult;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieListAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MOVIE_TAG = "movie";
    @BindView(R.id.movie_list_recycler_view)
    public RecyclerView movieListRecyclerView;
    @BindView(R.id.message)
    public TextView message;
    @BindView(R.id.most_popular)
    public RadioButton most_popular;
    @BindView(R.id.options)
    public RadioGroup options;
    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;
    private MovieListAdapter adapter;
    private MovieDBClient client;
    // Replace with your api_key if not using properties file
    private String api_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        options.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (adapter != null) {
                    adapter.clearMovies();
                }

                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                if (cm != null) {

                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected =
                            activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                    if (isConnected) {

                        progressBar.setVisibility(View.VISIBLE);
                        message.setVisibility(View.GONE);
                        fetchResults();

                    }
                    else {

                        message.setText(R.string.no_connection_error);

                    }

                }

            }

        });

        movieListRecyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            layoutManager = new GridLayoutManager(MainActivity.this, 2);

        }
        else {

            layoutManager = new GridLayoutManager(MainActivity.this, 3);

        }
        movieListRecyclerView.setLayoutManager(layoutManager);

        RequestManager glide = Glide.with(MainActivity.this);
        adapter = new MovieListAdapter(glide, MainActivity.this);

        movieListRecyclerView.setAdapter(this.adapter);

        Retrofit.Builder builder = new Builder().baseUrl(getString(R.string.base_url))
                                                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        client = retrofit.create(MovieDBClient.class);

        /*
         * Place a file called 'apiKey.properties' in src/main/assets with the key "api_key" with
         * its value being your moviedb api key (v3 auth) (i.e. api_key=*your api key*) or comment
         * out lines 137 through 150 and assign it directly on line 60.
         */
        Properties apiProperties = new Properties();
        try {

            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open(getString(R.string.properties_file_path));
            apiProperties.load(inputStream);
            api_key = apiProperties.getProperty(getString(R.string.api_key_property_key));

        }
        catch (Exception e) {

            Log.e(TAG, e.getMessage());

        }

    }

    private void fetchResults() {

        Call<MovieList> call;

        if (most_popular.isChecked()) {

            call = client.popularMovies(api_key);

        }
        else {

            call = client.highestRatedMovies(api_key);

        }

        call.enqueue(new Callback<MovieList>() {

            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                MovieList movieList = response.body();

                if (movieList != null) {

                    List<MovieListResult> results = movieList.getResults();

                    if (results != null && results.size() != 0) {

                        adapter.addMovies(movieList.getResults());
                        progressBar.setVisibility(View.GONE);

                    }

                }

            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

                Toast.makeText(MainActivity.this, R.string.generic_error, Toast.LENGTH_LONG).show();

            }

        });

    }

    @Override
    public void onClick(int movieId) {

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if (isConnected) {

                Call<Movie> call = client.getMovie(movieId, api_key);

                call.enqueue(new Callback<Movie>() {

                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {

                        Movie movie = response.body();

                        if (movie != null) {

                            Intent movieDetailIntent =
                                    new Intent(MainActivity.this, MovieDetailActivity.class);
                            movieDetailIntent.putExtra(MOVIE_TAG, movie);
                            startActivity(movieDetailIntent);

                        }

                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                        Toast.makeText(MainActivity.this, R.string.generic_error, Toast.LENGTH_LONG)
                             .show();

                    }

                });
            }
            else {

                Toast.makeText(MainActivity.this, R.string.connection_error, Toast.LENGTH_LONG)
                     .show();

            }

        }

    }

}
