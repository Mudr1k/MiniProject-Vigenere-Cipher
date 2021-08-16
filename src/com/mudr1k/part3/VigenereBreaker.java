package com.mudr1k.part3;

import com.mudr1k.part1.CaesarCracker;
import com.mudr1k.part1.VigenereCipher;
import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/*
 * Finally, it is time to expand your program so that it can crack messages in other languages. To accomplish this, you
 * need to write two new methods and modify two methods you have already written.
 * <p>
 * Specifically, you should do the following:
 * <p>
 * In the VigenereBreaker class, write the public method mostCommonCharIn, which has one parameter—a HashSet of
 * Strings dictionary. This method should find out which character, of the letters in the English alphabet, appears
 * most often in the words in dictionary. It should return this most commonly occurring character. Remember that
 * you can iterate over a HashSet of Strings with a for-each style for loop.
 * In the VigenereBreaker class, write the public method breakForAllLangs, which has two parameters—a String
 * encrypted, and a HashMap, called languages, mapping a String representing the name of a language to a HashSet of
 * Strings containing the words in that language. Try breaking the encryption for each language, and see which gives
 * the best results! Remember that you can iterate over the languages.keySet() to get the name of each language, and
 * then you can use .get() to look up the corresponding dictionary for that language. You will want to use the
 * breakForLanguage and countWords methods that you already wrote to do most of the work (it is slightly inefficient
 * to re-count the words here, but it is simpler, and the inefficiency is not significant). You will want to print
 * out the decrypted message as well as the language that you identified for the message.
 * Modify the method breakForLanguage to make use of your mostCommonCharIn method to find the most common character
 * in the language, and pass that to tryKeyLength instead of ‘e’.
 * Modify your breakVigenere method to read many dictionaries instead of just one. In particular, you should make a
 * HashMap mapping Strings to a HashSet of Strings that will map each language name to the set of words in its
 * dictionary. Then, you will want to read (using your readDictionary method) each of the dictionaries that we have
 * provided (Danish, Dutch, English, French, German, Italian, Portuguese, and Spanish) and store the words in the
 * HashMap you made. Reading all the dictionaries may take a little while, so you might add some print statements to
 * reassure you that your program is making progress. Once you have made that change, you will want to call
 * breakForAllLangs, passing in the message (the code to read in the message is unchanged from before), and the
 * HashMap you just created.
 * <p>
 * Test your program on the file athens_keyflute.txt. Notice that the correct key “flute” {5, 11, 20, 19, 4} is detected
 * for English, French, Danish, German, and Dutch, due to ‘e’ being the most common character and the languages having
 * cognates, but English has the greatest number of valid words for its decryption.
 */
public class VigenereBreaker {

    private String decrypted;

    public String getDecrypted() {
        return decrypted;
    }

    public void setDecrypted(String decrypted) {
        this.decrypted = decrypted;
    }

    public void breakVigenere() {
        FileResource fr = new FileResource();
//        FileResource fr = new FileResource("data/VigenereTestData/athens_keyflute.txt");
        String encrypted = fr.asString();

        HashMap<String, HashSet<String>> languages = new HashMap<>();

        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource dictFileRes = new FileResource(f);
            languages.put(f.getName(), readDictionary(dictFileRes));
            System.out.println(f.getName() + " : complete");
        }

        breakForAllLangs(encrypted, languages);
        System.out.println(getDecrypted());
    }

    public HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> dictionary = new HashSet<>();
        for (String line : fr.lines()) {
            dictionary.add(line.toLowerCase());
        }
        return dictionary;
    }

    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
        String language = "";
        int maxCorrectWords = 0;

        for (Map.Entry<String, HashSet<String>> entry : languages.entrySet()) {
            int currCorrectWords = breakForLanguage(encrypted, entry.getValue());
            System.out.println("Lang: " + entry.getKey() + "\t\t corrWords: " + currCorrectWords);
            if (currCorrectWords > maxCorrectWords) {
                maxCorrectWords = currCorrectWords;
                language = entry.getKey();
            }
        }

        System.out.println();
        System.out.println("Message language " + language);
        System.out.println();
    }

    private int breakForLanguage(String encrypted, HashSet<String> dictionary) {
        char mostCommon = mostCommonCharIn(dictionary);
        int[] key;
        int maxCorrectWords = 0;

        for (int i = 1; i <= 100; i++) {
            key = tryKeyLength(encrypted, i, mostCommon);
            VigenereCipher vigenereCipher = new VigenereCipher(key);
            String currDecrypted = vigenereCipher.decrypt(encrypted);
            int currCorrectWords = countWords(currDecrypted, dictionary);
            if (currCorrectWords > maxCorrectWords) {
                maxCorrectWords = currCorrectWords;
                setDecrypted(currDecrypted);
            }
        }
        return maxCorrectWords;
    }

    public char mostCommonCharIn(HashSet<String> dictionary) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        CaesarCracker caesarCracker = new CaesarCracker();
        int[] resultCountLetters = new int[alphabet.length()];
        int[] currCountLetters;

        for (String s : dictionary) {
            currCountLetters = caesarCracker.countLetters(s);
            for (int i = 0; i < alphabet.length(); i++) {
                resultCountLetters[i] += currCountLetters[i];
            }
        }
        return alphabet.charAt(caesarCracker.maxIndex(resultCountLetters));
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

    public int countWords(String message, HashSet<String> dictionary) {
        int count = 0;
        for (String word : message.toLowerCase().split("\\W+")) {
            if (dictionary.contains(word)) {
                ++count;
            }
        }
        return count;
    }

    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slice = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            slice.append(message.charAt(i));
        }
        return slice.toString();
    }
}
