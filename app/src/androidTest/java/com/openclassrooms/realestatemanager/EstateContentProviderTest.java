package com.openclassrooms.realestatemanager;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.provider.EstateContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
    public class EstateContentProviderTest {

    private static final String TAG = "EstateContentProviderTest";
    // FOR DATA
        private ContentResolver mContentResolver;

        // DATA SET FOR TEST
        private static long USER_ID = 1;

        @Before
        public void setUp() {
            Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                    RealEstateDatabase.class)
                    .allowMainThreadQueries()
                    .build();
            mContentResolver = InstrumentationRegistry.getContext().getContentResolver();
        }

        @Test
        public void getEstate() {
            final Cursor cursor = mContentResolver.query(EstateContentProvider.URI_ITEM, null, null, null, null);
            assertThat(cursor, notNullValue());
//            assertThat(cursor.getCount(), is(4));
//            Integer str = cursor.getInt(4);
            cursor.moveToFirst();
            Integer i = cursor.getColumnCount();
            Integer p = cursor.getPosition();
            Log.d(TAG,i+" "+p+" "+cursor.getString(12));//str.toString());
            cursor.close();
        }

//        @Test
//        public void insertAndGetItem() {
//            // BEFORE : Adding demo item
//            final Uri userUri = mContentResolver.insert(EstateContentProvider.URI_ITEM, generateItem());
//            // TEST
//            final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(EstateContentProvider.URI_ITEM, USER_ID), null, null, null, null);
//            assertThat(cursor, notNullValue());
//            assertThat(cursor.getCount(), is(1));
//            assertThat(cursor.moveToFirst(), is(true));
//            assertThat(cursor.getString(cursor.getColumnIndexOrThrow("text")), is("Visite cet endroit de rêve !"));
//        }

//        // ---
//
//        private ContentValues generateItem(){
//            final ContentValues values = new ContentValues();
//            values.put("text", "Visite cet endroit de rêve !");
//            values.put("category", "0");
//            values.put("isSelected", "false");
//            values.put("userId", "1");
//            return values;
//        }
    }
