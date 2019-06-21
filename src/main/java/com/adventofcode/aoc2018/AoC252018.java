package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;

class AoC252018 implements Solution {

    @Override
    public String solveFirstPart(final Stream<String> input) {
        final Set<Pair<Pair<Long, Long>, Pair<Long, Long>>> points =
                input.map(row -> row.split(","))
                        .map(tmp -> new Pair<>(
                                new Pair<>(atol(tmp[0]), atol(tmp[1])),
                                new Pair<>(atol(tmp[2]), atol(tmp[3]))))
                        .collect(Collectors.toUnmodifiableSet());
        final Map<Pair<Pair<Long, Long>, Pair<Long, Long>>,
                Set<Pair<Pair<Long, Long>, Pair<Long, Long>>>> starMapping = new HashMap<>();
        long res = 0L;
        for (final var one : points) {
            for (final var two : points) {
                final long dist = manhattanDistance(one.getFirst(), two.getFirst()) +
                        manhattanDistance(one.getSecond(), two.getSecond());
                if (dist <= 3) {
                    var constellationOneOpt = Optional.ofNullable(starMapping.get(one));
                    var constellationTwoOpt = Optional.ofNullable(starMapping.get(two));
                    if (constellationOneOpt.isEmpty() && constellationTwoOpt.isEmpty()) {
                        //new constellation
                        res++;
                        constellationOneOpt = Optional.of(new HashSet<>());
                    } else if (constellationOneOpt.isEmpty()) {
                        //existing constellation
                        constellationOneOpt = constellationTwoOpt;
                    } else if (constellationTwoOpt.isPresent() && !constellationOneOpt.equals(constellationTwoOpt)) {
                        //merge two constellations into one
                        res--;
                        for (final var star : constellationTwoOpt.get()) {
                            constellationOneOpt.ifPresent(constellationOne -> {
                                //add this stars to the constellation
                                constellationOne.add(star);
                                //this star now belongs to this constellation
                                starMapping.put(star, constellationOne);
                            });
                        }
                    }
                    constellationOneOpt.ifPresent(constellationOne -> {
                        constellationOne.add(one);
                        starMapping.put(one, constellationOne);
                        constellationOne.add(two);
                        starMapping.put(two, constellationOne);
                    });
                }
            }
        }
        return itoa(res);
    }

    @Override
    public String solveSecondPart(final Stream<String> input) {
        return MERRY_CHRISTMAS;
    }

}