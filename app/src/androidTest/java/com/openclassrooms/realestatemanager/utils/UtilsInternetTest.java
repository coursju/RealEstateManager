package com.openclassrooms.realestatemanager.utils;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UtilsInternetTest {

    @Test
    public void isConnectedTest() throws IOException, InterruptedException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        if (Utils.isInternetAvailable(appContext)){
            assertTrue(Utils.isConnected());
        }
    }
}
