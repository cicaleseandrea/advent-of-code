package com.adventofcode.aoc2017;


import com.adventofcode.Solution;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.concat;


class AoC102017 implements Solution {

    private static final int SIXTEEN = 16;

    private static String solve(final int[] input, final boolean first) {
        final List<Integer> sparseHash = IntStream.range(0, getInitialLength(input, first)).boxed().collect(toList());
        int skip = 0;
        int pos = 0;
        for (int i = 0; i < (first ? 1 : 64); i++) {
            for (int length : input) {
                rotate(sparseHash, pos, length);
                pos = incrementMod(pos, length + skip++, sparseHash.size());
            }
        }

        if (first) {
            return itoa(sparseHash.get(0) * sparseHash.get(1));
        } else {
            final ArrayList<Integer> denseHash = new ArrayList<>();
            int sparseIndex = 0;
            for (int i = 0; i < SIXTEEN; i++) {
                int hash = 0;
                for (int j = 0; j < SIXTEEN; j++) {
                    hash ^= sparseHash.get(sparseIndex++);
                }
                denseHash.add(hash);
            }
            return denseHash.stream().map(Integer::toHexString).map(AoC102017::padHex).collect(joining());
        }
    }

    private static int getInitialLength(int[] input, boolean first) {
        return first && input.length < 10 ? 5 : 256;
    }

    private static void rotate(List<Integer> list, int startPos, int length) {
        int i = startPos;
        int j = incrementMod(startPos, length - 1, list.size());
        for (int k = 0; k < length / 2; k++) {
            Collections.swap(list, i, j);
            if (j == 0) {
                j = list.size();
            }
            j--;
            i = incrementMod(i, list.size());
        }
    }

    private static String padHex(String s) {
        return Strings.padStart(s, 2, '0');
    }

    public String solveFirstPart(final Stream<String> input) {
        return solve(toLongStream(getFirstString(input)).mapToInt(Long::intValue).toArray(), true);
    }

    public String solveSecondPart(final Stream<String> input) {
        return solve(concat(getFirstString(input).chars(), IntStream.of(17, 31, 73, 47, 23)).toArray(), false);
    }

}