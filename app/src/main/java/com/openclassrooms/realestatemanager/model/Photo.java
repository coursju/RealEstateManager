package com.openclassrooms.realestatemanager.model;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.openclassrooms.realestatemanager.utils.BitmapUtils;

@Entity
public class Photo {
    @PrimaryKey(autoGenerate = true)
    public long photoId;

    public long estateCreatorId;
    public byte[] photo;
    public String photoDescription;

    public Photo() {
    }

    public Photo(long estateCreatorId, Bitmap photo, String photoDescription) {
        this.estateCreatorId = estateCreatorId;
        this.photo = BitmapUtils.convertBitmapToByteArray(photo);
        this.photoDescription = photoDescription;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public long getEstateCreatorId() {
        return estateCreatorId;
    }

    public void setEstateCreatorId(long estateCreatorId) {
        this.estateCreatorId = estateCreatorId;
    }

    public Bitmap getPhoto() {
        return BitmapUtils.convertCompressedByteArrayToBitmap(photo);
    }

    public void setPhoto(Bitmap photo) {
        this.photo = BitmapUtils.convertBitmapToByteArray(photo);
    }

    public String getPhotoDescription() {
        return photoDescription;
    }

    public void setPhotoDescription(String photoDescription) {
        this.photoDescription = photoDescription;
    }
}
