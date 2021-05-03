package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.EstateWithPhotos;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

public class EstateListViewModel extends ViewModel {

    private final EstateDataRepository mEstateDataSource;
    private final Executor executor;

    public EstateListViewModel(EstateDataRepository estateDataSource, Executor executor) {
        mEstateDataSource = estateDataSource;
        this.executor = executor;
    }

    public LiveData<List<EstateWithPhotos>> getEstateWithPhotos() {
        return mEstateDataSource.getEstateWithPhotos();
    }

    // --Estate--
    public long createEstate(Estate estate) {
        AtomicLong id = new AtomicLong();
        executor.execute(() -> {
            id.set(mEstateDataSource.createEstate(estate));
        });
        return id.get();
    }

    public void updateEstate(Estate estate) {
        executor.execute(() -> {
            mEstateDataSource.updateEstate(estate);
        });
    }

    // --Photo--
    public void createPhoto(Photo photo) {
        executor.execute(() -> {
            mEstateDataSource.createPhoto(photo);
        });
    }

    public void createPhoto(Photo... photo) {
        executor.execute(() -> {
            mEstateDataSource.createPhoto(photo);
        });
    }

    public void updatePhoto(Photo photo) {
        executor.execute(() -> {
            mEstateDataSource.updatePhoto(photo);
        });
    }

    public void updatePhoto(Photo... photo) {
        executor.execute(() -> {
            mEstateDataSource.updatePhoto(photo);
        });
    }

    public void deletePhoto(Photo photo) {
        executor.execute(() -> {
            mEstateDataSource.deletePhoto(photo);
        });
    }

    public void deletePhoto(Photo... photo) {
        executor.execute(() -> {
            mEstateDataSource.deletePhoto(photo);
        });
    }
}
