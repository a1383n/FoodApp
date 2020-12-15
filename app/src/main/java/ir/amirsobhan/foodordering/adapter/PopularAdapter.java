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
import ir.amirsobhan.foodordering.model.Popular;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private Context context;
    private List<Popular> popularList;

    public PopularAdapter(Context context, List<Popular> popularList) {
        this.context = context;
        this.popularList = popularList;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_recycler_items,parent,false);

        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        final Popular popular = popularList.get(position);
        Glide.with(context).load(popular.getImageUrl()).placeholder(R.drawable.download).error(R.drawable.ic_baseline_wifi_off_24).into(holder.popularImage);
        holder.popularName.setText(popular.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FoodDetailsActivity.class);
                Gson gson = new Gson();
                intent.putExtra("from","popular");
                intent.putExtra("json",gson.toJson(popular));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public static class PopularViewHolder extends RecyclerView.ViewHolder{
        ImageView popularImage;
        TextView popularName;
        public PopularViewHolder(@NonNull View itemView){
            super(itemView);

            popularImage = itemView.findViewById(R.id.cart_image);
            popularName = itemView.findViewById(R.id.cart_name);

        }
    }


}
