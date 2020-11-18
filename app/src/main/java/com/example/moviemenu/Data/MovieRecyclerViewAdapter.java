package com.example.moviemenu.Data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviemenu.Activities.MovieDetails;
import com.example.moviemenu.Model.Movies;
import com.example.moviemenu.R;
import com.squareup.picasso.Picasso;

import java.util.List;
 /*MovieRecyclerViewAdapter class constructed to control recycling views functionality*/
public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {
    private List<Movies> moviesList;
    Context context;

    public MovieRecyclerViewAdapter(Context context, List<Movies> moviesList) {
        this.moviesList = moviesList;
        this.context = context;

    }

    @NonNull
    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerViewAdapter.ViewHolder holder, int position) {
        Movies movies = moviesList.get(position);
        String posterLink = movies.getPoster();

        holder.title.setText(movies.getTitle());
        holder.type.setText(movies.getMovieType());
        holder.year.setText(movies.getYear());

        Picasso.with(context)
                .load(posterLink)
                .placeholder(android.R.drawable.ic_btn_speak_now)
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView type;
        TextView year;
        ImageView moviePoster;

        @Override
        public void onClick(View v) {

        }

        public ViewHolder(@NonNull View itemView, final Context viewHolderContext) {
            super(itemView);
            context = viewHolderContext;

            title = itemView.findViewById(R.id.movieTitle);
            type = itemView.findViewById(R.id.moviewCategory);
            year = itemView.findViewById(R.id.releasedId);
            moviePoster = itemView.findViewById(R.id.movieImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movies movies = moviesList.get(getAdapterPosition());
                    Intent movieDetails = new Intent(context, MovieDetails.class);
                    movieDetails.putExtra("movies", movies);
                    viewHolderContext.startActivity(movieDetails);
                }
            });
        }
    }
}