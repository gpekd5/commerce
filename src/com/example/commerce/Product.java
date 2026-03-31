package com.example.commerce;

public class Product {

    //속성
    private String productName;
    private int price;
    private String description;
    private int stock;

    //속성
    public Product(String productName, int price, String description, int stock){
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    //기능
    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setStock(int stock){
        this.stock = stock;
    }



}
