package com.openclassrooms.realestatemanager.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.Estate;

import java.util.List;

@Dao
public interface EstateDao {
    @Query("SELECT * FROM Estate")
    LiveData<List<Estate>> getEstates();

    @Query("SELECT * FROM Estate")
    Cursor getEstatesWithCursor();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertEstate(Estate estate);

    @Update
    int updateEstate(Estate estate);

}
