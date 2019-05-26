package com.adventofcode.aoc2017;

import com.adventofcode.Solution;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.LongBinaryOperator;

import static com.adventofcode.utils.Utils.*;

class AoC082017 implements Solution {

    private static String solve(final List<String> input, final boolean first) {
        final Map<String, Long> registers = new HashMap<>();
        long max = 0;
        for (final String s : input) {
            final List<String> row = splitOnTabOrSpace(s);
            long registerValue = registers.computeIfAbsent(row.get(4), k -> 0L);
            final BiPredicate<Long, Long> test = test(row.get(5));
            final long numberCheck = atol(row.get(6));
            if (test.test(registerValue, numberCheck)) {
                final String register = row.get(0);
                final LongBinaryOperator action = action(row.get(1));
                final long number = atol(row.get(2));
                registerValue = registers.compute(register, (r, i) -> action.applyAsLong(i != null ? i : 0L, number));
                max = Math.max(max, registerValue);
            }
        }
        if (first) {
            return itoa(Collections.max(registers.values()));
        } else {
            return itoa(max);
        }

    }

    private static LongBinaryOperator action(final String action) {
        return switch (action) {
            case "inc" -> (i, j) -> i + j;
            case "dec" -> (i, j) -> i - j;
            default -> throw new UnsupportedOperationException();
        };
    }

    private static BiPredicate<Long, Long> test(final String action) {
        return switch (action) {
            case "<" -> (i, j) -> i < j;
            case "<=" -> (i, j) -> i <= j;
            case ">" -> (i, j) -> i > j;
            case ">=" -> (i, j) -> i >= j;
            case "==" -> Long::equals;
            case "!=" -> (i, j) -> !i.equals(j);
            default -> throw new UnsupportedOperationException();
        };
    }

    public String solveFirstPart(final List<String> input) {
        return solve(input, true);
    }

    public String solveSecondPart(final List<String> input) {
        return solve(input, false);
    }
}