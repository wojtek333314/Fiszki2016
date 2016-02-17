package brotherhood.fichesenglish.utils;

import android.app.Activity;
import android.os.Bundle;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.database.DatabaseHelper;
import brotherhood.fichesenglish.database.SharedPrefsHelper;

/**
 * Created by Wojtek on 2016-02-17.
 */
public abstract class BaseActivity extends Activity {
    private static SharedPrefsHelper sharedPrefsHelper;
    private static DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        sharedPrefsHelper = new SharedPrefsHelper(this);
        databaseHelper = new DatabaseHelper(this);
        customOnCreate();
    }

    protected abstract void customOnCreate();

    protected static SharedPrefsHelper getSharedPrefsHelper() {
        return sharedPrefsHelper;
    }

    protected static DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }
}
