package com.rayit.man.lazy.stream;

import java.util.Optional;
import java.util.stream.Stream;

import static com.rayit.man.lazy.stream.Common.getWordStream;

public class SimpleAggregation {

    public static void main(String[] args) throws Exception {
        Stream<String> words = getWordStream();

        // count
        long count1 = words.filter(w -> w.length() > 12).count();
        System.out.println("count1 = " + count1);
        Optional<String> largest = words.max(String::compareToIgnoreCase);
        if (largest.isPresent()) {
            System.out.println("largest = " + largest.get());
        }

        // findFirst: Find the first one element which is matched with it
        Optional<String> startsWithM = words.filter(s -> s.startsWith("M")).findFirst();
        if (startsWithM.isPresent()) System.out.println("startsWithM = " + startsWithM.get());

        // findAny: Find the matched element
        Optional<String> startWithT = words.parallel().filter(s -> s.startsWith("T")).findAny();
        if (startWithT.isPresent()) System.out.println("startWithT = " + startWithT.get());

        // anyMatch: If there is a matched element in the collection
        boolean aWordStartsWithP = words.parallel().anyMatch(s -> s.startsWith("P"));
        System.out.println("aWordStartsWithP = " + aWordStartsWithP);

        // allMatch
        boolean allWordStartsWithQ = Stream.of("Qw", "Qere", "Qfff", "Qfgss").parallel().allMatch(s -> s.startsWith("Q"));
        System.out.println("allWordStartsWithQ = " + allWordStartsWithQ);

        // noneMatch
        boolean noneWordStartsWithQ = Stream.of("Qw", "Qere", "Qfff", "Qfgss").parallel().noneMatch(s -> s.startsWith("Q"));
        System.out.println("noneWordStartsWithQ = " + noneWordStartsWithQ);
    }
}
