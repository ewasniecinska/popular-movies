package com.popular_movies.android.popular_movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.popular_movies.android.popular_movies.api.MovieReview;
import com.popular_movies.android.popular_movies.api.MovieTrailer;
import com.popular_movies.android.popular_movies.api.MovieTrailerResult;
import com.popular_movies.android.popular_movies.api.MoviesService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {
    int position;
    public List<MovieTrailer> trailers;
    public List<MovieReview> reviews;
    public int size = 0;
    public Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        intent = getIntent();

        getMovieTrailers();





    }


    public void updateUI (Intent intent){
        TextView title = findViewById(R.id.title);
        TextView voteAvg = findViewById(R.id.vote_average);
        //TextView voteCount = findViewById(R.id.vote_count);
        TextView releaseDate = findViewById(R.id.release_date);
        TextView overview = findViewById(R.id.overview);
        //TextView orginalTitle = findViewById(R.id.orginal_title);
        ImageView poster = findViewById(R.id.poster);
        Button addToFavorite = findViewById(R.id.addToFavorite);

        TabHost mTabHost = (TabHost)findViewById(R.id.taphost);

        mTabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = mTabHost.newTabSpec("OVERVIEW");
        spec.setContent(R.id.tab1);
        spec.setIndicator("OVERVIEW");
        mTabHost.addTab(spec);

        //Tab 2
        spec = mTabHost.newTabSpec("REVIEWS");
        spec.setContent(R.id.tab2);
        spec.setIndicator("REVIEWS");
        mTabHost.addTab(spec);

        //Tab 3
        spec = mTabHost.newTabSpec("TRAILERS");
        spec.setContent(R.id.tab3);
        spec.setIndicator("TRAILERS");
        mTabHost.addTab(spec);



        // set title
        title.setText(intent.getStringExtra(getString(R.string.TITLE)));


        // set vote count
        //voteCount.setText(Integer.toString(intent.getIntExtra(getString(R.string.VOTE_COUNT), -1)));

        // set vote avg.
        voteAvg.setText(intent.getStringExtra(getString(R.string.VOTE_AVERAGE)));

        // set orginal title
        //orginalTitle.setText(intent.getStringExtra(getString(R.string.ORGINAL_TITLE)));

        // set realease date
        releaseDate.setText(intent.getStringExtra(getString(R.string.REALEASE_DATE)));

        // set overview
        overview.setText(intent.getStringExtra(getString(R.string.OVERVIEW)));
        //overview.setText(String.valueOf(reviews.get(0).getReviewAuthor()));


        // set poster
        String image_url = getString(R.string.image_url) + intent.getStringExtra(getString(R.string.POSTER));
        Picasso.with(getApplicationContext())
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(poster);
    }


    public void getMovieTrailers(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesService service = retrofit.create(MoviesService.class);
        Call<MovieTrailerResult> call = service.getMovieTrailers("372058", getString(R.string.api_key));

        call.enqueue(new Callback<MovieTrailerResult>() {
            @Override
            public void onResponse(Call<MovieTrailerResult> call, Response<MovieTrailerResult> response) {
                trailers = response.body().getMovieTrailers();
                updateUI(intent);
           }
            @Override
            public void onFailure(Call<MovieTrailerResult> call, Throwable throwable) {
            }
        });

    }


}
