package com.adventofcode.aoc2017;

import com.adventofcode.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntBiFunction;

import static com.adventofcode.utils.Utils.*;
import static java.util.Comparator.naturalOrder;

class AoC062017 implements Solution {

    private static String solve(final String input, final ToIntBiFunction<Integer, Integer> computeResult) {
        final List<Long> block = toLongList(splitOnTabOrSpace(input));
        final Map<List<Long>, Integer> blocksSeen = new HashMap<>();
        int firstSeen = 0;
        while (blocksSeen.putIfAbsent(block, firstSeen) == null) {
            firstSeen++;
            computeNextBlock(block);
        }
        return itoa(computeResult.applyAsInt(firstSeen, blocksSeen.get(block)));
    }

    private static void computeNextBlock(final List<Long> blocks) {
        final long max = blocks.parallelStream().max(naturalOrder()).orElse(0L);
        final int start = blocks.indexOf(max);
        final int size = blocks.size();
        final long baseIncrement = max / size;
        blocks.set(start, baseIncrement); //the start element will always be set at baseIncrement
        long mod = max % size;
        for (int i = incrementMod(start, size); i != start; i = incrementMod(i, size)) {
            //add 1 more for the first #mod elements
            final long increment = baseIncrement +
                    Math.min(mod, 1); // 0 or 1
            incrementListElement(blocks, i, increment);
            if (mod > 0) mod--;
        }
    }

    public String solveFirstPart(final List<String> input) {
        return solve(input.get(0), (firstLoop, seen) -> firstLoop);
    }

    public String solveSecondPart(final List<String> input) {
        return solve(input.get(0), (firstLoop, seen) -> firstLoop - seen);
    }
}
