package com.adventofcode.aoc2021;

import static java.util.Comparator.reverseOrder;

import static com.adventofcode.utils.Utils.NEIGHBOURS;
import static com.adventofcode.utils.Utils.getDigitsMatrix;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.listGetOrDefault;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

class AoC092021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final var heightmap = getDigitsMatrix( input );
		final List<Pair<Integer, Integer>> lowPoints = new ArrayList<>();
		for ( int i = 0; i < heightmap.size(); i++ ) {
			for ( int j = 0; j < heightmap.get( 0 ).size(); j++ ) {
				final var point = new Pair<>( i, j );
				if ( isLowPoint( heightmap, point ) ) {
					lowPoints.add( point );
				}
			}
		}

		if ( first ) {
			return itoa( lowPoints.stream()
					.mapToInt( lowPoint -> heightmap.get( lowPoint.getFirst() )
							.get( lowPoint.getSecond() ) + 1 )
					.sum() );
		} else {
			return itoa( lowPoints.stream()
					.map( lowPoint -> findBasinSize( heightmap, lowPoint ) )
					.sorted( reverseOrder() )
					.limit( 3 )
					.reduce( 1, ( a, b ) -> a * b ) );
		}
	}

	private boolean isLowPoint( final List<List<Integer>> heightmap,
			final Pair<Integer, Integer> point ) {
		return NEIGHBOURS.stream()
				.map( neighbourOffset -> listGetOrDefault(
						listGetOrDefault( heightmap, neighbourOffset.getFirst() + point.getFirst(),
								List.of() ), neighbourOffset.getSecond() + point.getSecond(),
						null ) )
				.filter( Objects::nonNull )
				.noneMatch( neighbour -> neighbour <= heightmap.get( point.getFirst() )
						.get( point.getSecond() ) );
	}

	private int findBasinSize( final List<List<Integer>> heightmap,
			final Pair<Integer, Integer> point ) {
		final Integer pointValue = listGetOrDefault(
				listGetOrDefault( heightmap, point.getFirst(), List.of() ), point.getSecond(),
				null );
		if ( pointValue == null || pointValue == 9 ) {
			return 0;
		} else {
			heightmap.get( point.getFirst() ).set( point.getSecond(), null );
			return 1 + NEIGHBOURS.stream()
					.mapToInt( neighbourOffset -> findBasinSize( heightmap,
							new Pair<>( neighbourOffset.getFirst() + point.getFirst(),
									neighbourOffset.getSecond() + point.getSecond() ) ) )
					.sum();
		}
	}
}
