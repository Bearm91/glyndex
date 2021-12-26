package com.bearm.glyndex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.R;
import com.bearm.glyndex.helpers.DetailsHelper;
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

        //Food name
        holder.myNameView.setText(currentFood.getName());

        if (!currentFood.isCustom()) {
            holder.myNameView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            holder.myNameView.setCompoundDrawablesWithIntrinsicBounds(null,null,context.getDrawable(R.drawable.ic_person),null);
        }

        //Glycemic index
        Integer currentFoodGI = currentFood.getGI();
        if (currentFoodGI == null) {
            holder.myIGView.setText("-");
        } else {
            holder.myIGView.setText(String.valueOf(currentFoodGI));
        }

        //Glycemic index color
        holder.myIGView.setBackgroundColor(DetailsHelper.getIGColor(context, currentFoodGI));
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
        CardView myCardView;

        ViewHolder(View itemView) {
            super(itemView);
            myNameView = itemView.findViewById(R.id.tv_food_name);
            myIGView = itemView.findViewById(R.id.tv_food_ig);
            myCardView = itemView.findViewById(R.id.cv_food_item);
            itemView.setOnClickListener(v -> {
                if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
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

    public void setEvents(List<Food> foods) {
        this.mData = foods;
        notifyDataSetChanged();
    }
}
