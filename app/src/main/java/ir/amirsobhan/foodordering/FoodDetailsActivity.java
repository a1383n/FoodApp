package ir.amirsobhan.foodordering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.amirsobhan.foodordering.model.Cart;
import ir.amirsobhan.foodordering.model.Favorite;
import ir.amirsobhan.foodordering.model.Popular;

public class FoodDetailsActivity extends AppCompatActivity {
    TextView name, price, rating, note;
    ImageView imageView, backButton, viewCard,likeButton;
    RatingBar ratingBar;
    Button addTo_card;
    Popular popular;
    boolean isFound = false;
    List<Cart> cartList = new ArrayList<>();
    CartDB db;
    FavDB favDB;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        Initializing();


    }

    private void Initializing() {
        name = findViewById(R.id.details_name);
        price = findViewById(R.id.details_price);
        imageView = findViewById(R.id.details_image);
        ratingBar = findViewById(R.id.details_ratingBar);
        rating = findViewById(R.id.details_rating_num);
        note = findViewById(R.id.details_note);
        backButton = findViewById(R.id.imageView2);
        viewCard = findViewById(R.id.imageView4);
        addTo_card = findViewById(R.id.details_add_to_card_button);
        likeButton = findViewById(R.id.imageView7);

        Gson gson = new Gson();
        popular = gson.fromJson(getIntent().getExtras().getString("json"), Popular.class);
        db = new CartDB(this);
        favDB = new FavDB(this);

        name.setText(popular.getName());
        price.setText(popular.getPrice());
        rating.setText(popular.getRating());
        note.setText(popular.getNote());

        viewCard.setImageDrawable((db.getCount() == 0) ? getDrawable(R.drawable.ic_cart) : getDrawable(R.drawable.ic_cart_full));

        Glide.with(this).load(popular.getImageUrl()).into(imageView);
        float stars = Float.valueOf(popular.getRating());
        ratingBar.setRating(stars);
        ratingBar.setIsIndicator(true);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

         cursor = db.SqlRawQuery("SELECT * FROM " + CartDB.TABLE_CART + " WHERE " + CartDB.ITEM_NAME + "='" + popular.getName()+"'");
        if (cursor.getCount() != 0) {
            addTo_card.setBackground(getDrawable(R.drawable.add_to_card_bg_w));
            addTo_card.setTextColor(Color.parseColor("#333333"));
            addTo_card.setText("View On Card");
            addTo_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FoodDetailsActivity.this, CartDetailsActivity.class));
                }
            });
        }else {
            addTo_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pattern p = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");
                    Matcher m = p.matcher(popular.getDeliveryCharges());
                    int charge = 0;
                    while(m.find()) {
                        charge += Integer.parseInt(m.group());
                    }
                    db.InsertIntoDatabase(new Cart(popular.getName(),popular.getImageUrl(),popular.getRating(),popular.getDeliveryTime(),popular.getDeliveryCharges(),popular.getPrice(),popular.getNote(),1,Integer.parseInt(popular.getPrice()),charge,false));
                    addTo_card.setBackground(getDrawable(R.drawable.add_to_card_bg_w));
                    addTo_card.setTextColor(Color.parseColor("#333333"));
                    addTo_card.setText("View On Card");
                    addTo_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(FoodDetailsActivity.this, CartDetailsActivity.class));
                        }
                    });
                    viewCard.setImageDrawable(getDrawable(R.drawable.avd_anim));
                    AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) viewCard.getDrawable();
                    drawable.start();
                }
            });

        }
        final Cursor fav = favDB.Query("SELECT * FROM " + FavDB.TABLE_NAME + " WHERE " + FavDB.ITEM_NAME + "='" + popular.getName()+"'");
        if (fav.getCount() != 0){
            likeButton.setImageResource(R.drawable.ic_baseline_favorite_24);
        }else{
            likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Cursor fav = favDB.Query("SELECT * FROM " + FavDB.TABLE_NAME + " WHERE " + FavDB.ITEM_NAME + "='" + popular.getName()+"'");
                if (fav.getCount() != 0){
                    likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    favDB.Delete(popular.getName());
                }else{
                    likeButton.setImageResource(R.drawable.ic_baseline_favorite_24);
                    favDB.Insert(new Favorite(
                            0,
                            popular.getName(),
                            popular.getImageUrl(),
                            popular.getRating(),
                            popular.getDeliveryTime(),
                            popular.getDeliveryCharges(),
                            popular.getPrice(),
                            popular.getNote()
                    ));                }
            }
        });
        viewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodDetailsActivity.this, CartDetailsActivity.class));
            }
        });
        viewCard.setImageDrawable((db.getCount() == 0) ? getDrawable(R.drawable.ic_cart) : getDrawable(R.drawable.ic_cart_full));
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewCard.setImageDrawable((db.getCount() == 0) ? getDrawable(R.drawable.ic_cart) : getDrawable(R.drawable.ic_cart_full));
        if (cursor.getCount() != 0) {
            addTo_card.setBackground(getDrawable(R.drawable.add_to_card_bg_w));
            addTo_card.setTextColor(Color.parseColor("#333333"));
            addTo_card.setText("View On Card");
            addTo_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FoodDetailsActivity.this, CartDetailsActivity.class));
                }
            });
        }else {
            addTo_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pattern p = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");
                    Matcher m = p.matcher(popular.getDeliveryCharges());
                    int charge = 0;
                    while(m.find()) {
                        charge += Integer.parseInt(m.group());
                    }
                    db.InsertIntoDatabase(new Cart(popular.getName(),popular.getImageUrl(),popular.getRating(),popular.getDeliveryTime(),popular.getDeliveryCharges(),popular.getPrice(),popular.getNote(),1,Integer.parseInt(popular.getPrice()),charge,false));
                    addTo_card.setBackground(getDrawable(R.drawable.add_to_card_bg_w));
                    addTo_card.setTextColor(Color.parseColor("#333333"));
                    addTo_card.setText("View On Card");
                    addTo_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(FoodDetailsActivity.this, CartDetailsActivity.class));
                        }
                    });
                    viewCard.setImageDrawable(getDrawable(R.drawable.avd_anim));
                    AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) viewCard.getDrawable();
                    drawable.start();
                }
            });

        }
    }
}