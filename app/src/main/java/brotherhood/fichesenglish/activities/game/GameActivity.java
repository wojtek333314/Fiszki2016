package brotherhood.fichesenglish.activities.game;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.activities.game.enums.FicheSide;
import brotherhood.fichesenglish.activities.game.enums.GameMode;
import brotherhood.fichesenglish.activities.game.enums.LanguageMode;
import brotherhood.fichesenglish.models.FicheModel;
import brotherhood.fichesenglish.server.enums.ServiceType;
import brotherhood.fichesenglish.utils.BaseActivity;

public class GameActivity extends BaseActivity {
    private static LanguageMode LANGUAGE_MODE;
    private static GameMode GAME_MODE;
    private ArrayList<FicheModel> fiches;

    private TextView ficheText;
    private Button showAnswer;
    private Button knowButton;
    private Button dontKnowButton;
    private ImageView soundButton;
    private View resultButtonsContainer;

    private FicheModel currentFiche;
    private MediaPlayer player;
    private AnimationsHelper animationsHelper;

    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_game);

        animationsHelper = new AnimationsHelper(this);
        player = new MediaPlayer();
        ficheText = (TextView) findViewById(R.id.ficheTask);
        showAnswer = (Button) findViewById(R.id.answerButton);
        knowButton = (Button) findViewById(R.id.know_button);
        dontKnowButton = (Button) findViewById(R.id.dont_know_button);
        soundButton = (ImageView) findViewById(R.id.soundButton);
        resultButtonsContainer = findViewById(R.id.resultButtonsContainer);

        clickListenersInit();
        loadTasksFromDatabase();
        loadRandomTask();
        refreshFicheText(LANGUAGE_MODE == LanguageMode.PL_TO_ENG ? FicheSide.POLISH : FicheSide.ENGLISH);
    }

    private void clickListenersInit() {
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
            }
        });

        knowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(!animationsHelper.canTouchButton())
                    return;
                currentFiche.setStatus(FicheModel.Status.LEARNED);
                getDatabaseHelper().saveSingleFiche(currentFiche);
                loadRandomTask();
                refreshFicheText(LANGUAGE_MODE == LanguageMode.PL_TO_ENG ? FicheSide.POLISH : FicheSide.ENGLISH);
                animationsHelper.onResultButtonClickAnimation();
            }
        });

        dontKnowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!animationsHelper.canTouchButton())
                    return;
                currentFiche.setStatus(FicheModel.Status.NOT_LEARNED);
                getDatabaseHelper().saveSingleFiche(currentFiche);
                loadRandomTask();
                refreshFicheText(LANGUAGE_MODE == LanguageMode.PL_TO_ENG ? FicheSide.POLISH : FicheSide.ENGLISH);
                animationsHelper.onResultButtonClickAnimation();
            }
        });

        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!animationsHelper.canTouchButton())
                    return;
                refreshFicheText(LANGUAGE_MODE == LanguageMode.PL_TO_ENG ? FicheSide.ENGLISH : FicheSide.POLISH);
                animationsHelper.onShowAnswerButtonStartAnimation();
            }
        });
    }

    private void loadTasksFromDatabase() {
        fiches = getDatabaseHelper().getFichesFromDatabase(GAME_MODE);
    }

    private void loadRandomTask() {
        System.out .println(GAME_MODE+":"+fiches.size());
        currentFiche = fiches.get(new Random().nextInt(fiches.size()));
    }

    private void refreshFicheText(FicheSide ficheSide) {
        switch (ficheSide) {
            case POLISH:
                ficheText.setText(currentFiche.getPlValue());
                break;
            case ENGLISH:
                ficheText.setText(currentFiche.getEngValue());
                break;
        }
        if(currentFiche.getSoundPath() == null || currentFiche.getSoundPath().equals("") )
            soundButton.setVisibility(View.INVISIBLE);
        else
            soundButton.setVisibility(View.VISIBLE);
    }

    private void playSound() {
        if(player.isPlaying()){
            return;
        }

        if (!isOnline()) {
            showOkMsgBox("", getString(R.string.no_network), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            return;
        }


        try {
            System.out.println(currentFiche.getSoundPath());
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(ServiceType.SERVER_PATH + "sound/" + currentFiche.getSoundPath());
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setLanguageMode(LanguageMode languageMode) {
        GameActivity.LANGUAGE_MODE = languageMode;
    }

    public static void setGameMode(GameMode gameMode) {
        GAME_MODE = gameMode;
    }

    public View getResultButtonsContainer() {
        return resultButtonsContainer;
    }

    public Button getShowAnswer() {
        return showAnswer;
    }

    public TextView getFicheText() {
        return ficheText;
    }
}
