package com.openclassrooms.realestatemanager.model;

import android.graphics.Bitmap;
import android.nfc.Tag;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.openclassrooms.realestatemanager.utils.ImagesSQLiteConverter;

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
    private String mAddress;
    private String mCity;
    private String mInterestingSpots;
    private Boolean mSold;
    private String mSoldDate;
    private String mAgentName;
    private String mPhotosString;
    //private String mPhotoDescription  !!! List
    @Ignore
    private List<Bitmap> mPhotosList;

    public Estate( String type, Integer price, Integer surface, Integer roomNumber,
                   String description, String address, String city, String interestingSpots,
                   Boolean sold, String soldDate, String agentName, String photosString) {
        mType = type;
        mPrice = price;
        mSurface = surface;
        mRoomNumber = roomNumber;
        mDescription = description;
        mAddress = address;
        mCity = city;
        mInterestingSpots = interestingSpots;
        mSold = sold;
        mSoldDate = soldDate;
        mAgentName = agentName;
        mPhotosString = photosString;

//        mPhotosList = ImagesSQLiteConverter.toOptionValuesList(mPhotosString);
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

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getCity() { return mCity; }

    public void setCity(String city) { mCity = city; }

    public String getInterestingSpots() {
        return mInterestingSpots;
    }

    public void setInterestingSpots(String interestingSpots) {mInterestingSpots = interestingSpots; }

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

    public String getPhotosString() { return mPhotosString;}

    public void setPhotosString(String photosString) { mPhotosString = photosString; }

    public List<Bitmap> getPhotosList() { return mPhotosList; }

    public void setPhotosList(List<Bitmap> photos) { mPhotosList = photos; }

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
