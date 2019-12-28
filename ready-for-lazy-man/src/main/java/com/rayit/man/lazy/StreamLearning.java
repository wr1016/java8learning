package com.rayit.man.lazy;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamLearning {

    public static void main(String[] args) throws Exception {
        List<String> words = getWordList();

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

        // map: Transfer words to lowercase
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

        // peek
        Object[] powers = Stream.iterate(1.0, p -> p * 2).peek(e -> System.out.println("Fetching " + e)).limit(20).toArray();

        // distinct: Here fetch only one "merrily"
        Object[] uniqueWords = Stream.of("merrily", "merrily", "merrily", "merrily").distinct().toArray();
        System.out.println("Arrays.toString(uniqueWords) = " + Arrays.toString(uniqueWords));

        // sorted
        Stream<String> longestFirst = words.stream().sorted(Comparator.comparing(String::length).reversed());
        System.out.println("longestFirst = " + Arrays.toString(longestFirst.toArray()));

        // count, stream()
        long count1 = words.stream().filter(w -> w.length() > 12).count();
        System.out.println("count1 = " + count1);
        // count, parallelStream()
        long count2 = words.parallelStream().filter(w -> w.length() > 8).count();
        System.out.println("count2 = " + count2);

        Optional<String> largest = words.stream().max(String::compareToIgnoreCase);
        if (largest.isPresent()) {
            System.out.println("largest = " + largest.get());
        }

        // findFirst: Find the first one element which is matched with it
        Optional<String> startsWithM = words.stream().filter(s -> s.startsWith("M")).findFirst();
        if (startsWithM.isPresent()) System.out.println("startsWithM = " + startsWithM.get());

        // findAny: Find the matched element
        Optional<String> startWithT = words.stream().parallel().filter(s -> s.startsWith("T")).findAny();
        if (startWithT.isPresent()) System.out.println("startWithT = " + startWithT.get());

        // anyMatch: If there is a matched element in the collection
        boolean aWordStartsWithP = words.stream().parallel().anyMatch(s -> s.startsWith("P"));
        System.out.println("aWordStartsWithP = " + aWordStartsWithP);

        // allMatch
        boolean allWordStartsWithQ = Stream.of("Qw", "Qere", "Qfff", "Qfgss").parallel().allMatch(s -> s.startsWith("Q"));
        System.out.println("allWordStartsWithQ = " + allWordStartsWithQ);

        // noneMatch
        boolean noneWordStartsWithQ = Stream.of("Qw", "Qere", "Qfff", "Qfgss").parallel().noneMatch(s -> s.startsWith("Q"));
        System.out.println("noneWordStartsWithQ = " + noneWordStartsWithQ);

        // Optional ifPresent(): optionalValue.ifPresent(v -> Process v)
        List<String> results = new ArrayList<>();
        Optional<String> optionalValue = Stream.of("Qw", "Qere", "Qfff", "Qfgss").parallel().filter(s -> s.startsWith("Q")).findAny();
        //optionalValue.ifPresent(v->results.add(v));
        optionalValue.ifPresent(results::add);
        System.out.println("results = " + Arrays.toString(results.toArray()));
        // list.removeIf(filter)
        results.removeIf(r -> r.contains("f"));
        System.out.println("results = " + Arrays.toString(results.toArray()));

        // Optional map(method)
        Optional<Boolean> added = optionalValue.map(results::add);
        System.out.println("added = " + added);

        Optional<String> optionalValue2 = Stream.of("Qw", "Qere", "Qfff", "Qfgss").parallel().filter(s -> s.startsWith("G")).findAny();

        // Optional orElse(element)
        String result1 = optionalValue2.orElse("");
        System.out.println("result1 = " + result1);

        // Optional orElseGet(method): If result2 is null, then it will call the method System.getProperty("user.dir") to get value to return
        String result2 = optionalValue2.orElseGet(() -> System.getProperty("user.dir"));
        System.out.println("result2 = " + result2);

        // Optional orElseThrow(Exception)
//        String result3 = optionalValue2.orElseThrow(NoSuchElementException::new);

        // Optional.of(obj), Optional.empty(): create Optional
        System.out.println("inverse(0) = " + inverse(Double.valueOf(0)));
        System.out.println("inverse(5) = " + inverse(Double.valueOf(5)));
        
        // Optional.ofNullable(obj)
        System.out.println("Optional.ofNullable(\"hello\") = " + Optional.ofNullable("hello"));
        System.out.println("Optional.ofNullable(null) = " + Optional.ofNullable(null));

        // Optional flatMap: Optional<T> <- f(), T <- Optional<U> <- g()
        Optional<Double> result3 = inverse(Double.valueOf(4)).flatMap(StreamLearning::squareRoot);
        System.out.println("result3 = " + result3.orElse(Double.valueOf(0)));
        Optional<Double> result4 = Optional.of(Double.valueOf(4)).flatMap(StreamLearning::inverse).flatMap(StreamLearning::squareRoot);
        System.out.println("result4 = " + result4.orElse(Double.valueOf(0)));
    }

    public static Optional<Double> squareRoot(Double x){
        return x<0?Optional.empty():Optional.of(Math.sqrt(x));
    }

    public static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
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
