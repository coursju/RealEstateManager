package com.openclassrooms.realestatemanager.controler;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapter.DetailsRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.adapter.EstateRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.EstateWithPhotos;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.EstateListFilteredViewModel;
import com.openclassrooms.realestatemanager.viewmodel.EstateListViewModel;
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;


public class DetailsFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = DetailsFragment.class.getSimpleName();

    private FloatingActionButton estateDetailsFloatingBtn;
    private RecyclerView mRecyclerView;

    private View view;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    private int mPosition;

    private TextView detailsTypeText;
    private TextView detailsStatutText;
    private TextView detailsPriceText;
    private TextView detailsAddressText;
    private TextView detailsCityText;
    private TextView detailsDescriptionText;
    private TextView detailsSurfaceText;
    private TextView detailsNumberOfRoomsText;
    private TextView detailsPointsOfInterestsText;
    private TextView detailsPublicationDateText;
    private TextView detailsSoldDateText;
    private TextView detailsAgentNameText;

    private EstateWithPhotos estateWithPhotos;
    private EstateListViewModel estateListViewModel;
    private EstateListFilteredViewModel estateListFilteredViewModel;

    public DetailsFragment(){}

    public DetailsFragment(int mPosition) {
        this.mPosition = mPosition;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mapFragment == null) {
            GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
            mapFragment = SupportMapFragment.newInstance(options);
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");

        view = inflater.inflate(R.layout.fragment_details, container, false);

        bindView();
        configureDetailsFloatingBtnListener();
        configureViewModel();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        getChildFragmentManager().beginTransaction().replace(R.id.maps_map, mapFragment).commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void updateMapAddress(){
        if (mMap != null){
            Executors.newCachedThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Address> mAddressList = null;
                        if (estateWithPhotos != null)
                            mAddressList = new Geocoder(getContext()).getFromLocationName(estateWithPhotos.getEstate().getAddress()+" "+estateWithPhotos.getEstate().getCity(), 1);
                        Log.i(TAG, "updateMapAddress");
                        List<Address> finalMAddressList = mAddressList;
                       if (getActivity() != null){
                           getActivity().runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   if (finalMAddressList != null && !finalMAddressList.isEmpty()){
                                       LatLng mLatLng = new LatLng(finalMAddressList.get(0).getLatitude(), finalMAddressList.get(0).getLongitude());
                                       mMap.addMarker(new MarkerOptions().position(mLatLng));
                                       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,15));
                                   }
                               }
                           });
                       }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        this.estateListViewModel =  ViewModelProviders.of(this, mViewModelFactory).get(EstateListViewModel.class);
        this.estateListFilteredViewModel = new ViewModelProvider(requireActivity()).get(EstateListFilteredViewModel.class);
        this.estateListFilteredViewModel.getFiltered().observe(this, isFiltered ->{
            if (isFiltered){
                estateListFilteredViewModel.getFilteredList().observe(this, filteredList -> {
                    if (!filteredList.isEmpty() && mPosition < filteredList.size()) {
                        estateWithPhotos = filteredList.get(mPosition);
                        configureView();
                        configureRecyclerView();
                        updateMapAddress();
                        mRecyclerView.setAdapter(new DetailsRecyclerViewAdapter(estateWithPhotos.getPhotoList()));
                    }else {getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();}
                });
            }else {
                estateListViewModel.getEstateWithPhotos().observe(this, estates -> {
                    estateWithPhotos = estates.get(mPosition);
                    configureView();
                    configureRecyclerView();
                    updateMapAddress();
                    mRecyclerView.setAdapter(new DetailsRecyclerViewAdapter(estateWithPhotos.getPhotoList()));
                });
            }
        });
    }

    private void bindView(){
        mRecyclerView = view.findViewById(R.id.details_pics_recyclerview);
        estateDetailsFloatingBtn = view.findViewById(R.id.estateDetailsFloatingBtn);
        detailsTypeText = view.findViewById(R.id.details_type_text);
        detailsStatutText = view.findViewById(R.id.details_statut_text);
        detailsPriceText = view.findViewById(R.id.details_price_text);
        detailsAddressText = view.findViewById(R.id.details_address_text);
        detailsCityText = view.findViewById(R.id.details_city_text);
        detailsDescriptionText = view.findViewById(R.id.details_description_text);
        detailsSurfaceText = view.findViewById(R.id.details_surface_text);
        detailsNumberOfRoomsText = view.findViewById(R.id.details_number_of_rooms_text);
        detailsPointsOfInterestsText = view.findViewById(R.id.details_points_of_interests_chipgroup);
        detailsPublicationDateText = view.findViewById(R.id.details_publication_date_text);
        detailsSoldDateText = view.findViewById(R.id.details_sold_date_text);
        detailsAgentNameText = view.findViewById(R.id.details_agent_name_text);
    }

    private void configureView(){
        Boolean isDollard = new ViewModelProvider(requireActivity()).get(EstateViewModel.class).getIsDollard().getValue();
        String str;
        if (estateWithPhotos != null){
            Estate estate = estateWithPhotos.getEstate();
            str =
                    (isDollard)?
                            "USD "+String.valueOf(estate.getPrice()) :
                            "EUR "+String.valueOf(Utils.convertDollarToEuro(estate.getPrice()));
            detailsTypeText.setText(estate.getType());
            detailsStatutText.setText((estate.getSold())? "Sold" : "On sale");
            detailsPriceText.setText(str);
            detailsAddressText.setText(estate.getAddress());
            detailsCityText.setText(estate.getCity());
            detailsDescriptionText.setText(estate.getDescription());
            detailsSurfaceText.setText(estate.getSurface().toString());
            detailsNumberOfRoomsText.setText(estate.getRoomNumber().toString());
            detailsPointsOfInterestsText.setText(estate.getInterestingSpots());
            detailsPublicationDateText.setText(estate.getPublicationDate());
            detailsSoldDateText.setText(estate.getSoldDate());
            detailsAgentNameText.setText(estate.getAgentName());
        }
    }

    private void configureDetailsFloatingBtnListener(){
        this.estateDetailsFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ModifyEstateActivity.class);
                intent.putExtra("position", mPosition);
                startActivity(intent);
            }
        });
    }

    private void configureRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }
}