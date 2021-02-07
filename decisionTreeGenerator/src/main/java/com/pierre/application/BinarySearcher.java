package com.pierre.application;

import java.util.Arrays;

public class BinarySearcher {

    public static void main(String[] args) {

        final int[] array = {1, 3, 8, 14, 18, 27, 30, 35, 44, 58};

        int[] array1 = {1, 3, 27, 30, 35, 44, 8, 14, 18, 58};

        int[] array2 = {1, 3, 6, 4, 1, 2};

        //System.out.println("result " + binarySearch(array, -100, 0, array.length));

        //System.out.println(Arrays.toString(bubbleSearch(array1)));

        array2 = bubbleSearch(array2);

        System.out.println(Arrays.toString(array2));

        System.out.println(test(array2));
    }

    private static int[] bubbleSearch(int[] array) {

        int swap;
        boolean finish;

        do {

            finish = true;

            for (int i = 0; i < array.length - 1; i++) {

                if (array[i] > array[i + 1]) {

                    swap = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = swap;
                    finish = false;
                }
            }

        } while (!finish);

        return array;
    }

    private static int test(int[] array) {

        int result = 0;

        for (int i = 0; i < array.length - 1; i++) {

            if (array[i] + 1 != array[i + 1] && array[i] != array[i + 1] && array[i] + 1 >= 0) {

                result = array[i] + 1;
                break;
            }
        }

        return result;
    }

    private static int binarySearch(int[] array, int n, int lowerBound, int upperBound) {

        if (upperBound >= lowerBound) {

            int mid = lowerBound + (upperBound - lowerBound) / 2;

            if (array[mid] > n) {

                return binarySearch(array, n, lowerBound, mid - 1);

            } else if (array[mid] < n) {

                return binarySearch(array, n, mid + 1, upperBound);

            } else if (array[mid] == n) {

                return mid;
            }
        }

        return -1;
    }
}
