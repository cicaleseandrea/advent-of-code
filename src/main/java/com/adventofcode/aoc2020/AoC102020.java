package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.itoa;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC102020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var list = Utils.toLongList( input );
		Collections.sort( list );
		list.add( 0, 0L );
		list.add( list.get( list.size() - 1 ) + 3 );
		final Map<Long, Long> diffs = new HashMap<>();
		for ( int i = 1; i < list.size(); i++ ) {
			final long diff = list.get( i ) - list.get( i - 1 );
			diffs.merge( diff, 1L, Long::sum );
		}
		return itoa( diffs.get( 1L ) * diffs.get( 3L ) );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {

		return itoa( 0 );
	}

}
