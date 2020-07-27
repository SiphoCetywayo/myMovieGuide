package com.example.moviemenu.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

  SharedPreferences sharedPrefs;

  public Prefs(Activity activity){
      sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE);
  }

  public void setSearch(String search){
       sharedPrefs.edit().putString("Search",search).commit();
  }

  public String getSearch(){
      return sharedPrefs.getString("Search", "Batman");
  }
}
