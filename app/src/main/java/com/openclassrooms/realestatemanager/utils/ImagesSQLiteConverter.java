package com.openclassrooms.realestatemanager.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImagesSQLiteConverter {

    private static final String TAG = "ImagesSQLiteConverter";

    @TypeConverter
    public static String fromBitmapListToJson(List<Bitmap> value) {
        Log.d(TAG,"fromBitmapListToJson");
        if (value== null) {
            return (null);
        }
        Gson gson = new Gson();
        List<String> strList = new ArrayList<>();
        for (Bitmap bit: value){
            strList.add(bitMapToString(bit));
        }

//        Type type = new TypeToken<ArrayList<Bitmap>>() {}.getType();
        return gson.toJson(strList);//, type);
    }

    public static String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        if(temp==null)
        {
            return null;
        }
        else
            return temp;
    }

    @TypeConverter
    public static List<Bitmap> toBitmapListFromJson(String value) {
        Log.d(TAG,"toBitmapListFromJson");
        if (value== null) {
            return (null);
        }
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> stringList = new Gson().fromJson(value, type);
        List<Bitmap> bitList = new ArrayList<>();
        for (String str: stringList){
            bitList.add(stringToBitMap(str));
        }
        return bitList;//gson.fromJson(value, type);
    }

    public static Bitmap stringToBitMap(String encodedString){
        try {
            byte[] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            if(bitmap==null)
            {
                return null;
            }
            else
            {
                return bitmap;
            }

        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
