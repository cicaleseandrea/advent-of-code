package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.itoa;

import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC012019 implements Solution {
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final long res = input.mapToLong( Utils::atol ).map( l -> computeFuel( l, first ) ).sum();
		return itoa( res );
	}

	private long computeFuel( long mass, final boolean first ) {
		long res = 0;
		do {
			mass = ( mass / 3 ) - 2;
			res += Math.max( 0, mass );
		} while ( mass >= 0 && !first );
		return res;
	}
}
