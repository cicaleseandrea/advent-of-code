package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.itoa;

import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC102015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 40 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 50 );
	}

	@NotNull
	private String solve( final Stream<String> input, final int iterations ) {
		StringBuilder sequence = new StringBuilder( Utils.getFirstString( input ) );
		for ( int i = 0; i < ( sequence.length() < 10 ? 1 : iterations ); i++ ) {
			sequence = getNextSequence( sequence );
		}
		return itoa( sequence.length() );
	}

	private static StringBuilder getNextSequence( StringBuilder sequence ) {
		final StringBuilder nextSequence = new StringBuilder();
		int count = 0;
		for ( int j = 0; j < sequence.length(); j++ ) {
			count++;
			final char currChar = sequence.charAt( j );
			final char nextChar = j + 1 < sequence.length() ? sequence.charAt( j + 1 ) : SPACE;
			if ( currChar != nextChar ) {
				nextSequence.append( count ).append( currChar );
				count = 0;
			}
		}
		return nextSequence;
	}

}
