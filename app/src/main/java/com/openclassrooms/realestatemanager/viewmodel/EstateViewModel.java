package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.EstateWithPhotos;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class EstateViewModel extends ViewModel {

    private final EstateDataRepository mEstateDataSource;
    private final Executor executor;
    private int selectedEstate;

    public EstateViewModel(EstateDataRepository estateDataSource, Executor executor) {
        mEstateDataSource = estateDataSource;
        this.executor = executor;
        this.selectedEstate = 0;
    }

    public int getSelectedEstate(){return this.selectedEstate;}

    public void setSelectedEstate(int selectedEstate){this.selectedEstate = selectedEstate;}

    public LiveData<List<EstateWithPhotos>> getEstateWithPhotos() {
        return mEstateDataSource.getEstateWithPhotos();
    }

    // --Estate--
    public long createEstate(Estate estate) {
        final long[] id = new long[1];
        executor.execute(() -> {
            id[0] = mEstateDataSource.createEstate(estate);
        });
        return id[0];
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
