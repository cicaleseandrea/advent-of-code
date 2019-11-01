package com.adventofcode.aoc2017;

import com.adventofcode.Solution;

import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;

class AoC092017 implements Solution {

    private static String solve(final String input, boolean first) {
        boolean deleteThisChar = false;
        boolean isGarbage = false;
        long garbageCount = 0L;
        long currentGroupScore = 0L;
        long totalGroupScore = 0L;
        for (final char c : input.toCharArray()) {
            if (deleteThisChar || deleteNextChar(c)) {
                //if delete this char, flip back to false
                //if delete next char, flip to true
                deleteThisChar = !deleteThisChar;
            } else if (isGarbage && (isGarbage = !endGarbage(c))) {
                //inside garbage
                garbageCount++;
            } else if (startGarbage(c)) {
                isGarbage = true;
            } else if (startGroup(c)) {
                currentGroupScore++;
            } else if (endGroup(c)) {
                totalGroupScore += currentGroupScore--;
            }
        }
        return first ? itoa(totalGroupScore) : itoa(garbageCount);
    }

    private static boolean startGroup(char c) {
        return c == '{';
    }

    private static boolean endGroup(char c) {
        return c == '}';
    }

    private static boolean startGarbage(char c) {
        return c == '<';
    }

    private static boolean endGarbage(char c) {
        return c == '>';
    }

    private static boolean deleteNextChar(char c) {
        return c == '!';
    }

    public String solveFirstPart(final Stream<String> input) {
        return solve(getFirstString(input), true);
    }

    public String solveSecondPart(final Stream<String> input) {
        return solve(getFirstString(input), false);
    }

}