package ir.amirsobhan.foodordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.amirsobhan.foodordering.adapter.FavoriteAdapter;
import ir.amirsobhan.foodordering.model.Favorite;

public class FavoriteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FavoriteAdapter adapter;
    List<Favorite> favoriteList = new ArrayList<>();
    FavDB db;
    ImageView backImageView;
    TextView favoriteEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Initializing();
        favoriteList = getDataFromDB();
        if (favoriteList.size() != 0) {
            adapter = new FavoriteAdapter(this,FavoriteActivity.this, favoriteList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            favoriteEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private List<Favorite> getDataFromDB() {
        List<Favorite> list = new ArrayList<>();
        Cursor cursor = db.Select();
        while (cursor.moveToNext()) {
            list.add(new Favorite(
                    cursor.getInt(cursor.getColumnIndex(FavDB.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(FavDB.ITEM_NAME)),
                    cursor.getString(cursor.getColumnIndex(FavDB.ITEM_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(FavDB.ITEM_RATTING)),
                    cursor.getString(cursor.getColumnIndex(FavDB.ITEM_DELIVERY_TIME)),
                    cursor.getString(cursor.getColumnIndex(FavDB.ITEM_DELIVERY_CHARGE)),
                    cursor.getString(cursor.getColumnIndex(FavDB.ITEM_PRICE)),
                    cursor.getString(cursor.getColumnIndex(FavDB.ITEM_NOTE))
            ));
        }
        return list;
    }

    private void Initializing() {
        recyclerView = findViewById(R.id.favorite_recycle);
        backImageView = findViewById(R.id.favorite_back);
        favoriteEmpty = findViewById(R.id.favorite_empty);
        db = new FavDB(this);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}