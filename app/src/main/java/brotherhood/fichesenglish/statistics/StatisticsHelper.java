package brotherhood.fichesenglish.statistics;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import brotherhood.fichesenglish.database.DatabaseHelper;
import brotherhood.fichesenglish.database.StatisticsDatabaseHelper;

/**
 * Created by Wojtek on 2016-02-19.
 */
public class StatisticsHelper {
    private DatabaseHelper databaseHelper;
    private StatisticsDatabaseHelper statisticsDatabaseHelper;

    public StatisticsHelper(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
        this.statisticsDatabaseHelper = new StatisticsDatabaseHelper(context);
    }

    public float getPercentageProgress(){
        return ((float)databaseHelper.getLearnedFichesCount()/(float)databaseHelper.getFichesCount());
    }

    public int getCountOfFichesToLearn(){
        return (int) (databaseHelper.getFichesCount() - databaseHelper.getLearnedFichesCount());
    }

    public int getCountOfLearnedFiches(){
        return (int) databaseHelper.getLearnedFichesCount();
    }

    public String getTimeSpend(){
        long time = statisticsDatabaseHelper.getSpendTime();
        return String.format("%02d:%02d:%02d:%02d",
                 TimeUnit.MILLISECONDS.toDays(time),
                 TimeUnit.MILLISECONDS.toHours(time),
                 TimeUnit.MILLISECONDS.toMinutes(time),
                 TimeUnit.MILLISECONDS.toSeconds(time) -
                         TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
         );
    }

    public String getAverageDailyTimeSpend(){
        long time = statisticsDatabaseHelper.getAverageDailyTime();
        return String.format("%02d:%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toDays(time),
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );
    }

}
