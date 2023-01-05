package com.notandroid.wishingapp.Model;

public class WishModel {
    String id;
    String wishData;


    public WishModel(String wishData,String id) {
        this.id = id;
        this.wishData = wishData;
    }

    WishModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWishData() {
        return wishData;
    }

    public void setWishData(String wishData) {
        this.wishData = wishData;
    }
}
