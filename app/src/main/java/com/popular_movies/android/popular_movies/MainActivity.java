package com.popular_movies.android.popular_movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.popular_movies.android.popular_movies.api.Movie;
import com.popular_movies.android.popular_movies.api.DiscoverResult;
import com.popular_movies.android.popular_movies.api.MoviesService;
import com.popular_movies.android.popular_movies.ui.OnItemClickListener;
import com.popular_movies.android.popular_movies.ui.RecyclerAdapter;
import com.popular_movies.android.popular_movies.ui.RecyclerTouchListener;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recyclerView -> create
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // recyclerView -> create and set gridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);


        // get list of DiscoverResult from movie database
        getListTopRatedOfMovies();


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new OnItemClickListener() {
            @Override
            public void onClick(View view, final int position) {

                Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                intent.putExtra(getString(R.string.ID), movies.get(position).getId());
                intent.putExtra(getString(R.string.TITLE), movies.get(position).getTitle());
                intent.putExtra(getString(R.string.POSTER), movies.get(position).getPosterPath());
                intent.putExtra(getString(R.string.VOTE_COUNT), movies.get(position).getVoteCount());
                intent.putExtra(getString(R.string.VOTE_AVERAGE), movies.get(position).getVoteAverage());
                intent.putExtra(getString(R.string.ORGINAL_LANGUAGE), movies.get(position).getOrginalLanguage());
                intent.putExtra(getString(R.string.ORGINAL_TITLE), movies.get(position).getOriginalTitle());
                intent.putExtra(getString(R.string.BACKDROP_PATH), movies.get(position).getBackdropPath());
                intent.putExtra(getString(R.string.IF_ADULT), movies.get(position).getIfAdult());
                intent.putExtra(getString(R.string.OVERVIEW), movies.get(position).getOverview());
                intent.putExtra(getString(R.string.REALEASE_DATE), movies.get(position).getReleaseDate());

                startActivity(intent);
            }

        }));
        
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
        }
        return true;
    }

    public void getListTopRatedOfMovies(){
        // set screen title
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
        // set screen title
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



