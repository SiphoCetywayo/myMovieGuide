package com.example.moviemenu.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviemenu.Data.MovieRecyclerViewAdapter;
import com.example.moviemenu.Model.Movies;
import com.example.moviemenu.R;
import com.example.moviemenu.Utils.Constants;
import com.example.moviemenu.Utils.Prefs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private List<Movies> moviesList;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue = Volley.newRequestQueue(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        recyclerView = findViewById(R.id.moviewRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        moviesList = new ArrayList<>();

        Prefs sharePrefs = new Prefs(MainActivity.this);
        String searchItem = sharePrefs.getSearch();
        moviesList = getMoviesList(searchItem);

        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this,moviesList);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        movieRecyclerViewAdapter.notifyDataSetChanged();
    }


    public List<Movies> getMoviesList(final String searchTerm) {
        moviesList.clear();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                Constants.RIGHT_URL + searchTerm + Constants.LEFT_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray movieSearch = response.getJSONArray("Search");
                    for(int i=0; i<response.length(); i++){
                        JSONObject searchObj = movieSearch.getJSONObject(i);

                        Movies movie = new Movies();
                        movie.setTitle(searchObj.getString("Title"));
                        movie.setMovieType("Type: " +searchObj.getString("Type"));
                        movie.setYear("Year Released: " +searchObj.getString("Year"));
                        movie.setPoster(searchObj.getString("Poster"));
                        /*Log.d("MovieTitle", movie.getTitle());*/

                        moviesList.add(movie);
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
        queue.add(jsonReq);
        return moviesList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}