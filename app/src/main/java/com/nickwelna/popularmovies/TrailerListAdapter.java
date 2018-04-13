package com.nickwelna.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nickwelna.popularmovies.TrailerListAdapter.TrailerViewHolder;
import com.nickwelna.popularmovies.networking.Video;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerListAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private final List<Video> trailers;

    public TrailerListAdapter(List<Video> trailers) {

        this.trailers = trailers;

    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trailer_list_item, parent, false);

        return new TrailerViewHolder(view, parent.getContext());

    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {

        holder.bind(position);

    }

    @Override
    public int getItemCount() {

        return trailers.size();

    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        @BindView(R.id.trailer_text_view)
        TextView trailerTextView;
        final Context context;

        TrailerViewHolder(View itemView, Context context) {

            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            this.context = context;

        }

        void bind(int position) {

            trailerTextView.setText(context.getString(R.string.trailer_text, (position + 1)));

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            String id = trailers.get(position).getKey();

            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            try {

                context.startActivity(appIntent);

            }
            catch (ActivityNotFoundException ex) {

                context.startActivity(webIntent);

            }

        }

    }

}
