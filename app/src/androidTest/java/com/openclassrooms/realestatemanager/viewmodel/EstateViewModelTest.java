package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.openclassrooms.realestatemanager.controler.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EstateViewModelTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void isEstateListFilteredViewModelCorrect(){
        EstateViewModel estateViewModel =
                new ViewModelProvider(mainActivityTestRule.getActivity()).get(EstateViewModel.class);
        assertTrue(estateViewModel.getEstate() instanceof MutableLiveData);
        assertTrue(estateViewModel.getPhotoList() instanceof MutableLiveData);
        assertTrue(estateViewModel.getSelected().getValue() instanceof Integer);
        assertTrue(estateViewModel.getIsDollard().getValue() instanceof Boolean);
    }
}
