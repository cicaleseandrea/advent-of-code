package com.adventofcode.aoc2018;

import com.adventofcode.Solution;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;
import static java.time.LocalDateTime.parse;
import static java.util.stream.Collectors.toMap;

class AoC042018 implements Solution {

    private static String solve(final Stream<String> input, final ToLongFunction<LongSummaryStatistics> findStat) {
        //sort input based on LocalDateTime. Each LocalDateTime has one message only
        final SortedMap<LocalDateTime, String> sorted = input.map(row -> row.split("[\\[\\]]"))
                .collect(toMap(split -> parse(split[1], DATE_TIME_FORMATTER), split -> split[2], (m1, m2) -> {
                    throw new IllegalStateException();
                }, TreeMap::new));

        //map each guard to his list of sleep frequencies per minute
        Map<String, Map<Integer, Long>> sleep = new HashMap<>();
        String id = EMPTY;
        int start = 0;
        for (final var row : sorted.entrySet()) {
            final int minute = row.getKey().getMinute();
            final String text = row.getValue();
            if (text.contains("asleep")) {
                start = minute;
            } else if (text.contains("wakes")) {
                //increment the corresponding minutes asleep for this guard
                for (int i = start; i < minute; i++) {
                    incrementMapElement(sleep.computeIfAbsent(id, k -> new HashMap<>()), i);
                }
            } else {
                id = extractNumberFromString(text.split("#")[1]);
            }
        }

        long res = 0;
        long maxSleep = 0;
        for (final var e : sleep.entrySet()) {
            final LongSummaryStatistics stats = e.getValue().values().parallelStream().mapToLong(Long::longValue).summaryStatistics();
            if (maxSleep < findStat.applyAsLong(stats)) {
                maxSleep = findStat.applyAsLong(stats);
                res = atol(e.getKey()) * Collections.max(e.getValue().entrySet(), Map.Entry.comparingByValue()).getKey();
            }
        }
        return itoa(res);
    }

    public String solveFirstPart(final Stream<String> input) {
        return solve(input, LongSummaryStatistics::getSum);
    }

    public String solveSecondPart(final Stream<String> input) {
        return solve(input, LongSummaryStatistics::getMax);
    }
}
