package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC012020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final var entries = toLongList( input );
		final int length = entries.size();
		for ( int i = 0; i < length; i++ ) {
			final long a = entries.get( i );
			for ( int j = i + 1; j < length; j++ ) {
				final long b = entries.get( j );
				if ( first && a + b == 2020 ) {
					return itoa( a * b );
				}
				for ( int k = j + 1; k < length; k++ ) {
					final long c = entries.get( k );
					if ( a + b + c == 2020 ) {
						return itoa( a * b * c );
					}
				}
			}
		}
		throw new IllegalArgumentException();
	}
}
