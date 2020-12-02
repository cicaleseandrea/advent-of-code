package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import java.util.function.BiPredicate;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Triplet;

class AoC022020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, ( password, args ) -> {
			final int min = args.getFirst();
			final int max = args.getSecond();
			final char letter = args.getThird();
			final long count = password.chars().filter( c -> c == letter ).count();
			return min <= count && count <= max;
		} );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, ( password, args ) -> {
			final int first = args.getFirst();
			final int second = args.getSecond();
			final char letter = args.getThird();
			return password.charAt( first - 1 ) == letter ^ password.charAt( second - 1 ) == letter;
		} );
	}

	private String solve( final Stream<String> input,
			final BiPredicate<String, Triplet<Integer, Integer, Character>> check ) {
		return itoa( input.filter( line -> {
			final var lineArray = line.split( ": " );
			final String policy = lineArray[0];
			final String password = lineArray[1];

			final var policyArray = policy.split( " " );
			final var numbers = policyArray[0].split( "-" );
			final int first = atoi( numbers[0] );
			final int second = atoi( numbers[1] );
			final char letter = policyArray[1].charAt( 0 );
			final var args = new Triplet<>( first, second, letter );

			return check.test( password, args );
		} ).count() );
	}
}
