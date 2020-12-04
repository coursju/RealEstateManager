package com.openclassrooms.realestatemanager.injection;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {


    private final EstateDataRepository mEstateDataSource;
    private final Executor executor;

    public ViewModelFactory(EstateDataRepository estateDataSource, Executor executor) {
        this.mEstateDataSource = estateDataSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EstateViewModel.class)) {
            return (T) new EstateViewModel(mEstateDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}