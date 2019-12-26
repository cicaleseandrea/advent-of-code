package com.adventofcode.aoc2018;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.toUnmodifiableList;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.PIPE;
import static com.adventofcode.utils.Utils.clearScreen;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC182018 implements Solution {

	private static final boolean PRINT = Boolean.parseBoolean( System.getProperty( "print" ) );

    private static String solve(final Stream<String> input, long maxIter) {
        var curr = getInitialState(input);
        final Map<List<List<Character>>, Long> seen = new HashMap<>();
        Optional<Long> prev = Optional.empty();
        long iter = 0;
        //stop when you have a cycle or computed all generations
        while (prev.isEmpty() && ++iter <= maxIter) {
			print( curr );
            final List<List<Character>> next = new ArrayList<>();
            for (int i = 0; i < curr.size(); i++) {
                final ArrayList<Character> tmp = new ArrayList<>();
                for (int j = 0; j < curr.get(0).size(); j++) {
                    //compute cell for next generation
                    tmp.add(computeCell(curr, i, j));
                }
                //store next generation
                next.add(tmp);
            }
            curr = next;
            //check if we have a cycle
            prev = Optional.ofNullable(seen.put(next, iter));
        }
        var res = curr;
        if (prev.isPresent()) {
            //in case of cycle compute the result
            final long left = maxIter - iter;
            final long cycleLength = iter - prev.get();
            for (final var e : seen.entrySet()) {
                if (e.getValue() == prev.get() + (left % cycleLength)) {
                    return itoa(computeResult(e.getKey()));
                }
            }
        }
        return itoa(computeResult(res));
    }

    private static List<List<Character>> getInitialState(final Stream<String> input) {
        return input.map(row -> row.chars().mapToObj(c -> (char) c).collect(toUnmodifiableList())).collect(toUnmodifiableList());
    }

    private static long computeResult(final List<List<Character>> curr) {
        final Map<Character, Long> res = curr.parallelStream().flatMap(Collection::stream).collect(groupingByConcurrent(identity(), counting()));
        return res.get(Utils.HASH) * res.get(PIPE);
    }

    private static Character computeCell(final List<List<Character>> curr, final int i, final int j) {
        final Character c = curr.get(i).get(j);
        int countHash = 0;
        int countPipe = 0;
        for (int x = Math.max(0, i - 1); x <= Math.min(curr.size() - 1, i + 1); x++) {
            for (int y = Math.max(0, j - 1); y <= Math.min(curr.get(0).size() - 1, j + 1); y++) {
                if (x == i && j == y) continue; //skip this cell
                final Character tmp = curr.get(x).get(y);
                if (tmp == HASH) countHash++;
                else if (tmp == PIPE) countPipe++;
            }
        }
        return switch (c) {
            case DOT -> countPipe >= 3 ? PIPE : DOT;
            case PIPE -> countHash >= 3 ? HASH : PIPE;
            case HASH -> countHash >= 1 && countPipe >= 1 ? HASH : DOT;
            default -> c;
        };
    }

	private static void print( final List<List<Character>> curr ) {
		if ( PRINT ) {
			try {
				clearScreen();
				Thread.sleep( 100 );
				for ( final var row : curr ) {
					for ( final var cell : row ) {
						System.out.print( cell );
					}
					System.out.println();
				}
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}
	}

    @Override
    public String solveFirstPart(final Stream<String> input) {
        return solve(input, 10L);
    }

    @Override
    public String solveSecondPart(final Stream<String> input) {
        return solve(input, 1000000000L);
    }
}