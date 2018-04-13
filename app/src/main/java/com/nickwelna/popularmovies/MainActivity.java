package com.nickwelna.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.nickwelna.popularmovies.MovieAdapter.MovieAdapterOnClickHandler;
import com.nickwelna.popularmovies.MovieListAdapter.MovieListAdapterOnClickHandler;
import com.nickwelna.popularmovies.data.FavoritesContract.FavoritesEntry;
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

public class MainActivity extends AppCompatActivity
        implements MovieListAdapterOnClickHandler, MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MOVIE_TAG = "movie";
    public static final String KEY_TAG = "api_key";
    @BindView(R.id.movie_list_recycler_view)
    public RecyclerView movieListRecyclerView;
    @BindView(R.id.message)
    public TextView message;
    @BindView(R.id.choices)
    public Spinner choices;
    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;
    private MovieDBClient client;
    // Replace with your apiKey if not using properties file
    private String apiKey;
    private boolean requestStarted = false;
    private RequestManager glide;
    private boolean movieSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> choicesAdapter = ArrayAdapter
                .createFromResource(this, R.array.choices, android.R.layout.simple_spinner_item);
        choicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choices.setAdapter(choicesAdapter);

        choices.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {

                    progressBar.setVisibility(View.VISIBLE);
                    message.setVisibility(View.GONE);
                    fetchResults();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        glide = Glide.with(MainActivity.this);

        Retrofit.Builder builder = new Builder().baseUrl(getString(R.string.base_url))
                                                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        client = retrofit.create(MovieDBClient.class);

        /*
         * Place a file called 'apiKey.properties' in src/main/assets with the key "apiKey" with
         * its value being your moviedb api key (v3 auth) (i.e. apiKey=*your api key*) or comment
         * out lines 137 through 150 and assign it directly on line 60.
         */
        Properties apiProperties = new Properties();
        try {

            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open(getString(R.string.properties_file_path));
            apiProperties.load(inputStream);
            apiKey = apiProperties.getProperty(getString(R.string.api_key_property_key));

        }
        catch (Exception e) {

            Log.e(TAG, e.getMessage());

        }

    }

    private void fetchResults() {

        if (!requestStarted) {

            Call<MovieList> call;

            switch (choices.getSelectedItemPosition()) {

                case 1:
                    call = client.popularMovies(apiKey);
                    requestStarted = true;
                    break;

                case 2:
                    call = client.highestRatedMovies(apiKey);
                    requestStarted = true;
                    break;

                default:
                    call = null;
                    break;

            }

            if (call != null) {

                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                if (cm != null) {

                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected =
                            activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    final MovieListAdapter adapter = new MovieListAdapter(glide, MainActivity.this);

                    movieListRecyclerView.setAdapter(adapter);
                    if (isConnected) {

                        call.enqueue(new Callback<MovieList>() {

                            @Override
                            public void onResponse(Call<MovieList> call,
                                                   Response<MovieList> response) {

                                requestStarted = false;
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

                                requestStarted = false;
                                choices.setSelection(0);
                                Toast.makeText(MainActivity.this, R.string.generic_error_2,
                                        Toast.LENGTH_LONG).show();

                            }

                        });

                    }
                    else {

                        message.setText(R.string.no_connection_error);
                        message.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        requestStarted = false;

                    }

                }

            }
            else {

                final MovieAdapter adapter =
                        new MovieAdapter(glide, MainActivity.this, client, apiKey, progressBar);

                movieListRecyclerView.setAdapter(adapter);

                Cursor cursor = getContentResolver()
                        .query(FavoritesEntry.CONTENT_URI, null, null, null, null);
                if (cursor != null) {

                    while (cursor.moveToNext()) {

                        adapter.addMovie(
                                cursor.getInt(cursor.getColumnIndex(FavoritesEntry.MOVIE_ID)));

                    }
                    cursor.close();

                }

            }

        }

    }

    @Override
    public void onClick(int movieId) {

        movieSelected = true;

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if (isConnected) {

                Call<Movie> movieCall = client.getMovie(movieId, apiKey);

                movieCall.enqueue(new Callback<Movie>() {

                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {

                        Movie movie = response.body();

                        if (movie != null) {

                            Intent movieDetailIntent =
                                    new Intent(MainActivity.this, MovieDetailActivity.class);
                            movieDetailIntent.putExtra(MOVIE_TAG, movie);
                            movieDetailIntent.putExtra(KEY_TAG, apiKey);
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

                Toast.makeText(MainActivity.this, R.string.connection_error_2, Toast.LENGTH_LONG)
                     .show();

            }

        }

    }

    @Override
    protected void onResume() {

        super.onResume();
        if (movieSelected) {

            fetchResults();
            movieSelected = false;

        }

    }

}
