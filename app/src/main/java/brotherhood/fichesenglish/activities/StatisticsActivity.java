package brotherhood.fichesenglish.activities;

import android.widget.ProgressBar;
import android.widget.TextView;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.statistics.StatisticsHelper;
import brotherhood.fichesenglish.utils.BaseActivity;

public class StatisticsActivity extends BaseActivity {

    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_statistics);
        StatisticsHelper stats = new StatisticsHelper(this);

        final ProgressBar progressBarTLearn = (ProgressBar) findViewById(R.id.toLearn);
        final ProgressBar progressBarInProgress = (ProgressBar) findViewById(R.id.inProgress);

        final TextView toLearnPercent = (TextView) findViewById(R.id.tv_to_learn_percent);
        final TextView toLearnValue = (TextView) findViewById(R.id.tv_to_learn_value);
        final TextView learningPercent = (TextView) findViewById(R.id.tv_learning_percent);
        final TextView learningValue = (TextView) findViewById(R.id.tv_learning_value);
        final TextView timeSpend = (TextView) findViewById(R.id.tv_time_learn_value);

        learningPercent.setText(getPercentStat(stats.getCountOfLearnedFiches(),stats.getCountOfFiches()));
        learningValue.setText(setValueStat(stats.getCountOfLearnedFiches(), stats.getCountOfFiches()));
        setProgress(progressBarInProgress, getProgressStatInt(stats.getCountOfLearnedFiches(), stats.getCountOfFiches()));

        toLearnPercent.setText(getPercentStat(stats.getCountOfFichesToLearn(), stats.getCountOfFiches()));
        toLearnValue.setText(setValueStat(stats.getCountOfFichesToLearn(), stats.getCountOfFiches()));
        setProgress(progressBarTLearn, getProgressStatInt(stats.getCountOfFichesToLearn(), stats.getCountOfFiches()));

        timeSpend.setText(stats.getTimeSpend());
    }

    //Procent na podstawie wszystkich fiszek i nauczonej/zapamiętenej/etc liczby fiszek
    public String getPercentStat(float totalSize, float actualSize) {
        float result;
        if (actualSize == 0)
            result = 0;
        else
            result = (totalSize / actualSize) * 100f;

        return String.format("%.2f", result)+"%";
    }

    // wartość liczbowa w stringu
    public String setValueStat(int totalSize, int actualSize) {
        return String.valueOf(totalSize).concat("/").concat(String.valueOf(actualSize));
    }

    // ustawienie progresbara na podstawie procentów zwróconych przez getProgressStatInt
    public void setProgress(ProgressBar progressBar, int progress) {
        progressBar.setSecondaryProgress(progress);
    }

    public int getProgressStatInt(float totalSize, float actualSize) {
        return (int) ((totalSize / actualSize) * 100);
    }
}
