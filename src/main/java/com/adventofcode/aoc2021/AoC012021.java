package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC012021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 1 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 3 );
	}

	private String solve( final Stream<String> input, final int windowSize ) {
		final var measurements = toLongList( input );
		int increased = 0;
		for ( int i = windowSize; i < measurements.size(); i++ ) {
			if ( measurements.get( i ) > measurements.get( i - windowSize ) ) {
				increased++;
			}
		}
		return itoa( increased );
	}
}
