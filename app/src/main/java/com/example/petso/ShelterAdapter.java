package com.example.petso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShelterAdapter extends RecyclerView.Adapter<ShelterAdapter.ShelterViewHolder> {
    private List<Shelter> shelterList;

    public ShelterAdapter(List<Shelter> shelterList) {
        this.shelterList = shelterList;
    }

    @NonNull
    @Override
    public ShelterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shelter_item, parent, false);
        return new ShelterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShelterViewHolder holder, int position) {
        Shelter shelter = shelterList.get(position);
        holder.shelterName.setText(shelter.getName());
        holder.shelterAddress.setText(shelter.getAddress());
        holder.shelterAvailability.setText(shelter.getAvailability());
    }

    @Override
    public int getItemCount() {
        return shelterList.size();
    }

    public static class ShelterViewHolder extends RecyclerView.ViewHolder {
        TextView shelterName, shelterAddress, shelterAvailability;

        public ShelterViewHolder(@NonNull View itemView) {
            super(itemView);
            shelterName = itemView.findViewById(R.id.shelter_name);
            shelterAddress = itemView.findViewById(R.id.shelter_address);
            shelterAvailability = itemView.findViewById(R.id.shelter_availability);
        }
    }
}
