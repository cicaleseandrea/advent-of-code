package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.itoa;

import java.util.List;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC092020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return itoa( findNumber( Utils.toLongList( input ) ) );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var list = Utils.toLongList( input );
		final long goal = findNumber( list );
		int start = 0;
		int end = 0;
		long sum = 0;
		while ( sum != goal ) {
			sum += list.get( end );
			end++;
			while ( sum > goal ) {
				sum -= list.get( start );
				start++;
			}
		}

		final var stats = list.subList( start, end )
				.stream()
				.mapToLong( Long::longValue )
				.summaryStatistics();
		return itoa( stats.getMin() + stats.getMax() );
	}

	private long findNumber( final List<Long> list ) {
		int preambleSize = list.size() < 30 ? 5 : 25;
		final var rest = list.subList( preambleSize, list.size() );
		int i = 0;
		int j = preambleSize;
		for ( final long n : rest ) {
			final List<Long> previous = list.subList( i++, j++ );
			if ( !isValidNumber( previous, n ) ) {
				return n;
			}
		}
		throw new IllegalArgumentException();
	}

	private boolean isValidNumber( final List<Long> list, final long n ) {
		final int length = list.size();
		for ( int i = 0; i < length; i++ ) {
			final long a = list.get( i );
			for ( int j = i + 1; j < length; j++ ) {
				final long b = list.get( j );
				final long sum = a + b;
				if ( sum == n ) {
					return true;
				}
			}
		}
		return false;
	}

}
