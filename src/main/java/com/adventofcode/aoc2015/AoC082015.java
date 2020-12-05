package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.itoa;

import java.util.Map;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC082015 implements Solution {

	private static final char BACKSLASH = '\\';
	private static final char DOUBLE_QUOTE = '"';
	private static final char HEX = 'x';

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var increments = Map.of( HEX, 3, DOUBLE_QUOTE, 1 );
		return itoa( input.mapToInt( string -> countCharacters( string, increments ) ).sum() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var increments = Map.of( HEX, 1, DOUBLE_QUOTE, 2 );
		return itoa( input.mapToInt( string -> countCharacters( string, increments ) ).sum() );
	}

	private static Integer countCharacters( final String string,
			final Map<Character, Integer> increments ) {
		final char[] chars = string.toCharArray();
		int count = 0;
		for ( int i = 0; i < chars.length; i++ ) {
			final char curr = chars[i];
			if ( curr == BACKSLASH ) {
				final char next = chars[i + 1];
				if ( next == HEX ) {
					count += increments.get( HEX );
					i += 3; //skip 3 chars
				} else {
					count += increments.get( DOUBLE_QUOTE );
					i++; //skip 1 char
				}
			} else if ( curr == DOUBLE_QUOTE ) {
				count += increments.get( DOUBLE_QUOTE );
			}
		}
		return count;
	}

}
