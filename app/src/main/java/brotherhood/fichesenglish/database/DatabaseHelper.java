package brotherhood.fichesenglish.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Wojtek on 2016-02-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2; //wersja bazy danych wymagana od funkcji tworzacej baze
    //ponizej znajduja sie nazwy pól w bazie danych i sharedPreferences. na ich podstawie jest tworzona abza danych i
    //parsowane sa dane do modelu CurrencyModel podczas odczytu  z bazy danych
    private static final String TABLE_NAME = "currencies";
    private static final String CODE_NAME = "code";
    private static final String SELL_VALUE_NAME = "sellValue";
    private static final String BUY_VALUE_NAME = "buyValue";
    private static final String AVERAGE_VALUE_NAME = "averageValue";
    private static final String WEIGHT_NAME = "weight";
    private static final String FULLNAME = "fullname";
    private static final String SHOW_IN_SETTINGS_NAME = "showInSettings";

    private static final String TABLE_CREATE_EXPRESSION =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + CODE_NAME + " TEXT PRIMARY KEY, "
                    + SELL_VALUE_NAME + " TEXT, "
                    + FULLNAME + " TEXT, "
                    + BUY_VALUE_NAME + " TEXT, "
                    + AVERAGE_VALUE_NAME + " TEXT, "
                    + WEIGHT_NAME + " TEXT ,"
                    + SHOW_IN_SETTINGS_NAME + " INTEGER );";

    private SharedPreferences preferences; //obiekt SharedPreferences czyli lokalny zbior obiektów klucz-wartosc.

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
        preferences = context.getSharedPreferences(TABLE_NAME, Activity.MODE_PRIVATE);
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

    /**
     * Pobiera cala baze danych z lokalnej pamieci urzadzenia i zwraca ja w postaci ArrayListy zawierajacej
     * CurrencyModel'e.
     *
     * @return
     */
    public ArrayList<CurrencyModel> getCurrenciesFromDatabase() {
        ArrayList<CurrencyModel> ret = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String code = cursor.getString(cursor
                        .getColumnIndex(CODE_NAME));
                String sellValue = cursor.getString(cursor
                        .getColumnIndex(SELL_VALUE_NAME));
                String buyValue = cursor.getString(cursor
                        .getColumnIndex(BUY_VALUE_NAME));
                String fullName = cursor
                        .getString(cursor.getColumnIndex(FULLNAME));
                String weight = cursor.getString(cursor
                        .getColumnIndex(WEIGHT_NAME));
                String averageValue = cursor.getString(cursor
                        .getColumnIndex(AVERAGE_VALUE_NAME));
                boolean showInSettingsValue = cursor.getInt(cursor
                        .getColumnIndex(SHOW_IN_SETTINGS_NAME)) > 0;

                CurrencyModel model = new CurrencyModel(code);
                model.setSellValue(sellValue);
                model.setBuyValue(buyValue);
                model.setFullName(fullName);
                model.setWeight(weight);
                model.setAverageValue(averageValue);
                model.setShowOnCurrenciesList(showInSettingsValue);
                ret.add(model);
                cursor.moveToNext();
            }
            getReadableDatabase().close();
        }

        return ret;
    }


    /**
     * Zapisuje do bazy danych obiekty listy CurrencyModel. Potem beda te dane wykorzystywane w Settings i Currencies activity.
     *
     * @param models
     */
    public void saveCurrencyModelList(ArrayList<CurrencyModel> models) {
        if (models != null) {
            SQLiteDatabase db = getWritableDatabase();
            for (CurrencyModel model : models) {
                ContentValues values = new ContentValues();
                values.put(CODE_NAME, model.getCode());
                values.put(AVERAGE_VALUE_NAME, model.getAverageValue());
                values.put(BUY_VALUE_NAME, model.getBuyValue());
                values.put(SELL_VALUE_NAME, model.getSellValue());
                values.put(WEIGHT_NAME, model.getWeight());
                values.put(FULLNAME, model.getFullName());
                values.put(SHOW_IN_SETTINGS_NAME, model.isShowOnCurrenciesList());

                db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);//wrzuca do bazy danych,
                //jesli dane istanitly to nadpisuje
            }
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