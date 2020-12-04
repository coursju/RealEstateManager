package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.model.Estate;

@Database(entities = {Estate.class}, version = 1, exportSchema = false)
public abstract class RealEstateDatabase extends RoomDatabase{

    private static final String TAG = "--RealEstateDatabase";
    // --- SINGLETON ---
    private static volatile RealEstateDatabase INSTANCE;

    // --- DAO ---
    public abstract EstateDao mEstateDao();

    // --- INSTANCE ---
    public static RealEstateDatabase getInstance(Context context) {
        Log.i(TAG,"getInstance");
        if (INSTANCE == null) {
            synchronized (RealEstateDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateDatabase.class, "RealEstateDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //if bug look at callback import androidx.room
    private static RoomDatabase.Callback prepopulateDatabase(){
        return new RoomDatabase.Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("mType", "Loft");
                contentValues.put("mPrice", 100000);
                contentValues.put("mSurface", 100);
                contentValues.put("mRoomNumber", 10);
                contentValues.put("mDescription", "Beautiful loft, center of NY");
                contentValues.put("mAddress", "dans ton Q");
                contentValues.put("mInterestingSpots", "school, hospital, markets");
                contentValues.put("mSold", false);
                contentValues.put("mSoldDate", "---");
                contentValues.put("mAgentName", "Brian Brian");

                Log.i(TAG,"callback"+contentValues.toString());

                db.insert("Estate", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }

}
