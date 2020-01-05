package com.rayit.man.lazy.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Common {

    public static final String filePathname = "E:\\DEV\\workspace\\java8learning\\ready-for-lazy-man\\src\\main\\resources\\YouHaveOnlyOneLife.txt";
    public static final String filePathname2 = "E:\\DEV\\workspace\\java8learning\\ready-for-lazy-man\\src\\main\\resources\\alice.txt";
    public static final String letterRegex = "[\\P{L}]+";

    public static String[] readContents() throws IOException {
        // Read the contents of the file into a string variable
        String contents = new String(Files.readAllBytes(Paths.get(filePathname2)), StandardCharsets.UTF_8);
        return contents.split(letterRegex);
    }

    public static List<String> getWordList() throws IOException {
        // Split string as letters, and non-alphabetic will be treated as separator
        List<String> words = Arrays.asList(readContents());
        return words;
    }

    public static Stream<String> getWordStream() throws IOException{
        return getWordList().stream();
    }

    public static Stream<Character> characterStream(String s) {
        List<Character> result = new ArrayList<>();
        for (char c : s.toCharArray()) {
            result.add(c);
        }
        return result.stream();
    }
}
