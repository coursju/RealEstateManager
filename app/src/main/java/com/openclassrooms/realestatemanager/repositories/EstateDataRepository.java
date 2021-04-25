package com.openclassrooms.realestatemanager.repositories;

import android.util.Log;

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
    public long createEstate(Estate estate){
        long id = mEstateDao.insertEstate(estate);
        Log.i(TAG, String.valueOf(id));
        return id;//mEstateDao.insertEstate(estate);
        }
    public void createPhoto(Photo photo){   mEstateDao.insertPhoto(photo); }
    public void createPhoto(Photo... photo){  mEstateDao.insertPhotos(photo); }

    // --- UPDATE ---
    public int updateEstate(Estate estate){  return mEstateDao.updateEstate(estate); }
    public int updatePhoto(Photo photo){  return mEstateDao.updatePhoto(photo); }
    public void updatePhoto(Photo... photo){  mEstateDao.updatePhotos(photo); }

    // --- DELETE ---
    public int deletePhoto(Photo photo){  return mEstateDao.deletePhoto(photo); }
    public void deletePhoto(Photo... photo){  mEstateDao.deletePhotos(photo); }
}
