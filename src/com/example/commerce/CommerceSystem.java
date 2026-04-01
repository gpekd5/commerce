package com.example.commerce;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * 커머스 플랫폼 전체 구동 클래스
 * 메뉴 출력, 사용자 입력 처리, 장바구니 기능
 */
public class CommerceSystem {

    private final List<Category> categories = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);
    int selectProductNo;
    int selectCategoryNo;
    int selectedAddNo;

    private final Cart cart = new Cart();

    public CommerceSystem() {
        initData();
    }

    private void initData() {
        //전자제품, 의류, 식품
        List<Product> electronics = new ArrayList<>();
        electronics.add(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 2));
        electronics.add(new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 40));
        electronics.add(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 30));
        electronics.add(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 20));

        List<Product> cloths = new ArrayList<>();
        cloths.add(new Product("후드티", 190000, "편안한 오버핏 후드티", 200));
        cloths.add(new Product("청바지", 129000, "슬림핏 데님 팬츠", 250));
        cloths.add(new Product("운동화", 180000, "러닝용 경량 운동화", 300));

        List<Product> foods = new ArrayList<>();
        foods.add(new Product("바나나", 3000, "맛있는 바나나", 300));
        foods.add(new Product("계란", 13000, "신선한 왕란 30구", 50));
        foods.add(new Product("햇반", 1500, "즉석 조리 가능한 쌀밥", 2000));

        categories.add(new Category("전자제품", electronics));
        categories.add(new Category("의류", cloths));
        categories.add(new Category("식품", foods));
    }

    /**
     *  메인 메뉴를 반복 실행
     */
    public void start() {

        while (true) {

            if (!cart.isEmpty()) {
                System.out.println();
                System.out.println("[ 아래 메뉴를 선택해주세요 ] ");
            }

            System.out.println();
            System.out.println("[ 실시간 커머스 플랫폼 메인 ] ");

            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("%d. %-12s%n",
                        i + 1,
                        categories.get(i).getCategoryName());
            }

            System.out.printf("0. %-10s | %s%n", "종료", "프로그램 종료");

            if (!cart.isEmpty()) {
                System.out.println();
                System.out.println("[ 주문 관리 ] ");
                System.out.printf("4. %-10s | %s%n", "장바구니 확인", "장비구니를 확인 후 주문합니다.");
                System.out.printf("5. %-10s | %s%n", "주문 취소", "진행중인 주문을 취소합니다.");
            }

            try {
                selectCategoryNo = sc.nextInt();

                if (selectCategoryNo == 0) {
                    System.out.println();
                    System.out.println("커머스 플랫폼을 종료합니다.");
                    break;
                } else if (selectCategoryNo < 1 || selectCategoryNo > categories.size()) {

                    if (!cart.isEmpty()) {
                        if (selectCategoryNo == 4) {
                            orderShoppingCart();
                            continue;
                        } else if (selectCategoryNo == 5) {
                            cart.cartClear();
                            continue;
                        }
                    }

                    System.out.println("올바른 번호를 입력하세요.");
                    continue;
                }
                showCategoryMenu(categories.get(selectCategoryNo - 1));

            } catch (InputMismatchException e) {
                System.out.println("올바른 숫자를 입력하세요.");
                sc.nextLine();
            }
        }
    }

    /**
     * 장바구니 리스트에 등록한 상품을 주문할 건지 확인하는 기능
     *
     */
    public void orderShoppingCart() {

        while (true) {
            System.out.println();
            System.out.println("아래와 같이 주문 하시겠습니까?");
            cart.showCartList();

            System.out.println();
            System.out.println("1. 주문 확정     2. 메인으로 돌아가기");

            try {
                selectCategoryNo = sc.nextInt();

                if (selectCategoryNo == 1) {
                    cart.orderCartList();
                    break;
                } else if (selectCategoryNo == 2) {
                    break;
                } else
                    System.out.println("올바른 번호를 입력하세요.");
            } catch (InputMismatchException e) {
                System.out.println("올바른 숫자를 입력하세요.");
                sc.nextLine();
            }
        }
    }

    /**
     * 선택한 카테고리의 상품 목록을 출력하고
     * 선택한 상품 확인
     *
     * @param category 선택된 카테고리
     */
    public void showCategoryMenu(Category category) {
        while (true) {
            System.out.println();
            System.out.printf("[ %s 카테고리 ] %n", category.getCategoryName());

            List<Product> products = category.getProducts();

            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                System.out.printf("%d. %-12s  | %,10d원 | %s%n",
                        i + 1,
                        product.getProductName(),
                        product.getPrice(),
                        product.getDescription());
            }

            System.out.printf("0. %s%n", "뒤로가기");

            try {
                selectProductNo = sc.nextInt();

                if (selectProductNo == 0) {
                    System.out.println();
                    System.out.println("커머스 플랫폼을 종료합니다.");
                    break;
                }

                if (selectProductNo < 1 || selectProductNo > categories.size()) {
                    System.out.println("올바른 번호를 입력하세요.");
                    continue;
                }

                Product selectProduct = products.get(selectProductNo - 1);

                System.out.printf("선택한 상품: %s | %,d원 | %s | 재고: %d개%n",
                        selectProduct.getProductName(),
                        selectProduct.getPrice(),
                        selectProduct.getDescription(),
                        selectProduct.getStock());

                addShoppingCart(selectProduct);

                break;
            } catch (InputMismatchException e) {
                System.out.println("올바른 숫자를 입력하세요.");
                sc.nextLine();
            }
        }
    }

    /**
     * 선택한 상품을 장바구니에 추가할지 사용자 확인하는 기능
     *
     * @param product 선택된 상품
     */
    public void addShoppingCart(Product product) {
        while (true) {
            System.out.println();
            System.out.printf("\"%s | %,d원 | %s\"%n",
                    product.getProductName(),
                    product.getPrice(),
                    product.getDescription());

            System.out.println("위 삼품을 장바구니에 추가하시겠습니까?");
            System.out.println("1.확인      2.취소");

            try {
                sc.nextLine();
                selectedAddNo = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력하세요.");
                continue;
            }

            if (selectedAddNo == 1) {
                cart.addCartItem(product);
                break;
            } else if (selectedAddNo == 2) {
                break;
            } else
                System.out.println("1 또는 2를 입력해주세요");
        }
    }
}
