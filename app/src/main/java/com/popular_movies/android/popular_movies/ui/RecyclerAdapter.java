package com.popular_movies.android.popular_movies.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.popular_movies.android.popular_movies.R;
import com.popular_movies.android.popular_movies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ewasniecinska on 26.02.2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MovieHolder> {
    private List<Movie> listOfMovies;
    private Context context;
    private int row;
    private OnItemClickListener clickListener;

    @BindView(R.id.layout) FrameLayout layout;
    @BindView(R.id.poster) ImageView poster;
    @BindView(R.id.movie_title) TextView movie_title;
    @BindView(R.id.movie_score) TextView movie_score;


    public RecyclerAdapter (List<Movie> listOfMovies, Context context, int row){
        this.listOfMovies = listOfMovies;
        this.context = context;
        this.row = row;
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {
        FrameLayout layout;
        ImageView poster;
        TextView movie_title;
        TextView movie_score;

        public MovieHolder(View v) {
            super(v);
            layout = (FrameLayout) v.findViewById(R.id.layout);
            poster = (ImageView) v.findViewById(R.id.poster);
            movie_title = v.findViewById(R.id.movie_title);
            movie_score = v.findViewById(R.id.movie_score);

        }
    }

    @Override
    public RecyclerAdapter.MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(row, parent, false);
        return new MovieHolder(view);

    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        String image_url = context.getString(R.string.image_url) + listOfMovies.get(position).getPosterPath();
        String title = listOfMovies.get(position).getTitle();
        holder.movie_title.setText(title);
        String score = listOfMovies.get(position).getVoteAverage();
        holder.movie_score.setText(score);
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.dark_header)
                .error(android.R.drawable.dark_header)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return listOfMovies.size();
    }


}
