package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;
import static java.util.stream.Collectors.toUnmodifiableList;

class AoC072018 implements Solution {

    private static void setup(final List<String> input, final Map<Character, Set<Character>> edges, final Map<Character,
            Long> in, final Queue<Character> stepsReady, final List<Pair<Character, Integer>> workersReady,
                              final int nWorkers) {
        computeEdges(input, edges, in);
        computeStepsReady(edges, in, stepsReady);
        computeWorkersReady(workersReady, nWorkers);
    }

    private static void computeWorkersReady(final List<Pair<Character, Integer>> workersReady,
                                            final int nWorkers) {
        for (int i = 0; i < nWorkers; i++) {
            workersReady.add(new Pair<>(DOT, 0));
        }
    }

    private static boolean isSolutionInput(final List<String> input) {
        return input.size() > 10;
    }

    private static void computeStepsReady(final Map<Character, Set<Character>> edges, final Map<Character, Long> in,
                                          final Queue<Character> stepsReady) {
        for (final Character k : edges.keySet()) {
            if (!in.containsKey(k)) {
                stepsReady.add(k);
            }
        }
    }

    private static void computeEdges(final List<String> input, final Map<Character, Set<Character>> edges,
                                     final Map<Character, Long> in) {
        for (final String s : input) {
            final List<String> row = splitOnTabOrSpace(s);
            final Character from = row.get(1).charAt(0);
            final Character to = row.get(7).charAt(0);
            edges.computeIfAbsent(from, k -> new HashSet<>()).add(to);
            edges.computeIfAbsent(to, k -> new HashSet<>());
            incrementMapElement(in, to);
        }
    }

    private static String solve(final List<String> input, final int nWorkers,
                                final BinaryOperator<String> computeResult) {
        final Map<Character, Set<Character>> edges = new HashMap<>();
        final Map<Character, Long> in = new HashMap<>();
        final Queue<Character> stepsReady = new PriorityQueue<>();
        final List<Pair<Character, Integer>> workersBusy = new ArrayList<>();
        final List<Pair<Character, Integer>> workersReady = new ArrayList<>();
        setup(input, edges, in, stepsReady, workersReady, nWorkers);

        int steps = -1;
        final StringBuilder tasksOrdered = new StringBuilder();
        while (!workersBusy.isEmpty() || !stepsReady.isEmpty()) {
            steps++;
            performWork(edges, in, stepsReady, workersBusy, workersReady, tasksOrdered);
            distributeWork(input, stepsReady, workersBusy, workersReady);
        }
        return computeResult.apply(tasksOrdered.toString(), itoa(steps));
    }

    private static void distributeWork(final List<String> input, final Queue<Character> stepsReady, final List<Pair<Character, Integer>> workersBusy, final List<Pair<Character, Integer>> workersReady) {
        //give work to available workers
        final var iterator = workersReady.iterator();
        while (iterator.hasNext() && !stepsReady.isEmpty()) {
            final Pair<Character, Integer> w = iterator.next();
            final Character min = stepsReady.remove();
            w.setFirst(min);
            w.setSecond(getTime(min, isSolutionInput(input)));
            //worker is busy
            iterator.remove();
            workersBusy.add(w);
        }
    }

    private static void performWork(final Map<Character, Set<Character>> edges, final Map<Character, Long> in, final Queue<Character> stepsReady, final List<Pair<Character, Integer>> workersBusy, final List<Pair<Character, Integer>> workersReady, final StringBuilder tasksOrdered) {
        final var iterator = workersBusy.iterator();
        while (iterator.hasNext()) {
            final Pair<Character, Integer> w = iterator.next();
            w.setSecond(w.getSecond() - 1); //work on it
            if (w.getSecond() == 0) {
                //task completed
                final Character completed = w.getFirst();
                tasksOrdered.append(completed);
                for (final Character to : edges.get(completed)) {
                    if (decrementMapElement(in, to) == 0) {
                        //no remaining dependencies
                        stepsReady.add(to);
                    }
                }
                //worker is available
                iterator.remove();
                workersReady.add(w);
            }
        }
    }

    private static int getTime(final char c, boolean isSolutionInput) {
        //'A'==1, 'B'==2, etc...
        return (c - AT) + (isSolutionInput ? 60 : 0);
    }

    public String solveFirstPart(final Stream<String> input) {
        return solve(input.collect(toUnmodifiableList()), 1, (tasks, steps) -> tasks);
    }

    public String solveSecondPart(final Stream<String> input) {
        final List<String> list = input.collect(toUnmodifiableList());
        return solve(list, isSolutionInput(list) ? 5 : 2, (tasks, steps) -> steps);
    }
}
