package com.rayit.man.lazy;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamLearning {

    public static void main(String[] args) throws Exception {
        List<String> words = getWordList();

//        long count = words.stream().filter(w -> w.length() > 12).count();
        long count = words.parallelStream().filter(w -> w.length() > 8).count();
        System.out.println("count = " + count);

        long count2 = generateSteamByOf().filter(w -> w.length() > 7).count();
        System.out.println("count2 = " + count2);

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

        // map: Exchange words to lowercase
        Stream<String> lowercaseWords = words.stream().map(String::toLowerCase);
        System.out.println("lowercaseWords = " + lowercaseWords.collect(Collectors.joining(",")));

        // map / flatMap
        Stream<Stream<Character>> result = words.stream().map(w -> characterStream(w));
        result.forEach(stream -> stream.forEach(System.out::write));
        Stream<Character> letters = words.stream().flatMap(w -> characterStream(w));
        letters.forEach(System.out::write);

        // limit
        long randomsCount = Stream.generate(Math::random).limit(5).count();
        System.out.println("randomsCount = " + randomsCount);

        // skip
        Stream<String> words2 = Stream.of(new String[]{"1", "2", "3"}).skip(1);
        System.out.println("words2 = " + words2.collect(Collectors.joining(",")));

        // concat
        Stream<Character> combined = Stream.concat(characterStream("Hello"), characterStream("World"));
        combined.forEach(System.out::print);
//        combined.peek(c->System.out.println("Char " +c.charValue())).;

        // peek
        Object[] powers = Stream.iterate(1.0, p -> p * 2).peek(e -> System.out.println("Fetching " + e)).limit(20).toArray();
    }

    public static final String filePathname = "E:\\DEV\\workspace\\java8learning\\ready-for-lazy-man\\src\\main\\resources\\YouHaveOnlyOneLife.txt";
    public static final String letterRegex = "[\\P{L}]+";

    public static List<String> getWordList() throws IOException {
        // Split string as letters, and non-alphabetic will be treated as separator
        List<String> words = Arrays.asList(readContents());
        return words;
    }

    public static Stream<String> generateSteamByOf() throws IOException {
        // Generate stream by static method 'Stream.of()'
        Stream<String> words = Stream.of(readContents());
        return words;
    }

    public static String[] readContents() throws IOException {
        // Read the contents of the file into a string variable
        String contents = new String(Files.readAllBytes(Paths.get(filePathname)), StandardCharsets.UTF_8);
        return contents.split(letterRegex);
    }

    public static Stream<String> generateStreamByPattern() throws IOException {
        Stream<String> words = Pattern.compile(letterRegex).splitAsStream(new String(Files.readAllBytes(Paths.get(filePathname)), StandardCharsets.UTF_8));
        return words;
    }

    public static Stream<Character> characterStream(String s) {
        List<Character> result = new ArrayList<>();
        for (char c : s.toCharArray()) {
            result.add(c);
        }
        return result.stream();
    }
}
