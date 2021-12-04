package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.itoa;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC032021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var numbers = input.toList();
		int gammaRate = 0;
		final int numberLength = numbers.get( 0 ).length();
		for ( int i = 0; i < numberLength; i++ ) {
			int ones = 0;
			for ( final String number : numbers ) {
				final int bit = charToInt( number.charAt( i ) );
				ones += ( bit == 1 ) ? +1 : -1;
			}
			gammaRate *= 2;
			gammaRate += ( ones > 0 ) ? 1 : 0;
		}
		final int episolonRate = gammaRate ^ ( ( 1 << numberLength ) - 1 );
		return itoa( episolonRate * gammaRate );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final Set<String> numbers = input.collect( Collectors.toSet() );
		final long oxygen = solve( ( a, b ) -> a >= b, numbers, 0 );
		final long co2 = solve( ( a, b ) -> a < b, numbers, 0 );
		return itoa( oxygen * co2 );
	}

	private int solve( final BiPredicate<Integer, Integer> predicate, final Set<String> numbers,
			final int position ) {
		var ones = new HashSet<String>();
		var zeroes = new HashSet<String>();
		for ( final String number : numbers ) {
			final int bit = charToInt( number.charAt( position ) );
			if ( bit == 1 ) {
				ones.add( number );
			} else {
				zeroes.add( number );
			}
		}

		final Set<String> keep;
		if ( predicate.test( ones.size(), zeroes.size() ) ) {
			keep = ones;
		} else {
			keep = zeroes;
		}
		if ( keep.size() == 1 ) {
			return keep.stream().map( n -> Integer.parseInt( n, 2 ) ).findFirst().orElseThrow();
		} else {
			return solve( predicate, keep, position + 1 );
		}
	}
}
