package com.mudr1k.part1;

import java.util.Arrays;

/**
 * This class implements a Vigenère cipher.
 * It has the following functionality:
 * <p>
 * - public VigenereCipher(int[] key): the constructor, which takes a key, which is an array of integers and
 * initializes the field ciphers, which is an array of CaesarCipher objects.
 * - public String encrypt(String input): a method that encrypts the String passed in and returns the encrypted message.
 * - public String decrypt(String input): a method that decrypts the String passed in and returns the decrypted message.
 * - public String toString(): returns a String representing the key for this cipher.
 * - You can test these methods in the tester class by creating a VigenereCipher object with the key “rome”,
 * which is {17, 14, 12, 4} in integers and encrypting and decrypting the file titus-small.txt, the encrypted
 * first line of which is “Tcmp-pxety mj nikhqv htee mrfhtii tyv”.
 */

public class VigenereCipher {
    CaesarCipher[] ciphers;

    public VigenereCipher(int[] key) {
        ciphers = new CaesarCipher[key.length];
        for (int i = 0; i < key.length; i++) {
            ciphers[i] = new CaesarCipher(key[i]);
        }
    }

    public String encrypt(String input) {
        StringBuilder answer = new StringBuilder();
        int i = 0;
        for (char c : input.toCharArray()) {
            int cipherIndex = i % ciphers.length;
            CaesarCipher thisCipher = ciphers[cipherIndex];
            answer.append(thisCipher.encryptLetter(c));
            ++i;
        }
        return answer.toString();
    }

    public String decrypt(String input) {
        StringBuilder answer = new StringBuilder();
        int i = 0;
        for (char c : input.toCharArray()) {
            int cipherIndex = i % ciphers.length;
            CaesarCipher thisCipher = ciphers[cipherIndex];
            answer.append(thisCipher.decryptLetter(c));
            ++i;
        }
        return answer.toString();
    }

    public String toString() {
        return Arrays.toString(ciphers);
    }
}

