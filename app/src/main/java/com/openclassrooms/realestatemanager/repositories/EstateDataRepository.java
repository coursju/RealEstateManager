package com.openclassrooms.realestatemanager.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.model.Estate;

import java.util.List;

public class EstateDataRepository {

    private static final String TAG = "--EstateDataRepository";
    private final EstateDao mEstateDao;

    public EstateDataRepository(EstateDao estateDao) { this.mEstateDao = estateDao; }

    // --- GET ---

    public LiveData<List<Estate>> getItems(){ return this.mEstateDao.getEstates(); }

    // --- CREATE ---

    public void createItem(Estate estate){ mEstateDao.insertEstate(estate); }

    // --- UPDATE ---
    public void updateItem(Estate estate){ mEstateDao.updateEstate(estate); }
}
