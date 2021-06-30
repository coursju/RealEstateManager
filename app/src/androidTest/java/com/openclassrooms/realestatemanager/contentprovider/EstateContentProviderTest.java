package com.openclassrooms.realestatemanager.contentprovider;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.openclassrooms.realestatemanager.controler.MainActivity;
import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.provider.EstateContentProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class EstateContentProviderTest {
    private static final String TAG = "EstateContentProviderTest";

    private ContentResolver mContentResolver;

    @Before
    public void setUp() {
        mContentResolver = InstrumentationRegistry.getContext().getContentResolver();
    }

    @Test
    public void getEstate() {
            String selection = "SELECT * FROM Estate";
            final Cursor cursor = mContentResolver.query(EstateContentProvider.URI_ITEM, null, selection, null, null);
            assertThat(cursor, notNullValue());
            cursor.moveToFirst();
            Integer p = cursor.getPosition();
            assertThat(p, is(0));
            Log.i(TAG, cursor.getString(12));
//            assertThat(cursor.getString(12), is("brian")); // put final value
            cursor.close();
    }
}
