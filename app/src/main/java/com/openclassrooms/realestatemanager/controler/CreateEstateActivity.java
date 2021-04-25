package com.openclassrooms.realestatemanager.controler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.utils.ChipgroupUtils;
import com.openclassrooms.realestatemanager.utils.Utils;

public class CreateEstateActivity extends BaseEstateActivity {

    private final String TAG = "CreateEstateActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void bindView(){
        super.bindView();
        detailsPublicationDateText.setText(Utils.getTodayDate());
        detailsStatutText.setEnabled(false);
    }

    @Override
    public void configureEstateListViewModel() {
        super.configureEstateListViewModel();
        estateListViewModel.getEstateWithPhotos().observe(this, estatesWithPhotosList ->{
            estateWithPhotosList = estatesWithPhotosList;
        });
    }

    @Override
    public void configureValideButton(View view){
        long id = estateWithPhotosList.size();
        Log.i(TAG, "configureValideButton"+String.valueOf(id));
        estateListViewModel.createEstate(createEstateObject());

        for (Photo photo : photos){
            photo.setEstateCreatorId(id+1);
            estateListViewModel.createPhoto(photo);
        }
    }

    @Override
    public void putExistingDatas(){
        if (estate != null){
            String[] str = estate.getInterestingSpots().split(",");
            for (int i = 0; i < str.length; i++){
                ChipgroupUtils.addNewChip( this, detailsPointsOfInterestsChipgroup, str[i]);
            }
        }
    }
}