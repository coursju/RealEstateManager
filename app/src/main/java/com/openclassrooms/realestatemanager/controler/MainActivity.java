package com.openclassrooms.realestatemanager.controler;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "--MainActivity";
    private EstateViewModel estateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureViewModel();

    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel =  ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel.class);

       estateViewModel.getEstates().observe(this, estates -> {

            Log.i(TAG,"observer "+estates.get(0).toString());
       });
        //try unsert
        Estate est = new Estate("villa",12, 5,4,"bien","ds q","",false,"","");
        estateViewModel.createEstate(est);
        Boolean bol = estateViewModel.getEstates() == null;
//        RealEstateDatabase db = RealEstateDatabase.getInstance(this);
//        EstateDao estateDao = db.mEstateDao();
//        estateDao.insertEstate(est);
//        List<Estate> estList= estateDao.getEstates().getValue();
//        Boolean bol = estList == null;
        Log.i(TAG,bol.toString());
    }

    public EstateViewModel getEstateViewModel() {
        return estateViewModel;
    }
}
