package com.sunbeaminfo.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sunbeaminfo.app.Entity.Venue;
import com.sunbeaminfo.app.R;
import com.sunbeaminfo.app.api.API;

import java.util.List;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.MyViewHolder> {

    Context context;
    List<Venue> venueList;

    public VenueAdapter(Context context, List<Venue> venueList) {
        this.context = context;
        this.venueList = venueList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_venue, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueAdapter.MyViewHolder holder, int position) {
        Venue currentVenue = venueList.get(position);
        holder.textName.setText(currentVenue.getName());
        holder.textPrice.setText(currentVenue.getPrice());
        holder.textLocation.setText(currentVenue.getLocation());
        Glide.with(context).load(API.BASE_URL+"/"+venueList.get(position).getImage()).into(holder.imgview);
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textPrice, textLocation;
        ImageView imgview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            textLocation = itemView.findViewById(R.id.textLocation);
            imgview = itemView.findViewById(R.id.imgview);
        }
    }
}
