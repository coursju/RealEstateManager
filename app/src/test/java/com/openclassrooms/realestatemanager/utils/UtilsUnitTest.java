package com.openclassrooms.realestatemanager.utils;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UtilsUnitTest {


    @Test
    public void convertDollarToEuroTest() {
        assertEquals(812, Utils.convertDollarToEuro(1000), 0);
    }

    @Test
    public void convertEuroToDollarTest() {
        assertEquals(123, Utils.convertEuroToDollar(100), 0);
    }

    @Test
    public void getTodayDateTest() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(new Date());
        assertEquals(date, Utils.getTodayDate());
    }
}