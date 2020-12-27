package com.adventofcode.aoc2015;

import static java.util.Comparator.naturalOrder;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.google.common.hash.Hashing;

class AoC042015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( getFirstString( input ), 5 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( getFirstString( input ), 6 );
	}

	private String solve( final String secret, final int startingZeros ) {
		final Set<Integer> result = Collections.synchronizedSet( new HashSet<>() );
		IntStream.iterate( 0, i -> result.isEmpty(), i -> i + 1 )
				.parallel()
				.filter( i -> startsWithZeros( computeHash( secret + i ), startingZeros ) )
				.forEach( result::add );
		return itoa( result.stream().min( naturalOrder() ).orElseThrow() );
	}

	private boolean startsWithZeros( final byte[] bytes, final int startingZeros ) {
		for ( int j = 0; j < startingZeros / 2; j++ ) {
			if ( bytes[j] != 0 ) {
				return false;
			}
		}
		return startingZeros % 2 == 0 || ( ( bytes[startingZeros / 2] >>> 4 ) & 0xF ) == 0;
	}

	@SuppressWarnings("deprecation")
	private byte[] computeHash( final String message ) {
		return Hashing.md5().hashString( message, StandardCharsets.UTF_8 ).asBytes();
	}
}
