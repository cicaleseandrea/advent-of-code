package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.itoa;

import java.util.Arrays;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC152020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 2020 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 30000000 );
	}

	private String solve( final Stream<String> input, final int numbers ) {
		final var startingList = Utils.toLongList( Utils.getFirstString( input ) );
		final int startingSize = startingList.size();
		final int[] lastPositions = new int[numbers];
		Arrays.fill( lastPositions, -1 );
		int lastNumber = -1;
		for ( int i = 0; i < startingSize; i++ ) {
			lastNumber = startingList.get( i ).intValue();
			lastPositions[lastNumber] = i;
		}
		for ( int i = startingSize; i < numbers; i++ ) {
			final int lastPosition = lastPositions[lastNumber];
			final int currentPosition = i - 1;
			lastPositions[lastNumber] = currentPosition;
			lastNumber = ( lastPosition == -1 ) ? 0 : currentPosition - lastPosition;
		}
		return itoa( lastNumber );
	}

}
