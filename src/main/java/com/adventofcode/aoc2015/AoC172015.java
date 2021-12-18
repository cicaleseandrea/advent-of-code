package com.adventofcode.aoc2015;

import static java.util.Comparator.reverseOrder;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongStream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC172015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, ( a, b ) -> a >= b );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, Integer::equals );
	}

	private String solve( final Stream<String> input,
			final BiPredicate<Integer, Integer> countCombination ) {
		final var containers = toLongStream( input ).sorted( reverseOrder() ).toList();
		final var liters = containers.size() < 6 ? 25 : 150;

		final var combinations = fill( liters, containers );

		final var minContainers = combinations.stream().mapToInt( List::size ).min().orElseThrow();
		final long count = combinations.stream()
				.filter( combination -> countCombination.test( combination.size(), minContainers ) )
				.count();
		return itoa( count );
	}

	private List<List<Long>> fill( final long liters, final List<Long> containers ) {
		final List<List<Long>> combinations = new ArrayList<>();

		for ( int i = 0; i < containers.size(); i++ ) {
			final var container = containers.get( i );
			final var litersLeft = liters - container;

			if ( litersLeft == 0 ) {
				combinations.add( new ArrayList<>( List.of( container ) ) );
			} else if ( litersLeft > 0 ) {
				final var containersLeft = containers.subList( i + 1, containers.size() );
				final var combinationsTail = fill( litersLeft, containersLeft );

				combinationsTail.forEach( combination -> combination.add( container ) );
				combinations.addAll( combinationsTail );
			}
		}
		return combinations;
	}

}
