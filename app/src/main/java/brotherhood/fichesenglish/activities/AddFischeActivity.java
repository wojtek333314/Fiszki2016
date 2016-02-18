package brotherhood.fichesenglish.activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.server.ServerRequest;
import brotherhood.fichesenglish.server.enums.ServiceType;
import brotherhood.fichesenglish.server.parameters.Parameters;
import brotherhood.fichesenglish.utils.BaseActivity;

public class AddFischeActivity extends BaseActivity {


    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_add_fische);
        Button bt_add = (Button) findViewById(R.id.bt_add);
        final EditText et_add_pl = (EditText) findViewById(R.id.et_add_pl);
        final EditText et_add_eng = (EditText) findViewById(R.id.et_add_eng);


        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline())
                    showOkMsgBox(getResources().getString(R.string.no_network), getResources().getString(R.string.check_network), null);
                else {
                    if (validateUserData(et_add_pl) && validateUserData(et_add_eng))
                        addFicheToDatabase(et_add_pl, et_add_eng);
                    else
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public boolean validateUserData(EditText editText) {
        return editText.getText().length() > 1;

    }

    public void addFicheToDatabase(EditText pl, EditText eng) {
        new ServerRequest(ServiceType.ADD_FICHE, new Parameters()
                .addParam("pl", pl.getText().toString())
                .addParam("ang", eng.getText().toString()))
                .setServerRequestListener(new ServerRequest.ServerRequestListener() {

                    @Override
                    public void onSuccess(String json) {
                        JSONObject response = null;
                        try {
                            response = new JSONObject(json);
                            if (response.getBoolean("data")) {
                                showOkMsgBox("Sukces!",getResources().getString(R.string.succesfully_added),null);
                            }
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
