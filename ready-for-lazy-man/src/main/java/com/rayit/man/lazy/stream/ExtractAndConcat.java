package com.rayit.man.lazy.stream;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rayit.man.lazy.stream.Common.characterStream;

public class ExtractAndConcat {

    public static void main(String[] args) {
        // limit
        long randomsCount = Stream.generate(Math::random).limit(5).count();
        System.out.println("randomsCount = " + randomsCount);

        // skip
        Stream<String> words2 = Stream.of(new String[]{"1", "2", "3"}).skip(1);
        System.out.println("words2 = " + words2.collect(Collectors.joining(",")));

        // concat
        Stream<Character> combined = Stream.concat(characterStream("Hello"), characterStream("World"));
        combined.forEach(System.out::print);

        // peek
        Double[] powers = Stream.iterate(1.0, p -> p * 2).peek(e -> System.out.println("Fetching " + e)).limit(20).toArray(Double[]::new);
        System.out.println("powers = " + Arrays.toString(powers));
    }
}
