package com.mudr1k.part2;

import com.mudr1k.part1.CaesarCracker;
import com.mudr1k.part1.VigenereCipher;
import edu.duke.FileResource;

import java.util.HashSet;

/*
 * Now that you have broken Vigenère ciphers with a known key length, it is time to expand your program’s functionality
 * to break Vigenère ciphers of unknown key length. You will do this by adding three methods to your VigenereBreaker
 * class, and then modifying your breakVigenere method.
 * <p>
 * Specifically, you should do the following:
 * <p>
 * In the VigenereBreaker class, write the public method readDictionary, which has one parameter—a FileResource fr.
 * This method should first make a new HashSet of Strings, then read each line in fr (which should contain exactly one
 * word per line), convert that line to lowercase, and put that line into the HashSet that you created. The method
 * should then return the HashSet representing the words in a dictionary. All the dictionary files, including the
 * English dictionary file, are included in the starter program you download. They are inside the folder called ‘dictionaries’.
 * <p>
 * In the VigenereBreaker class, write the public method countWords, which has two parameters—a String message, and a
 * HashSet of Strings dictionary. This method should split the message into words (use .split(“\\W+”), which returns a
 * String array), iterate over those words, and see how many of them are “real words”—that is, how many appear in the
 * dictionary. Recall that the words in dictionary are lowercase. This method should return the integer count of how
 * many valid words it found.
 * <p>
 * In the VigenereBreaker class, write the public method breakForLanguage, which has two parameters—a String encrypted,
 * and a HashSet of Strings dictionary. This method should try all key lengths from 1 to 100 (use your tryKeyLength
 * method to try one particular key length) to obtain the best decryption for each key length in that range. For each
 * key length, your method should decrypt the message (using VigenereCipher’s decrypt method as before), and count how
 * many of the “words” in it are real words in English, based on the dictionary passed in (use the countWords method you
 * just wrote). This method should figure out which decryption gives the largest count of real words, and return that
 * String decryption. Note that there is nothing special about 100; we will just give you messages with key lengths in
 * the range 1–100. If you did not have this information, you could iterate all the way to encrypted.length().
 * Your program would just take a bit longer to run.
 * <p>
 * Finally, you need to modify your breakVigenere method to make use of your new code. The steps below describe what
 * your breakVigenere method should do. They are similar to the steps from the previous portion of this project but
 * have been updated: new items are in italics, [and old items that you should no longer do are in square brackets.]
 * <p>
 * 1. Create a new FileResource using its default constructor (which displays a dialog for you to select a file to decrypt).
 * 2. Use that FileResource’s asString method to read the entire contents of the file into a String.
 * 3. You should make a new FileResource to read from the English dictionary file that we have provided.
 * [Use the tryKeyLength method that you just wrote to find the key for the message you read in. For now, you should
 * just pass ‘e’ for mostCommon.]
 * 4. You should use your readDictionary method to read the contents of that file into a HashSet of Strings. [You should
 * create a new VigenereCipher, passing in the key that tryKeyLength found for you.]
 * 5. You should use the breakForLanguage method that you just wrote to decrypt the encrypted message. [You should use
 * a VigenereCipher object to decrypt the encrypted message.]
 * 6. Finally, you should print out the decrypted message!
 * <p>
 * Test this method on the text file athens_keyflute.txt. The first line should be “SCENE II. Athens. QUINCE'S house”,
 * and the key is “flute”, or {5, 11, 20, 19, 4}. This file contains 376 valid words out of 386 (total word count
 * ignores whitespace).
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

        FileResource englishDict = new FileResource("data/dictionaries/English");
        HashSet<String> dictionary = readDictionary(englishDict);
        String decrypt = breakForLanguage(encrypted, dictionary);

        System.out.println(decrypt);
    }

    public HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> dictionary = new HashSet<>();
        for (String line : fr.lines()) {
            dictionary.add(line.toLowerCase());
        }
        return dictionary;
    }

    public int countWords(String message, HashSet<String> dictionary) {
        int count = 0;
        for (String word : message.toLowerCase().split("\\W+")) {
            if (dictionary.contains(word)) {
                ++count;
            }
        }
        return count;
    }

    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
        String decrypted = "";
        char mostCommon = 'e';
        int[] key;
        int maxCorrectWords = 0;
        for (int i = 1; i <= 100; i++) {
            key = tryKeyLength(encrypted, i, mostCommon);
            VigenereCipher vigenereCipher = new VigenereCipher(key);
            String currDecrypted = vigenereCipher.decrypt(encrypted);
            int currCorrectWords = countWords(currDecrypted, dictionary);
            if (currCorrectWords > maxCorrectWords) {
                maxCorrectWords = currCorrectWords;
                decrypted = currDecrypted;
            }
        }
        return decrypted;
    }
}
