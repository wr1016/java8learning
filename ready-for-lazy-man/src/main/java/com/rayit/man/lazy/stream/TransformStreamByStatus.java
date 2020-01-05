package com.rayit.man.lazy.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import static com.rayit.man.lazy.stream.Common.getWordStream;

public class TransformStreamByStatus {

    public static void main(String[] args) throws Exception {
        // distinct: Here fetch only one "merrily"
        Object[] uniqueWords = Stream.of("merrily", "merrily", "merrily", "merrily").distinct().toArray();
        System.out.println("Arrays.toString(uniqueWords) = " + Arrays.toString(uniqueWords));

        // sorted
        Stream<String> longestFirst = getWordStream().sorted(Comparator.comparing(String::length).reversed());
        System.out.println("longestFirst = " + Arrays.toString(longestFirst.toArray()));
    }
}
