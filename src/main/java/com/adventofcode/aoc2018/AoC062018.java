package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

import java.util.*;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;

class AoC062018 implements Solution {

    private static int rows;
    private static int columns;

    private static void reset() {
        rows = 0;
        columns = 0;
    }

    private static Map<Character, Pair<Integer, Integer>> createPoints(final Stream<String> input) {
        final Map<Character, Pair<Integer, Integer>> points = new HashMap<>();
        char name = 'A';
        for (final String s : getIterable(input)) {
            final Pair<Integer, Integer> point = createPairInteger(s.split(","));
            points.put(name++, point);
            assert name != SPACE;
            columns = Math.max(columns, point.getFirst() + 1);
            rows = Math.max(rows, point.getSecond() + 1);
        }
        return points;
    }

    private static boolean isOnLimit(final int i, final int j) {
        return i == 0 || j == 0 || i == rows - 1 || j == columns - 1;
    }

    public String solveFirstPart(final Stream<String> input) {
        reset();
        final var points = createPoints(input);
        final Map<Character, Long> frequencies = new HashMap<>();
        final Set<Character> banned = new HashSet<>();
        banned.add(SPACE);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                char closest = SPACE;
                long minDistance = Long.MAX_VALUE;
                for (final Map.Entry<Character, Pair<Integer, Integer>> point : points.entrySet()) {
                    //compute Manhattan distance
                    final Pair<Integer, Integer> p = point.getValue();
                    final long distance = manhattanDistance(p.getFirst(), p.getSecond(), j, i);
                    if (distance < minDistance) {
                        //find the closest location
                        minDistance = distance;
                        closest = point.getKey();
                    } else if (distance == minDistance) {
                        closest = SPACE;
                    }
                }
                //closest location for each point
                if (!isOnLimit(i, j)) {
                    frequencies.merge(closest, 1L, Long::sum);
                } else {
                    banned.add(closest);
                }
            }
        }
        frequencies.keySet().removeAll(banned);
        return itoa(Collections.max(frequencies.values()));
    }

    public String solveSecondPart(final Stream<String> input) {
        reset();
        final var points = createPoints(input);
        long res = 0L;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //compute Manhattan distance from each location and sum them all
                long manDistance = 0L;
                for (final Pair<Integer, Integer> p : points.values()) {
                    manDistance += manhattanDistance(p.getFirst(), p.getSecond(), j, i);
                }
                if (manDistance < (points.size() < 10 ? 32 : 10000)) {
                    res++;
                }
            }
        }
        return itoa(res);
    }
}
