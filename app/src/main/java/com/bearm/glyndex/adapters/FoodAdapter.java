package com.bearm.glyndex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.R;
import com.bearm.glyndex.models.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private List<Food> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public FoodAdapter(Context context, List<Food> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.mClickListener = mClickListener;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.food_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food currentFood = mData.get(position);
        //Log.e("FOODITEM", currentFood.toString());

        //Food name
        holder.myNameView.setText(currentFood.getName());

        //Glycemic index
        Integer currentFoodGI = currentFood.getGI();
        if (currentFoodGI == null) {
            holder.myIGView.setText("-");
        } else {
            holder.myIGView.setText(String.valueOf(currentFoodGI));
        }

        //Glycemic index icon
        String icon = "ic_up_yellow_arrow";
        //holder.myIGView.setTextColor(ContextCompat.getColor(context, R.color.colorYellow));

        if (currentFoodGI == null) {
            icon = "ic_up_blue_arrow";
            //holder.myIGView.setTextColor(ContextCompat.getColor(context, R.color.colorGray));

        } else if (currentFoodGI <= 55) {
            icon = "ic_up_green_arrow";
            //holder.myIGView.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
        } else if (currentFoodGI >= 70) {
            icon = "ic_up_red_arrow";
            //holder.myIGView.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        int resourceIdImage = context.getResources().getIdentifier(icon, "drawable",
                context.getPackageName());
        //use this id to set the image anywhere
        holder.myImageView.setImageResource(resourceIdImage);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myNameView;
        TextView myIGView;
        ImageView myImageView;
        CardView myCardView;

        ViewHolder(View itemView) {
            super(itemView);
            myNameView = itemView.findViewById(R.id.tv_food_name);
            myIGView = itemView.findViewById(R.id.tv_food_ig);
            myImageView = itemView.findViewById(R.id.iv_food_ig);
            myCardView = itemView.findViewById(R.id.cv_food_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
                }
            });

        }

    }

    // convenience method for getting data at click position
    Food getItem(int id) {
        return mData.get(id);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
