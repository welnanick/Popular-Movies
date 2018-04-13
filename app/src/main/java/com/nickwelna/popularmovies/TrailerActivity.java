package com.nickwelna.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nickwelna.popularmovies.networking.Video;
import com.nickwelna.popularmovies.networking.VideoList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerActivity extends AppCompatActivity {

    @BindView(R.id.trailer_recycler_view)
    RecyclerView trailerRecyclerView;
    @BindView(R.id.no_trailers_text_view)
    TextView noTrailersTextView;
    private final List<Video> trailers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        VideoList videoList = intent.getParcelableExtra(MovieDetailActivity.VIDEOS_TAG);

        for (Video video : videoList.getResults()) {

            if (video.getType().equals("Trailer")) {

                trailers.add(video);

            }

        }

        if (trailers.size() > 0) {

            noTrailersTextView.setVisibility(View.GONE);
            trailerRecyclerView.setHasFixedSize(true);
            trailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            trailerRecyclerView.setAdapter(new TrailerListAdapter(trailers));

        }
        else {

            noTrailersTextView.setVisibility(View.VISIBLE);

        }

    }

}
