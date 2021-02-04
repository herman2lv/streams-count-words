package com.efimchick.ifmo.streams.countwords;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Words {
    private static final String ONE_OR_MANY_WHITESPACE = "\\s+";
    private static final String OUTPUT_FORMAT = "%s - %d%n";
    private static final int MIN_LENGTH = 4;
    private static final int MIN_OCCURS = 10;
    private static final String NON_LATIN_NON_CYRILLIC_CHAR = "[^a-zA-Zа-яА-Я]";
    private static final String WHITESPACE = " ";

    public String countWords(final List<String> lines) {
        String output = lines.stream()
                .map(s -> s.replaceAll(NON_LATIN_NON_CYRILLIC_CHAR, WHITESPACE))
                .flatMap(s -> Arrays.stream(s.toLowerCase().split(ONE_OR_MANY_WHITESPACE)))
                .filter(s -> s.length() >= MIN_LENGTH)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() >= MIN_OCCURS)
                .sorted(Map.Entry.comparingByKey())
                .sorted((e1, e2) -> (int) (e2.getValue() - e1.getValue()))
                .map(e -> String.format(OUTPUT_FORMAT, e.getKey(), e.getValue()))
                .collect(Collectors.joining());
        return removeLastLineFeed(output);
    }

    private String removeLastLineFeed(final String output) {
        return output.substring(0, output.length() - 1);
    }
}
