package com.openclassrooms.realestatemanager.controler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.openclassrooms.realestatemanager.R;
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
    public void configureValideButton(View view) {
        if (photos != null){
            if (photos.size() == 0){
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.create_valid_button_empty_photos), Toast.LENGTH_SHORT).show();
            }
            else{
                long id = estateWithPhotosList.size();
                Log.i(TAG, "configureValideButton" + String.valueOf(id));
                estateListViewModel.createEstate(createEstateObject());

                for (Photo photo : photos) {
                    photo.setEstateCreatorId(id + 1);
                    estateListViewModel.createPhoto(photo);
                }

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                sendNotification();
            }
        }else{
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.create_valid_button_empty_photos), Toast.LENGTH_SHORT).show();
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

    public void sendNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("estate created", "estate created", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(CreateEstateActivity.this, "estate created")
                .setSmallIcon(R.drawable.add_black_24dp)
                .setAutoCancel(true)
                .setContentTitle(getResources().getString(R.string.notification_title))
                //.setContentText("textContent")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1234, builder.build());
    }
}