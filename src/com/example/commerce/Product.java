package com.example.commerce;

/**
 * 상품 정보를 나타내는 클래스
 *
 */
public class Product {

    //속성
    private final String productName;
    private final int price;
    private final String description;
    private int stock;

    //속성
    public Product(String productName, int price, String description, int stock) {
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

    public void setStock(int stock) {
        this.stock = stock;
    }
}
