package com.openclassrooms.realestatemanager.model;

import android.graphics.Bitmap;
import android.nfc.Tag;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Estate {

    private static final String TAG = "--Estate";
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String mType;
    private Integer mPrice;
    private Integer mSurface;
    private Integer mRoomNumber;
    private String mDescription;
//    private List<Bitmap> mPhotos;
    private String mAddress;
    private String mInterestingSpots;
    private Boolean mSold;
    private String mSoldDate;
    private String mAgentName;

    public Estate( String type, Integer price, Integer surface, Integer roomNumber, String description, /*List<Bitmap> photos,*/ String address, String interestingSpots, Boolean sold, String soldDate, String agentName) {
        mType = type;
        mPrice = price;
        mSurface = surface;
        mRoomNumber = roomNumber;
        mDescription = description;
//        mPhotos = photos;
        mAddress = address;
        mInterestingSpots = interestingSpots;
        mSold = sold;
        mSoldDate = soldDate;
        mAgentName = agentName;

        Log.i(TAG,"into constructor");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public Integer getPrice() {
        return mPrice;
    }

    public void setPrice(Integer price) {
        mPrice = price;
    }

    public Integer getSurface() {
        return mSurface;
    }

    public void setSurface(Integer surface) {
        mSurface = surface;
    }

    public Integer getRoomNumber() {
        return mRoomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        mRoomNumber = roomNumber;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

//    public List<Bitmap> getPhotos() {
//        return mPhotos;
//    }
//
//    public void setPhotos(List<Bitmap> photos) {
//        mPhotos = photos;
//    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getInterestingSpots() {
        return mInterestingSpots;
    }

    public void setInterestingSpots(String interestingSpots) {
        mInterestingSpots = interestingSpots;
    }

    public Boolean getSold() {
        return mSold;
    }

    public void setSold(Boolean sold) {
        mSold = sold;
    }

    public String getSoldDate() {
        return mSoldDate;
    }

    public void setSoldDate(String soldDate) {
        mSoldDate = soldDate;
    }

    public String getAgentName() {
        return mAgentName;
    }

    public void setAgentName(String agentName) {
        mAgentName = agentName;
    }

    @Override
    public String toString() {
        return "Estate{" +
                "id=" + id +
                ", mType='" + mType + '\'' +
                ", mPrice=" + mPrice +
                ", mSurface=" + mSurface +
                ", mRoomNumber=" + mRoomNumber +
                ", mDescription='" + mDescription + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mInterestingSpots='" + mInterestingSpots + '\'' +
                ", mSold=" + mSold +
                ", mSoldDate='" + mSoldDate + '\'' +
                ", mAgentName='" + mAgentName + '\'' +
                '}';
    }
}
