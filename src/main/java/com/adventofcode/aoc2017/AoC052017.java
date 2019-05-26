package com.adventofcode.aoc2017;

import com.adventofcode.Solution;

import java.util.List;
import java.util.function.LongUnaryOperator;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

class AoC052017 implements Solution {

    private static long computeOffsetSecondPart(final long offset) {
        return offset < 3 ? offset + 1 : offset - 1;
    }

    private static String solve(final List<String> input, LongUnaryOperator computeOffset) {
        final List<Long> numbers = toLongList(input);
        int res = 0;
        int i = 0;
        while (i < numbers.size()) {
            final long offset = numbers.get(i);
            numbers.set(i, computeOffset.applyAsLong(offset));
            i += offset;
            res++;
        }
        return itoa(res);
    }

    private static long computeOffsetFirstPart(final long offset) {
        return offset + 1;
    }

    public String solveFirstPart(final List<String> input) {
        return solve(input, AoC052017::computeOffsetFirstPart);
    }

    public String solveSecondPart(final List<String> input) {
        return solve(input, AoC052017::computeOffsetSecondPart);
    }
}
