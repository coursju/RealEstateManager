package com.openclassrooms.realestatemanager.adapter;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.BaseEstateActivity;
import com.openclassrooms.realestatemanager.controler.CreateEstateActivity;
import com.openclassrooms.realestatemanager.model.Photo;

import java.util.List;

public class CreateModifyRecyclerAdapter extends RecyclerView.Adapter<CreateModifyRecyclerAdapter.ViewHolder>{

    private static final String TAG = "CreateModifyRecyclerAd";
    private List<Photo> photoList;
    private BaseEstateActivity activity;

    public CreateModifyRecyclerAdapter(List<Photo> photoList, BaseEstateActivity activity){
        this.photoList = photoList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CreateModifyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_fragment_item, parent, false);
        return new CreateModifyRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateModifyRecyclerAdapter.ViewHolder holder, int position) {
        holder.mImageView.setImageBitmap(photoList.get(position).getPhoto());
        if (photoList.get(position).getPhotoDescription() != null){
            holder.shortDescriptionTxt.setText(photoList.get(position).getPhotoDescription());
        }
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mImageView;
        public Button removeButton;
        public EditText shortDescriptionTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.create_image_item);
            removeButton = itemView.findViewById(R.id.create_remove_button);
            shortDescriptionTxt = itemView.findViewById(R.id.create_short_description_text);

            shortDescriptionTxt.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER){
                        Log.i(CreateModifyRecyclerAdapter.TAG, "shortDescriptionTxt : "+shortDescriptionTxt.getText().toString());
                        activity.addPhotoDescription(getAdapterPosition(), shortDescriptionTxt.getText().toString());
                        return  true;
                    }else {
                        return false;
                    }
                }
            });
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.removePhoto(getAdapterPosition());
                }
            });
        }
    }

}
