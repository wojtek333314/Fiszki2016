package brotherhood.fichesenglish.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import brotherhood.fichesenglish.activities.game.enums.GameMode;
import brotherhood.fichesenglish.models.FicheModel;

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
    private static final String STATUS = "status";


    private static final String TABLE_CREATE_EXPRESSION =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + ID + " INTEGER PRIMARY KEY, "
                    + PL_VALUE + " TEXT, "
                    + ENG_VALUE + " TEXT, "
                    + CATEGORY_VALUE + " TEXT, "
                    + STATUS + " INT, "
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
    public ArrayList<FicheModel> getFichesFromDatabase(GameMode gameMode) {
        String whereClausure = gameMode == GameMode.REPEAT_MODE ? " where status = 1" : " ";

        ArrayList<FicheModel> entityList = new ArrayList<FicheModel>();
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_NAME + whereClausure, null);
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
                int status = cursor.getInt(cursor.getColumnIndex(STATUS));

                FicheModel model = new FicheModel();
                model.setId(id);
                model.setPlValue(plValue);
                model.setEngValue(engValue);
                model.setCategory(categoryValue);
                model.setSoundPath(soundValue);
                model.setStatus(FicheModel.Status.defineStatus(status));
                entityList.add(model);
                cursor.moveToNext();
            }
            getReadableDatabase().close();
        }

        return entityList;
    }


    public void saveSingleFiche(JSONObject singleFicheJSONObject) throws JSONException {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, singleFicheJSONObject.getString("id"));
        values.put(PL_VALUE, singleFicheJSONObject.getString("pl"));
        values.put(ENG_VALUE, singleFicheJSONObject.getString("eng"));
        values.put(CATEGORY_VALUE, singleFicheJSONObject.getString("category"));
        values.put(SOUND_PATH, singleFicheJSONObject.getString("soundPath"));
        values.put(STATUS, 0);

        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);//wrzuca do bazy danych,
        //jesli dane istanitly to nadpisuje

    }

    public void saveSingleFiche(FicheModel singleFiche) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, singleFiche.getId());
        values.put(PL_VALUE, singleFiche.getPlValue());
        values.put(ENG_VALUE, singleFiche.getEngValue());
        values.put(CATEGORY_VALUE, singleFiche.getCategory());
        values.put(SOUND_PATH, singleFiche.getSoundPath());
        values.put(STATUS, FicheModel.Status.getStatusCode(singleFiche.getStatus()));

        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);//wrzuca do bazy danych,
    }

    public long getLearnedFichesCount() {
        SQLiteStatement s = getReadableDatabase().compileStatement("select count(*) from " + TABLE_NAME
                + " where status=1; ");
        return s.simpleQueryForLong();
    }

    public long getFichesCount(){
        SQLiteStatement s = getReadableDatabase().compileStatement("select count(*) from " + TABLE_NAME
                + " ; ");
        return s.simpleQueryForLong();
    }

    public boolean isFicheWasLearned(int id){
        SQLiteStatement s = getReadableDatabase().compileStatement("select "+STATUS+" from " + TABLE_NAME
                + " where id="+id+" ; ");
        return s.simpleQueryForLong()==1;
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