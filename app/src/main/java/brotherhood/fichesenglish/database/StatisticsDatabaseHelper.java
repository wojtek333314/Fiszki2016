package brotherhood.fichesenglish.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.Calendar;

/**
 * Created by Wojtek on 2016-02-19.
 */
public class StatisticsDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "statistics";
    private static final String ID = "id";
    private static final String DAY = "day";
    private static final String VALUE = "value";
    private static final String FICHES_LEARNED = "fichesLearned";

    private static final String TABLE_CREATE_EXPRESSION =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DAY + " TEXT, "
                    + FICHES_LEARNED + " INT, "
                    + VALUE + " LONG )";


    public StatisticsDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Metoda tworzaca baze danych
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_EXPRESSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long getSpendTime() {
        try {
            SQLiteStatement s = getReadableDatabase().compileStatement("select SUM(" + VALUE + ") from "
                    + TABLE_NAME + " ; ");
            return s.simpleQueryForLong();
        } catch (SQLiteDoneException e) {
            return 0;
        }
    }

    public long getAverageDailyTime() {
        try {
            SQLiteStatement s = getReadableDatabase().compileStatement("select AVG(" + VALUE + ") from "
                    + TABLE_NAME + " ; ");
            return s.simpleQueryForLong();
        } catch (SQLiteDoneException e) {
            return 0;
        }
    }

    public void addDailyTime(long time) {
        Calendar calendar = Calendar.getInstance();
        String date = Integer.toString(calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DAY_OF_MONTH));

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DAY, date);
        values.put(VALUE, getTimeSpendOfDate(date) + time);
        values.put(FICHES_LEARNED, getFichesLearnedAtDate(date));
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    private int getFichesLearnedAtDate(String date) {
        try {
            SQLiteStatement s = getReadableDatabase().compileStatement("select " + FICHES_LEARNED + " from "
                    + TABLE_NAME + " where " + DAY + "=" + date + " ; ");

            return (int) s.simpleQueryForLong();
        } catch (SQLiteDoneException e) {
            return 0;
        }
    }

    public void addCountOfLearnedFiches(int count) {
        Calendar calendar = Calendar.getInstance();
        String date = Integer.toString(calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DAY_OF_MONTH));

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DAY, date);
        values.put(VALUE, getTimeSpendOfDate(date));
        values.put(FICHES_LEARNED, getFichesLearnedAtDate(date) + count);
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public long getTimeSpendOfDate(String date) {
        try {
            SQLiteStatement s = getReadableDatabase().compileStatement("select " + VALUE + " from "
                    + TABLE_NAME + " where " + DAY + "=" + date + " ; ");
            return s.simpleQueryForLong();
        } catch (SQLiteDoneException e) {
            return 0;
        }
    }

    /**
     * Zapobiega wyciekowi z SQLLiteConnection
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}