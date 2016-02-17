package brotherhood.fichesenglish.activities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.activities.game.GameActivity;
import brotherhood.fichesenglish.utils.BaseActivity;

public class PreGameActivity extends BaseActivity {

    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_pre_game);
        Button bt_createFishe = (Button)findViewById(R.id.bt_learnNew);
        Button bt_repeatFische = (Button)findViewById(R.id.bt_repeatFische);

        bt_createFishe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toGameActivity = new Intent(PreGameActivity.this, GameActivity.class);
                toGameActivity.putExtra("key","value"); //TODO download new fisches
                startActivity(toGameActivity);
            }
        });

        bt_repeatFische.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toGameActivity = new Intent(PreGameActivity.this, GameActivity.class);
                toGameActivity.putExtra("key","value"); //TODO download only memorized fisches
                startActivity(toGameActivity);
            }
        });
    }
}
