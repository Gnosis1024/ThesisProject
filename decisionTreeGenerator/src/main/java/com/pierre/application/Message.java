package com.pierre.application;

public class Message {

    public static void main(String[] args) {

        String a = "The quick brown fox jumps over the lazy dog";

        System.out.println(solution(a, 39));

    }

    public static String solution(String message, int K) {

        String croppedMessage;

        if (message.length() > K) {

            if (message.toCharArray()[K] == ' ') {

                croppedMessage = message.substring(0, K);

            } else {

                int lastSpace = 0;

                for (int i = K; i >= 0; i--) {

                    if (message.toCharArray()[i] == ' ') {

                        lastSpace = i;
                        break;
                    }
                }

                croppedMessage = message.substring(0, lastSpace);
            }

        } else {

            croppedMessage = message;
        }

        return  croppedMessage;
    }
}
