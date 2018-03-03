package com.popular_movies.android.popular_movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        Intent intent = getIntent();
        updateUI(intent);


    }

    public void updateUI (Intent intent){
        TextView title = findViewById(R.id.title);
        TextView voteAvg = findViewById(R.id.vote_average);
        TextView voteCount = findViewById(R.id.vote_count);
        TextView releaseDate = findViewById(R.id.release_date);
        TextView overview = findViewById(R.id.overview);
        TextView orginalTitle = findViewById(R.id.orginal_title);
        ImageView poster = findViewById(R.id.poster);


        // set title
        title.setText(intent.getStringExtra(getString(R.string.TITLE)));

        // set vote count
        voteCount.setText(Integer.toString(intent.getIntExtra(getString(R.string.VOTE_COUNT), -1)));

        // set vote avg.
        voteAvg.setText(intent.getStringExtra(getString(R.string.VOTE_AVERAGE)));

        // set orginal title
        orginalTitle.setText(intent.getStringExtra(getString(R.string.ORGINAL_TITLE)));

        // set realease date
        releaseDate.setText(intent.getStringExtra(getString(R.string.REALEASE_DATE)));

        // set overview
        overview.setText(intent.getStringExtra(getString(R.string.OVERVIEW)));

        // set poster
        String image_url = getString(R.string.image_url) + intent.getStringExtra(getString(R.string.POSTER));
        Picasso.with(getApplicationContext())
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(poster);
    }
}
