package com.techweezy.travelmantics.model;

import java.io.Serializable;

public class TravelDeals implements Serializable {
    private String id;
    private String place_name;
    private String description;
    private String price;
    private String imageUrl;
    private String imageName;

    public TravelDeals(){}
    public TravelDeals(String id,String place_name,
                       String description, String price,
                       String imageUrl, String imageName) {
        this.setId(id);
        this.setPlace_name(place_name);
        this.setDescription(description);
        this.setPrice(price);
        this.setImageUrl(imageUrl);
        this.setImageName(imageName);

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
