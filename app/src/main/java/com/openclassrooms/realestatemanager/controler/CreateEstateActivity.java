package com.openclassrooms.realestatemanager.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;

public class CreateEstateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_estate);
    }
}