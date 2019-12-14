package com.adventofcode.aoc2019;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;

class AoC142019 implements Solution {
    public String solveFirstPart(final Stream<String> input) {
        return solve(input, true);
    }

    public String solveSecondPart(final Stream<String> input) {
        return solve(input, false);
    }

    private String solve(final Stream<String> input, final boolean first) {
        final Multimap<String, Pair<String, Long>> invertedReactions = HashMultimap.create();
        final Map<String, Long> reactionsNumber = new HashMap<>();
        final Map<String, Long> unitsCreated = new HashMap<>();

        initialize(input, invertedReactions, reactionsNumber, unitsCreated);

        final long orePerFuel = computeOre(invertedReactions, new HashMap<>(reactionsNumber), unitsCreated, 1L);

        if (first) {
            return itoa(orePerFuel);
        } else {
            //lazy solution... binary search
            final long oreAvailable = 1000000000000L;
            long minFuel = oreAvailable / orePerFuel;
            long maxFuel = minFuel * 2;
            while (maxFuel - minFuel > 1) {
                final long midFuel = (minFuel + maxFuel) / 2;
                final long oreNeeded = computeOre(invertedReactions, new HashMap<>(reactionsNumber), unitsCreated, midFuel);
                if (oreNeeded < oreAvailable) {
                    minFuel = midFuel;
                } else {
                    maxFuel = midFuel;
                }
            }
            return itoa(minFuel);
        }
    }

    private void initialize(final Stream<String> input, final Multimap<String, Pair<String, Long>> invertedReactions, final Map<String, Long> reactionsNumber, final Map<String, Long> unitsCreated) {
        input.forEach(line -> {
            final List<String> words = toWordList(line);
            final List<Long> numbers = toPositiveLongList(line);
            final String outputChemicalId = words.get(numbers.size() - 1);
            unitsCreated.put(outputChemicalId, numbers.get(numbers.size() - 1));
            for (int i = 0; i < numbers.size() - 1; i++) {
                final Pair<String, Long> inputChemical = new Pair<>(words.get(i), numbers.get(i));
                invertedReactions.put(outputChemicalId, inputChemical);
                incrementMapElement(reactionsNumber, inputChemical.getFirst());
            }
        });
    }

    private long computeOre(final Multimap<String, Pair<String, Long>> invertedReactions, final Map<String, Long> reactionsNumber, final Map<String, Long> unitsCreated, final long fuelUnits) {
        final Map<String, Long> unitsNeeded = new HashMap<>();
        unitsNeeded.put("FUEL", fuelUnits);

        final Deque<String> readyToReact = new LinkedList<>();
        readyToReact.add("FUEL");

        while (!readyToReact.isEmpty()) {
            final String outputChemicalId = readyToReact.pop();
            for (final Pair<String, Long> inputChemical : invertedReactions.get(outputChemicalId)) {
                final long outputUnitsNeeded = unitsNeeded.get(outputChemicalId);
                final long outputUnitsCreated = unitsCreated.get(outputChemicalId);
                final long inputChemicalNeeded = (outputUnitsNeeded / outputUnitsCreated) + (outputUnitsNeeded % outputUnitsCreated == 0 ? 0 : 1);
                unitsNeeded.merge(inputChemical.getFirst(), inputChemical.getSecond() * inputChemicalNeeded, Long::sum);
                if (decrementMapElement(reactionsNumber, inputChemical.getFirst()) == 0) {
                    readyToReact.add(inputChemical.getFirst());
                }
            }
        }

        return unitsNeeded.get("ORE");
    }

}
