package ir.amirsobhan.foodordering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ir.amirsobhan.foodordering.model.Cart;

public class CartDB extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "cart";
    private static String LOG_TAG = "CartDatabase Status:";
    public static String TABLE_CART = "cartTable";
    public static String KEY_ID = "id";
    public static String ITEM_NAME = "name";
    public static String ITEM_IMAGE = "image";
    public static String ITEM_RATTING = "ratting";
    public static String ITEM_DELIVERY_TIME = "delivery_time";
    public static String ITEM_DELIVERY_CHARGE = "delivery_charge";
    public static String ITEM_PRICE = "price";
    public static String ITEM_NOTE = "note";
    public static String ITEM_COUNT = "count";
    public static String ITEM_PRICE_INT = "price_int";
    public static String ITEM_DELIVERY_CHARGE_INT = "delivery_charge_int";
    public static String ITEM_FAVORITE = "favorite";
    private static String CREATE_TABLE_CART = "CREATE TABLE "+ TABLE_CART + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ITEM_NAME + " TEXT,"
            + ITEM_IMAGE + " TEXT,"
            + ITEM_RATTING + " TEXT,"
            + ITEM_DELIVERY_TIME + " TEXT,"
            + ITEM_DELIVERY_CHARGE + " TEXT,"
            + ITEM_PRICE + " TEXT,"
            + ITEM_NOTE + " TEXT,"
            + ITEM_COUNT + " INTEGER,"
            + ITEM_PRICE_INT + " INTEGER,"
            + ITEM_DELIVERY_CHARGE_INT + " INTEGER ,"
            + ITEM_FAVORITE + " INTEGER)";

    public CartDB(Context context){
        super(context,DATABASE_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    // Insert Data Into Database
    public void InsertIntoDatabase(Cart cart){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME,cart.getName());
        contentValues.put(ITEM_IMAGE,cart.getImageUrl());
        contentValues.put(ITEM_RATTING,cart.getRating());
        contentValues.put(ITEM_DELIVERY_TIME,cart.getDeliveryTime());
        contentValues.put(ITEM_DELIVERY_CHARGE,cart.getDeliveryCharges());
        contentValues.put(ITEM_PRICE,cart.getPrice());
        contentValues.put(ITEM_COUNT,cart.getCount());
        contentValues.put(ITEM_PRICE_INT,cart.getPriceInt());
        contentValues.put(ITEM_DELIVERY_CHARGE_INT,cart.getDeliveryChargeInt());
        contentValues.put(ITEM_FAVORITE,(cart.isFavorite()) ? 1 : 0);
        db.insert(TABLE_CART,null,contentValues);

        Log.d(LOG_TAG,contentValues.toString()+"\n Added");
    }
    //Read All data;
    public Cursor ReadAll(){
     SQLiteDatabase db = this.getReadableDatabase();
     String sql = "SELECT * FROM "+ TABLE_CART;
     Log.d(LOG_TAG,"ReadAll:"+sql);
     return db.rawQuery(sql,null,null);
    }

    //Read data in database
    public Cursor ReadData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+ TABLE_CART + " WHERE "+ KEY_ID + "=" + id + "";
        return db.rawQuery(sql,null,null);
    }

    //Update Favorite Status
    public void UpdateFavorite(int id,boolean favoriteStatus){
        SQLiteDatabase db = this.getWritableDatabase();
        int status = (favoriteStatus) ? 1 : 0;
        String sql = "UPDATE " + TABLE_CART + " SET " + ITEM_FAVORITE + "="+status+" WHERE "+ KEY_ID + "="+ id + "";
        db.execSQL(sql);
        Log.d(LOG_TAG,"UpdateFavorite:"+sql);
    }


    //Read favorite Status
    public boolean ReadFavoriteStatus(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+ TABLE_CART + " WHERE "+ KEY_ID + "=" + id + "";
        Cursor cursor = db.rawQuery(sql,null,null);
        cursor.moveToNext();
        return (cursor.getInt(cursor.getColumnIndex(ITEM_FAVORITE))) == 1 ? true : false;
    }

    //Select all favorite
    public Cursor SelectAllFavorite(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+ TABLE_CART + " WHERE "+ ITEM_FAVORITE + "='1'";
        return db.rawQuery(sql,null,null);
    }

    //Update Count
    public void UpdateCount(String id,int count){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_CART + " SET " + ITEM_COUNT + "='"+count+"' WHERE " + KEY_ID + "="+id;
        db.execSQL(sql);
    }

    // Delete Cart Item
    public void DeleteCartItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM "+ TABLE_CART + " WHERE "+ KEY_ID + "="+ id + "";
        db.execSQL(sql);
    }

    //get Count
    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM "+ TABLE_CART;
        Cursor cursor = db.rawQuery(sql,null,null);
        cursor.moveToNext();
        return cursor.getInt(cursor.getColumnIndex("COUNT(*)"));
    }

    public Cursor SqlRawQuery(String sql){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(sql,null,null);
    }
}
