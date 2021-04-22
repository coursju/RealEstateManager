package com.openclassrooms.realestatemanager.controler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapter.CreateModifyRecyclerAdapter;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.EstateListViewModel;
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateEstateActivity extends AppCompatActivity {

    private final String TAG = "CreateEstateActivity";
    private static final Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    private EstateListViewModel estateListViewModel;
    private EstateViewModel estateViewModel;
    private Estate estate;
    private List<Photo> photos;
    private String currentPhotoPath;

    private EditText detailsTypeText;
    private Spinner detailsStatutText;
    private EditText detailsPriceText;
    private EditText detailsAddressText;
    private EditText detailsCityText;
    private EditText detailsDescriptionText;
    private EditText detailsSurfaceText;
    private EditText detailsNumberOfRoomsText;
    private EditText detailsPointsOfInterestsText;
    private TextView detailsPublicationDateText;
    private TextView detailsSoldDateText;
    private EditText detailsAgentNameText;

    private Button detailsAddGalleryButton;
    private Button detailsAddCameraButton;
    private Button submitButton;

    private RecyclerView detailsPicsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_estate);
        bindView();
        configureSpinners();
        configureRecyclerView();
        configureEstateListViewModel();
        configureEstateViewModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (photos != null) Log.i(TAG, String.valueOf(photos.size()));
    }

    public void configureEstateListViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getApplicationContext());
        this.estateListViewModel =  ViewModelProviders.of(this, mViewModelFactory).get(EstateListViewModel.class);
    }

    public void configureEstateViewModel(){
        this.estateViewModel = new ViewModelProvider(this).get(EstateViewModel.class);
        this.estateViewModel.getPhotoList().observe( this, list ->{
            photos = list;
            detailsPicsRecyclerView.setAdapter(new CreateModifyRecyclerAdapter(photos, this));
        });
    }

    private void bindView(){
        detailsTypeText = findViewById(R.id.details_type_text);
        detailsStatutText = findViewById(R.id.details_statut_spinner);
        detailsPriceText = findViewById(R.id.details_price_text);
        detailsAddressText = findViewById(R.id.details_address_text);
        detailsCityText = findViewById(R.id.details_city_text);
        detailsDescriptionText = findViewById(R.id.details_description_text);
        detailsSurfaceText = findViewById(R.id.details_surface_text);
        detailsNumberOfRoomsText = findViewById(R.id.details_number_of_rooms_text);
        detailsPointsOfInterestsText = findViewById(R.id.details_points_of_interests_text);
        detailsPublicationDateText = findViewById(R.id.details_publication_date_text);
        detailsSoldDateText = findViewById(R.id.details_sold_date_text);
        detailsAgentNameText = findViewById(R.id.details_agent_name_text);

        detailsAddGalleryButton = findViewById(R.id.details_add_gallery_button);
        detailsAddCameraButton = findViewById(R.id.details_add_camera_button);
        submitButton = findViewById(R.id.submit_button);

        detailsPicsRecyclerView = findViewById(R.id.details_pics_recyclerview);

        detailsPublicationDateText.setText(Utils.getTodayDate());
        detailsStatutText.setEnabled(false);
    }

    private void configureSpinners(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.statut_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detailsStatutText.setAdapter(adapter);
        Log.i(TAG, detailsStatutText.getSelectedItem().toString());
    }

    private void configureRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        detailsPicsRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void configureCreateButton(View view){
        long id = estateListViewModel.createEstate(createEstateObject());
        for (Photo photo : photos){
            photo.setEstateCreatorId(id);
        }
        Photo[] photoArray = (Photo[]) photos.toArray();
        estateListViewModel.createPhoto(photoArray);
    }

    public  void configureAddFromGalleryButton(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    public void configureTakeAPictureButton(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
              Log.d(TAG, ex.getMessage());
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.openclassrooms.realestatemanager.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(intent, REQUEST_CAMERA);
            }
        }
    }

    public Estate createEstateObject () {
        Estate mEstate = new Estate(
                detailsTypeText.getText().toString(),
                Integer.parseInt(detailsPriceText.getText().toString()),
                Integer.parseInt(detailsSurfaceText.getText().toString()),
                Integer.parseInt(detailsNumberOfRoomsText.getText().toString()),
                detailsDescriptionText.getText().toString(),
                detailsAddressText.getText().toString(),
                detailsCityText.getText().toString(),
                detailsPointsOfInterestsText.getText().toString(),
                detailsStatutText.getSelectedItem().toString().equals("On sale"),
                detailsPublicationDateText.getText().toString(),
                detailsSoldDateText.getText().toString(),
                detailsAgentNameText.getText().toString());
        return mEstate;
    }

            public void removePhoto(int index){
                this.photos.remove(index);
                estateViewModel.setPhotoList(photos);
            }

            public void addPhotoDescription(int index, String description){
                this.photos.get(index).setPhotoDescription(description);
                estateViewModel.setPhotoList(photos);
            }

            @Override
            public void onActivityResult ( int requestCode, int resultCode, Intent data){
                super.onActivityResult(requestCode, resultCode, data);

                if (resultCode == Activity.RESULT_OK) {

                    if (requestCode == REQUEST_CAMERA) {

                        Bitmap bmp = BitmapFactory.decodeFile(currentPhotoPath);
                        Photo photo = new Photo();
                        photo.setPhoto(bmp);
                        if (photos == null) photos = new ArrayList<>();
                        photos.add(photo);
                        estateViewModel.setPhotoList(photos);
                        Log.i(TAG, "onActivityResult camera :" + String.valueOf(photos.size()));

                    } else if (requestCode == SELECT_FILE) {

                        Uri selectedImageUri = data.getData();
                        Bitmap bmp = null;

                        try {
                            bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                            Photo photo = new Photo();
                            photo.setPhoto(bmp);
                            if (photos == null) photos = new ArrayList<>();
                            photos.add(photo);
                            estateViewModel.setPhotoList(photos);
                            Log.i(TAG, "onActivityResult image :" + String.valueOf(photos.size()));

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    }

                }
            }

            private File createImageFile () throws IOException {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File image = File.createTempFile(
                        imageFileName,
                        ".jpg",
                        storageDir
                );
                currentPhotoPath = image.getAbsolutePath();
                return image;
            }
}