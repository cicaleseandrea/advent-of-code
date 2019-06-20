package com.adventofcode.aoc2018;

import com.adventofcode.Solution;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static com.adventofcode.utils.Utils.*;
import static java.lang.Character.toUpperCase;

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

    public String solveFirstPart(final List<String> input) {
        return itoa(react(input.get(0), SPACE));
    }

    public String solveSecondPart(final List<String> input) {
        final String str = input.get(0);
        int res = upperCaseLetters().mapToInt(remove -> react(str, remove)).min().orElse(0);
        return itoa(res);
    }
}