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
import com.bearm.glyndex.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public CategoryAdapter(Context context, List<Category> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.mClickListener = mClickListener;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.category_list_item, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Category currentCategory = mData.get(position);
        holder.myTextView.setText(currentCategory.getName());
        String iconName = currentCategory.getIconName();
        if (iconName != null) {
            int resourceIdImage = context.getResources().getIdentifier(iconName, "drawable",
                    context.getPackageName());
            //use this id to set the image anywhere
            holder.myImageView.setImageResource(resourceIdImage);
        }
    }


    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        ImageView myImageView;
        CardView myCardView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tv_cat_name);
            myImageView = itemView.findViewById(R.id.iv_cat_icon);
            myCardView = itemView.findViewById(R.id.cv_item);
            itemView.setOnClickListener(v -> {
                if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
            });

        }

    }

    // convenience method for getting data at click position
    Category getItem(int id) {
        return mData.get(id);
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
