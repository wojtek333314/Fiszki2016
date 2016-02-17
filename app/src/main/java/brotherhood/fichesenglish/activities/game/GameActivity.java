package brotherhood.fichesenglish.activities.game;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.activities.game.enums.GameMode;
import brotherhood.fichesenglish.database.FicheModel;
import brotherhood.fichesenglish.server.enums.ServiceType;
import brotherhood.fichesenglish.utils.BaseActivity;

public class GameActivity extends BaseActivity {
    private static GameMode gameMode;
    private static ArrayList<FicheModel> fiches;

    private TextView ficheText;
    private Button showAnswer;
    private Button knowButton;
    private Button dontKnowButton;
    private ImageView soundButton;
    private View afterAnswerView;

    private FicheModel currentFiche;


    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_game);

        ficheText = (TextView) findViewById(R.id.ficheTask);
        showAnswer = (Button) findViewById(R.id.answerButton);
        knowButton = (Button) findViewById(R.id.know_button);
        dontKnowButton = (Button) findViewById(R.id.dont_know_button);
        soundButton = (ImageView) findViewById(R.id.soundButton);
        afterAnswerView = findViewById(R.id.afterAnswerView);

        clickListenersInit();
        loadTasksFromDatabase();
        loadRandomTask();
        refreshFicheText();
    }

    private void clickListenersInit() {
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    MediaPlayer player = new MediaPlayer();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(ServiceType.SERVER_PATH + "sound/" + currentFiche.getSoundPath());
                    player.prepare();
                    player.start();


                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
    }

    private void loadTasksFromDatabase() {
        if (fiches == null) {
            fiches = getDatabaseHelper().getFichesFromDatabase();
        }
    }

    private void loadRandomTask() {
        currentFiche = fiches.get(new Random().nextInt(fiches.size()));
    }

    private void refreshFicheText() {
        ficheText.setText(currentFiche.getEngValue());
    }

    private void setVisibilityAnswerButton(boolean visibility) {
        showAnswer.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void setVisibilityAfterAnswerView(boolean visibility) {
        afterAnswerView.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void playSound() {

    }

    public static void setGameMode(GameMode gameMode) {
        GameActivity.gameMode = gameMode;
    }
}
