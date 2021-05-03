package com.openclassrooms.realestatemanager.controler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.EstateWithPhotos;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.utils.ChipgroupUtils;

public class ModifyEstateActivity extends BaseEstateActivity {

    private final String TAG = "ModifyEstateActivity";
    protected EstateWithPhotos estateWithPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void configureEstateListViewModel() {
        super.configureEstateListViewModel();
        estateListViewModel.getEstateWithPhotos().observe(this, estatesWithPhotosList ->{
            estateWithPhotosList = estatesWithPhotosList;
            int position = getIntent().getIntExtra("position", 0);
            estateWithPhotos = estatesWithPhotosList.get(position);
            estateViewModel.setEstate(estateWithPhotos.getEstate());
            estateViewModel.setPhotoList(estateWithPhotos.getPhotoList());
        });
    }

    @Override
    public void configureValideButton(View view) {
        if (photos != null){
            if (photos.size() == 0){
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.create_valid_button_empty_photos), Toast.LENGTH_SHORT).show();
            }
            else{
                long id = estate.getEstateId();
                Estate mEstate = createEstateObject();
                mEstate.setEstateId(id);
                estateListViewModel.updateEstate(mEstate);

                for (Photo photo1 : estateWithPhotos.getPhotoList()){
                    estateListViewModel.deletePhoto(photo1);
                }

                for (Photo photo2 : photos){
                    photo2.setEstateCreatorId(id);
                    estateListViewModel.createPhoto(photo2);
                }

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }else{
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.create_valid_button_empty_photos), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void putExistingDatas() {
        submitButton.setText(getResources().getString(R.string.modify_submit_button));
        if (estate != null){
            String[] str = estate.getInterestingSpots().split(",");
            for (int i = 0; i < str.length; i++){
                ChipgroupUtils.addNewChip( this, detailsPointsOfInterestsChipgroup, str[i]);
            }

            detailsTypeText.setText(estate.getType());
            detailsStatutText.setSelection((estate.getSold())? 1 : 0 );
            detailsPriceText.setText(String.valueOf(estate.getPrice()));
            detailsAddressText.setText(estate.getAddress());
            detailsCityText.setText(estate.getCity());
            detailsDescriptionText.setText(estate.getDescription());
            detailsSurfaceText.setText(String.valueOf(estate.getSurface()));
            detailsNumberOfRoomsText.setText(String.valueOf(estate.getRoomNumber()));
            detailsPublicationDateText.setText(estate.getPublicationDate());
            detailsSoldDateText.setText(estate.getSoldDate());
            detailsAgentNameText.setText(estate.getAgentName());
        }
    }
}

