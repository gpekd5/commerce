package com.example.commerce;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * 관리자 기능 클래스
 * 상품 추가, 수정, 삭제 및 전체 상품 조회 기능
 *
 */
public class Admin {

    private final String PASSWORD = "password1234";
    private final Scanner sc = new Scanner(System.in);
    private final List<Category> categories;
    private final Cart cart;

    public Admin(List<Category> categories, Cart cart) {
        this.categories = categories;
        this.cart = cart;
    }

    /**
     * 관리자 로그인 기능
     * 비밀번호를 최대 3회까지 입력받고 성공 시 관리자 메뉴로 이동
     *
     * @param categories 카테고리 목록
     */
    public void adminLogin(List<Category> categories) {
        int repeatPW = 0;

        while (repeatPW < 3) {
            System.out.println("관리자 비밀번호를 입력해주세요:");

            String inputpassword = sc.nextLine();

            if (PASSWORD.equals(inputpassword)) {
                adminShowMenu(categories);
                return;
            } else {
                repeatPW++;
                System.out.println("비밀번호가 틀렸습니다.(" + repeatPW + "/3회)");
            }
        }

        System.out.println("3회 실패로 메인 메뉴로 돌아갑니다.");
    }

    /**
     * 관리자 메뉴 출력 및 기능 선택
     * 상품 추가, 수정, 삭제, 전체 조회 기능
     *
     * @param categories 카테고리 목록
     */
    public void adminShowMenu(List<Category> categories) {

        while (true) {
            System.out.println();
            System.out.println("[ 관리자 모드 ]");
            System.out.println("1. 상품 추가");
            System.out.println("2. 상품 수정");
            System.out.println("3. 상품 삭제");
            System.out.println("4. 전체 상품 현황");
            System.out.println("0. 메인으로 돌아가기");

            try {
                int selectedAdminMenu = sc.nextInt();
                sc.nextLine();

                if (selectedAdminMenu == 0) {
                    break;
                } else if (selectedAdminMenu == 1) {
                    adminAddProduct(categories);
                } else if (selectedAdminMenu == 2) {
                    adminEditProduct();
                } else if (selectedAdminMenu == 3) {
                    adminDeleteProduct();
                } else if (selectedAdminMenu == 4) {
                    showAllProducts();
                } else
                    System.out.println("올바른 번호를 입력하세요.");

            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력하세요.");
                sc.nextLine();
            }
        }
    }

    /**
     * 선택한 카테고리에 상품을 추가하는 기능
     * 상품명 중복 여부를 검사하고, 상품을 생성한다.
     * 상품에는 가격, 상품 설명, 재고수량 입력
     *
     * @param categories 카테고리 목록
     */
    public void adminAddProduct(List<Category> categories) {
        while (true) {
            System.out.println();
            System.out.println("어느 카테고리에 상품을 추가하시겠습니까?");

            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("%d. %-12s%n",
                        i + 1,
                        categories.get(i).getCategoryName());
            }

            try {
                int categoryNo = sc.nextInt();
                sc.nextLine();

                if (categoryNo < 1 || categoryNo > categories.size()) {
                    System.out.println("올바른 번호를 입력하세요.");
                    continue;
                }

                Category category = categories.get(categoryNo - 1);
                System.out.printf("%n[ %s 카테고리에 상품 추가 ]\n", category.getCategoryName());

                System.out.print("상품명을 입력해주세요: ");
                String productName = sc.nextLine();

                for (Product product : category.getProducts()) {
                    if (product.getProductName().equals(productName)) {
                        System.out.println("같은 카테고리 내에 중복 상품명이 존재합니다.");
                        break;
                    }
                }

                System.out.print("가격을 입력해주세요: ");
                int price = sc.nextInt();
                sc.nextLine();

                System.out.print("상품 설명을 입력해주세요: ");
                String productDescription = sc.nextLine();

                System.out.print("재고수량을 입력해주세요: ");
                int stock = sc.nextInt();
                sc.nextLine();

                Product newProduct = new Product(productName, price, productDescription, stock);

                System.out.println();
                System.out.printf("%s | %,d원 | %s | 재고: %d개%n",
                        newProduct.getProductName(),
                        newProduct.getPrice(),
                        newProduct.getDescription(),
                        newProduct.getStock());

                System.out.println("위 정보로 상품을 추가하시겠습니까?");
                System.out.println("1. 확인    2. 취소");

                int addConfirm = sc.nextInt();
                if (addConfirm == 1) {
                    category.getProducts().add(newProduct);
                    System.out.println("\n상품이 성공적으로 추가되었습니다!");
                    break;
                } else if (addConfirm == 2) {
                    System.out.println("상품 추가가 취소되었습니다.");
                } else {
                    System.out.println("올바른 번호를 입력하세요.");
                }

            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력하세요.");
                sc.nextLine();
            }

        }
    }

    /**
     * 상품명 기준으로 상품 검색
     * 가격, 설명, 재고 수량 수정
     */
    public void adminEditProduct() {
        System.out.print("수정할 상품명을 입력해주세요: ");
        String inputName = sc.nextLine();

        Product foundProduct = null;

        for (Category category : categories) {
            for (Product product : category.getProducts()) {
                if (product.getProductName().equals(inputName)) {
                    foundProduct = product;
                    break;
                }
            }
            if (foundProduct != null) {
                break;
            }
        }

        if (foundProduct == null) {
            System.out.println("해당 상품을 찾을 수 없습니다.");
            return;
        }

        System.out.printf("현재 상품 정보: %s | %,d원 | %s | 재고: %d개%n",
                foundProduct.getProductName(),
                foundProduct.getPrice(),
                foundProduct.getDescription(),
                foundProduct.getStock());

        System.out.println();
        System.out.println("수정할 항목을 선택해주세요:");
        System.out.println("1. 가격");
        System.out.println("2. 설명");
        System.out.println("3. 재고수량");

        try {
            int selectedNo = sc.nextInt();
            sc.nextLine();

            if (selectedNo == 1) {
                int oldPrice = foundProduct.getPrice();
                System.out.printf("현재 가격: %,d원%n", oldPrice);
                System.out.print("새로운 가격을 입력해주세요: ");
                int newPrice = sc.nextInt();
                sc.nextLine();

                foundProduct.setPrice(newPrice);

                System.out.printf("%s의 가격이 %,d원 → %,d원으로 수정되었습니다.%n",
                        foundProduct.getProductName(),
                        oldPrice,
                        newPrice);

            } else if (selectedNo == 2) {
                String oldDescription = foundProduct.getDescription();
                System.out.println("현재 설명: " + oldDescription);
                System.out.print("새로운 설명을 입력해주세요: ");
                String newDescription = sc.nextLine();

                foundProduct.setDescription(newDescription);

                System.out.printf("%s의 설명이 \"%s\" → \"%s\"로 수정되었습니다.%n",
                        foundProduct.getProductName(),
                        oldDescription,
                        newDescription);

            } else if (selectedNo == 3) {
                int oldStock = foundProduct.getStock();
                System.out.println("현재 재고수량: " + oldStock);
                System.out.print("새로운 재고수량을 입력해주세요: ");
                int newStock = sc.nextInt();
                sc.nextLine();

                foundProduct.setStock(newStock);

                System.out.printf("%s의 재고가 %d개 → %d개로 수정되었습니다.%n",
                        foundProduct.getProductName(),
                        oldStock,
                        newStock);

            } else {
                System.out.println("올바른 번호를 입력하세요.");
            }

        } catch (InputMismatchException e) {
            System.out.println("숫자를 입력하세요.");
            sc.nextLine();
        }
    }

    /**
     * 선택한 카테고리에서 상품 삭제 기능
     * 삭제 시 장바구니에서도 해당 상품을 제거
     */
    public void adminDeleteProduct() {
        while (true) {
            System.out.println();
            System.out.println("어느 카테고리의 상품을 삭제하시겠습니까?");

            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("%d. %-12s%n",
                        i + 1,
                        categories.get(i).getCategoryName());
            }

            System.out.println("0. 뒤로가기");

            try {
                int categoryNo = sc.nextInt();
                sc.nextLine();

                if (categoryNo == 0) return;

                if (categoryNo < 1 || categoryNo > categories.size()) {
                    System.out.println("올바른 번호를 입력하세요.");
                    continue;
                }

                Category category = categories.get(categoryNo - 1);
                System.out.printf("%n[ %s 카테고리의 상품 삭제 ]\n", category.getCategoryName());

                List<Product> products = category.getProducts();

                //상품 다 지우고 없을 경우
                if (products.isEmpty()) {
                    System.out.println("해당 카테고리에 상품이 없습니다.");
                    return;
                }

                for (int i = 0; i < products.size(); i++) {
                    Product p = products.get(i);
                    System.out.printf("%d. %-12s | %,10d원 | %s | 재고: %d개%n",
                            i + 1,
                            p.getProductName(),
                            p.getPrice(),
                            p.getDescription(),
                            p.getStock());
                }
                System.out.println("0. 뒤로가기");

                int productNo = sc.nextInt();
                sc.nextLine();

                if (productNo == 0) return;

                if (productNo < 1 || productNo > products.size()) {
                    System.out.println("올바른 번호를 입력하세요.");
                    return;
                }

                Product selectedProduct = products.get(productNo - 1);

                System.out.println();
                System.out.printf("선택한 상품: %s | %,d원 | %s | 재고: %d개%n",
                        selectedProduct.getProductName(),
                        selectedProduct.getPrice(),
                        selectedProduct.getDescription(),
                        selectedProduct.getStock());

                System.out.println();
                System.out.println("위 상품을 삭제하시겠습니까?");
                System.out.println("1. 확인    2. 취소");

                int confirm = sc.nextInt();
                sc.nextLine();

                if (confirm == 1) {
                    products.remove(selectedProduct);
                    cart.deleteProduct(selectedProduct);
                    System.out.println("상품이 성공적으로 삭제되었습니다!");
                } else if (confirm == 2) {
                    System.out.println("삭제가 취소되었습니다.");
                } else {
                    System.out.println("올바른 번호를 입력하세요.");
                }

                return;

            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력하세요.");
                sc.nextLine();
            }
        }
    }

    /**
     * 모든 카테고리의 상품을 디스플레이하는 기능
     */
    public void showAllProducts() {
        System.out.println();
        System.out.println("[ 전체 상품 현황 ]");

        for (Category category : categories) {
            System.out.println();
            System.out.printf("[ %s ]%n", category.getCategoryName());

            List<Product> products = category.getProducts();

            if (products.isEmpty()) {
                System.out.println("등록된 상품이 없습니다.");
                continue;
            }

            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);

                System.out.printf("%d. %-12s | %,10d원 | %s | 재고: %d개%n",
                        i + 1,
                        product.getProductName(),
                        product.getPrice(),
                        product.getDescription(),
                        product.getStock());
            }
        }
    }
}
