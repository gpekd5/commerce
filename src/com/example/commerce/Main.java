package com.example.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();

        products.add(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 50));
        products.add(new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 40));
        products.add(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 30));
        products.add(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 20));

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