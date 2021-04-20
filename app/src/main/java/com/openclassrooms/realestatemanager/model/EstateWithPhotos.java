package com.openclassrooms.realestatemanager.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class EstateWithPhotos {
    @Embedded public Estate estate;
    @Relation(
            parentColumn = "estateId",
            entityColumn = "estateCreatorId"
    )
    public List<Photo> photoList;

    public EstateWithPhotos() {
    }

    public EstateWithPhotos(Estate estate, List<Photo> photoList) {
        this.estate = estate;
        this.photoList = photoList;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }
}