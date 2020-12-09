package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Collections2;

class AoC132015 implements Solution {
	private static final Pattern HAPPINESS_REGEX = Pattern.compile(
			"(\\w+) would (\\w+ \\d+) happiness units by sitting next to (\\w+)\\." );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final var happiness = getHappiness( input );
		final var people = new HashSet<>( happiness.keySet() );
		if ( !first ) {
			final String ME = "me";
			for ( final String person : people ) {
				happiness.computeIfAbsent( person, k -> new HashMap<>() ).put( ME, 0 );
				happiness.computeIfAbsent( ME, k -> new HashMap<>() ).put( person, 0 );
			}
			people.add( ME );
		}

		final int size = people.size();
		int max = 0;
		for ( final var arrangement : Collections2.permutations( people ) ) {
			int sum = 0;
			for ( int i = 0; i < size; i++ ) {
				final String personA = arrangement.get( i );
				final String personB = arrangement.get( Utils.incrementMod( i, size ) );
				sum += happiness.get( personA ).get( personB );
				sum += happiness.get( personB ).get( personA );
			}
			if ( sum > max ) {
				max = sum;
			}
		}
		return itoa( max );
	}

	private Map<String, Map<String, Integer>> getHappiness( final Stream<String> input ) {
		final Map<String, Map<String, Integer>> happiness = new HashMap<>();
		input.forEach( line -> {
			final var matcher = HAPPINESS_REGEX.matcher( line );
			if ( !matcher.matches() ) {
				throw new IllegalArgumentException();
			}
			final String personA = matcher.group( 1 );
			final String personB = matcher.group( 3 );
			final int change = atoi(
					matcher.group( 2 ).replace( "lose ", "-" ).replace( "gain ", "" ) );
			happiness.computeIfAbsent( personA, k -> new HashMap<>() ).put( personB, change );
		} );

		return happiness;
	}

}
