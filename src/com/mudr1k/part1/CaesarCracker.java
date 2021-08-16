package com.mudr1k.part1;

/*
 * This class provides an implementation of the Caesar cipher cracking (or breaking) algorithm that you learned
 * about earlier.
 * As with the CaesarCipher class, a few adjustments have been made:
 * <p>
 * - The constructor takes a parameter for the most common letter, so it can be used for languages other than English.
 * - Finding the key has been separated from decrypting the message. You can use the method getKey to pass in an
 * encrypted message and receive the key back.
 * - You can test these methods in the tester class by making a new CaesarCracker object and decrypting the file
 * titus-small_key5.txt using no arguments for the constructor (default most common character ‘e’),
 * and the file oslusiadas_key17.txt, noting that the most common character in Portuguese is ‘a’.
 */

public class CaesarCracker {
    char mostCommon;

    public CaesarCracker() {
        mostCommon = 'e';
    }

    public CaesarCracker(char c) {
        mostCommon = c;
    }

    public int[] countLetters(String message) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[alphabet.length()];
        for (int i = 0; i < message.length(); i++) {
            int index = alphabet.indexOf(Character.toLowerCase(message.charAt(i)));
            if (index != -1) {
                counts[index] += 1;
            }
        }
        return counts;
    }

    public int maxIndex(int[] values) {
        int maxIndex = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > values[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public int getKey(String encrypted) {
        int[] freqs = countLetters(encrypted);
        int maxIndex = maxIndex(freqs);
        int mostCommonPos = mostCommon - 'a';
        int dKey = maxIndex - mostCommonPos;
        if (maxIndex < mostCommonPos) {
            dKey = 26 - (mostCommonPos - maxIndex);
        }
        return dKey;
    }

    public String decrypt(String encrypted) {
        int key = getKey(encrypted);
        CaesarCipher cc = new CaesarCipher(key);
        return cc.decrypt(encrypted);
    }
}
