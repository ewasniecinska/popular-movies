package com.popular_movies.android.popular_movies;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.popular_movies.android.popular_movies.api.DiscoverResult;
import com.popular_movies.android.popular_movies.api.Movie;
import com.popular_movies.android.popular_movies.api.MoviesService;
import com.popular_movies.android.popular_movies.data.MovieProvider;
import com.popular_movies.android.popular_movies.ui.OnItemClickListener;
import com.popular_movies.android.popular_movies.ui.RecyclerAdapter;
import com.popular_movies.android.popular_movies.ui.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView = null;
    private List<Movie> movies;

    ContentResolver contentResolver;
    Cursor cursor;
    Uri uri;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), calculateNoOfColumns(this));
        recyclerView.setLayoutManager(gridLayoutManager);

        uri = MovieProvider.CONTENT_URI;
        contentResolver = getContentResolver();

        getSavedSortBy();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new OnItemClickListener() {
            @Override
            public void onClick(View view, final int position) {

                Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                intent.putExtra(getString(R.string.ID), movies.get(position).getId());
                Log.d("intent", String.valueOf(movies.get(position).getId()));
                intent.putExtra(getString(R.string.TITLE), movies.get(position).getTitle());
                intent.putExtra(getString(R.string.POSTER), movies.get(position).getPosterPath());
                intent.putExtra(getString(R.string.VOTE_AVERAGE), movies.get(position).getVoteAverage());
                intent.putExtra(getString(R.string.OVERVIEW), movies.get(position).getOverview());
                intent.putExtra(getString(R.string.REALEASE_DATE), movies.get(position).getReleaseDate());

                startActivity(intent);
            }

        }));
        
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

    // menu -> create
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // menu -> items switch
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top_rated:
                getListTopRatedOfMovies();
                return true;
            case R.id.popular:
                getListOfPopularMovies();
                return true;
            case R.id.favorite:
                getFavoriteMovies();
                return true;
        }
        return true;
    }

    public void getSavedSortBy(){
        String sortMode = sharedpreferences.getString(getString(R.string.sort), "");
        switch (sortMode) {
            case "Top rated movies":
                getListTopRatedOfMovies();
                return;
            case "Most popular movies":
                getListOfPopularMovies();
                return;
            case "Favorite":
                getFavoriteMovies();
                return ;
            case "":
                getListTopRatedOfMovies();
                return ;
        }
    }

    public void updateSharedPreference(String sortMode){
        SharedPreferences.Editor edit = sharedpreferences.edit();
        edit.putString(getString(R.string.sort), sortMode);
        edit.commit();
    }

    public void getFavoriteMovies(){
        updateSharedPreference(getString(R.string.favorite_movies));

        setTitle(getString(R.string.favorite_movies));

        // clear current list of movies
        movies = new ArrayList<Movie>();

        // get all favorite movies from database
        cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor.moveToFirst()){
            Movie movie;
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String score = cursor.getString(cursor.getColumnIndex("score"));
                String overview = cursor.getString(cursor.getColumnIndex("overview"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String poster = cursor.getString(cursor.getColumnIndex("poster"));
                movie = new Movie(0, id, false, score, title,
                        null, poster, null, null,
                        null, null, false, overview,
                        date);
                movies.add(movie);

            }while(cursor.moveToNext());
        }
        cursor.close();

        recyclerView.setAdapter(new RecyclerAdapter(movies, getApplicationContext(), R.layout.row_layout));


    }

    public void getListTopRatedOfMovies(){

        updateSharedPreference(getString(R.string.top_rated_movies));

        setTitle(getString(R.string.top_rated_movies));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesService service = retrofit.create(MoviesService.class);
        Call<DiscoverResult> call = service.getTopRatedMovies(getString(R.string.api_key));

        callApi(call);

    }

    public void getListOfPopularMovies(){
        updateSharedPreference(getString(R.string.popular_movies));

        setTitle(getString(R.string.popular_movies));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesService service = retrofit.create(MoviesService.class);
        Call<DiscoverResult> call = service.getMostPopularMovies(getString(R.string.api_key));

        callApi(call);
    }

    public void callApi(Call<DiscoverResult> call){
        call.enqueue(new Callback<DiscoverResult>() {
            @Override
            public void onResponse(Call<DiscoverResult> call, Response<DiscoverResult> response) {
                movies = response.body().getListOfMovies();
                recyclerView.setAdapter(new RecyclerAdapter(movies, getApplicationContext(), R.layout.row_layout));
            }
            @Override
            public void onFailure(Call<DiscoverResult> call, Throwable throwable) {
            }
        });
    }

}



