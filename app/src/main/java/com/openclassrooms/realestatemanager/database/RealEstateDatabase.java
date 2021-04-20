package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.utils.ImagesSQLiteConverter;

import java.util.ArrayList;
import java.util.List;

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
                            .addCallback(prepopulateDatabase())
                           // .createFromAsset("RealEstateDatabase_backup.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback prepopulateDatabase(){
        return new RoomDatabase.Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("type", "Loft");
                contentValues.put("price", 100000);
                contentValues.put("surface", 100);
                contentValues.put("roomNumber", 10);
                contentValues.put("description", "Beautiful loft, center of NY");
                contentValues.put("address", "dans ton Q");
                contentValues.put("city", "Brooklyn");
                contentValues.put("interestingSpots", "school, hospital, markets");
                contentValues.put("sold", false);
                contentValues.put("soldDate", "---");
                contentValues.put("agentName", "Brian Brian");

                Log.i(TAG,"callback"+contentValues.toString());

                db.insert("Estate", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
