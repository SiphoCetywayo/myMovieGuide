package com.example.moviemenu.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviemenu.Model.Movies;
import com.example.moviemenu.R;
import com.example.moviemenu.Utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetails extends AppCompatActivity {
    private Movies movies;
    private RequestQueue requestQueue;
    private String movieId;
    TextView mTitle;
    TextView mReleaseDate;
    TextView mCategory;
    TextView mRatings;
    TextView mRunTime;
    TextView mDirector;
    TextView mActors;
    TextView mWriters;
    TextView mPlot;
    TextView mBoxOffice;
    ImageView mMovieDetailsPoster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        BottomNavigationView bottomNavigationView = findViewById(R.id.myNavBar);
        final Menu menu = bottomNavigationView.getMenu();
        final MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        requestQueue = Volley.newRequestQueue(this);
        movies = (Movies) getIntent().getSerializableExtra("movies");
        movieId = movies.getImbdId();
        uiSetup();

        getMoviesDetails(movieId);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if (menuItem != item) {
                    moveBacktoHomeActivity();
                }
            }
    });
}

    private void moveBacktoHomeActivity() {
        Intent homeInten = new Intent(MovieDetails.this, MainActivity.class);
        startActivity(homeInten);
    }

    private void getMoviesDetails(String movieID) {

        JsonObjectRequest jsnObj = new JsonObjectRequest(Request.Method.GET,
                Constants.ID_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.has("Ratings")) {
                        JSONArray ratings = response.getJSONArray("Ratings");
                        String Source = null;
                        String Value = null;

                        if (ratings.length() > 0) {
                            JSONObject mRating = ratings.getJSONObject(ratings.length() - 1);
                            Source = mRating.getString("Source");
                            Value = mRating.getString("Value");
                            mRatings.setText(Source + " " + Value);
                        } else {
                            mRatings.setText("Ratings: N/A");
                        }

                        mTitle.setText(movies.getTitle());
                        mCategory.setText(movies.getMovieType());
                        mReleaseDate.setText(movies.getYear());
                        mRunTime.setText("Runtime: " + response.getString("Runtime"));
                        mDirector.setText("Directed by: " + response.getString("Director"));
                        mActors.setText("Actors: " + response.getString("Actors"));
                        mWriters.setText("Writers: " + response.getString("Writer"));
                        mPlot.setText("Plot: " + response.getString("Plot"));
                        mBoxOffice.setText("BoxOffice: " + response.getString("BoxOffice"));
                        String Poster = movies.getPoster();

                        Picasso.with(getApplicationContext())
                                .load(Poster)
                                .placeholder(android.R.drawable.ic_btn_speak_now)
                                .into(mMovieDetailsPoster);

                        movies.setPoster(response.getString("Poster"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsnObj);
    }

    private void uiSetup() {
        mTitle = findViewById(R.id.MovieTitleDt);
        mReleaseDate = findViewById(R.id.releaseIdDT);
        mCategory = findViewById(R.id.categoryDt);
        mRatings = findViewById(R.id.RatingDt);
        mRunTime = findViewById(R.id.RuntimeDt);
        mDirector = findViewById(R.id.directorDt);
        mActors = findViewById(R.id.actorDetails);
        mPlot = findViewById(R.id.plotDetails);
        mWriters = findViewById(R.id.writersDetails);
        mBoxOffice = findViewById(R.id.boxOfficeDet);
        mMovieDetailsPoster = findViewById(R.id.MovieDtPoster);
    }

}