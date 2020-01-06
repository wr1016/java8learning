package com.rayit.man.lazy.stream;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class OriginalTypeStream {

    public static void main(String[] args) throws Exception {
        // IntStream -> int/short/char/byte/boolean
        // LongStream -> long
        // DoubleStream -> float/double
        IntStream intStream = IntStream.of(1, 1, 2, 4, 6);
        int[] ints = new int[]{1, 2, 3, 4, 5, 6, 6};
        IntStream intStream1 = Arrays.stream(ints, 1, 4);
        System.out.println("intStream1 = " + Arrays.toString(intStream1.toArray()));

        // range() / rangeClosed()
        IntStream zeroToNinetyNine = IntStream.range(0, 100); // not include 100
        System.out.println("zeroToNinetyNine = " + zeroToNinetyNine.summaryStatistics().getMax());
        IntStream zeroToHundred = IntStream.rangeClosed(0, 100); // include 100
        System.out.println("zeroToHundred = " + zeroToHundred.summaryStatistics().getMax());

        // string.codePoints() / string.chars()
        String sentence = "\uD835\uDD46 is the set of octonions.";
        IntStream codes = sentence.codePoints();
        System.out.println("codes = " + Arrays.toString(codes.toArray()));
        IntStream codes2 = sentence.chars();
        System.out.println("codes2 = " + Arrays.toString(codes2.toArray()));

        // mapToInt/mapToLong/mapToDouble
        IntStream lengths = Common.getWordStream().mapToInt(String::length);
        System.out.println("lengths = " + lengths);
        // Original Stream to Object Stream
        Stream<Integer> integerStream = IntStream.range(0, 100).boxed();
        System.out.println("integerStream = " + Arrays.toString(integerStream.toArray()));

        // getAsInt/getAsLong/getAsDouble
        // sum/average/max/min
        OptionalInt optionalInt = IntStream.range(0, 100).reduce((x, y) -> x + y);
        System.out.println("optionalInt = " + optionalInt.getAsInt());
        OptionalLong optionalLong = LongStream.range(0, 100).reduce((x, y) -> x + y);
        System.out.println("optionalLong = " + optionalLong.getAsLong());
        OptionalDouble optionalDouble = DoubleStream.of(0, 1, 2, 4, 5).average();
        System.out.println("optionalDouble = " + optionalDouble.getAsDouble());
        double optionalDouble1 = DoubleStream.of(0, 1, 2, 4, 5).sum();
        OptionalDouble optionalDouble2 = DoubleStream.of(0, 1, 2, 4, 5).max();
        OptionalDouble optionalDouble3 = DoubleStream.of(0, 1, 2, 4, 5).min();

        // random.ints()/random.longs()/random.doubles()
        Random random = new Random();
        IntStream intStream2 = random.ints();
        IntStream intStream3 = random.ints(10);
        LongStream longStream = random.longs();
        DoubleStream doubleStream = random.doubles();
    }
}
