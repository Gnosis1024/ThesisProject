package com.pierre.application;

public class StepCalculator {

    public static void main(String[] args) {

        int[] array = {1, 4, 45, 6, 0, 19};

        System.out.println(calculateSteps(array, 51));
    }

    private static int calculateSteps(int[] array1, int n) {

        int maxSum = 0;
        int sum;
        int result = n;

        for (int i = 0; i < array1.length; i++) {

            for (int j = i; j < array1.length; j++) {

                sum = 0;

                for (int k = i; k <= j; k++) {

                    sum += array1[k];
                }

                if (maxSum < sum) {

                    maxSum = sum;
                }

                if (maxSum > n && result < maxSum) {

                    System.out.println(result + " " + maxSum);
                    result = maxSum;
                }
            }
        }

        return result;
    }
}
