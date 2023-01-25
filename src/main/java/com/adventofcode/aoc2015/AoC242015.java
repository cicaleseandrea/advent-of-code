package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.stream.Stream;

class AoC242015 implements Solution {

	@Override
	public String solveFirstPart(final Stream<String> input) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart(final Stream<String> input) {
		return solve( input, false );
	}

	private String solve(final Stream<String> input, final boolean first) {
		final var packages = input.map( Utils::atoi ).collect( toSet() );
		final var groupSize = getGroupSize( packages ) / (first ? 3 : 4);

		var min = Long.MAX_VALUE;
		for ( int i = 0; min == Long.MAX_VALUE; i++ ) {
			for ( final var group : Sets.combinations( packages, i ) ) {
				if ( getGroupSize( group ) == groupSize ) {
					min = Math.min( min, getQuantumEntanglement( group ) );
				}
			}
		}
		return itoa( min );
	}

	private int getGroupSize(final Collection<Integer> group) {
		return group.stream().mapToInt( Integer::intValue ).sum();
	}

	private long getQuantumEntanglement(final Collection<Integer> group) {
		return group.stream().mapToLong( Integer::intValue ).reduce( 1, (a, b) -> a * b );
	}
}
