package com.openclassrooms.realestatemanager.utils;

import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.BaseEstateActivity;

public class ChipgroupUtils {

    private static final String TAG = "ChipgroupUtils";

    public static void addNewChip(BaseEstateActivity activity, ChipGroup chipGroup, String keyword){
        if (keyword == null || keyword.isEmpty()) {
            Log.i(TAG, "addNewChip: empty text field");
            return;
        }

        try {
            LayoutInflater inflater = LayoutInflater.from(activity.getApplicationContext());

            Chip newChip = (Chip) inflater.inflate(R.layout.layout_chip_entry, chipGroup, false);
            newChip.setText(keyword);
            newChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorAccent)));
            chipGroup.addView(newChip);

            newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Chip chip = (Chip) v;
                    ChipGroup parent = (ChipGroup) chip.getParent();
                    parent.removeView(chip);
                }
            });

            activity.detailsPointsOfInterestsText.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
        }
    }

    public static String saveSelections(ChipGroup chipGroup)  {
        int count = chipGroup.getChildCount();

        String s = "";
        for(int i=0;i< count; i++) {
            Chip child = (Chip) chipGroup.getChildAt(i);

            if(s == "")  {
                s = child.getText().toString();
            } else {
                s += ", " + child.getText().toString();
            }
        }
        Log.i(TAG, "saveSelections: "+s);
        return s;
    }
}
