package com.adventofcode.aoc2018;

import com.adventofcode.Solution;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static com.adventofcode.utils.Utils.*;

class AoC092018 implements Solution {

    private static String solve(final List<String> input, final long multiplier) {
        final List<String> sentence = splitOnTabOrSpace(input.get(0));
        final int players = atoi(sentence.get(0));
        final long[] scores = new long[players];
        final long nMarbles = atol(sentence.get(6)) * multiplier;
        final List<Long> marbles = new LinkedList<>();
        int currentPlayer = -1;
        ListIterator<Long> it = marbles.listIterator();
        for (long i = 0; i <= nMarbles; i++) {
            if (i > 0 && i % 23 == 0) {
                //new current position
                for (int j = 0; j < 7; j++) {
                    it = prev(marbles, it);
                }
                //update score
                scores[currentPlayer] += i + it.previous();
                //remove marble
                it.remove();
                it = next(marbles, it);
            } else {
                //new current position
                it = next(marbles, it);
                //insert marble
                it.add(i);
            }
            currentPlayer = incrementMod(currentPlayer, players);
        }
        return itoa(Arrays.stream(scores).max().orElse(0));
    }

    private static ListIterator<Long> prev(final List<Long> marbles, ListIterator<Long> it) {
        if (!it.hasPrevious()) {
            it = marbles.listIterator(marbles.size());
        }
        if (it.hasPrevious()) {
            it.previous();
        }
        return it;
    }

    private static ListIterator<Long> next(final List<Long> marbles, ListIterator<Long> it) {
        if (!it.hasNext()) {
            it = marbles.listIterator();
        }
        if (it.hasNext()) {
            it.next();
        }
        return it;
    }

    public String solveFirstPart(final List<String> input) {
        return solve(input, 1L);
    }

    public String solveSecondPart(final List<String> input) {
        return solve(input, 100L);
    }
}