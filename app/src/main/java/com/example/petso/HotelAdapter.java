package com.example.petso;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private List<Hotel> hotelList;
    private OnItemClickListener listener;

    // Constructor to initialize the adapter with the list of hotels and listener
    public HotelAdapter(List<Hotel> hotelList, OnItemClickListener listener) {
        this.hotelList = hotelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for individual items
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        // Get the hotel for this position
        Hotel hotel = hotelList.get(position);
        holder.hotelName.setText(hotel.getName());
        holder.hotelAddress.setText(hotel.getAddress());
        holder.hotelStatus.setText(hotel.getStatus());

        // Set up the click listener for this hotel item
        holder.itemView.setOnClickListener(v -> {
            // Trigger the listener's onItemClick method when an item is clicked
            listener.onItemClick(hotel);
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    // ViewHolder class to hold the views for individual hotel items
    static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView hotelName, hotelAddress, hotelStatus;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.hotel_name);
            hotelAddress = itemView.findViewById(R.id.hotel_address);
            hotelStatus = itemView.findViewById(R.id.hotel_status);
        }
    }

    // Interface for handling item clicks
    public interface OnItemClickListener {
        void onItemClick(Hotel hotel);
    }
}
