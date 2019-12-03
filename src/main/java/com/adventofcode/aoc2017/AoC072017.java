package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.EMPTY;
import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.getIterable;
import static com.adventofcode.utils.Utils.incrementMapElement;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnTabOrSpace;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Node;
import com.adventofcode.utils.Pair;

class AoC072017 implements Solution {
    private static Node<Pair<String, Long>> buildTree(final Stream<String> input) {
        var root = new Node<>(new Pair<>(EMPTY, 0L));
        final Map<String, Node<Pair<String, Long>>> programsSeen = new HashMap<>();
        for (final String s : getIterable(input)) {
            var n = parseRow(s, programsSeen).getParent();
            while (n.isPresent()) {
                root = n.get();
                n = root.getParent();
            }
        }
        return root;
    }

    private static Node<Pair<String, Long>> parseRow(final String row, final Map<String, Node<Pair<String, Long>>> programsSeen) {
        final List<String> strings = splitOnTabOrSpace(row);
        final String name = strings.get(0);
        final long weight = atol(strings.get(1).replaceAll("[()]", EMPTY));

        final var n = programsSeen.computeIfAbsent(name, k -> new Node<>(new Pair<>(k, weight)));
        n.getKey().setSecond(weight);

        var child = n;
        for (int i = 3; i < strings.size(); i++) {
            final String childName = strings.get(i).replace(",", EMPTY);
            child = programsSeen.computeIfAbsent(childName, k -> new Node<>(new Pair<>(k, 0L)));
            n.addChild(child);
        }
        return child;
    }

    public String solveFirstPart(final Stream<String> input) {
        final Set<String> programsSeen = new HashSet<>();
        final Set<String> children = new HashSet<>();
        for (final String s : getIterable(input)) {
            final List<String> strings = splitOnTabOrSpace(s);
            final String name = strings.get(0);
            programsSeen.add(name);
            if (strings.indexOf("->") >= 0) {
                for (int i = 3; i < strings.size(); i++) {
                    final String child = strings.get(i).replace(",", EMPTY);
                    children.add(child);
                }
            }
        }
        programsSeen.removeAll(children);
        return programsSeen.stream().findFirst().orElseThrow();
    }

    public String solveSecondPart(final Stream<String> input) {
        final var root = buildTree(input);
        final long res = -computeBalancedSum(root);
        return itoa(res);
    }

    private long computeBalancedSum(final Node<Pair<String, Long>> root) {
        long sum = root.getKey().getSecond(); //weight
        final Map<Long, Node<Pair<String, Long>>> childrenSums = new HashMap<>();
        final Map<Long, Long> frequencies = new HashMap<>();
        for (final var child : root.getChildren()) {
            final long childSum = computeBalancedSum(child);
            //propagate immediately negative result
            if (childSum < 0) {
                return childSum;
            }
            childrenSums.put(childSum, child);
            incrementMapElement(frequencies, childSum);
            sum += childSum;
        }
        //empty or all equal
        if (frequencies.size() <= 1) {
            return sum;
        }

        long right = frequencies.entrySet()
                .parallelStream()
                .filter( e -> e.getValue() != 1 )
                .map( Map.Entry::getKey )
                .findAny()
                .orElseThrow();
        long wrong = frequencies.entrySet()
                .parallelStream()
                .filter( e -> e.getValue() == 1 )
                .map( Map.Entry::getKey )
                .findAny()
                .orElseThrow();

        final var wrongNode = childrenSums.get(wrong);
        return wrong - right - wrongNode.getKey().getSecond();
    }
}