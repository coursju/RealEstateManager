package com.openclassrooms.realestatemanager.helper;

import com.openclassrooms.realestatemanager.model.Estate;

import java.util.ArrayList;
import java.util.List;

public class EstateList {
     private static List<Estate> estateList = new ArrayList<>();

    synchronized public static List<Estate> getEstateList() {
        return estateList;
    }

    synchronized public static void setEstateList(List<Estate> estateList) {
        EstateList.estateList = estateList;
    }
}
