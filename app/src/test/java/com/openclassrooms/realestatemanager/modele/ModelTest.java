package com.openclassrooms.realestatemanager.modele;

import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Photo;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ModelTest {

    @Test
    public void isEstateCorrect(){
        Estate estate = new Estate( "loft", 100, 1, 1,
                "description", "pas loin", "city", "cool",
                true, "19/12/19", "19/12/19", "brian");
        assertTrue(estate.getType() == "loft");
        assertTrue(estate.getPrice() == 100);
        assertTrue(estate.getSurface() == 1);
        assertTrue(estate.getRoomNumber() == 1);
        assertTrue(estate.getDescription() == "description");
        assertTrue(estate.getAddress() == "pas loin");
        assertTrue(estate.getCity() == "city");
        assertTrue(estate.getInterestingSpots() == "cool");
        assertTrue(estate.getSold() == true);
        assertTrue(estate.getPublicationDate() == "19/12/19");
        assertTrue(estate.getSoldDate() == "19/12/19");
        assertTrue(estate.getAgentName() == "brian");
    }

    @Test
    public void isPhotoCorrect(){
        Photo photo = new Photo(1, new byte[]{} ,"photoDescription");
        assertTrue(photo.getEstateCreatorId() == 1);
        assertTrue(photo.getPhotoDescription() == "photoDescription");
    }
}
