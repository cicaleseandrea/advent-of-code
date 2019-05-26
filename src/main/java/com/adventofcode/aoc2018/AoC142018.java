package com.adventofcode.aoc2018;

import com.adventofcode.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import static com.adventofcode.utils.Utils.*;

class AoC142018 implements Solution {

    private static List<Integer> recipes;

    private static String solve(final List<String> input, final BiPredicate<Integer, List<Integer>> isResult,
                                final BiFunction<Integer, List<Integer>, String> computeResult) {
        reset();
        final List<Integer> num = new ArrayList<>();
        for (final char c : input.get(0).toCharArray()) {
            num.add(charToInt(c));
        }
        final int n = atoi(input.get(0));
        int first = 0;
        int second = 1;
        while (!isResult.test(n, num)) {
            final int sum = recipes.get(first) + recipes.get(second);
            if (sum > 9) {
                recipes.add(sum / 10);
                if (isResult.test(n, num)) break;
            }
            recipes.add(sum % 10);
            first = incrementMod(first, recipes.get(first) + 1, recipes.size());
            second = incrementMod(second, recipes.get(second) + 1, recipes.size());
        }
        return computeResult.apply(n, num);
    }

    private static String computeResultFirstPart(final int n, final List<Integer> dummy) {
        final StringBuilder str = new StringBuilder(10);
        for (int i = n; i < n + 10; i++) {
            str.append(recipes.get(i));
        }
        return str.toString();
    }

    private static boolean isResultSecondPart(final int dummy, final List<Integer> num) {
        int i = recipes.size();
        int j = num.size();
        if (i < j) {
            return false;
        }
        //check if recipes are the same
        while (j > 0) {
            if (!recipes.get(--i).equals(num.get(--j))) {
                return false;
            }
        }
        return true;
    }

    private static void reset() {
        recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);
    }

    public String solveFirstPart(final List<String> input) {
        return solve(input,
                (n, dummy) -> recipes.size() >= n + 10, //check if we have enough recipes
                AoC142018::computeResultFirstPart);
    }

    public String solveSecondPart(final List<String> input) {
        return solve(input, AoC142018::isResultSecondPart, (dummy, num) -> itoa((long) recipes.size() - num.size()));
    }
}