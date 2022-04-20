package fi.lab.mapproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "places.db";
    private static final String TABLE_NAME = "places";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LONG = "longitude";

    private static final String CREATE_PLACES_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_LAT + " FLOAT,"
            + KEY_LONG + " FLOAT)";

    public static final String DROP_PLACES_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHandler(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(CREATE_PLACES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL(DROP_PLACES_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addPlace(PlacePoint placePoint){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, placePoint.getPlaceName());
        values.put(KEY_LAT, placePoint.getPlaceLatitude());
        values.put(KEY_LONG, placePoint.getPlaceLongitude());
        db.insert(TABLE_NAME, null, values);
    }

    public void deletePlace(PlacePoint placePoint){
        //TODO: OPTIONAL DELETING FROM DB
        //NOT PRIORITISED BECAUSE IT'S EASIER TO DELETE AND SAVE WHOLE DB
    }

    public List<PlacePoint> getAllPlaces(){
        List<PlacePoint> placesList = new LinkedList<>();
        String selectQuery = "SELECT * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            float lat = cursor.getFloat(2);
            float lng = cursor.getFloat(3);
            PlacePoint placePoint = new PlacePoint(name, lat, lng);

            placesList.add(placePoint);
        }
        cursor.close();
        return placesList;
    }

    public void clearPlaces(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, new String[]{});
    }

}
