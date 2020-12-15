package ir.amirsobhan.foodordering.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.amirsobhan.foodordering.CartDB;
import ir.amirsobhan.foodordering.CartDetailsActivity;
import ir.amirsobhan.foodordering.FavDB;
import ir.amirsobhan.foodordering.OnValueChangeListener;
import ir.amirsobhan.foodordering.R;
import ir.amirsobhan.foodordering.SharedPreferencesHelper;
import ir.amirsobhan.foodordering.ValueSelector;
import ir.amirsobhan.foodordering.model.Cart;
import ir.amirsobhan.foodordering.model.Favorite;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CardViewHolder> {

    private Context context;
    private List<Cart> cartList;
    private SharedPreferencesHelper helper;
    private boolean checked = false;
    private TextView cartPay,cartEstimated,cartEmpty,txt6,txt7;
    private Button checkOutBtn;
    private CartDB db;
    private FavDB favDB;
    private Activity activity;
    private CartDetailsActivity cartDetails;

    public CartAdapter(Context context, Activity activity, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
        this.activity = activity;
        helper = SharedPreferencesHelper.getPreferences(context);
        cartPay = activity.findViewById(R.id.cart_pay);
        cartEmpty = activity.findViewById(R.id.cart_for_empty);
        txt6 = activity.findViewById(R.id.textView6);
        checkOutBtn = activity.findViewById(R.id.cart_checkout);

        db = new CartDB(context);
        favDB = new FavDB(context);

        cartDetails = new CartDetailsActivity();

    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_recycler_item,parent,false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder holder, final int position) {
        final Cart cart = cartList.get(position);

        holder.cartName.setText(cart.getName());
        holder.cartNote.setText(cart.getNote());
        holder.cartPrice.setText(cart.getPrice());
        holder.cartRatting.setText(cart.getRating());
        holder.cartDeliveryTime.setText(cart.getDeliveryTime());
        holder.cartDeliveryCharge.setText(cart.getDeliveryCharges());

        Glide.with(context).load(cart.getImageUrl()).into(holder.cartImage);

        holder.valueSelector.setOnValueChangeListener(new OnValueChangeListener() {
            @Override
            public void OnValueChange(int value) {
                cartList.get(position).setCount(value);
                db.UpdateCount(cart.getId()+"",value);
                UpdateCartPay(cartList);
            }
        });

        holder.valueSelector.setValue(cart.getCount());
        holder.valueSelector.setMinValue(1);
        holder.valueSelector.setMaxValue(10);

        UpdateCartPay(cartList);

        if (favDB.Is(cart.getName())){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.cartFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart_unchecked_to_checked));
                AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) holder.cartFavorite.getDrawable();
                drawable.start();
            }else {
                holder.cartFavorite.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_favorite_24));
            }
        }

        holder.cartDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.DeleteCartItem(cart.getId());
                cartList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartList.size());
                if (cartList.size() == 0){
                    cartEmpty.setVisibility(View.VISIBLE);
                    txt6.setVisibility(View.GONE);
                    cartPay.setVisibility(View.GONE);
                    checkOutBtn.setVisibility(View.GONE);
                }else {
                    UpdateCartPay(cartList);
                }
            }
        });
        holder.cartFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean favoriteStatus = favDB.Is(cart.getName());
                if (favoriteStatus){
                    favDB.Delete(cart.getName());
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        holder.cartFavorite.setImageDrawable(context.getDrawable(R.drawable.heart_checked_to_unchecked));
                        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) holder.cartFavorite.getDrawable();
                        drawable.start();
                    }else {
                        holder.cartFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    }
                }else {
                    favDB.Insert(new Favorite(
                            0,
                            cart.getName(),
                            cart.getImageUrl(),
                            cart.getRating(),
                            cart.getDeliveryTime(),
                            cart.getDeliveryCharges(),
                            cart.getPrice(),
                            cart.getNote()
                    ));
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        holder.cartFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart_unchecked_to_checked));
                        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) holder.cartFavorite.getDrawable();
                        drawable.start();
                    }else {
                        holder.cartFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_favorite_24));
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        TextView cartName,cartNote,cartRatting,cartPrice,cartDeliveryCharge,cartDeliveryTime;
        ImageView cartImage,cartFavorite,cartDelete;
        ValueSelector valueSelector;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            cartName = itemView.findViewById(R.id.cart_name);
            cartNote = itemView.findViewById(R.id.cart_note);
            cartRatting = itemView.findViewById(R.id.cart_rating);
            cartPrice = itemView.findViewById(R.id.cart_price);
            cartDeliveryCharge = itemView.findViewById(R.id.cart_delivery_charge);
            cartDeliveryTime = itemView.findViewById(R.id.cart_delivery_time);
            cartImage = itemView.findViewById(R.id.cart_image);
            cartFavorite = itemView.findViewById(R.id.cart_favorite);
            cartDelete = itemView.findViewById(R.id.cart_delete);
            valueSelector = itemView.findViewById(R.id.cart_valueSelector);
        }
    }
    public void UpdateCartPay(List<Cart> cartList){
        int totalDeliveryCharge = 0 , totalPrice = 0;
        for (int i = 0;i < cartList.size(); i++){
            Cart cart = cartList.get(i);
            if (cart.getCount() > 1){
                totalPrice += cart.getPriceInt() * cart.getCount();
            }else {
                totalPrice += cart.getPriceInt();
            }
            totalDeliveryCharge += cart.getDeliveryChargeInt();
        }
        String text;
        if (totalDeliveryCharge == 0){
            text = totalPrice + "";
        }else{
            text = totalPrice + " + " + totalDeliveryCharge;
        }
        cartPay = activity.findViewById(R.id.cart_pay);
        cartPay.setText(text);
    }

}
