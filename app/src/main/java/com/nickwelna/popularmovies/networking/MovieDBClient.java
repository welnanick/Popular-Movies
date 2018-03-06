package com.nickwelna.popularmovies.networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nick on 3/5/2018.
 */
public interface MovieDBClient {

    @GET("/3/movie/popular")
    Call<MovieList> popularMovies(@Query("api_key") String apiKey);

}
