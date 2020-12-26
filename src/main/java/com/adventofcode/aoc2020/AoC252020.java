package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC252020 implements Solution {

	private static final int MOD = 20201227;

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var numbers = toLongList( input );
		final long cardPublicKey = numbers.get( 0 );
		final long doorPublicKey = numbers.get( 1 );
		long value = 1;
		long encryptionKey = 1;
		while ( value != cardPublicKey ) {
			value = ( value * 7 ) % MOD;
			encryptionKey = ( encryptionKey * doorPublicKey ) % MOD;
		}
		return itoa( encryptionKey );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return MERRY_CHRISTMAS;
	}

}
