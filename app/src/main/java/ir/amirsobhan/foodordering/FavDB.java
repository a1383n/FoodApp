package ir.amirsobhan.foodordering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import ir.amirsobhan.foodordering.model.Favorite;

public class FavDB extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "fav";
    private static String LOG_TAG = "FavDatabase Status:";
    public static String TABLE_NAME= "fav_table";
    public static String KEY_ID = "id";
    public static String ITEM_NAME = "name";
    public static String ITEM_IMAGE = "image";
    public static String ITEM_RATTING = "ratting";
    public static String ITEM_DELIVERY_TIME = "delivery_time";
    public static String ITEM_DELIVERY_CHARGE = "delivery_charge";
    public static String ITEM_PRICE = "price";
    public static String ITEM_NOTE = "note";
    private static String CREATE_TABLE_FAV = "CREATE TABLE "+ TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ITEM_NAME + " TEXT,"
            + ITEM_IMAGE + " TEXT,"
            + ITEM_RATTING + " TEXT,"
            + ITEM_DELIVERY_TIME + " TEXT,"
            + ITEM_DELIVERY_CHARGE + " TEXT,"
            + ITEM_PRICE + " TEXT,"
            + ITEM_NOTE + " TEXT)";

    public FavDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     * Initializing and get database
     * @return the writableDatabase
     */
    private SQLiteDatabase getDB(){
        return getWritableDatabase();
    }

    /**
     * Insert data to database
     * @param favorite the data want to be insert
     */
    public void Insert(Favorite favorite){
        ContentValues values = new ContentValues();
        values.put(ITEM_NAME,favorite.getName());
        values.put(ITEM_RATTING,favorite.getRating());
        values.put(ITEM_IMAGE,favorite.getImageUrl());
        values.put(ITEM_PRICE,favorite.getPrice());
        values.put(ITEM_DELIVERY_TIME,favorite.getDeliveryTime());
        values.put(ITEM_DELIVERY_CHARGE,favorite.getDeliveryCharges());
        values.put(ITEM_NOTE,favorite.getNote());
        getDB().insert(TABLE_NAME,null,values);
    }

    /**
     * Select data from database
     * @return table data in Cursor object
     */
    public Cursor Select(){
        return getDB().rawQuery("SELECT * FROM " + TABLE_NAME,null,null);
    }

    /**
     * Delete data in table
     * @param id for find row to delete
     */
    public void Delete(int id){
        getDB().execSQL("DELETE FROM "+ TABLE_NAME + " WHERE "+ KEY_ID + "="+ id);
    }


    public void Delete(String name){
        getDB().execSQL("DELETE FROM "+ TABLE_NAME + " WHERE "+ ITEM_NAME + "='"+ name + "'");
    }

    /**
     * Count data in database
     * @return number of row in database
     */
    public int Count(){
        return getDB().rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME,null,null).getInt(0);
    }

    public boolean Is(String name){
        return (getDB().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ITEM_NAME + "='" + name + "'",null,null).getCount() > 0) ? true :false;
    }

    /**
     * custom query
     * @param sql the sql command
     * @return the cursor if your query had result
     */
    public Cursor Query(String sql){
        return getDB().rawQuery(sql,null,null);
    }
}
