package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;

import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

class AoC042015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( getFirstString( input ), true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( getFirstString( input ), false );
	}

	private String solve( final String secret, final boolean first ) {
		final int res = IntStream.range( 0, Integer.MAX_VALUE )
				.dropWhile( discard( secret, first ? "00000" : "000000" )::test )
				.findFirst()
				.orElseThrow();
		return itoa( res );
	}

	private Predicate<Integer> discard( final String secret, final String prefix ) {
		return Predicate.not( i -> computeHash( secret + i ).startsWith( prefix ) );
	}

	private String computeHash( final String message ) {
		return Hashing.md5().hashString( message, Charsets.UTF_8 ).toString();
	}
}
