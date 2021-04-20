package com.openclassrooms.realestatemanager.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.EstateWithPhotos;
import com.openclassrooms.realestatemanager.model.Photo;

import java.util.List;

@Dao
public interface EstateDao {
    @Transaction
    @Query("SELECT * FROM Estate")
    LiveData<List<EstateWithPhotos>> getEstateWithPhotos();

    @Query("SELECT * FROM Estate")
    LiveData<List<Estate>> getEstates();

    @Query("SELECT * FROM Estate")
    Cursor getEstatesWithCursor();

    @Query("SELECT * FROM Photo WHERE estateCreatorId = :id")
    Cursor getPhotosWithCursorByID(long id);

    // --Estate--
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertEstate(Estate estate);

    @Update
    int updateEstate(Estate estate);

    // --Photo--
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPhoto(Photo photo);

    @Update
    int updatePhoto(Photo photo);

    @Delete
    int deletePhoto(Photo photo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhotos(Photo... photo);

    @Update
    void updatePhotos(Photo... photo);

    @Delete
    void deletePhotos(Photo... photo);
}
