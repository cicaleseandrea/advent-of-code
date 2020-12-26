package com.adventofcode.aoc2020;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.incrementMod;
import static com.adventofcode.utils.Utils.itoa;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC232020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final int maxCups = first ? 9 : 1_000_000;
		final int[] cupToNext = initialize( input, maxCups );
		int current = cupToNext[0];
		for ( int i = 0; i < ( first ? 100 : 10_000_000 ); i++ ) {
			// select 3 cups
			final int cup1 = cupToNext[current];
			final int cup2 = cupToNext[cup1];
			final int cup3 = cupToNext[cup2];
			// detach main list from 3 cups
			cupToNext[current] = cupToNext[cup3];
			// find destination
			final int destination = getDestinationLabel( current, maxCups,
					List.of( cup1, cup2, cup3 ) );
			// insert 3 cups after destination
			final int destinationNext = cupToNext[destination];
			cupToNext[destination] = cup1;
			cupToNext[cup3] = destinationNext;

			current = cupToNext[current];
		}
		current = cupToNext[1];
		if ( first ) {
			final StringBuilder result = new StringBuilder();
			for ( int i = 0; i < maxCups - 1; i++ ) {
				result.append( current );
				current = cupToNext[current];
			}
			return result.toString();
		} else {
			return itoa( (long) current * cupToNext[current] );
		}
	}

	private int[] initialize( final Stream<String> input, final int maxCups ) {
		final int[] cupToNext = new int[maxCups + 1];
		final List<Integer> cups = IntStream.concat(
				getFirstString( input ).chars().map( Utils::charToInt ),
				IntStream.rangeClosed( 10, maxCups ) ).boxed().collect( toList() );
		for ( int i = 0; i < cups.size(); i++ ) {
			cupToNext[cups.get( i )] = cups.get( incrementMod( i, maxCups ) );
		}
		cupToNext[0] = cups.get( 0 );
		return cupToNext;
	}

	private int getDestinationLabel( final int current, final int max,
			final Collection<Integer> subset ) {
		int destination = current;
		do {
			destination--;
			if ( destination == 0 ) {
				destination = max;
			}
		} while ( subset.contains( destination ) );
		return destination;
	}

}
