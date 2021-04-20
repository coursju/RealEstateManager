package com.openclassrooms.realestatemanager.controler;

import android.database.CursorWindow;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.openclassrooms.realestatemanager.R;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    private EstateListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureCursorSize();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
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
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_port, mDetailsFragment, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        }
    }
}
