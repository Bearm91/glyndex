package com.bearm.glyndex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.R;
import com.bearm.glyndex.models.Measurement;
import com.bearm.glyndex.viewModels.MeasurementViewModel;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    private List<Measurement> mData;
    private LayoutInflater mInflater;
    private Context context;
    private MeasurementViewModel measurementViewModel;

    // data is passed into the constructor
    public DetailsAdapter(Context context, List<Measurement> data, MeasurementViewModel measurementViewModel) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.measurementViewModel = measurementViewModel;
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
        } else {
            holder.linearLayout.setBackgroundColor(context.getColor(R.color.colorWhite));

        }

        if (mData.get(position).isCustom()) {
            holder.customImageView.setVisibility(View.VISIBLE);
        } else {
            holder.customImageView.setVisibility(View.INVISIBLE);
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Measurement getItem (int position) {
       return mData.get(position);
    }

    public List<Measurement> getMeasurementList() {
        return this.mData;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myNameView;
        TextView myCarbsView;
        LinearLayout linearLayout;
        ImageView customImageView;


        ViewHolder(View itemView) {
            super(itemView);
            myNameView = itemView.findViewById(R.id.tv_measurement_name);
            myCarbsView = itemView.findViewById(R.id.tv_carbs_unit);
            linearLayout = itemView.findViewById(R.id.ll_measurement_item);
            customImageView = itemView.findViewById(R.id.icon_custom_measurement);

        }
    }

    public void setEvents(List<Measurement> measurements) {
        this.mData = measurements;
        notifyDataSetChanged();
    }

}
