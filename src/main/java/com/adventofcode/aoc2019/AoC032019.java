package com.adventofcode.aoc2019;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.manhattanDistance;
import static com.adventofcode.utils.Utils.splitOnRegex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.google.common.collect.Sets;

class AoC032019 implements Solution {

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input,
				//distance from center
				( firstWire, secondWire ) -> ( intersection ) -> manhattanDistance( Pair.ZERO,
						intersection ) );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input,
				//sum of steps
				( firstWire, secondWire ) -> ( intersection ) -> firstWire.get(
						intersection ) + secondWire.get( intersection ) );
	}

	private String solve( final Stream<String> input,
			final BiFunction<Map<Pair<Long, Long>, Long>, Map<Pair<Long, Long>, Long>, ToLongFunction<Pair<Long, Long>>> function ) {
		final var wires = input.collect( toList() );

		final Set<Pair<Long, Long>> firstWire = new HashSet<>();
		final Map<Pair<Long, Long>, Long> firstWireSteps = new HashMap<>();
		initializeWire( wires.get( 0 ), firstWire, firstWireSteps );

		final Set<Pair<Long, Long>> secondWire = new HashSet<>();
		final Map<Pair<Long, Long>, Long> secondWireSteps = new HashMap<>();
		initializeWire( wires.get( 1 ), secondWire, secondWireSteps );

		final var curriedFunction = function.apply( firstWireSteps, secondWireSteps );
		final Long min = Sets.intersection( firstWire, secondWire )
				.stream()
				.mapToLong( curriedFunction )
				.min()
				.orElseThrow();
		return itoa( min );
	}

	private void initializeWire( final String wireString, final Set<Pair<Long, Long>> wire,
			final Map<Pair<Long, Long>, Long> positions ) {
		final Pair<Long, Long> start = new Pair<>( 0L, 0L );
		final AtomicLong steps = new AtomicLong();
		splitOnRegex( wireString, "," ).forEach( direction -> {
			switch ( direction.charAt( 0 ) ) {
			case 'U' -> followDirection( wire, positions, direction, start, steps,
					it -> it.setSecond( it.getSecond() + 1 ) );
			case 'D' -> followDirection( wire, positions, direction, start, steps,
					it -> it.setSecond( it.getSecond() - 1 ) );
			case 'L' -> followDirection( wire, positions, direction, start, steps,
					it -> it.setFirst( it.getFirst() - 1 ) );
			case 'R' -> followDirection( wire, positions, direction, start, steps,
					it -> it.setFirst( it.getFirst() + 1 ) );
			default -> throw new IllegalStateException();
			}
		} );
	}

	private void followDirection( final Set<Pair<Long, Long>> wire,
			final Map<Pair<Long, Long>, Long> positions, final String direction,
			final Pair<Long, Long> current, final AtomicLong steps,
			final Consumer<Pair<Long, Long>> move ) {
		for ( int i = 0; i < extractIntegerFromString( direction ); i++ ) {
			move.accept( current );
			final Pair<Long, Long> point = new Pair<>( current.getFirst(), current.getSecond() );
			wire.add( point );
			positions.put( point, steps.incrementAndGet() );
		}
	}

}
