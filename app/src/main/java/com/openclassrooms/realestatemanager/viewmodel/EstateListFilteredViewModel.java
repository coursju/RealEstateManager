package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.EstateWithPhotos;

import java.util.ArrayList;
import java.util.List;

public class EstateListFilteredViewModel extends ViewModel {

    private MutableLiveData<Boolean> filtered;
    private MutableLiveData<List<EstateWithPhotos>> filteredList;

    public LiveData<Boolean> getFiltered() {
        if (this.filtered == null){
            this.filtered = new MutableLiveData<Boolean>(false);
        }
        return this.filtered;
    }

    public void setFiltered(Boolean updatedFiltered){
        if (this.filtered == null){
            this.filtered = new MutableLiveData<Boolean>();
        }
        this.filtered.setValue(updatedFiltered);
    }

    public LiveData<List<EstateWithPhotos>> getFilteredList() {
        if (this.filteredList == null){
            this.filteredList = new MutableLiveData<List<EstateWithPhotos>>();
        }
        return filteredList;
    }

    public void setFilteredList(List<EstateWithPhotos> updatedFilteredList) {
        if (this.filteredList == null){
            this.filteredList = new MutableLiveData<List<EstateWithPhotos>>();
        }
        this.filteredList.setValue(updatedFilteredList);
        this.filtered.setValue(true);
    }
}
