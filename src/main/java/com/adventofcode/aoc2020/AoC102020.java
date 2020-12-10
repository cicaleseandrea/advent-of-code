package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.itoa;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC102020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final var list = initializeList( input );
		long ones = 0;
		long threes = 0;
		int windowLength = 0;
		long totalArrangements = 1L;
		for ( int i = 1; i < list.size(); i++ ) {
			windowLength++;
			final long diff = list.get( i ) - list.get( i - 1 );
			switch ( (int) diff ) {
			case 1 -> ones++;
			case 3 -> {
				threes++;
				totalArrangements *= computeArrangements( windowLength );
				windowLength = 0;
			}
			default -> throw new IllegalStateException( "Unexpected value: " + diff );
			}
		}
		if ( first ) {
			return itoa( ones * threes );
		} else {
			return itoa( totalArrangements );
		}
	}

	private double computeArrangements( final int windowLength ) {
		return switch ( windowLength ) {
			case 1, 2 -> 1;
			case 3 -> 2;
			case 4 -> 4;
			case 5 -> 7;
			default -> throw new IllegalStateException( "Unexpected value: " + windowLength );
		};
	}

	private List<Long> initializeList( final Stream<String> input ) {
		final var list = Utils.toLongList( input );
		Collections.sort( list );
		list.add( 0, 0L );
		list.add( list.get( list.size() - 1 ) + 3 );
		return list;
	}

}
