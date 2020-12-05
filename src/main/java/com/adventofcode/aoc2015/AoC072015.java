package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnTabOrSpace;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;

class AoC072015 implements Solution {

	//@formatter:off
	private static final Map<String, BinaryOperator<Integer>> OPERATIONS = Map.of(
			"AND", ( a, b ) -> a & b,
			"OR", ( a, b ) -> a | b,
			"NOT", ( a, b ) -> ~a,
			"LSHIFT", ( a, b ) -> a << b,
			"RSHIFT", ( a, b ) -> a >>> b,
			"IDENTITY", ( a, b ) -> a
	);
	//@formatter:on

	private final Map<String, Integer> initiallyAvailable = new HashMap<>();
	private final Set<String> initiallyUnavailable = new HashSet<>();
	private final Map<String, BinaryOperator<Integer>> inputFunctions = new HashMap<>();
	private final ListMultimap<String, String> dependencies = ArrayListMultimap.create();

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		initialize( input );
		return itoa( findSignal() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		initialize( input );
		final Integer result = findSignal();
		initiallyUnavailable.remove( "b" );
		initiallyAvailable.put( "b", result );
		return itoa( findSignal() );
	}

	private void initialize( final Stream<String> input ) {
		initiallyAvailable.clear();
		initiallyUnavailable.clear();
		inputFunctions.clear();
		dependencies.clear();

		input.forEach( line -> {
			final var tokens = splitOnTabOrSpace( line );
			final String destination = tokens.get( tokens.size() - 1 );
			switch ( tokens.size() ) {
			case 3 -> {
				final String sourceA = tokens.get( 0 );
				initialize( destination, "IDENTITY", sourceA, null );
			}
			case 4 -> {
				final String operation = tokens.get( 0 );
				final String sourceA = tokens.get( 1 );
				initialize( destination, operation, sourceA, null );
			}
			case 5 -> {
				final String operation = tokens.get( 1 );
				final String sourceA = tokens.get( 0 );
				final String sourceB = tokens.get( 2 );
				initialize( destination, operation, sourceA, sourceB );
			}
			default -> throw new IllegalStateException(
					"Unexpected number of tokens: " + tokens.size() );
			}
		} );
	}

	private void initialize( final String destination, final String operation, final String sourceA,
			final String sourceB ) {
		initiallyUnavailable.add( destination );
		inputFunctions.put( destination, OPERATIONS.get( operation ) );
		initializeSourceSignal( destination, sourceA );
		if ( sourceB != null ) {
			initializeSourceSignal( destination, sourceB );
		}
	}

	private void initializeSourceSignal( final String destination, final String source ) {
		dependencies.put( destination, source );
		if ( source.matches( "\\d+" ) ) {
			initiallyAvailable.put( source, atoi( source ) );
		}
	}

	private Integer findSignal() {
		final var available = new HashMap<>( initiallyAvailable );
		final var unavailable = new HashSet<>( initiallyUnavailable );

		while ( !available.containsKey( "a" ) || !available.containsKey( "b" ) ) {
			final var unavailableItr = unavailable.iterator();
			while ( unavailableItr.hasNext() ) {
				final String unavailableSignal = unavailableItr.next();
				final var needed = dependencies.get( unavailableSignal );
				final boolean canBeComputed = needed.stream().allMatch( available::containsKey );
				if ( canBeComputed ) {
					final Integer sourceA = available.get( needed.get( 0 ) );
					final Integer sourceB = available.get( Iterables.get( needed, 1, null ) );
					final Integer result = inputFunctions.get( unavailableSignal )
							.apply( sourceA, sourceB );
					available.put( unavailableSignal, result );
					unavailableItr.remove();
				}
			}
		}
		return available.get( "a" );
	}
}
