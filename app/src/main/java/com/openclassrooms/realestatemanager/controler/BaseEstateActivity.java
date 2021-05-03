package com.openclassrooms.realestatemanager.controler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapter.CreateModifyRecyclerAdapter;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.EstateWithPhotos;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.utils.ChipgroupUtils;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.EstateListViewModel;
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BaseEstateActivity extends AppCompatActivity {
    private static final String TAG = "BaseEstateActivity";

    protected static final Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    protected EstateListViewModel estateListViewModel;
    protected EstateViewModel estateViewModel;
    protected Estate estate;
    protected List<Photo> photos;
    protected List<EstateWithPhotos> estateWithPhotosList;
    protected String currentPhotoPath;

    protected AutoCompleteTextView detailsTypeText;
    protected Spinner detailsStatutText;
    protected EditText detailsPriceText;
    protected AutoCompleteTextView detailsAddressText;
    protected AutoCompleteTextView detailsCityText;
    protected EditText detailsDescriptionText;
    protected EditText detailsSurfaceText;
    protected EditText detailsNumberOfRoomsText;
    public EditText detailsPointsOfInterestsText;
    protected ChipGroup detailsPointsOfInterestsChipgroup;
    protected TextView detailsPublicationDateText;
    protected TextView detailsSoldDateText;
    protected EditText detailsAgentNameText;

    protected Button detailsAddGalleryButton;
    protected Button detailsAddCameraButton;
    protected Button submitButton;

    protected RecyclerView detailsPicsRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_estate);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bindView();
        configureAutoCompleteAdapter();
        configureSpinners();
        configureRecyclerView();
        configureEstateListViewModel();
        configureEstateViewModel();
        configureAddressTextWatcher();
;    }

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
            Log.i(TAG,"configureEstateViewModel: getPhotoList");
        });
        this.estateViewModel.getEstate().observe(this, estate -> {
            this.estate = estate;
            putExistingDatas();
            Log.i(TAG,"configureEstateViewModel: getEstate");
        });
    }

    protected void bindView(){
        detailsTypeText = findViewById(R.id.details_type_text);
        detailsStatutText = findViewById(R.id.details_statut_spinner);
        detailsPriceText = findViewById(R.id.details_price_text);
        detailsAddressText = findViewById(R.id.details_address_text);
        detailsCityText = findViewById(R.id.details_city_text);
        detailsDescriptionText = findViewById(R.id.details_description_text);
        detailsSurfaceText = findViewById(R.id.details_surface_text);
        detailsNumberOfRoomsText = findViewById(R.id.details_number_of_rooms_text);
        detailsPointsOfInterestsText = findViewById(R.id.details_points_of_interests_text);
        detailsPointsOfInterestsChipgroup = findViewById(R.id.details_points_of_interests_chipgroup);
        detailsPublicationDateText = findViewById(R.id.details_publication_date_text);
        detailsSoldDateText = findViewById(R.id.details_sold_date_text);
        detailsAgentNameText = findViewById(R.id.details_agent_name_text);

        detailsAddGalleryButton = findViewById(R.id.details_add_gallery_button);
        detailsAddCameraButton = findViewById(R.id.details_add_camera_button);
        submitButton = findViewById(R.id.submit_button);

        detailsPicsRecyclerView = findViewById(R.id.details_pics_recyclerview);
    }

    protected void configureAutoCompleteAdapter(){

        ArrayAdapter<CharSequence> TypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_dropdown_item_1line);
        detailsTypeText.setAdapter(TypeAdapter);
        detailsTypeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsTypeText.showDropDown();
            }
        });

        ArrayAdapter<CharSequence> CityAdapter = ArrayAdapter.createFromResource(this,
                R.array.city_array, android.R.layout.simple_dropdown_item_1line);
        detailsCityText.setAdapter(CityAdapter);
        detailsCityText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsCityText.showDropDown();
            }
        });
    }

    protected void configureSpinners(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.statut_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detailsStatutText.setAdapter(adapter);
        Log.i(TAG, detailsStatutText.getSelectedItem().toString());
        detailsStatutText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    detailsSoldDateText.setText("");
                }else{
                    detailsSoldDateText.setText(Utils.getTodayDate());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    protected void configureRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        detailsPicsRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void configureAddressTextWatcher(){
        this.detailsAddressText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<String> strTab = new ArrayList<>();
                        try {
                            List<Address> mAddressList = new Geocoder(getApplicationContext()).getFromLocationName(s.toString()+" "+detailsCityText.getText().toString(), 10);
                            for (int i =0; i < mAddressList.size(); i++){
                                Address address = mAddressList.get(i);
                                strTab.add(address.getAddressLine(0));
                                Log.i(TAG, strTab.get(i));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                            android.R.layout.simple_dropdown_item_1line, strTab);
                                    detailsAddressText.setAdapter(addressAdapter);
                                    detailsAddressText.showDropDown();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void configureValideButton(View view){}

    public void putExistingDatas(){}

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
        Integer price = (!detailsPriceText.getText().toString().equals(""))?
            Integer.parseInt(detailsPriceText.getText().toString()) : 0;
        Integer surface = (!detailsSurfaceText.getText().toString().equals(""))?
                Integer.parseInt(detailsSurfaceText.getText().toString()) : 0;
        Integer roomsNumber = (!detailsNumberOfRoomsText.getText().toString().equals(""))?
                Integer.parseInt(detailsNumberOfRoomsText.getText().toString()) : 0;

        Estate mEstate = new Estate(
                detailsTypeText.getText().toString(),
                price,
                surface,
                roomsNumber,
                detailsDescriptionText.getText().toString(),
                detailsAddressText.getText().toString(),
                detailsCityText.getText().toString(),
                ChipgroupUtils.saveSelections(detailsPointsOfInterestsChipgroup),
                detailsStatutText.getSelectedItem().toString().equals("Sold"),
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

    protected File createImageFile () throws IOException {
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
    public void addNewChip(View view) {
        ChipgroupUtils.addNewChip(this, detailsPointsOfInterestsChipgroup, detailsPointsOfInterestsText.getText().toString());
        detailsPointsOfInterestsText.setText("");
    }

    @Override
    protected void onDestroy() {
        this.estate = createEstateObject();
        estateViewModel.setEstate(this.estate);
        super.onDestroy();
    }
}
