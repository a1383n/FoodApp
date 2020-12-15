package ir.amirsobhan.foodordering;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import ir.amirsobhan.foodordering.adapter.AllmenuAdapter;
import ir.amirsobhan.foodordering.adapter.PopularAdapter;
import ir.amirsobhan.foodordering.adapter.RecommendedAdapter;
import ir.amirsobhan.foodordering.model.Allmenu;
import ir.amirsobhan.foodordering.model.FoodData;
import ir.amirsobhan.foodordering.model.Popular;
import ir.amirsobhan.foodordering.model.Recommended;
import ir.amirsobhan.foodordering.retrofit.ApiInterface;
import ir.amirsobhan.foodordering.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ApiInterface apiInterface;

    RecyclerView popularRecyclerView, recommendedRecyclerView, allmenuRecyclerView;
    PopularAdapter popularAdapter;
    RecommendedAdapter recommendedAdapter;
    AllmenuAdapter allmenuAdapter;
    SharedPreferencesHelper helper;
    SwipeRefreshLayout refreshLayout;

    TextView popularTitle, recommendedTitle, allmenuTitle;
    ImageView cart,favorite;

    CartDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initializing();
        SendRequest();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SendRequest();
            }
        });
    }

    private void SendRequest() {
        refreshLayout.setRefreshing(true);
        Call<List<FoodData>> call = apiInterface.getAllData();
        call.enqueue(new Callback<List<FoodData>>() {
            @Override
            public void onResponse(Call<List<FoodData>> call, Response<List<FoodData>> response) {
                List<FoodData> foodDataList = response.body();
                getPopularData(foodDataList.get(0).getPopular());
                getRecommendedData(foodDataList.get(0).getRecommended());
                getAllmenu(foodDataList.get(0).getAllmenu());
                refreshLayout.setRefreshing(false);

                popularTitle.setVisibility(View.VISIBLE);
                recommendedTitle.setVisibility(View.VISIBLE);
                allmenuTitle.setVisibility(View.VISIBLE);

                popularRecyclerView.setVisibility(View.VISIBLE);
                recommendedRecyclerView.setVisibility(View.VISIBLE);
                allmenuRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<FoodData>> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialogTheme);
                builder.setTitle("Server not responding");
                builder.setMessage("Check your connection and try again");
                builder.setIcon(getDrawable(R.drawable.ic_baseline_wifi_off_24));
                builder.setCancelable(false);
                builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SendRequest();
                    }
                });
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
//                builder.setPositiveButtonIcon(getDrawable(R.drawable.ic_baseline_refresh_24));
//                builder.setNegativeButtonIcon(getDrawable(R.drawable.ic_baseline_exit_to_app_24));

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void Initializing() {
        refreshLayout = findViewById(R.id.swipe_refresh_main);
        popularRecyclerView = findViewById(R.id.popular_recycler);
        recommendedRecyclerView = findViewById(R.id.recommeneded_recycler);
        allmenuRecyclerView = findViewById(R.id.allmenu_recycler);
        cart = findViewById(R.id.ic_card_main);
        favorite = findViewById(R.id.favorite_main);
        popularTitle = findViewById(R.id.textView2);
        recommendedTitle = findViewById(R.id.textView3);
        allmenuTitle = findViewById(R.id.textView4);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        refreshLayout.setProgressViewOffset(false,0,350);
        db = new CartDB(this);

        cart.setImageDrawable((db.getCount() == 0) ? getDrawable(R.drawable.ic_cart) : getDrawable(R.drawable.ic_cart_full));

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartDetailsActivity.class));
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FavoriteActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cart.setImageDrawable((db.getCount() == 0) ? getDrawable(R.drawable.ic_cart) : getDrawable(R.drawable.ic_cart_full));
    }

    private void getPopularData(List<Popular> popularList) {
        popularAdapter = new PopularAdapter(this, popularList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerView.setAdapter(popularAdapter);
    }

    private void getRecommendedData(List<Recommended> recommendedList) {
        recommendedAdapter = new RecommendedAdapter(this, recommendedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);
    }

    private void getAllmenu(List<Allmenu> allmenuList) {
        allmenuAdapter = new AllmenuAdapter(this, allmenuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        allmenuRecyclerView.setLayoutManager(layoutManager);
        allmenuRecyclerView.setAdapter(allmenuAdapter);
    }
}