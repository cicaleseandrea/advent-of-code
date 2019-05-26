package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.function.LongPredicate;

import static com.adventofcode.utils.Pair.ZERO;
import static com.adventofcode.utils.Utils.*;
import static java.util.Collections.singleton;

class AoC202018 implements Solution {

    private static final boolean PRINT = false;

    private static String solve(final String input, final LongPredicate filter,
                                final Function<LongSummaryStatistics, Long> computeResult) {
        final Map<Pair<Long, Long>, Character> map = new HashMap<>();
        map.put(ZERO, DOT);
        branch(map, singleton(ZERO), input, 1);
        if (PRINT) {
            print(map);
        }
        return itoa(computeResult(map, filter, computeResult));
    }

    private static Pair<Set<Pair<Long, Long>>, Integer> branch(final Map<Pair<Long, Long>, Character> map,
                                                               final Set<Pair<Long, Long>> startPos,
                                                               final String input, int i) {
        final Set<Pair<Long, Long>> endPos = new HashSet<>();
        var curr = startPos;
        boolean exit = false;
        while (!exit) {
            final char c = input.charAt(i);
            //move index to next character
            i++;
            if (c == '(') {
                //follow an entire new branch
                final var res = branch(map, curr, input, i);
                //continue moving after the branch
                curr = res.getFirst();
                i = res.getSecond();
            } else if (isEndOfPath(c)) {
                //save the result of this path
                endPos.addAll(curr);
                if (c == PIPE) {
                    //compute an alternative path from the starting position
                    curr = startPos;
                } else {
                    //exit from this branch
                    exit = true;
                }
            } else {
                //move in another room
                curr = move(map, curr, c);
            }
        }
        //return end positions and index to next character
        return new Pair<>(endPos, i);
    }

    private static Number computeResult(final Map<Pair<Long, Long>, Character> map, final LongPredicate filter,
                                        final Function<LongSummaryStatistics, Long> computeResult) {
        //BFS to find shortest path to every room (unweighted graphs, no need for Dijkstra)
        final Queue<Pair<Pair<Long, Long>, Long>> queue = new LinkedList<>();
        final Set<Pair<Long, Long>> seen = new HashSet<>();
        //save all distances
        final List<Long> distances = new ArrayList<>();
        //start from source
        queue.add(new Pair<>(ZERO, 0L));
        while (!queue.isEmpty()) {
            //remove current node
            final var curr = queue.remove();
            final long newDistance = curr.getSecond() + 1;
            final Pair<Long, Long> pos = curr.getFirst();
            seen.add(pos);
            for (final Pair<Long, Long> n : computeNeighbours(pos, map)) {
                //first time you see a node, store its distance
                if (!seen.contains(n)) {
                    //add n to the queue
                    queue.add(new Pair<>(n, newDistance));
                    //store distance of n
                    distances.add(newDistance);
                }
            }
        }
        final LongSummaryStatistics stats = distances.stream().mapToLong(Long::longValue).filter(filter).summaryStatistics();
        return computeResult.apply(stats);
    }

    private static List<Pair<Long, Long>> computeNeighbours(final Pair<Long, Long> pos,
                                                            final Map<Pair<Long, Long>, Character> map) {
        final List<Pair<Long, Long>> neighbours = new ArrayList<>();
        final long x = pos.getFirst();
        final long y = pos.getSecond();
        //add all the adjacent rooms that can be reached through a door
        if (isADoor(map, x, y + 1)) {
            neighbours.add(new Pair<>(x, y + 2));
        }
        if (isADoor(map, x, y - 1)) {
            neighbours.add(new Pair<>(x, y - 2));
        }
        if (isADoor(map, x + 1, y)) {
            neighbours.add(new Pair<>(x + 2, y));
        }
        if (isADoor(map, x - 1, y)) {
            neighbours.add(new Pair<>(x - 2, y));
        }
        return neighbours;
    }

    private static boolean isADoor(final Map<Pair<Long, Long>, Character> map, final long x, final long y) {
        return map.getOrDefault(new Pair<>(x, y), HASH) == PIPE;
    }

    private static boolean isEndOfPath(final char c) {
        return c == ')' || c == '$' || c == PIPE;
    }

    private static Set<Pair<Long, Long>> move(final Map<Pair<Long, Long>, Character> map,
                                              final Set<Pair<Long, Long>> enter, final char c) {
        final Set<Pair<Long, Long>> exit = new HashSet<>();
        for (final Pair<Long, Long> e : enter) {
            final long x = e.getFirst();
            final long y = e.getSecond();
            final Pair<Long, Long> door;
            final Pair<Long, Long> room;
            switch (c) {
                case 'N':
                    door = new Pair<>(x, y - 1);
                    room = new Pair<>(x, y - 2);
                    break;
                case 'S':
                    door = new Pair<>(x, y + 1);
                    room = new Pair<>(x, y + 2);
                    break;
                case 'W':
                    door = new Pair<>(x - 1, y);
                    room = new Pair<>(x - 2, y);
                    break;
                case 'E':
                    door = new Pair<>(x + 1, y);
                    room = new Pair<>(x + 2, y);
                    break;
                default:
                    throw new IllegalStateException(c + " is not a direction");
            }
            map.put(door, PIPE);
            map.put(room, DOT);
            exit.add(room);
        }
        return exit;
    }

    private static void print(final Map<Pair<Long, Long>, Character> map
    ) {
        final long minX = -8;
        final long minY = -8;
        final long maxX = 8;
        final long maxY = 8;
        for (long y = minY; y <= maxY; y++) {
            for (long x = minX; x <= maxX; x++) {
                System.out.print(map.getOrDefault(new Pair<>(x, y), HASH));
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public String solveFirstPart(final List<String> input) {
        return solve(input.get(0), i -> true, LongSummaryStatistics::getMax);
    }

    @Override
    public String solveSecondPart(final List<String> input) {
        return solve(input.get(0), i -> i >= 1000, LongSummaryStatistics::getCount);
    }
}