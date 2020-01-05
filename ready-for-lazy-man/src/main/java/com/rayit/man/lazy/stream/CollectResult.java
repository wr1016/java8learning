package com.rayit.man.lazy.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rayit.man.lazy.stream.Common.getWordStream;

public class CollectResult {

    public static void main(String[] args) throws Exception {
        // toArray(T[] t)
        Stream<String> words = getWordStream();
        String[] result = words.toArray(String[]::new);

        // collect
        HashSet<String> result2 = getWordStream().collect(HashSet::new, HashSet::add, HashSet::addAll);
        // Collectors.toList()
        List<String> result3 = getWordStream().collect(Collectors.toList());
        // Collectors.toSet()
        Set<String> result4 = getWordStream().collect(Collectors.toSet());
        // Collectors.toCollection(T t)
        TreeSet<String> result5 = getWordStream().collect(Collectors.toCollection(TreeSet::new));
        // joining()
        String result6 = getWordStream().collect(Collectors.joining());
        // joining(",")
        String result7 = getWordStream().collect(Collectors.joining(","));
        // If words contain other value(not string), then it needs to be mapped to string.
        String result8 = getWordStream().map(Object::toString).collect(Collectors.joining(","));
        System.out.println("result8 = " + result8);

        // summarizingInt()/summarizingLong()/summarizingDouble()
        IntSummaryStatistics summary = getWordStream().collect(Collectors.summarizingInt(String::length));
        double averageWordLength = summary.getAverage();
        System.out.println("averageWordLength = " + averageWordLength);
        double maxWordLength = summary.getMax();
        System.out.println("maxWordLength = " + maxWordLength);

        // forEach
        getWordStream().forEach(System.out::println);
        // forEachOrdered
        getWordStream().forEachOrdered(System.out::println);
    }
}
