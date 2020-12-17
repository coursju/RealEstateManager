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
import com.openclassrooms.realestatemanager.utils.ImagesSQLiteConverter;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Estate.class}, version = 1, exportSchema = false)
@TypeConverters({ImagesSQLiteConverter.class})
public abstract class RealEstateDatabase extends RoomDatabase{

    private static Context mContext;

    private static final String TAG = "--RealEstateDatabase";
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

    //if bug look at callback import androidx.room
    private static RoomDatabase.Callback prepopulateDatabase(){
        return new RoomDatabase.Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                List<Bitmap> bitmap = new ArrayList<Bitmap>();
                Bitmap bitmap1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pict1);
                Bitmap bitmap2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pict3);

                bitmap.add(bitmap1);
                bitmap.add(bitmap2);

                String str = ImagesSQLiteConverter.fromBitmapListToJson(bitmap);

                ContentValues contentValues = new ContentValues();
                contentValues.put("mType", "Loft");
                contentValues.put("mPrice", 100000);
                contentValues.put("mSurface", 100);
                contentValues.put("mRoomNumber", 10);
                contentValues.put("mDescription", "Beautiful loft, center of NY");
                contentValues.put("mAddress", "dans ton Q");
                contentValues.put("mCity", "Brooklyn");
                contentValues.put("mInterestingSpots", "school, hospital, markets");
                contentValues.put("mSold", false);
                contentValues.put("mSoldDate", "---");
                contentValues.put("mAgentName", "Brian Brian");
                contentValues.put("mPhotosString", str);

                Log.i(TAG,"callback"+contentValues.toString());

                db.insert("Estate", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }

}
