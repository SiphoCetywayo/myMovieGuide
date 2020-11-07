package com.example.moviemenu.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    SharedPreferences shredPrefs;

    public Prefs(Activity activity) {
        shredPrefs = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public void setSearch(String search) {
        shredPrefs.edit().putString("Search", search).commit();
    }

    public String getSearch(){
        return shredPrefs.getString("Search", "Batman");
    }
}
