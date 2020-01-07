package com.rayit.man.lazy.stream;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.rayit.man.lazy.stream.GroupAndSlice.readCities;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingByConcurrent;

public class ParallelStream {

    public static void main(String[] args) throws IOException {
        Stream<String> parallelWords = Stream.of(Common.readContents()).parallel();
        Stream<String> parallelWords2 = Arrays.stream(Common.readContents()).parallel();
        Stream<String> parallelWords3 = Common.getWordList().parallelStream();

        // Count the total number of all short words in a string stream
        int[] shortWordsLengthArray = new int[12];
        Arrays.fill(shortWordsLengthArray, 0);
        Common.getWordStream().parallel().forEach(
                s -> {
                    if (s.length() < 12) shortWordsLengthArray[s.length()]++;
                }
        );
        System.out.println("shortWordsLengthArray = " + Arrays.toString(shortWordsLengthArray));
        // Codes above are very bad, the function passed to forEach() will run concurrently in multiple threads to update a shared array, and it is likely to get a different total each time.
        // One way, can use a counter to fix this situation: AtomicInteger
        AtomicInteger[] shortWordCounters = new AtomicInteger[12];
        for (int i = 0; i < shortWordCounters.length; i++) {
            shortWordCounters[i] = new AtomicInteger();
        }
        Common.getWordStream().parallel().forEach(
                s -> {
                    if (s.length() < 12) shortWordCounters[s.length()].getAndIncrement();
                });
        System.out.println("shortWordCounters = " + Arrays.toString(shortWordCounters));
        // Another way, can slice words in groups by their length
        Map<Integer, Long> shortWordsLengthMap = Common.getWordStream().parallel().filter(s -> s.length() < 12).collect(groupingByConcurrent(String::length, counting()));
        System.out.println("shortWordsLengthMap = " + shortWordsLengthMap);

        // Run the first time
//        shortWordCounters = [1, 1500, 3121, 4064, 3632, 2525, 1742, 1517, 745, 632, 339, 146]
//        shortWordsLengthMap = {0=1, 1=1826, 2=4999, 3=7637, 4=6166, 5=3589, 6=2203, 7=1867, 8=831, 9=697, 10=358, 11=149}

        // Run the second time
//        shortWordsLengthArray = [1, 1475, 3088, 3941, 3606, 2531, 1708, 1493, 758, 640, 348, 147]
//        shortWordsLengthMap = {0=1, 1=1826, 2=4999, 3=7637, 4=6166, 5=3589, 6=2203, 7=1867, 8=831, 9=697, 10=358, 11=149}


        // Sequential Stream: stream generated from an ordered collection (array or list), range value, generator and iterator, or calling stream.sorted()
        // Unordered stream operation: Stream.unordered(), Stream.distinct(), Stream.map(fun), Collectors.groupingByConcurrent(fun)
        Stream<String> sample = Common.getWordStream().distinct();
        Stream<String> sample1 = Common.getWordStream().parallel().unordered().limit(100);
        Map<String, List<City>> result = readCities().parallel().collect(groupingByConcurrent(City::getState));

        // Principle of non-interference
        List<String> wordList = Common.getWordList();
        Stream<String> words = wordList.stream();
        wordList.add("END"); // OK
        long n = words.distinct().count();
        System.out.println("n = " + n);

        Stream<String> words2 = wordList.stream();
        words.forEach(s -> {
            if (s.length() < 12) wordList.remove(s); // Not okay, interfered
        });
    }
}
