package com.example.mainproject.Navigation.Home.Rentedcoll;

public class RentedClass {
     String title;
     String Sprice ,desc ,Oprice;

    String image , key;

    public RentedClass(String title, String sprice, String desc, String oprice, String image) {
        this.title = title;
        this.Sprice = sprice;
        this.desc = desc;
        this.Oprice = oprice;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSprice() {
        return Sprice;
    }

    public void setSprice(String sprice) {
        Sprice = sprice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOprice() {
        return Oprice;
    }

    public void setOprice(String oprice) {
        Oprice = oprice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
