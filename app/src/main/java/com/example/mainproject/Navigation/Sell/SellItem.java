package com.example.mainproject.Navigation.Sell;

public class SellItem {
    private String title;
    private String price;
    String Image;
    String   Description , Original_pricee , Selling_price,key;



    public SellItem() {

    }

    public SellItem(String title, String price, String imageUrl, String des, String oprice) {
        this.title = title;
        this.price = price;
        this.Image= imageUrl;
        this.Description =des;
        this.Original_pricee=oprice;
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

    public String getSelling_price() {
        return Selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.Selling_price = selling_price;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

