package com.rayit.man.lazy.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rayit.man.lazy.stream.CollectResultIntoMap.people2;
import static java.util.stream.Collectors.*;

class City {
    private String name;
    private String state;
    private int population;

    public City(String name, String state, int population) {
        this.name = name;
        this.state = state;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public int getPopulation() {
        return population;
    }
}

public class GroupAndSlice {

    public static final String filename = "E:\\DEV\\workspace\\java8learning\\ready-for-lazy-man\\src\\main\\resources\\cities.txt";

    public static Stream<City> readCities() throws IOException {
        return Files.lines(Paths.get(filename)).map(l -> l.split(", ")).map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));
    }

    public static void main(String[] args) throws IOException {
        // groupingBy()
        Map<Integer, List<Person>> idToPersons = people2().collect(Collectors.groupingBy(Person::getId));
        List<Person> onePerson = idToPersons.get(1001);
        System.out.println("onePerson = " + onePerson);

        // partitioningBy()
        Map<Boolean, List<Person>> idToPersons2 = people2().collect(Collectors.partitioningBy(p -> p.getId() == 1001));
        List<Person> onePerson2 = idToPersons2.get(true);
        System.out.println("onePerson2 = " + onePerson2);

        // groupingByConcurrent()
        ConcurrentMap<Integer, List<Person>> idToPersons3 = people2().parallel().collect(Collectors.groupingByConcurrent(Person::getId));
        List<Person> onePerson3 = idToPersons3.get(1004);
        System.out.println("onePerson3 = " + onePerson3);

        Map<Integer, Set<Person>> idToPersons4 = people2().collect(Collectors.groupingBy(Person::getId, Collectors.toSet()));
        Set<Person> onePerson4 = idToPersons4.get(1001);
        System.out.println("onePerson4 = " + onePerson4);

        // counting()
        Map<Integer, Long> idToPersonsCounts = people2().collect(Collectors.groupingBy(Person::getId, Collectors.counting()));
        Long personCount = idToPersonsCounts.get(1001);
        System.out.println("personCount = " + personCount);

        // summingInt()
        Map<String, Integer> stateToCityPopulation = readCities().collect(groupingBy(City::getState, summingInt(City::getPopulation)));
        System.out.println("stateToCityPopulation: " + stateToCityPopulation);

        // maxBy()/minBy()
        Map<String, Optional<City>> stateToLargestCity = readCities().collect(
                groupingBy(City::getState, maxBy(Comparator.comparing(City::getPopulation))));
        System.out.println("stateToLargestCity: " + stateToLargestCity);

        // mapping()
        Map<String, Optional<String>> stateToLongestCityName = readCities().collect(
                groupingBy(City::getState, mapping(City::getName, maxBy(Comparator.comparing(String::length)))));
        System.out.println("stateToLongestCityName: " + stateToLongestCityName);


        // [Collectors.groupingBy,Collectors.mapping] VS [Collectors.toMap]
        Map<Integer, Set<String>> idToPersons5 = people2().collect(Collectors.groupingBy(Person::getId, Collectors.mapping(Person::getName, Collectors.toSet())));
        Set<String> onePerson5 = idToPersons5.get(1001);
        System.out.println("onePerson5 = " + onePerson5);

        // Collectors.groupingBy(), summarizingInt()/summarizingLong()/summarizingDouble()
        Map<String, IntSummaryStatistics> stateToCityPopulationSummary = readCities().collect(
                Collectors.groupingBy(City::getState,
                        Collectors.summarizingInt(City::getPopulation)));
        System.out.println(stateToCityPopulationSummary.get("NY"));

        // Collectors.reducing()
        Map<String, String> stateToCityNames = readCities().collect(
                Collectors.groupingBy(City::getState,
                        Collectors.reducing("", City::getName,
                                (s, t) -> s.length() == 0 ? t : s + ", " + t)));
        System.out.println("stateToCityNames: " + stateToCityNames);

        Map<String, String> stateToCityNames2 = readCities().collect(
                Collectors.groupingBy(City::getState,
                        Collectors.mapping(City::getName,
                                Collectors.joining(", "))));
        System.out.println("stateToCityNames2: " + stateToCityNames2);
    }
}
