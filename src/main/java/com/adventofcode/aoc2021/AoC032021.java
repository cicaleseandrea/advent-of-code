package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.itoa;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

class AoC032021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final Set<String> numbers = input.collect( Collectors.toSet() );
		final long gammaRate = solve( ( ones, zeroes ) -> ones >= zeroes, numbers, 0, true );
		final long epsilonRate = solve( ( ones, zeroes ) -> ones < zeroes, numbers, 0, true );
		return itoa( gammaRate * epsilonRate );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final Set<String> numbers = input.collect( Collectors.toSet() );
		final long oxygen = solve( ( ones, zeroes ) -> ones >= zeroes, numbers, 0, false );
		final long co2 = solve( ( ones, zeroes ) -> ones < zeroes, numbers, 0, false );
		return itoa( oxygen * co2 );
	}

	private int solve( final BiPredicate<Integer, Integer> bitCriteria, final Set<String> numbers,
			final int position, final boolean first ) {
		if ( numbers.size() == 1 ) {
			return numbers.stream().map( n -> Integer.parseInt( n, 2 ) ).findFirst().orElseThrow();
		}

		final int numberLength = numbers.stream().findFirst().orElseThrow().length();
		if ( position >= numberLength ) {
			return 0;
		}

		final var onesAndZeroes = splitOnesAndZeroes( numbers, position );
		final var ones = onesAndZeroes.getFirst();
		final var zeroes = onesAndZeroes.getSecond();
		final boolean bitCriteriaResult = bitCriteria.test( ones.size(), zeroes.size() );

		if ( first ) {
			final int head = bitCriteriaResult ? ( 1 << ( numberLength - position - 1 ) ) : 0;
			final int tail = solve( bitCriteria, numbers, position + 1, first );
			return head + tail;
		} else {
			final Set<String> keep = bitCriteriaResult ? ones : zeroes;
			return solve( bitCriteria, keep, position + 1, first );
		}
	}

	private Pair<HashSet<String>, HashSet<String>> splitOnesAndZeroes( final Set<String> numbers,
			final int position ) {
		final var ones = new HashSet<String>();
		final var zeroes = new HashSet<String>();
		for ( final String number : numbers ) {
			final char bit = number.charAt( position );
			if ( bit == '1' ) {
				ones.add( number );
			} else {
				zeroes.add( number );
			}
		}
		return new Pair<>( ones, zeroes );
	}
}
