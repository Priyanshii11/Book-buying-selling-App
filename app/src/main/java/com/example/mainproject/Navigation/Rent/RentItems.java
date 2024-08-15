package com.example.mainproject.Navigation.Rent;

public class RentItems {

    private String title;
    private String price;

    String Image;
    String Description , Original_pricee ;
    String key ;

    public RentItems() {

    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getOriginal_pricee() {
        return Original_pricee;
    }

    public void setOriginal_pricee(String original_pricee) {
        Original_pricee = original_pricee;
    }

    public RentItems(String title, String price, String image, String description, String original_pricee) {
        this.title = title;
        this.price = price;
        this.Image = image;
        this.Description = description;
        this.Original_pricee = original_pricee;
    }


}

