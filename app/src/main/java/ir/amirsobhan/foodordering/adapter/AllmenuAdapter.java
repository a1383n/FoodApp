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
import ir.amirsobhan.foodordering.model.Allmenu;

public class AllmenuAdapter extends RecyclerView.Adapter<AllmenuAdapter.AllmenuViewHolder> {

    private Context context;
    private List<Allmenu> allmenuList;

    public AllmenuAdapter(Context context, List<Allmenu> allmenuList) {
        this.context = context;
        this.allmenuList = allmenuList;
    }

    @NonNull
    @Override
    public AllmenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.allmenu_recycler_items,parent,false);
        return new AllmenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllmenuViewHolder holder, int position) {
        final Allmenu allmenu = allmenuList.get(position);

        holder.popularName.setText(allmenu.getName());
        holder.popularNote.setText(allmenu.getNote());
        holder.popularRating.setText(allmenu.getRating());
        holder.popularTime.setText(allmenu.getDeliveryTime());
        holder.popularCharges.setText(allmenu.getDeliveryCharges());
        holder.popularPrice.setText(allmenu.getPrice());

        Glide.with(context).load(allmenu.getImageUrl()).placeholder(R.drawable.download).error(R.drawable.ic_baseline_wifi_off_24).into(holder.popularImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FoodDetailsActivity.class);
                Gson gson = new Gson();
                intent.putExtra("from","allmenu");
                intent.putExtra("json",gson.toJson(allmenu));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allmenuList.size();
    }

    public static class AllmenuViewHolder extends RecyclerView.ViewHolder{

        TextView popularName,popularNote,popularRating,popularTime,popularCharges,popularPrice;
        ImageView popularImage;

        public AllmenuViewHolder(@NonNull View itemView) {
            super(itemView);

            popularName = itemView.findViewById(R.id.cart_name);
            popularNote = itemView.findViewById(R.id.cart_note);
            popularRating = itemView.findViewById(R.id.cart_rating);
            popularTime = itemView.findViewById(R.id.cart_delivery_time);
            popularCharges = itemView.findViewById(R.id.cart_delivery_charge);
            popularPrice = itemView.findViewById(R.id.cart_price);
            popularImage = itemView.findViewById(R.id.cart_image);

        }
    }


}
