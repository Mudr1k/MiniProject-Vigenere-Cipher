package com.mudr1k.part1;

import edu.duke.FileResource;

class VigenereBreakerTest {

    public static void main(String[] args) {
        sliceString();
        tryKeyLength();
        breakVigenere();
    }

     static void sliceString() {
        VigenereBreaker vigenereBreaker = new VigenereBreaker();
        System.out.println(vigenereBreaker.sliceString("abcdefghijklm", 0, 3));
        System.out.println(vigenereBreaker.sliceString("abcdefghijklm", 1, 3));
        System.out.println(vigenereBreaker.sliceString("abcdefghijklm", 2, 3));
        System.out.println(vigenereBreaker.sliceString("abcdefghijklm", 0, 4));
        System.out.println(vigenereBreaker.sliceString("abcdefghijklm", 1, 4));
        System.out.println(vigenereBreaker.sliceString("abcdefghijklm", 2, 4));
        System.out.println(vigenereBreaker.sliceString("abcdefghijklm", 3, 4));
        System.out.println(vigenereBreaker.sliceString("abcdefghijklm", 0, 5));
        System.out.println(vigenereBreaker.sliceString("abcdefghijklm", 1, 5));
        System.out.println(vigenereBreaker.sliceString("abcdefghijklm", 2, 5));
        System.out.println(vigenereBreaker.sliceString("abcdefghijklm", 3, 5));
        System.out.println(vigenereBreaker.sliceString("abcdefghijklm", 4, 5));
    }

    static void tryKeyLength() {
        String keyWord = "flute";
        char mostCommonChar = 'e';
        FileResource fr = new FileResource("data/VigenereTestData/athens_keyflute.txt");

        VigenereBreaker vigenereBreaker = new VigenereBreaker();
        int[] keys = vigenereBreaker.tryKeyLength(fr.asString(), keyWord.length(), mostCommonChar);

        for (int key : keys) {
            System.out.print(key + " ");
        }
        System.out.println("\n");
    }

    static void breakVigenere() {
        VigenereBreaker vigenereBreaker = new VigenereBreaker();
        vigenereBreaker.breakVigenere();
    }
}