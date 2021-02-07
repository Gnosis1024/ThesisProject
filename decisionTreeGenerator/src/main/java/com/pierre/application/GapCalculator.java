package com.pierre.application;

public class GapCalculator {

    public static void main(String[] args) {

        System.out.println(gapCalculator(529));
    }

    public static int gapCalculator(int n) {

        int currGap = 0;
        int maxGap = 0;
        boolean inGap = false;
        char[] bn = Integer.toBinaryString(n).toCharArray();

        for (int i = 0; i < bn.length; i++) {

            if (!inGap && bn[i] == '1') {

                inGap = true;

            } else if (inGap && bn[i] == '1') {

                inGap = false;

                if (maxGap < currGap) {

                    maxGap = currGap;
                }
            }

            if (bn[i] == '0' && inGap) {

                currGap++;
            }
        }

        return maxGap;
    }
}
