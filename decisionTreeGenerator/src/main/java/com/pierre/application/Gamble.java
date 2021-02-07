package com.pierre.application;

public class Gamble {

    public static void main(String[] args) {

        System.out.println(solution(18, 2));
    }

    public static int solution(int N, int K) {

        int count = 0;

        while (N > 1) {

            count++;

            if (N % 2 == 0 && K > 0) {

                N = N / 2;
                K--;

            } else {

                N = N - 1;
            }
        }

        return count;
    }
}