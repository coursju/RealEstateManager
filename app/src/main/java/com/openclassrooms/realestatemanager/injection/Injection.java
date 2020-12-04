package com.openclassrooms.realestatemanager.injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static EstateDataRepository provideEstateDataSource(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new EstateDataRepository(database.mEstateDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        EstateDataRepository dataSourceEstate = provideEstateDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceEstate, executor);
    }

}
