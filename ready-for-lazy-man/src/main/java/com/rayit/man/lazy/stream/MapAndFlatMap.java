package com.rayit.man.lazy.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rayit.man.lazy.stream.Common.characterStream;
import static com.rayit.man.lazy.stream.Common.getWordList;

public class MapAndFlatMap {

    public static void main(String[] args) throws Exception {
        List<String> words = getWordList();

        // map: Transfer words to lowercase
        Stream<String> lowercaseWords = words.stream().map(String::toLowerCase);
        System.out.println("lowercaseWords = " + lowercaseWords.collect(Collectors.joining(",")));

        // map / flatMap
        Stream<Stream<Character>> result = words.stream().map(w -> characterStream(w));
        result.forEach(stream -> stream.forEach(System.out::write));
        Stream<Character> letters = words.stream().flatMap(w -> characterStream(w));
        letters.forEach(System.out::write);
    }
}
