package com.openclassrooms.realestatemanager.controler;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.provider.EstateContentProvider;
import com.openclassrooms.realestatemanager.utils.ImagesSQLiteConverter;
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "--MainActivity";
    private EstateViewModel estateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureCursorSize();
//        configureViewModel();
    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel =  ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel.class);

       estateViewModel.getEstates().observe(this, estates -> {

            Log.i(TAG,"observer "+estates.get(0).toString());
       });
        //try unsert
//        Estate est = new Estate("villa",12, 5,4,
//                "bien","ds q","Brooklyn","",false,
//                "","", ImagesSQLiteConverter.fromValuesToList(new ArrayList<Bitmap>()));
//        estateViewModel.createEstate(est);
//        Boolean bol = estateViewModel.getEstates() == null;
//        Log.i(TAG,bol.toString());
    }

    private void configureCursorSize(){
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    public EstateViewModel getEstateViewModel() {
        return estateViewModel;
    }
}
