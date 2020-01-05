package com.rayit.man.lazy.stream;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rayit.man.lazy.stream.Common.*;

public class GenerateStream {

    public static void main(String[] args) throws Exception {
        // Stream.of()
        long count = generateSteamByOf().filter(w -> w.length() > 7).count();
        System.out.println("count = " + count);

        // Stream.empty(): New an empty stream
        Stream<String> emptyStream = Stream.empty();
        System.out.println("emptyStream = " + emptyStream.iterator().hasNext());

        // Stream.generate(): Creat a constant stream
        Stream<String> echos = Stream.generate(() -> "Echo");
        System.out.println("echos = " + echos.iterator().next());
        // Creat a limited random number stream
        Stream<Double> randoms = Stream.generate(Math::random).limit(5);
        System.out.println("randoms = ");
        randoms.forEach(System.out::println);

        // Pattern.compile()
        generateStreamByPattern().forEach(System.out::print);

        // iterate: Generate a sequence like 0 1 2 3...
        String integers = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE)).limit(100).map(BigInteger::toString).collect(Collectors.joining(","));
        System.out.println("integers = " + integers);
    }

    public static Stream<String> generateSteamByOf() throws IOException {
        // Generate stream by static method 'Stream.of()'
        Stream<String> words = Stream.of(readContents());
        return words;
    }

    public static Stream<String> generateStreamByPattern() throws IOException {
        Stream<String> words = Pattern.compile(letterRegex).splitAsStream(new String(Files.readAllBytes(Paths.get(filePathname2)), StandardCharsets.UTF_8));
        return words;
    }
}
