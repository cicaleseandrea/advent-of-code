package com.adventofcode.aoc2017;

import com.adventofcode.Solution;

import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;

class AoC012017 implements Solution {

    private static String solve(final String s, final IntUnaryOperator computeNext) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            //compute where is the value to check
            final int next = computeNext.applyAsInt(i);
            final char toCheck = s.charAt(next);
            final char curr = s.charAt(i);
            if (curr == toCheck) {
                res += charToInt(curr);
            }
        }
        return itoa(res);
    }

    public String solveFirstPart(final Stream<String> input) {
        final String s = getFirstString(input);
        return solve(s, i -> (i + 1) % s.length());
    }

    public String solveSecondPart(final Stream<String> input) {
        final String s = getFirstString(input);
        return solve(s, i -> (i + s.length() / 2) % s.length());
    }
}
