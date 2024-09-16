package ru.netology;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Generator {
    public static AtomicInteger counter3 = new AtomicInteger();
    public static AtomicInteger counter4 = new AtomicInteger();
    public static AtomicInteger counter5 = new AtomicInteger();

    public static void generateShortWord() throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrome = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text) && !isSameLetter(text)) {
                    // System.out.println("Palindrome " + text);
                    counter(text.length());
                }
            }
        });
        palindrome.start();

        Thread sameLetter = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    // System.out.println("SameLetter " + text);
                    counter(text.length());
                }
            }
        });
        sameLetter.start();

        Thread ascendingOrder = new Thread(() -> {
            for (String text : texts) {
                if (!isPalindrome(text) && isAscendingOrderLetter(text)) {
                    //System.out.println("AscendingOrder " + text);
                    counter(text.length());
                }
            }
        });
        ascendingOrder.start();


        sameLetter.join();
        ascendingOrder.join();
        palindrome.join();


        System.out.println("Beautiful words with length 3: " + counter3);
        System.out.println("Beautiful words with length 4: " + counter4);
        System.out.println("Beautiful words with length 5: " + counter5);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        StringBuilder reversed = new StringBuilder();
        for (int i = text.length() - 1; i >= 0; i--) {
            reversed.append(text.charAt(i));
        }
        return reversed.toString().equals(text);
    }

    public static boolean isSameLetter(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAscendingOrderLetter(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void counter(int textLength) {
        if (textLength == 3) {
            counter3.getAndIncrement();
        } else if (textLength == 4) {
            counter4.getAndIncrement();
        } else if (textLength == 5) {
            counter5.getAndIncrement();
        }
    }

}
