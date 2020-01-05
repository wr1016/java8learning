package com.rayit.man.lazy.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class OptionalUsage {

    public static void main(String[] args) {
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
        Optional<Double> result3 = inverse(Double.valueOf(4)).flatMap(OptionalUsage::squareRoot);
        System.out.println("result3 = " + result3.orElse(Double.valueOf(0)));
        Optional<Double> result4 = Optional.of(Double.valueOf(4)).flatMap(OptionalUsage::inverse).flatMap(OptionalUsage::squareRoot);
        System.out.println("result4 = " + result4.orElse(Double.valueOf(0)));
    }

    public static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }
}
