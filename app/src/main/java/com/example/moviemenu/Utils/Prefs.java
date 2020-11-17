package com.example.moviemenu.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

public class Prefs {
    SharedPreferences shredPrefs;

    //Storing data into SharedPreferences
    public Prefs(@NotNull Activity activity) {
        shredPrefs = activity.getPreferences(Context.MODE_PRIVATE);
    }

    /*Storing the key and its value as the data is fetched from edit text search*/
    public void setSearch(String search) {
        shredPrefs.edit().putString("Search", search).commit();
    }

    public void setIdsearch(String search){
        shredPrefs.edit().putString("Id", search).commit();
    }

    public String getSearch(){
        return shredPrefs.getString("Search", "Superman");
    }

    public String getIdSearch(){
        return shredPrefs.getString("Id","tt0099387");
    }
}
