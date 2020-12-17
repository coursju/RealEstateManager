package com.openclassrooms.realestatemanager.controler;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.utils.CrudHelper;
import com.openclassrooms.realestatemanager.utils.FromCursorToEstateList;
import com.openclassrooms.realestatemanager.utils.GetEstateListCallback;
import com.openclassrooms.realestatemanager.utils.ImagesSQLiteConverter;
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel;

import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment {

    private static final String TAG = "DetailsFragment";

    private Button buttonTxt;
    private ImageView img;
    private String leTest;

    private ContentResolver mContentResolver;
    private GetEstateListCallback mGetEstateListCallback;
    private List<Estate> mEstateList;

    private EstateViewModel estateViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureGetEstateListCallback();
        mContentResolver = getContext().getContentResolver();
        new FromCursorToEstateList(mContentResolver, mGetEstateListCallback).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");

        View view = inflater.inflate(R.layout.fragment_details, container, false);
        buttonTxt = view.findViewById(R.id.button);
        img = view.findViewById(R.id.imageView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onViewCreated");
        super.onViewCreated(view, savedInstanceState);

//        configureViewModel();
    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        this.estateViewModel =  ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel.class);
//        //try unsert
//        List<Bitmap> bitmap = new ArrayList<Bitmap>();
//        bitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.pict1));
//        String str = ImagesSQLiteConverter.fromValuesToList(bitmap);
//        Estate est = new Estate("pour le test",1,1,1,"","","","",true,"","",str);
//        estateViewModel.createEstate(est);
    }

    private void configureGetEstateListCallback(){
        this.mGetEstateListCallback = new GetEstateListCallback() {
            @Override
            public void updateEstateList(List<Estate> estateList) {
                mEstateList = estateList;
                Log.d(TAG, "into configureGetEstateListCallback "+estateList.get(0).getAgentName());
                Bitmap btm = ImagesSQLiteConverter.toBitmapListFromJson(mEstateList.get(0).getPhotosString()).get(1);

                img.setImageBitmap(btm);
            }
        };
    }
}