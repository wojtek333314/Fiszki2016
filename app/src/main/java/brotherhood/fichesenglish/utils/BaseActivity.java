package brotherhood.fichesenglish.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    private static AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        sharedPrefsHelper = new SharedPrefsHelper(this);
        databaseHelper = new DatabaseHelper(this);
        customOnCreate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    protected static SharedPrefsHelper getSharedPrefsHelper() {
        return sharedPrefsHelper;
    }

    protected static DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    protected abstract void customOnCreate();

    protected void showOkMsgBox(String title, String msg, DialogInterface.OnClickListener clickListener) {
        if (alertDialog != null && alertDialog.isShowing())
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null && !title.equals(""))
            builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(getString(R.string.ok_label), clickListener);
        alertDialog = builder.create();
        alertDialog.show();
    }

    protected void showMsgBox(String title, String msg, String buttonText, DialogInterface.OnClickListener clickListener) {
        if (alertDialog != null && alertDialog.isShowing())
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null && !title.equals(""))
            builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(buttonText, clickListener);
        alertDialog = builder.create();
        alertDialog.show();
    }

    protected void showYesNoMsgBox(String title, String msg
            , DialogInterface.OnClickListener yesButtonClickListener, DialogInterface.OnClickListener noButtonClickListener) {
        if (alertDialog != null && alertDialog.isShowing())
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null && !title.equals(""))
            builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(getString(R.string.yes_label), yesButtonClickListener);
        builder.setNegativeButton(getString(R.string.no_label), noButtonClickListener);
        alertDialog = builder.create();
        alertDialog.show();
    }

    protected boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
