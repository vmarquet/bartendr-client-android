package com.github.vmarquet.bartendr;


public class Article {
    private int id;
    private String name;
    private String description;
    private double price;
    private String picture;

    // constructors
    public Article(int id) {
        this.id = id;
    }
    public Article(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // getters
    public int getArticleId() {  // don't call it getId, it's already used
        return this.id;
    }

    public String getArticleName() {  // don't call it getName, it's already used
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public double getPrice() {
        return this.price;
    }

    public String getPicture() {
        return this.picture;
    }

    // setters
    public void setArticleName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
