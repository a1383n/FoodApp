package ir.amirsobhan.foodordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.amirsobhan.foodordering.adapter.CartAdapter;
import ir.amirsobhan.foodordering.model.Cart;

public class CartDetailsActivity extends AppCompatActivity {
    ImageView backBtn;
    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    TextView cartPay,cartEstimated,cartEmpty,txt6,txt7;
    Button checkOutBtn;
    List<Cart> cartList = new ArrayList<>();
    CartDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);
        Initializing();
    }
    private void Initializing(){
        backBtn = findViewById(R.id.cart_back_btn);
        cartRecyclerView = findViewById(R.id.cart_recycler);
        cartPay = findViewById(R.id.cart_pay);
        cartEmpty = findViewById(R.id.cart_for_empty);
        txt6 = findViewById(R.id.textView6);
        checkOutBtn = findViewById(R.id.cart_checkout);
        db = new CartDB(this);

        Cursor cursor = db.ReadAll();
        try {
            while (cursor.moveToNext()){
                cartList.add(new Cart(
                        cursor.getInt(cursor.getColumnIndex(CartDB.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(CartDB.ITEM_NAME)),
                        cursor.getString(cursor.getColumnIndex(CartDB.ITEM_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(CartDB.ITEM_RATTING)),
                        cursor.getString(cursor.getColumnIndex(CartDB.ITEM_DELIVERY_TIME)),
                        cursor.getString(cursor.getColumnIndex(CartDB.ITEM_DELIVERY_CHARGE)),
                        cursor.getString(cursor.getColumnIndex(CartDB.ITEM_PRICE)),
                        cursor.getString(cursor.getColumnIndex(CartDB.ITEM_NOTE)),
                        cursor.getInt(cursor.getColumnIndex(CartDB.ITEM_COUNT)),
                        cursor.getInt(cursor.getColumnIndex(CartDB.ITEM_PRICE_INT)),
                        cursor.getInt(cursor.getColumnIndex(CartDB.ITEM_DELIVERY_CHARGE_INT)),
                        (cursor.getInt(cursor.getColumnIndex(CartDB.ITEM_FAVORITE)) == 1) ? true : false
                        ));
            }
        }finally {
            if (cursor != null && cursor.isClosed()){
                cursor.close();
                db.close();
            }
        }
        if (cursor.getCount() != 0) {
            cartAdapter = new CartAdapter(this, CartDetailsActivity.this, cartList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            cartRecyclerView.setLayoutManager(layoutManager);
            cartRecyclerView.setAdapter(cartAdapter);
         //   UpdateCartPay(cartList);
        } else {
            cartEmpty.setVisibility(View.VISIBLE);
            txt6.setVisibility(View.GONE);
            cartPay.setVisibility(View.GONE);
            checkOutBtn.setVisibility(View.GONE);
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}