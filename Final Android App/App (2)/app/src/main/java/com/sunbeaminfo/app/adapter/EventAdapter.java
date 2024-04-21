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
import com.sunbeaminfo.app.Entity.Event;
import com.sunbeaminfo.app.R;
import com.sunbeaminfo.app.api.API;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    Context context;
    List<Event> eventList;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_event, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event currentEvent = eventList.get(position);
        holder.textevent.setText(currentEvent.getName());
        holder.textDetails.setText(currentEvent.getDetails());
        Glide.with(context).load(API.BASE_URL+"/"+eventList.get(position).getImage()).into(holder.imgevent);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textevent, textDetails;
        ImageView imgevent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textevent = itemView.findViewById(R.id.textname);
            textDetails = itemView.findViewById(R.id.textDetails);
            imgevent = itemView.findViewById(R.id.imgview);
        }
    }
}