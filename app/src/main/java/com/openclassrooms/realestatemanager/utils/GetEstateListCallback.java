package com.openclassrooms.realestatemanager.utils;

import com.openclassrooms.realestatemanager.model.Estate;

import java.util.List;

public interface GetEstateListCallback {
    void updateEstateList(List<Estate> estateList);
}
