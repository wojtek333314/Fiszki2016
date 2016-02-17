package brotherhood.fichesenglish.activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.server.ServerRequest;
import brotherhood.fichesenglish.server.enums.ServiceType;
import brotherhood.fichesenglish.server.parameters.Parameters;
import brotherhood.fichesenglish.utils.BaseActivity;

public class AddFischeActivity extends BaseActivity {


    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_add_fische);
        Button bt_add = (Button)findViewById(R.id.bt_add);
        final EditText et_add_pl = (EditText)findViewById(R.id.et_add_pl);
        final EditText et_add_eng = (EditText)findViewById(R.id.et_add_eng);



        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ServerRequest(ServiceType.ADD_FICHE, new Parameters()
                        .addParam("pl", et_add_pl.getText().toString())
                        .addParam("ang",et_add_eng.getText().toString()))
                        .setServerRequestListener(new ServerRequest.ServerRequestListener() {

                    @Override
                    public void onSuccess(String json) {
                    }

                    @Override
                    public void onError(int code, String description) {

                    }
                }).execute();
            }
        });

    }
}
