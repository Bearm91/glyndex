package com.bearm.glyndex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.R;
import com.bearm.glyndex.models.Measurement;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    private List<Measurement> mData;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    public DetailsAdapter(Context context, List<Measurement> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.measurement_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Measurement currentMeasurement = mData.get(position);

        //Measurement name
        holder.myNameView.setText(currentMeasurement.getName());

        //Measurement quantity
        holder.myCarbsView.setText(context.getString(
                R.string.ch_ration,
                String.valueOf(currentMeasurement.getChRationPerMeasurement()),
                String.valueOf(currentMeasurement.getChRationPerMeasurement() * 10)));

        if (position % 2 == 0) {
            holder.linearLayout.setBackgroundColor(context.getColor(R.color.colorMeasurement));
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myNameView;
        TextView myCarbsView;
        LinearLayout linearLayout;


        ViewHolder(View itemView) {
            super(itemView);
            myNameView = itemView.findViewById(R.id.tv_measurement_name);
            myCarbsView = itemView.findViewById(R.id.tv_carbs_unit);
            linearLayout = itemView.findViewById(R.id.ll_measurement_item);

        }

    }

    // convenience method for getting data at click position
    Measurement getItem(int id) {
        return mData.get(id);
    }

}
