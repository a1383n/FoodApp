package ir.amirsobhan.foodordering;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ir.amirsobhan.foodordering.model.Popular;

public class SharedPreferencesHelper {
    private static SharedPreferencesHelper sharedPreferencesHelper;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String SHARED_PREFERENCES_NAME = "save_data";
    public static final String TAG_CART = "card_data";
    public static final String TAG_FAVORITE = "favorite_data";

    private SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static SharedPreferencesHelper getPreferences(Context context) {
        if (sharedPreferencesHelper == null) {
            sharedPreferencesHelper = new SharedPreferencesHelper(context);
        }
        return sharedPreferencesHelper;
    }

    public void addToList(String key, Popular popular) {
        String json = get(key);
        if (json != null) {
            List<Popular> list;
            Gson gson = new Gson();
            Type type = new TypeToken<List<Popular>>() {
            }.getType();
            list = gson.fromJson(json, type);
            list.add(popular);

            Gson gson1 = new Gson();
            set(key, gson1.toJson(list));
        } else {
            Gson gson = new Gson();
            List<Popular> popularList = new ArrayList<>();
            popularList.add(popular);
            set(key, gson.toJson(popularList));
        }
    }

    public List<Popular> getFromList(String key) {
        String json = get(key);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Popular>>() {
            }.getType();
            return gson.fromJson(json, type);
        } else {
            return null;
        }
    }

    public void deleteFromList(String key, Popular popular) {
        String json = get(key);
        if (json != null) {
            List<Popular> list;
            Gson gson = new Gson();
            Type type = new TypeToken<List<Popular>>() {
            }.getType();
            list = gson.fromJson(json, type);

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getName().equals(popular.getName())) {
                    list.remove(i);
                    if (list.size() == 0){
                        editor.remove(key);
                        editor.apply();
                    }
                }
            }

            Gson gson1 = new Gson();
            set(key, gson1.toJson(list));
        }
    }

    public boolean isCardEmpty(){
        if (get(TAG_CART) == null){
            return true;
        }else if (get(TAG_CART).equals("[]")){
            return true;
        }else {
            return false;
        }
    }

    public boolean isFavoriteEmpty(){
        if (get(TAG_FAVORITE) == null){
            return true;
        }else if (get(TAG_FAVORITE).equals("[]")){
            return false;
        }else {
            return false;
        }
    }

    public String get(String key){
        return sharedPreferences.getString(key,null);
    }
    public void set(String key,String data){
        editor.putString(key,data);
        editor.apply();
    }
}

