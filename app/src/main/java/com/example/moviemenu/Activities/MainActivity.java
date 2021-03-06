package com.example.moviemenu.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviemenu.Data.MovieRecyclerViewAdapter;
import com.example.moviemenu.Model.Movies;
import com.example.moviemenu.R;
import com.example.moviemenu.Utils.Constants;
import com.example.moviemenu.Utils.Prefs;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

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
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.myNavBar);
        final Menu menu = bottomNavigationView.getMenu();
        final MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        /*bottomNavigationView sets navigation item lister to listen to click events in the bottom navigation widget*/
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                if (menuItem == item) {
                    displayInputDialog();
                } else if (menuItem != item) {
                    moveToLiveTv();
                }
            }
        });
        setSupportActionBar(toolbar);

        /*this line instantiates newRequestQue from Volley Library*/
        queue = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.moviewRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Prefs sharePrefs = new Prefs(MainActivity.this);
        String searchItem = sharePrefs.getSearch();
        moviesList = new ArrayList<>();


        moviesList = getMoviesList(searchItem);

        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this, moviesList);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        movieRecyclerViewAdapter.notifyDataSetChanged();
    }

    /*Activity switching method from MainActivity to MovieTrailers activity*/
    private void moveToLiveTv() {
        Intent liveTv = new Intent(MainActivity.this, MovieTrailers.class);
        startActivity(liveTv);
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
        displayInputDialog();

        //noinspection SimplifiableIfStatement
        if (id == R.id.new_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*displayInputDialog contain movie search dialog box functionality*/
    public void displayInputDialog() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_page, null);
        final EditText searchEditEntry = view.findViewById(R.id.searchEdt);
        Button btnSubmit = view.findViewById(R.id.submitButton);

        //Setting up dialog with View widget
        alertDialogBuilder.setView(view);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs prefs = new Prefs(MainActivity.this);
                if (!searchEditEntry.getText().toString().isEmpty()) {
                    String movieSearch = searchEditEntry.getText().toString();
                    prefs.setSearch(movieSearch);
                    moviesList.clear();
                    getMoviesList(movieSearch);

                    //movieRecyclerViewAdapter notifies recyclerview of any change occurrence
                    movieRecyclerViewAdapter.notifyDataSetChanged();

                }
                alertDialog.dismiss();
            }
        });
    }
    /*getMovieList gets all the movies from Movie data model also employs JsonObjectRequest to fetch data from omdbapi*/
    public List<Movies> getMoviesList(final String searchTerm) {
        moviesList.clear();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                Constants.RIGHT_URL + searchTerm + Constants.LEFT_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray movieSearch = response.getJSONArray("Search");
                    for (int i = 0; i < movieSearch.length(); i++) {
                        JSONObject searchObj = movieSearch.getJSONObject(i);

                        Movies movie = new Movies();
                        movie.setTitle(searchObj.getString("Title"));
                        movie.setMovieType("Type: " + searchObj.getString("Type"));
                        movie.setYear("Year Released: " + searchObj.getString("Year"));
                        movie.setPoster(searchObj.getString("Poster"));
                        moviesList.add(movie);
                    }

                    movieRecyclerViewAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.v("myError" + error.toString());

            }
        });
        queue.add(jsonReq);
        return moviesList;
    }
}
