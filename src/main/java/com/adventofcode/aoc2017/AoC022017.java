package com.adventofcode.aoc2017;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.itoa;

class AoC022017 implements Solution {
    private static long computePartialResultFirst(final List<Long> num) {
        final LongSummaryStatistics stats = new LongSummaryStatistics();
        num.forEach(stats::accept);
        return stats.getMax() - stats.getMin();
    }

    private static long computePartialResultSecond(final List<Long> num) {
        for (int i = 0; i < num.size(); i++) {
            for (int j = i + 1; j < num.size(); j++) {
                final long a = num.get(i);
                final long b = num.get(j);
                final long max = Math.max(a, b);
                final long min = Math.min(a, b);
                //check if one number can be divided by the other
                if (max % min == 0) {
                    return max / min;
                }
            }
        }
        return 0;
    }

    private static String solve(final Stream<String> input, final ToLongFunction<List<Long>> computeRowResult) {
        final long res = input.map(Utils::toLongList).mapToLong(computeRowResult).sum();
        return itoa(res);
    }

    @Override
    public String solveFirstPart(final Stream<String> input) {
        return solve(input, AoC022017::computePartialResultFirst);
    }

    @Override
    public String solveSecondPart(final Stream<String> input) {
        return solve(input, AoC022017::computePartialResultSecond);
    }
}
