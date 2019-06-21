package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Triplet;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

import static com.adventofcode.utils.Triplet.ZERO;
import static com.adventofcode.utils.Utils.*;

class AoC232018 implements Solution {

    private static String solve(final Stream<String> input, final boolean first) {
        final SortedMap<Long, Integer> map = new TreeMap<>();
        int maxRadius = 0;
        Triplet<Long, Long, Long> maxNanobot = ZERO;
        final Map<Triplet<Long, Long, Long>, Integer> nanobots = new HashMap<>();
        for (final String row : getIterable(input)) {
            final Triplet<Long, Long, Long> pos = new Triplet<>(0L, 0L, 0L);
            int radius = extractInfo(row, pos);
            nanobots.put(pos, radius);
            if (radius > maxRadius) {
                maxRadius = radius;
                maxNanobot = pos;
            }
            final long distance = manhattanDistance(ZERO, pos);
            //manhattan distance of the first point that is contained in this nanobot radius
            map.merge(Math.max(0, distance - radius), 1, Integer::sum);
            //manhattan distance of the first point that is not contained in this nanobot radius
            map.merge(distance + radius + 1, -1, Integer::sum);
        }
        if (first) {
            return itoa(computeResult(maxRadius, maxNanobot, nanobots));
        } else {
            int count = 0;
            int maxCount = 0;
            long res = 0;
            for (final Map.Entry<Long, Integer> e : map.entrySet()) {
                //count how many nanobots overlap
                count += e.getValue();
                if (count > maxCount) {
                    maxCount = count;
                    //store the manhattan distance of the first point that overlaps with most nanobots
                    res = e.getKey();
                }
            }
            return itoa(res);
        }
    }

    private static int extractInfo(final String row, final Triplet<Long, Long, Long> pos) {
        final String[] tmp = row.split("[<>,=]");
        //set position
        pos.setFirst(atol(tmp[2]));
        pos.setSecond(atol(tmp[3]));
        pos.setThird(atol(tmp[4]));
        //return radius
        return atoi(tmp[7]);
    }

    private static long computeResult(final int radius, final Triplet<Long, Long, Long> nanobot,
                                      final Map<Triplet<Long, Long, Long>, Integer> nanobots) {
        //count how many nanobots are in range
        return nanobots.keySet().parallelStream().filter(n -> manhattanDistance(nanobot, n) <= radius).count();
    }

    @Override
    public String solveFirstPart(final Stream<String> input) {
        return solve(input, true);
    }

    @Override
    public String solveSecondPart(final Stream<String> input) {
        /*
        "Unicorn Magic" solution from here:
        https://www.reddit.com/r/adventofcode/comments/a8s17l/2018_day_23_solutions/ecdqzdg/
        It does not work with all possible inputs
        */
        return solve(input, false);
    }
}