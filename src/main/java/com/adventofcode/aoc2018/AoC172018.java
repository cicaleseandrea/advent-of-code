package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;

class AoC172018 implements Solution {

    private static final boolean PRINT = false;

    private static String solve(final Stream<String> input,
                                final Predicate<Map.Entry<Pair<Integer, Integer>, Character>> count) {
        final Map<Pair<Integer, Integer>, Character> map = new HashMap<>();
        final var points = init(input, map);
        final int firstY = points.getFirst().getSecond();
        final int lastY = points.getSecond().getSecond();
        final Pair<Integer, Integer> source = new Pair<>(500, 0);
        map.put(source, PIPE);
        fall(map, source, points);
        if (PRINT) {
            print(map, points);
        }
        return itoa(map.entrySet().stream().
                filter(e -> count.test(e) || e.getValue() == TILDE)
                .filter(e -> e.getKey().getSecond() >= firstY && e.getKey().getSecond() <= lastY)
                .count());
    }

    private static void fall(final Map<Pair<Integer, Integer>, Character> map, final Pair<Integer, Integer> source,
                             final Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> points) {
        //never move x, only y
        final int x = source.getFirst();
        int y = source.getSecond();
        Pair<Integer, Integer> curr = new Pair<>(x, y);
        final int lastY = points.getSecond().getSecond();
        //fall until you go under the last point or you hit clay (#) or still water (~)
        while (y <= lastY && canFlow(map, curr)) {
            //running water
            map.put(curr, PIPE);
            y++;
            curr = new Pair<>(x, y);
        }
        //if you did not go under the last point, spread out horizontally
        if (y <= lastY) {
            boolean fallLeft;
            boolean fallRight;
            do {
                //when blocked, go up and fill with running water
                y--;
                fallLeft = fill(true, map, new Pair<>(x, y), PIPE, points);
                fallRight = fill(false, map, new Pair<>(x + 1, y), PIPE, points);
                //if the water is blocked, this level becomes still water
                if (!fallLeft && !fallRight) {
                    fill(true, map, new Pair<>(x, y), TILDE, points);
                    fill(false, map, new Pair<>(x + 1, y), TILDE, points);
                }
            } while (!fallLeft && !fallRight);
        }
    }

    private static boolean fill(final boolean left, final Map<Pair<Integer, Integer>, Character> map,
                                final Pair<Integer, Integer> source, final char water,
                                final Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> points) {
        //never move y, only x
        int y = source.getSecond();
        int x = source.getFirst();
        Pair<Integer, Integer> curr = new Pair<>(x, y);
        Pair<Integer, Integer> under = new Pair<>(x, y + 1);
        //move until you either fall or hit clay (#) or still water (~)
        while (!canFlow(map, under) && canFlow(map, curr)) {
            //fill with water
            map.put(curr, water);
            if (left) {
                x--;
            } else {
                x++;
            }
            curr = new Pair<>(x, y);
            under = new Pair<>(x, y + 1);
        }
        //fall
        final boolean fall = canFlow(map, under);
        if (fall) {
            fall(map, curr, points);
        }
        return fall;
    }

    private static boolean canFlow(final Map<Pair<Integer, Integer>, Character> map, final Pair<Integer, Integer> curr) {
        return map.getOrDefault(curr, PIPE) == PIPE;
    }

    private static Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> init(final Stream<String> input,
                                                                             final Map<Pair<Integer, Integer>, Character> map) {
        int maxX = 0;
        int maxY = 0;
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        for (final String row : getIterable(input)) {
            final boolean startX = row.charAt(0) == 'x';
            String[] tmp = row.split("=");
            int x = 0;
            int y = 0;
            int i = atoi(extractNumberFromString(tmp[1]));
            if (startX) {
                x = i;
                maxX = Math.max(maxX, i);
                minX = Math.min(minX, i);
            } else {
                y = i;
                maxY = Math.max(maxY, i);
                minY = Math.min(minY, i);
            }
            tmp = tmp[2].split("\\.+");
            final int min = atoi(extractNumberFromString(tmp[0]));
            final int max = atoi(extractNumberFromString(tmp[1]));
            for (i = min; i <= max; i++) {
                if (startX) {
                    y = i;
                    maxY = Math.max(maxY, i);
                    minY = Math.min(minY, i);
                } else {
                    x = i;
                    maxX = Math.max(maxX, i);
                    minX = Math.min(minX, i);
                }
                final Pair<Integer, Integer> position = new Pair<>(x, y);
                map.put(position, HASH);
            }
        }
        final Pair<Integer, Integer> first = new Pair<>(minX, minY);
        final Pair<Integer, Integer> last = new Pair<>(maxX, maxY);
        return new Pair<>(first, last);
    }

    private static void print(final Map<Pair<Integer, Integer>, Character> map,
                              final Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> points) {
        final Pair<Integer, Integer> first = points.getFirst();
        final Pair<Integer, Integer> last = points.getSecond();
        for (int y = first.getSecond(); y <= last.getSecond(); y++) {
            for (int x = first.getFirst(); x <= last.getFirst(); x++) {
                System.out.print(map.getOrDefault(new Pair<>(x, y), SPACE));
            }
            System.out.println();
        }
        System.out.println();
    }

    public String solveFirstPart(final Stream<String> input) {
        return solve(input, e -> e.getValue() == PIPE);
    }

    public String solveSecondPart(final Stream<String> input) {
        return solve(input, e -> false);
    }
}