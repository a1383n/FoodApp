package ir.amirsobhan.foodordering.retrofit;

import java.util.List;

import ir.amirsobhan.foodordering.model.FoodData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("food.json")
    Call<List<FoodData>> getAllData();
}
