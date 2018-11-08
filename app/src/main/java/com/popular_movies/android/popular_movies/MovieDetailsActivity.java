package com.popular_movies.android.popular_movies;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.popular_movies.android.popular_movies.api.MoviesService;
import com.popular_movies.android.popular_movies.data.MovieDatabase;
import com.popular_movies.android.popular_movies.data.MovieProvider;
import com.popular_movies.android.popular_movies.data.SQLiteHelper;
import com.popular_movies.android.popular_movies.models.Movie;
import com.popular_movies.android.popular_movies.models.MovieReview;
import com.popular_movies.android.popular_movies.models.MovieReviewResult;
import com.popular_movies.android.popular_movies.models.MovieTrailer;
import com.popular_movies.android.popular_movies.models.MovieTrailerResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    public List<String> listReviews = new ArrayList<String>();
    public List<TrailerListItem> listTrailers = new ArrayList<TrailerListItem>();
    public SQLiteHelper myDb;
    boolean isFavorite = false;
    MenuItem addToFav;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.vote_average) TextView voteAvg;
    @BindView(R.id.overview) TextView overview;
    @BindView(R.id.poster) ImageView poster;
    //@BindView(R.id.list_tab2) ListView reviewsTabList;
    //@BindView(R.id.list_tab3) ListView trailersTabList;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.btn_watch_trailer)
    Button watchTrailer;

    Movie movie;
    ContentResolver contentResolver;
    Uri uri;


    public class TrailerListItem {
        String trailerUrl;
        String trailerName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");

        movie = getIntent().getParcelableExtra("MOVIE");
        Log.d("TEST", String.valueOf(movie.getId()));

        myDb = new SQLiteHelper(getBaseContext());

        uri = MovieProvider.CONTENT_URI;

        contentResolver = getContentResolver();

        getMovieTrailers();

    }


    public void updateUI(final Intent intent) {
        setPoster();
        //setUpTabReviews();
        setUpTabTrailers();

        title.setText(movie.getTitle());
        voteAvg.setText(movie.getVoteAverage());
        overview.setText(movie.getOverview());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        addToFav = menu.findItem(R.id.add_to_favorite);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_favorite:
                new FavoriteButtonAction().execute();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setPoster(){
        String image_url = getString(R.string.image_url) + movie.getBackdropPath();
        Picasso.with(getApplicationContext())
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(poster);
    }

    /*
    public void setUpTabReviews(){

        String[] newStringList = new String[listReviews.size()];
        newStringList = listReviews.toArray(newStringList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_tab2_row, newStringList);

        reviewsTabList.setAdapter(adapter);

    } */
    public void setUpTabTrailers(){

        if(!listTrailers.isEmpty()){
           watchTrailer.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String url = listTrailers.get(0).trailerUrl;
                   Intent intent = new Intent(Intent.ACTION_VIEW);
                   intent.setData(Uri.parse(url));
                   startActivity(intent);

                   if (intent.resolveActivity(getPackageManager()) != null) {
                       startActivity(intent);
                   }
               }
           });

        } else {
            watchTrailer.setVisibility(View.GONE);
        }

    }


    public void getMovieTrailers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesService service = retrofit.create(MoviesService.class);

        Call<MovieReviewResult> callReviews = service.getMovieReviews(movie.getId(), getString(R.string.api_key));


        callReviews.enqueue(new Callback<MovieReviewResult>() {

            @Override
            public void onResponse(Call<MovieReviewResult> call, Response<MovieReviewResult> response) {
                reviews = response.body().getMovieReviews();
                int count = reviews.size();
                for (int i = 0; i < count; i++) {
                    listReviews.add(reviews.get(i).getReviewContent());

                }

            }

            @Override
            public void onFailure(Call<MovieReviewResult> call, Throwable throwable) {

            }


        });

        Call<MovieTrailerResult> callTrailers = service.getMovieTrailers(movie.getId(), getString(R.string.api_key));

        callTrailers.enqueue(new Callback<MovieTrailerResult>() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<MovieTrailerResult> call, Response<MovieTrailerResult> response) {
                trailers = response.body().getMovieTrailers();
                int count = trailers.size();
                for (int i = 0; i < count; i++) {
                    TrailerListItem item = new TrailerListItem();
                    item.trailerName = trailers.get(i).getTrailerName();
                    item.trailerUrl = trailers.get(i).getTrailerUrl();
                    listTrailers.add(item);
                }

                updateUI(intent);
                checkIfMovieInDb();

            }

            @Override
            public void onFailure(Call<MovieTrailerResult> call, Throwable throwable) {
            }
        });

    }

    private class FavoriteButtonAction extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            boolean isSuccessful = true;
            if (isFavorite) {
                contentResolver.delete(uri, String.valueOf(movie.getId()), null);
            } else {
                contentResolver.insert(uri, getContentValues());
            }
            return isSuccessful;
        }

        @Override
        protected void onPostExecute(Boolean isSuccessful) {
            super.onPostExecute(isSuccessful);

            if (isFavorite) {
                isFavorite = false;
                addToFav.setIcon(getDrawable(R.drawable.ic_bookmark));
                showToast(getString(R.string.delete_message));
            } else {
                isFavorite = true;
                addToFav.setIcon(getDrawable(R.drawable.ic_bookmark_full));
                showToast(getString(R.string.add_message));

            }
        }

        private ContentValues getContentValues() {
            ContentValues values = new ContentValues();
            values.put(MovieDatabase.COLUMN_ID, movie.getId());
            values.put(MovieDatabase.COLUMN_TITLE, movie.getTitle());
            values.put(MovieDatabase.COLUMN_SCORE, movie.getVoteAverage());
            values.put(MovieDatabase.COLUMN_OVERVIEW, movie.getOverview());
            values.put(MovieDatabase.COLUMN_DATE, movie.getReleaseDate());
            values.put(MovieDatabase.COLUMN_POSTER, movie.getPosterPath());
            return values;
        }
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void checkIfMovieInDb(){
        boolean movieInDb = myDb.CheckIsDataAlreadyInDBorNot("movies", "id", movie.getId());
        if(movieInDb){
            isFavorite = true;
            addToFav.setIcon(getDrawable(R.drawable.ic_bookmark_full));
        } else{
            isFavorite = false;
            addToFav.setIcon(getDrawable(R.drawable.ic_bookmark));

        }
    }
}



