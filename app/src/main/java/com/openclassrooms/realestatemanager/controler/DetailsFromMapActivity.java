package com.openclassrooms.realestatemanager.controler;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.openclassrooms.realestatemanager.R;

public class DetailsFromMapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int position = getIntent().getIntExtra("position", 0);
        setContentView(R.layout.activity_details);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_layout, new DetailsFragment(position), null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }
}

