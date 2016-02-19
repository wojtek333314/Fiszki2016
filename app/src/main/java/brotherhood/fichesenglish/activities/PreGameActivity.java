package brotherhood.fichesenglish.activities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.activities.game.GameActivity;
import brotherhood.fichesenglish.activities.game.enums.GameMode;
import brotherhood.fichesenglish.activities.game.enums.LanguageMode;
import brotherhood.fichesenglish.utils.BaseActivity;

public class PreGameActivity extends BaseActivity {

    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_pre_game);
        Button learnLesson = (Button)findViewById(R.id.bt_learnNew);
        Button repeatLesson = (Button)findViewById(R.id.bt_repeatFische);
        final RadioButton plToEng = (RadioButton)findViewById(R.id.rb_pl_eng);

        learnLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameActivity.setLanguageMode(plToEng.isChecked() ? LanguageMode.PL_TO_ENG : LanguageMode.ENG_TO_PL);
                GameActivity.setGameMode(GameMode.LEARN_MODE);
                startActivity(new Intent(PreGameActivity.this, GameActivity.class));
            }
        });

        repeatLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getDatabaseHelper().getLearnedFichesCount() < 30){
                    showOkMsgBox("",getString(R.string.learn_more_info),null);
                    return;
                }

                GameActivity.setLanguageMode(plToEng.isChecked() ? LanguageMode.PL_TO_ENG : LanguageMode.ENG_TO_PL);
                GameActivity.setGameMode(GameMode.REPEAT_MODE);
                startActivity(new Intent(PreGameActivity.this, GameActivity.class));
            }
        });
    }
}
