package com.example.commerce;

import java.util.ArrayList;
import java.util.List;

/**
 * 장바구니 관리 클래스
 * 상품 추가, 조회, 주문 기능 담당
 */
public class Cart {

    private final List<CartItem> cartItems = new ArrayList<>();

    /**
     * 리스트에 항목이 있는지 없는지 확인
     * @return boolean 형식으로 null 인지 아닌지 반환
     */
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    /**
     * 리스트 초기화
     */
    public void cartClear() {
        cartItems.clear();
        System.out.println("주문을 취소합니다. 모든 장바구니의 목록이 삭제됩니다.");
    }

    /**
     * 장바구니 상품 추가
     * 이미 존재하는 상품이면 수량 증가
     * 없으면 새로운 항목 추가
     *
     * @param product 추가할 상품
     */
    public void addCartItem(Product product) {
        int stock = product.getStock();
        if (stock == 0) {
            System.out.println("재고가 부족합니다!!!!");
            return;
        }
        //리스트 전체를 반복하여 입력받은 항목과 일치하는 항목이 있으면 수량 up, 그렇지 않으면 신규 항목 리스트에 추가
        for (CartItem item : cartItems) {
            int quantity = item.getQuantity();
            if (stock <= quantity) {
                System.out.println("재고가 부족합니다!!!!");
                return;
            }
            if (item.getProduct().getProductName().equals(product.getProductName())) {
                item.addQuantity(1);

                System.out.println();
                System.out.println(product.getProductName() + "가 장바구니에 추가되었습니다.");
                return;
            }
        }

        cartItems.add(new CartItem(product, 1));
        System.out.println();
        System.out.println(product.getProductName() + "가 장바구니에 추가되었습니다.");
    }

    /**
     * 장바구니 상품 목록과 총 금액 출력
     */
    public void showCartList() {
        System.out.println();
        System.out.println("[ 장바구니 내역 ]");

        for (CartItem item : cartItems) {
            System.out.printf("%-12s | %,10d원 | %s | 수량: %d개%n",
                    item.getProduct().getProductName(),
                    item.getProduct().getPrice(),
                    item.getProduct().getDescription(),
                    item.getQuantity());
        }

        System.out.println();
        System.out.println("[ 총 주문 금액 ]");

        int totalPrice = addTotalPrice();
        System.out.printf("%,10d원",totalPrice);
    }

    /**
     * 장바구니에 담긴 전체 금액 계산
     *
     * @return 총 주문 금액
     */
    public int addTotalPrice() {
        int totalPrice = 0;

        for (CartItem item : cartItems) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    /**
     * 장바구니 상품 주문 처리
     * 재고 차감 후 장바구니초기화
     */
    public void orderCartList() {
        int totalPrice = addTotalPrice();
        System.out.printf("주문이 완료되었습니다! 총 금액: %,d원%n", totalPrice);

        for (CartItem item : cartItems) {
            int stock = item.getProduct().getStock();
            int cart = item.getQuantity();
            int updateStock = stock - cart;

            item.getProduct().setStock(updateStock);
            System.out.printf("%s 재고가 %d개 ->  %d개로 업데이트 되었습니다.%n",
                    item.getProduct().getProductName(),
                    stock, updateStock);
        }
        cartItems.clear();
    }
}
