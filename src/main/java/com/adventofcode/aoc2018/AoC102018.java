package com.adventofcode.aoc2018;

import static com.adventofcode.utils.Utils.BLACK;
import static com.adventofcode.utils.Utils.EMPTY;
import static com.adventofcode.utils.Utils.WHITE;
import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.getIterable;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;

class AoC102018 implements Solution {

    private static String solve(final Stream<String> input, final BinaryOperator<String> computeResult) {
        var curr = computeInitialPoints(getIterable(input));
        int res = 0;
        boolean printed = false;
        String message = EMPTY;
        do {
            //stringRepresentation message if it appeared
            if (isMessage(curr)) {
                message = printSolution(curr);
                printed = true;
            } else {
                curr = computeNextPoints(curr);
                res++;
            }
        } while (!printed);

        return computeResult.apply(message, itoa(res));
    }

    private static Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> computeNextPoints(final Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> curr) {
        final Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> next = new HashMap<>();
        for (final Map.Entry<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> e : curr.entrySet()) {
            final Pair<Integer, Integer> p = e.getKey();
            for (final Pair<Integer, Integer> v : e.getValue()) {
                //move points with proper velocity
                final int x = p.getFirst() + v.getFirst();
                final int y = p.getSecond() + v.getSecond();
                next.computeIfAbsent(new Pair<>(x, y),
                        k -> new ArrayList<>()).add(v);
            }
        }
        return next;
    }

    private static boolean isMessage(final Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> curr) {
        for (final Pair<Integer, Integer> p : curr.keySet()) {
            final Integer x = p.getFirst();
            final Integer y = p.getSecond();
            if (!curr.containsKey(new Pair<>(x - 1, y - 1))
                    && !curr.containsKey(new Pair<>(x - 1, y))
                    && !curr.containsKey(new Pair<>(x - 1, y + 1))
                    && !curr.containsKey(new Pair<>(x + 1, y - 1))
                    && !curr.containsKey(new Pair<>(x + 1, y))
                    && !curr.containsKey(new Pair<>(x + 1, y + 1))
                    && !curr.containsKey(new Pair<>(x, y - 1))
                    && !curr.containsKey(new Pair<>(x, y + 1))) {
                return false;
            }
        }
        return true;
    }

    private static Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> computeInitialPoints(final Iterable<String> input) {
        final Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> points = new HashMap<>();
        for (final String s : input) {
            final List<String> p = Utils.splitOnTabOrSpace(s.replaceAll("[<>,]", " "));
            points.computeIfAbsent(new Pair<>(atoi(p.get(1)), atoi(p.get(2))),
                    k -> new ArrayList<>()).add(new Pair<>(atoi(p.get(4)), atoi(p.get(5))));
        }
        return points;
    }

    private static String printSolution(final Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> curr) {
        final StringBuilder str = new StringBuilder();
        int maxRows = Integer.MIN_VALUE;
        int minRows = Integer.MAX_VALUE;
        int maxColumns = Integer.MIN_VALUE;
        int minColumns = Integer.MAX_VALUE;
        //compute borders
        for (final Pair<Integer, Integer> p : curr.keySet()) {
            final int x = p.getFirst();
            final int y = p.getSecond();
            minColumns = Math.min(minColumns, x);
            maxColumns = Math.max(maxColumns, x);
            minRows = Math.min(minRows, y);
            maxRows = Math.max(maxRows, y);
        }

        for (int i = minRows; i <= maxRows; i++) {
            for (int j = minColumns; j <= maxColumns; j++) {
                if (curr.containsKey(new Pair<>(j, i))) {
                    str.append(BLACK);
                } else {
                    str.append(WHITE);
                }
            }
            str.append('\n');
        }
        return str.deleteCharAt(str.length() - 1).toString();
    }

    public String solveFirstPart(final Stream<String> input) {
        return solve(input, (msg, n) -> msg);
    }

    public String solveSecondPart(final Stream<String> input) {
        return solve(input, (msg, n) -> n);
    }
}