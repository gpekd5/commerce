package com.example.commerce;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private List<Product> products;

    public CommerceSystem(List<Product> products) {
        this.products = products;
    }

    public void Start(){
        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");

        for (int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            System.out.printf("%d. %-12s | %,10d원 | %s%n",
                    i+1,
                    product.getProductName(),
                    product.getPrice(),
                    product.getDescription());
        }

        System.out.printf("0. %-10s | %s%n", "종료", "프로그램 종료");

        Scanner sc = new Scanner(System.in);

        int selectNo = sc.nextInt();

        if (selectNo == 0)
        {
            System.out.println();
            System.out.println("커머스 플랫폼을 종료합니다.");
        }
    }
}
