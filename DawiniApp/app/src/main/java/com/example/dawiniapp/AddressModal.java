package com.example.dawiniapp;

import java.io.Serializable;

public class AddressModal implements Serializable {

    private String addressId,userId,city,building,street,floor;



    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "AddressModal{" +
                "addressId='" + addressId + '\'' +
                ", userId='" + userId + '\'' +
                ", city='" + city + '\'' +
                ", building='" + building + '\'' +
                ", street='" + street + '\'' +
                ", floor='" + floor + '\'' +
                '}';
    }
}
