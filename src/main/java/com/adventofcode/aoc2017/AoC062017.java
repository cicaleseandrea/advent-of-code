package com.adventofcode.aoc2017;

import com.adventofcode.Solution;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntBiFunction;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;

class AoC062017 implements Solution {

    private static String solve(final String input, final ToIntBiFunction<Integer, Integer> computeResult) {
        final List<Long> block = toLongList(input);
        final Map<List<Long>, Integer> blocksSeen = new HashMap<>();
        int firstSeen = 0;
        while (blocksSeen.putIfAbsent(block, firstSeen) == null) {
            firstSeen++;
            computeNextBlock(block);
        }
        return itoa(computeResult.applyAsInt(firstSeen, blocksSeen.get(block)));
    }

    private static void computeNextBlock(final List<Long> blocks) {
        final long max = Collections.max(blocks);
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

    public String solveFirstPart(final Stream<String> input) {
        return solve(getFirstString(input), (firstLoop, seen) -> firstLoop);
    }

    public String solveSecondPart(final Stream<String> input) {
        return solve(getFirstString(input), (firstLoop, seen) -> firstLoop - seen);
    }
}
