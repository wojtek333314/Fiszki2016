package brotherhood.fichesenglish.database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefsHelper {
    private static final String SHARED_PREFERENCES_NAME = "fiche";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefsHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getString(String key){
        return sharedPreferences.getString(key,null);
    }

    public void putString(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }
}
