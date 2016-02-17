package brotherhood.fichesenglish.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.database.DatabaseHelper;
import brotherhood.fichesenglish.server.ServerRequest;
import brotherhood.fichesenglish.server.enums.ServiceType;
import brotherhood.fichesenglish.server.parameters.Parameters;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        System.out.println("ON CREATE");
        downloadDatabase();
    }


    public void downloadDatabase() {
        new ServerRequest(ServiceType.GET_DATABASE, new Parameters().addParam("", ""))
                .setServerRequestListener(new ServerRequest.ServerRequestListener() {
            @Override
            public void onSuccess(final String json) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                        try {
                            JSONArray jsonArray = new JSONObject(json).getJSONArray("data");
                            for(int i = 0; i < jsonArray.length(); i ++) {
                                JSONObject singleObject = jsonArray.getJSONObject(i);
                                dbHelper.saveSingleFiche(singleObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent toMenuActivity = new Intent(SplashActivity.this, MenuActivity.class);
                        startActivity(toMenuActivity);
                    }
                }).start();


            }

            @Override
            public void onError(int code, String description) {

            }
        }).execute();
    }

    public void checkDatabaseVersion() {
        new ServerRequest(ServiceType.CHECK_VERSION, new Parameters().addParam("","")).
                setServerRequestListener(new ServerRequest.ServerRequestListener() {
                    @Override
                    public void onSuccess(String json) {
                        try {
                            JSONObject jsonObject = new JSONObject(json);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(int code, String description) {

                    }
                }).execute();
    }

}



