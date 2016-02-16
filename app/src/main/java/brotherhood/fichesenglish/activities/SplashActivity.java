package brotherhood.fichesenglish.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import brotherhood.fichesenglish.R;
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
            public void onSuccess(String json) {
                System.out.println(json);
                Intent toMenuActivity = new Intent(SplashActivity.this, MenuActivity.class);
                startActivity(toMenuActivity);
            }

            @Override
            public void onError(int code, String description) {

            }
        }).execute();
    }
}



