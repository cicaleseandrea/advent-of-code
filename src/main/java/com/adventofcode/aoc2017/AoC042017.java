package com.adventofcode.aoc2017;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

import java.util.List;
import java.util.function.Predicate;

import static com.adventofcode.utils.Utils.itoa;

class AoC042017 implements Solution {
    private static String solve(final List<String> input, final Predicate<List<String>> predicate) {
        final long res = input.parallelStream().map(Utils::splitOnTabOrSpace).filter(predicate).count();
        return itoa(res);
    }

    public String solveFirstPart(final List<String> input) {
        return solve(input, Utils::areDistinct);
    }

    public String solveSecondPart(final List<String> input) {
        return solve(input, Utils::areNotAnagrams);
    }
}
