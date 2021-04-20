package com.openclassrooms.realestatemanager.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Photo;

public class EstateContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.openclassrooms.realestatemanager.provider";
    public static final String TABLE_NAME1 = Estate.class.getSimpleName();
    public static final Uri URI_ITEM1 = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME1);
    public static final String TABLE_NAME2 = Photo.class.getSimpleName();
    public static final Uri URI_ITEM2 = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME2);

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (getContext() != null){
            if (uri == URI_ITEM1){
                final Cursor cursor;
                cursor = RealEstateDatabase.getInstance(getContext()).mEstateDao().getEstatesWithCursor();
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }else if (uri == URI_ITEM2){
                final Cursor cursor;
                cursor = RealEstateDatabase.getInstance(getContext()).mEstateDao().getPhotosWithCursorByID(Long.parseLong(selection));
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }
        }

        throw new IllegalArgumentException("Failed to query row for uri " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
