package com.adventofcode.aoc2018;

import static java.lang.Character.toUpperCase;

import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.upperCaseLetters;

import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC052018 implements Solution {

    private static int react(final String input, char remove) {
        final Deque<Character> res = new LinkedList<>();
        for (final char curr : input.toCharArray()) {
            if (remove != toUpperCase(curr)) {
                if (!res.isEmpty() && sameTypeDifferentPolarity(curr, res.element())) {
                    res.pop(); //curr reacts with the previous character
                } else {
                    res.push(curr); //curr can be added to the solution
                }
            }
            // else skip this character
        }
        return res.size();
    }

    private static boolean sameTypeDifferentPolarity(final char a, final char b) {
        return (a ^ b) == 32;
    }

    public String solveFirstPart(final Stream<String> input) {
        return itoa(react(getFirstString(input), SPACE));
    }

    public String solveSecondPart(final Stream<String> input) {
        final String str = getFirstString(input);
        int res = upperCaseLetters().mapToInt( remove -> react( str, remove ) ).min().orElseThrow();
        return itoa(res);
    }
}
