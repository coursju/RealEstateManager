package com.openclassrooms.realestatemanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Photo;

import java.util.List;

public class DetailsRecyclerViewAdapter extends RecyclerView.Adapter<DetailsRecyclerViewAdapter.ViewHolder>{

    private List<Photo> photoList;

    public DetailsRecyclerViewAdapter(List<Photo> photoList){
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImageView.setImageBitmap(photoList.get(position).getPhoto());
        holder.mTextView.setText(photoList.get(position).getPhotoDescription());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mImageView;
        public final TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.details_item_pict);
            mTextView = itemView.findViewById(R.id.details_item_txt);
        }
    }

    }
