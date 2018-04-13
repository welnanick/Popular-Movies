package com.nickwelna.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.nickwelna.popularmovies.MovieAdapter.MovieViewHolder;
import com.nickwelna.popularmovies.data.FavoritesContract.FavoritesEntry;
import com.nickwelna.popularmovies.networking.Movie;
import com.nickwelna.popularmovies.networking.MovieDBClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final List<Integer> movies = new ArrayList<>();
    private final RequestManager glide;
    private final MovieAdapterOnClickHandler onClickHandler;
    private final MovieDBClient client;
    private final String apiKey;
    private final ProgressBar progressBar;

    public void clearMovies() {

        int size = movies.size();
        movies.clear();
        notifyItemRangeRemoved(0, size);

    }

    interface MovieAdapterOnClickHandler {

        void onClick(int movie);

    }

    public MovieAdapter(RequestManager glide, MovieAdapterOnClickHandler onClickHandler,
                        MovieDBClient client, String apiKey, ProgressBar progressBar) {

        this.glide = glide;
        this.onClickHandler = onClickHandler;
        this.client = client;
        this.apiKey = apiKey;
        this.progressBar = progressBar;

    }

    public List<Integer> getMovies() {

        return movies;

    }

    public void addMovie(int movie) {

        this.movies.add(movie);
        notifyItemInserted(this.movies.size() - 1);

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);

        return new MovieViewHolder(view, parent.getContext());

    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        int movie = movies.get(position);
        holder.bind(movie);

    }

    @Override
    public int getItemCount() {

        return movies.size();

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        @BindView(R.id.movie_poster)
        ImageView moviePoster;
        @BindView(R.id.movie_title_text_view)
        TextView movieTitleTextView;
        final Context context;

        MovieViewHolder(View itemView, Context context) {

            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            this.context = context;

        }

        void bind(int movie) {

            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (cm != null) {

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected =
                        activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected) {

                    Call<Movie> movieCall = client.getMovie(movie, apiKey);

                    movieCall.enqueue(new Callback<Movie>() {

                        @Override
                        public void onResponse(Call<Movie> call, Response<Movie> response) {

                            Movie movie = response.body();

                            if (movie != null) {

                                RequestOptions test =
                                        new RequestOptions().override(Target.SIZE_ORIGINAL);
                                glide.load(context.getString(R.string.image_url_base) +
                                        context.getString(R.string.grid_image_size) +
                                        movie.getPosterPath()).apply(test).into(moviePoster);
                                progressBar.setVisibility(View.GONE);

                            }

                        }

                        @Override
                        public void onFailure(Call<Movie> call, Throwable t) {

                            Toast.makeText(context, R.string.generic_error, Toast.LENGTH_LONG)
                                 .show();

                        }

                    });

                }
                else {

                    Uri uri = FavoritesEntry.CONTENT_URI;
                    uri = Uri.withAppendedPath(uri, String.valueOf(movie));
                    Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {

                        String title =
                                cursor.getString(cursor.getColumnIndex(FavoritesEntry.MOVIE_TITLE));
                        movieTitleTextView.setText(title);
                        movieTitleTextView.setVisibility(View.VISIBLE);
                        cursor.close();

                    }
                    progressBar.setVisibility(View.GONE);

                }

            }

        }

        @Override
        public void onClick(View v) {

            onClickHandler.onClick(movies.get(getAdapterPosition()));

        }

    }

}
