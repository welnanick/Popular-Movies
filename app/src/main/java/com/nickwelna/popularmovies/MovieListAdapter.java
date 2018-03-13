package com.nickwelna.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.nickwelna.popularmovies.MovieListAdapter.MovieListViewHolder;
import com.nickwelna.popularmovies.networking.MovieListResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListViewHolder> {

    private List<MovieListResult> movies = null;
    private final RequestManager glide;
    private final MovieListAdapterOnClickHandler onClickHandler;

    public void clearMovies() {

        if (movies != null) {

            int size = movies.size();
            movies = null;
            notifyItemRangeRemoved(0, size);

        }

    }

    interface MovieListAdapterOnClickHandler {

        void onClick(int movieId);

    }

    public MovieListAdapter(RequestManager glide, MovieListAdapterOnClickHandler onClickHandler) {

        this.glide = glide;
        this.onClickHandler = onClickHandler;

    }

    public List<MovieListResult> getMovies() {

        return movies;

    }

    public void addMovies(@NonNull List<MovieListResult> movies) {

        if (this.movies == null) {

            this.movies = new ArrayList<>();

        }

        for (MovieListResult movie : movies) {

            this.movies.add(movie);
            notifyItemInserted(this.movies.size() - 1);

        }

    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);

        return new MovieListViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {

        MovieListResult movie = movies.get(position);
        holder.bind(movie.getPosterPath());

    }

    @Override
    public int getItemCount() {

        if (movies == null) {

            return 0;

        }

        return movies.size();
    }

    public class MovieListViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        @BindView(R.id.movie_poster)
        ImageView moviePoster;
        Context context;

        public MovieListViewHolder(View itemView, Context context) {

            super(itemView);

            itemView.setOnClickListener(this);

            ButterKnife.bind(this, itemView);

            this.context = context;

        }

        public void bind(String urlText) {

            RequestOptions test = new RequestOptions().override(Target.SIZE_ORIGINAL);
            glide.load(context.getString(R.string.image_url_base) +
                    context.getString(R.string.grid_image_size) + urlText).apply(test)
                 .into(moviePoster);

        }

        @Override
        public void onClick(View v) {

            onClickHandler.onClick(movies.get(getAdapterPosition()).getId());

        }

    }

}
