package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

class AoC062021 implements Solution {

	// used for memoization
	private static final Map<Pair<Long, Long>, Long> cache = new HashMap<>();

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 80 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 256 );
	}

	private String solve( final Stream<String> input, final int days ) {
		final var ages = new ArrayList<>( toLongList( input.findFirst().orElseThrow() ) );
		long result = 0;
		for ( final long age : ages ) {
			result += solve( age, days );
		}

		return itoa( result );
	}

	private long solve( final long age, final long days ) {
		final Pair<Long, Long> input = new Pair<>( age, days );
		if ( cache.containsKey( input ) ) {
			return cache.get( input );
		}

		final long daysAfterSpawn = days - ( age + 1 );
		final long result;
		if ( daysAfterSpawn < 0 ) {
			// no time left to spawn
			result = 1;
		} else {
			// spawn
			result = /*existing one*/ solve( 6, daysAfterSpawn ) + /*new one*/ solve( 8,
					daysAfterSpawn );
		}

		cache.put( input, result );
		return result;
	}
}
