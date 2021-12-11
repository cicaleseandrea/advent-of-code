package com.adventofcode.aoc2021;

import static java.util.Comparator.reverseOrder;

import static com.adventofcode.utils.Utils.NEIGHBOURS_4;
import static com.adventofcode.utils.Utils.getDigitsMatrix;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.listGetOrDefault;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.adventofcode.Solution;

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
		final List<Point> lowPoints = new ArrayList<>();
		for ( int i = 0; i < heightmap.size(); i++ ) {
			for ( int j = 0; j < heightmap.get( 0 ).size(); j++ ) {
				final var point = new Point( i, j );
				if ( isLowPoint( heightmap, point ) ) {
					lowPoints.add( point );
				}
			}
		}

		if ( first ) {
			return itoa( lowPoints.stream()
					.mapToInt( lowPoint -> heightmap.get( lowPoint.x ).get( lowPoint.y ) + 1 )
					.sum() );
		} else {
			return itoa( lowPoints.stream()
					.map( lowPoint -> findBasinSize( heightmap, lowPoint ) )
					.sorted( reverseOrder() )
					.limit( 3 )
					.reduce( 1, ( a, b ) -> a * b ) );
		}
	}

	private boolean isLowPoint( final List<List<Integer>> heightmap, final Point point ) {
		return NEIGHBOURS_4.stream()
				.map( neighbourOffset -> listGetOrDefault(
						listGetOrDefault( heightmap, point.x + neighbourOffset.getFirst(),
								List.of() ), point.y + neighbourOffset.getSecond(), null ) )
				.filter( Objects::nonNull )
				.noneMatch( neighbour -> neighbour <= heightmap.get( point.x ).get( point.y ) );
	}

	private int findBasinSize( final List<List<Integer>> heightmap, final Point point ) {
		final Integer pointValue = listGetOrDefault(
				listGetOrDefault( heightmap, point.x, List.of() ), point.y, null );
		if ( pointValue == null || pointValue == 9 ) {
			return 0;
		} else {
			heightmap.get( point.x ).set( point.y, null );
			return 1 + NEIGHBOURS_4.stream()
					.mapToInt( neighbourOffset -> findBasinSize( heightmap,
							new Point( point.x + neighbourOffset.getFirst(),
									point.y + neighbourOffset.getSecond() ) ) )
					.sum();
		}
	}

	private record Point(int x, int y) {}
}
