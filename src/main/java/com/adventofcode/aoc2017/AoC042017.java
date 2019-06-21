package com.adventofcode.aoc2017;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.itoa;

class AoC042017 implements Solution {
    private static String solve(final Stream<String> input, final Predicate<List<String>> predicate) {
        final long res = input.map(Utils::splitOnTabOrSpace).filter(predicate).count();
        return itoa(res);
    }

    public String solveFirstPart(final Stream<String> input) {
        return solve(input, Utils::areDistinct);
    }

    public String solveSecondPart(final Stream<String> input) {
        return solve(input, Utils::areNotAnagrams);
    }
}
