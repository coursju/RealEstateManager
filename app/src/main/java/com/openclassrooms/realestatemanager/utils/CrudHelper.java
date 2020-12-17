package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.model.Estate;

import java.lang.ref.WeakReference;

public class CrudHelper extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "CrudHelper";
    private WeakReference<Estate> mEstateWeakReference;
    private WeakReference<String> mStringWeakReference;
    private WeakReference<Context> mContextWeakReference;

    public CrudHelper(Context context, Estate estate, String string) {
        mEstateWeakReference = new WeakReference<>(estate);
        mStringWeakReference = new WeakReference<>(string);
        mContextWeakReference = new WeakReference<>(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        switch (mStringWeakReference.get()){
            case "insert":
                RealEstateDatabase.getInstance(mContextWeakReference.get()).mEstateDao().insertEstate(mEstateWeakReference.get());
                break;
            case "update":
                RealEstateDatabase.getInstance(mContextWeakReference.get()).mEstateDao().updateEstate(mEstateWeakReference.get());
                break;
        }
        return null;
    }
}
