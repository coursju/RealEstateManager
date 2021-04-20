package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.EstateWithPhotos;
import com.openclassrooms.realestatemanager.model.Photo;

import java.util.List;

public class EstateDataRepository {

    private static final String TAG = "EstateDataRepository";
    private final EstateDao mEstateDao;

    public EstateDataRepository(EstateDao estateDao) { this.mEstateDao = estateDao; }

    // --- GET ---
    public LiveData<List<EstateWithPhotos>> getEstateWithPhotos(){ return this.mEstateDao.getEstateWithPhotos(); }

    // --- CREATE ---
    public long createEstate(Estate estate){ return mEstateDao.insertEstate(estate); }
    public void createPhoto(Photo photo){  mEstateDao.insertPhoto(photo); }
    public void createPhoto(Photo... photo){  mEstateDao.insertPhotos(photo); }

    // --- UPDATE ---
    public void updateEstate(Estate estate){  mEstateDao.updateEstate(estate); }
    public void updatePhoto(Photo photo){  mEstateDao.updatePhoto(photo); }
    public void updatePhoto(Photo... photo){  mEstateDao.updatePhotos(photo); }

    // --- DELETE ---
    public void deletePhoto(Photo photo){  mEstateDao.deletePhoto(photo); }
    public void deletePhoto(Photo... photo){  mEstateDao.deletePhotos(photo); }
}
