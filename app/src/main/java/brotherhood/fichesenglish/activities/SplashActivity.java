package brotherhood.fichesenglish.activities;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.server.ServerRequest;
import brotherhood.fichesenglish.server.enums.ServiceType;
import brotherhood.fichesenglish.server.parameters.Parameters;
import brotherhood.fichesenglish.utils.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_splash);
        checkDatabaseVersion();
    }

    public void downloadDatabase() {
        new ServerRequest(ServiceType.GET_DATABASE, new Parameters())
                .setServerRequestListener(new ServerRequest.ServerRequestListener() {
                    @Override
                    public void onSuccess(final String json) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONArray jsonArray = new JSONObject(json).getJSONObject("data").getJSONArray("json");
                                    JSONObject jsonObject = new JSONObject(json).getJSONObject("data");
                                    String version = jsonObject.getString("version");
                                    getSharedPrefsHelper().putString("version", version);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject singleObject = jsonArray.getJSONObject(i);
                                        getDatabaseHelper().saveSingleFiche(singleObject);
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
        new ServerRequest(ServiceType.CHECK_VERSION, new Parameters())
                .setServerRequestListener(new ServerRequest.ServerRequestListener() {
                    @Override
                    public void onSuccess(String json) {
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            String version = jsonObject.getJSONObject("data").getString("version");
                            String localVersion = getSharedPrefsHelper().getString("version");

                            if (localVersion != null && localVersion.equals(version)) {
                                Intent toMenuActivity = new Intent(SplashActivity.this, MenuActivity.class);
                                startActivity(toMenuActivity);
                            } else
                                downloadDatabase();

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



