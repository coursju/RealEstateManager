package com.openclassrooms.realestatemanager.database;

import android.database.Cursor;
import android.util.Log;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Photo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private EstateDao estateDao;
    private Estate estate;
    private Photo photo;
    private long id;

    @Before
    public void initDb() throws Exception {
        RealEstateDatabase db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RealEstateDatabase.class)
                .allowMainThreadQueries()
                .build();

        estate = new Estate( "loft", 100, 1, 1,
                "description", "pas loin", "city", "cool",
                true, "19/12/19", "19/12/19", "brian");

        estateDao = db.mEstateDao();
    }


    @Test
    public void insertAndQueryEstateTest(){
        id = estateDao.insertEstate(estate);

        Cursor cursor = estateDao.getEstatesWithCursor();
        assertTrue(cursor != null);
        cursor.moveToFirst();
        Log.i("DatabaseTest", cursor.getString(12));
        assertThat(cursor.getString(12) ,is("brian") );
    }

    @Test
    public void insertAndQueryPhotoTest(){
        Photo photo = new Photo(id, new byte[]{} ,"photoDescription");
        estateDao.insertPhoto(photo);

        Cursor cursor = estateDao.getPhotosWithCursorByID(id);
        assertTrue(cursor != null);
        cursor.moveToFirst();
        assertThat(cursor.getString(3), is("photoDescription"));
    }
}
