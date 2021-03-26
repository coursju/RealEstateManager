package com.openclassrooms.realestatemanager.controler;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "--MainActivity";
    private EstateViewModel estateViewModel;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureCursorSize();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        configureFragment();
        configureViewModel();
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

    public void configureFragment(){

        if (findViewById(R.id.fragment_land_details) != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_land_list, new EstateListFragment(), null)
                    .replace(R.id.fragment_land_details, new DetailsFragment(), null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name") // name can be null
                    .commit();
        }else{
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_port, new EstateListFragment(), null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name") // name can be null
                    .commit();
        }

    }
}
