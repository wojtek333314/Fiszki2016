package brotherhood.fichesenglish.activities;

import android.widget.ProgressBar;
import android.widget.TextView;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.utils.BaseActivity;

public class StatisticsActivity extends BaseActivity {

    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_statistics);

        final ProgressBar pb_toLearn = (ProgressBar) findViewById(R.id.toLearn);
        final ProgressBar pb_inProgress = (ProgressBar) findViewById(R.id.inProgress);
        final ProgressBar pb_totalLearnTime = (ProgressBar) findViewById(R.id.totalLearnTime);
        final ProgressBar pb_dailyLearnTime = (ProgressBar) findViewById(R.id.dailyLearnTime);

        final TextView tv_to_learn_percent = (TextView)findViewById(R.id.tv_to_learn_percent);
        final TextView tv_to_learn_value = (TextView)findViewById(R.id.tv_to_learn_value);
        final TextView tv_learning_percent = (TextView)findViewById(R.id.tv_learning_percent);
        final TextView tv_learning_value = (TextView)findViewById(R.id.tv_learning_value);
        final TextView tv_time_learn_value = (TextView)findViewById(R.id.tv_time_learn_value);
        final TextView tv_daily_time_learn_value = (TextView)findViewById(R.id.tv_daily_time_learn_value);

        tv_learning_percent.setText(String.valueOf(setPercentStat(12, 100)));
        tv_learning_value.setText(setValueStat(123,5674));
        setProgress(pb_toLearn,getProgressStatInt(100,1000));

    }

    //Procent na podstawie wszystkich fiszek i nauczonej/zapamiętenej/etc liczby fiszek
    public String setPercentStat(float totalSize, float actualSize) {
        float result = totalSize/actualSize*100f;
        return String.format("%.2f ‰",result);
    }

    // wartość liczbowa w stringu
    public String setValueStat(int totalSize, int actualSize) {
        return  String.valueOf(totalSize).concat("/").concat(String.valueOf(actualSize));
    }

    // ustawienie progresbara na podstawie procentów zwróconych przez getProgressStatInt
    public void setProgress(ProgressBar progressBar, int progress) {
        progressBar.setSecondaryProgress(progress);
        }
    public int getProgressStatInt(float totalSize, float actualSize) {
        return (int) (totalSize/actualSize*100);
    }
}
