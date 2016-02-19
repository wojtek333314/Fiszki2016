package brotherhood.fichesenglish.activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.models.FicheModel;
import brotherhood.fichesenglish.utils.BaseActivity;

public class AddFischeActivity extends BaseActivity {
    private EditText et_add_pl;
    private EditText et_add_eng;

    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_add_fische);
        Button bt_add = (Button) findViewById(R.id.bt_add);
        et_add_pl = (EditText) findViewById(R.id.et_add_pl);
        et_add_eng = (EditText) findViewById(R.id.et_add_eng);


        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUserData(et_add_pl) && validateUserData(et_add_eng))
                    addFicheToDatabase();
                else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public boolean validateUserData(EditText editText) {
        return editText.getText().toString().length() > 1;

    }

    public void addFicheToDatabase() {
        FicheModel ficheModel = new FicheModel();
        ficheModel.setStatus(FicheModel.Status.UNKNOWN);
        ficheModel.setCategory("v1");
        ficheModel.setEngValue(et_add_eng.getText().toString());
        ficheModel.setPlValue(et_add_pl.getText().toString());
        ficheModel.setId((int) (getDatabaseHelper().getFichesCount() + 1));
        ficheModel.setSoundPath("");

        getDatabaseHelper().saveSingleFiche(ficheModel);
        showOkMsgBox("Sukces!", getResources().getString(R.string.succesfully_added), null);

    }

}
