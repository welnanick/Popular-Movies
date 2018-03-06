package com.nickwelna.popularmovies;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.nickwelna.popularmovies.networking.MovieDBClient;
import com.nickwelna.popularmovies.networking.MovieList;

import java.io.InputStream;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                                                         .addConverterFactory(
                                                                 GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        MovieDBClient client = retrofit.create(MovieDBClient.class);

        /*
         * Place a file called 'apiKey.properties' in src/main/assets with the key "api_key" with
         * its value being your moviedb api key (v3 auth) (i.e. api_key=*your api key*) or comment
         * out lines 40 through 49 and pass it directly as a string parameter on line 51.
         */
        Properties apiProperties = new Properties();
        try {

            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("apiKey.properties");
            apiProperties.load(inputStream);

        }
        catch (Exception e) {

            Log.e(TAG, e.getMessage());

        }

        // Replace with your api_key if not using properties file
        Call<MovieList> call = client.popularMovies(apiProperties.getProperty("api_key"));

        call.enqueue(new Callback<MovieList>() {

            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_LONG).show();

                MovieList movieList = response.body();

                Toast.makeText(MainActivity.this, "Success 2!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }

        });

    }

}
