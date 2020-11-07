package com.example.moviemenu.Data;

import android.content.Context;
import android.icu.text.CaseMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviemenu.Activities.MainActivity;
import com.example.moviemenu.Model.Movies;
import com.example.moviemenu.R;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;
import java.util.List;

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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerViewAdapter.ViewHolder holder, int position) {
        Movies movies = moviesList.get(position);
        String Poster = movies.getPoster();

        holder.title.setText(movies.getTitle());
        holder.type.setText(movies.getMovieType());
        holder.year.setText(movies.getYear());

        Picasso.with(context)
                .load(Poster)
                .placeholder(android.R.drawable.ic_btn_speak_now)
                .into(holder.movieImgage);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView type;
        TextView year;
        ImageView movieImgage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.movieTitle);
            type = itemView.findViewById(R.id.moviewCategory);
            year = itemView.findViewById(R.id.releasedId);
            movieImgage = itemView.findViewById(R.id.movieImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Toast.makeText(context, "Item is clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}