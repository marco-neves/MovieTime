package com.illicitintelligence.movietime.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.illicitintelligence.movietime.R;
import com.illicitintelligence.movietime.model.Movie;
import com.illicitintelligence.movietime.util.Constants;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    public interface MovieDetailInterface {
        void prepareMovieDetail(Movie movie, ImageView view);
    }

    private Context context;
    private List<Movie> movies;
    private MovieDetailInterface movieDetailInterface;

    public RecyclerAdapter(Context context, List<Movie> movies, MovieDetailInterface movieDetailInterface) {
        this.movies = movies;
        this.context = context;
        this.movieDetailInterface = movieDetailInterface;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapter.MyViewHolder holder, final int position) {
        String titleText = movies.get(position).getTitle().trim();
        holder.title.setText(titleText);
        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,24);
        String movieURL = movies.get(position).getPosterPath();
        movieURL = Constants.BASE_URL_IMAGE+movieURL;
        Glide.with(context).load(movieURL).placeholder(R.drawable.ic_photo)
                .into(holder.icon);
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieDetailInterface.prepareMovieDetail(movies.get(position),holder.icon);
            }
        });
        ViewCompat.setTransitionName(holder.icon,Constants.TRANSITION_NAME_BASE+movies.get(position).getId());
    }
    @Override
    public int getItemCount() {
        return movies.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title_textview);
            icon = itemView.findViewById(R.id.movie_icon_imageview);
        }
    }
}
