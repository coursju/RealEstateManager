package com.openclassrooms.realestatemanager.controler;

import android.content.ContentResolver;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapter.DetailsRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.helper.EstateList;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.utils.FromCursorToEstateList;
import com.openclassrooms.realestatemanager.utils.GetEstateListCallback;

import java.io.IOException;
import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;


public class DetailsFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "DetailsFragment";

    private Button buttonTxt;
    private ImageView img;
    private String leTest;
    private FloatingActionButton estateDetailsFloatingBtn;

    private ContentResolver mContentResolver;
    private GetEstateListCallback mGetEstateListCallback;
    private View view;
    private RecyclerView mRecyclerView;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    private int mPosition;

    public DetailsFragment(){}

    public DetailsFragment(int mPosition) {
        this.mPosition = mPosition;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureGetEstateListCallback();
        mContentResolver = getContext().getContentResolver();
//        new FromCursorToEstateList(mContentResolver, mGetEstateListCallback).execute();
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

        configureView();

        configureDetailsFloatingBtnListener();

        configureRecyclerView();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
    }

    private void configureGetEstateListCallback(){
        this.mGetEstateListCallback = new GetEstateListCallback() {
            @Override
            public void updateEstateList(List<Estate> estateList) {
                Log.d(TAG, "into configureGetEstateListCallback ");
                mRecyclerView.setAdapter(new DetailsRecyclerViewAdapter(estateList));

            }
        };
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<Address> mAddressList = null;
        try {
            mAddressList = new Geocoder(getContext()).getFromLocationName("marius et ary leblond , saint pierre, reunion", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mAddressList != null && !mAddressList.isEmpty()){
            LatLng mLatLng = new LatLng(mAddressList.get(0).getLatitude(),mAddressList.get(0).getLongitude());
            mMap.addMarker(new MarkerOptions().position(mLatLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,15));
        }

    }

    private void configureView(){
        mRecyclerView = view.findViewById(R.id.details_pics_recyclerview);
        estateDetailsFloatingBtn = view.findViewById(R.id.estateDetailsFloatingBtn);

    }

    private void configureDetailsFloatingBtnListener(){
        this.estateDetailsFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ModifyEstateActivity.class);
                //putextra string
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