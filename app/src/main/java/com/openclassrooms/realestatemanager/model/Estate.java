package com.openclassrooms.realestatemanager.model;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Estate {

    private static final String TAG = "Estate";
    @PrimaryKey(autoGenerate = true)
    private long estateId;
    private String type;
    private Integer price;
    private Integer surface;
    private Integer roomNumber;
    private String description;
    private String address;
    private String city;
    private String interestingSpots;
    private Boolean sold;
    private String publicationDate;
    private String soldDate;
    private String agentName;

    public Estate( String type, Integer price, Integer surface, Integer roomNumber,
                   String description, String address, String city, String interestingSpots,
                   Boolean sold, String publicationDate, String soldDate, String agentName) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.roomNumber = roomNumber;
        this.description = description;
        this.address = address;
        this.city = city;
        this.interestingSpots = interestingSpots;
        this.sold = sold;
        this.publicationDate = publicationDate;
        this.soldDate = soldDate;
        this.agentName = agentName;

        Log.i(TAG,"into constructor");
    }

    public long getEstateId() {
        return estateId;
    }

    public void setEstateId(long estateId) {
        this.estateId = estateId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSurface() {
        return surface;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInterestingSpots() {
        return interestingSpots;
    }

    public void setInterestingSpots(String interestingSpots) {
        this.interestingSpots = interestingSpots;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    @Override
    public String toString() {
        return "Estate{" +
                "id=" + estateId +
                ", mType='" + type + '\'' +
                ", mPrice=" + price +
                ", mSurface=" + surface +
                ", mRoomNumber=" + roomNumber +
                ", mDescription='" + description + '\'' +
                ", mAddress='" + address + '\'' +
                ", mInterestingSpots='" + interestingSpots + '\'' +
                ", mSold=" + sold +
                ", mSoldDate='" + soldDate + '\'' +
                ", mAgentName='" + agentName + '\'' +
                '}';
    }
}
