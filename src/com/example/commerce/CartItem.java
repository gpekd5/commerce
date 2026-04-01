package com.example.commerce;

/**
 * 장바구니 개별 상품 정보 클래스
 *
 */
public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    /**
     * 상품 수량 증가
     *
     * @param quantity 증가시킬 수량
     */
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
