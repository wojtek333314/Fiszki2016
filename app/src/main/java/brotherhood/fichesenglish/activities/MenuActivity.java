package brotherhood.fichesenglish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.database.DatabaseHelper;
import brotherhood.fichesenglish.utils.BaseActivity;

public class MenuActivity extends BaseActivity {


    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_menu);
        Button bt_practice = (Button) findViewById(R.id.bt_practise);
        Button bt_stats = (Button) findViewById(R.id.bt_stats);
        Button bt_addFishe = (Button) findViewById(R.id.bt_addFische);

        DatabaseHelper dtbh = new DatabaseHelper(getApplicationContext());

        System.out.println(dtbh.getFichesFromDatabase().get(0).getEngValue());

        bt_practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPreGameActivity = new Intent(MenuActivity.this,PreGameActivity.class);
                startActivity(toPreGameActivity);
            }
        });

        bt_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toStatisticsActivity = new Intent(MenuActivity.this,StatisticsActivity.class);
                startActivity(toStatisticsActivity);
            }
        });

        bt_addFishe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAddFischeActivity = new Intent(MenuActivity.this,AddFischeActivity.class);
                startActivity(toAddFischeActivity);
            }
        });
    }
}
