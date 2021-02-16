package com.openclassrooms.realestatemanager.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.utils.ImagesSQLiteConverter;

import java.util.List;

public class DetailsRecyclerViewAdapter extends RecyclerView.Adapter<DetailsRecyclerViewAdapter.ViewHolder>{

    private List<Estate> mEstateList;

    public DetailsRecyclerViewAdapter(List<Estate> estateList){
        mEstateList = estateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap bitmap = ImagesSQLiteConverter.toBitmapListFromJson(mEstateList.get(position).getPhotosString()).get(0);
        holder.mImageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mEstateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.details_item_pict);
        }
    }

    }
