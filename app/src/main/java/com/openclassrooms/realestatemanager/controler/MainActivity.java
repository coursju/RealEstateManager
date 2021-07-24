package com.openclassrooms.realestatemanager.controler;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorWindow;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.dialog.SearchDialog;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.EstateListFilteredViewModel;
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel;

import java.io.IOException;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 12;
    private FragmentManager fragmentManager;
    private EstateListFragment mListFragment;
    private EstateViewModel estateViewModel;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureCursorSize();
        checkPermissions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        estateViewModel = new ViewModelProvider(this).get(EstateViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                mListFragment.clearFilter();
                new SearchDialog(mListFragment).show(getSupportFragmentManager(), "SearchDialog");
                return true;
            case R.id.menu_map:
                Intent intent = new Intent(this, MapsEstateActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_loan:
                Intent intent2 = new Intent(this, LoanSimulatorActivity.class);
                startActivity(intent2);
                return true;
            case R.id.menu_convert:
                estateViewModel.setIsDollard();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        configureFragment();
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

    public void configureFragment(){
        mListFragment = new EstateListFragment(this);

        if (findViewById(R.id.fragment_land_details) != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_land_list, mListFragment, null)
                    .replace(R.id.fragment_land_details, new DetailsFragment(estateViewModel.getSelected().getValue()), null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        }else{
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_port, mListFragment, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        }
    }

    public void showFragmentDetails(int position){
        DetailsFragment mDetailsFragment = new DetailsFragment(position);

        if (findViewById(R.id.fragment_land_details) != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_land_details, mDetailsFragment, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        }else{
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermissions(){
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_WIFI_STATE) ==
                PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.INTERNET) ==
                PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED)
        {
            Log.i(TAG, "Permissions check OK");

        }else {
            requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.INTERNET,
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permissions check OK");
                }  else {
                    Log.i(TAG, "Permissions denied");
                }
                return;
        }
    }
}
