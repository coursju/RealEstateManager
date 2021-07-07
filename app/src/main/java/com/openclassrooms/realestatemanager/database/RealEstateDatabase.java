package com.openclassrooms.realestatemanager.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Photo;

@Database(entities = {Estate.class, Photo.class}, version = 1)
public abstract class RealEstateDatabase extends RoomDatabase{

    private static Context mContext;

    private static final String TAG = "RealEstateDatabase";
    // --- SINGLETON ---
    private static volatile RealEstateDatabase INSTANCE;

    // --- DAO ---
    public abstract EstateDao mEstateDao();

    // --- INSTANCE ---
    public static RealEstateDatabase getInstance(Context context) {
        mContext = context;
        Log.i(TAG,"getInstance");
        if (INSTANCE == null) {
            synchronized (RealEstateDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateDatabase.class, "RealEstateDatabase.db")
                            .createFromAsset("RealEstateDatabase.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
