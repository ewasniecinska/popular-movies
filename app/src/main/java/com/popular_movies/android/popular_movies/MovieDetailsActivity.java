package com.popular_movies.android.popular_movies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.popular_movies.android.popular_movies.api.MovieReview;
import com.popular_movies.android.popular_movies.api.MovieReviewResult;
import com.popular_movies.android.popular_movies.api.MovieTrailer;
import com.popular_movies.android.popular_movies.api.MovieTrailerResult;
import com.popular_movies.android.popular_movies.api.MoviesService;
import com.popular_movies.android.popular_movies.data.MovieDatabase;
import com.popular_movies.android.popular_movies.data.MovieProvider;
import com.popular_movies.android.popular_movies.data.SQLiteHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    public List<String> listReviews = new ArrayList<String>();
    public List<TrailerListItem> listTrailers = new ArrayList<TrailerListItem>();
    public Integer movieId;
    public SQLiteHelper myDb;
    Button addToFavorite;
    boolean isFavorite = false;

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

        intent = getIntent();
        movieId = intent.getIntExtra(getString(R.string.ID), 0);

        myDb = new SQLiteHelper(getBaseContext());

        uri = MovieProvider.CONTENT_URI;

        contentResolver = getContentResolver();

        getMovieTrailers();

    }


    public void updateUI(final Intent intent) {
        final TextView title = findViewById(R.id.title);
        final TextView voteAvg = findViewById(R.id.vote_average);
        final TextView releaseDate = findViewById(R.id.release_date);
        final TextView overview = findViewById(R.id.overview);
        final ImageView poster = findViewById(R.id.poster);
        addToFavorite = findViewById(R.id.addToFavorite);

        addToFavorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new FavoriteButtonAction().execute();
            }
        });

        final TabHost mTabHost = (TabHost) findViewById(R.id.taphost);

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


        ListView list = (ListView) findViewById(R.id.list_tab2);


        String[] stockArr = new String[listReviews.size()];
        stockArr = listReviews.toArray(stockArr);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_tab2_row, stockArr);

        list.setAdapter(adapter);

        //Tab 3
        spec = mTabHost.newTabSpec("TRAILERS");
        spec.setContent(R.id.tab3);
        spec.setIndicator("TRAILERS");
        mTabHost.addTab(spec);

        final ListView trailersList = (ListView) findViewById(R.id.list_tab3);

        String[] trailersNamesArray = new String[listTrailers.size()];

        for (int i = 0; i < listTrailers.size(); i++) {
            TrailerListItem item = listTrailers.get(i);
            trailersNamesArray[i] = item.trailerName;
        }

        ArrayAdapter<String> trailersListAdapter = new ArrayAdapter<String>(this, R.layout.list_tab2_row, trailersNamesArray);

        trailersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TrailerListItem item = listTrailers.get(position);
                String url = item.trailerUrl;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        trailersList.setAdapter(trailersListAdapter);
        title.setText(intent.getStringExtra(getString(R.string.TITLE)));
        voteAvg.setText(intent.getStringExtra(getString(R.string.VOTE_AVERAGE)));
        releaseDate.setText(intent.getStringExtra(getString(R.string.REALEASE_DATE)));
        overview.setText(intent.getStringExtra(getString(R.string.OVERVIEW)));

        // set poster
        String image_url = getString(R.string.image_url) + intent.getStringExtra(getString(R.string.POSTER));
        Picasso.with(getApplicationContext())
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(poster);
    }


    public void getMovieTrailers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesService service = retrofit.create(MoviesService.class);

        Call<MovieReviewResult> callReviews = service.getMovieReviews(movieId, getString(R.string.api_key));

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

        Call<MovieTrailerResult> callTrailers = service.getMovieTrailers(movieId, getString(R.string.api_key));

        callTrailers.enqueue(new Callback<MovieTrailerResult>() {
            @Override
            public void onResponse(Call<MovieTrailerResult> call, Response<MovieTrailerResult> response) {
                trailers = response.body().getMovieTrailers();
                Log.e("trailers", new Integer(trailers.size()).toString());
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
                //myDb.deleteMovie(intent.getIntExtra(getString(R.string.ID), 0));
                int movieId = intent.getIntExtra(getString(R.string.ID), 0);
                contentResolver.delete(uri, String.valueOf(movieId), null);
            } else {
                //long row = myDb.insertMovie(getContentValues());

                contentResolver.insert(uri, getContentValues());
            }
            return isSuccessful;
        }

        @Override
        protected void onPostExecute(Boolean isSuccessful) {
            super.onPostExecute(isSuccessful);

            if (isFavorite) {
                addToFavorite.setText("ADD TO FAVORITE");
                isFavorite = false;
                showToast("Movie deleted from favorite");
            } else {
                addToFavorite.setText("DELETE FROM FAVORITE");
                isFavorite = true;
                showToast("Movie added to favorite");

            }
        }

        private ContentValues getContentValues() {
            ContentValues values = new ContentValues();
            values.put(MovieDatabase.COLUMN_ID, intent.getIntExtra(getString(R.string.ID), 0));
            values.put(MovieDatabase.COLUMN_TITLE, intent.getStringExtra(getString(R.string.TITLE)));
            values.put(MovieDatabase.COLUMN_SCORE, intent.getStringExtra(getString(R.string.VOTE_AVERAGE)));
            values.put(MovieDatabase.COLUMN_OVERVIEW, intent.getStringExtra(getString(R.string.OVERVIEW)));
            values.put(MovieDatabase.COLUMN_DATE, intent.getStringExtra(getString(R.string.REALEASE_DATE)));
            values.put(MovieDatabase.COLUMN_POSTER, intent.getStringExtra("POSTER"));
            return values;
        }
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void checkIfMovieInDb(){
        boolean movieInDb = myDb.CheckIsDataAlreadyInDBorNot("movies", "id", intent.getIntExtra(getString(R.string.ID),0));
        if(movieInDb){
            addToFavorite.setText("DELETE FROM FAVORITE");
            isFavorite = true;
        } else{
            addToFavorite.setText("ADD TO FAVORITE");
            isFavorite = false;
        }
    }
}



