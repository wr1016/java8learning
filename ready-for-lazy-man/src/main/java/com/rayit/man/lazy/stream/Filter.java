package com.rayit.man.lazy.stream;

import java.util.List;

import static com.rayit.man.lazy.stream.Common.getWordList;

public class Filter {

    public static void main(String[] args) throws Exception {
        List<String> words = getWordList();

        // filter, stream()
        long count1 = words.stream().filter(w -> w.length() > 12).count();
        System.out.println("count1 = " + count1);
        // filter, parallelStream()
        long count2 = words.parallelStream().filter(w -> w.length() > 8).count();
        System.out.println("count2 = " + count2);
    }
}
