package com.example.mainproject.Navigation.Home.Buy;

public class BuyItems {

    private String title;
    private String Sprice ,desc ,Oprice;

    String Image , key;



    public String getDessc() {
        return desc;
    }

    public void setDessc(String desc) {
        this.desc = desc;
    }

    public String getOPrice() {
        return Oprice;
    }

    public void setOPrice(String oprice) {
        Oprice = oprice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getSPrice() {
        return Sprice;
    }

    public void setSPrice(String Sprice) {
        this.Sprice = Sprice;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BuyItems(String title, String Sprice, String image ,String desc , String Oprice) {
        this.title = title;
        this.Sprice = Sprice;
        this.Image = image;
        this.Oprice= Oprice;
        this.desc=desc;
    }

}
