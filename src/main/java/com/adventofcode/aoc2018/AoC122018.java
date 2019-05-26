package com.adventofcode.aoc2018;

import com.adventofcode.Solution;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.adventofcode.utils.Utils.*;

class AoC122018 implements Solution {

    private static String solve(final List<String> input, final long numGen) {
        final var rules = getRules(input);
        var curr = getFirstGeneration(input);
        int min = 0;
        int max = curr.size();
        long sum = 0;
        long oldDiff = 0;
        long newDiff;
        boolean loop = false;
        for (long i = 0; !loop && i < numGen; i++) {
            final Map<Integer, Character> next = new HashMap<>();
            //compute new pots taking into account all the possible next positions
            min -= 2;
            max += 2;
            for (int j = min; j < max; j++) {
                if (rules.
                        getOrDefault(curr.getOrDefault(j - 2, DOT), Collections.emptyMap()).
                        getOrDefault(curr.getOrDefault(j - 1, DOT), Collections.emptyMap()).
                        getOrDefault(curr.getOrDefault(j, DOT), Collections.emptyMap()).
                        getOrDefault(curr.getOrDefault(j + 1, DOT), Collections.emptyMap()).
                        containsKey(curr.getOrDefault(j + 2, DOT))) {
                    //pot
                    next.put(j, HASH);
                } else {
                    //not
                    next.put(j, DOT);
                }
            }
            newDiff = -sum + next.entrySet().parallelStream().filter(e -> e.getValue() == HASH).
                    mapToInt(Map.Entry::getKey).sum();
            //after a while, check if two generations added the same partial result
            if (i % 200 == 0 && oldDiff == newDiff) {
                loop = true;
                sum += (numGen - i) * newDiff;
            } else {
                sum += newDiff;
            }
            oldDiff = newDiff;
            curr = next;
        }
        return itoa(sum);
    }

    private static Map<Integer, Character> getFirstGeneration(final List<String> input) {
        final Map<Integer, Character> pots = new HashMap<>();
        final String state = splitOnTabOrSpace(input.get(0)).get(2);
        for (int i = 0; i < state.length(); i++) {
            pots.put(i, state.charAt(i));
        }
        return pots;
    }

    private static Map<Character, Map<Character, Map<Character,
            Map<Character, Map<Character, Character>>>>> getRules(final List<String> input) {
        final Map<Character, Map<Character, Map<Character, Map<Character, Map<Character, Character>>>>> rules = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            final List<String> tmp = splitOnTabOrSpace(input.get(i));
            final String in = tmp.get(0);
            final char out = tmp.get(2).charAt(0);
            //add only rules that create a pot
            if (out == HASH) {
                rules.
                        computeIfAbsent(in.charAt(0), k -> new HashMap<>()).
                        computeIfAbsent(in.charAt(1), k -> new HashMap<>()).
                        computeIfAbsent(in.charAt(2), k -> new HashMap<>()).
                        computeIfAbsent(in.charAt(3), k -> new HashMap<>()).
                        putIfAbsent(in.charAt(4), out);
            }
        }
        return rules;
    }

    public String solveFirstPart(final List<String> input) {
        return solve(input, 20L);
    }

    public String solveSecondPart(final List<String> input) {
        return solve(input, 50000000000L);
    }
}