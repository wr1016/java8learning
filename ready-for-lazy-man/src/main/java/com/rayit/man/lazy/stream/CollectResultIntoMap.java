package com.rayit.man.lazy.stream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Person {
    private int id;
    private String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getClass().getName() +
                "[id=" + id + ",name=" + name + "]";
    }
}

public class CollectResultIntoMap {

    public static Stream<Person> people() {
        return Stream.of(
                new Person(1001, "Peter"),
                new Person(1002, "Paul"),
                new Person(1003, "Mary"));
    }

    public static Stream<Person> people2() {
        return Stream.of(
                new Person(1001, "Peter"),
                new Person(1001, "Paul"),
                new Person(1003, "Mary"),
                new Person(1004, "Alice"),
                new Person(1004, "May"));
    }

    public static void main(String[] args) {
        // toMap()
        Map<Integer, String> idToName = people().collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println("idToName = " + idToName);
        // Function.identity(): get element value from Person
        Map<Integer, Person> idToPerson = people().collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println("idToPerson = " + idToPerson);

//        Map<Integer, Person> idToPerson2 = people2().collect(Collectors.toMap(Person::getId, Function.identity()));
//        System.out.println("idToPerson2 = " + idToPerson2);

//        Map<Integer, Person> idToPerson3 = people2().collect(
//                Collectors.toMap(
//                        Person::getId,
//                        Function.identity(),
//                        (existingValue, newValue) -> {
//                            throw new IllegalStateException();
//                        },
//                        TreeMap::new));
//        System.out.println("idToPerson3: " + idToPerson.getClass().getName() + idToPerson3);

        Map<Integer, Person> idToPerson4 = people2().collect(
                Collectors.toMap(
                        Person::getId,
                        Function.identity(),
                        (existingValue, newValue) -> existingValue));
        System.out.println("idToPerson4: " + idToPerson.getClass().getName() + idToPerson4);

        Map<Integer, Set<String>> idToPerson5 = people2().collect(
                Collectors.toMap(
                        Person::getId,
                        p -> Collections.singleton(p.getName()),// Collections.singleton: new a collection that only has one element like the value of p.getName()
                        (a, b) -> { // union of a and b
                            Set<String> r = new HashSet<>(a);
                            r.addAll(b);
                            return r;
                        }));
        System.out.println("idToPerson5: " + idToPerson.getClass().getName() + idToPerson5);
    }
}
