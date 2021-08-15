package com.mudr1k.part1;

import edu.duke.FileResource;

/**
 * Your first step in this mini-project is to write the three methods in the VigenereBreaker class.
 * Specifically you should do the following:
 * <p>
 * 1. Write the public method sliceString, which has three parameters—a String message, representing the encrypted
 * message, an integer whichSlice, indicating the index the slice should start from, and an integer totalSlices,
 * indicating the length of the key. This method returns a String consisting of every totalSlices-th character from
 * message, starting at the whichSlice-th character.
 * <p>
 * You can test your method on these examples:
 * sliceString("abcdefghijklm", 0, 3) should return "adgjm"
 * sliceString("abcdefghijklm", 1, 3) should return "behk"
 * sliceString("abcdefghijklm", 2, 3) should return "cfil"
 * sliceString("abcdefghijklm", 0, 4) should return "aeim"
 * sliceString("abcdefghijklm", 1, 4) should return "bfj"
 * sliceString("abcdefghijklm", 2, 4) should return "cgk"
 * sliceString("abcdefghijklm", 3, 4) should return "dhl"
 * sliceString("abcdefghijklm", 0, 5) should return "afk"
 * sliceString("abcdefghijklm", 1, 5) should return "bgl"
 * sliceString("abcdefghijklm", 2, 5) should return "chm"
 * sliceString("abcdefghijklm", 3, 5) should return "di"
 * sliceString("abcdefghijklm", 4, 5) should return "ej"
 * <p>
 * 2. Write the public method tryKeyLength, which takes three parameters—a String encrypted that represents the
 * encrypted message, an integer klength that represents the key length, and a character mostCommon that indicates
 * the most common character in the language of the message. This method should make use of the CaesarCracker class,
 * as well as the sliceString method, to find the shift for each index in the key. You should fill in the key
 * (which is an array of integers) and return it.
 * <p>
 * Test this method on the text file athens_keyflute.txt, which is a scene from A Midsummer Night’s Dream encrypted
 * with the key “flute”, and make sure you get the key {5, 11, 20, 19, 4}.
 * <p>
 * 3. Write the public method breakVigenere with no parameters. This void method should put everything together, so that
 * you can create a new VigenereBreaker in BlueJ, call this method on it, and crack the cipher used on a message.
 * This method should perform 6 tasks (in this order):
 * 1. Create a new FileResource using its default constructor (which displays a dialog for you to select a file
 * to decrypt).
 * 2. Use the asString method to read the entire contents of the file into a String.
 * 3. Use the tryKeyLength method, which you just wrote, to find the key for the message you read in. For now,
 * you should just pass ‘e’ for mostCommon.
 * 4. You should create a new VigenereCipher, passing in the key that tryKeyLength found for you.
 * 5. You should use the VigenereCipher’s decrypt method to decrypt the encrypted message.
 * 6. Finally, you should print out the decrypted message!
 * <p>
 * Test this method on the text file athens_keyflute.txt, using key length 5. The first line should be
 * “SCENE II. Athens. QUINCE'S house.”
 */

public class VigenereBreaker {

    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slice = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            slice.append(message.charAt(i));
        }
        return slice.toString();
    }

    public int[] tryKeyLength(String encrypted, int kLength, char mostCommon) {
        int[] key = new int[kLength];
        CaesarCracker caesarCracker = new CaesarCracker(mostCommon);
        String slice;
        for (int i = 0; i < kLength; i++) {
            slice = sliceString(encrypted, i, kLength);
            key[i] = caesarCracker.getKey(slice);
        }
        return key;
    }

    public void breakVigenere() {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        int kLength = 4;
        char mostCommon = 'e';
        int[] key = tryKeyLength(encrypted, kLength, mostCommon);

        VigenereCipher vigenereCipher = new VigenereCipher(key);
        String decrypt = vigenereCipher.decrypt(encrypted);
        System.out.println(decrypt);
    }
}
