package com.oppwa.mobile.connect.dataweb.model;

public class Products {
    String title;
    String description;
    int img;
    Double price;
    Boolean isIva;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImg() {
        return img;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getIva() {
        return isIva;
    }

    public Products(String title, String description, int img, Double price, Boolean isIva) {
        this.title = title;
        this.description = description;
        this.img = img;
        this.price = price;
        this.isIva = isIva;
    }

    public Products(String title, String description, int img, Double price) {
        this.title = title;
        this.description = description;
        this.img = img;
        this.price = price;
        this.isIva = false;
    }
}
