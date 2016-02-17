package brotherhood.fichesenglish.activities;

import android.view.View;
import android.widget.Button;

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
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ServerRequest(ServiceType.ADD_FICHE, new Parameters().addParam("pl","słóweczko")
                        .addParam("ang","angielusie").addParam("category","kategoryjka").addParam("soundPath","scieżka"));
            }
        });

    }
}
