package com.adventofcode.aoc2016;

import java.nio.charset.StandardCharsets;
import java.util.function.BiPredicate;
import java.util.function.IntBinaryOperator;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.hash.Hashing;

class AoC052016 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, ( character, password ) -> 0 <= character && character < 16,
				bytes -> (int) bytes[2], ( length, character ) -> length );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input,
				( position, password ) -> 0 <= position && position < 8 && password[position] == 0,
				bytes -> {
					int character = bytes[3];
					// remove the last 4 bits (the eighth character)
					character = character >>> 4;
					// keep only the last 4 bits (the seventh character)
					character = character & 0xF;
					return character;
				}, ( length, character ) -> character );
	}

	private String solve( final Stream<String> input,
			final BiPredicate<Integer, char[]> acceptCharacter,
			final ToIntFunction<byte[]> getCharacter, final IntBinaryOperator getPosition ) {
		final String id = Utils.getFirstString( input );
		final char[] password = new char[8];
		int length = 0;
		for ( long i = 0; length < 8; i++ ) {
			// each byte corresponds to 2 hexadecimal characters
			final var bytes = computeHash( id + i );
			final int character = bytes[2];
			if ( bytes[0] == 0 && bytes[1] == 0 && acceptCharacter.test( character, password ) ) {
				final int position = getPosition.applyAsInt( length, character );
				password[position] = Integer.toHexString( getCharacter.applyAsInt( bytes ) )
						.charAt( 0 );
				length++;
			}
		}
		return new String( password );
	}

	@SuppressWarnings("deprecation")
	private byte[] computeHash( final String message ) {
		return Hashing.md5().hashString( message, StandardCharsets.UTF_8 ).asBytes();
	}

}
