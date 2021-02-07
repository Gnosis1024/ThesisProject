package com.pierre.application;

import java.util.HashMap;
import java.util.Map;

public class Task {

    public static void main(String[] args) {

        int a = 0;
        int b = 0;
        System.out.println(solution(a, b));
    }

    public static int solution(int A, int B) {

        if (A <= B) {

            int count = 0;

            for (int i = A; i <= B; i++) {

                if (isLovely(i)) {

                    count++;
                }
            }

            return count;

        } else {

            return 0;
        }
    }

    public static boolean isLovely(int A) {

        char[] a = Integer.toString(A).toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        boolean lovely = true;

        for (char e : a) {

            if (map.containsKey(e)) {

                map.put(e, map.get(e) + 1);

            } else {

                map.put(e, 1);
            }
        }

        for (int e : map.values()) {

            if (e >= 3) {

                lovely = false;
                break;
            }
        }

        return lovely;
    }
}
