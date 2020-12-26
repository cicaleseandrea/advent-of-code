package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.toLongList;

import java.math.BigInteger;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC252020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var numbers = toLongList( input );
		final long cardPublicKey = numbers.get( 0 );
		final long doorPublicKey = numbers.get( 1 );
		long value = 1;
		int cardLoopSize = 0;
		while ( value != cardPublicKey ) {
			value = ( value * 7 ) % 20201227;
			cardLoopSize++;
		}

		return BigInteger.valueOf( doorPublicKey )
				.modPow( BigInteger.valueOf( cardLoopSize ), BigInteger.valueOf( 20201227 ) )
				.toString();
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return MERRY_CHRISTMAS;
	}

}
