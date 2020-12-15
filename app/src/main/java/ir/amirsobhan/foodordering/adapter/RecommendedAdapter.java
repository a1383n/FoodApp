package ir.amirsobhan.foodordering.adapter;

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
import com.google.gson.Gson;

import java.util.List;

import ir.amirsobhan.foodordering.FoodDetailsActivity;
import ir.amirsobhan.foodordering.R;
import ir.amirsobhan.foodordering.model.Recommended;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {

    private Context context;
    private List<Recommended> recommendedList;

    public RecommendedAdapter(Context context, List<Recommended> recommendedList) {
        this.context = context;
        this.recommendedList = recommendedList;
    }

    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rcommended_recycler_items,parent,false);

        return new RecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, int position) {
        final Recommended recommended = recommendedList.get(position);
        Glide.with(context).load(recommended.getImageUrl()).placeholder(R.drawable.download).error(R.drawable.ic_baseline_wifi_off_24).into(holder.recommendedImage);
        holder.recommendedName.setText(recommended.getName());
        holder.recommendedRating.setText(recommended.getRating());
        holder.recommendedDeliveryTime.setText(recommended.getDeliveryTime());
        holder.recommendedDeliveryType.setText(recommended.getDeliveryCharges());
        holder.recommendedCharges.setText(recommended.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FoodDetailsActivity.class);
                Gson gson = new Gson();
                intent.putExtra("from","recommended");
                intent.putExtra("json",gson.toJson(recommended));

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    public static class RecommendedViewHolder extends RecyclerView.ViewHolder{
        ImageView recommendedImage;
        TextView recommendedName,recommendedRating,recommendedDeliveryTime,recommendedCharges,recommendedDeliveryType;
        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);
            recommendedImage = itemView.findViewById(R.id.recommended_image);
            recommendedName = itemView.findViewById(R.id.recommended_name);
            recommendedRating = itemView.findViewById(R.id.recommended_rating);
            recommendedDeliveryTime = itemView.findViewById(R.id.recommended_delivery_time);
            recommendedCharges = itemView.findViewById(R.id.recommended_price);
            recommendedDeliveryType = itemView.findViewById(R.id.delivery_type);

        }
    }


}
