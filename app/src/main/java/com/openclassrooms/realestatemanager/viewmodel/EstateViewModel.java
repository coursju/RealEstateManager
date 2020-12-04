package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class EstateViewModel extends ViewModel {

    private final EstateDataRepository mEstateDataSource;
    private final Executor executor;

    public EstateViewModel(EstateDataRepository estateDataSource, Executor executor) {
        mEstateDataSource = estateDataSource;
        this.executor = executor;
    }

    public LiveData<List<Estate>> getEstates() {
        return mEstateDataSource.getItems();
    }

    public void createEstate(Estate estate) {
        executor.execute(() -> {
            mEstateDataSource.createItem(estate);
        });
    }

    public void updateEstate(Estate estate) {
        executor.execute(() -> {
            mEstateDataSource.updateItem(estate);
        });
    }
}
