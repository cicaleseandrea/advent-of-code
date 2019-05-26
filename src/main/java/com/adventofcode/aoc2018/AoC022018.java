package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.adventofcode.utils.Utils.boolToLong;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

class AoC022018 implements Solution {
    private static Pair<Long, Long> accumulateResults(final Pair<Long, Long> p, final Pair<Long, Long> r) {
        return new Pair<>(p.getFirst() + r.getFirst(), p.getSecond() + r.getSecond());
    }

    private static Pair<Long, Long> computePartialResult(final String letters) {
        final Collection<Long> frequencies = letters.chars().boxed().collect(groupingBy(identity(), counting())).values();
        return new Pair<>(boolToLong(frequencies.contains(2L)), boolToLong(frequencies.contains(3L)));
    }

    public String solveFirstPart(final List<String> input) {
        final Pair<Long, Long> res = input.parallelStream().map(AoC022018::computePartialResult)
                .reduce(
                        Pair.ZERO, AoC022018::accumulateResults);
        return itoa(res.getFirst() * res.getSecond());
    }

    public String solveSecondPart(final List<String> input) {
        for (int i = 0; i < input.get(0).length(); i++) {
            final Set<String> set = new HashSet<>();
            for (final String str : input) {
                //remove char at position i
                final String substring = str.substring(0, i) + str.substring(i + 1);
                if (!set.add(substring)) {
                    return substring;
                }
            }
        }
        return input.get(0);
    }
}
