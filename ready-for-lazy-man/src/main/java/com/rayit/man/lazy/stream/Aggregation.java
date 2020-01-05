package com.rayit.man.lazy.stream;

import java.util.Optional;
import java.util.stream.Stream;

import static com.rayit.man.lazy.stream.GenerateStream.generateStreamByPattern;

public class Aggregation {

    public static void main(String[] args) throws Exception {

        // reduce

        Stream<Double> values = Stream.generate(Math::random).limit(10);
        Optional<Double> sum = values.reduce((x, y) -> x + y);
        sum.ifPresent(x -> System.out.println("sum = " + sum.get()));
        //sum.ifPresent(System.out::println);

        Stream<Double> values2 = Stream.generate(Math::random).limit(20);
        Optional<Double> sum2 = values2.reduce(Double::sum);
        System.out.println("sum2 = " + sum2.orElse(Double.valueOf(0)));

        Integer[] integers = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Stream<Integer> values3 = Stream.of(integers);
        Integer sum3 = values3.reduce(0, (x, y) -> x + y);
        System.out.println("sum3 = " + sum3);

        Stream<String> words = generateStreamByPattern();
        int result = words.reduce(0, (total, word) -> total + word.length(), (total1, total2) -> total1 + total2);
        System.out.println("result = " + result);

        Stream<String> words2 = generateStreamByPattern();
        int result2 = words2.mapToInt(String::length).sum();
        System.out.println("result2 = " + result2);
    }
}
