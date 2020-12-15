package ir.amirsobhan.foodordering.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.amirsobhan.foodordering.FavDB;
import ir.amirsobhan.foodordering.R;
import ir.amirsobhan.foodordering.model.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private Context context;
    private Activity activity;
    private List<Favorite> favoriteList;
    private FavDB db;

    public FavoriteAdapter(Context context,Activity activity, List<Favorite> favoriteList) {
        this.context = context;
        this.activity = activity;
        this.favoriteList = favoriteList;
        db = new FavDB(context);
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.favorite_recycler_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, final int position) {
        final Favorite favorite = favoriteList.get(position);
        holder.favoriteName.setText(favorite.getName());
        holder.favoriteRatting.setText(favorite.getRating());
        holder.favoritePrice.setText(favorite.getPrice());
        holder.favoriteDeliveryTime.setText(favorite.getDeliveryTime());
        holder.favoriteDeliveryCharge.setText(favorite.getDeliveryCharges());
        holder.favoriteNote.setText(favorite.getNote());
        Glide.with(context).load(favorite.getImageUrl()).into(holder.favoriteImage);

        holder.favoriteDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.Delete(favorite.getId());
                favoriteList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,favoriteList.size());
                updateList(favoriteList);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent());
            }
        });
    }


    private void updateList(List<Favorite> list){
        if (list.size() == 0){
            activity.findViewById(R.id.favorite_empty).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.favorite_recycle).setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView favoriteName,favoriteNote,favoriteRatting,favoritePrice,favoriteDeliveryCharge,favoriteDeliveryTime;
        ImageView favoriteImage,favoriteDelete;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            favoriteName = itemView.findViewById(R.id.favorite_name);
            favoriteNote = itemView.findViewById(R.id.favorite_note);
            favoriteRatting = itemView.findViewById(R.id.favorite_rating);
            favoritePrice = itemView.findViewById(R.id.favorite_price);
            favoriteDeliveryCharge = itemView.findViewById(R.id.favorite_delivery_charge);
            favoriteDeliveryTime = itemView.findViewById(R.id.favorite_delivery_time);
            favoriteImage = itemView.findViewById(R.id.favorite_image);
            favoriteDelete = itemView.findViewById(R.id.favorite_delete);

        }
    }
}
