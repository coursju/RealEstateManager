package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class EstateViewModel extends ViewModel {
    private MutableLiveData<Estate> estate;
    private MutableLiveData<List<Photo>> photoList;
    private MutableLiveData<Integer> selected;
    private MutableLiveData<Boolean> isDollard;

    public LiveData<Estate> getEstate() {
        if (estate == null){
            estate = new MutableLiveData<Estate>();
        }
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate.setValue(estate);
    }

    public LiveData<List<Photo>> getPhotoList() {
        if (photoList == null){
            photoList = new MutableLiveData<List<Photo>>();
        }
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        if (this.photoList == null){
            this.photoList = new MutableLiveData<List<Photo>>();
        }
        this.photoList.setValue(photoList);
    }

    public void addPhoto(Photo photo){
        if (this.photoList == null){
            this.photoList = new MutableLiveData<>(new ArrayList<>());
        }
        List<Photo> list = photoList.getValue();
        list.add(photo);
        photoList.setValue(list);
    }

    public void removePhoto(int index){
        if (this.photoList != null){
            photoList.getValue().remove(index);
        }
    }

    public MutableLiveData<Integer> getSelected() {
        if (this.selected == null){
            this.selected = new MutableLiveData<Integer>(0);
        }
        return selected;
    }

    public void setSelected(Integer selected) {
        if (this.selected == null){
            this.selected = new MutableLiveData<Integer>(0);
        }
        this.selected.setValue(selected);
    }

    public MutableLiveData<Boolean> getIsDollard() {
        if (this.isDollard == null){
            this.isDollard = new MutableLiveData<Boolean>(true);
        }
        return isDollard;
    }

    public void setIsDollard() {
        if (this.isDollard == null){
            this.isDollard = new MutableLiveData<Boolean>(true);
        }
        this.isDollard.setValue(!this.isDollard.getValue());
    }
}
