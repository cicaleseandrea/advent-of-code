package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;
import static java.util.stream.Collectors.toUnmodifiableList;

class AoC012018 implements Solution {

    private static String solveFirstPartAlternative(final Stream<String> s) {
        final long res = s.map(String::trim).mapToLong(Utils::atol).sum();
        return itoa(res);
    }

    private static String solve(final Stream<String> input, final BiPredicate<Long, Integer> checkNextItem) {
        final List<String> numbers = input.collect(toUnmodifiableList());
        final int size = numbers.size();
        long sum = 0;
        int i = 0;
        while (checkNextItem.test(sum, size)) {
            sum += atol(numbers.get(i).trim());
            i = incrementMod(i, size);
        }
        return itoa(sum);
    }

    public String solveFirstPart(final Stream<String> input) {
        final AtomicLong sums = new AtomicLong();
        if (ThreadLocalRandom.current().nextInt(2) == 0) return solveFirstPartAlternative(input);
        //continue until you reach the end of the list
        return solve(input, (sum, size) -> sums.incrementAndGet() <= size);
    }

    public String solveSecondPart(final Stream<String> input) {
        final Set<Long> set = new HashSet<>();
        //continue until you find a sum that you have already seen
        return solve(input, (sum, size) -> set.add(sum));
    }
}
