package com.nickwelna.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nickwelna.popularmovies.ReviewListAdapter.ReviewViewHolder;
import com.nickwelna.popularmovies.networking.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ReviewListAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private final List<Review> reviews;

    public ReviewListAdapter(List<Review> reviews) {

        this.reviews = reviews;

    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_list_item, parent, false);

        return new ReviewViewHolder(view, parent.getContext());

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        holder.bind(position);

    }

    @Override
    public int getItemCount() {

        return reviews.size();

    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.reviewer)
        TextView reviewer;
        @BindView(R.id.review)
        TextView review;
        final Context context;

        ReviewViewHolder(View itemView, Context context) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = context;

        }

        void bind(int position) {

            reviewer.setText(context.getString(R.string.author_text, reviews.get(position).getAuthor()));
            review.setText(reviews.get(position).getContent());

        }

    }

}
