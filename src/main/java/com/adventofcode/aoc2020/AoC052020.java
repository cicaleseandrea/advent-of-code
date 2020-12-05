package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.itoa;

import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC052020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return itoa( input.mapToInt( AoC052020::decodeSeat ).max().orElseThrow() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var seats = input.mapToInt( AoC052020::decodeSeat ).sorted().iterator();
		int prev = seats.nextInt();
		while ( seats.hasNext() ) {
			final int curr = seats.nextInt();
			if ( curr - prev > 1 ) {
				return itoa( curr - 1L );
			}
			prev = curr;
		}
		throw new IllegalArgumentException();
	}

	private static Integer decodeSeat( final String seat ) {
		final String binaryRow = seat.substring( 0, 7 ).replace( "F", "0" ).replace( "B", "1" );
		final String binaryColumn = seat.substring( 7 ).replace( "L", "0" ).replace( "R", "1" );
		final int row = Integer.parseInt( binaryRow, 2 );
		final int column = Integer.parseInt( binaryColumn, 2 );
		return row * 8 + column;
	}

}
