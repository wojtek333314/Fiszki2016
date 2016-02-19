package brotherhood.fichesenglish.activities.game;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import brotherhood.fichesenglish.R;

/**
 * Created by Wojtek on 2016-02-19.
 */
public class AnimationsHelper {
    private Animation answerButtonHideAnimation;
    private Animation answerButtonShowAnimation;
    private Animation resultButtonsShowAnimation;
    private Animation resultButtonsHideAnimation;
    private Animation turnCardAnimation;

    private GameActivity gameActivity;

    private boolean answersButtonAnimating;
    private boolean showButtonAnimating;

    public AnimationsHelper(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        answerButtonHideAnimation = AnimationUtils.loadAnimation(gameActivity, R.anim.fade_out);
        answerButtonShowAnimation = AnimationUtils.loadAnimation(gameActivity, R.anim.fade_in);
        resultButtonsHideAnimation = AnimationUtils.loadAnimation(gameActivity, R.anim.fade_out);
        resultButtonsShowAnimation = AnimationUtils.loadAnimation(gameActivity, R.anim.fade_in);

        answerButtonHideAnimation.setDuration(200);
        answerButtonShowAnimation.setDuration(200);
        resultButtonsHideAnimation.setDuration(200);
        resultButtonsShowAnimation.setDuration(200);

        initListeners();
    }

    private void initListeners(){
        answerButtonHideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                gameActivity.getShowAnswer().setVisibility(View.VISIBLE);
                showButtonAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gameActivity.getShowAnswer().setVisibility(View.INVISIBLE);
                startShowingResultButtons();
                showButtonAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        answerButtonShowAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                gameActivity.getShowAnswer().setVisibility(View.VISIBLE);
                showButtonAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showButtonAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        resultButtonsShowAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                gameActivity.getResultButtonsContainer().setVisibility(View.VISIBLE);
                answersButtonAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                answersButtonAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        resultButtonsHideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                gameActivity.getResultButtonsContainer().setVisibility(View.VISIBLE);
                answersButtonAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                answersButtonAnimating = false;
                gameActivity.getResultButtonsContainer().setVisibility(View.INVISIBLE);
                gameActivity.getShowAnswer().startAnimation(answerButtonShowAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void startShowingResultButtons(){
        gameActivity.getResultButtonsContainer().startAnimation(resultButtonsShowAnimation);
    }

    public void onShowAnswerButtonStartAnimation(){
        answerButtonHideAnimation.reset();
        answerButtonShowAnimation.reset();
        gameActivity.getShowAnswer().startAnimation(answerButtonHideAnimation);
    }

    public void onResultButtonClickAnimation(){
        resultButtonsShowAnimation.reset();
        resultButtonsHideAnimation.reset();
        gameActivity.getResultButtonsContainer().startAnimation(resultButtonsHideAnimation);
    }

    public boolean canTouchButton(){
        return !(showButtonAnimating || answersButtonAnimating);
    }
}
