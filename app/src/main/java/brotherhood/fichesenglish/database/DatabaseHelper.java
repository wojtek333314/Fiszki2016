package brotherhood.fichesenglish.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2; //wersja bazy danych wymagana od funkcji tworzacej baze
    //ponizej znajduja sie nazwy pól w bazie danych. Na ich podstawie jest tworzona baza danych i
    //parsowane sa dane do modelu CurrencyModel podczas odczytu  z bazy danych
    private static final String TABLE_NAME = "fiches";
    private static final String ID = "id";
    private static final String PL_VALUE = "pl";
    private static final String ENG_VALUE = "eng";
    private static final String CATEGORY_VALUE = "category";
    private static final String SOUND_PATH = "soundPath";


    private static final String TABLE_CREATE_EXPRESSION =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + ID + " INTEGER PRIMARY KEY, "
                    + PL_VALUE + " TEXT, "
                    + ENG_VALUE + " TEXT, "
                    + CATEGORY_VALUE + " TEXT, "
                    + SOUND_PATH + " TEXT )";


    public DatabaseHelper(Context context) {
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

    /**
     * Pobiera cala baze danych z lokalnej pamieci urzadzenia i zwraca ja w postaci ArrayListy zawierajacej
     * Model'e.
     *
     * @return
     */
    public ArrayList<FicheModel> getFichesFromDatabase() {
        ArrayList<FicheModel> entityList = new ArrayList<FicheModel>();
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(ID));
                String plValue = cursor.getString(cursor
                        .getColumnIndex(PL_VALUE));
                String engValue = cursor.getString(cursor
                        .getColumnIndex(ENG_VALUE));
                String categoryValue = cursor.getString(cursor
                        .getColumnIndex(CATEGORY_VALUE));
                String soundValue = cursor.getString(cursor
                        .getColumnIndex(SOUND_PATH));

                FicheModel model = new FicheModel();
                model.setId(id);
                model.setPlValue(plValue);
                model.setEngValue(engValue);
                model.setCategory(categoryValue);
                model.setSoundPath(soundValue);
                entityList.add(model);
                cursor.moveToNext();
            }
            getReadableDatabase().close();
        }

        return entityList;
    }


    /**
     * Zapisuje do bazy danych obiekty listy CurrencyModel. Potem beda te dane wykorzystywane w Settings i Currencies activity.
     *
     * @param modelsEntity
     */
    public void saveFicheModelList(ArrayList<FicheModel> modelsEntity) {
        if (modelsEntity != null) {
            SQLiteDatabase db = getWritableDatabase();
            for (FicheModel model : modelsEntity) {
                ContentValues values = new ContentValues();
                values.put(ID, model.getId());
                values.put(PL_VALUE, model.getPlValue());
                values.put(ENG_VALUE, model.getEngValue());
                values.put(CATEGORY_VALUE, model.getCategory());
                values.put(SOUND_PATH, model.getSoundPath());

                db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);//wrzuca do bazy danych,
                //jesli dane istanitly to nadpisuje
            }
        }
    }

    public void saveSingleFiche(JSONObject singleFicheJSONObject) throws JSONException {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, singleFicheJSONObject.getString("id"));
        values.put(PL_VALUE, singleFicheJSONObject.getString("pl"));
        values.put(ENG_VALUE, singleFicheJSONObject.getString("eng"));
        values.put(CATEGORY_VALUE, singleFicheJSONObject.getString("category"));
        values.put(SOUND_PATH, singleFicheJSONObject.getString("soundPath"));

        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);//wrzuca do bazy danych,
        //jesli dane istanitly to nadpisuje

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